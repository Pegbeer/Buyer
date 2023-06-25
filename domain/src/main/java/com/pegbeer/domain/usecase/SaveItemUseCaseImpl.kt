package com.pegbeer.domain.usecase

import com.pegbeer.domain.model.Item
import com.pegbeer.domain.model.SortMode
import com.pegbeer.domain.model.Result
import com.pegbeer.domain.repository.ItemRepository
import kotlinx.coroutines.flow.flow


class SaveItemUseCaseImpl(private val itemRepository: ItemRepository) : SaveItemsUseCase {

    override suspend fun download() {
        itemRepository.downloadItems()
    }

    override suspend fun refresh(): List<Item> {
        return itemRepository.getAll()
    }

    override fun filterItems(sortMode: SortMode) = flow {
        emit(Result.loading())
        val result = itemRepository.filterItemsBySortMode(sortMode)
        emit(Result.success(result))
    }


}