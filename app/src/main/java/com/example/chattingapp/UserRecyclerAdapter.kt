package com.example.chattingapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class UserRecyclerAdapter(var context:Context,var userlist:MutableList<UserModel>): RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
       var view:View  = LayoutInflater.from(context).inflate(R.layout.user_tab_layout,parent,false);
       return UserViewHolder(view);
    }

    override fun getItemCount(): Int {
         return userlist.size;
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
         var useritem = userlist.get(position);
         holder.name.text= useritem.name;
        //
        // Binding all the data
         holder.itemView.setOnClickListener {
             var intent: Intent = Intent(context,ChatActivity::class.java)
             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             intent.putExtra("name",useritem.name);
             intent.putExtra("uid",useritem.uid);
             context.startActivity(intent);
         }

    }
    class UserViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
           var name = itemView.findViewById<TextView>(R.id.username);
    }
}