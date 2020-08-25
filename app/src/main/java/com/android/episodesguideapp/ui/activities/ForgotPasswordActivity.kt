package com.android.episodesguideapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.episodesguideapp.R
import com.android.episodesguideapp.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private val TAG = "ForgotActivity"

    private var emailEditText: EditText? = null
    private var buttonForgot: Button? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        initialise()
    }

    private fun initialise() {
        emailEditText = findViewById(R.id.edit_text_email_forgot)
        buttonForgot = findViewById(R.id.button_confirm_forgot)

        mAuth = FirebaseAuth.getInstance()

        buttonForgot!!.setOnClickListener { sendResetPasswordEmail() }
    }

    private fun sendResetPasswordEmail() {
        val email = emailEditText?.text.toString()

        if(!TextUtils.isEmpty(email)){
            mAuth!!
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val message = "email sent"
                        Log.d(TAG, message)
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        updateUI()
                    } else {
                        Log.w(TAG, task.exception!!.message.toString())
                        Toast.makeText(this, "no user found with this email", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "enter your email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}