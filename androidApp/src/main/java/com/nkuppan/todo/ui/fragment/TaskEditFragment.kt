package com.nkuppan.todo.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentTaskEditBinding
import com.nkuppan.todo.db.SubTask
import com.nkuppan.todo.extention.EventObserver
import com.nkuppan.todo.extention.autoCleared
import com.nkuppan.todo.extention.setupStringSnackbar
import com.nkuppan.todo.shared.utils.CommonUtils
import com.nkuppan.todo.ui.viewmodel.TaskCreateViewModel
import com.nkuppan.todo.utils.AppUIUtils
import com.nkuppan.todo.utils.NavigationManager.setFailureResult
import com.nkuppan.todo.utils.NavigationManager.setSuccessResult
import com.nkuppan.todo.utils.RequestParam

class TaskEditFragment : BaseFragment() {

    private var viewModel: TaskCreateViewModel by autoCleared()

    private var dataBinding: FragmentTaskEditBinding by autoCleared()

    private var menu: Menu? = null

    private var subTaskViewList: MutableList<View> = mutableListOf()

    private var subTaskList: MutableList<SubTask> = mutableListOf()

    private var isDataSavedSilently = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_edit, container, false)
        viewModel = ViewModelProvider(this).get(TaskCreateViewModel::class.java)
        dataBinding = FragmentTaskEditBinding.bind(view)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this.viewLifecycleOwner
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_task_edit, menu)
        this.menu = menu
        enableRevertOrDone()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_done -> {
                viewModel.markThisTaskAsCompleted()
                true
            }
            R.id.action_revert -> {
                viewModel.revertThisTask()
                true
            }
            R.id.action_delete -> {
                viewModel.deleteThisTask()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (requireActivity() as AppCompatActivity).setSupportActionBar(dataBinding.toolbar)

        setHasOptionsMenu(true)

        super.onViewCreated(view, savedInstanceState)

        dataBinding.root.setupStringSnackbar(
            this,
            viewModel.snackBarMessage,
            Snackbar.LENGTH_SHORT
        )

        dataBinding.toolbar.setNavigationOnClickListener {
            if (isDataSavedSilently) {
                setSuccessResult()
            } else {
                setFailureResult()
            }
        }

        viewModel.taskGroupName.observe(viewLifecycleOwner, EventObserver {
            dataBinding.taskGroupList.text = it
        })

        viewModel.selectDateTime.observe(viewLifecycleOwner, EventObserver {
            AppUIUtils.showDatePickerDialog(this, viewModel.taskEndDate) {
                viewModel.updateDateTime(it)
            }
        })

        viewModel.success.observe(viewLifecycleOwner, EventObserver {
            setSuccessResult()
        })

        viewModel.silentSuccess.observe(viewLifecycleOwner, EventObserver {
            requireActivity().setResult(Activity.RESULT_OK)
            isDataSavedSilently = true
        })

        viewModel.changeTaskGroup.observe(viewLifecycleOwner, EventObserver {
            TaskGroupSelectionFragment().apply {
                arguments = Bundle().apply {
                    putString(RequestParam.TASK_GROUP_ID, viewModel.getSelectedTaskGroupId())
                }
                selection = {
                    viewModel.updateTaskGroup(it)
                }
                show(this@TaskEditFragment.childFragmentManager, "change_task_group")
            }
        })

        viewModel.deleted.observe(viewLifecycleOwner, EventObserver {
            setSuccessResult()
        })

        viewModel.updateMenu.observe(viewLifecycleOwner, EventObserver {
            enableRevertOrDone()
        })

        viewModel.loadTaskDetails(
            requireActivity().intent?.extras?.getString(RequestParam.TASK_ID)
        )

        viewModel.loadTaskGroupList()

        viewModel.subTaskList.observe(viewLifecycleOwner, { it ->

            addSubTaskContainer()

            if (!it.isNullOrEmpty()) {
                it.forEach {
                    if (it.status != 2L) {
                        addSubTaskContainer(it)
                    }
                }
            }
        })

        dataBinding.taskTitle.postDelayed({
            addTextWatcherToSaveAutomatically()
        }, 500)
    }

    private fun addTextWatcherToSaveAutomatically() {

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Nothing to do
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Nothing to do
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.saveTaskDetails(saveSilently = true)
            }
        }

        dataBinding.taskTitle.addTextChangedListener(textWatcher)
        dataBinding.taskDescription.addTextChangedListener(textWatcher)
    }

    private fun addSubTaskContainer(aSubTask: SubTask? = null) {

        val subTaskView = LayoutInflater.from(requireContext()).inflate(
            R.layout.sub_task_view, null
        )

        val subTaskText = subTaskView.findViewById<TextView>(R.id.sub_task_text)
        subTaskText.setOnClickListener {
            val subTask = SubTask(
                id = CommonUtils.getRandomUUID(),
                description = "",
                parent_task_id = viewModel.getTaskId(),
                status = 1,
                created_on = CommonUtils.getDateTime().toDouble(),
                updated_on = CommonUtils.getDateTime().toDouble()
            )
            viewModel.createSubTask(subTask)
            addSubTaskContainer(subTask)
        }

        val container = subTaskView.findViewById<LinearLayout>(R.id.sub_task_input_container)
        val input = subTaskView.findViewById<EditText>(R.id.sub_task_input)
        val deleteSubTaskView = subTaskView.findViewById<ImageView>(R.id.clear_text)
        val markAsCompleted = subTaskView.findViewById<RadioButton>(R.id.mark_complete)

        input.setText(aSubTask?.description)

        input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Nothing to do
            }

            override fun onTextChanged(aText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.updateSubTask(aSubTask, aText?.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                //Nothing to do
            }
        })

        markAsCompleted.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                viewModel.updateSubTaskStatus(aSubTask)
                removeSubTaskView(subTaskView)
            }
        }

        input.setOnFocusChangeListener { _, focus ->
            deleteSubTaskView.visibility = if (focus) View.VISIBLE else View.GONE
        }

        deleteSubTaskView.setOnClickListener {
            viewModel.deleteSubTask(aSubTask)
            removeSubTaskView(subTaskView)
        }

        if (aSubTask == null) {
            subTaskText.visibility = View.VISIBLE
            container.visibility = View.GONE
        } else {
            subTaskText.visibility = View.GONE
            container.visibility = View.VISIBLE
            deleteSubTaskView.visibility =
                if (aSubTask.description.isNotEmpty()) View.VISIBLE else View.GONE
            subTaskList.add(aSubTask)
        }

        subTaskViewList.add(0, subTaskView)
        dataBinding.subTaskContainer.addView(subTaskView, 0)

        dataBinding.taskTitle.postDelayed({
            input.isFocusable = true
        }, 500)
    }

    private fun removeSubTaskView(subTaskView: View) {
        subTaskViewList.remove(subTaskView)
        dataBinding.subTaskContainer.removeView(subTaskView)
        dataBinding.subTaskContainer.invalidate()
    }

    private fun enableRevertOrDone() {
        val isCompleted = viewModel.isCompleted()
        menu?.findItem(R.id.action_revert)?.apply {
            isVisible = isCompleted
        }
        menu?.findItem(R.id.action_done)?.apply {
            isVisible = !isCompleted
        }
    }
}