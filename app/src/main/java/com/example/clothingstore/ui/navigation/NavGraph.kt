package com.example.clothingstore.ui.navigation

sealed class Screen(val route: String, val title: String) {
    data object Home : Screen("home", "متجر الملابس")
    data object ProductDetail : Screen("product_detail/{productId}", "تفاصيل المنتج") {
        fun passId(id: Int) = "product_detail/$id"
    }
    data object Cart : Screen("cart", "سلة التسوق")
}
