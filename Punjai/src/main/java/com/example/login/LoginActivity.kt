package com.example.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

class LoginActivity : AppCompatActivity() {
    var EmailId: EditText? = null
    var Password: EditText? = null
    var btnSignIn: Button? = null
    var tvSignUp: TextView? = null
    var mFirebaseAuth: FirebaseAuth? = null
    private var mAuthStateListener: AuthStateListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mFirebaseAuth = FirebaseAuth.getInstance()
        EmailId = findViewById(R.id.editText)
        Password = findViewById(R.id.editText2)
        btnSignIn = findViewById(R.id.button)
        tvSignUp = findViewById(R.id.textView)
        mAuthStateListener = AuthStateListener {
            val mFirebaseUser = mFirebaseAuth!!.currentUser
            if (mFirebaseUser != null) {
                Toast.makeText(this@LoginActivity, "You are logged in", Toast.LENGTH_SHORT).show()
                val i = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(i)
            } else {
                Toast.makeText(this@LoginActivity, "Please Login", Toast.LENGTH_SHORT).show()
            }
        }
        btnSignIn?.setOnClickListener(View.OnClickListener {
            val email = EmailId?.getText().toString()
            val pwd = Password?.getText().toString()
            if (email.isEmpty()) {
                EmailId?.setError("Please enter email ID")
                EmailId?.requestFocus()
            } else if (pwd.isEmpty()) {
                Password?.setError("Please enter your password")
                Password?.requestFocus()
            } else if (email.isEmpty() && pwd.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Fields Are Empty!", Toast.LENGTH_SHORT).show()
            } else if (!(email.isEmpty() && pwd.isEmpty())) {
                mFirebaseAuth!!.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(this@LoginActivity) { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "LogIn Error, Please LogIn Again", Toast.LENGTH_SHORT).show()
                    } else {
                        val intToHome = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intToHome)
                    }
                }
            } else {
                Toast.makeText(this@LoginActivity, "Error Occurred!", Toast.LENGTH_SHORT).show()
            }
        })
        tvSignUp?.setOnClickListener(View.OnClickListener {
            val intSignUp = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intSignUp)
        })
    }

    override fun onStart() {
        super.onStart()
        mFirebaseAuth!!.addAuthStateListener(mAuthStateListener!!)
    }
}