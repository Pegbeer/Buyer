package com.pegbeer.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pegbeer.domain.dao.ItemDao
import com.pegbeer.domain.model.Item

@Database(entities = [
    Item::class
], version = 1, exportSchema = false)
abstract class BuyerDatabase : RoomDatabase(){

    abstract val itemDao:ItemDao
}