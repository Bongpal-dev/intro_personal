package com.android.android_introduction_personal_project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout


class SignInActivity : AppCompatActivity() {
    private val et_id by lazy { findViewById<EditText>(R.id.et_id) }
    private val et_pw by lazy { findViewById<EditText>(R.id.et_password) }
    private val btn_login by lazy { findViewById<Button>(R.id.btn_login) }
    private val btn_signUp by lazy { findViewById<ConstraintLayout>(R.id.layout_sign_up) }
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        clickLogin()
        clickSignUp()
        setResultNext()

        Users.addUser(User("배영수", "qqqqq", "12345", 33, "ENTP"))
    }

    private fun clickLogin() {
        btn_login.setOnClickListener {
            when {
                et_id.text.isEmpty() && et_pw.text.isEmpty() -> {
                    showToast(this, "아이디와 비밀번호를 입력해주세요")
                    return@setOnClickListener
                }

                et_id.text.isEmpty() -> {
                    showToast(this, "아이디를 입력해주세요")
                    return@setOnClickListener
                }

                et_pw.text.isEmpty() -> {
                    showToast(this, "비밀번호를 입력해주세요")
                    return@setOnClickListener
                }

                Users.userList.none { it.id == et_id.text.toString() } -> {
                    showToast(this, "가입되지 않은 아이디입니다")
                    return@setOnClickListener
                }
                Users.userList.find { it.id == et_id.text.toString() }?.pw != et_pw.text.toString() -> {
                    showToast(this, "비밀번호가 틀렸습니다")
                    return@setOnClickListener
                }
            }

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("id", et_id.text.toString())
            startActivity(intent)
        }
    }

    private fun clickSignUp() {
        btn_signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun setResultNext() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val nowUser = Users.userList.find {
                    it.id == (result.data?.getStringExtra("id") ?: "")
                }
                et_id.setText(nowUser?.id ?: "")
                et_pw.setText(nowUser?.pw ?: "")
            }
        }

    }

}

fun showToast(c: Context, msg: String) {
    Toast.makeText(c, msg, Toast.LENGTH_SHORT).show()
}
