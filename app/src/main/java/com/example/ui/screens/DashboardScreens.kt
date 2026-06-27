package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
fun DashboardScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value
    val isGuest = !viewModel.isLoggedIn.value
    val userName = if (isGuest) KayanL10n.getString("guest", lang) else viewModel.loggedInUser.value

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

            // Upper Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "${KayanL10n.getString("welcome", lang)} $userName",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KayanRoyalBlue
                    )
                    Text(
                        text = "2026-06-27 | ${viewModel.currentCountry.value.flag} ${KayanL10n.getString(viewModel.currentCountry.value.nameKey, lang)}",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Notification Icon with red dot badge
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(KayanRoyalBlue.copy(alpha = 0.08f))
                            .clickable { viewModel.navigateTo(Screen.Notifications) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            tint = KayanRoyalBlue
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = (-4).dp, y = 4.dp)
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(KayanError)
                        )
                    }

                    // Profile pic circle or outline
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(KayanRoyalBlue, KayanPepsiBlue)))
                            .clickable { viewModel.navigateTo(Screen.Profile) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Unified Kayan Wallet & Points Status Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(Brush.linearGradient(listOf(KayanRoyalBlue, KayanPepsiBlue)))
                    .border(
                        1.dp,
                        Brush.linearGradient(listOf(KayanGold.copy(alpha = 0.6f), Color.Transparent)),
                        RoundedCornerShape(22.dp)
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AccountBalanceWallet,
                                contentDescription = null,
                                tint = KayanGold,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = KayanL10n.getString("wallet_balance", lang),
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        // Points count
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White.copy(alpha = 0.12f))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = KayanGold,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${viewModel.pointsBalance.value} ${KayanL10n.getString("point_unit", lang)}",
                                color = KayanGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "%.2f %s".format(viewModel.walletBalance.value, KayanL10n.getString("sar", lang)),
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${KayanL10n.getString("savings", lang)}: %.2f %s".format(
                                viewModel.totalSavings.value,
                                KayanL10n.getString("sar", lang)
                            ),
                            color = KayanSkyBlue,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = KayanL10n.getString("app_title", lang),
                            color = Color.White.copy(alpha = 0.4f),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // The Three Mighty Section Cards taking up the remaining screen space
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // CARD 1: Domestic Services (الخدمات المنزلية)
                DashboardCategoryCard(
                    title = KayanL10n.getString("domestic_services", lang),
                    desc = KayanL10n.getString("domestic_desc", lang),
                    actionText = KayanL10n.getString("discover", lang),
                    startColor = KayanSkyBlue,
                    endColor = KayanTurquoise,
                    icon = Icons.Default.HomeRepairService,
                    onClick = { viewModel.navigateTo(Screen.ServicesHome) }
                )

                // CARD 2: Shopping Store (متجر التسوق)
                DashboardCategoryCard(
                    title = KayanL10n.getString("shopping_store", lang),
                    desc = KayanL10n.getString("shopping_desc", lang),
                    actionText = KayanL10n.getString("shop_now", lang),
                    startColor = KayanRoyalBlue,
                    endColor = KayanPepsiBlue,
                    icon = Icons.Default.ShoppingBag,
                    onClick = { viewModel.navigateTo(Screen.ShopHome) }
                )

                // CARD 3: Classified Ads (الإعلانات المبوبة)
                DashboardCategoryCard(
                    title = KayanL10n.getString("classified_ads", lang),
                    desc = KayanL10n.getString("classified_desc", lang),
                    actionText = KayanL10n.getString("browse_ads", lang),
                    startColor = KayanPepsiBlue,
                    endColor = KayanSkyBlue,
                    icon = Icons.Default.Campaign,
                    onClick = { viewModel.navigateTo(Screen.ClassifiedsHome) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ColumnScope.DashboardCategoryCard(
    title: String,
    desc: String,
    actionText: String,
    startColor: Color,
    endColor: Color,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .clip(RoundedCornerShape(22.dp))
            .background(Brush.linearGradient(listOf(startColor, endColor)))
            .border(
                1.5.dp,
                Brush.linearGradient(listOf(Color.White.copy(alpha = 0.4f), Color.Transparent)),
                RoundedCornerShape(22.dp)
            )
            .clickable(onClick = onClick)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = desc,
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                
                // Explored action chip
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = actionText,
                        color = KayanRoyalBlue,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = KayanRoyalBlue,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Large decorative circular icon
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value
    val isGuest = !viewModel.isLoggedIn.value

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

            // Screen Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = KayanRoyalBlue)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = KayanL10n.getString("profile_title", lang),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Large Avatar & Edit Form Indicator
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(KayanRoyalBlue, KayanPepsiBlue))),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(54.dp)
                    )
                    
                    // Mini edit floating action button
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(KayanGold)
                            .border(1.5.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = KayanRoyalBlue,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                val nameStr = if (isGuest) KayanL10n.getString("guest", lang) else viewModel.loggedInUser.value
                Text(
                    text = nameStr,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KayanRoyalBlue
                )

                if (!isGuest) {
                    Text(
                        text = viewModel.userEmail.value,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = viewModel.userPhone.value,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Profile Stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileStatCard(
                    title = if (lang == Language.AR) "طلباتي" else "Bookings",
                    value = "${viewModel.bookings.size}",
                    modifier = Modifier.weight(1f)
                )
                ProfileStatCard(
                    title = if (lang == Language.AR) "النقاط" else "Points",
                    value = "${viewModel.pointsBalance.value}",
                    modifier = Modifier.weight(1f)
                )
                ProfileStatCard(
                    title = if (lang == Language.AR) "التوفير" else "Savings",
                    value = "%.0f %s".format(viewModel.totalSavings.value, KayanL10n.getString("sar", lang)),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Wallet Balance Row Inside Profile
            LuxuryCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = KayanL10n.getString("wallet_balance", lang),
                            fontSize = 13.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "%.2f %s".format(viewModel.walletBalance.value, KayanL10n.getString("sar", lang)),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KayanRoyalBlue
                        )
                    }
                    
                    // QR Code Scanner / Quick Pay Action
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(KayanRoyalBlue)
                            .clickable { }
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = if (lang == Language.AR) "سحب الأرباح" else "Withdraw Cash",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // List Options
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                item {
                    ProfileMenuRow(
                        icon = Icons.Default.Settings,
                        title = KayanL10n.getString("settings_title", lang)
                    ) {
                        viewModel.navigateTo(Screen.Settings)
                    }
                }
                item {
                    ProfileMenuRow(
                        icon = Icons.Default.HomeRepairService,
                        title = if (lang == Language.AR) "حجوزاتي وطلباتي" else "My Bookings"
                    ) {
                        viewModel.navigateTo(Screen.MyBookings)
                    }
                }
                item {
                    ProfileMenuRow(
                        icon = Icons.Default.HeadsetMic,
                        title = KayanL10n.getString("live_support", lang)
                    ) {
                        viewModel.navigateTo(Screen.HelpSupport)
                    }
                }
                item {
                    ProfileMenuRow(
                        icon = Icons.Default.StarHalf,
                        title = KayanL10n.getString("rate_app", lang)
                    ) {
                        viewModel.navigateTo(Screen.AppRating)
                    }
                }
                item {
                    ProfileMenuRow(
                        icon = Icons.Default.Info,
                        title = KayanL10n.getString("about_app", lang)
                    ) {
                        viewModel.navigateTo(Screen.AboutKayan)
                    }
                }
                item {
                    val label = if (isGuest) KayanL10n.getString("login", lang) else KayanL10n.getString("logout", lang)
                    ProfileMenuRow(
                        icon = if (isGuest) Icons.Default.Login else Icons.Default.Logout,
                        title = label,
                        iconColor = KayanError,
                        textColor = KayanError
                    ) {
                        viewModel.isLoggedIn.value = false
                        viewModel.navigateAndClear(Screen.Login)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileStatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(KayanSilver.copy(alpha = 0.12f))
            .border(1.dp, KayanSilver.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = KayanRoyalBlue)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = title, fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun ProfileMenuRow(
    icon: ImageVector,
    title: String,
    iconColor: Color = KayanRoyalBlue,
    textColor: Color = Color.DarkGray,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(iconColor.copy(alpha = 0.08f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun SettingsScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value
    var selectedLang by remember { mutableStateOf(viewModel.currentLanguage.value) }
    var receiveNotifications by remember { mutableStateOf(true) }
    var selectedCountry by remember { mutableStateOf(viewModel.currentCountry.value) }

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

            // Top Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = KayanRoyalBlue)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = KayanL10n.getString("settings_title", lang),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Language Switch Preference Row
            Text(
                text = KayanL10n.getString("lang_select", lang),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray.copy(alpha = 0.2f))
                    .padding(3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (selectedLang == Language.AR) KayanRoyalBlue else Color.Transparent)
                        .clickable {
                            selectedLang = Language.AR
                            viewModel.currentLanguage.value = Language.AR
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "عربي", color = if (selectedLang == Language.AR) Color.White else KayanRoyalBlue, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (selectedLang == Language.EN) KayanRoyalBlue else Color.Transparent)
                        .clickable {
                            selectedLang = Language.EN
                            viewModel.currentLanguage.value = Language.EN
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "English", color = if (selectedLang == Language.EN) Color.White else KayanRoyalBlue, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Notifications switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = KayanL10n.getString("notifications", lang),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                    Text(
                        text = if (lang == Language.AR) "تلقي عروض الخصومات وتحديثات الطلبات" else "Receive special deals & order updates",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Switch(
                    checked = receiveNotifications,
                    onCheckedChange = { receiveNotifications = it },
                    colors = SwitchDefaults.colors(checkedTrackColor = KayanRoyalBlue)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Light/Dark Theme Preference
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = KayanL10n.getString("app_theme", lang),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                    Text(
                        text = if (lang == Language.AR) "التبديل بين الوضع الليلي والنهاري" else "Switch between dark and light screens",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Switch(
                    checked = viewModel.isDarkMode.value,
                    onCheckedChange = { viewModel.toggleTheme() },
                    colors = SwitchDefaults.colors(checkedTrackColor = KayanRoyalBlue)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            KayanGradientButton(
                text = KayanL10n.getString("save_changes", lang),
                onClick = {
                    viewModel.navigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun NotificationsScreen(viewModel: KayanViewModel) {
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
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = KayanRoyalBlue)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = KayanL10n.getString("notifications", lang),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Notifications List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(viewModel.notifications) { notification ->
                    val title = if (lang == Language.AR) notification.titleAr else notification.titleEn
                    val content = if (lang == Language.AR) notification.contentAr else notification.contentEn
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (!notification.isRead) KayanRoyalBlue.copy(alpha = 0.05f) else Color.LightGray.copy(alpha = 0.15f))
                            .border(1.dp, KayanSilver.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(KayanRoyalBlue.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mail,
                                contentDescription = null,
                                tint = KayanRoyalBlue,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = KayanRoyalBlue)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = content, fontSize = 12.sp, color = Color.DarkGray)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = notification.time, fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HelpSupportScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value
    var supportTopic by remember { mutableStateOf("") }
    var supportDesc by remember { mutableStateOf("") }

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
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = KayanRoyalBlue)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = KayanL10n.getString("live_support", lang),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Quick live chat button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Brush.linearGradient(listOf(KayanRoyalBlue, KayanPepsiBlue)))
                    .clickable { viewModel.navigateTo(Screen.LiveChat) }
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Forum, contentDescription = null, tint = KayanGold, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (lang == Language.AR) "المحادثة الفورية المباشرة" else "Start Real-Time Live Chat",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (lang == Language.AR) "تحدث مع مشرف خدمة عملاء مخصص فوراً" else "Chat with our executive concierge desk now",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = if (lang == Language.AR) "تذكرة دعم جديدة" else "Submit Support Ticket",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = KayanRoyalBlue
            )

            Spacer(modifier = Modifier.height(12.dp))

            KayanTextField(
                value = supportTopic,
                onValueChange = { supportTopic = it },
                label = if (lang == Language.AR) "عنوان المشكلة / الموضوع" else "Problem Subject"
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = supportDesc,
                onValueChange = { supportDesc = it },
                label = { Text(if (lang == Language.AR) "اكتب تفاصيل مشكلتك هنا..." else "Describe problem details...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = KayanRoyalBlue, unfocusedBorderColor = KayanSilver)
            )

            Spacer(modifier = Modifier.height(16.dp))

            KayanGradientButton(
                text = if (lang == Language.AR) "إرسال التذكرة" else "Submit Ticket",
                onClick = {
                    supportTopic = ""
                    supportDesc = ""
                    viewModel.navigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun LiveChatScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value
    var inputMsg by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            // Chat Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = KayanRoyalBlue)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(KayanGold),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.SupportAgent, contentDescription = null, tint = KayanRoyalBlue)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (lang == Language.AR) "الدعم الذكي لكيان" else "Kayan Smart Support Desk",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = KayanRoyalBlue
                    )
                    Text(
                        text = KayanL10n.getString("active_now", lang),
                        fontSize = 11.sp,
                        color = KayanSuccess,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Divider(color = KayanSilver.copy(alpha = 0.4f))

            // Messages scroll area
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(viewModel.helpMessages) { message ->
                    val isMe = message.sender == "user"
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp,
                                        bottomStart = if (isMe) 16.dp else 4.dp,
                                        bottomEnd = if (isMe) 4.dp else 16.dp
                                    )
                                )
                                .background(if (isMe) KayanRoyalBlue else Color.LightGray.copy(alpha = 0.35f))
                                .border(
                                    1.dp,
                                    if (isMe) Color.Transparent else KayanSilver.copy(alpha = 0.5f),
                                    RoundedCornerShape(16.dp)
                                )
                                .padding(12.dp)
                                .widthIn(max = 260.dp)
                        ) {
                            Column {
                                Text(
                                    text = message.content,
                                    color = if (isMe) Color.White else Color.DarkGray,
                                    fontSize = 13.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = message.timestamp,
                                    color = if (isMe) Color.White.copy(alpha = 0.6f) else Color.Gray,
                                    fontSize = 9.sp,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }
                    }
                }
            }

            Divider(color = KayanSilver.copy(alpha = 0.4f))

            // Bottom Input Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputMsg,
                    onValueChange = { inputMsg = it },
                    placeholder = { Text(if (lang == Language.AR) "اكتب رسالة..." else "Write a message...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = KayanRoyalBlue,
                        unfocusedBorderColor = KayanSilver
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(10.dp))

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(KayanRoyalBlue, KayanPepsiBlue)))
                        .clickable {
                            if (inputMsg.isNotBlank()) {
                                viewModel.sendHelpMessage(inputMsg)
                                inputMsg = ""
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AppRatingScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value
    var ratingStars by remember { mutableStateOf(5) }
    var reviewText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    text = KayanL10n.getString("rate_app", lang),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            KayanLogo(tintColor = KayanRoyalBlue, glow = false)

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = if (lang == Language.AR) "كيف ترى تجربتك مع كيان؟" else "Rate your experience with Kayan",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = KayanRoyalBlue
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Star selector row
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                for (i in 1..5) {
                    Icon(
                        imageVector = if (i <= ratingStars) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = null,
                        tint = if (i <= ratingStars) KayanGold else Color.LightGray,
                        modifier = Modifier
                            .size(45.dp)
                            .clickable { ratingStars = i }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = reviewText,
                onValueChange = { reviewText = it },
                label = { Text(if (lang == Language.AR) "أضف تعليقاً إضافياً (اختياري)..." else "Write feedback (optional)...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = KayanRoyalBlue, unfocusedBorderColor = KayanSilver)
            )

            Spacer(modifier = Modifier.height(30.dp))

            KayanGradientButton(
                text = if (lang == Language.AR) "إرسال التقييم" else "Submit Rating",
                onClick = {
                    viewModel.navigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AboutKayanScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    text = KayanL10n.getString("about_app", lang),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = KayanRoyalBlue
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            KayanLogo(tintColor = KayanRoyalBlue, glow = false)

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = if (lang == Language.AR) {
                    "كيان هو السوبر أب الخليجي المتكامل والموثوق كلياً لتلبية كافة الاحتياجات اليومية والخدمية للمواطن والمقيم في دول مجلس التعاون الخليجي. يمنحك تجربة استخدام حصرية فائقة الفخامة ويدمج الخدمات المنزلية، التسوق الفاخر، والإعلانات المبوبة لتبسيط وتحسين نمط حياتك اليومية."
                } else {
                    "Kayan is the prestigious Gulf Super App, natively crafted to satisfy all premium service needs of citizens and residents across the GCC countries. Merging elite home services, luxury product shopping, and secure classified ads, Kayan delivers a truly majestic user experience."
                },
                fontSize = 15.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = if (lang == Language.AR) "النسخة 1.0.0 (تطوير تجريبي)" else "Version 1.0.0 (Gold Master)",
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
