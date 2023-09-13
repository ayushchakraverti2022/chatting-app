package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chattingapp.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    //ALL variable declaration
    lateinit var mainBinding: ActivityMainBinding;
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance();
    var rdatabaseref:DatabaseReference = FirebaseDatabase.getInstance().getReference();

    var userlist:MutableList<UserModel> = mutableListOf();
    lateinit var useradapter:UserRecyclerAdapter;
    // end
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        //Firebase initialise
        FirebaseApp.initializeApp(applicationContext);

        supportActionBar?.setTitle("Friends")

        // mainActivity UserList Recycler View
        useradapter  = UserRecyclerAdapter(applicationContext,userlist)
        mainBinding.userRecyclerview.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false);
        mainBinding.userRecyclerview.adapter = useradapter;

        // Getting Users From database
        rdatabaseref.child("users").addValueEventListener(object:ValueEventListener{
         override fun onDataChange(snapshot: DataSnapshot) {
             userlist.clear()
             for(snapshot1 in snapshot.children){
                 if(!mAuth.uid.equals(snapshot1.child("uid").getValue().toString())) {

                     // adding user to the list to use in recycler view
                     userlist.add(
                         UserModel(
                             snapshot1.child("email").getValue().toString(),
                             snapshot1.child("name").getValue().toString(),
                             snapshot1.child("uid").getValue().toString()
                         )
                     );
                 }
                //  Toast.makeText(applicationContext,snapshot1.child("name").getValue().toString(),Toast.LENGTH_SHORT).show()
             }
             useradapter.notifyDataSetChanged();
         }
         override fun onCancelled(error: DatabaseError) {  }

       })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.lgout){
            // Logout menu
            mAuth.signOut();
            var intent: Intent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent);
            finish()

        }else if(item.itemId==R.id.chngename){
            // Change name menu option
            var intent: Intent =
                Intent(applicationContext, ChangeNameActivity::class.java)
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item)
    }

}