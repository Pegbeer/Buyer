package com.pegbeer.infrastructure.database

import com.pegbeer.domain.dao.ItemDao
import com.pegbeer.domain.model.Item
import com.pegbeer.domain.model.SortMode
import com.pegbeer.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class RoomRepository(private val itemDao: ItemDao) : ItemRepository {

    override suspend fun downloadItems() {
        return
    }

    override suspend fun getAll(): List<Item> {
        return itemDao.getAll()
    }

    override suspend fun filterItemsBySortMode(sortMode: SortMode): List<Item> {
        if(sortMode == SortMode.All) return itemDao.getAll()
        return itemDao.getItemsByTipoCompra(sortMode.getValue())
    }


}