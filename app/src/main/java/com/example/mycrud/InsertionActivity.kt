package com.example.mycrud

import android.annotation.SuppressLint
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream
import java.util.*

class InsertionActivity : AppCompatActivity() {

    var sImage:String? = null


    private lateinit var etProductName: EditText
    private lateinit var etProductPrice: EditText
    private lateinit var etProductCatagory: EditText
    private lateinit var imageView: ImageView
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etProductName = findViewById(R.id.etProductName)
        etProductPrice = findViewById(R.id.etProductPrice)
        etProductCatagory = findViewById(R.id.etProductCatagory)
        imageView = findViewById(R.id.imageView)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Product")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val ProductName = etProductName.text.toString()
        val ProductPrice = etProductPrice.text.toString()
        val ProductCatagory = etProductCatagory.text.toString()
        val sImage = sImage.toString()

        //vadlidating if the value is provided or not
        if (ProductName.isEmpty()) {
            etProductName.error = "Please enter name"
        }
        if (ProductPrice.isEmpty()) {
            etProductPrice.error = "Please enter age"
        }
        if (ProductCatagory.isEmpty()) {
            etProductCatagory.error = "Please enter salary"
        }


        val ProductId = dbRef.push().key!!

        val employee = EmployeeModel(ProductId, ProductName, ProductPrice, ProductCatagory, sImage)

        //saving data

        dbRef.child(ProductId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etProductName.text.clear()
                etProductPrice.text.clear()
                etProductCatagory.text.clear()
                imageView.setImageResource(R.drawable.profile)


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

    //insert image
    @RequiresApi(Build.VERSION_CODES.O)
    fun insert_Img(view: View) {

        val myfileintent = Intent(Intent.ACTION_GET_CONTENT)
        myfileintent.type = "image/*"
        launcher.launch(myfileintent)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result ->
        if (result.resultCode == RESULT_OK) {
            val uri = result.data!!.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val mybitmap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                mybitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val bytes = stream.toByteArray()
                sImage = Base64.getEncoder().encodeToString(bytes)
                val imageView = findViewById<ImageView>(R.id.imageView)
                imageView.setImageBitmap(mybitmap)


            } catch (ex: Exception) {
                Toast.makeText(this, "Error ${ex.message}", Toast.LENGTH_LONG).show()            }
        }

    }

}