package com.example.contactwithfirebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactwithfirebase.Adapter.ContactAdapter
import com.example.contactwithfirebase.Models.Contact
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    var floatAction:View? = null
    var db: FirebaseFirestore? = null
    var contacts:MutableList<Contact>? = null
    var contact:Map<String, Any>? = null
    var re_contact: RecyclerView? = null
    var progressDialog:ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Loading....")
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
        floatAction = findViewById(R.id.btn_float)
        re_contact = findViewById(R.id.container)
        db = FirebaseFirestore.getInstance()
        contacts = mutableListOf()
        floatAction!!.setOnClickListener {
            val intent = Intent(this,ContactActivity::class.java)
            startActivity(intent)
        }

        db!!.collection("contacts")
            .get()
            .addOnCompleteListener {result->
                if(result.isSuccessful){
                    for( document in result.result!!){
                        contact = document.data
                        contacts!!.add(Contact(contact!!["name"].toString(),contact!!["phone"].toString(),contact!!["address"].toString()))
                    }
                    if(progressDialog!!.isShowing){
                        progressDialog!!.dismiss()
                    }
                    re_contact!!.layoutManager = LinearLayoutManager(this)
                    val contactAdapter = ContactAdapter(this,contacts!!)
                    re_contact!!.adapter = contactAdapter
                }

            }

    }
}