package com.pegbeer.buyer.di

import android.content.Context
import androidx.room.Room
import com.pegbeer.domain.BuyerDatabase
import com.pegbeer.domain.dao.ItemDao
import com.pegbeer.domain.repository.ItemRepository
import com.pegbeer.domain.usecase.SaveItemUseCaseImpl
import com.pegbeer.domain.usecase.SaveItemsUseCase
import com.pegbeer.infrastructure.AppRepository
import com.pegbeer.infrastructure.database.RoomRepository
import com.pegbeer.infrastructure.network.ApiService
import com.pegbeer.infrastructure.network.RetrofitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideApiService():ApiService{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://testapipayer.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BuyerDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BuyerDatabase::class.java,
            "local_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideItemDao(database: BuyerDatabase):ItemDao{
        return database.itemDao
    }

    @Provides
    @Singleton
    fun provideItemRepository(
        @ApplicationContext context: Context,
        apiService: ApiService,
        itemDao: ItemDao
    ): ItemRepository {
        val retrofitRepository = RetrofitRepository(apiService, itemDao)
        val roomRepository = RoomRepository(itemDao)
        return AppRepository(context, retrofitRepository, roomRepository)
    }

    @Provides
    @Singleton
    fun provideSaveItemsUseCase(itemRepository: ItemRepository):SaveItemsUseCase{
        return SaveItemUseCaseImpl(itemRepository)
    }

}