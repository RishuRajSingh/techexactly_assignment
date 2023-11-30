package com.navkiraninfotech.assignment.repository

import com.navkiraninfotech.assignment.dataobjects.AppListResponse
import com.navkiraninfotech.assignment.utils.Constants
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface AssignmentRestApi {
    @POST(Constants.getAppList)
    suspend fun getAppList(@Query("kid_id") kidId: String): Response<AppListResponse.Response>
}