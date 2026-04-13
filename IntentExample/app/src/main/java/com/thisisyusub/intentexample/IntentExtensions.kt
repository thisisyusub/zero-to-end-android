package com.thisisyusub.intentexample

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri

/**
 * IMPLICIT INTENT EXAMPLE
 */

fun Context.sendEmail(email: String, subject: String, body: String) {
    // Implicit Intent using ACTION_SENDTO
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:".toUri() // Ensures only email apps handle this
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    try {
        // The OS takes over here (Binder -> ATMS -> PackageManager)
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        // Senior practice: Never let an implicit intent crash the app
        Toast.makeText(this, "No email app installed!", Toast.LENGTH_SHORT)
            .show()
        // Optional fallback: redirect to a web browser or play store
    }
}