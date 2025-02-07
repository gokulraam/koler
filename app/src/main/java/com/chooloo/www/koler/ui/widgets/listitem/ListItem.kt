package com.chooloo.www.koler.ui.widgets.listitem

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginTop
import com.chooloo.www.koler.KolerApp
import com.chooloo.www.koler.R
import com.chooloo.www.koler.ui.widgets.IconButton
import com.chooloo.www.koler.util.ViewManager
import com.github.abdularis.civ.AvatarImageView
import com.github.abdularis.civ.AvatarImageView.Companion.SHOW_IMAGE
import com.github.abdularis.civ.AvatarImageView.Companion.SHOW_INITIAL

@SuppressLint("CustomViewStyleable", "Recycle")
open class ListItem : LinearLayout {
    private var _isPadded: Boolean = true
    private var _isCompact: Boolean = false

    private val _viewManager by lazy { ViewManager(context) }

    private var _onLeftButtonClickListener: () -> Unit = {}
    private var _onRightButtonClickListener: () -> Unit = {}

    protected val _buttonLeft: IconButton
    protected val _buttonRight: IconButton
    protected val _image: AvatarImageView
    protected val _title: AppCompatTextView
    protected val _header: AppCompatTextView
    protected val _caption: AppCompatTextView
    protected val _personLayout: ConstraintLayout

    protected val dimenSpacing by lazy { resources.getDimensionPixelSize(R.dimen.default_spacing) }
    protected val dimenImageSize by lazy { resources.getDimensionPixelSize(R.dimen.image_size_small) }
    protected val dimenSpacingBig by lazy { resources.getDimensionPixelSize(R.dimen.default_spacing_big) }
    protected val dimenSpacingSmall by lazy { resources.getDimensionPixelSize(R.dimen.default_spacing_small) }

    var isCompact: Boolean
        get() = _isCompact
        set(value) {
            setPaddingMode(value, _isPadded)
        }


    var headerText: String?
        get() = _header.text.toString()
        set(value) {
            _header.apply {
                text = value
                visibility = if (value != null && value != "") VISIBLE else GONE
            }
        }

    var isPadded: Boolean
        get() = _isPadded
        set(value) {
            setPaddingMode(_isCompact, value)
            _isPadded = value
        }

    var imageSize: Int
        get() = _image.height
        set(value) {
            _image.layoutParams = LayoutParams(value, value)
        }

    var imageTextSize: Float
        get() = _image.textSize
        set(value) {
            _image.textSize = value
        }

    var imageTintList: ColorStateList?
        get() = _image.imageTintList
        set(value) {
            _image.imageTintList = value
        }

