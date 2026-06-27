package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
fun ServicesHomeScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value
    var searchInput by remember { mutableStateOf("") }
    val filteredServices = viewModel.services.filter {
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

            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateAndClear(Screen.Dashboard) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = KayanRoyalBlue)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = KayanL10n.getString("domestic_services", lang),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search input field
            KayanTextField(
                value = searchInput,
                onValueChange = { searchInput = it },
                label = KayanL10n.getString("search_services", lang),
                leadingIcon = Icons.Default.Search
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Promotional Banner
            LuxuryBanner(
                title = if (lang == Language.AR) "عرض حصري لفترة محدودة!" else "Exclusive Limited Offer!",
                subtitle = KayanL10n.getString("services_banner_text", lang),
                bannerColors = listOf(KayanSkyBlue, KayanTurquoise)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Content List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Horizontal category chips
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
                            Pair("clean_cat", KayanL10n.getString("clean_cat", lang)),
                            Pair("ac_cat", KayanL10n.getString("ac_cat", lang)),
                            Pair("electricity_cat", KayanL10n.getString("electricity_cat", lang)),
                            Pair("plumbing_cat", KayanL10n.getString("plumbing_cat", lang)),
                            Pair("pest_cat", KayanL10n.getString("pest_cat", lang)),
                            Pair("security_cat", KayanL10n.getString("security_cat", lang))
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

                // Emergency response card
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(Brush.linearGradient(listOf(KayanError, KayanWarning)))
                            .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                            .clickable { searchInput = "طوارئ" }
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = KayanL10n.getString("emergency_service", lang),
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Text(
                                    text = KayanL10n.getString("emergency_desc", lang),
                                    color = Color.White.copy(alpha = 0.9f),
                                    fontSize = 11.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FlashOn,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }

                // Filtered Kayan Services List
                items(filteredServices) { service ->
                    val sName = if (lang == Language.AR) service.nameAr else service.nameEn
                    val sDesc = if (lang == Language.AR) service.descriptionAr else service.descriptionEn

                    LuxuryCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.navigateTo(Screen.ServiceDetail(service.id)) }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(Color(service.imagePlaceholderColor)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = getServiceIcon(service.categoryKey),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = sName,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = KayanRoyalBlue,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = sDesc,
                                    fontSize = 11.sp,
                                    color = Color.Gray,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "${KayanL10n.getString("price_starting", lang)}: %.0f %s".format(service.price, KayanL10n.getString("sar", lang)),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = KayanRoyalBlue
                                    )

                                    StarRatingBar(rating = service.rating, size = 12)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getServiceIcon(key: String): ImageVector {
    return when (key) {
        "clean_cat" -> Icons.Default.CleaningServices
        "ac_cat" -> Icons.Default.AcUnit
        "electricity_cat" -> Icons.Default.FlashOn
        "plumbing_cat" -> Icons.Default.Plumbing
        "pest_cat" -> Icons.Default.BugReport
        else -> Icons.Default.Security
    }
}

@Composable
fun ServiceDetailScreen(viewModel: KayanViewModel, serviceId: Int) {
    val lang = viewModel.currentLanguage.value
    val service = viewModel.services.find { it.id == serviceId } ?: viewModel.services[0]
    val sName = if (lang == Language.AR) service.nameAr else service.nameEn
    val sDesc = if (lang == Language.AR) service.descriptionAr else service.descriptionEn

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
                    text = KayanL10n.getString("app_title", lang),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Large Service Showcase Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(service.imagePlaceholderColor)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getServiceIcon(service.categoryKey),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Name & Ratings
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = sName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KayanRoyalBlue,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                StarRatingBar(rating = service.rating, size = 16)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Price starting
            Text(
                text = "${KayanL10n.getString("price_starting", lang)}: %.2f %s".format(service.price, KayanL10n.getString("sar", lang)),
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = KayanRoyalBlue
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Description
            Text(
                text = sDesc,
                fontSize = 14.sp,
                color = Color.DarkGray,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Kayan Guaranteed Checklist
            Text(
                text = KayanL10n.getString("service_features", lang),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = KayanRoyalBlue
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                BulletCheckRow(text = KayanL10n.getString("feat_1", lang))
                BulletCheckRow(text = KayanL10n.getString("feat_2", lang))
                BulletCheckRow(text = KayanL10n.getString("feat_3", lang))
                BulletCheckRow(text = KayanL10n.getString("feat_4", lang))
            }

            Spacer(modifier = Modifier.weight(1f))

            KayanGradientButton(
                text = KayanL10n.getString("order_now", lang),
                onClick = {
                    viewModel.navigateTo(Screen.BookingDateTime(service.id))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun BulletCheckRow(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = KayanSuccess,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 12.sp, color = Color.DarkGray, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun BookingDateTimeScreen(viewModel: KayanViewModel, serviceId: Int) {
    val lang = viewModel.currentLanguage.value
    var selectedDate by remember { mutableStateOf("2026-06-28") }
    var selectedTime by remember { mutableStateOf("10:00 AM") }

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
                    text = KayanL10n.getString("choose_datetime", lang),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Simple Calendar Days Carousel
            Text(
                text = if (lang == Language.AR) "التواريخ المتاحة" else "Available Dates",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(12.dp))

            val dates = listOf(
                Pair("2026-06-28", "28 Jun"),
                Pair("2026-06-29", "29 Jun"),
                Pair("2026-06-30", "30 Jun"),
                Pair("2026-07-01", "01 Jul"),
                Pair("2026-07-02", "02 Jul")
            )

            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(dates) { date ->
                    val isSelected = selectedDate == date.first
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) KayanRoyalBlue else KayanSilver.copy(alpha = 0.15f))
                            .border(
                                1.5.dp,
                                if (isSelected) KayanGold else Color.Transparent,
                                RoundedCornerShape(16.dp)
                            )
                            .clickable { selectedDate = date.first }
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = date.second.split(" ")[0],
                                color = if (isSelected) Color.White else KayanRoyalBlue,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = date.second.split(" ")[1],
                                color = if (isSelected) KayanGold else Color.Gray,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Time Slots Carousel
            Text(
                text = if (lang == Language.AR) "الأوقات والفتحات المتاحة" else "Available Hours",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(12.dp))

            val times = listOf("08:00 AM", "10:00 AM", "12:30 PM", "02:00 PM", "04:30 PM", "07:00 PM")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(times) { time ->
                    val isSelected = selectedTime == time
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) KayanRoyalBlue else KayanSilver.copy(alpha = 0.15f))
                            .border(1.dp, if (isSelected) KayanGold else Color.Transparent, RoundedCornerShape(12.dp))
                            .clickable { selectedTime = time }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = time,
                            color = if (isSelected) Color.White else Color.DarkGray,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            KayanGradientButton(
                text = KayanL10n.getString("next", lang),
                onClick = {
                    viewModel.navigateTo(Screen.BookingAddress(serviceId, selectedDate, selectedTime))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun BookingAddressScreen(viewModel: KayanViewModel, serviceId: Int, date: String, time: String) {
    val lang = viewModel.currentLanguage.value
    var districtInput by remember { mutableStateOf("حي حطين") }
    var streetInput by remember { mutableStateOf("شارع الأمير تركي الأول") }
    var houseInput by remember { mutableStateOf("فيلا رقم 14") }
    var extraInput by remember { mutableStateOf("") }

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
                    text = KayanL10n.getString("choose_address", lang),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            KayanTextField(
                value = districtInput,
                onValueChange = { districtInput = it },
                label = if (lang == Language.AR) "الحي والمدينة" else "District & City",
                leadingIcon = Icons.Default.LocationCity
            )

            Spacer(modifier = Modifier.height(12.dp))

            KayanTextField(
                value = streetInput,
                onValueChange = { streetInput = it },
                label = KayanL10n.getString("street_name", lang),
                leadingIcon = Icons.Default.Map
            )

            Spacer(modifier = Modifier.height(12.dp))

            KayanTextField(
                value = houseInput,
                onValueChange = { houseInput = it },
                label = KayanL10n.getString("house_number", lang),
                leadingIcon = Icons.Default.Home
            )

            Spacer(modifier = Modifier.height(12.dp))

            KayanTextField(
                value = extraInput,
                onValueChange = { extraInput = it },
                label = KayanL10n.getString("address_details", lang),
                leadingIcon = Icons.Default.Info
            )

            Spacer(modifier = Modifier.weight(1f))

            KayanGradientButton(
                text = KayanL10n.getString("next", lang),
                onClick = {
                    viewModel.navigateTo(
                        Screen.BookingSummary(
                            serviceId,
                            date,
                            time,
                            houseInput,
                            "$streetInput، $districtInput",
                            extraInput
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun BookingSummaryScreen(viewModel: KayanViewModel, serviceId: Int, date: String, time: String, house: String, street: String, extra: String) {
    val lang = viewModel.currentLanguage.value
    val service = viewModel.services.find { it.id == serviceId } ?: viewModel.services[0]
    val subtotal = service.price
    val vat = subtotal * 0.15
    val total = subtotal + vat

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
                    text = KayanL10n.getString("summary", lang),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            LuxuryCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (lang == Language.AR) service.nameAr else service.nameEn,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarToday, contentDescription = null, tint = KayanRoyalBlue, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "$date | $time", fontSize = 13.sp, color = Color.DarkGray)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = KayanRoyalBlue, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "$house، $street", fontSize = 13.sp, color = Color.DarkGray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }

                if (extra.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "($extra)", fontSize = 12.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Price Breakdown Table
            Text(
                text = if (lang == Language.AR) "تفاصيل الفاتورة" else "Payment Details",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = KayanRoyalBlue
            )
            Spacer(modifier = Modifier.height(12.dp))

            LuxuryCard(modifier = Modifier.fillMaxWidth()) {
                PriceRow(label = KayanL10n.getString("service_cost", lang), value = subtotal, lang = lang)
                Spacer(modifier = Modifier.height(8.dp))
                PriceRow(label = KayanL10n.getString("tax", lang), value = vat, lang = lang)
                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = KayanSilver.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(12.dp))
                PriceRow(label = KayanL10n.getString("total_amount", lang), value = total, lang = lang, isBold = true)
            }

            Spacer(modifier = Modifier.weight(1f))

            KayanGradientButton(
                text = KayanL10n.getString("confirm_booking", lang),
                onClick = {
                    viewModel.navigateTo(Screen.BookingPayment(serviceId, date, time, house, street, extra, total))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun PriceRow(label: String, value: Double, lang: Language, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = if (isBold) 15.sp else 13.sp,
            fontWeight = if (isBold) FontWeight.ExtraBold else FontWeight.Medium,
            color = if (isBold) KayanRoyalBlue else Color.Gray
        )
        Text(
            text = "%.2f %s".format(value, KayanL10n.getString("sar", lang)),
            fontSize = if (isBold) 16.sp else 13.sp,
            fontWeight = if (isBold) FontWeight.ExtraBold else FontWeight.Bold,
            color = KayanRoyalBlue
        )
    }
}

@Composable
fun BookingPaymentScreen(viewModel: KayanViewModel, serviceId: Int, date: String, time: String, house: String, street: String, extra: String, amount: Double) {
    val lang = viewModel.currentLanguage.value
    var selectedMethod by remember { mutableStateOf("card") }
    
    // Fake credit card form inputs
    var cardNumber by remember { mutableStateOf("") }
    var cardHolder by remember { mutableStateOf("") }
    var expiry by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

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
                    text = KayanL10n.getString("pay_method", lang),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Payment options
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                // Card Option
                PaymentMethodRow(
                    label = KayanL10n.getString("pay_card", lang),
                    isSelected = selectedMethod == "card",
                    icon = Icons.Default.CreditCard,
                    onClick = { selectedMethod = "card" }
                )

                // Wallet Option
                PaymentMethodRow(
                    label = "${KayanL10n.getString("pay_wallet", lang)} (%.2f SAR)".format(viewModel.walletBalance.value),
                    isSelected = selectedMethod == "wallet",
                    icon = Icons.Default.AccountBalanceWallet,
                    onClick = { selectedMethod = "wallet" }
                )

                // COD Option
                PaymentMethodRow(
                    label = KayanL10n.getString("pay_cod", lang),
                    isSelected = selectedMethod == "cod",
                    icon = Icons.Default.Payment,
                    onClick = { selectedMethod = "cod" }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Conditional credit card form
            if (selectedMethod == "card") {
                Text(
                    text = KayanL10n.getString("add_card", lang),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
                Spacer(modifier = Modifier.height(10.dp))

                KayanTextField(
                    value = cardNumber,
                    onValueChange = { cardNumber = it },
                    label = KayanL10n.getString("card_number", lang),
                    leadingIcon = Icons.Default.CreditCard
                )

                Spacer(modifier = Modifier.height(10.dp))

                KayanTextField(
                    value = cardHolder,
                    onValueChange = { cardHolder = it },
                    label = KayanL10n.getString("card_holder", lang),
                    leadingIcon = Icons.Default.Person
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    KayanTextField(
                        value = expiry,
                        onValueChange = { expiry = it },
                        label = KayanL10n.getString("expiry_date", lang),
                        modifier = Modifier.weight(1f)
                    )
                    KayanTextField(
                        value = cvv,
                        onValueChange = { cvv = it },
                        label = KayanL10n.getString("cvv", lang),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            KayanGradientButton(
                text = "${if (lang == Language.AR) "ادفع " else "Pay "} %.2f %s".format(amount, KayanL10n.getString("sar", lang)),
                onClick = {
                    val bookingId = viewModel.createBooking(serviceId, date, time, house, street, extra, amount)
                    viewModel.navigateTo(Screen.BookingSuccess(bookingId))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun PaymentMethodRow(label: String, isSelected: Boolean, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(if (isSelected) KayanRoyalBlue.copy(alpha = 0.08f) else Color.Transparent)
            .border(1.5.dp, if (isSelected) KayanRoyalBlue else KayanSilver.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = if (isSelected) KayanRoyalBlue else Color.Gray)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = if (isSelected) KayanRoyalBlue else Color.DarkGray)
        Spacer(modifier = Modifier.weight(1f))
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = KayanRoyalBlue)
        )
    }
}

@Composable
fun BookingSuccessScreen(viewModel: KayanViewModel, bookingId: String) {
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
            // Giant Success Badge with checkmark
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
                text = KayanL10n.getString("success_pay", lang),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = KayanRoyalBlue,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = KayanL10n.getString("success_pay_desc", lang),
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Booking ID label
            Text(
                text = "${if (lang == Language.AR) "رقم الحجز الموحد" else "Unified Booking ID"}: $bookingId",
                fontSize = 15.sp,
                color = KayanRoyalBlue,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(KayanGold.copy(alpha = 0.15f))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            KayanGradientButton(
                text = KayanL10n.getString("track_booking", lang),
                onClick = {
                    viewModel.navigateTo(Screen.BookingTrack(bookingId))
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

@Composable
fun BookingTrackScreen(viewModel: KayanViewModel, bookingId: String) {
    val lang = viewModel.currentLanguage.value
    val booking = viewModel.bookings.find { it.id == bookingId } ?: viewModel.bookings.lastOrNull() ?: return

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
                    text = "${KayanL10n.getString("track_booking", lang)} ($bookingId)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Map Simulation Decorative Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(KayanSkyBlue.copy(alpha = 0.08f))
                    .border(1.5.dp, KayanRoyalBlue.copy(alpha = 0.2f), RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Map, contentDescription = null, tint = KayanRoyalBlue, modifier = Modifier.size(45.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (lang == Language.AR) "خريطة المتابعة المباشرة لـ كيان" else "Kayan Live Dispatch Map Radar",
                        color = KayanRoyalBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Assigned Technician Row
            LuxuryCard(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(KayanRoyalBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = booking.technician.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = KayanRoyalBlue)
                        Text(text = KayanL10n.getString("verified_tech", lang), fontSize = 11.sp, color = KayanSuccess, fontWeight = FontWeight.Bold)
                    }

                    // Direct chat/call actions
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(KayanRoyalBlue.copy(alpha = 0.08f))
                                .clickable { viewModel.navigateTo(Screen.LiveChat) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Chat, contentDescription = null, tint = KayanRoyalBlue, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Status Stepper Tracker
            Text(
                text = if (lang == Language.AR) "حالة تنفيذ الطلب" else "Order Execution Status",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = KayanRoyalBlue
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                val curStatus = booking.status
                
                TrackerStep(
                    label = KayanL10n.getString("success_pay", lang),
                    isDone = true
                )
                TrackerStep(
                    label = KayanL10n.getString("tech_status_otw", lang),
                    isDone = curStatus >= BookingStatus.DISPATCHED
                )
                TrackerStep(
                    label = KayanL10n.getString("tech_status_arrived", lang),
                    isDone = curStatus >= BookingStatus.ARRIVED
                )
                TrackerStep(
                    label = KayanL10n.getString("tech_status_working", lang),
                    isDone = curStatus >= BookingStatus.WORKING
                )
                TrackerStep(
                    label = KayanL10n.getString("tech_status_done", lang),
                    isDone = curStatus == BookingStatus.DONE
                )
            }

            // Attendance QR Code area
            Divider(color = KayanSilver.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = KayanL10n.getString("qr_code_arrival", lang),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = KayanRoyalBlue
                    )
                    Text(
                        text = if (lang == Language.AR) "أظهر هذا الرمز للفني عند وصوله" else "Show this to expert upon arrival",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .border(1.5.dp, KayanRoyalBlue, RoundedCornerShape(8.dp))
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.QrCode, contentDescription = null, tint = KayanRoyalBlue, modifier = Modifier.size(45.dp))
                }
            }
        }
    }
}

@Composable
fun TrackerStep(label: String, isDone: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (isDone) KayanSuccess else Color.LightGray.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            if (isDone) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = if (isDone) FontWeight.Bold else FontWeight.Medium,
            color = if (isDone) KayanRoyalBlue else Color.Gray
        )
    }
}

@Composable
fun MyBookingsScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
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
                    text = if (lang == Language.AR) "حجوزاتي الموحدة" else "My Unified Bookings",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bookings List
            if (viewModel.bookings.isEmpty()) {
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (lang == Language.AR) "ليس لديك أي حجوزات مجدولة حالياً" else "No scheduled bookings found",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(viewModel.bookings) { booking ->
                        val bName = if (lang == Language.AR) booking.serviceItem.nameAr else booking.serviceItem.nameEn
                        LuxuryCard(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { viewModel.navigateTo(Screen.BookingTrack(booking.id)) }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(booking.serviceItem.imagePlaceholderColor)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = getServiceIcon(booking.serviceItem.categoryKey),
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = bName, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = KayanRoyalBlue)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(text = "${booking.date} | ${booking.time}", fontSize = 11.sp, color = Color.Gray)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${if (lang == Language.AR) "حالة الفني:" else "Tech status:"} ${booking.status}",
                                        fontSize = 11.sp,
                                        color = KayanSuccess,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray)
                            }
                        }
                    }
                }
            }
        }
    }
}
