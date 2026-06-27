package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.models.*
import com.example.viewmodel.KayanViewModel
import com.example.viewmodel.Screen
import com.example.ui.components.*
import com.example.ui.theme.*
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ShopHomeScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value
    var searchInput by remember { mutableStateOf("") }
    val filteredProducts = viewModel.products.filter {
        val matchAr = it.nameAr.contains(searchInput, ignoreCase = true)
        val matchEn = it.nameEn.contains(searchInput, ignoreCase = true)
        matchAr || matchEn
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            // Upper Shop Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { viewModel.navigateAndClear(Screen.Dashboard) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = KayanRoyalBlue)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = KayanL10n.getString("shopping_store", lang),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = KayanRoyalBlue
                    )
                }

                // Shopping Cart Button with active badges count
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(KayanRoyalBlue.copy(alpha = 0.08f))
                        .clickable { viewModel.navigateTo(Screen.Cart) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        tint = KayanRoyalBlue
                    )
                    if (viewModel.cart.isNotEmpty()) {
                        val cartCount = viewModel.cart.sumOf { it.quantity }
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = (-2).dp, y = 2.dp)
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(KayanError),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$cartCount",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            KayanTextField(
                value = searchInput,
                onValueChange = { searchInput = it },
                label = KayanL10n.getString("search_shop", lang),
                leadingIcon = Icons.Default.Search
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Promo Banner
            LuxuryBanner(
                title = KayanL10n.getString("flash_sale", lang),
                subtitle = if (lang == Language.AR) "تخفيضات فلاش تبدأ اليوم! وفر حتى 20% على الأجهزة والروائح!" else "Flash sale starts now! Save up to 20%!",
                bannerColors = listOf(KayanRoyalBlue, KayanPepsiBlue)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Categories Row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                val cats = listOf(
                    Pair("all", if (lang == Language.AR) "الكل" else "All"),
                    Pair("electronics", if (lang == Language.AR) "إلكترونيات" else "Electronics"),
                    Pair("luxury", if (lang == Language.AR) "عطور وبخور" else "Luxury & Oud"),
                    Pair("home", if (lang == Language.AR) "منزل ومطبخ" else "Home & Dallah")
                )
                items(cats) { cat ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(KayanRoyalBlue.copy(alpha = 0.06f))
                            .border(1.dp, KayanRoyalBlue.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                            .clickable { searchInput = if (cat.first == "all") "" else cat.second }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = cat.second,
                            color = KayanRoyalBlue,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Products Grid List
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredProducts) { product ->
                    val pName = if (lang == Language.AR) product.nameAr else product.nameEn
                    
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.dp, KayanSilver.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                            .clickable { viewModel.navigateTo(Screen.ProductDetail(product.id)) }
                            .padding(12.dp)
                    ) {
                        Column {
                            // Product Image placeholder
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(KayanRoyalBlue.copy(alpha = 0.04f))
                                    .border(1.dp, KayanSilver.copy(alpha = 0.2f), RoundedCornerShape(14.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = getProductIcon(product.category),
                                    contentDescription = null,
                                    tint = KayanRoyalBlue,
                                    modifier = Modifier.size(36.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = pName,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = KayanRoyalBlue,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                text = product.brand,
                                fontSize = 10.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            // Ratings row
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                StarRatingBar(rating = product.rating, size = 10)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "(${product.reviewsCount})", fontSize = 9.sp, color = Color.Gray)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Pricing with optional strike discount
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val currentPrice = product.discountPrice ?: product.price
                                Text(
                                    text = "%.0f %s".format(currentPrice, KayanL10n.getString("sar", lang)),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = KayanRoyalBlue
                                )
                                if (product.discountPrice != null) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "%.0f".format(product.price),
                                        fontSize = 10.sp,
                                        color = Color.LightGray,
                                        textDecoration = TextDecoration.LineThrough
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Quick add to cart button
                            KayanGradientButton(
                                text = KayanL10n.getString("add_to_cart", lang),
                                onClick = {
                                    viewModel.addToCart(product)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(38.dp),
                                borderBrush = null
                            )
                        }
                    }
                }
            }
        }
    }
}

fun getProductIcon(category: String): ImageVector {
    return when (category) {
        "Electronics" -> Icons.Default.DeveloperBoard
        "Luxury" -> Icons.Default.Opacity
        else -> Icons.Default.CoffeeMaker
    }
}

