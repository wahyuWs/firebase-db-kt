package com.wahyus.firebasedbkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.wahyus.firebasedbkt.databinding.ActivityFetchBinding

@Suppress("UNCHECKED_CAST")
class FetchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFetchBinding
    private lateinit var database: DatabaseReference
    private lateinit var fetchAdapter: FetchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFetchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = ArrayList<User>()

        fetchAdapter = FetchAdapter()
        database = FirebaseDatabase.getInstance().getReference("users")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
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
}