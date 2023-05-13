package com.example.login_signup

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login_signup.adapter.MyItemAdapter
import com.example.login_signup.eventbus.UpdateCartEvent
import com.example.login_signup.listener.ICartLoadListener
import com.example.login_signup.listener.IProductLoadListener
import com.example.login_signup.model.CartModel
import com.example.login_signup.model.ItemModel
import com.example.login_signup.utils.SpaceItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nex3z.notificationbadge.NotificationBadge
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class CartListActivity : AppCompatActivity(), IProductLoadListener, ICartLoadListener {

//    lateinit var productLoadListener: IProductLoadListener
//    lateinit var cartLoadListener: ICartLoadListener
    lateinit var recycler_product: RecyclerView
    lateinit var btnCart: FrameLayout
    lateinit var mainLayout: FrameLayout
    lateinit var badge: NotificationBadge


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        if (EventBus.getDefault().hasSubscriberForEvent(UpdateCartEvent::class.java))
            EventBus.getDefault().removeStickyEvent(UpdateCartEvent::class.java)
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onUpdateCartEvent(event: UpdateCartEvent) {
        countCartFromFirebase()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cartlist_activity)
        init()
        loadProductFromFirebase()
        countCartFromFirebase()
    }

    private fun countCartFromFirebase() {
        val cartModelList: MutableList<CartModel> = ArrayList()
        FirebaseDatabase.getInstance()
            .getReference("Cart")
            .child("UNIQUE_USER_ID")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (cartSnapshot in snapshot.children) {
                        val cartModel = cartSnapshot.getValue(CartModel::class.java)
                        cartModel!!.key = cartSnapshot.key
                        cartModelList.add(cartModel)
                    }
//                    cartLoadListener.onLoadCartSuccess(cartModelList)
                }

                override fun onCancelled(error: DatabaseError) {
//                    cartLoadListener.onLoadCartFailed(error.message)
                }
            })
    }

    private fun loadProductFromFirebase() {
        val itemModelList: MutableList<ItemModel> = ArrayList()
        FirebaseDatabase.getInstance()
            .getReference("Item")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (itemSnapshot in snapshot.children) {
                            val itemModel = itemSnapshot.getValue(ItemModel::class.java)
                            itemModel!!.key = itemSnapshot.key
                            itemModelList.add(itemModel)
                        }
//                        productLoadListener.onProductLoadSuccess(itemModelList)
                    } else {
//                        productLoadListener.onProductLoadFailed("Items not exist")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
//                    productLoadListener.onProductLoadFailed(error.message)
                }
            })
    }

    private fun init() {
//        productLoadListener = this
//        cartLoadListener = this

        val gridLayoutManager = GridLayoutManager(this, 2)
        recycler_product.layoutManager = gridLayoutManager
        recycler_product.addItemDecoration(SpaceItemDecoration())

        btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        fun onProductLoadSuccess(itemModelList: List<ItemModel>) {
//            val adapter = MyItemAdapter(this, itemModelList, cartLoadListener)
//            recycler_product.adapter = adapter
        }

        fun onProductLoadFailed(message: String?) {
            Snackbar.make(mainLayout, message!!, Snackbar.LENGTH_LONG).show()
        }

        fun onLoadCartSuccess(cartModelList: List<CartModel>) {
            var cartSum = 0
            for (cartModel in cartModelList) {
                cartSum += cartModel.quantity
            }
            badge.setNumber(cartSum)
        }

        fun onLoadCartFailed(message: String?) {
            Snackbar.make(mainLayout, message!!, Snackbar.LENGTH_LONG).show()
        }
    }
}
