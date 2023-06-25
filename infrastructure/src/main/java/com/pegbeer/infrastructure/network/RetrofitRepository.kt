package com.pegbeer.infrastructure.network

import com.pegbeer.domain.dao.ItemDao
import com.pegbeer.domain.model.Item
import com.pegbeer.domain.model.SortMode
import com.pegbeer.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class RetrofitRepository(private val apiService: ApiService, private val itemDao: ItemDao) :  ItemRepository{

    override suspend fun downloadItems() {
        val response = apiService.getAllItems()
        if(response.isSuccessful){
            val result = response.body()!!
            val items = result.data?.item ?: emptyList()
            itemDao.insertAll(items)
        }
    }

    override suspend fun getAll(): List<Item> {
        val response = apiService.getAllItems()
        if (response.isSuccessful){
            val result = response.body()!!
            return result.data?.item ?: emptyList()
        }
        return emptyList()
    }

    override suspend fun filterItemsBySortMode(sortMode: SortMode): List<Item> {
        val items = getAll()
        if(sortMode == SortMode.All) return items
        return items.filter { it.tipoCompra == sortMode.getValue() }.toList()
    }


}