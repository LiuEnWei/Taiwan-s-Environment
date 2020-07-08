package com.wayne.taiwan_s_environment.view.imageoriginal

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.View
import com.wayne.taiwan_s_environment.Constant
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.span.BoldColorClickableSpan
import com.wayne.taiwan_s_environment.model.span.OnClickableSpanListener
import com.wayne.taiwan_s_environment.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_image_original.*
import timber.log.Timber

class ImageOriginalFragment: BaseFragment(R.layout.fragment_image_original), OnClickableSpanListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text_taiwan_icon.text = getSpannableString(R.string.taiwan_icon, R.string.freepik, R.string.flaticon)
        text_taiwan_icon.movementMethod = LinkMovementMethod.getInstance()

        text_android_icon.text = getSpannableString(R.string.android_icon, R.string.freepik, R.string.flaticon)
        text_android_icon.movementMethod = LinkMovementMethod.getInstance()

        text_taiwan_svg.text = getSpannableString(R.string.taiwan_svg, R.string.luuva, R.string.wikimedia_commons)
        text_taiwan_svg.movementMethod = LinkMovementMethod.getInstance()

        text_app_icons.text = getSpannableString(R.string.app_icons, R.string.android, R.string.material_design)
        text_app_icons.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun getSpannableString(nameId: Int, madeById: Int, fromId: Int): SpannableString {
        val color = resources.getColor(R.color.colorLightBlue800)
        val name = getString(nameId)
        val madeBy = getString(madeById)
        val from = getString(fromId)
        val text = getString(R.string.icons_made_by, name, madeBy, from)
        val textSpannable = SpannableString(text)
        val nameStart = text.indexOf(name)
        textSpannable.setSpan(BoldColorClickableSpan(color, nameId, this), nameStart, nameStart + name.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

        val madeByStart = text.indexOf(madeBy)
        textSpannable.setSpan(BoldColorClickableSpan(color, madeById, this), madeByStart, madeByStart + madeBy.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

        val fromStart = text.indexOf(from)
        textSpannable.setSpan(BoldColorClickableSpan(color, fromId, this), fromStart, fromStart + from.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

        return textSpannable
    }

    override fun onClick(id: Int) {
        Timber.e("view : $view")

        when (id) {
            R.string.taiwan_icon -> openUrl(Constant.LINK_TAIWAN_ICON)
            R.string.android_icon -> openUrl(Constant.LINK_ANDROID_ICON)
            R.string.freepik -> openUrl(Constant.LINK_FREEPIK)
            R.string.flaticon -> openUrl(Constant.LINK_FLATICON)
            R.string.taiwan_svg -> openUrl(Constant.LINK_TAIWAN_SVG)
            R.string.luuva -> openUrl(Constant.LINK_LUUVA)
            R.string.wikimedia_commons -> openUrl(Constant.LINK_WIKIMEDIA_COMMONS)
            R.string.app_icons -> openUrl(Constant.LINK_APP_ICONS)
            R.string.android -> openUrl(Constant.LINK_ANDROID)
            R.string.material_design -> openUrl(Constant.LINK_MATERIAL_DESIGN)
        }
    }

    override fun isToolBarShow(): Boolean {
        return true
    }

    override fun getToolBarColor(): Int {
        return R.color.colorLightBlue200
    }
}