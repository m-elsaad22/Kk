package com.example.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class Language {
    AR, EN
}

// Localized String Dictionary
object KayanL10n {
    fun getString(key: String, lang: Language): String {
        val ar = mapOf(
            "app_title" to "كيان - السوبر أب الخليجي",
            "tagline" to "تطبيقك الخليجي الفاخر والمتكامل لكل الاحتياجات",
            "lang_choose" to "اختر لغتك ودولتك",
            "get_started" to "ابدأ الاستخدام",
            "select_country" to "اختر الدولة",
            "saudi" to "المملكة العربية السعودية",
            "uae" to "الإمارات العربية المتحدة",
            "qatar" to "دولة قطر",
            "kuwait" to "دولة الكويت",
            "bahrain" to "مملكة البحرين",
            "oman" to "سلطنة عمان",
            "egypt" to "جمهورية مصر العربية",
            
            // Onboarding
            "onboard_1_title" to "مرحباً بك في كيان",
            "onboard_1_desc" to "السوبر أب الخليجي الأول لخدماتك اليومية بتصميم استثنائي",
            "onboard_2_title" to "كل ما تحتاجه في مكان واحد",
            "onboard_2_desc" to "خدمات منزلية معتمدة، تسوق متكامل، وإعلانات مبوبة موثوقة",
            "onboard_3_title" to "جودة وضمان فائق",
            "onboard_3_desc" to "فنيون محترفون، دفع آمن، وخصومات وعروض يومية متجددة",
            "next" to "التالي",
            "skip" to "تخطي",
            
            // Auth
            "login" to "تسجيل الدخول",
            "signup" to "إنشاء حساب جديد",
            "email_or_phone" to "البريد الإلكتروني أو رقم الجوال",
            "password" to "كلمة المرور",
            "remember_me" to "تذكرني",
            "forgot_password" to "نسيت كلمة المرور؟",
            "or_continue_with" to "أو تابع باستخدام",
            "dont_have_account" to "ليس لديك حساب؟ سجل الآن",
            "have_account" to "لديك حساب بالفعل؟ سجل دخولك",
            "fullname" to "الاسم الكامل",
            "email" to "البريد الإلكتروني",
            "phone" to "رقم الجوال",
            "confirm_password" to "تأكيد كلمة المرور",
            "agree_terms" to "أوافق على الشروط والأحكام الخاصة بكيان",
            "otp_verify" to "رمز التحقق OTP",
            "otp_desc" to "أدخل رمز التحقق المكون من 5 أرقام المرسل إلى هاتفك",
            "verify" to "تحقق",
            "resend" to "إعادة الإرسال",
            "forgot_title" to "استعادة كلمة المرور",
            "forgot_desc" to "أدخل بريدك الإلكتروني أو رقم جوالك لإرسال رمز استعادة الحساب",
            "send_otp" to "إرسال رمز التحقق",
            "new_password" to "كلمة مرور جديدة",
            "new_password_desc" to "أنشئ كلمة مرور قوية لتأمين حسابك الفاخر",
            "save" to "حفظ وتغيير",
            
            // Dashboard
            "welcome" to "أهلاً بك يا",
            "guest" to "زائرنا الكريم",
            "wallet_balance" to "رصيد المحفظة",
            "kayan_points" to "نقاط كيان",
            "savings" to "إجمالي التوفير",
            "sar" to "ريال",
            "point_unit" to "نقطة",
            "domestic_services" to "خدمات منزلية",
            "domestic_desc" to "تنظيف، صيانة، كهرباء، سباكة وتكييف مع ضمان سنة كاملة",
            "discover" to "اكتشف الخدمات",
            "shopping_store" to "متجر التسوق",
            "shopping_desc" to "المنتجات الأكثر مبيعاً وأحدث الأجهزة الفاخرة",
            "shop_now" to "تسوق الآن",
            "classified_ads" to "إعلانات مبوبة",
            "classified_desc" to "بيع وشراء السيارات، العقارات، والأجهزة والمنتجات المميزة",
            "browse_ads" to "تصفح الإعلانات",
            
            // Services
            "search_services" to "ابحث عن خدمة منزلية...",
            "services_banner_text" to "خصم 30% على أول حجز لك اليوم!",
            "categories" to "الفئات الرئيسية",
            "featured_techs" to "فنيون متميزون ومعتمدون",
            "today_offers" to "عروض حصرية لليوم",
            "clean_cat" to "تنظيف منازل",
            "ac_cat" to "صيانة تكييف",
            "electricity_cat" to "كهرباء وإضاءة",
            "plumbing_cat" to "سباكة ومعالجة تسريب",
            "pest_cat" to "مكافحة حشرات",
            "security_cat" to "أنظمة أمن وكاميرات",
            "emergency_service" to "خدمات طوارئ 24/7",
            "emergency_desc" to "فني طوارئ سريع يصلك خلال 30 دقيقة فقط",
            "order_now" to "احجز الآن",
            "verified_tech" to "فني معتمد من كيان",
            "active_now" to "نشط الآن",
            "experience" to "سنوات الخبرة",
            "years" to "سنوات",
            "price_starting" to "يبدأ السعر من",
            
            // Service Detail / Booking
            "service_features" to "مزايا الخدمة المضمونة:",
            "feat_1" to "فنيون معتمدون ومفحوصون أمنياً",
            "feat_2" to "ضمان لمدة سنة كاملة على المواد والصيانة",
            "feat_3" to "شامل الأدوات والمعدات الفنية الفاخرة",
            "feat_4" to "استجابة سريعة ووصول خلال ساعتين كحد أقصى",
            "choose_datetime" to "اختر التاريخ والوقت",
            "choose_address" to "تحديد موقع الخدمة",
            "house_number" to "رقم المنزل / الشقة",
            "street_name" to "اسم الشارع والحي",
            "address_details" to "تفاصيل إضافية للعنوان",
            "confirm_booking" to "تأكيد الحجز",
            "summary" to "ملخص تفاصيل الحجز",
            "service_cost" to "تكلفة الخدمة",
            "tax" to "الضريبة (15%)",
            "total_amount" to "الإجمالي النهائي",
            "pay_method" to "طريقة الدفع",
            "pay_card" to "بطاقة ائتمانية / مدى",
            "pay_wallet" to "رصيد محفظة كيان",
            "pay_cod" to "الدفع نقداً بعد الانتهاء",
            "add_card" to "إضافة بطاقة جديدة",
            "card_number" to "رقم البطاقة",
            "card_holder" to "اسم حامل البطاقة",
            "expiry_date" to "تاريخ الانتهاء MM/YY",
            "cvv" to "رمز الأمان CVV",
            "success_pay" to "تم الدفع وتأكيد الحجز بنجاح!",
            "success_pay_desc" to "تم جدولة الخدمة وتعيين الفني. ستتلقى رمز حضور وتحديثات مباشرة.",
            "track_booking" to "تتبع وصول الفني",
            "tech_status_otw" to "الفني في الطريق إليك",
            "tech_status_arrived" to "الفني وصل لموقعك",
            "tech_status_working" to "بدء تنفيذ الخدمة الآن",
            "tech_status_done" to "تم إنجاز الخدمة بنجاح",
            "cancel_booking" to "إلغاء الحجز",
            "reschedule" to "إعادة جدولة الحجز",
            "otp_cancel_confirm" to "رمز إلغاء الحجز",
            "digital_sign" to "التوقيع الإلكتروني لإتمام الخدمة",
            "pdf_invoice" to "تحميل فاتورة PDF معتمدة",
            "qr_code_arrival" to "رمز الاستجابة السريعة (QR) لإثبات الحضور",
            
            // Shop
            "search_shop" to "ابحث في متجر كيان الفاخر...",
            "flash_sale" to "تنزيلات فلاش الكبرى",
            "add_to_cart" to "إضافة إلى السلة",
            "cart_title" to "سلة التسوق",
            "promo_code" to "كود خصم / كوبون",
            "apply" to "تطبيق",
            "checkout" to "إتمام الشراء",
            "delivery_address" to "عنوان التوصيل",
            "order_summary" to "ملخص الطلبية",
            "order_success_title" to "تم تسجيل طلبك الفاخر بنجاح!",
            "order_success_desc" to "فريق اللوجستيات في كيان يقوم بتجهيز منتجاتك الفاخرة الآن.",
            "track_order" to "تتبع مسار شحنتك",
            "order_shipped" to "تم الشحن مع مندوب كيان",
            "item_return" to "إرجاع واستبدال المنتج",
            "seller_profile" to "ملف البائع",
            
            // Classifieds
            "search_classifieds" to "ابحث في إعلانات كيان المبوبة...",
            "post_ad" to "إضافة إعلان جديد",
            "premium_ad" to "ترقية لإعلان مميز (Premium)",
            "my_ads" to "إعلاناتي المنشورة والمغلقة",
            "ad_details" to "تفاصيل الإعلان",
            "chat_seller" to "محادثة البائع",
            "call_seller" to "اتصال هاتفي",
            "ad_step_1" to "الخطوة 1: فئة الإعلان وبياناته",
            "ad_step_2" to "الخطوة 2: رفع الصور والتفاصيل",
            "ad_step_3" to "الخطوة 3: المعاينة والنشر والترقية",
            "publish" to "نشر الإعلان الآن",
            "ad_title" to "عنوان الإعلان",
            "ad_price" to "السعر المطلوب",
            "ad_desc" to "وصف الإعلان بدقة",
            "ad_success" to "تم نشر إعلانك بنجاح!",
            
            // Profile & Settings
            "profile_title" to "الملف الشخصي الفاخر",
            "edit_profile" to "تعديل الحساب",
            "settings_title" to "الإعدادات العامة",
            "app_theme" to "وضع العرض (مظلم / مضيء)",
            "lang_select" to "تغيير لغة التطبيق",
            "notifications" to "الإشعارات العامة",
            "security_2fa" to "الأمان والتحقق الثنائي",
            "faq" to "الأسئلة الشائعة FAQ",
            "live_support" to "الدعم والمحادثة المباشرة",
            "rate_app" to "تقييم تطبيق كيان",
            "about_app" to "عن كيان ورؤيتنا",
            "logout" to "تسجيل خروج",
            "save_changes" to "حفظ التغييرات"
        )

        val en = mapOf(
            "app_title" to "KAYAN - Gulf Super App",
            "tagline" to "Your Luxury Integrated Gulf Super App for All Needs",
            "lang_choose" to "Choose Language & Region",
            "get_started" to "Get Started",
            "select_country" to "Select Country",
            "saudi" to "Kingdom of Saudi Arabia",
            "uae" to "United Arab Emirates",
            "qatar" to "State of Qatar",
            "kuwait" to "State of Kuwait",
            "bahrain" to "Kingdom of Bahrain",
            "oman" to "Sultanate of Oman",
            "egypt" to "Arab Republic of Egypt",
            
            // Onboarding
            "onboard_1_title" to "Welcome to KAYAN",
            "onboard_1_desc" to "The premier Gulf Super App for your daily needs with extraordinary design",
            "onboard_2_title" to "Everything in One Place",
            "onboard_2_desc" to "Certified home services, premium shopping, and verified classified ads",
            "onboard_3_title" to "Premium Quality & Guarantee",
            "onboard_3_desc" to "Certified technicians, secure payments, and daily deals with 1-year warranty",
            "next" to "Next",
            "skip" to "Skip",
            
            // Auth
            "login" to "Login",
            "signup" to "Create Account",
            "email_or_phone" to "Email or Mobile Number",
            "password" to "Password",
            "remember_me" to "Remember Me",
            "forgot_password" to "Forgot Password?",
            "or_continue_with" to "Or continue with",
            "dont_have_account" to "Don't have an account? Sign up",
            "have_account" to "Already have an account? Login",
            "fullname" to "Full Name",
            "email" to "Email Address",
            "phone" to "Mobile Number",
            "confirm_password" to "Confirm Password",
            "agree_terms" to "I agree to KAYAN's Terms & Conditions",
            "otp_verify" to "OTP Verification",
            "otp_desc" to "Enter the 5-digit verification code sent to your phone",
            "verify" to "Verify",
            "resend" to "Resend",
            "forgot_title" to "Password Recovery",
            "forgot_desc" to "Enter email or phone to receive account verification OTP",
            "send_otp" to "Send Verification Code",
            "new_password" to "New Password",
            "new_password_desc" to "Create a strong password to secure your premium account",
            "save" to "Save & Update",
            
            // Dashboard
            "welcome" to "Welcome,",
            "guest" to "Valued Guest",
            "wallet_balance" to "Wallet Balance",
            "kayan_points" to "Kayan Points",
            "savings" to "Total Savings",
            "sar" to "SAR",
            "point_unit" to "Pts",
            "domestic_services" to "Home Services",
            "domestic_desc" to "Cleaning, AC, Electric, Plumbing with 1-year solid warranty",
            "discover" to "Discover Services",
            "shopping_store" to "Shopping Store",
            "shopping_desc" to "Most popular high-end lifestyle products & luxury tech",
            "shop_now" to "Shop Now",
            "classified_ads" to "Classified Ads",
            "classified_desc" to "Buy and sell cars, real estate, premium watches, and more",
            "browse_ads" to "Browse Ads",
            
            // Services
            "search_services" to "Search for home services...",
            "services_banner_text" to "Get 30% OFF your first service today!",
            "categories" to "Main Categories",
            "featured_techs" to "Certified Elite Technicians",
            "today_offers" to "Exclusive Deals of the Day",
            "clean_cat" to "Home Cleaning",
            "ac_cat" to "AC Maintenance",
            "electricity_cat" to "Electrical & Lighting",
            "plumbing_cat" to "Plumbing & Leakage",
            "pest_cat" to "Pest Control",
            "security_cat" to "Smart Security & Cameras",
            "emergency_service" to "24/7 Emergency Services",
            "emergency_desc" to "Lightning-fast technician dispatch within 30 minutes",
            "order_now" to "Book Now",
            "verified_tech" to "KAYAN Certified Expert",
            "active_now" to "Active Now",
            "experience" to "Experience",
            "years" to "Years",
            "price_starting" to "Starting from",
            
            // Service Detail / Booking
            "service_features" to "Guaranteed Service Perks:",
            "feat_1" to "Police-checked, background-screened expert technicians",
            "feat_2" to "1-year solid warranty on materials & service labor",
            "feat_3" to "Includes all elite mechanical & electronic tools",
            "feat_4" to "Rapid responsive arrival within 2 hours maximum",
            "choose_datetime" to "Select Date & Time",
            "choose_address" to "Determine Service Location",
            "house_number" to "House / Apartment Number",
            "street_name" to "Street Name & District",
            "address_details" to "Additional Location Details",
            "confirm_booking" to "Confirm Booking",
            "summary" to "Booking Summary Details",
            "service_cost" to "Service Fee",
            "tax" to "VAT (15%)",
            "total_amount" to "Final Grand Total",
            "pay_method" to "Payment Method",
            "pay_card" to "Credit / Mada Cards",
            "pay_wallet" to "Kayan Wallet Balance",
            "pay_cod" to "Pay Cash on Service Completion",
            "add_card" to "Add New Card",
            "card_number" to "Card Number",
            "card_holder" to "Cardholder Name",
            "expiry_date" to "Expiry MM/YY",
            "cvv" to "CVV",
            "success_pay" to "Payment & Booking Confirmed!",
            "success_pay_desc" to "Service scheduled. Technician assigned. Your attendance QR is ready.",
            "track_booking" to "Track Dispatch",
            "tech_status_otw" to "Technician is on the way",
            "tech_status_arrived" to "Technician has arrived",
            "tech_status_working" to "Service execution in progress",
            "tech_status_done" to "Service completed successfully",
            "cancel_booking" to "Cancel Booking",
            "reschedule" to "Reschedule Booking",
            "otp_cancel_confirm" to "Booking Cancellation Code",
            "digital_sign" to "Electronic Sign-off on Completion",
            "pdf_invoice" to "Download Certified PDF Invoice",
            "qr_code_arrival" to "Secure QR Code for Attendance verification",
            
            // Shop
            "search_shop" to "Search in KAYAN Luxury Store...",
            "flash_sale" to "Mega Flash Sale",
            "add_to_cart" to "Add to Cart",
            "cart_title" to "Shopping Cart",
            "promo_code" to "Coupon / Promo Code",
            "apply" to "Apply",
            "checkout" to "Proceed to Checkout",
            "delivery_address" to "Delivery Address",
            "order_summary" to "Order Summary",
            "order_success_title" to "Order Placed Successfully!",
            "order_success_desc" to "Kayan's elite logistics team is packaging your luxury products now.",
            "track_order" to "Track Shipment Path",
            "order_shipped" to "Shipped with Kayan Delivery Agent",
            "item_return" to "Return & Exchange Item",
            "seller_profile" to "Seller Profile",
            
            // Classifieds
            "search_classifieds" to "Search in KAYAN Classifieds...",
            "post_ad" to "Post New Advertisement",
            "premium_ad" to "Upgrade to Premium Ad",
            "my_ads" to "My Published & Closed Ads",
            "ad_details" to "Ad Details",
            "chat_seller" to "Chat with Seller",
            "call_seller" to "Call Seller",
            "ad_step_1" to "Step 1: Ad Category & Core Info",
            "ad_step_2" to "Step 2: Media Upload & Details",
            "ad_step_3" to "Step 3: Preview, Publish & Promos",
            "publish" to "Publish Ad Now",
            "ad_title" to "Ad Title",
            "ad_price" to "Required Price",
            "ad_desc" to "Accurate description",
            "ad_success" to "Ad Published Successfully!",
            
            // Profile & Settings
            "profile_title" to "Luxury Profile Panel",
            "edit_profile" to "Edit Account Details",
            "settings_title" to "General Settings",
            "app_theme" to "Display Mode (Dark / Light)",
            "lang_select" to "Change App Language",
            "notifications" to "System Notifications",
            "security_2fa" to "Security & Two-Factor Auth",
            "faq" to "Frequently Asked Questions",
            "live_support" to "Live Chat & Support Desk",
            "rate_app" to "Rate Kayan Experience",
            "about_app" to "About Kayan & Vision",
            "logout" to "Log Out",
            "save_changes" to "Save Settings Changes"
        )

        return if (lang == Language.AR) {
            ar[key] ?: key
        } else {
            en[key] ?: key
        }
    }
}

