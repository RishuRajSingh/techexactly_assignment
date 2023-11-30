package com.navkiraninfotech.assignment.interfaces

import com.navkiraninfotech.assignment.dataobjects.AppListResponse

interface onCheckClickListener {
    fun onCheckClicked(data: AppListResponse.AppList,boolean: Boolean)
}