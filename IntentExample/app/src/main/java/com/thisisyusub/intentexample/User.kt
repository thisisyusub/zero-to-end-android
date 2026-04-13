package com.thisisyusub.intentexample

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// @Parcelize automatically generates the high-speed Binder IPC code for you
@Parcelize
data class User(
    val id: Int,
    val name: String,
    val role: String
) : Parcelable