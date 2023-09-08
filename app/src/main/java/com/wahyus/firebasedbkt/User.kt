package com.wahyus.firebasedbkt

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val username: String? = null,
    val name: String? = null,
    val age: String? = null,
    val address: String? = null
)
