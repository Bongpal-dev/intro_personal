package com.android.android_introduction_personal_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

class HomeActivity : AppCompatActivity() {
    val tvName by lazy { findViewById<TextView>(R.id.tv_home_name) }
    val tvId by lazy { findViewById<TextView>(R.id.tv_home_id) }
    val tvAge by lazy { findViewById<TextView>(R.id.tv_age_value) }
    val tvMbti by lazy { findViewById<TextView>(R.id.tv_mbti_value) }
    val ivPhoto by lazy { findViewById<ImageView>(R.id.iv_home_photo) }
    val btnToMain by lazy { findViewById<Button>(R.id.btn_to_main) }
    val userDetails by lazy {
        Users.userList.find { it.id == intent.getStringExtra("id") }
            ?: User("Null", "null", "null", -1, "null", "null")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        toMainInit()
        userInfoUpdate(userDetails, Random.nextInt(5))
    }

    private fun userInfoUpdate(d: User, p: Int) {
        tvName.text = d.name
        tvId.text = d.id
        tvAge.text = d.age.toString()
        tvMbti.text = d.mbti

        when (p) {
            0 -> ivPhoto.setImageDrawable(getDrawable(R.drawable.illho))
            1 -> ivPhoto.setImageDrawable(getDrawable(R.drawable.leeho))
            2 -> ivPhoto.setImageDrawable(getDrawable(R.drawable.samho))
            3 -> ivPhoto.setImageDrawable(getDrawable(R.drawable.saho))
            4 -> ivPhoto.setImageDrawable(getDrawable(R.drawable.ohho))
        }
    }

    private fun toMainInit() {
        btnToMain.setOnClickListener {
            finish()
        }
    }
}