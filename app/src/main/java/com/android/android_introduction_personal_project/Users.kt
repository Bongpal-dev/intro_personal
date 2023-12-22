package com.android.android_introduction_personal_project

object Users {
    val userList = arrayListOf<User>(
        User("배영수", "etgt777", "12345", 33, "etgt@naver.com", "ENTP")
    )

    fun addUser(user: User) {
        userList += user
    }
}