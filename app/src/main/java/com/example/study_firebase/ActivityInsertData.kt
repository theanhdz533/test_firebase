package com.example.study_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.study_firebase.databinding.ActivityInsertDataBinding
import com.example.study_firebase.model.PersonModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ActivityInsertData : AppCompatActivity() {
    lateinit var binding: ActivityInsertDataBinding
    // references
    lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_data)
        binding = ActivityInsertDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // create table
        dbRef = FirebaseDatabase.getInstance().getReference("person")

        // btn save
        binding.btnSaveData.setOnClickListener {
            saveData()
            binding.edtBorn.setText("")
            binding.edtName.setText("")
            binding.edtEmail.setText("")
        }

    }

    private fun saveData() {
        // get data
        val getName = binding.edtName.text.toString()
        val getBorn = binding.edtBorn.text.toString()
        val getEmail = binding.edtEmail.text.toString()


        // check data
        var count:Int = 0
        if (getName.isEmpty()){
            binding.edtName.error = "Please! Enter your name ^^"
            count++
        }
        if (getBorn.isEmpty()){
            binding.edtBorn.error = "Please! Enter your birthday!"
            count++
        }
        if (getEmail.isEmpty()){
            binding.edtEmail.error = "Please! Enter your email!"
            count++
        }

        // push data
        if (count==0){
            val id = dbRef.push().key!!
            val person = PersonModel(id,getName,getBorn,getEmail)
            // notification
            dbRef.child(id).setValue(person)
                .addOnCompleteListener {
                    Toast.makeText(this,"Data inserted!",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {err->
                    Toast.makeText(this,"Data inserted error!!! ${err.message}",Toast.LENGTH_SHORT).show()
                }
        }


    }
}