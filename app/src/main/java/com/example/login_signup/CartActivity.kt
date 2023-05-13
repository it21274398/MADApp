package com.example.login_signup

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login_signup.adapter.MyCartAdapter
import com.example.login_signup.eventbus.UpdateCartEvent
import com.example.login_signup.listener.ICartLoadListener
import com.example.login_signup.model.CartModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CartActivity : AppCompatActivity(), ICartLoadListener {

    var cartLoadListener: ICartLoadListener? = null

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
        loadCartFromFirebase()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        init()
        loadCartFromFirebase()
    }

    private fun loadCartFromFirebase() {
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
                    cartLoadListener?.onLoadCartSuccess(cartModelList)
                }

                override fun onCancelled(error: DatabaseError) {
                    cartLoadListener?.onLoadCartFailed(error.message)
                }
            })
    }

    @SuppressLint("WrongViewCast")
    private fun init() {
        cartLoadListener = this
        val layoutManager = LinearLayoutManager(this)

        val recyclerCart = findViewById<RecyclerView>(R.id.recycler_cart)
        recyclerCart.layoutManager = layoutManager
        recyclerCart.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }
    }

    override fun onLoadCartSuccess(cartModelList: List<CartModel>) {
        var sum = 0.0 //calculate total
        for (cartModel in cartModelList) {
//            sum += cartModel?.totalPrice ?: 0.0
        }
        val txtTotal = findViewById<TextView>(R.id.txtTotal)
//        txtTotal.text = StringBuilder
        txtTotal.text = StringBuilder("$").append(sum)

        val adapter = MyCartAdapter(this, cartModelList)
        val recyclerCart = findViewById<RecyclerView>(R.id.recycler_cart)
        recyclerCart.adapter = adapter
    }

    override fun onLoadCartFailed(message: String?) {
        val mainLayout = findViewById<View>(R.id.mainLayout)
        Snackbar.make(mainLayout, message ?: "Error loading cart", Snackbar.LENGTH_LONG).show()
    }
}
