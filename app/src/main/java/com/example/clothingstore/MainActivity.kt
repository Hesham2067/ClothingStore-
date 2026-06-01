package com.example.clothingstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clothingstore.ui.navigation.Screen
import com.example.clothingstore.ui.screens.CartScreen
import com.example.clothingstore.ui.screens.HomeScreen
import com.example.clothingstore.ui.screens.ProductDetailScreen
import com.example.clothingstore.ui.theme.ClothingStoreTheme
import com.example.clothingstore.ui.theme.Secondary
import com.example.clothingstore.viewmodel.CartViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClothingStoreTheme {
                ClothingStoreApp()
            }
        }
    }
}

@Composable
fun ClothingStoreApp() {
    val navController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onProductClick = { id ->
                    navController.navigate(Screen.ProductDetail.passId(id))
                },
                onCartClick = {
                    navController.navigate(Screen.Cart.route)
                },
                cartItemCount = cartViewModel.itemCount
            )
        }

        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            ProductDetailScreen(
                productId = productId,
                onBack = { navController.popBackStack() },
                onAddToCart = { product, size, color ->
                    cartViewModel.addToCart(product, size, color)
                    Toast.makeText(context, "تمت الإضافة إلى السلة", Toast.LENGTH_SHORT).show()
                }
            )
        }

        composable(Screen.Cart.route) {
            CartScreen(
                cartItems = cartViewModel.cartItems,
                totalPrice = cartViewModel.totalPrice,
                onBack = { navController.popBackStack() },
                onUpdateQuantity = { id, delta -> cartViewModel.updateQuantity(id, delta) },
                onRemove = { id -> cartViewModel.removeFromCart(id) },
                onCheckout = {
                    Toast.makeText(context, "تم إتمام الطلب بنجاح!", Toast.LENGTH_LONG).show()
                    cartViewModel.clearCart()
                }
            )
        }
    }
}
