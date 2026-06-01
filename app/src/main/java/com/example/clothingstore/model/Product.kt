package com.example.clothingstore.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageRes: Int,
    val description: String,
    val sizes: List<String> = listOf("S", "M", "L", "XL"),
    val colors: List<String> = listOf("أسود", "أبيض", "أزرق")
)
