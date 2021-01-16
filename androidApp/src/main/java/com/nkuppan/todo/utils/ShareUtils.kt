package com.nkuppan.todo.utils

import android.content.Context
import android.content.Intent
import com.nkuppan.todo.R

object ShareUtils {

    /**
     * Used to create a email action intent and opening the chooser to
     *
     * @param aContext to open email application
     * @param aEmailId email id of the recipient to send the content
     * @param aEmailSubject subject to be attached into the email
     * @param aEmailContent content of the of email
     */
    fun sendEmailFeedback(
        aContext: Context,
        aEmailId: String,
        aEmailSubject: String,
        aEmailContent: String
    ) {

        val emailIntent = Intent(Intent.ACTION_SEND)

        emailIntent.type = "plain/text"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(aEmailId))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, aEmailSubject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, aEmailContent)

        aContext.startActivity(
            Intent.createChooser(
                emailIntent,
                aContext.getString(R.string.send_email_title)
            )
        )
    }
}