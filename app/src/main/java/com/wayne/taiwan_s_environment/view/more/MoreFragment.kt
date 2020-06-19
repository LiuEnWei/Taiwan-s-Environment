package com.wayne.taiwan_s_environment.view.more

import android.os.Bundle
import android.view.View
import com.wayne.taiwan_s_environment.BuildConfig
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_more.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoreFragment : BaseFragment(R.layout.fragment_more), View.OnClickListener {

    companion object {

    }

    private val viewModel by viewModel<MoreViewModel>()
    private val appVersion = "v${BuildConfig.VERSION_NAME}"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_app_version.text = appVersion
        btn_references.setOnClickListener(this)
        btn_photo_and_icon_original.setOnClickListener(this)
        btn_about_developer.setOnClickListener(this)
        btn_privacy_policy.setOnClickListener(this)
        btn_copyright.setOnClickListener(this)
        btn_app_version.setOnClickListener(this)
    }

    override fun isBottomNavigationShow(): Boolean {
        return true
    }

    override fun isToolBarShow(): Boolean {
        return true
    }

    override fun getToolBarColor(): Int {
        return R.color.colorLightBlue200
    }

    // TODO
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_references -> {

            }
            R.id.btn_photo_and_icon_original -> {

            }
            R.id.btn_about_developer -> {

            }
            R.id.btn_privacy_policy -> {

            }
            R.id.btn_copyright -> {

            }
            R.id.btn_app_version -> {
                if (text_app_version.visibility == View.VISIBLE) {
                    text_app_version.visibility = View.INVISIBLE
                } else {
                    text_app_version.visibility = View.VISIBLE
                }
            }
        }
    }
}