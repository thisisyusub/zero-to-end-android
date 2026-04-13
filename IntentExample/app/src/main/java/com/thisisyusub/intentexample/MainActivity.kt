package com.thisisyusub.intentexample

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thisisyusub.intentexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // 1. Declare the binding variable late-initialized
    private lateinit var binding: ActivityMainBinding

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent(),
    ) { uri ->
        if (uri != null) {
            binding.ivAvatar.setImageURI(uri)
            Toast.makeText(this, "Avatar updated!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Action cancelled.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.btnViewProfile.setOnClickListener {
            val mockUser = User(id = 1, name = "Kanan", role = "Android Engineer")
            val intent = ProfileActivity.newIntent(this, mockUser)
            startActivity(intent)
        }

        binding.btnContactSupport.setOnClickListener {
//            applicationContext.sendEmail(
//                email = "kanan.yusub@gmail.com",
//                subject = "Message from the zero-to-end-android",
//                body = "Hello World!",
//            )

//            val intent = Intent(Intent.ACTION_SEND)
//            val chooser = Intent.createChooser(intent, "Choose Mail App")
//
//            if(chooser.resolveActivity(packageManager) != null) {
//                startActivity(chooser)
//            }

            //    createAlarm("DUR", 5, 0)

            addEvent(
                title = "Meeting with Team",
                location = "Google Meet",
                begin = System.currentTimeMillis(),
                end = System.currentTimeMillis() + 60 * 60 * 1000,
            )
        }

        binding.btnChangeAvatar.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
    }

    fun createAlarm(message: String, hour: Int, minutes: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_HOUR, hour)
            putExtra(AlarmClock.EXTRA_MINUTES, minutes)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    fun addEvent(title: String, location: String, begin: Long, end: Long) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.Events.EVENT_LOCATION, location)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}

