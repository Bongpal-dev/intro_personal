package com.android.android_introduction_personal_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

class HomeActivity : AppCompatActivity() {
    val tv_name by lazy { findViewById<TextView>(R.id.tv_home_name) }
    val tv_id by lazy { findViewById<TextView>(R.id.tv_home_id) }
    val tv_age by lazy { findViewById<TextView>(R.id.tv_age_value) }
    val tv_mbti by lazy { findViewById<TextView>(R.id.tv_mbti_value) }
    val iv_photo by lazy { findViewById<ImageView>(R.id.iv_home_photo) }
    val btn_toMaint by lazy { findViewById<Button>(R.id.btn_to_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val userDetails = Users.userList.find { it.id == intent.getStringExtra("id") }

        toMainClick()
        userInfoUpdate(
            if (userDetails != null) {
                userDetails
            } else {
                return
            },
            Random.nextInt(5)
        )



    }

    private fun userInfoUpdate(d: User, p: Int) {
        tv_name.text = d.name
        tv_id.text = d.id
        tv_age.text = d.age.toString()
        tv_mbti.text = d.mbti

        when (p) {
            0 -> iv_photo.setImageDrawable(getDrawable(R.drawable.illho))
            1 -> iv_photo.setImageDrawable(getDrawable(R.drawable.leeho))
            2 -> iv_photo.setImageDrawable(getDrawable(R.drawable.samho))
            3 -> iv_photo.setImageDrawable(getDrawable(R.drawable.saho))
            4 -> iv_photo.setImageDrawable(getDrawable(R.drawable.ohho))
        }
    }

    private fun toMainClick() {
        btn_toMaint.setOnClickListener {
                    finish()
        }
    }
}