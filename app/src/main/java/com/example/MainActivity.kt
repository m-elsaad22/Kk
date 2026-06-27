package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.models.Language
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.KayanViewModel
import com.example.viewmodel.Screen
import com.example.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val kViewModel: KayanViewModel = viewModel()
            
            MyApplicationTheme(darkTheme = kViewModel.isDarkMode.value) {
                // Handle RTL/LTR Layout Orientation dynamically based on active language setting
                val dir = if (kViewModel.currentLanguage.value == Language.AR) {
                    LayoutDirection.Rtl
                } else {
                    LayoutDirection.Ltr
                }
                
                CompositionLocalProvider(LocalLayoutDirection provides dir) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        KayanAppRouter(viewModel = kViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun KayanAppRouter(viewModel: KayanViewModel) {
    val activeScreen = viewModel.backStack.last()
    
    // Register physical device back press trigger to navigate previous stack items safely
    BackHandler(enabled = viewModel.backStack.size > 1) {
        viewModel.navigateBack()
    }

    when (activeScreen) {
        is Screen.Splash -> SplashScreen(viewModel = viewModel)
        is Screen.LanguageRegion -> LanguageRegionScreen(viewModel = viewModel)
        is Screen.Onboarding -> OnboardingScreen(viewModel = viewModel, step = activeScreen.step)
        is Screen.Login -> LoginScreen(viewModel = viewModel)
        is Screen.SignUp -> SignUpScreen(viewModel = viewModel)
        is Screen.OtpVerification -> OtpVerificationScreen(viewModel = viewModel)
        is Screen.ForgotPassword -> ForgotPasswordScreen(viewModel = viewModel)
        is Screen.NewPassword -> NewPasswordScreen(viewModel = viewModel, account = activeScreen.account)
        is Screen.Dashboard -> DashboardScreen(viewModel = viewModel)
        is Screen.Profile -> ProfileScreen(viewModel = viewModel)
        is Screen.Settings -> SettingsScreen(viewModel = viewModel)
        is Screen.Notifications -> NotificationsScreen(viewModel = viewModel)
        is Screen.MyBookings -> MyBookingsScreen(viewModel = viewModel)
        is Screen.HelpSupport -> HelpSupportScreen(viewModel = viewModel)
        is Screen.LiveChat -> LiveChatScreen(viewModel = viewModel)
        is Screen.AppRating -> AppRatingScreen(viewModel = viewModel)
        is Screen.AboutKayan -> AboutKayanScreen(viewModel = viewModel)
        
        // Home Services Section
        is Screen.ServicesHome -> ServicesHomeScreen(viewModel = viewModel)
        is Screen.ServiceDetail -> ServiceDetailScreen(viewModel = viewModel, serviceId = activeScreen.serviceId)
        is Screen.BookingDateTime -> BookingDateTimeScreen(viewModel = viewModel, serviceId = activeScreen.serviceId)
        is Screen.BookingAddress -> BookingAddressScreen(viewModel = viewModel, serviceId = activeScreen.serviceId, date = activeScreen.date, time = activeScreen.time)
        is Screen.BookingSummary -> BookingSummaryScreen(viewModel = viewModel, serviceId = activeScreen.serviceId, date = activeScreen.date, time = activeScreen.time, house = activeScreen.house, street = activeScreen.street, extra = activeScreen.extra)
        is Screen.BookingPayment -> BookingPaymentScreen(viewModel = viewModel, serviceId = activeScreen.serviceId, date = activeScreen.date, time = activeScreen.time, house = activeScreen.house, street = activeScreen.street, extra = activeScreen.extra, amount = activeScreen.amount)
        is Screen.BookingSuccess -> BookingSuccessScreen(viewModel = viewModel, bookingId = activeScreen.bookingId)
        is Screen.BookingTrack -> BookingTrackScreen(viewModel = viewModel, bookingId = activeScreen.bookingId)
        
        // Shop Section
        is Screen.ShopHome -> ShopHomeScreen(viewModel = viewModel)
        is Screen.ProductDetail -> ProductDetailScreen(viewModel = viewModel, productId = activeScreen.productId)
        is Screen.Cart -> CartScreen(viewModel = viewModel)
        is Screen.OrderSuccess -> OrderSuccessScreen(viewModel = viewModel)
        
        // Classifieds Section
        is Screen.ClassifiedsHome -> ClassifiedsHomeScreen(viewModel = viewModel)
        is Screen.ClassifiedsDetail -> ClassifiedsDetailScreen(viewModel = viewModel, adId = activeScreen.adId)
        is Screen.CreateAdStep1 -> CreateAdStep1Screen(viewModel = viewModel)
        is Screen.CreateAdStep2 -> CreateAdStep2Screen(viewModel = viewModel, title = activeScreen.title, price = activeScreen.price, category = activeScreen.category)
        is Screen.CreateAdStep3 -> CreateAdStep3Screen(viewModel = viewModel, title = activeScreen.title, price = activeScreen.price, category = activeScreen.category, desc = activeScreen.desc)
        is Screen.PublishSuccess -> PublishSuccessScreen(viewModel = viewModel)
        
        else -> DashboardScreen(viewModel = viewModel)
    }
}
