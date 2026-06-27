package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.models.*
import com.example.viewmodel.KayanViewModel
import com.example.viewmodel.Screen
import com.example.ui.components.*
import com.example.ui.theme.*
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(viewModel: KayanViewModel) {
    LaunchedEffect(Unit) {
        delay(2500)
        viewModel.navigateAndClear(Screen.LanguageRegion)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(listOf(KayanRoyalBlue, KayanPepsiBlue))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            KayanLogo(tintColor = Color.White, glow = true)
            Spacer(modifier = Modifier.height(30.dp))
            CircularProgressIndicator(
                color = KayanGold,
                strokeWidth = 3.dp,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun LanguageRegionScreen(viewModel: KayanViewModel) {
    var selectedLang by remember { mutableStateOf(viewModel.currentLanguage.value) }
    var selectedCountry by remember { mutableStateOf(viewModel.currentCountry.value) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            KayanLogo(tintColor = KayanRoyalBlue, glow = false)
            
            Spacer(modifier = Modifier.height(30.dp))
            
            Text(
                text = KayanL10n.getString("lang_choose", selectedLang),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = KayanRoyalBlue,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Language Switcher Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray.copy(alpha = 0.25f))
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (selectedLang == Language.AR) KayanRoyalBlue else Color.Transparent)
                        .clickable {
                            selectedLang = Language.AR
                            viewModel.currentLanguage.value = Language.AR
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "عربي",
                        color = if (selectedLang == Language.AR) Color.White else KayanRoyalBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (selectedLang == Language.EN) KayanRoyalBlue else Color.Transparent)
                        .clickable {
                            selectedLang = Language.EN
                            viewModel.currentLanguage.value = Language.EN
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "English",
                        color = if (selectedLang == Language.EN) Color.White else KayanRoyalBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = KayanL10n.getString("select_country", selectedLang),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Country Selector Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(COUNTRIES) { country ->
                    val isSelected = selectedCountry.code == country.code
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isSelected) KayanRoyalBlue.copy(alpha = 0.08f) else Color.Transparent)
                            .border(
                                1.5.dp,
                                if (isSelected) KayanRoyalBlue else KayanSilver.copy(alpha = 0.5f),
                                RoundedCornerShape(14.dp)
                            )
                            .clickable {
                                selectedCountry = country
                                viewModel.setCountry(country)
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = country.flag, fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = KayanL10n.getString(country.nameKey, selectedLang),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isSelected) KayanRoyalBlue else Color.DarkGray
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = KayanRoyalBlue
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            KayanGradientButton(
                text = KayanL10n.getString("get_started", selectedLang),
                onClick = {
                    viewModel.navigateTo(Screen.Onboarding(1))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun OnboardingScreen(viewModel: KayanViewModel, step: Int) {
    val lang = viewModel.currentLanguage.value

    val title = when (step) {
        1 -> KayanL10n.getString("onboard_1_title", lang)
        2 -> KayanL10n.getString("onboard_2_title", lang)
        else -> KayanL10n.getString("onboard_3_title", lang)
    }

    val desc = when (step) {
        1 -> KayanL10n.getString("onboard_1_desc", lang)
        2 -> KayanL10n.getString("onboard_2_desc", lang)
        else -> KayanL10n.getString("onboard_3_desc", lang)
    }

    val icon = when (step) {
        1 -> Icons.Default.HomeRepairService
        2 -> Icons.Default.ShoppingBag
        else -> Icons.Default.Campaign
    }

    val iconColor = when (step) {
        1 -> KayanSkyBlue
        2 -> KayanRoyalBlue
        else -> KayanTurquoise
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Skip Button
        Text(
            text = KayanL10n.getString("skip", lang),
            color = KayanRoyalBlue,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 50.dp, end = 24.dp)
                .clickable {
                    viewModel.navigateAndClear(Screen.Login)
                }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Visual Banner Illustration Circle
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.12f))
                    .border(2.dp, Brush.linearGradient(listOf(iconColor, KayanSilver)), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(85.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = KayanRoyalBlue,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = desc,
                fontSize = 15.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Carousel Progress Dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (i in 1..3) {
                    Box(
                        modifier = Modifier
                            .size(width = if (i == step) 24.dp else 8.dp, height = 8.dp)
                            .clip(CircleShape)
                            .background(if (i == step) KayanRoyalBlue else Color.LightGray)
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            KayanGradientButton(
                text = KayanL10n.getString("next", lang),
                onClick = {
                    if (step < 3) {
                        viewModel.navigateTo(Screen.Onboarding(step + 1))
                    } else {
                        viewModel.navigateAndClear(Screen.Login)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun LoginScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value
    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Skip / Continue as Guest
        Text(
            text = KayanL10n.getString("skip", lang),
            color = KayanRoyalBlue,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 50.dp, end = 24.dp)
                .clickable {
                    viewModel.isLoggedIn.value = false
                    viewModel.navigateAndClear(Screen.Dashboard)
                }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            KayanLogo(tintColor = KayanRoyalBlue, glow = false)
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = KayanL10n.getString("login", lang),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = KayanRoyalBlue
            )

            Spacer(modifier = Modifier.height(20.dp))

            KayanTextField(
                value = emailOrPhone,
                onValueChange = { emailOrPhone = it },
                label = KayanL10n.getString("email_or_phone", lang),
                leadingIcon = Icons.Default.Person
            )

            Spacer(modifier = Modifier.height(12.dp))

            KayanTextField(
                value = password,
                onValueChange = { password = it },
                label = KayanL10n.getString("password", lang),
                leadingIcon = Icons.Default.Lock,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(checkedColor = KayanRoyalBlue)
                    )
                    Text(
                        text = KayanL10n.getString("remember_me", lang),
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }
                
                Text(
                    text = KayanL10n.getString("forgot_password", lang),
                    color = KayanRoyalBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable {
                        viewModel.navigateTo(Screen.ForgotPassword)
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            KayanGradientButton(
                text = KayanL10n.getString("login", lang),
                onClick = {
                    viewModel.isLoggedIn.value = true
                    viewModel.navigateAndClear(Screen.Dashboard)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = KayanL10n.getString("or_continue_with", lang),
                color = Color.Gray,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Social Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialIconCircle(icon = Icons.Default.PhoneAndroid) {
                    viewModel.isLoggedIn.value = true
                    viewModel.navigateAndClear(Screen.Dashboard)
                }
                SocialIconCircle(icon = Icons.Default.AccountBalanceWallet) {
                    viewModel.isLoggedIn.value = true
                    viewModel.navigateAndClear(Screen.Dashboard)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = KayanL10n.getString("dont_have_account", lang),
                color = KayanRoyalBlue,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    viewModel.navigateTo(Screen.SignUp)
                }
            )
        }
    }
}

@Composable
fun SocialIconCircle(icon: ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(54.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(1.dp, KayanSilver, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = KayanRoyalBlue,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun SignUpScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value
    var fullname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var agreeTerms by remember { mutableStateOf(false) }

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
            KayanLogo(tintColor = KayanRoyalBlue, glow = false)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = KayanL10n.getString("signup", lang),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = KayanRoyalBlue
            )

            Spacer(modifier = Modifier.height(20.dp))

            KayanTextField(
                value = fullname,
                onValueChange = { fullname = it },
                label = KayanL10n.getString("fullname", lang),
                leadingIcon = Icons.Default.Person
            )

            Spacer(modifier = Modifier.height(10.dp))

            KayanTextField(
                value = email,
                onValueChange = { email = it },
                label = KayanL10n.getString("email", lang),
                leadingIcon = Icons.Default.Email
            )

            Spacer(modifier = Modifier.height(10.dp))

            KayanTextField(
                value = phone,
                onValueChange = { phone = it },
                label = KayanL10n.getString("phone", lang),
                leadingIcon = Icons.Default.Phone
            )

            Spacer(modifier = Modifier.height(10.dp))

            KayanTextField(
                value = password,
                onValueChange = { password = it },
                label = KayanL10n.getString("password", lang),
                leadingIcon = Icons.Default.Lock,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(10.dp))

            KayanTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = KayanL10n.getString("confirm_password", lang),
                leadingIcon = Icons.Default.Lock,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = agreeTerms,
                    onCheckedChange = { agreeTerms = it },
                    colors = CheckboxDefaults.colors(checkedColor = KayanRoyalBlue)
                )
                Text(
                    text = KayanL10n.getString("agree_terms", lang),
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            KayanGradientButton(
                text = KayanL10n.getString("signup", lang),
                onClick = {
                    viewModel.navigateTo(Screen.OtpVerification)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = KayanL10n.getString("have_account", lang),
                color = KayanRoyalBlue,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    viewModel.navigateBack()
                }
            )
        }
    }
}

@Composable
fun OtpVerificationScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value
    var otpDigits = remember { mutableStateListOf("", "", "", "", "") }
    var timerSeconds by remember { mutableStateOf(60) }

    LaunchedEffect(Unit) {
        while (timerSeconds > 0) {
            delay(1000)
            timerSeconds -= 1
        }
    }

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
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(KayanRoyalBlue.copy(alpha = 0.08f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PhoneAndroid,
                    contentDescription = null,
                    tint = KayanRoyalBlue,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = KayanL10n.getString("otp_verify", lang),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = KayanRoyalBlue
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = KayanL10n.getString("otp_desc", lang),
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 5 Digit Inputs Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 0..4) {
                    OutlinedTextField(
                        value = otpDigits[i],
                        onValueChange = {
                            if (it.length <= 1) {
                                otpDigits[i] = it
                            }
                        },
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = KayanRoyalBlue
                        ),
                        modifier = Modifier
                            .size(54.dp)
                            .border(1.5.dp, KayanRoyalBlue, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            KayanGradientButton(
                text = KayanL10n.getString("verify", lang),
                onClick = {
                    viewModel.isLoggedIn.value = true
                    viewModel.navigateAndClear(Screen.Dashboard)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = KayanL10n.getString("resend", lang) + " ",
                    fontSize = 14.sp,
                    color = if (timerSeconds == 0) KayanRoyalBlue else Color.Gray,
                    fontWeight = if (timerSeconds == 0) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.clickable(enabled = timerSeconds == 0) {
                        timerSeconds = 60
                    }
                )
                if (timerSeconds > 0) {
                    Text(
                        text = "(${timerSeconds}s)",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun ForgotPasswordScreen(viewModel: KayanViewModel) {
    val lang = viewModel.currentLanguage.value
    var accountInput by remember { mutableStateOf("") }

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
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(KayanRoyalBlue.copy(alpha = 0.08f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LockReset,
                    contentDescription = null,
                    tint = KayanRoyalBlue,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = KayanL10n.getString("forgot_title", lang),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = KayanRoyalBlue
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = KayanL10n.getString("forgot_desc", lang),
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            KayanTextField(
                value = accountInput,
                onValueChange = { accountInput = it },
                label = KayanL10n.getString("email_or_phone", lang),
                leadingIcon = Icons.Default.Email
            )

            Spacer(modifier = Modifier.height(24.dp))

            KayanGradientButton(
                text = KayanL10n.getString("send_otp", lang),
                onClick = {
                    viewModel.navigateTo(Screen.NewPassword(accountInput))
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = KayanL10n.getString("login", lang),
                color = KayanRoyalBlue,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    viewModel.navigateBack()
                }
            )
        }
    }
}

@Composable
fun NewPasswordScreen(viewModel: KayanViewModel, account: String) {
    val lang = viewModel.currentLanguage.value
    var pass by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }

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
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(KayanRoyalBlue.copy(alpha = 0.08f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.VpnKey,
                    contentDescription = null,
                    tint = KayanRoyalBlue,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = KayanL10n.getString("new_password", lang),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = KayanRoyalBlue
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = KayanL10n.getString("new_password_desc", lang),
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            KayanTextField(
                value = pass,
                onValueChange = { pass = it },
                label = KayanL10n.getString("password", lang),
                leadingIcon = Icons.Default.Lock,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(12.dp))

            KayanTextField(
                value = confirmPass,
                onValueChange = { confirmPass = it },
                label = KayanL10n.getString("confirm_password", lang),
                leadingIcon = Icons.Default.Lock,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Strength Indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isStrong = pass.length >= 8
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(if (pass.isNotEmpty()) KayanError else Color.LightGray)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(if (pass.length > 5) KayanWarning else Color.LightGray)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(if (isStrong) KayanSuccess else Color.LightGray)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            KayanGradientButton(
                text = KayanL10n.getString("save", lang),
                onClick = {
                    viewModel.isLoggedIn.value = true
                    viewModel.navigateAndClear(Screen.Dashboard)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
