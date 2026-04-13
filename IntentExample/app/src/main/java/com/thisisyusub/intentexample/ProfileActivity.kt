package com.thisisyusub.intentexample

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thisisyusub.intentexample.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_USER, User::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_USER)
        }

        requireNotNull(user) { "ProfileActivity must be opened with a valid User object!" }

        binding.tvName.text = user.name
        binding.tvRole.text = "Role: ${user.role}"

        binding.btnClose.setOnClickListener {
            finish() // Destroys this Activity and returns to MainActivity
        }
    }

    // Senior Pattern: Encapsulate Intent Creation
    companion object {
        private const val EXTRA_USER = "com.thisisyusub.intentexample.EXTRA_USER"

        fun newIntent(context: Context, user: User): Intent {
            return Intent(context, ProfileActivity::class.java).apply {
                putExtra(EXTRA_USER, user)
            }
        }
    }
}