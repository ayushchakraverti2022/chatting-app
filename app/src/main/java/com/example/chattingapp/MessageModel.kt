package com.example.chattingapp

class MessageModel {

    lateinit var pairlist:Pair<String, String>;
                   //          <Uid  ,    message>
    constructor(pairlist: Pair<String, String>) {
        this.pairlist = pairlist
    }
}