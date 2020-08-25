package com.android.episodesguideapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.episodesguideapp.R
import com.android.episodesguideapp.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_user.*

class CreateUserActivity : AppCompatActivity() {

    //UI elements
    private var nameEditText: EditText? = null
    private var lastNameEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var createUserButton: Button? = null

    // Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "CreateUserActivity"

    //global variables
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var password: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        initialise()
    }

    private fun initialise() {
        nameEditText = findViewById<EditText>(R.id.edit_text_name_create)
        lastNameEditText = findViewById<EditText>(R.id.edit_text_last_name_create)
        emailEditText = findViewById<EditText>(R.id.edit_text_email_create)
        passwordEditText = findViewById<EditText>(R.id.edit_text_password_create)
        createUserButton = findViewById<Button>(R.id.button_confirm_create)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        button_confirm_create!!.setOnClickListener {
            createNewAccount()
        }
    }

    private fun createNewAccount() {
        firstName = nameEditText?.text.toString()
        lastName = lastNameEditText?.text.toString()
        email = emailEditText?.text.toString()
        password = passwordEditText?.text.toString()

        if (!TextUtils.isEmpty(firstName) &&
            !TextUtils.isEmpty(lastName) &&
            !TextUtils.isEmpty(email) &&
            !TextUtils.isEmpty(password)
        ) {
            mAuth = FirebaseAuth.getInstance()

            mAuth!!
                .createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val userId = mAuth!!.currentUser!!.uid

                        verifyEmail()

                        val currentUserDb = mDatabaseReference!!.child(userId)
                        currentUserDb.child("firstName").setValue(firstName)
                        currentUserDb.child("lastName").setValue(lastName)
                        updateUserInfoAndUi()
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            this@CreateUserActivity,
                            "authentication failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
        } else {
            Toast.makeText(this, "enter all your information", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@CreateUserActivity,
                        "verification email sent to " + mUser.email,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(
                        this@CreateUserActivity,
                        "failed to send email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun updateUserInfoAndUi(){
        val intent = Intent(this@CreateUserActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}