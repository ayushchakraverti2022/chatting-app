package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chattingapp.databinding.ActivityChangeNameBinding
import com.example.chattingapp.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class ChangeNameActivity : AppCompatActivity() {
    lateinit var changeNameBinding: ActivityChangeNameBinding;
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance();
    var rdatabaseref: DatabaseReference = FirebaseDatabase.getInstance().getReference();
    lateinit var username:String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeNameBinding =  ActivityChangeNameBinding.inflate(layoutInflater)
        setContentView(changeNameBinding.root)
        FirebaseApp.initializeApp(applicationContext);

        supportActionBar?.setTitle("Your Profile")

         // Getting and setting username to the edittext
        rdatabaseref.child("users").child(mAuth.uid.toString()).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                username = snapshot.child("name").getValue().toString();
                changeNameBinding.stname.setText(username);
                  //for(snapshot1 in snapshot.children){
                    //  Toast.makeText(applicationContext,snapshot.child("name").getValue().toString() , Toast.LENGTH_SHORT).show();
                 // }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })


       // Saving Username to the database
        changeNameBinding.namesave.setOnClickListener {
             Toast.makeText(applicationContext, "Wait..", Toast.LENGTH_SHORT)
                 .show();
             if(!changeNameBinding.stname.text?.isBlank()!!){
                 rdatabaseref.child("users").child(mAuth.uid.toString()).child("name").setValue(changeNameBinding.stname.text.toString())
                     .addOnSuccessListener {
                         Toast.makeText(applicationContext, "Done", Toast.LENGTH_SHORT)
                             .show();
                     }
             }
         }

    }
}