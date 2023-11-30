package com.navkiraninfotech.assignment.dataobjects

class AppListResponse {
    data class Response(
        val `data`: Data,
        val message: String,
        val success: Boolean
    )
    data class Data(
        val app_list: List<AppList>,
        val usage_access: Int
    )
    data class AppList(
        val app_icon: String,
        val app_id: Int,
        val app_name: String,
        val app_package_name: String,
        val fk_kid_id: Int,
        val kid_profile_image: String,
        var status: String
    )
}