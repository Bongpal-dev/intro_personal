package com.android.android_introduction_personal_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import kotlin.random.Random

class SignUpActivity : AppCompatActivity() {
    val idValue by lazy { findViewById<EditText>(R.id.et_sign_up_input_id) }
    val errMsgId by lazy { findViewById<TextView>(R.id.tv_sign_up_err_id_input) }
    val password by lazy { findViewById<EditText>(R.id.et_sign_up_input_password) }
    val errMsgPassword by lazy { findViewById<TextView>(R.id.tv_sign_up_err_password_input) }
    val checkPassword by lazy { findViewById<EditText>(R.id.et_sign_up_input_check_password) }
    val errMsgCheckPassword by lazy { findViewById<TextView>(R.id.tv_sign_up_err_check_password_input) }
    val name by lazy { findViewById<EditText>(R.id.et_sign_up_input_name) }
    val errMsgName by lazy { findViewById<TextView>(R.id.tv_sign_up_err_name_input) }
    val birth by lazy { findViewById<EditText>(R.id.et_sign_up_input_birth) }
    val errMsgBirth by lazy { findViewById<TextView>(R.id.tv_sign_up_err_birth_input) }
    val emailId by lazy { findViewById<EditText>(R.id.et_sign_up_input_email_id) }
    val emailDomain by lazy { findViewById<EditText>(R.id.et_sign_up_input_email_domain) }
    val emailDomainSpinner by lazy { findViewById<Spinner>(R.id.spinner_sign_up_email_domain) }
    val errMsgEmail by lazy { findViewById<TextView>(R.id.tv_sign_up_err_email) }
    val mbtiSpinner by lazy { findViewById<Spinner>(R.id.spinner_signUp_mbti) }
    val errMsgMbti by lazy { findViewById<TextView>(R.id.tv_sign_up_err_mbti_input) }
    val signUpButton by lazy { findViewById<Button>(R.id.btn_sign_up_summit) }
    var selectMbtiValue: String = ""
    var emailIdValue = ""
    var emailDomainValue = ""
    var elementValidity = arrayListOf<Boolean>(
        false,
        false,
        false,
        false,
        false,
        false,
        false
    )

