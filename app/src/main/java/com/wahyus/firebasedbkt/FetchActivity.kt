package com.wahyus.firebasedbkt

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.wahyus.firebasedbkt.databinding.ActivityFetchBinding

@Suppress("UNCHECKED_CAST")
class FetchActivity : AppCompatActivity(), FetchAdapter.ItemAdapterCallback, FetchAdapter.ItemRemoveCallback {
    private lateinit var binding: ActivityFetchBinding
    private lateinit var database: DatabaseReference
    private lateinit var fetchAdapter: FetchAdapter
    private lateinit var dialog: Dialog
    private lateinit var view: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFetchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = mutableListOf<User>()

        fetchAdapter = FetchAdapter(this, this)
        database = FirebaseDatabase.getInstance().getReference("users")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user.clear()
                for (child in snapshot.children) {
                    val data = child.getValue(User::class.java)
                    if (data != null) {
                        user.add(data)
                    }
                }
                fetchAdapter.setData(user)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.rvListUser.apply {
            layoutManager = LinearLayoutManager(this@FetchActivity)
            adapter = fetchAdapter
        }
    }

    private fun setDialog(data: User) {
        dialog = Dialog(this)
        view = layoutInflater.inflate(R.layout.dialog_update, null)
        dialog.apply {
            setContentView(view)
            setCancelable(true)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        val edtUsername = view.findViewById<EditText>(R.id.edt_username_dialog)
        val edtName = view.findViewById<EditText>(R.id.edt_name_dialog)
        val edtAge = view.findViewById<EditText>(R.id.edt_age_dialog)
        val edtAddress = view.findViewById<EditText>(R.id.edt_address_dialog)
        val btnUpdate = view.findViewById<Button>(R.id.btn_update)

        edtUsername.setText(data.username)
        edtName.setText(data.name)
        edtAge.setText(data.age)
        edtAddress.setText(data.address)

        btnUpdate.setOnClickListener {
            //get data edittext when user changes data
            val username = edtUsername.text.toString()
            val updateName = edtName.text.toString()
            val updateAge = edtAge.text.toString()
            val updateAddress = edtAddress.text.toString()

            updateDataDb(username, updateName, updateAge, updateAddress)
        }
    }

    private fun updateDataDb(username: String, updateName: String, updateAge: String, updateAddress: String) {
        val user = mapOf<String, String>(
            "address" to updateAddress,
            "age" to updateAge,
            "name" to updateName
        )

        database.child(username).updateChildren(user)
            .addOnSuccessListener {
                Toast.makeText(this, "update successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .addOnFailureListener {
                Toast.makeText(this, "update failed: $it", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onClick(data: User) {
        setDialog(data)
        dialog.show()
    }

    override fun onItemRemove(data: User) {
        database.child(data.username.toString()).removeValue()
    }
}