package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chattingapp.databinding.ActivityChatBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    // All variables
    lateinit var chatBinding: ActivityChatBinding;
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance();
    var rdatabaseref: DatabaseReference = FirebaseDatabase.getInstance().getReference();
    lateinit var name:String;
    lateinit var uid:String;

    lateinit var chatadapter:ChatRecyclerAdapter;

    var chatlist:MutableList<MessageModel> = mutableListOf();
    // data class for message // From Database
    data class message(var uid:String ,var chat:String );


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatBinding =  ActivityChatBinding.inflate(layoutInflater)
        setContentView(chatBinding.root)
        FirebaseApp.initializeApp(applicationContext);

        // Getting Intent values from main Activity // Name and uID of the party
        name = intent.getStringExtra("name").toString();
        uid =  intent.getStringExtra("uid").toString();

        supportActionBar?.setTitle(name)

        // Adapter Setting
        chatadapter = ChatRecyclerAdapter(applicationContext,chatlist);
        chatBinding.recyclerViewChat.layoutManager  = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false);
        chatBinding.recyclerViewChat.adapter = chatadapter;

        // getting chats
        rdatabaseref.child("chats").child(mAuth.uid.toString()).child(uid).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                chatlist.clear()
                for(snapshot1 in snapshot.children){

                   var pair:Pair<String, String>  = snapshot1.child("uid").getValue().toString() to snapshot1.child("chat").getValue().toString()
                    //Toast.makeText(applicationContext,pair.toString(),Toast.LENGTH_LONG).show();
                   chatlist.add(MessageModel(pair));
                }
                chatadapter.notifyDataSetChanged();

            }

            override fun onCancelled(error: DatabaseError) {


            }

        })

         // send button code // saving chat
        chatBinding.buttonSend.setOnClickListener {
            if(!chatBinding.editTextchatTab.text.isBlank()){
                var uidchat =  message(mAuth.uid.toString(), chatBinding.editTextchatTab.text.toString());
                chatBinding.editTextchatTab.setText("");
                rdatabaseref.child("chats").child(mAuth.uid.toString()).child(uid).push().setValue(uidchat).addOnCompleteListener {
                    if(it.isSuccessful){
                        // Saving Chats to the Both UID nodes in  the database
                        rdatabaseref.child("chats").child(uid).child(mAuth.uid.toString()).push().setValue(uidchat).addOnSuccessListener {

                            chatadapter.notifyDataSetChanged();
                        }
                    }
                }
            }

        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chatmenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.clear){
            // Clear Menu option code // Deleting Database chats of the parties
            Toast.makeText(applicationContext,"Wait..",Toast.LENGTH_SHORT).show()
            rdatabaseref.child("chats").child(mAuth.uid.toString()).child(uid).removeValue().addOnSuccessListener {
                //rdatabaseref.child("chats").child(uid).child(mAuth.uid.toString()).removeValue();
            }
            chatadapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item)
    }
}