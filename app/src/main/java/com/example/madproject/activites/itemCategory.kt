package com.example.madproject.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        btnUpdate.setOnClickListener {
            UpdateDilog(
                intent.getStringExtra("itemId").toString(),
                intent.getStringExtra("itemName").toString()
            )
        }

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

    private fun UpdateDilog(
        itemId : String,
        itemName : String
    ){
        val mDilog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_updatedilog,null)

        mDilog.setView(mDialogView)

        val etItemName = mDialogView.findViewById<EditText>(R.id.etName)
        val etItemPrice = mDialogView.findViewById<EditText>(R.id.etPrice)
        val etItemDetails = mDialogView.findViewById<EditText>(R.id.etDetails)
        val btnUpdteData = mDialogView.findViewById<Button>(R.id.btnUpdate)

        etItemName.setText(intent.getStringExtra("itemName").toString())
        etItemPrice.setText(intent.getStringExtra("itemPrice").toString())
        etItemDetails.setText(intent.getStringExtra("itemDettails").toString())

        mDilog.setTitle("Updating $itemName Record")

        val alertDilog = mDilog.create()
        alertDilog.show()

        btnUpdteData.setOnClickListener {
            updateItemData(
                itemId,
                etItemName.text.toString(),
                etItemPrice.text.toString(),
                etItemDetails.text.toString()
            )
            Toast.makeText(applicationContext, "Item Data Updated", Toast.LENGTH_LONG).show()
            //we are setting updated data to our textview
            Fitemname.text = etItemName.text.toString()
            Fitemprice.text = etItemPrice.text.toString()
            Fitemdis.text = etItemDetails.text.toString()

            alertDilog.dismiss()
        }

    }
    private fun updateItemData(
    id:String,
    name:String,
    price:String,
    details:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("item").child(id)
        val itemInfo = ItemModel(id, name, price, details)
        dbRef.setValue(itemInfo)
    }

}