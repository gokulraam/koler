package com.chooloo.www.koler.ui.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import com.chooloo.www.koler.R
import com.chooloo.www.koler.util.ViewManager


@SuppressLint("Recycle", "CustomViewStyleable")
class DialpadKey : LinearLayout {
    private val _viewManager by lazy { ViewManager(context) }

    var keyCode = 0
    private var _digitTextView: TextView
    private var _lettersTextView: TextView

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleRes: Int = 0
    ) : super(context, attrs, defStyleRes) {
        gravity = Gravity.CENTER_HORIZONTAL

        _digitTextView = TextView(context, attrs, defStyleRes).apply {
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

            setTextAppearance(R.style.Koler_Text_Headline2)
        }.also {
            addView(it)
        }

        _lettersTextView = TextView(context, attrs, defStyleRes).apply {
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            typeface = ResourcesCompat.getFont(context, R.font.google_sans_bold)

            setPadding(0, _viewManager.getSizeInDp(5), 0, 0)
            setTextAppearance(R.style.Koler_Text_Caption)
        }.also {
            addView(it)
        }

        orientation = VERTICAL
        layoutParams = LayoutParams(WRAP_CONTENT, MATCH_PARENT)
        background = _viewManager.selectableItemBackgroundBorderlessDrawable
        digit = context.obtainStyledAttributes(attrs, R.styleable.Koler_DialpadKey)
            .getString(R.styleable.Koler_DialpadKey_digit)

        setPadding(_viewManager.getSizeInDp(7))
    }

    var digit: String?
        get() = _digitTextView.text.toString()
        set(value) {
            _digitTextView.text = value
            when (value) {
                "0" -> {
                    keyCode = KeyEvent.KEYCODE_0
                    _lettersTextView.text = context.getString(R.string.dialpad_0_letters)
                }
                "1" -> {
                    keyCode = KeyEvent.KEYCODE_1
                    _lettersTextView.setBackgroundResource(R.drawable.ic_voicemail_black_24dp)
                }
                "2" -> {
                    keyCode = KeyEvent.KEYCODE_2
                    _lettersTextView.text = context.getString(R.string.dialpad_2_letters)
                }
                "3" -> {
                    keyCode = KeyEvent.KEYCODE_3
                    _lettersTextView.text = context.getString(R.string.dialpad_3_letters)
                }
                "4" -> {
                    keyCode = KeyEvent.KEYCODE_4
                    _lettersTextView.text = context.getString(R.string.dialpad_4_letters)
                }
                "5" -> {
                    keyCode = KeyEvent.KEYCODE_5
                    _lettersTextView.text = context.getString(R.string.dialpad_5_letters)
                }
                "6" -> {
                    keyCode = KeyEvent.KEYCODE_6
                    _lettersTextView.text = context.getString(R.string.dialpad_6_letters)
                }
                "7" -> {
                    keyCode = KeyEvent.KEYCODE_7
                    _lettersTextView.text = context.getString(R.string.dialpad_7_letters)
                }
                "8" -> {
                    keyCode = KeyEvent.KEYCODE_8
                    _lettersTextView.text = context.getString(R.string.dialpad_8_letters)
                }
                "9" -> {
                    keyCode = KeyEvent.KEYCODE_9
                    _lettersTextView.text = context.getString(R.string.dialpad_9_letters)
                }
                "*" -> {
                    keyCode = KeyEvent.KEYCODE_STAR
                    _lettersTextView.text = context.getString(R.string.dialpad_star_letters)
                }
                "#" -> {
                    keyCode = KeyEvent.KEYCODE_POUND
                    _lettersTextView.text = context.getString(R.string.dialpad_pound_letters)
                }
                else -> {
                }
            }
        }
}