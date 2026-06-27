package com.example.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

sealed class Screen {
    object Splash : Screen()
    object LanguageRegion : Screen()
    data class Onboarding(val step: Int) : Screen()
    object Login : Screen()
    object SignUp : Screen()
    object OtpVerification : Screen()
    object ForgotPassword : Screen()
    data class NewPassword(val account: String) : Screen()
    object Dashboard : Screen()
    object Profile : Screen()
    object Settings : Screen()
    object Notifications : Screen()
    object MyBookings : Screen()
    object Wishlist : Screen()
    object HelpSupport : Screen()
    object LiveChat : Screen()
    object AppRating : Screen()
    object AboutKayan : Screen()
    
    // Services
    object ServicesHome : Screen()
    object ServicesSearch : Screen()
    data class ServiceDetail(val serviceId: Int) : Screen()
    data class BookingDateTime(val serviceId: Int) : Screen()
    data class BookingAddress(val serviceId: Int, val date: String, val time: String) : Screen()
    data class BookingSummary(val serviceId: Int, val date: String, val time: String, val house: String, val street: String, val extra: String) : Screen()
    data class BookingPayment(val serviceId: Int, val date: String, val time: String, val house: String, val street: String, val extra: String, val amount: Double) : Screen()
    data class BookingSuccess(val bookingId: String) : Screen()
    data class BookingTrack(val bookingId: String) : Screen()
    
    // Shop
    object ShopHome : Screen()
    object ShopSearch : Screen()
    data class ProductDetail(val productId: Int) : Screen()
    object Cart : Screen()
    object ChooseDeliveryAddress : Screen()
    object ChoosePayment : Screen()
    object OrderSuccess : Screen()
    data class OrderTrack(val orderId: String) : Screen()
    
    // Classifieds
    object ClassifiedsHome : Screen()
    object ClassifiedsSearch : Screen()
    data class ClassifiedsDetail(val adId: Int) : Screen()
    object CreateAdStep1 : Screen()
    data class CreateAdStep2(val title: String, val price: Double, val category: String) : Screen()
    data class CreateAdStep3(val title: String, val price: Double, val category: String, val desc: String) : Screen()
    object PublishSuccess : Screen()
}

class KayanViewModel : ViewModel() {
    
    // Navigation State
    val backStack = mutableStateListOf<Screen>(Screen.Splash)
    
    fun navigateTo(screen: Screen) {
        backStack.add(screen)
    }
    