// Country Definition
data class Country(val code: String, val nameKey: String, val flag: String)

val COUNTRIES = listOf(
    Country("sa", "saudi", "🇸🇦"),
    Country("ae", "uae", "🇦🇪"),
    Country("qa", "qatar", "🇶🇦"),
    Country("kw", "kuwait", "🇰🇼"),
    Country("bh", "bahrain", "🇧🇭"),
    Country("om", "oman", "🇴🇲"),
    Country("eg", "egypt", "🇪🇬")
)

// Data Models
data class ServiceItem(
    val id: Int,
    val nameAr: String,
    val nameEn: String,
    val categoryKey: String,
    val price: Double,
    val imagePlaceholderColor: Long,
    val rating: Float,
    val descriptionAr: String,
    val descriptionEn: String
)

data class Technician(
    val id: Int,
    val name: String,
    val rating: Float,
    val experienceYears: Int,
    val specialtyKey: String,
    val imageUrl: String = ""
)

data class Product(
    val id: Int,
    val nameAr: String,
    val nameEn: String,
    val price: Double,
    val rating: Float,
    val discountPrice: Double? = null,
    val brand: String,
    val descriptionAr: String,
    val descriptionEn: String,
    val category: String,
    val reviewsCount: Int
)

data class ClassifiedAd(
    val id: Int,
    val titleAr: String,
    val titleEn: String,
    val price: Double,
    val locationAr: String,
    val locationEn: String,
    val sellerName: String,
    val sellerPhone: String,
    val sellerRating: Float,
    val descriptionAr: String,
    val descriptionEn: String,
    val views: Int,
    val isPremium: Boolean,
    val category: String,
    val datePosted: String
)

data class CartItem(
    val product: Product,
    var quantity: Int
)

enum class BookingStatus {
    SCHEDULED, DISPATCHED, ARRIVED, WORKING, DONE
}

data class Booking(
    val id: String,
    val serviceItem: ServiceItem,
    val date: String,
    val time: String,
    val address: String,
    val totalAmount: Double,
    val status: BookingStatus,
    val technician: Technician
)

data class KayanMessage(
    val id: String,
    val sender: String, // "user" or "agent" or "technician" or "seller"
    val content: String,
    val timestamp: String
)

data class KayanNotification(
    val id: String,
    val titleAr: String,
    val titleEn: String,
    val contentAr: String,
    val contentEn: String,
    val time: String,
    val isRead: Boolean
)
