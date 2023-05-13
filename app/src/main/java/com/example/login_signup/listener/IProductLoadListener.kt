package com.example.login_signup.listener

import com.example.login_signup.model.ItemModel

interface IProductLoadListener {
    fun onProductLoadListener(productModelList:List<ItemModel>)
    fun onLoadFailed(message:String?)
    abstract fun onProductLoadSuccess(itemModels: MutableList<ItemModel>)
    abstract fun onProductLoadFailed(s: String)
}