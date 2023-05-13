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
    private lateinit var btnUpdate: Button
    private lateinit var btndelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_category)

        FitemId = findViewById(R.id.FitemId)
        Fitemname = findViewById(R.id.Fitemname)
        Fitemprice = findViewById(R.id.Fitemprice)
        Fitemdis = findViewById(R.id.Fitemdis)
        btnUpdate = findViewById(R.id.updatebtn) // Initialize btnUpdate
        btndelete = findViewById(R.id.deletebtn) // Initialize btndelete

        setValuesToViews()


//        btnUpdate.setOnClickListener {
//            val intent = Intent(this@itemCategory, updatedilog::class.java)
//            intent.putExtra("itemId", FitemId.text.toString())
//            intent.putExtra("itemName", Fitemname.text.toString())
//            intent.putExtra("itemPrice", Fitemprice.text.toString())
//            intent.putExtra("itemDettails", Fitemdis.text.toString())
//            startActivityForResult(intent, UPDATE_REQUEST_CODE)
//        }


        btndelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("itemId").toString()
            )
        }
    }


    private fun deleteRecord(
        id:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("item").child(id)
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



//    companion object {
//        private const val UPDATE_REQUEST_CODE = 123
//    }

}