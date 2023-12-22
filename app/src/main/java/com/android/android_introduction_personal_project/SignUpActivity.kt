package com.android.android_introduction_personal_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import java.time.LocalDate

class SignUpActivity : AppCompatActivity() {
    val etId by lazy { findViewById<EditText>(R.id.et_sign_up_input_id) }
    val tvErrMsgId by lazy { findViewById<TextView>(R.id.tv_sign_up_err_id_input) }
    val etPassword by lazy { findViewById<EditText>(R.id.et_sign_up_input_password) }
    val tvErrMsgPassword by lazy { findViewById<TextView>(R.id.tv_sign_up_err_password_input) }
    val etCheckPassword by lazy { findViewById<EditText>(R.id.et_sign_up_input_check_password) }
    val tvErrMsgCheckPassword by lazy { findViewById<TextView>(R.id.tv_sign_up_err_check_password_input) }
    val etName by lazy { findViewById<EditText>(R.id.et_sign_up_input_name) }
    val tvErrMsgName by lazy { findViewById<TextView>(R.id.tv_sign_up_err_name_input) }
    val etBirth by lazy { findViewById<EditText>(R.id.et_sign_up_input_birth) }
    val tvErrMsgBirth by lazy { findViewById<TextView>(R.id.tv_sign_up_err_birth_input) }
    val etEmailId by lazy { findViewById<EditText>(R.id.et_sign_up_input_email_id) }
    val etEmailDomain by lazy { findViewById<EditText>(R.id.et_sign_up_input_email_domain) }
    val spinnerEmailDomain by lazy { findViewById<Spinner>(R.id.spinner_sign_up_email_domain) }
    val tvErrMsgEmail by lazy { findViewById<TextView>(R.id.tv_sign_up_err_email) }
    val spinnerMbti by lazy { findViewById<Spinner>(R.id.spinner_signUp_mbti) }
    val tvErrMsgMbti by lazy { findViewById<TextView>(R.id.tv_sign_up_err_mbti_input) }
    val btnSignUpSummit by lazy { findViewById<Button>(R.id.btn_sign_up_summit) }
    var selectMbtiValue: String = ""
    var emailIdValue: String = ""
    var emailDomainValue: String = ""
    var validityPassOrNot = arrayListOf(false, false, false, false, false, false, false)

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_sign_up)
            inputValue()
            signUpSummitInit()
        }

    fun inputValue() {
        mbtiSpinnerInit()
        emailDomainSpinnerSelectInit()
        validityRuleInit()
    }

    fun signUpSummitInit() {
        btnSignUpSummit.setOnClickListener {
            if (!checkInputValidity()){
                return@setOnClickListener
            }

            Users.addUser(
                User(
                    etName.text.toString(),
                    etId.text.toString(),
                    this.etPassword.text.toString(),
                    LocalDate.now().year - etBirth.text.substring(0..3).toInt(),
                    "${emailIdValue}@${emailDomainValue}",
                    selectMbtiValue
                )
            )
            intent.putExtra("id", etId.text.toString())
            setResult(RESULT_OK, intent)
            finish()
            showToast(this, "회원가입이 완료되었습니다.")
        }
    }

    fun validityRuleInit() {
        idValidity()
        passwordValidity()
        passwordCheck()
        nameValidity()
        birthValidity()
        emailValidity()
    }

    private fun mbtiSpinnerInit() {
        val mbtiDetails = resources.getStringArray(R.array.mbti_array)

        ArrayAdapter.createFromResource(
            this, R.array.mbti_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMbti.adapter = adapter
        }
        spinnerMbti.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                    validityPassOrNot[6] = true
                    tvErrMsgMbti.visibility = View.GONE
                }
                validityCheck()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun emailDomainSpinnerSelectInit() {
        val domainDetails = resources.getStringArray(R.array.domain_value)

        ArrayAdapter.createFromResource(
            this, R.array.domain_value, android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            spinnerEmailDomain.adapter = adapter
        }
        spinnerEmailDomain.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                when (position) {
                    1 -> emailDomainSelectOnMenu(domainDetails, 1)
                    2 -> emailDomainSelectOnMenu(domainDetails, 2)
                    3 -> emailDomainSelectOnMenu(domainDetails, 3)
                    4 -> emailDomainSelectOnMenu(domainDetails, 4)
                    5 -> emailDomainSelectOnMenu(domainDetails, 5)
                    6 -> emailDomainSelectOnMenu(domainDetails, 6)
                    7 -> etEmailDomain.visibility = View.VISIBLE
                }

                if (emailDomainValue.isNotEmpty()) {
                    tvErrMsgEmail.visibility = View.GONE
                }
                if (emailIdValue.isNotEmpty() && emailDomainValue.isNotEmpty()) {
                    tvErrMsgEmail.visibility = View.GONE
                    validityPassOrNot[5] = true
                }
                validityCheck()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun emailDomainSelectOnMenu(arr: Array<String>, i: Int) {
        etEmailDomain.visibility = View.GONE
        emailDomainValue = arr[i]
    }

    private fun idValidity() {
        var idPattern = Regex("^[a-zA-Z0-9]+$")

        etId.focusOutEmpty(tvErrMsgId, Strings.errorMsg[0][0])

        etId.doAfterTextChanged {
            var inputId = etId.text.toString()

            validityPassOrNot[0] = true

            if (idPattern.containsMatchIn(inputId)) {
                tvErrMsgId.visibility = View.GONE
            }

            if (inputId.length >= 6) {
                tvErrMsgId.visibility = View.GONE
            }

            if (!idPattern.containsMatchIn(inputId)) {
                tvErrMsgId.errMsg(Strings.errorMsg[0][3])
                validityPassOrNot[0] = false
            }

            if (inputId.length < 6) {
                tvErrMsgId.errMsg(Strings.errorMsg[0][1])
                validityPassOrNot[0] = false
            }
            validityCheck()
        }
    }

    private fun passwordValidity() {
        var passwordPattern = Regex(
            "(?=.*(?!\\d)[!@#$%^&*()-_=+[ ]{}|;:'\",<.>/?])(?=.*\\d)(?=.*[a-zA-Z])"
        )

        etPassword.focusOutEmpty(tvErrMsgPassword, Strings.errorMsg[1][0])

        etPassword.doAfterTextChanged {
            var pwInput = etPassword.text.toString()

            validityPassOrNot[1] = true

            if (passwordPattern.containsMatchIn(pwInput)) {
                tvErrMsgPassword.visibility = View.GONE
            }

            if (pwInput.length >= 8) {
                tvErrMsgPassword.visibility = View.GONE
            }

            if (pwInput.length < 8) {
                tvErrMsgPassword.errMsg(Strings.errorMsg[1][1])
                validityPassOrNot[1] = false
            }

            if (!passwordPattern.containsMatchIn(pwInput)) {
                tvErrMsgPassword.errMsg(Strings.errorMsg[1][2])
                validityPassOrNot[1] = false
            }
            validityCheck()
        }
    }

    private fun passwordCheck() {

        etCheckPassword.focusOutEmpty(tvErrMsgCheckPassword, Strings.errorMsg[2][0])

        etCheckPassword.doAfterTextChanged {
            validityPassOrNot[2] = true

            if (etPassword.text.toString() == etCheckPassword.text.toString()) {
                tvErrMsgCheckPassword.visibility = View.GONE
            }

            if (etPassword.text.toString() != etCheckPassword.text.toString()) {
                tvErrMsgCheckPassword.errMsg(Strings.errorMsg[2][0])
                validityPassOrNot[2] = false
            }

            if (etCheckPassword.text.isEmpty()) {
                tvErrMsgCheckPassword.errMsg(Strings.errorMsg[2][1])
                validityPassOrNot[2] = false
            }
            validityCheck()
        }
    }

    private fun nameValidity() {

        etName.focusOutEmpty(tvErrMsgName, Strings.errorMsg[3][0])

        etName.doAfterTextChanged {
            validityPassOrNot[3] = false

            if (!etName.text.isEmpty()) {
                tvErrMsgName.visibility = View.GONE
                validityPassOrNot[3] = true
            }
            validityCheck()
        }
    }

    private fun birthValidity() {
        var birthPattern = Regex(
            "^(19[0-9]{2}|20[0-2][0-4])(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\$"
        )

        etBirth.focusOutEmpty(tvErrMsgBirth, Strings.errorMsg[4][0])

        etBirth.doAfterTextChanged {
            validityPassOrNot[4] = true

            var birthInput = etBirth.text.toString()

            if (birthPattern.containsMatchIn(birthInput)) {
                tvErrMsgBirth.visibility = View.GONE
            }

            if (!birthPattern.containsMatchIn(birthInput)) {
                tvErrMsgBirth.errMsg(Strings.errorMsg[4][1])
                validityPassOrNot[4] = false
            }
            validityCheck()
        }
    }

    private fun emailValidity() {
        val emailIdPattern = Regex("^[a-zA-Z0-9]+$")

        etEmailId.focusOutEmpty(tvErrMsgEmail, Strings.errorMsg[5][0])

        etEmailId.doAfterTextChanged {
            validityPassOrNot[5] = false

            var emailIdInput = etEmailId.text.toString()

            if (emailIdPattern.containsMatchIn(emailIdInput)) {
                tvErrMsgEmail.visibility = View.GONE
            }

            if (!emailIdPattern.containsMatchIn(emailIdInput)) {
                tvErrMsgEmail.errMsg(Strings.errorMsg[5][1])
                validityPassOrNot[5] = false
            }
            emailIdValue = etEmailId.text.toString()
            validityCheck()
        }

        val emailDomainPattern = Regex("^[a-z,0-9,A-Z]+.[a-z]{2,3}$")

        etEmailDomain.focusOutEmpty(tvErrMsgEmail, Strings.errorMsg[6][0])

        etEmailDomain.doAfterTextChanged {
            var emailDomainInput = etEmailDomain.text.toString()

            if (emailDomainPattern.containsMatchIn(emailDomainInput)) {
                tvErrMsgEmail.visibility = View.GONE
            }

            if (!emailDomainPattern.containsMatchIn(emailDomainInput)) {
                tvErrMsgEmail.errMsg(Strings.errorMsg[6][1])
                validityPassOrNot[5] = false
                emailDomainValue = ""
            }
            emailDomainValue = emailDomainInput

            if (emailIdValue.isNotEmpty() && emailDomainValue.isNotEmpty()) {
                tvErrMsgEmail.visibility = View.GONE
                validityPassOrNot[5] = true
            }
            validityCheck()
        }


    }

    private fun checkInputValidity(): Boolean {
        if (etId.text.isEmpty()) {
            tvErrMsgId.errMsg(Strings.errorMsg[0][0])
            etId.focusChange()
            return false
        }

        if (!validityPassOrNot[0]) {
            etId.focusChange()
            return false
        }

        if (Users.userList.any { it.id == etId.text.toString() }) {
            tvErrMsgId.errMsg(Strings.errorMsg[0][2])
            etId.focusChange()
            return false
        }

        if (etPassword.text.isEmpty()) {
            tvErrMsgPassword.errMsg(Strings.errorMsg[1][0])
            etPassword.focusChange()
            return false
        }

        if (!validityPassOrNot[1]) {
            etPassword.focusChange()
            return false
        }

        if (etCheckPassword.text.isEmpty()) {
            tvErrMsgCheckPassword.errMsg(Strings.errorMsg[2][0])
            etCheckPassword.focusChange()
            return false
        }

        if (etPassword.text.toString() != etCheckPassword.text.toString()) {
            tvErrMsgCheckPassword.errMsg(Strings.errorMsg[2][1])
            etCheckPassword.focusChange()
            return false
        }

        if (etName.text.isEmpty()) {
            tvErrMsgName.errMsg(Strings.errorMsg[3][0])
            etName.focusChange()
            return false
        }

        if (etBirth.text.isEmpty()) {
            tvErrMsgBirth.errMsg(Strings.errorMsg[4][0])
            etBirth.focusChange()
            return false
        }

        if (!validityPassOrNot[4]) {
            etBirth.focusChange()
            return false
        }

        if (etEmailId.text.isEmpty()) {
            tvErrMsgEmail.errMsg(Strings.errorMsg[5][0])
            etEmailId.focusChange()
            return false
        }

        if (selectMbtiValue.isEmpty()) {
            tvErrMsgMbti.errMsg(Strings.errorMsg[7][0])
            spinnerMbti.requestFocus()
            return false
        }
        return true
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

    private fun validityCheck() {
        if(validityPassOrNot.all { it == true }) {
            btnSignUpSummit.background = getDrawable(R.drawable.active_rectangle_button)
        } else {
            btnSignUpSummit.background = getDrawable(R.drawable.inactive_rectangle_button)
        }
    }

}