@Composable
fun ProductDetailScreen(viewModel: KayanViewModel, productId: Int) {
    val lang = viewModel.currentLanguage.value
    val product = viewModel.products.find { it.id == productId } ?: viewModel.products[0]
    val pName = if (lang == Language.AR) product.nameAr else product.nameEn
    val pDesc = if (lang == Language.AR) product.descriptionAr else product.descriptionEn

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = KayanRoyalBlue)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = product.brand,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Large showcase product box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(KayanRoyalBlue.copy(alpha = 0.05f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getProductIcon(product.category),
                    contentDescription = null,
                    tint = KayanRoyalBlue,
                    modifier = Modifier.size(70.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Title
            Text(
                text = pName,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = KayanRoyalBlue
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                StarRatingBar(rating = product.rating, size = 14)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "(${product.reviewsCount} ${if (lang == Language.AR) "تقييم" else "reviews"})", fontSize = 12.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pricing Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                val currentPrice = product.discountPrice ?: product.price
                Text(
                    text = "%.2f %s".format(currentPrice, KayanL10n.getString("sar", lang)),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KayanRoyalBlue
                )
                if (product.discountPrice != null) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "%.2f".format(product.price),
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (lang == Language.AR) "خصم حصري" else "On Sale",
                        color = KayanError,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(KayanError.copy(alpha = 0.08f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Description Title
            Text(
                text = if (lang == Language.AR) "مواصفات وتفاصيل المنتج" else "Product Specifications",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = KayanRoyalBlue
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = pDesc,
                fontSize = 14.sp,
                color = Color.DarkGray,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            KayanGradientButton(
                text = KayanL10n.getString("add_to_cart", lang),
                onClick = {
                    viewModel.addToCart(product)
                    viewModel.navigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CartScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value
    var couponInput by remember { mutableStateOf("") }
    var discountPercent by remember { mutableStateOf(0.0) } // Starts with 0

    val subtotal = viewModel.getCartTotal()
    val discount = subtotal * discountPercent
    val vat = (subtotal - discount) * 0.15
    val grandTotal = (subtotal - discount) + vat

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = KayanRoyalBlue)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = KayanL10n.getString("cart_title", lang),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (viewModel.cart.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.RemoveShoppingCart, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(60.dp))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (lang == Language.AR) "سلة مشترياتك فارغة حالياً" else "Your shopping cart is empty",
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else {
                // Cart Items list
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(viewModel.cart) { item ->
                        val pName = if (lang == Language.AR) item.product.nameAr else item.product.nameEn
                        val pPrice = item.product.discountPrice ?: item.product.price

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .border(1.dp, KayanSilver.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(KayanRoyalBlue.copy(alpha = 0.06f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = getProductIcon(item.product.category),
                                    contentDescription = null,
                                    tint = KayanRoyalBlue,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = pName,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = KayanRoyalBlue,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "%.2f %s".format(pPrice, KayanL10n.getString("sar", lang)),
                                    fontSize = 12.sp,
                                    color = KayanRoyalBlue,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }

                            // Stepper Quantity Editor
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(KayanRoyalBlue.copy(alpha = 0.08f))
                                        .clickable { viewModel.removeFromCart(item.product) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Remove, contentDescription = null, tint = KayanRoyalBlue, modifier = Modifier.size(14.dp))
                                }

                                Text(
                                    text = "${item.quantity}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = KayanRoyalBlue
                                )

                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(KayanRoyalBlue.copy(alpha = 0.08f))
                                        .clickable { viewModel.addToCart(item.product) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = null, tint = KayanRoyalBlue, modifier = Modifier.size(14.dp))
                                }
                            }
                        }
                    }
                }

                // Coupon code row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = couponInput,
                        onValueChange = { couponInput = it },
                        placeholder = { Text(KayanL10n.getString("promo_code", lang)) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = KayanRoyalBlue)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            if (couponInput.trim().lowercase() == "kayan30") {
                                discountPercent = 0.30 // Apply 30% discount
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = KayanRoyalBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(50.dp)
                    ) {
                        Text(KayanL10n.getString("apply", lang), fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Itemized Invoice
                LuxuryCard(modifier = Modifier.fillMaxWidth()) {
                    PriceRow(label = if (lang == Language.AR) "المجموع الفرعي" else "Subtotal", value = subtotal, lang = lang)
                    if (discountPercent > 0.0) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = if (lang == Language.AR) "خصم الكوبون (30%)" else "Coupon Code Discount (30%)", fontSize = 12.sp, color = KayanError, fontWeight = FontWeight.Bold)
                            Text(text = "-%.2f %s".format(discount, KayanL10n.getString("sar", lang)), fontSize = 12.sp, color = KayanError, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    PriceRow(label = KayanL10n.getString("tax", lang), value = vat, lang = lang)
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(color = KayanSilver.copy(alpha = 0.4f))
                    Spacer(modifier = Modifier.height(10.dp))
                    PriceRow(label = KayanL10n.getString("total_amount", lang), value = grandTotal, lang = lang, isBold = true)
                }

                Spacer(modifier = Modifier.height(20.dp))

                KayanGradientButton(
                    text = KayanL10n.getString("checkout", lang),
                    onClick = {
                        viewModel.clearCart()
                        viewModel.navigateTo(Screen.OrderSuccess)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun OrderSuccessScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(KayanSuccess.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = KayanSuccess,
                    modifier = Modifier.size(45.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = KayanL10n.getString("order_success_title", lang),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = KayanRoyalBlue,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = KayanL10n.getString("order_success_desc", lang),
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            KayanGradientButton(
                text = if (lang == Language.AR) "العودة للتسوق" else "Return to Shop",
                onClick = {
                    viewModel.navigateAndClear(Screen.ShopHome)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            KayanSecondaryButton(
                text = if (lang == Language.AR) "العودة للرئيسية" else "Back to Main",
                onClick = {
                    viewModel.navigateAndClear(Screen.Dashboard)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
