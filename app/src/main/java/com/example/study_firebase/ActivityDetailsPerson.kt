package com.example.study_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.study_firebase.databinding.ActivityDetailsPersonBinding
import com.example.study_firebase.model.PersonModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ActivityDetailsPerson : AppCompatActivity() {
    lateinit var binding: ActivityDetailsPersonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_person)
        binding = ActivityDetailsPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // display description
        details()

        // btn delete
        binding.btnDelete.setOnClickListener {
            deleteRecord(intent.getStringExtra("id").toString())
        }
        // btn update
        binding.btnUpdate.setOnClickListener {
            openDialog(intent.getStringExtra("id").toString())

        }
    }

    private fun openDialog(id:String) {
        val dialog = AlertDialog.Builder(this)
        val viewDialog = layoutInflater.inflate(R.layout.update_dialog,null)
        dialog.setView(viewDialog)

        //copy data to dialog
        val name = viewDialog.findViewById<EditText>(R.id.edtDialogName)
        val age = viewDialog.findViewById<EditText>(R.id.edtDialogAge)
        val email = viewDialog.findViewById<EditText>(R.id.edtDialogEmail)
        val btnUpdate = viewDialog.findViewById<Button>(R.id.btnDialogUpdate)


        // paste data
        name.setText(intent.getStringExtra("name").toString())
        age.setText(intent.getStringExtra("born").toString())
        email.setText(intent.getStringExtra("email").toString())


        val alertDialog = dialog.create()
        alertDialog.show()

        // set event to button update
        btnUpdate.setOnClickListener {
            updateData(id,name.text.toString(),age.text.toString())
            // close dialog
            alertDialog.dismiss()
            Toast.makeText(this,"Updated!!!",Toast.LENGTH_SHORT).show()

            // display again data after update
            binding.txtName.setText(name.text.toString())
            binding.txtBorn.setText(age.text.toString())
            binding.txtEmail.setText(email.text.toString())
        }

    }

    private fun updateData(id: String, name: String, age: String) {
             val dbRef = FirebaseDatabase.getInstance().getReference("person").child(id)
             val personInfor = PersonModel(id,name,age)
              dbRef.setValue(personInfor)

    }


    // fun delete

    private fun deleteRecord(id:String) {
      val dbRef = FirebaseDatabase.getInstance().getReference("person").child(id)
      val delete = dbRef.removeValue()
      delete.addOnSuccessListener {
          Toast.makeText(this,"Deleted!!!",Toast.LENGTH_SHORT).show()
          val intent = Intent(this,ActivityShowData::class.java)
          finish()
          startActivity(intent)
      }
          .addOnFailureListener {err->
              Toast.makeText(this,"Deleted error ${err.message}!!!",Toast.LENGTH_SHORT).show()
          }
    }

    private fun details() {
        binding.txtID.setText(intent.getStringExtra("id"))
        binding.txtName.setText(intent.getStringExtra("name"))
        binding.txtEmail.setText(intent.getStringExtra("email"))
        binding.txtBorn.setText(intent.getStringExtra("born"))
    }
}