package com.example.mycrud

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var tvProductId: TextView
    private lateinit var tvProductName: TextView
    private lateinit var tvProductPrice: TextView
    private lateinit var tvProductCatagory:  TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        initView()
        setValuesToViews()


        //click button go to UpdateActivity
        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("ProductId").toString(),
                intent.getStringExtra("ProductName").toString()
            )
        }

        //click button go to DeleteActivity
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("ProductId").toString()
            )
        }

    }

    private fun initView() {
        tvProductId = findViewById(R.id.tvProductId)
        tvProductName = findViewById(R.id.tvProductName)
        tvProductPrice = findViewById(R.id.tvProductPrice)
        tvProductCatagory = findViewById(R.id.tvProductCatagory)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvProductId.text = intent.getStringExtra("ProductId")
        tvProductName.text = intent.getStringExtra("ProductName")
        tvProductPrice.text = intent.getStringExtra("ProductPrice")
        tvProductCatagory.text = intent.getStringExtra("ProductCatagory")

    }

    //delete record
    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Product").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Product data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    //update dialog
    @SuppressLint("MissingInflatedId")
    private fun openUpdateDialog(
        ProductId: String,
        ProductName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etProductName = mDialogView.findViewById<EditText>(R.id.etProductName)
        val etProductPrice = mDialogView.findViewById<EditText>(R.id.etProductPrice)
        val etProductCatagory = mDialogView.findViewById<EditText>(R.id.etProductCatagory)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etProductName.setText(intent.getStringExtra("ProductName").toString())
        etProductPrice.setText(intent.getStringExtra("ProductPrice").toString())
        etProductCatagory.setText(intent.getStringExtra("ProductCatagory").toString())


        mDialog.setTitle("Updating $ProductName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                ProductId,
                etProductName.text.toString(),
                etProductPrice.text.toString(),
                etProductCatagory.text.toString()
            )

            Toast.makeText(applicationContext, "Product Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvProductName.text = etProductName.text.toString()
            tvProductPrice.text = etProductPrice.text.toString()
            tvProductCatagory.text = etProductCatagory.text.toString()

            alertDialog.dismiss()
        }
    }

    //update data to firebase

    private fun updateEmpData(
        id: String,
        name: String,
        Price: String,
        Catagory: String,

    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Product").child(id)
        val empInfo = EmployeeModel(id, name, Price, Catagory)
        dbRef.setValue(empInfo)
    }

}