    fun navigateBack() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
        }
    }
    
    fun navigateAndClear(screen: Screen) {
        backStack.clear()
        backStack.add(screen)
    }

    // App Preferences & Theme State
    val currentLanguage = mutableStateOf(Language.AR)
    val isDarkMode = mutableStateOf(false)
    val currentCountry = mutableStateOf(COUNTRIES[0]) // Saudi Arabia default

    // User State
    val isLoggedIn = mutableStateOf(false)
    val loggedInUser = mutableStateOf("عبدالعزيز آل سعود")
    val userEmail = mutableStateOf("abdullah.gulf@kayan.sa")
    val userPhone = mutableStateOf("+966 50 123 4567")
    val walletBalance = mutableStateOf(2450.50)
    val pointsBalance = mutableStateOf(1280)
    val totalSavings = mutableStateOf(350.0)

    // Pre-populated Kayan Services
    val services = listOf(
        ServiceItem(1, "تنظيف فيلا كاملة فاخر", "Full Luxury Villa Cleaning", "clean_cat", 450.0, 0xFF00B4D8, 4.9f, "تنظيف عميق فاخر لجميع غرف الفيلا، غسيل السجاد، تعقيم الحمامات والمطابخ بأحدث الأجهزة الألمانية والمنظفات الصديقة للبيئة.", "Deep luxury villa cleaning including carpet washing, kitchen & bathroom sanitization using German equipment."),
        ServiceItem(2, "صيانة مكيف سبليت متكاملة", "Full Split AC Maintenance", "ac_cat", 150.0, 0xFF00A8A8, 4.8f, "غسيل الوحدة الخارجية والداخلية بمضخة مياه قوية، فحص الفريون، وتنظيف الفلاتر ومجرى الصرف لضمان هواء صحي بارد.", "Outer/inner unit washing, freon level checks, filter cleaning for chilled healthy air flow."),
        ServiceItem(3, "إصلاح أعطال كهرباء ودوائر", "Electrical Breakdown Repair", "electricity_cat", 99.0, 0xFF0A2B5E, 4.7f, "تشخيص فوري وإصلاح القواطع الكهربائية، الإنارة المتضررة، الأفياش والدوائر القصيرة مع ضمان فني كامل.", "Immediate diagnosis & fixing of fuses, lighting, sockets, short circuits with professional warranty."),
        ServiceItem(4, "تسليك سباكة ومعالجة تسريبات", "Plumbing Leakage Fixing", "plumbing_cat", 120.0, 0xFF0033A0, 4.6f, "حل انسداد المواسير، معالجة تسريبات المياه المخفية، تبديل الخلاطات والمحابس التالفة بأجهزة الكشف الحديثة.", "Unblocking pipes, fixing concealed water leaks, replacing damaged taps and valves."),
        ServiceItem(5, "رش مبيدات ومكافحة حشرات شامل", "Ultimate Pest Control Service", "pest_cat", 180.0, 0xFFC0C0C0, 4.9f, "إبادة شاملة للرواسب، الحشرات الزاحفة والطائرة، والنمل الأبيض مع مواد آمنة كلياً على الأطفال والحيوانات الأليفة وضمان سنة.", "Complete elimination of crawling & flying insects using organic child-safe materials with 1-year warranty."),
        ServiceItem(6, "تركيب كاميرات مراقبة وأمن ذكي", "Smart Camera & Security Install", "security_cat", 299.0, 0xFFF4D03F, 4.8f, "تركيب وضبط كاميرات مراقبة خارجية/داخلية عالية الدقة 4K مع إمكانية المراقبة المباشرة عبر الجوال الذكي.", "Installation of 4K smart security cameras connected directly to your mobile app.")
    )

    // Certified Technicians (from Kayan directly)
    val technicians = listOf(
        Technician(1, "م. طارق الحربي", 4.9f, 8, "ac_cat"),
        Technician(2, "م. سليم منصور", 4.8f, 6, "clean_cat"),
        Technician(3, "م. أحمد الرويلي", 4.7f, 10, "electricity_cat"),
        Technician(4, "م. فؤاد الزهراني", 4.9f, 5, "plumbing_cat"),
        Technician(5, "م. ياسر القحطاني", 4.8f, 7, "pest_cat"),
        Technician(6, "م. بندر الشمري", 4.9f, 9, "security_cat")
    )

    // Pre-populated Shopping Products
    val products = listOf(
        Product(1, "أبل آيفون 15 برو ماكس - 512 جيجا - تيتانيوم", "Apple iPhone 15 Pro Max - 512GB - Titanium", 5399.0, 4.9f, 4999.0, "Apple", "الإصدار الأقوى والأحدث من آيفون بهيكل متين من التيتانيوم وشريحة A17 برو الخارقة وكاميرات سينمائية.", "The ultimate titanium smartphone featuring A17 Pro chip and cine-camera system.", "Electronics", 240),
        Product(2, "سامسونج جالكسي S24 ألترا - 512 جيجا", "Samsung Galaxy S24 Ultra - 512GB", 4899.0, 4.8f, 4499.0, "Samsung", "جوال الذكاء الاصطناعي الأقوى مع قلم S-Pen مدمج وشاشة مسطحة مذهلة وكاميرا بدقة 200 ميجابكسل.", "The absolute AI powerhouse with integrated S-Pen and astonishing 200MP camera.", "Electronics", 185),
        Product(3, "أبل آيباد برو M4 - 11 بوصة - شاشة أوليد", "Apple iPad Pro M4 - 11 Inch - OLED", 4199.0, 4.9f, null, "Apple", "الآيباد الأرقى والأنحف على الإطلاق، مدعوم بمعالج M4 الثوري وشاشة ريتنا فائقة التباين والعمق.", "The thinnest, most powerful iPad powered by revolutionary M4 chip and OLED display.", "Electronics", 65),
        Product(4, "سماعة سوني WH-1000XM5 العازلة للضوضاء", "Sony WH-1000XM5 Noise Canceling Headphones", 1299.0, 4.7f, 1149.0, "Sony", "أفضل تجربة عزل ضوضاء في العالم مع صوت نقي غامر وعمر بطارية يصل إلى 30 ساعة متواصلة.", "World-class noise cancellation headphones with breathtaking sound and 30-hour battery.", "Electronics", 312),
        Product(5, "بخور عود أزرق سيوفي فاخر ملكي", "Royal Seufi Blue Oud Premium", 550.0, 4.9f, 450.0, "Al-Qurashi", "كسرات بخور عود طبيعي فاخر جداً ذو رائحة ترابية زكية تدوم لعدة أيام في المجالس والمنازل الخليجية.", "Luxury natural blue oud chips with long-lasting warm scent perfect for prestige gatherings.", "Luxury", 120),
        Product(6, "دلة العرب الفاخرة لتحضير القهوة السعودية", "Dallah Al-Arab Luxury Saudi Coffee Maker", 420.0, 4.8f, 380.0, "Dallah", "دلة كهربائية ذكية ممتازة لتحضير القهوة العربية والخليجية التقليدية بضغطة زر وبطابع تراثي فاخر.", "Smart electric Dallah for brewing authentic traditional Saudi coffee automatically.", "Home", 98)
    )

    // Pre-populated Classified Advertisements
    val classifiedAds = mutableStateListOf(
        ClassifiedAd(1, "مرسيدس بنز G63 AMG موديل 2024 - أصفار", "Mercedes Benz G63 AMG 2024 - Brand New", 850000.0, "الرياض - الياسمين", "Riyadh - Al-Yasmin", "خالد العتيبي", "+966 55 555 1122", 4.9f, "للبيع مرسيدس جي كلاس 63 كامل المواصفات، لون أسود مطفي مع داخلية جلد أحمر ديزاينو، فل كربون فايبر، شاشات خلفية، أصفار وارد الجفالي وضمان ممتد.", "Mercedes G-Class 63 AMG, Matte Black exterior with Designo Red leather interior, carbon fiber trims, zero kilometers, full warranty.", 1240, true, "Motors", "2026-06-25"),
        ClassifiedAd(2, "فيلا فاخرة للبيع في حي حطين الراقي", "Luxury Modern Villa for Sale in Hittin", 4800000.0, "الرياض - حطين", "Riyadh - Hittin", "مؤسسة القمة العقارية", "+966 50 111 2222", 4.8f, "فيلا مودرن ذكية تشطيب فاخر جداً، مساحة 450م، مسبح، تكييف مخفي، مصعد، شلالات مائية، صالات مفتوحة و5 أجنحة نوم ماستر مذهلة.", "Modern smart villa, 450 sqm with private pool, elevator, central AC, waterfalls, open salons and 5 master suites.", 850, true, "Real Estate", "2026-06-24"),
        ClassifiedAd(3, "ساعة رولكس صبمارينر ذهب وأزرق", "Rolex Submariner Gold & Blue Dial", 72000.0, "جدة - الروضة", "Jeddah - Al-Rawdah", "فيصل السديري", "+966 53 112 3344", 4.9f, "ساعة رولكس أصلية صبمارينر (دات) ذهب أصفر 18 قيراط مع ستيل، ميناء أزرق، موديل 2023 كامل الملحقات العلبة والكتالوج وضمان الوكيل.", "Authentic Rolex Submariner Gold/Steel Blue Dial, 2023 edition, with official box, booklets and international warranty.", 420, true, "Watches", "2026-06-26"),
        ClassifiedAd(4, "جت سكي سي دو GTX 300 ليميتد", "Sea-Doo GTX 300 Limited Jet Ski", 65000.0, "الخبر - أمواج", "Khobar - Amwaj", "عمر الغامدي", "+966 54 999 8888", 4.6f, "جت سكي بحالة الوكالة، موديل 2022، قاطع 35 ساعة فقط، صيانة دورية في الوكالة، شامل المقطورة والغطاء الأصلي.", "Premium Sea-Doo Jet Ski GTX 300 Limited, 2022 model, 35 running hours, with official trailer and cover.", 310, false, "Boats", "2026-06-23")
    )

    // User Interactive State Collections
    val cart = mutableStateListOf<CartItem>()
    val wishlistServices = mutableStateListOf<ServiceItem>()
    val wishlistProducts = mutableStateListOf<Product>()
    val wishlistAds = mutableStateListOf<ClassifiedAd>()
    val bookings = mutableStateListOf<Booking>()
    
    // Help Live Chat Simulator Messages
    val helpMessages = mutableStateListOf(
        KayanMessage("m1", "agent", "مرحباً بك في خدمة عملاء كيان الفاخرة! كيف يمكننا خدمتك اليوم؟", "18:00"),
        KayanMessage("m2", "agent", "Welcome to Kayan Luxury Support! How can we assist you today?", "18:00")
    )

    // General Notifications
    val notifications = mutableStateListOf(
        KayanNotification("n1", "تم قبول طلبك الفني!", "Service Request Approved!", "تم إسناد الفني م. طارق الحربي لصيانة التكييف بنجاح.", "Technician Eng. Tariq Al-Harbi has been assigned to your AC.", "قبل 5 دقائق", false),
        KayanNotification("n2", "رصيد نقاط إضافي مجاني", "Bonus Points Reward", "مبروك! حصلت على 200 نقطة إضافية لإتمامك حجزاً فاخراً.", "Congrats! You earned 200 points for completing your last service booking.", "قبل ساعة", false),
        KayanNotification("n3", "خصومات فلاش الكبرى بدأت!", "Mega Flash Sale is Live!", "خصم حتى 30% على المنتجات الفاخرة والإلكترونيات لساعات محدودة.", "Up to 30% OFF luxury items and electronics for a limited time.", "قبل 3 ساعات", true)
    )

    init {
        // Prepopulate Wishlists with some items so they aren't empty
        wishlistServices.add(services[0])
        wishlistProducts.add(products[4])
        wishlistAds.add(classifiedAds[0])

        // Prepopulate active booking to track
        bookings.add(
            Booking(
                id = "K-9524",
                serviceItem = services[1], // AC Maintenance
                date = "2026-06-28",
                time = "10:00 AM",
                address = "الرياض، حي حطين، رقم 12",
                totalAmount = 172.50,
                status = BookingStatus.SCHEDULED,
                technician = technicians[0] // Tariq
            )
        )
    }

    // Interactive Actions

    fun toggleLanguage() {
        currentLanguage.value = if (currentLanguage.value == Language.AR) Language.EN else Language.AR
    }

    fun toggleTheme() {
        isDarkMode.value = !isDarkMode.value
    }

    fun setCountry(country: Country) {
        currentCountry.value = country
    }

    // Cart Logic
    fun addToCart(product: Product) {
        val existing = cart.find { it.product.id == product.id }
        if (existing != null) {
            existing.quantity += 1
            // Trigger recomposition by removing and re-adding
            val index = cart.indexOf(existing)
            cart.removeAt(index)
            cart.add(index, existing)
        } else {
            cart.add(CartItem(product, 1))
        }
    }

    fun removeFromCart(product: Product) {
        val existing = cart.find { it.product.id == product.id }
        if (existing != null) {
            if (existing.quantity > 1) {
                existing.quantity -= 1
                val index = cart.indexOf(existing)
                cart.removeAt(index)
                cart.add(index, existing)
            } else {
                cart.remove(existing)
            }
        }
    }

    fun clearCart() {
        cart.clear()
    }

    fun getCartTotal(): Double {
        return cart.sumOf { (it.product.discountPrice ?: it.product.price) * it.quantity }
    }

    // Wishlist Logic
    fun toggleServiceWishlist(service: ServiceItem) {
        if (wishlistServices.contains(service)) {
            wishlistServices.remove(service)
        } else {
            wishlistServices.add(service)
        }
    }

    fun toggleProductWishlist(product: Product) {
        if (wishlistProducts.contains(product)) {
            wishlistProducts.remove(product)
        } else {
            wishlistProducts.add(product)
        }
    }

    fun toggleAdWishlist(ad: ClassifiedAd) {
        if (wishlistAds.contains(ad)) {
            wishlistAds.remove(ad)
        } else {
            wishlistAds.add(ad)
        }
    }

    // Booking Logic
    fun createBooking(serviceId: Int, date: String, time: String, house: String, street: String, extra: String, total: Double): String {
        val service = services.find { it.id == serviceId } ?: services[0]
        // Assign first tech of that specialty or a random tech
        val tech = technicians.find { it.specialtyKey == service.categoryKey } ?: technicians[0]
        val bookingId = "K-${(1000..9999).random()}"
        
        val newBooking = Booking(
            id = bookingId,
            serviceItem = service,
            date = date,
            time = time,
            address = "$street, $house ($extra)",
            totalAmount = total,
            status = BookingStatus.SCHEDULED,
            technician = tech
        )
        bookings.add(newBooking)
        
        // Subtract wallet balance if paying with wallet
        if (walletBalance.value >= total) {
            walletBalance.value -= total
            pointsBalance.value += (total * 0.1).toInt() // 10% cash back in points
        }
        
        // Trigger automated simulation of technician dispatching
        simulateTechnicianDispatch(bookingId)
        
        return bookingId
    }

    private fun simulateTechnicianDispatch(bookingId: String) {
        viewModelScope.launch {
            // Keep delaying to transition through states OTW -> ARRIVED -> WORKING -> DONE
            delay(15000) // 15 seconds for simulation speed
            updateBookingStatus(bookingId, BookingStatus.DISPATCHED)
            addSimulatedMessage("tech", "أهلاً بك عميلي الكريم، أنا فني كيان المعتمد ${technicians[0].name}. أنا في طريقي لموقعك الآن.")
            
            delay(15000)
            updateBookingStatus(bookingId, BookingStatus.ARRIVED)
            addSimulatedMessage("tech", "لقد وصلت لموقع الخدمة الآن، يرجى تزويدي برمز الحضور QR الظاهر في هاتفك لبدء العمل.")
            
            delay(15000)
            updateBookingStatus(bookingId, BookingStatus.WORKING)
            addSimulatedMessage("tech", "شكراً لك، تم التحقق من الكود وبدأت العمل على الخدمة بأعلى جودة.")
            
            delay(20000)
            updateBookingStatus(bookingId, BookingStatus.DONE)
            addSimulatedMessage("tech", "الحمد لله، تم إتمام العمل المطلوب بالكامل. يسعدني جداً تقييمك للخدمة عبر التطبيق.")
        }
    }

    private fun updateBookingStatus(bookingId: String, status: BookingStatus) {
        val bookingIndex = bookings.indexOfFirst { it.id == bookingId }
        if (bookingIndex != -1) {
            val updated = bookings[bookingIndex].copy(status = status)
            bookings[bookingIndex] = updated
        }
    }

    // Help Support Live Chat simulator
    fun sendHelpMessage(text: String) {
        if (text.isBlank()) return
        helpMessages.add(KayanMessage(UUID.randomUUID().toString(), "user", text, "18:05"))
        
        // Automated trigger for instant realistic agent replies
        viewModelScope.launch {
            delay(1500)
            val lower = text.lowercase()
            val reply = if (currentLanguage.value == Language.AR) {
                when {
                    lower.contains("تأخير") || lower.contains("الفني") -> "أهلاً بك، تم تعيين الفني وهو ملتزم بالموعد تماماً. يمكنك تتبع موقعه فوراً من شاشة 'طلباتي'."
                    lower.contains("استرجاع") || lower.contains("شحن") -> "رصيد الإرجاع يتم إيداعه فورياً في محفظة كيان الخاصة بك، ويمكنك استخدامه أو سحبه لحسابك البنكي."
                    lower.contains("خصم") || lower.contains("كوبون") -> "نعم! يمكنك استخدام كوبون الخصم الجديد 'KAYAN30' للحصول على خصم 30% فوري على أي خدمة منزلية!"
                    else -> "شكراً لتواصلك مع كيان الفاخرة. تم تحويل استفسارك لمشرف علاقات العملاء وسيتصل بك خلال دقائق."
                }
            } else {
                when {
                    lower.contains("delay") || lower.contains("technician") || lower.contains("driver") -> "Hello! Your Kayan technician is committed to the schedule. You can track their real-time location under 'My Bookings'."
                    lower.contains("refund") || lower.contains("wallet") -> "Refunds are processed instantly to your Kayan Wallet, ready to be reused or withdrawn to your bank."
                    lower.contains("discount") || lower.contains("promo") -> "Yes! Use coupon 'KAYAN30' to get an instant 30% discount on any home service."
                    else -> "Thank you for contacting Kayan Support. We have routed your request to a live relationship executive who will reply shortly."
                }
            }
            helpMessages.add(KayanMessage(UUID.randomUUID().toString(), "agent", reply, "18:05"))
        }
    }

    private fun addSimulatedMessage(sender: String, text: String) {
        helpMessages.add(KayanMessage(UUID.randomUUID().toString(), sender, text, "18:06"))
    }

    // Post Ad Wizard
    fun publishClassifiedAd(title: String, price: Double, category: String, desc: String, isPremium: Boolean) {
        val newId = (classifiedAds.maxOfOrNull { it.id } ?: 0) + 1
        val titleAr = if (currentLanguage.value == Language.AR) title else "إعلان: $title"
        val titleEn = if (currentLanguage.value == Language.EN) title else "Ad: $title"
        
        val ad = ClassifiedAd(
            id = newId,
            titleAr = titleAr,
            titleEn = titleEn,
            price = price,
            locationAr = "الرياض - السليمانية",
            locationEn = "Riyadh - Al-Sulaimaniyah",
            sellerName = loggedInUser.value,
            sellerPhone = userPhone.value,
            sellerRating = 5.0f,
            descriptionAr = desc,
            descriptionEn = desc,
            views = 1,
            isPremium = isPremium,
            category = category,
            datePosted = "2026-06-26"
        )
        classifiedAds.add(0, ad) // Prepend for latest
    }
}