    val TAG = "Sign_up_activity_test"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("signupactivity check", "실행되었습니다")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        inputValue()
        summitSignUp()
    }

    fun inputValue() {
        mbtiSpinnerSelect()
        emailDomainSpinnerSelect()
        validityCheck()
    }

    fun summitSignUp() {
        signUpButton.setOnClickListener {
            Log.i(TAG, "$elementValidity")

            if (checkElementValidity()) return@setOnClickListener

            Users.addUser(
                User(
                    name.text.toString(),
                    idValue.text.toString(),
                    this.password.text.toString(),
                    Random.nextInt(20, 40),
                    "${emailIdValue}@${emailDomainValue}",
                    selectMbtiValue
                )
            )
            intent.putExtra("id", idValue.text.toString())
            setResult(RESULT_OK, intent)
            finish()
            showToast(this, "회원가입이 완료되었습니다.")
        }
    }

    fun validityCheck() {
        idValidity()
        passwordValidity()
        passwordCheck()
        nameValidity()
        birthValidity()
        emailValidity()
    }

    private fun mbtiSpinnerSelect() {
        val mbtiDetails = resources.getStringArray(R.array.mbti_array)

        ArrayAdapter.createFromResource(
            this, R.array.mbti_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mbtiSpinner.adapter = adapter
        }
        mbtiSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                when (position) {
                    0 -> selectMbtiValue = ""
                    1 -> selectMbtiValue = mbtiDetails[1]
                    2 -> selectMbtiValue = mbtiDetails[2]
                    3 -> selectMbtiValue = mbtiDetails[3]
                    4 -> selectMbtiValue = mbtiDetails[4]
                    5 -> selectMbtiValue = mbtiDetails[5]
                    6 -> selectMbtiValue = mbtiDetails[6]
                    7 -> selectMbtiValue = mbtiDetails[7]
                    8 -> selectMbtiValue = mbtiDetails[8]
                    9 -> selectMbtiValue = mbtiDetails[9]
                    10 -> selectMbtiValue = mbtiDetails[10]
                    11 -> selectMbtiValue = mbtiDetails[11]
                    12 -> selectMbtiValue = mbtiDetails[12]
                    13 -> selectMbtiValue = mbtiDetails[13]
                    14 -> selectMbtiValue = mbtiDetails[14]
                    15 -> selectMbtiValue = mbtiDetails[15]
                    16 -> selectMbtiValue = mbtiDetails[16]
                }
                if (selectMbtiValue.isNotEmpty()) {
                    elementValidity[6] = true
                    errMsgMbti.visibility = View.GONE
                }
                Log.d(TAG, selectMbtiValue)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun emailDomainSpinnerSelect() {
        val domainDetails = resources.getStringArray(R.array.domain_value)

        ArrayAdapter.createFromResource(
            this, R.array.domain_value, android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            emailDomainSpinner.adapter = adapter
        }
        emailDomainSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                when (position) {
                    1 -> {
                        emailDomain.visibility = View.GONE
                        emailDomainValue = domainDetails[1]
                    }

                    2 -> {
                        emailDomain.visibility = View.GONE
                        emailDomainValue = domainDetails[2]
                    }

                    3 -> {
                        emailDomain.visibility = View.GONE
                        emailDomainValue = domainDetails[3]
                    }

                    4 -> {
                        emailDomain.visibility = View.GONE
                        emailDomainValue = domainDetails[4]
                    }

                    5 -> {
                        emailDomain.visibility = View.GONE
                        emailDomainValue = domainDetails[5]
                    }

                    6 -> {
                        emailDomain.visibility = View.GONE
                        emailDomainValue = domainDetails[6]
                    }

                    7 -> {
                        emailDomain.visibility = View.VISIBLE
                    }
                }

                if (emailDomainValue.isNotEmpty()) {
                    errMsgEmail.visibility = View.GONE
                }
                if (emailIdValue.isNotEmpty() && emailDomainValue.isNotEmpty()) {
                    errMsgEmail.visibility = View.GONE
                    elementValidity[5] = true
                }
                Log.d(TAG, emailIdValue + "@" + emailDomainValue)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun idValidity() {
        var idPattern = Regex("^[a-zA-Z0-9]+$")

        idValue.focusOutEmpty(errMsgId, Strings.errorMsg[0][0])

        idValue.doAfterTextChanged {
            var inputId = idValue.text.toString()

            elementValidity[0] = true

            if (idPattern.containsMatchIn(inputId)) {
                errMsgId.visibility = View.GONE
            }

            if (inputId.length >= 6) {
                errMsgId.visibility = View.GONE
            }

            if (!idPattern.containsMatchIn(inputId)) {
                errMsgId.errMsg(Strings.errorMsg[0][3])
                elementValidity[0] = false
            }

            if (inputId.length < 6) {
                errMsgId.errMsg(Strings.errorMsg[0][1])
                elementValidity[0] = false
            }
        }
    }

    private fun passwordValidity() {
        var passwordPattern = Regex(
            "(?=.*(?!\\d)[!@#$%^&*()-_=+[ ]{}|;:'\",<.>/?])(?=.*\\d)(?=.*[a-zA-Z])"
        )

        password.focusOutEmpty(errMsgPassword, Strings.errorMsg[1][0])

        password.doAfterTextChanged {
            var pwInput = password.text.toString()

            elementValidity[1] = true

            if (passwordPattern.containsMatchIn(pwInput)) {
                errMsgPassword.visibility = View.GONE
            }

            if (pwInput.length >= 8) {
                errMsgPassword.visibility = View.GONE
            }

            if (pwInput.length < 8) {
                errMsgPassword.errMsg(Strings.errorMsg[1][1])
                elementValidity[1] = false
            }

            if (!passwordPattern.containsMatchIn(pwInput)) {
                errMsgPassword.errMsg(Strings.errorMsg[1][2])
                elementValidity[1] = false
            }
        }
    }

    private fun passwordCheck() {

        checkPassword.focusOutEmpty(errMsgCheckPassword, Strings.errorMsg[2][0])

        checkPassword.doAfterTextChanged {
            elementValidity[2] = true

            if (password.text.toString() == checkPassword.text.toString()) {
                errMsgCheckPassword.visibility = View.GONE
            }

            if (password.text.toString() != checkPassword.text.toString()) {
                errMsgCheckPassword.errMsg(Strings.errorMsg[2][0])
                elementValidity[2] = false
            }

            if (checkPassword.text.isEmpty()) {
                errMsgCheckPassword.errMsg(Strings.errorMsg[2][1])
                elementValidity[2] = false
            }
        }
    }

    private fun nameValidity() {

        name.focusOutEmpty(errMsgName, Strings.errorMsg[3][0])

        name.doAfterTextChanged {
            elementValidity[3] = false

            if (!name.text.isEmpty()) {
                errMsgName.visibility = View.GONE
                elementValidity[3] = true
            }
        }
    }

    private fun birthValidity() {
        var birthPattern = Regex(
            "^(19[0-9]{2}|20[0-2][0-4])(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\$"
        )

        birth.focusOutEmpty(errMsgBirth, Strings.errorMsg[4][0])

        birth.doAfterTextChanged {
            elementValidity[4] = true

            var birthInput = birth.text.toString()

            if (birthPattern.containsMatchIn(birthInput)) {
                errMsgBirth.visibility = View.GONE
            }

            if (!birthPattern.containsMatchIn(birthInput)) {
                errMsgBirth.errMsg(Strings.errorMsg[4][1])
                elementValidity[4] = false
            }
        }
    }

    private fun emailValidity() {
        val emailIdPattern = Regex("^[a-zA-Z0-9]+$")

        emailId.focusOutEmpty(errMsgEmail, Strings.errorMsg[5][0])

        emailId.doAfterTextChanged {
            elementValidity[5] = false

            var emailIdInput = emailId.text.toString()

            if (emailIdPattern.containsMatchIn(emailIdInput)) {
                errMsgEmail.visibility = View.GONE
            }

            if (!emailIdPattern.containsMatchIn(emailIdInput)) {
                errMsgEmail.errMsg(Strings.errorMsg[5][1])
                elementValidity[5] = false
            }
            emailIdValue = emailId.text.toString()
        }

        val emailDomainPattern = Regex("^[a-z,0-9,A-Z]+.[a-z]{2,3}$")

        emailDomain.focusOutEmpty(errMsgEmail, Strings.errorMsg[6][0])

        emailDomain.doAfterTextChanged {
            var emailDomainInput = emailDomain.text.toString()

            if (emailDomainPattern.containsMatchIn(emailDomainInput)) {
                errMsgEmail.visibility = View.GONE
            }

            if (!emailDomainPattern.containsMatchIn(emailDomainInput)) {
                errMsgEmail.errMsg(Strings.errorMsg[6][1])
                elementValidity[5] = false
                emailDomainValue = ""
            }
            emailDomainValue = emailDomainInput
            Log.i(TAG, "${emailDomainValue}")

            if (emailIdValue.isNotEmpty() && emailDomainValue.isNotEmpty()) {
                errMsgEmail.visibility = View.GONE
                elementValidity[5] = true
            }
        }


    }

    private fun checkElementValidity(): Boolean {
        if (idValue.text.isEmpty()) {
            errMsgId.errMsg(Strings.errorMsg[0][0])
            idValue.focusChange()
            return true
        }

        if (!elementValidity[0]) {
            idValue.focusChange()
            return true
        }

        if (Users.userList.any { it.id == idValue.text.toString() }) {
            errMsgId.errMsg(Strings.errorMsg[0][2])
            idValue.focusChange()
            return true
        }

        if (password.text.isEmpty()) {
            errMsgPassword.errMsg(Strings.errorMsg[1][0])
            password.focusChange()
            return true
        }

        if (!elementValidity[1]) {
            password.focusChange()
            return true
        }

        if (checkPassword.text.isEmpty()) {
            errMsgCheckPassword.errMsg(Strings.errorMsg[2][0])
            checkPassword.focusChange()
            return true
        }

        if (password.text.toString() != checkPassword.text.toString()) {
            errMsgCheckPassword.errMsg(Strings.errorMsg[2][1])
            checkPassword.focusChange()
            return true
        }

        if (name.text.isEmpty()) {
            errMsgName.errMsg(Strings.errorMsg[3][0])
            name.focusChange()
            return true
        }

        if (birth.text.isEmpty()) {
            errMsgBirth.errMsg(Strings.errorMsg[4][0])
            birth.focusChange()
            return true
        }

        if (!elementValidity[4]) {
            birth.focusChange()
            return true
        }

        if (emailId.text.isEmpty()) {
            errMsgEmail.errMsg(Strings.errorMsg[5][0])
            emailId.focusChange()
            return true
        }

        if (selectMbtiValue.isEmpty()) {
            errMsgMbti.errMsg(Strings.errorMsg[7][0])
            mbtiSpinner.requestFocus()
            return true
        }
        return false
    }

    private fun TextView.errMsg(msg: String) {
        this.setText(msg)
        this.visibility = View.VISIBLE
        this.setTextColor(resources.getColor(R.color.warning))
    }

    private fun EditText.focusChange() {
        val imm = getSystemService(InputMethodManager::class.java)

        this.requestFocus()
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun EditText.focusOutEmpty(errMsg: TextView, string: String) {
        this.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (this.text.isEmpty()) {
                    errMsg.errMsg(string)
                }
            }
        }
    }


}
