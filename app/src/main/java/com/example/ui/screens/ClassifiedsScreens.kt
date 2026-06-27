package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.models.*
import com.example.viewmodel.KayanViewModel
import com.example.viewmodel.Screen
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun ClassifiedsHomeScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value
    var searchInput by remember { mutableStateOf("") }
    val filteredAds = viewModel.classifiedAds.filter {
        val matchAr = it.titleAr.contains(searchInput, ignoreCase = true)
        val matchEn = it.titleEn.contains(searchInput, ignoreCase = true)
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

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateAndClear(Screen.Dashboard) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = KayanRoyalBlue)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = KayanL10n.getString("classified_ads", lang),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search input
            KayanTextField(
                value = searchInput,
                onValueChange = { searchInput = it },
                label = KayanL10n.getString("search_classifieds", lang),
                leadingIcon = Icons.Default.Search
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Main feeds list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Category grid (Motors, Real Estate, Watches, Boats)
                item {
                    Text(
                        text = KayanL10n.getString("categories", lang),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = KayanRoyalBlue
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val cats = listOf(
                            Pair("Motors", if (lang == Language.AR) "سيارات ومحركات" else "Motors"),
                            Pair("Real Estate", if (lang == Language.AR) "عقارات وأراضي" else "Real Estate"),
                            Pair("Watches", if (lang == Language.AR) "ساعات وهدايا" else "Watches"),
                            Pair("Boats", if (lang == Language.AR) "قوارب وبحرية" else "Boats")
                        )
                        items(cats) { cat ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(KayanRoyalBlue.copy(alpha = 0.06f))
                                    .border(1.dp, KayanRoyalBlue.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                                    .clickable { searchInput = cat.second }
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
                }

                // Classified Ads list items
                items(filteredAds) { ad ->
                    val title = if (lang == Language.AR) ad.titleAr else ad.titleEn
                    val location = if (lang == Language.AR) ad.locationAr else ad.locationEn

                    LuxuryCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.navigateTo(Screen.ClassifiedsDetail(ad.id)) }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Large preview box placeholder
                            Box(
                                modifier = Modifier
                                    .size(75.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(KayanRoyalBlue.copy(alpha = 0.05f))
                                    .border(1.dp, KayanSilver.copy(alpha = 0.3f), RoundedCornerShape(14.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = getAdIcon(ad.category),
                                    contentDescription = null,
                                    tint = KayanRoyalBlue,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = title,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = KayanRoyalBlue,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )
                                    
                                    // Premium Badge
                                    if (ad.isPremium) {
                                        Text(
                                            text = if (lang == Language.AR) "مميز" else "Premium",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = KayanRoyalBlue,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(KayanGold)
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "%.0f %s".format(ad.price, KayanL10n.getString("sar", lang)),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = KayanRoyalBlue
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(text = location, fontSize = 10.sp, color = Color.Gray)
                                    }
                                    
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Visibility, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(12.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(text = "${ad.views}", fontSize = 10.sp, color = Color.LightGray)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Floating Action Button to post ad
        FloatingActionButton(
            onClick = {
                viewModel.navigateTo(Screen.CreateAdStep1)
            },
            containerColor = KayanRoyalBlue,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .size(56.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
        }
    }
}

fun getAdIcon(category: String): ImageVector {
    return when (category) {
        "Motors" -> Icons.Default.DirectionsCar
        "Real Estate" -> Icons.Default.Home
        "Watches" -> Icons.Default.Watch
        else -> Icons.Default.DirectionsBoat
    }
}

@Composable
fun ClassifiedsDetailScreen(viewModel: KayanViewModel, adId: Int) {
    val lang = viewModel.currentLanguage.value
    val ad = viewModel.classifiedAds.find { it.id == adId } ?: viewModel.classifiedAds[0]
    val title = if (lang == Language.AR) ad.titleAr else ad.titleEn
    val desc = if (lang == Language.AR) ad.descriptionAr else ad.descriptionEn
    val location = if (lang == Language.AR) ad.locationAr else ad.locationEn
    val context = LocalContext.current

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
                    text = KayanL10n.getString("ad_details", lang),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Big Showcase Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(KayanRoyalBlue.copy(alpha = 0.05f))
                    .border(1.5.dp, KayanRoyalBlue.copy(alpha = 0.1f), RoundedCornerShape(22.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getAdIcon(ad.category),
                    contentDescription = null,
                    tint = KayanRoyalBlue,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Title with Premium Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KayanRoyalBlue,
                    modifier = Modifier.weight(1f)
                )
                if (ad.isPremium) {
                    Text(
                        text = if (lang == Language.AR) "مميز" else "Premium",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KayanRoyalBlue,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(KayanGold)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Price Row
            Text(
                text = "%.0f %s".format(ad.price, KayanL10n.getString("sar", lang)),
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = KayanRoyalBlue
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = location, fontSize = 13.sp, color = Color.Gray)
                
                Spacer(modifier = Modifier.width(20.dp))

                Icon(Icons.Default.Visibility, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "${ad.views} ${if (lang == Language.AR) "مشاهدة" else "views"}", fontSize = 13.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Seller Card
            LuxuryCard(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(KayanRoyalBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = ad.sellerName, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = KayanRoyalBlue)
                        StarRatingBar(rating = ad.sellerRating, size = 11)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = if (lang == Language.AR) "وصف الإعلان" else "Description",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = KayanRoyalBlue
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = desc,
                fontSize = 14.sp,
                color = Color.DarkGray,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            // Contact Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                KayanGradientButton(
                    text = KayanL10n.getString("chat_seller", lang),
                    onClick = {
                        viewModel.navigateTo(Screen.LiveChat)
                    },
                    modifier = Modifier.weight(1f)
                )

                KayanSecondaryButton(
                    text = KayanL10n.getString("call_seller", lang),
                    onClick = {
                        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${ad.sellerPhone}"))
                        context.startActivity(dialIntent)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CreateAdStep1Screen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value
    var adTitle by remember { mutableStateOf("") }
    var adPrice by remember { mutableStateOf("") }
    var adCategory by remember { mutableStateOf("Motors") }

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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = KayanRoyalBlue)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = KayanL10n.getString("ad_step_1", lang),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            KayanTextField(
                value = adTitle,
                onValueChange = { adTitle = it },
                label = KayanL10n.getString("ad_title", lang),
                leadingIcon = Icons.Default.Title
            )

            Spacer(modifier = Modifier.height(12.dp))

            KayanTextField(
                value = adPrice,
                onValueChange = { adPrice = it },
                label = KayanL10n.getString("ad_price", lang),
                leadingIcon = Icons.Default.MonetizationOn
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (lang == Language.AR) "اختر فئة الإعلان" else "Choose Ad Category",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(10.dp))

            val cats = listOf("Motors", "Real Estate", "Watches", "Boats")
            cats.forEach { cat ->
                val isSelected = adCategory == cat
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) KayanRoyalBlue.copy(alpha = 0.08f) else Color.Transparent)
                        .border(1.dp, if (isSelected) KayanRoyalBlue else KayanSilver.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .clickable { adCategory = cat }
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(getAdIcon(cat), contentDescription = null, tint = if (isSelected) KayanRoyalBlue else Color.Gray)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = cat, fontWeight = FontWeight.Bold, color = if (isSelected) KayanRoyalBlue else Color.DarkGray)
                    Spacer(modifier = Modifier.weight(1f))
                    RadioButton(selected = isSelected, onClick = { adCategory = cat }, colors = RadioButtonDefaults.colors(selectedColor = KayanRoyalBlue))
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            KayanGradientButton(
                text = KayanL10n.getString("next", lang),
                onClick = {
                    val p = adPrice.toDoubleOrNull() ?: 0.0
                    viewModel.navigateTo(Screen.CreateAdStep2(adTitle, p, adCategory))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CreateAdStep2Screen(viewModel: KayanViewModel, title: String, price: Double, category: String) {
    val lang = viewModel.currentLanguage.value
    var adDesc by remember { mutableStateOf("") }

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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = KayanRoyalBlue)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = KayanL10n.getString("ad_step_2", lang),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Upload Media Simulation box
            Text(
                text = if (lang == Language.AR) "الصور ومقاطع الفيديو للإعلان" else "Media Photos & Videos",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(KayanRoyalBlue.copy(alpha = 0.05f))
                    .border(2.dp, Brush.sweepGradient(listOf(KayanRoyalBlue, KayanSkyBlue)), RoundedCornerShape(16.dp))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.CloudUpload, contentDescription = null, tint = KayanRoyalBlue, modifier = Modifier.size(45.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = if (lang == Language.AR) "اضغط لرفع صور حقيقية عالية الجودة" else "Tap to upload high fidelity photos", color = KayanRoyalBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = adDesc,
                onValueChange = { adDesc = it },
                label = { Text(KayanL10n.getString("ad_desc", lang)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = KayanRoyalBlue)
            )

            Spacer(modifier = Modifier.weight(1f))

            KayanGradientButton(
                text = KayanL10n.getString("next", lang),
                onClick = {
                    viewModel.navigateTo(Screen.CreateAdStep3(title, price, category, adDesc))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CreateAdStep3Screen(viewModel: KayanViewModel, title: String, price: Double, category: String, desc: String) {
    val lang = viewModel.currentLanguage.value
    var isPremiumChecked by remember { mutableStateOf(false) }

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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = KayanRoyalBlue)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = KayanL10n.getString("ad_step_3", lang),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Premium Upgrade Promotion Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(Brush.linearGradient(listOf(KayanRoyalBlue, KayanPepsiBlue)))
                    .border(2.dp, KayanGold, RoundedCornerShape(22.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = KayanL10n.getString("premium_ad", lang),
                            color = KayanGold,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Icon(Icons.Default.Stars, contentDescription = null, tint = KayanGold, modifier = Modifier.size(24.dp))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (lang == Language.AR) {
                            "احصل على تفاعل مضاعف حتى 3 مرات وسرعة بيع خارقة عبر تثبيت إعلانك في صدر الصفحة الأولى للقسم برمز ذهبي مذهل مقابل 50 ريال فقط!"
                        } else {
                            "Get 3x triple response views and ultra fast closing times by pinning your advertisement in top gold Master slider for only 50 SAR!"
                        },
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = isPremiumChecked,
                            onCheckedChange = { isPremiumChecked = it },
                            colors = CheckboxDefaults.colors(checkedColor = KayanGold, checkmarkColor = KayanRoyalBlue)
                        )
                        Text(
                            text = if (lang == Language.AR) "نعم، أريد الترقية إلى إعلان مميز فاخر" else "Yes, I want to upgrade to luxury Premium ad",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            KayanGradientButton(
                text = KayanL10n.getString("publish", lang),
                onClick = {
                    viewModel.publishClassifiedAd(title, price, category, desc, isPremiumChecked)
                    viewModel.navigateTo(Screen.PublishSuccess)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun PublishSuccessScreen(viewModel: KayanViewModel) {
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
                text = KayanL10n.getString("ad_success", lang),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = KayanRoyalBlue,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            KayanGradientButton(
                text = if (lang == Language.AR) "الذهاب لإعلانات كيان" else "Go to Classifieds Feed",
                onClick = {
                    viewModel.navigateAndClear(Screen.ClassifiedsHome)
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
