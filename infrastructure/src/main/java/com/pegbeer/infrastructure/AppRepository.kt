package com.pegbeer.infrastructure

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import com.pegbeer.domain.model.Item
import com.pegbeer.domain.model.SortMode
import com.pegbeer.domain.repository.ItemRepository
import com.pegbeer.infrastructure.database.RoomRepository
import com.pegbeer.infrastructure.network.RetrofitRepository

class AppRepository(
    private val context:Context,
    private val retrofitRepository: RetrofitRepository,
    private val roomRepository: RoomRepository
) : ItemRepository {

    override suspend fun downloadItems() {
        retrofitRepository.downloadItems()
    }

    override suspend fun getAll(): List<Item> {
        return if(isNetworkAvailable()){
            return retrofitRepository.getAll()
        }else{
            roomRepository.getAll()
        }
    }

    override suspend fun filterItemsBySortMode(sortMode: SortMode): List<Item> {
        return if(isNetworkAvailable()){
            retrofitRepository.filterItemsBySortMode(sortMode)
        }else{
            roomRepository.filterItemsBySortMode(sortMode)
        }
    }

    private fun isNetworkAvailable():Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        if (connectivityManager != null) {
            val network: Network? = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        }

        return false
    }

}