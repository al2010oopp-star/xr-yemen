package com.yemeni.messenger

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {
    private val green = Color.rgb(24, 128, 82)
    private val red = Color.rgb(190, 48, 55)
    private val dark = Color.rgb(34, 42, 39)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = green
        window.navigationBarColor = Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        showChats()
    }

    private fun baseColumn(): LinearLayout = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setBackgroundColor(Color.rgb(247, 249, 247))
        layoutDirection = View.LAYOUT_DIRECTION_RTL
        setPadding(dp(16), dp(12), dp(16), dp(12))
    }

    private fun showChats() {
        val page = baseColumn()
        val header = TextView(this).apply {
            text = "التواصل اليمني"
            textSize = 25f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
            setPadding(dp(16), 0, dp(16), 0)
            setBackgroundColor(green)
        }
        page.addView(header, LinearLayout.LayoutParams(-1, dp(60)))
        val subtitle = TextView(this).apply {
            text = "المحادثات"
            textSize = 20f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(dark)
            setPadding(0, dp(18), 0, dp(10))
        }
        page.addView(subtitle)
        page.addView(chatRow("أحمد", "السلام عليكم، كيف حالك؟", "٨:٣٠ م", "أ"))
        page.addView(chatRow("العائلة", "تم إرسال صورة", "٧:١٥ م", "ع"))
        page.addView(chatRow("محمد", "نتحدث لاحقاً إن شاء الله", "أمس", "م"))
        page.addView(chatRow("أصدقاء العمل", "شكراً لكم", "أمس", "ع"))
        val spacer = Space(this)
        page.addView(spacer, LinearLayout.LayoutParams(1, 0, 1f))
        val actions = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }
        actions.addView(actionButton("المحادثات") { showChats() }, LinearLayout.LayoutParams(0, dp(52), 1f))
        actions.addView(actionButton("جهات الاتصال") { showInfo("جهات الاتصال", "ستظهر جهات اتصالك هنا بعد إضافة ميزة التسجيل والصلاحيات.") }, LinearLayout.LayoutParams(0, dp(52), 1f))
        actions.addView(actionButton("الإعدادات") { showSettings() }, LinearLayout.LayoutParams(0, dp(52), 1f))
        page.addView(actions)
        setContentView(page)
    }

    private fun chatRow(name: String, message: String, time: String, initial: String): View {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(10), dp(12), dp(10), dp(12))
            setBackgroundColor(Color.WHITE)
        }
        val avatar = TextView(this).apply {
            text = initial
            textSize = 20f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setBackgroundColor(green)
        }
        row.addView(avatar, LinearLayout.LayoutParams(dp(46), dp(46)))
        val texts = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(12), 0, dp(8), 0)
        }
        texts.addView(TextView(this).apply {
            text = name; textSize = 17f; typeface = Typeface.DEFAULT_BOLD; setTextColor(dark)
        })
        texts.addView(TextView(this).apply {
            text = message; textSize = 14f; setTextColor(Color.DKGRAY)
        })
        row.addView(texts, LinearLayout.LayoutParams(0, -2, 1f))
        row.addView(TextView(this).apply {
            text = time; textSize = 12f; setTextColor(red)
        })
        row.setOnClickListener { showInfo(name, "هذه شاشة محادثة تجريبية. إرسال الرسائل الحقيقي يحتاج إلى خادم وربط حسابات المستخدمين.") }
        val wrapper = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, dp(3), 0, dp(3))
            addView(row)
        }
        return wrapper
    }

    private fun actionButton(label: String, click: () -> Unit) = Button(this).apply {
        text = label
        isAllCaps = false
        setTextColor(green)
        setOnClickListener { click() }
    }

    private fun showSettings() {
        val page = baseColumn()
        page.addView(title("الإعدادات"))
        page.addView(infoLine("الخصوصية", "قفل بالبصمة — سيُضاف عند تنفيذ الحماية"))
        page.addView(infoLine("الرسائل ذاتية الاختفاء", "اختيار مدة اختفاء الرسائل — غير مفعّل بعد"))
        page.addView(infoLine("إظهار رقم الهاتف", "مخفي / جهات الاتصال / ظاهر — إعداد مبدئي"))
        page.addView(infoLine("المظهر", "الوضع النهاري والليلي — سيُستكمل"))
        page.addView(infoLine("المكالمات", "مكالمات صوتية فقط — تحتاج إلى خدمة اتصال"))
        page.addView(actionButton("العودة للمحادثات") { showChats() })
        setContentView(page)
    }

    private fun showInfo(title: String, message: String) {
        val page = baseColumn()
        page.addView(title(title))
        page.addView(infoLine("", message))
        page.addView(actionButton("العودة للمحادثات") { showChats() })
        setContentView(page)
    }

    private fun title(value: String) = TextView(this).apply {
        text = value; textSize = 24f; typeface = Typeface.DEFAULT_BOLD
        setTextColor(green); setPadding(0, dp(10), 0, dp(18))
    }

    private fun infoLine(label: String, value: String) = TextView(this).apply {
        text = if (label.isBlank()) value else "$label\n$value"
        textSize = 16f; setTextColor(dark); setPadding(dp(12), dp(14), dp(12), dp(14))
        setBackgroundColor(Color.WHITE)
    }

    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()
}
