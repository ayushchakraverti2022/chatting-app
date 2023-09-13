package com.example.chattingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ChatRecyclerAdapter(var context:Context, var msagelist:MutableList<MessageModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_Send= 1
    private val VIEW_TYPE_Receive = 2

    class sentmessageViewholder(itemView: View):RecyclerView.ViewHolder(itemView){
            var msent =itemView.findViewById<TextView>(R.id.sendmessage)
    }

    class receivedmessageViewholder(itemView: View):RecyclerView.ViewHolder(itemView){
        var mreceive =itemView.findViewById<TextView>(R.id.receivemessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
             if(viewType==1){
                 var view = LayoutInflater.from(context).inflate(R.layout.sendchat,parent,false);
                 return sentmessageViewholder(view);
             }else{
                 var view = LayoutInflater.from(context).inflate(R.layout.receivechat,parent,false);
                 return receivedmessageViewholder(view);
             }
    }

    override fun getItemViewType(position: Int): Int {
        var message = msagelist[position]
        return if (FirebaseAuth.getInstance().uid.equals(message.pairlist.first)) {
            VIEW_TYPE_Send;
        } else {
            VIEW_TYPE_Receive;
        }
    }

    override fun getItemCount(): Int {
       return  msagelist.size;
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var pair: MessageModel = msagelist[position];

        if(holder.javaClass==sentmessageViewholder::class.java){
            var viewHolder =  holder as sentmessageViewholder
            holder.msent.text = pair.pairlist.second.toString();

        }else{
            var viewHolder =  holder as receivedmessageViewholder
            holder.mreceive.text =  pair.pairlist.second.toString();
        }
    }
}