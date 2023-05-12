package com.example.madproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R
import com.example.madproject.models.ItemModel

class ItemAdapter (private val itemList:ArrayList<ItemModel>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

    private lateinit var mListener : onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setonItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_category,parent,false)
        return ViewHolder(itemView,mListener)

    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        val currentitem = itemList[position]
        holder.FitemId.text = currentitem.itemId
        holder.Fitemname.text = currentitem.itemName
        holder.Fitemprice.text = currentitem.itemPrice
        holder.Fitemdis.text = currentitem.itemImage
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) :RecyclerView.ViewHolder(itemView){
        val FitemId : TextView = itemView.findViewById(R.id.FitemId)
        val Fitemname : TextView = itemView.findViewById(R.id.Fitemname)
        val Fitemprice : TextView = itemView.findViewById(R.id.Fitemprice)
        val Fitemdis : TextView = itemView.findViewById(R.id.Fitemdis)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}