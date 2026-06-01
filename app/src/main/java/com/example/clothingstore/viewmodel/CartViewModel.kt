package com.example.clothingstore.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.clothingstore.model.Product

data class CartItem(
    val product: Product,
    val quantity: Int = 1,
    val selectedSize: String = "M",
    val selectedColor: String = "أسود"
)

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    val totalPrice: Double get() = _cartItems.sumOf { it.product.price * it.quantity }

    val itemCount: Int get() = _cartItems.sumOf { it.quantity }

    fun addToCart(product: Product, size: String = "M", color: String = "أسود") {
        val existing = _cartItems.find { it.product.id == product.id }
        if (existing != null) {
            val index = _cartItems.indexOf(existing)
            _cartItems[index] = existing.copy(quantity = existing.quantity + 1)
        } else {
            _cartItems.add(CartItem(product = product, selectedSize = size, selectedColor = color))
        }
    }

    fun removeFromCart(productId: Int) {
        _cartItems.removeAll { it.product.id == productId }
    }

    fun updateQuantity(productId: Int, delta: Int) {
        val index = _cartItems.indexOfFirst { it.product.id == productId }
        if (index == -1) return
        val item = _cartItems[index]
        val newQty = item.quantity + delta
        if (newQty <= 0) {
            _cartItems.removeAt(index)
        } else {
            _cartItems[index] = item.copy(quantity = newQty)
        }
    }

    fun clearCart() {
        _cartItems.clear()
    }
}
