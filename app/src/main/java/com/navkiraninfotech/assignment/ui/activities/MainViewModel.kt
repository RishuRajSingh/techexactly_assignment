package com.navkiraninfotech.assignment.ui.activities

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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

@HiltViewModel
class MainViewModel@Inject constructor(private val repository: Repository) : ViewModel() {
    private var _appListResponse = MutableLiveData<NetworkResult<AppListResponse.Response>>()
    val appListResponse: LiveData<NetworkResult<AppListResponse.Response>> get() = _appListResponse

    fun getAppList(queries: String, context: Context) = viewModelScope.launch {
        getAppListSafeCall(queries,context)
    }

    private suspend fun getAppListSafeCall(queries: String, context: Context) {
        _appListResponse.value = NetworkResult.Loading()
        if (hasInternetConnection(context)) {
            try {
                val result = repository.remote.appListData(queries)
                _appListResponse.value = handleAppListResponse(result)

                val appData = _appListResponse.value!!.data
            } catch (e: Exception) {
                _appListResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            _appListResponse.postValue(NetworkResult.Error("No Internet connection."))
        }
    }

    private fun handleAppListResponse(response: Response<AppListResponse.Response>): NetworkResult<AppListResponse.Response>? {
        when {
            response.message().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API Not Responding.")
            }

            response.isSuccessful -> {
                val result = response.body()!!
                return NetworkResult.Success(result)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(context:Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}