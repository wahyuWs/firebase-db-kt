package com.wahyus.firebasedbkt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.wahyus.firebasedbkt.databinding.ActivityInsert2Binding

class InsertActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityInsert2Binding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInsert2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("users")
        binding.btnSave.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val name = binding.edtName.text.toString()
            val age = binding.edtAge.text.toString()
            val address = binding.edtAddress.text.toString()

            if (username.isEmpty()) {
                binding.edtUsername.error = "can not be empty"
                binding.edtUsername.requestFocus()
            }else if (name.isEmpty()) {
                binding.edtName.error = "can not be empty"
                binding.edtName.requestFocus()
            }else if (age.isEmpty()) {
                binding.edtAge.error = "can not be empty"
                binding.edtAge.requestFocus()
            }else if (address.isEmpty()) {
                binding.edtAddress.error = "can not be empty"
                binding.edtAddress.requestFocus()
            } else{
                val user = User(
                    username, name, age, address
                )
                database.child(username).setValue(user)
                    .addOnSuccessListener {
                        Toast.makeText(this, "successfully saved data", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "failed to save data", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}