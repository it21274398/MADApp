package com.example.madproject.models

class ItemModel(val itemId: String, val itemName: String, val itemPrice: String, val itemImage: String) {
    constructor() : this("", "", "", "")
}