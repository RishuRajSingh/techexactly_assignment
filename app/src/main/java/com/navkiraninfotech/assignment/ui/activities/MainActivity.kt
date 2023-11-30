package com.navkiraninfotech.assignment.ui.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.navkiraninfotech.assignment.databinding.ActivityMainBinding
import com.navkiraninfotech.assignment.dataobjects.AppListResponse
import com.navkiraninfotech.assignment.ui.fragments.appListFragment.AppListViewModel
import com.navkiraninfotech.assignment.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private var appList = ArrayList<AppListResponse.AppList>()
    private lateinit var model: AppListViewModel
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        model = ViewModelProvider(this)[AppListViewModel::class.java]
        viewModel.getAppList("378", this)
        viewModel.appListResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        appList = it.data.app_list as ArrayList<AppListResponse.AppList>
                        if (appList.size > 0) {
                            model.setData(appList)
                        } else {
                            Toast.makeText(
                                this,
                                response.message.toString(),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                }

                is NetworkResult.Error -> {
                    model.setData(ArrayList())
                    Toast.makeText(
                        this,
                        "Network error",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

                is NetworkResult.Loading -> {
                }

                else -> {
                    model.setData(ArrayList())
                    Toast.makeText(
                        this,
                        "Api not response",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}