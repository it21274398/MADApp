package com.example.madproject.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.madproject.R
import com.example.madproject.models.ItemModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdminPanel : AppCompatActivity() {

    private lateinit var itemName: EditText
    private lateinit var itemPrice: EditText
    private lateinit var itemimage: EditText
    private lateinit var btnsavedata: Button


    private lateinit var btnfetchdata:Button
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        itemName = findViewById(R.id.itemName)
        itemPrice = findViewById(R.id.itemPrice)
        itemimage = findViewById(R.id.itemimage)
        btnsavedata = findViewById(R.id.btnsavedata)

        dbRef = FirebaseDatabase.getInstance().getReference("Item")

        btnsavedata.setOnClickListener {
            saveitemdata()

        }

    }
    private fun saveitemdata(){
        var itName = itemName.text.toString()
        var itPrice = itemPrice.text.toString()
        var itimage = itemimage.text.toString()

        if (itName.isEmpty()){
            itemName.error = "please enter item name"
        }
        if (itPrice.isEmpty()){
            itemPrice.error = "please enter item price"
        }
        if (itimage.isEmpty()){
            itemimage.error = "please enter item image"
        }
        //getting values
        val itemId = dbRef.push().key!!
        var item = ItemModel(itemId, itName, itPrice, itimage)

        dbRef.child(itemId).setValue(item)
            .addOnCompleteListener {
                Toast.makeText(this, "data added successfully", Toast.LENGTH_SHORT).show()

                itemName.text.clear()
                itemPrice.text.clear()
                itemimage.text.clear()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "ERROR: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

}

