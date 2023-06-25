package com.pegbeer.buyer.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.pegbeer.buyer.core.BaseViewModel
import com.pegbeer.domain.model.Item
import com.pegbeer.domain.model.SortMode
import com.pegbeer.domain.usecase.SaveItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val itemUseCases: SaveItemsUseCase
) : BaseViewModel() {


    val itemsOnRefresh = MutableLiveData(emptyList<Item>())

    val preferenceFilter = MutableLiveData(SortMode.All)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getItems() = preferenceFilter.asFlow().flatMapLatest {
        itemUseCases.filterItems(it)
    }

    internal fun refreshItems(){
        launchRequest {
            itemsOnRefresh.value = itemUseCases.refresh()
        }
    }


    fun showSnackBar(msg:String?){
        mutableSnackbar.value = msg
    }
}