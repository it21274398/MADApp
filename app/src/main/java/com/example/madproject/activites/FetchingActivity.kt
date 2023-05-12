package com.example.madproject.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R
import com.example.madproject.adapters.ItemAdapter
import com.example.madproject.models.ItemModel
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var itemList:ArrayList<ItemModel>
    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        itemRecyclerView = findViewById(R.id.rvitem)
        itemRecyclerView.layoutManager = LinearLayoutManager(this)
        itemRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        itemList =  arrayListOf<ItemModel>()

        getItemsData()
    }
    private fun getItemsData(){
        itemRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

         dbRef = FirebaseDatabase.getInstance().getReference("Item")

        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                if(snapshot.exists()){
                    for (iteSnap in snapshot.children){
                        val itemData = iteSnap.getValue(ItemModel::class.java)
                        itemList.add(itemData!!)
                    }
                 val iAdapter = ItemAdapter(itemList)
                    itemRecyclerView.adapter = iAdapter

                    iAdapter.setonItemClickListener(object : ItemAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity,itemCategory::class.java)

                            //put extra
                            intent.putExtra("itemId",itemList[position].itemId)
                            intent.putExtra("itemName",itemList[position].itemName)
                            intent.putExtra("itemPrice",itemList[position].itemPrice)
                            intent.putExtra("itemDettails",itemList[position].itemImage)

                            startActivity(intent)
                        }

                    })

                    itemRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}