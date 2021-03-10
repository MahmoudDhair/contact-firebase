package com.example.contactwithfirebase

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class ContactActivity : AppCompatActivity() {
    var ed_name:EditText? = null
    var ed_phune:EditText? = null
    var ed_address:EditText? = null
    var btn_save:Button? = null
    var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        ed_name = findViewById(R.id.ed_name)
        ed_phune = findViewById(R.id.ed_number)
        ed_address = findViewById(R.id.ed_address)
        btn_save = findViewById(R.id.btn_save)
        db = FirebaseFirestore.getInstance()
        btn_save!!.setOnClickListener { viwe->
           if(ed_name!!.text.isNotEmpty()){
               if(ed_phune!!.text.isNotEmpty()){
                   if(ed_address!!.text.isNotEmpty()){

                       var name = ed_name!!.text.toString()
                       var phone = ed_phune!!.text.toString()
                       var address = ed_address!!.text.toString()

                       val contact:HashMap<String, Any> = HashMap<String,Any>()
                       contact.put("name",name)
                       contact.put("phone",phone)
                       contact.put("address",address)
                       db!!.collection("contacts").add(contact)
                           .addOnSuccessListener {
                               Snackbar.make(viwe, "The Contact added successfully", Snackbar.LENGTH_LONG)
                                   .show()
                               ed_name!!.setText("")
                               ed_phune!!.setText("")
                               ed_address!!.setText("")
                           }
                           .addOnFailureListener {
                               Snackbar.make(viwe, "Happened Error", Snackbar.LENGTH_LONG)
                                   .show()
                           }
                   }else{
                       Snackbar.make(viwe, "The Address Must Not Be Empty", Snackbar.LENGTH_LONG)
                           .show()
                   }
               }else{
                   Snackbar.make(viwe, "The Phone Number Must Not Be Empty", Snackbar.LENGTH_LONG)
                       .show()
               }
           }else{
               Snackbar.make(viwe, "The Name Must Not Be Empty", Snackbar.LENGTH_LONG)
                   .show()
           }
        }
    }
}