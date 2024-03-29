package com.android.episodesguideapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.episodesguideapp.R
import com.android.episodesguideapp.featureFlags.Feature
import com.android.episodesguideapp.featureFlags.FeatureService
import com.android.episodesguideapp.ui.MainActivity
import com.android.episodesguideapp.ui.MainActivityFlutter
import com.google.firebase.auth.FirebaseAuth
import com.launchdarkly.sdk.*
import com.launchdarkly.sdk.android.*


class LoginActivity() : AppCompatActivity() {

    private lateinit var flagService: FeatureService

    private val TAG = "LoginActivity"

    private var email: String? = null
    private var password: String? = null

    private var createTextView: TextView? = null
    private var forgotTextView: TextView? = null
    private var loginButton: Button? = null
    private var passwordEditText: EditText? = null
    private var emailEditText: EditText? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        flagService = FeatureService(application)

        logFeature(Feature.NewFeatureOne.title, flagService.isFeatureEnabled(Feature.NewFeatureOne))

        if (supportActionBar != null)
            supportActionBar?.hide()

        setUpFields()
    }

    private fun setUpFields() {
        createTextView = findViewById(R.id.text_view_create_login)
        forgotTextView = findViewById(R.id.text_view_forgot_login)
        loginButton = findViewById(R.id.button_confirm_login)
        passwordEditText = findViewById(R.id.edit_text_password_login)
        emailEditText = findViewById(R.id.edit_text_email_login)

        mAuth = FirebaseAuth.getInstance()

        forgotTextView!!.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity, ForgotPasswordActivity::class.java
                )
            )
        }

        createTextView!!.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity, CreateUserActivity::class.java
                )
            )
        }

        loginButton!!.setOnClickListener { loginUser() }
    }

    private fun loginUser() {
        email = emailEditText?.text.toString()
        password = passwordEditText?.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            Log.d(TAG, "logging user")

            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        updateUI(flagService.isFeatureEnabled(Feature.NewFeatureOne))
                    } else {
                        Log.e(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this@LoginActivity,
                            "authentication failed",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(featureToggleIsEnable: Boolean) {
        if (featureToggleIsEnable) {
            val intent = Intent(this@LoginActivity, MainActivityFlutter::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        } else {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }

    private fun logFeature(featureName: String, enabled: Boolean) {
        if (enabled) {
            Log.d(TAG, "Feature \"$featureName\" Enabled")
        } else {
            Log.d(TAG, "Feature \"$featureName\" Disabled")
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}