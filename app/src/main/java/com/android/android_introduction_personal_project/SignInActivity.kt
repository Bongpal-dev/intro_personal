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
    private val etId by lazy { findViewById<EditText>(R.id.et_sign_in_id) }
    private val etPassword by lazy { findViewById<EditText>(R.id.et_password) }
    private val btnLogin by lazy { findViewById<Button>(R.id.btn_login) }
    private val btnSignUp by lazy { findViewById<ConstraintLayout>(R.id.layout_sign_up) }
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initialize()
    }

    private fun initialize() {
        loginInit()
        signUpInit()
        resultLauncherInit()
    }

    private fun loginInit() {
        btnLogin.setOnClickListener {
            when {
                etId.text.isEmpty() && etPassword.text.isEmpty() -> {
                    showToast(this, "아이디와 비밀번호를 입력해주세요")
                    return@setOnClickListener
                }

                etId.text.isEmpty() -> {
                    showToast(this, "아이디를 입력해주세요")
                    return@setOnClickListener
                }

                etPassword.text.isEmpty() -> {
                    showToast(this, "비밀번호를 입력해주세요")
                    return@setOnClickListener
                }

                Users.userList.none { it.id == etId.text.toString() } -> {
                    showToast(this, "가입되지 않은 아이디입니다")
                    return@setOnClickListener
                }

                Users.userList.find { it.id == etId.text.toString() }?.pw != etPassword.text.toString() -> {
                    showToast(this, "비밀번호가 틀렸습니다")
                    return@setOnClickListener
                }
            }

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("id", etId.text.toString())
            startActivity(intent)
        }
    }

    private fun signUpInit() {
        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun resultLauncherInit() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val nowUser = Users.userList.find {
                        it.id == (result.data?.getStringExtra("id") ?: "")
                    }
                    etId.setText(nowUser?.id ?: "")
                    etPassword.setText(nowUser?.pw ?: "")
                }
            }
    }
}

fun showToast(c: Context, msg: String) {
    Toast.makeText(c, msg, Toast.LENGTH_SHORT).show()
}