    var imageVisibility: Boolean
        get() = _image.visibility == VISIBLE
        set(value) {
            _image.visibility = if (value) VISIBLE else GONE
            if (!value) {
                _title.layoutParams =
                    ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                        setMargins(0, 0, _title.marginTop, 0)
                    }
            }
        }

    var imageDrawable: Drawable?
        get() = _image.drawable
        set(value) {
            _image.setImageDrawable(value)
            _image.state = SHOW_IMAGE
        }

    fun setImageUri(imageUri: Uri?) {
        _image.setImageURI(imageUri)
        _image.state = if (imageUri != null) SHOW_IMAGE else SHOW_INITIAL
    }

    fun setImageInitials(text: String?) {
        _image.text = text
        text?.let { _image.state = SHOW_INITIAL }
    }

    fun setImageResource(@DrawableRes res: Int) {
        _image.setImageResource(res)
    }

    fun setImageBackgroundColor(@ColorInt color: Int) {
        _image.setBackgroundColor(color)
    }


    var titleText: String?
        get() = _title.text.toString()
        set(value) {
            _title.text = value ?: ""
        }

    fun setTitleTextColor(@ColorInt color: Int) {
        _title.setTextColor(color)
    }


    var captionText: String?
        get() = _caption.text.toString()
        set(value) {
            _caption.apply {
                text = value ?: ""
                visibility = if (value == null) GONE else VISIBLE
            }
        }

    fun setCaptionTextColor(@ColorInt color: Int) {
        _caption.setTextColor(color)
    }


    var leftButtonVisibility: Boolean
        get() = _buttonLeft.visibility == VISIBLE
        set(value) {
            _buttonLeft.visibility = if (value) VISIBLE else GONE
        }


    var rightButtonVisibility: Boolean
        get() = _buttonRight.visibility == VISIBLE
        set(value) {
            _buttonRight.visibility = if (value) VISIBLE else GONE
        }


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleRes: Int = 0
    ) : super(context, attrs, defStyleRes) {
        orientation = VERTICAL
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)

        _header = AppCompatTextView(context, attrs, defStyleRes).apply {
            isClickable = true
            isFocusable = true
            id = View.generateViewId()
            textAlignment = TEXT_ALIGNMENT_VIEW_START
            layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                setMargins(dimenSpacingSmall, dimenSpacing, dimenSpacingBig, dimenSpacingSmall)
            }

            setTextAppearance(R.style.Koler_Text_Subtitle2)
        }

        _title = AppCompatTextView(context, attrs, defStyleRes).apply {
            id = View.generateViewId()
            textAlignment = TEXT_ALIGNMENT_VIEW_START
            layoutParams = ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                setMargins(dimenSpacing - 5, 0, dimenSpacing, 0)
            }

            setTextAppearance(R.style.Koler_Text_Headline4)
        }

        _caption = AppCompatTextView(context, attrs, defStyleRes).apply {
            id = View.generateViewId()
            textAlignment = TEXT_ALIGNMENT_VIEW_START
            layoutParams = ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

            setTextAppearance(R.style.Koler_Text_Caption)
            setPadding(0, _viewManager.getSizeInDp(2), 0, 0)
        }

        _image = AvatarImageView(context, attrs).apply {
            state = SHOW_INITIAL
            id = generateViewId()
            textSize = resources.getDimension(R.dimen.caption_1)
            layoutParams = ConstraintLayout.LayoutParams(dimenImageSize, dimenImageSize)
            textColor = ContextCompat.getColor(context, R.color.color_image_placeholder_foreground)
            avatarBackgroundColor =
                ContextCompat.getColor(context, R.color.color_image_placeholder_background)
        }

        _buttonLeft = IconButton(context, attrs, defStyleRes).apply {
            visibility = GONE
            id = generateViewId()
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                setMargins(
                    dimenSpacingSmall,
                    dimenSpacingSmall,
                    dimenSpacingSmall,
                    dimenSpacingSmall
                )
            }

            setOnClickListener { _onLeftButtonClickListener.invoke() }
        }

        _buttonRight = IconButton(context, attrs, defStyleRes).apply {
            visibility = GONE
            id = generateViewId()
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                setMargins(
                    dimenSpacingSmall,
                    dimenSpacingSmall,
                    dimenSpacingSmall,
                    dimenSpacingSmall
                )
            }

            setOnClickListener { _onRightButtonClickListener.invoke() }
        }

        _personLayout = ConstraintLayout(context, attrs, defStyleRes).apply {
            isClickable = true
            id = View.generateViewId()
            background = _viewManager.selectableItemBackgroundDrawable
            layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }

        context.obtainStyledAttributes(attrs, R.styleable.Koler_ListItem, 0, 0).also {
            titleText = it.getString(R.styleable.Koler_ListItem_title)
            headerText = it.getString(R.styleable.Koler_ListItem_header)
            captionText = it.getString(R.styleable.Koler_ListItem_caption)
            imageDrawable = it.getDrawable(R.styleable.Koler_ListItem_src)
            isCompact = it.getBoolean(R.styleable.Koler_ListItem_compact, false)
        }

        _personLayout.apply {
            addView(_image)
            addView(_title)
            addView(_caption)
            addView(_buttonLeft)
            addView(_buttonRight)
        }

        ConstraintSet().apply {
            clone(_personLayout)

            _image.id.also {
                connect(it, BOTTOM, PARENT_ID, BOTTOM)
                connect(it, START, PARENT_ID, START)
                connect(it, TOP, PARENT_ID, TOP)
            }

            _title.id.also {
                connect(it, BOTTOM, _caption.id, TOP)
                connect(it, START, _image.id, END)
                connect(it, END, PARENT_ID, END)
                connect(it, TOP, PARENT_ID, TOP)
                setHorizontalBias(it, 0F)
            }

            _caption.id.also {
                connect(it, BOTTOM, PARENT_ID, BOTTOM)
                connect(it, START, _title.id, START)
                connect(it, TOP, _title.id, BOTTOM)
            }

            _buttonRight.id.also {
                connect(it, END, PARENT_ID, END)
                connect(it, TOP, PARENT_ID, TOP)
                connect(it, BOTTOM, PARENT_ID, BOTTOM)
            }

            _buttonLeft.id.also {
                connect(it, TOP, PARENT_ID, TOP)
                connect(it, BOTTOM, PARENT_ID, BOTTOM)
                connect(it, END, _buttonRight.id, START)
            }

            createVerticalChain(
                PARENT_ID,
                TOP,
                PARENT_ID,
                BOTTOM,
                intArrayOf(_title.id, _caption.id),
                null,
                CHAIN_PACKED
            )

            applyTo(_personLayout)
        }

        addView(_header)
        addView(_personLayout)
    }


    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        if (selected) {
            _personLayout.setBackgroundColor(_viewManager.getAttrColor(R.attr.colorSecondary))
        } else {
            _personLayout.background = _viewManager.selectableItemBackgroundDrawable
        }
    }

    override fun setOnClickListener(onClickListener: OnClickListener?) {
        _personLayout.setOnClickListener(onClickListener)
    }

    override fun setOnLongClickListener(onLongClickListener: OnLongClickListener?) {
        _personLayout.setOnLongClickListener(onLongClickListener)
    }


    protected open fun setPaddingMode(isCompact: Boolean, isEnabled: Boolean) {
        _personLayout.setPadding(
            if (isEnabled) dimenSpacing else 0,
            if (isCompact) 3 else dimenSpacing - 7,
            if (isEnabled) dimenSpacing else 0,
            if (isCompact) 3 else dimenSpacing - 7
        )
        _header.setPadding(
            if (isEnabled) dimenSpacing else 0,
            dimenSpacing,
            if (isEnabled) dimenSpacing else 0,
            if (isCompact) dimenSpacingSmall - 10 else dimenSpacing
        )
    }

    fun setTitleColor(@ColorInt color: Int) {
        _title.setTextColor(color)
    }

    fun setTitleBold(isBold: Boolean) {
        _title.typeface = ResourcesCompat.getFont(
            context,
            if (isBold) R.font.google_sans_bold else R.font.google_sans_regular
        )
    }

    fun setLeftButtonTintColor(@ColorRes colorRes: Int) {
        _buttonLeft.imageTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
    }

    fun setLeftButtonBackgroundTintColor(@ColorInt color: Int) {
        _buttonLeft.backgroundTintList = ColorStateList.valueOf(color)
    }

    fun setLeftButtonDrawable(@DrawableRes drawableRes: Int) {
        _buttonLeft.setImageDrawable(ContextCompat.getDrawable(context, drawableRes))
    }

    fun setOnLeftButtonClickListener(onLeftButtonClickListener: () -> Unit) {
        _onLeftButtonClickListener = onLeftButtonClickListener
    }

    fun setRightButtonTintColor(@ColorRes colorRes: Int) {
        _buttonRight.imageTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
    }

    fun setRightButtonBackgroundTintColor(@ColorInt color: Int) {
        _buttonRight.backgroundTintList = ColorStateList.valueOf(color)
    }

    fun setRightButtonDrawable(@DrawableRes drawableRes: Int) {
        _buttonRight.setImageDrawable(ContextCompat.getDrawable(context, drawableRes))
    }

    fun setOnRightButtonClickListener(onRightButtonClickListener: () -> Unit) {
        _onRightButtonClickListener = onRightButtonClickListener
    }

    fun blinkCaption() {
        (context.applicationContext as KolerApp).componentRoot.animationInteractor.animateBlink(
            _caption,
            2500
        )
    }
}