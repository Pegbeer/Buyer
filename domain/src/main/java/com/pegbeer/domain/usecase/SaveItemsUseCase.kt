package com.pegbeer.domain.usecase

import com.pegbeer.domain.model.Item
import com.pegbeer.domain.model.Result
import com.pegbeer.domain.model.SortMode
import kotlinx.coroutines.flow.Flow

interface SaveItemsUseCase {
    suspend fun download()
    suspend fun refresh():List<Item>
    fun filterItems(sortMode: SortMode): Flow<Result<List<Item>>>
}