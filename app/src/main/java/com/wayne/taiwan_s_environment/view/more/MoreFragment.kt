package com.wayne.taiwan_s_environment.view.more

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.wayne.taiwan_s_environment.BuildConfig
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_more.*


class MoreFragment : BaseFragment(R.layout.fragment_more), View.OnClickListener {
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

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_references -> {
                findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToReferencesFragment())
            }
            R.id.btn_photo_and_icon_original -> {
                findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToImageOriginalFragment())
            }
            R.id.btn_about_developer -> {
                findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToAboutDeveloperFragment())
            }
            R.id.btn_privacy_policy -> {
                findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToPrivacyPolicyFragment())
            }
            R.id.btn_copyright -> {
                Toast.makeText(requireContext(), R.string.copyright_message, Toast.LENGTH_LONG).show()
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