package com.example.madproject.activites

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.madproject.R
import com.example.madproject.models.ItemModel
import com.google.firebase.database.FirebaseDatabase

class updatedilog : AppCompatActivity() {
    private lateinit var etItemName: EditText
    private lateinit var etItemPrice: EditText
    private lateinit var etItemDetails: EditText
    private lateinit var btnUpdate: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updatedilog)

        etItemName = findViewById(R.id.etName)
        etItemPrice = findViewById(R.id.etPrice)
        etItemDetails = findViewById(R.id.etDetails)
        btnUpdate = findViewById(R.id.btnUpdate)

        val itemId = intent.getStringExtra("itemId")
        val itemName = intent.getStringExtra("itemName")
        val itemPrice = intent.getStringExtra("itemPrice")
        val itemDetails = intent.getStringExtra("itemDettails")

        etItemName.setText(itemName)
        etItemPrice.setText(itemPrice)
        etItemDetails.setText(itemDetails)

        btnUpdate.setOnClickListener {
            val updatedItemName = etItemName.text.toString()
            val updatedItemPrice = etItemPrice.text.toString()
            val updatedItemDetails = etItemDetails.text.toString()

            val intent = Intent()
            intent.putExtra("updatedItemName", updatedItemName)
            intent.putExtra("updatedItemPrice", updatedItemPrice)
            intent.putExtra("updatedItemDetails", updatedItemDetails)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    private fun updateItemData(itemId: String, itemName: String, itemPrice: String, itemDetails: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Item").child(itemId)
        val item = ItemModel(itemId, itemName, itemPrice, itemDetails)
        dbRef.setValue(item)
    }
}
