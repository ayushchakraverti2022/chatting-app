package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chattingapp.databinding.ActivitySignBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignActivity : AppCompatActivity() {

    // All variables
    lateinit var signBinding:ActivitySignBinding;
    var mAuth:FirebaseAuth = FirebaseAuth.getInstance();
    var rdatabase:DatabaseReference= FirebaseDatabase.getInstance().getReference();

    // data class for storing User data
    data class User(var email:String ,var name:String,var uid:String );

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signBinding =  ActivitySignBinding.inflate(layoutInflater)
        setContentView(signBinding.root)
        FirebaseApp.initializeApp(applicationContext);

        supportActionBar?.setTitle("Welcome")

        // signing code
        signBinding.sign.setOnClickListener {
           if(!signBinding.email.text?.isBlank()!! &&  !signBinding.password.text?.isBlank()!! ) {
                Toast.makeText(applicationContext, "Wait...", Toast.LENGTH_SHORT).show();

                mAuth.createUserWithEmailAndPassword(
                    signBinding.email.text.toString(),
                    signBinding.password.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        var user = User(
                            signBinding.email.text.toString(),
                            signBinding.name.text.toString(),
                            mAuth.uid.toString()
                        );
                        // creating user in realtime database
                        rdatabase.child("users").child(mAuth.uid.toString()).setValue(user)
                            .addOnCompleteListener {
                                Toast.makeText(applicationContext, "Signed In", Toast.LENGTH_SHORT)
                                    .show();
                                var intent: Intent =
                                    Intent(applicationContext, LoginActivity::class.java)
                                startActivity(intent);
                                finish();
                            }
                    }
                }
            }
        }





    }
}