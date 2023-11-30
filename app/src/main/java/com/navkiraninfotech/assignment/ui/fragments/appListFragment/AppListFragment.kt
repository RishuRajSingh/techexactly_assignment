package com.navkiraninfotech.assignment.ui.fragments.appListFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.navkiraninfotech.assignment.R
import com.navkiraninfotech.assignment.adapter.AppListVewAdapter
import com.navkiraninfotech.assignment.databinding.FragmentAppListBinding
import com.navkiraninfotech.assignment.dataobjects.AppListResponse
import com.navkiraninfotech.assignment.interfaces.onCheckClickListener
import java.util.Locale

class AppListFragment : Fragment(R.layout.fragment_app_list), onCheckClickListener {
    private lateinit var binding: FragmentAppListBinding

    private lateinit var viewModel: AppListViewModel
    private var appList = ArrayList<AppListResponse.AppList>()
    private lateinit var itemAdapter: AppListVewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAppListBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[AppListViewModel::class.java]
        itemAdapter = AppListVewAdapter(appList,this)
        binding.rvAppList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = itemAdapter
            showShimmerEffect()
        }
        viewModel.data.observe(viewLifecycleOwner, Observer {
            hideShimmerEffect()
            appList = it as ArrayList<AppListResponse.AppList>
            if (appList.size > 0) {
                showNoData(false)
                itemAdapter.setAppList(appList)
            } else
                showNoData(true)
        })

        binding.searchItem.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                filter(editable.toString())
            }
        })

        return binding.root
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist = ArrayList<AppListResponse.AppList>()
        try {
            appList.indices.asSequence()
                .filter {
                    appList[it].app_name.lowercase(Locale.getDefault())
                        .contains(text.lowercase(Locale.getDefault()))
                }
                .forEach { filteredlist.add(appList[it]) }
        } catch (e: Exception) {
            itemAdapter.setAppList(appList)
        }
        itemAdapter.setAppList(filteredlist)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AppListFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
    }

    private fun showNoData(isNoData: Boolean) {
        when (isNoData) {
            true -> {
                binding.rvAppList.visibility = View.GONE
                binding.llNoData.visibility = View.VISIBLE
            }

            false -> {
                binding.rvAppList.visibility = View.VISIBLE
                binding.llNoData.visibility = View.GONE
            }
        }
    }

    private fun showShimmerEffect() {
        binding.rvAppList.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.rvAppList.hideShimmer()
    }

    override fun onCheckClicked(data: AppListResponse.AppList, boolean: Boolean) {
        if (boolean) data.status = "Active"
        else data.status = "Inactive"
        Toast.makeText(
            requireContext(),
            data.status,
            Toast.LENGTH_SHORT
        ).show()
    }
}