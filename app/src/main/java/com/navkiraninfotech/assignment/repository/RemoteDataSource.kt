package com.manishjandu.foodify.data

import com.navkiraninfotech.assignment.repository.AssignmentRestApi
import com.navkiraninfotech.assignment.dataobjects.AppListResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val callApi: AssignmentRestApi
) {
    suspend fun appListData(queries:String): Response<AppListResponse.Response> {
        return callApi.getAppList(queries)
    }
}