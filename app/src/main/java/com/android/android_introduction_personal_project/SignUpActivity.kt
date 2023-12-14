package com.android.android_introduction_personal_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import kotlin.random.Random

class SignUpActivity : AppCompatActivity() {
    val et_name by lazy { findViewById<EditText>(R.id.et_signUp_name) }
    val et_id by lazy { findViewById<EditText>(R.id.et_signUp_id) }
    val et_pw by lazy { findViewById<EditText>(R.id.et_signUp_password) }
    val et_check_pw by lazy { findViewById<EditText>(R.id.et_signUp_check_password) }
    val spinner_mbti by lazy { findViewById<Spinner>(R.id.spinner_signUp_mbti) }
    val btn_sign_up by lazy { findViewById<Button>(R.id.btn_sign_up) }
    var mbtiValue: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("signupactivity check", "실행되었습니다")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        summitSignUp()
        selectSpinner()
    }

    fun summitSignUp() {
        btn_sign_up.setOnClickListener {
            when {
                et_name.text.isEmpty() -> {
                    showToast(this, "이름을 입력해주세요.")
                    return@setOnClickListener
                }

                et_id.text.isEmpty() -> {
                    showToast(this, "아이디를 입력해주세요")
                    return@setOnClickListener
                }

                Users.userList.any { it.id == et_id.text.toString() } -> {
                    showToast(this, "이미 가입된 아이디입니다")
                    return@setOnClickListener
                }

                this.et_pw.text.isEmpty() -> {
                    showToast(this, "비밀번호를 입력해주세요")
                    return@setOnClickListener
                }

                et_check_pw.text.isEmpty() -> {
                    showToast(this, "비밀번호를 확인해주세요")
                    return@setOnClickListener
                }

                mbtiValue.isEmpty() -> {
                    showToast(this, "MBTI를 선택해주세요")
                    return@setOnClickListener
                }

                et_pw.text.toString() != et_check_pw.text.toString() -> {
                    showToast(this, "비밀번호 정보가 일치하지 않습니다")
                    return@setOnClickListener
                }
            }
            Users.addUser(
                User(
                    et_name.text.toString(),
                    et_id.text.toString(),
                    this.et_pw.text.toString(),
                    Random.nextInt(20, 40),
                    mbtiValue
                )
            )
            intent.putExtra("id", et_id.text.toString())
            setResult(RESULT_OK,intent)
            finish()
            showToast(this, "회원가입이 완료되었습니다.")
        }
    }

    private fun selectSpinner() {
        val mbtiDetails = resources.getStringArray(R.array.mbti_array)

        ArrayAdapter.createFromResource(
            this,
            R.array.mbti_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_mbti.adapter = adapter
        }
        spinner_mbti.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> mbtiValue = ""
                    1 -> mbtiValue = mbtiDetails[1]
                    2 -> mbtiValue = mbtiDetails[2]
                    3 -> mbtiValue = mbtiDetails[3]
                    4 -> mbtiValue = mbtiDetails[4]
                    5 -> mbtiValue = mbtiDetails[5]
                    6 -> mbtiValue = mbtiDetails[6]
                    7 -> mbtiValue = mbtiDetails[7]
                    8 -> mbtiValue = mbtiDetails[8]
                    9 -> mbtiValue = mbtiDetails[9]
                    10 -> mbtiValue = mbtiDetails[10]
                    11 -> mbtiValue = mbtiDetails[11]
                    12 -> mbtiValue = mbtiDetails[12]
                    13 -> mbtiValue = mbtiDetails[13]
                    14 -> mbtiValue = mbtiDetails[14]
                    15 -> mbtiValue = mbtiDetails[15]
                    16 -> mbtiValue = mbtiDetails[16]
                }
                Log.d("mbti_test", "$mbtiValue")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}

