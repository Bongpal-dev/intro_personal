package com.android.android_introduction_personal_project

object Users {
    val userList = arrayListOf<User>()

    fun addUser(user: User) {
        userList += user
    }
}