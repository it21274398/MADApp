package com.example.login_signup.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.login_signup.R
import com.example.login_signup.eventbus.UpdateCartEvent
import com.example.login_signup.listener.ICartLoadListener
import com.example.login_signup.model.CartModel
import com.example.login_signup.model.ItemModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.greenrobot.eventbus.EventBus

class MyItemAdapter(
    private val context: Context,
    private val list:List<ItemModel>,
    private val cartListener: ICartLoadListener


):RecyclerView.Adapter<MyItemAdapter.MyItemViewHolder>() {
    class MyItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var imageView:ImageView
        var txtName: TextView
        var txtPrice:TextView

//        private var clickListener:IRecyclerClickListener? = null
//        fun setClickListener(clickListener:IRecyclerClickListener) {
//            this.clickListener =clickListener;
//        }

        init{
            imageView =itemView.findViewById(R.id.imageView) as ImageView
            txtName =itemView.findViewById(R.id.txtName) as TextView
            txtPrice =itemView.findViewById(R.id.txtPrice) as TextView

            itemView.setOnClickListener(this)
        }

        override fun onClick(v:View?){
//            clickListener!!.onItemClickListener(v:adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemViewHolder {
        return MyItemViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.layout_product_item,parent,false))

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {
        Glide.with(context)
            .load(list[position].image)
            .into(holder.imageView!!)
        holder.txtName!!.text=java.lang.StringBuilder().append(list[position].name)
        holder.txtPrice!!.text=java.lang.StringBuilder("$").append(list[position].price)

//        holder.setClickListener(object:IRecyclerClickListener{
//            fun onItemClickListener(view: View?,position: Int) {
//                addToCart(list[position])
//            }
//
//        })
    }

    private fun addToCart(itemModel: ItemModel) {
        val userCart = FirebaseDatabase.getInstance()
            .getReference("Cart")
            .child("UNIQUE_USER_ID")

        userCart.child(itemModel.key!!)
            .addListenerForSingleValueEvent(object :ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val cartModel =snapshot.getValue(CartModel::class.java)
                        val updateData:MutableMap<String,Any> = HashMap()
                        cartModel!!.quantity = cartModel!!.quantity+1;
                        updateData["quantity"]=cartModel!!.quantity
                        updateData["totalPrice"] =cartModel!!.quantity*cartModel.price!!.toFloat()

                       userCart.child(itemModel.key!!)
                           .updateChildren(updateData)
                           .addOnSuccessListener {
                               EventBus.getDefault().postSticky(UpdateCartEvent())
                               cartListener.onLoadCartFailed("Success add to cart")
                           }
                           .addOnFailureListener{e-> cartListener.onLoadCartFailed(e.message)}
                    }else
                    {
                        val cartModel= CartModel()
                        cartModel.key = itemModel.key
                        cartModel.name = itemModel.name
                        cartModel.image = itemModel.image
                        cartModel.price = itemModel.price
                        cartModel.quantity =1
                        cartModel.totalPrice = itemModel.price!!.toFloat()

                        userCart.child(itemModel.key!!)
                            .setValue(cartModel)
                            .addOnSuccessListener {
                                EventBus.getDefault().postSticky(UpdateCartEvent())
                                cartListener.onLoadCartFailed("Success add to cart")
                            }
                            .addOnFailureListener{e-> cartListener.onLoadCartFailed(e.message)}
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

}