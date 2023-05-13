package com.example.madproject.activites

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.madproject.R
import com.example.madproject.models.ItemModel
import com.google.firebase.database.FirebaseDatabase


class itemCategory : AppCompatActivity() {



    private lateinit var FitemId: TextView
    private lateinit var Fitemname: TextView
    private lateinit var Fitemprice: TextView
    private lateinit var Fitemdis: TextView
    private lateinit var btndelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_category)

        FitemId = findViewById(R.id.FitemId)
        Fitemname = findViewById(R.id.Fitemname)
        Fitemprice = findViewById(R.id.Fitemprice)
        Fitemdis = findViewById(R.id.Fitemdis)

        setValuesToViews()

        btndelete.setOnClickListener {
            val id = FitemId.text.toString()
            removeItem(id)
        }

    }


    private fun deleteRecord(
        id:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Item").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener{
            Toast.makeText(this, "Item Deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this,FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error ->
            Toast.makeText(this,"Deleting Error ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
    private fun setValuesToViews(){
        FitemId.text = intent.getStringExtra("itemId")
        Fitemname.text = intent.getStringExtra("itemName")
        Fitemprice.text = intent.getStringExtra("itemPrice")
        Fitemdis.text = intent.getStringExtra("itemDettails")
    }

    private fun removeItem(itemId: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Item").child(itemId)
        dbRef.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Item removed successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to remove item: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

}