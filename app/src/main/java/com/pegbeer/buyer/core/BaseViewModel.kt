package com.pegbeer.buyer.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.microsoft.appcenter.crashes.Crashes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val refreshing: LiveData<Boolean>
        get() = _refreshing.distinctUntilChanged()
    private val _refreshing = MutableLiveData<Boolean>()

    val snackbar: LiveData<String?>
        get() = mutableSnackbar
    protected val mutableSnackbar = SingleLiveEvent<String?>()

    protected fun launchRequest(
        onSuccess: () -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
        block: suspend CoroutineScope.() -> Unit,
    ): Job {
        return viewModelScope.launch {
            try {
                _refreshing.value = true
                mutableSnackbar.value = null
                block()
                onSuccess()
            } catch (exception: Exception) {
                onFailure(exception)
                Crashes.trackError(exception)
                mutableSnackbar.value = exception.message ?: "An error occurred while trying to complete the collect launchRequest"
            } finally {
                _refreshing.value = false
            }
        }
    }
}