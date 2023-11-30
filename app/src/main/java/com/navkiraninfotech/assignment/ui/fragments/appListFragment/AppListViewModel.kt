package com.navkiraninfotech.assignment.ui.fragments.appListFragment

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishjandu.foodify.data.Repository
import com.navkiraninfotech.assignment.dataobjects.AppListResponse
import com.navkiraninfotech.assignment.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class AppListViewModel :ViewModel() {
    val data=MutableLiveData<List<AppListResponse.AppList>>()
    fun setData(text: List<AppListResponse.AppList>)
    {
        data.value= text
    }
}