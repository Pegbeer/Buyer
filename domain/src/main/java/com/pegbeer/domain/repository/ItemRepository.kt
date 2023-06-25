package com.pegbeer.domain.repository


import com.pegbeer.domain.model.Item
import com.pegbeer.domain.model.SortMode

interface ItemRepository {
    suspend fun downloadItems()
    suspend fun getAll():List<Item>
    suspend fun filterItemsBySortMode(sortMode: SortMode): List<Item>
}