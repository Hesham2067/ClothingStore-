package com.example.clothingstore.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clothingstore.data.SampleData
import com.example.clothingstore.model.Product
import com.example.clothingstore.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    onBack: () -> Unit,
    onAddToCart: (Product, String, String) -> Unit
) {
    val product = SampleData.products.find { it.id == productId } ?: return
    var selectedSize by remember { mutableStateOf(product.sizes[1]) }
    var selectedColor by remember { mutableStateOf(product.colors[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product.name, color = CardBg) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "رجوع", tint = CardBg)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .background(Surface)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .background(Accent.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = product.name.first().toString(),
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    color = Accent
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = product.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(8.dp))
                Text(text = "$${product.price}", style = MaterialTheme.typography.headlineLarge, color = Secondary)
                Spacer(Modifier.height(12.dp))
                Text(text = product.description, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)

                Spacer(Modifier.height(20.dp))
                Text(text = "المقاس", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    product.sizes.forEach { size ->
                        FilterChip(
                            selected = selectedSize == size,
                            onClick = { selectedSize = size },
                            label = { Text(size) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Secondary,
                                selectedLabelColor = CardBg
                            )
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))
                Text(text = "اللون", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    product.colors.forEach { color ->
                        FilterChip(
                            selected = selectedColor == color,
                            onClick = { selectedColor = color },
                            label = { Text(color) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Secondary,
                                selectedLabelColor = CardBg
                            )
                        )
                    }
                }

                Spacer(Modifier.height(28.dp))
                Button(
                    onClick = { onAddToCart(product, selectedSize, selectedColor) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Secondary)
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("أضف إلى السلة", fontSize = 16.sp)
                }
            }
        }
    }
}
