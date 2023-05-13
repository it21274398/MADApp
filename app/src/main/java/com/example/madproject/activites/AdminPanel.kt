package com.example.madproject.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

    private lateinit var dbRef: DatabaseReference

    companion object {
        const val EDIT_ITEM_REQUEST_CODE = 100
    }

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

    private fun saveitemdata() {
        val itName = itemName.text.toString()
        val itPrice = itemPrice.text.toString()
        val itimage = itemimage.text.toString()

        if (itName.isEmpty()) {
            itemName.error = "Please enter item name"
            return
        }
        if (itPrice.isEmpty()) {
            itemPrice.error = "Please enter item price"
            return
        }
        if (itimage.isEmpty()) {
            itemimage.error = "Please enter item image"
            return
        }

        // Generating a new item ID
        val itemId = dbRef.push().key ?: ""
        val item = ItemModel(itemId, itName, itPrice, itimage)

        dbRef.child(itemId).setValue(item)
            .addOnCompleteListener {
                Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show()

                itemName.text.clear()
                itemPrice.text.clear()
                itemimage.text.clear()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "ERROR: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    fun gotoupdatePage(view: View) {
        val intent = Intent(this, updatedilog::class.java)
        intent.putExtra("itemId", "") // Pass the itemId of the item being edited
        intent.putExtra("itemName", "") // Pass the current item name
        intent.putExtra("itemPrice", "") // Pass the current item price
        intent.putExtra("itemDetails", "") // Pass the current item details
        startActivityForResult(intent, EDIT_ITEM_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EDIT_ITEM_REQUEST_CODE && resultCode == RESULT_OK) {
            val updatedItemName = data?.getStringExtra("updatedItemName")
            val updatedItemPrice = data?.getStringExtra("updatedItemPrice")
            val updatedItemDetails = data?.getStringExtra("updatedItemDetails")

            // Perform necessary operations with the updated data
        }
    }
}
