package com.wayne.taiwan_s_environment.view.taiwan

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.richpath.RichPath
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.view.adapter.TaiwanAdapter
import com.wayne.taiwan_s_environment.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_taiwan.*


class TaiwanFragment : BaseFragment(R.layout.fragment_taiwan) {

    private val viewModel by viewModels<TaiwanViewModel>()
    private var clickRichPath: RichPath? = null
    private val defaultColor: Int by lazy { getColor(requireContext(), R.color.colorRocGreen)  }
    private val clickColor: Int by lazy { getColor(requireContext(), R.color.colorGreen500)  }

    private lateinit var bottomSheet: BottomSheetBehavior<View>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheet = BottomSheetBehavior.from(bottom_sheet)

        viewModel.epaList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiResult.Success -> {
                    val list = it.result
                    if (list.isNullOrEmpty()) {
                        text_no_site.visibility = View.VISIBLE
                        recycler_taiwan.visibility = View.INVISIBLE
                    } else {
                        text_no_site.visibility = View.GONE
                        recycler_taiwan.visibility = View.VISIBLE
                    }
                    recycler_taiwan.adapter = TaiwanAdapter(list)
                }

                is ApiResult.Error -> {
                    showErrorMessage(getErrorMessage(it.throwable),
                        DialogInterface.OnClickListener { dialog, view ->
                            dialog.dismiss()
                        })
                }
            }
        })

        rich_path_taiwan.setOnPathClickListener { rich ->
            clickRichPath?.fillColor = defaultColor
            when {
                clickRichPath?.name != null && clickRichPath?.name == rich.name -> {
                    clickRichPath = null
                    viewModel.getUVByCounty(null)
                    toolbar.title = getString(R.string.taiwan_region_title)
                }
                rich.name != null -> {
                    rich_path_taiwan.findRichPathByName(rich.name)?.let {
                        it.fillColor = clickColor
                        clickRichPath = it
                        viewModel.getUVByCounty(rich.name)
                        toolbar.title = rich.name

                        if (bottomSheet.state == BottomSheetBehavior.STATE_COLLAPSED) {
                            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
                        }
                    }
                }
                else -> {
                    clickRichPath = null
                    viewModel.getUVByCounty(null)
                    toolbar.title = getString(R.string.taiwan_region_title)
                }
            }
        }

        (requireActivity()as AppCompatActivity ).setSupportActionBar(toolbar)

        viewModel.getUVByCounty()
    }

    override fun isBottomNavigationShow(): Boolean {
        return true
    }

    override fun getStatusBarColor(): Int {
        return R.color.colorRocBlue
    }
}