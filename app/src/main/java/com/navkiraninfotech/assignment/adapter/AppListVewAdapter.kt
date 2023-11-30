package com.navkiraninfotech.assignment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.navkiraninfotech.assignment.databinding.AppItemListViewBinding
import com.navkiraninfotech.assignment.dataobjects.AppListResponse
import com.navkiraninfotech.assignment.interfaces.onCheckClickListener

@SuppressLint("StaticFieldLeak")
private lateinit var context: Context

class AppListVewAdapter(
    private var list: List<AppListResponse.AppList>,private val listener: onCheckClickListener
) : RecyclerView.Adapter<AppListVewAdapter.ViewHolder>() {

    fun setAppList(list: List<AppListResponse.AppList>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            AppItemListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position], list.size, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private val binding: AppItemListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindItems(item: AppListResponse.AppList, size: Int, position: Int) {
            binding.apply {
                Glide.with(context).load(item.app_icon).into(imgApp);
                tvAppName.text = item.app_name
                when {
                    item.status.equals("Active",true) -> switchBtn.isChecked = true
                    else -> switchBtn.isChecked = false
                }
                switchBtn.setOnCheckedChangeListener { buttonView, isChecked ->
                    listener.onCheckClicked(item,isChecked)
                }

            }

        }
    }
}