package com.pegbeer.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pegbeer.domain.model.Item

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items:List<Item>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item:Item)

    @Query("SELECT * FROM item")
    suspend fun getAll():List<Item>

    @Query("SELECT * FROM item WHERE tipoCompra = :tipoCompra")
    suspend fun getItemsByTipoCompra(tipoCompra: String): List<Item>

    @Query("SELECT * FROM item WHERE id = :id AND tipoCompra = :tipoCompra")
    suspend fun getById(id:String,tipoCompra: String):Item
}