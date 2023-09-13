package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chattingapp.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var loginBinding:ActivityLoginBinding;
    var mAuth:FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setTitle("Welcome")

        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        FirebaseApp.initializeApp(applicationContext);

        // Checking if user is already signed in // If yes then go to main Activity
        if(mAuth.uid!=null){
            var intent:Intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

         // If Not then login

        // Login else GO to SignUp ACtivity to create user and Registor it
        loginBinding.login.setOnClickListener {
            if(!loginBinding.email.text?.isBlank()!! &&  !loginBinding.password.text?.isBlank()!!){
                Toast.makeText(applicationContext, "Wait...", Toast.LENGTH_SHORT).show();
                mAuth.signInWithEmailAndPassword(
                    loginBinding.email.text.toString(),
                    loginBinding.password.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        var intent: Intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {

                        // if not and account then sign up
                        Toast.makeText(
                            applicationContext,
                            "Sign in if haven't yet.",
                            Toast.LENGTH_SHORT
                        ).show()
                        var intent: Intent = Intent(applicationContext, SignActivity::class.java)
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }

        // manually go the registor Activity // Signin Activity
        loginBinding.textviewregistor.setOnClickListener {
            var intent:Intent = Intent(applicationContext,SignActivity::class.java)
            startActivity(intent);
            finish();
        }





    }
}