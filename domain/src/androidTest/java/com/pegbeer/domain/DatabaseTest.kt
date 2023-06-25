package com.pegbeer.domain

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.pegbeer.domain.dao.ItemDao
import com.pegbeer.domain.model.Item
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DatabaseTest {

    @get:Rule val hiltRule = HiltAndroidRule(this)

    private lateinit var database:BuyerDatabase
    private lateinit var dao: ItemDao

    @Before
    fun setup() {
        hiltRule.inject()
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BuyerDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.itemDao
    }

    @After
    fun cleanup(){
        database.close()
    }

    @Test
    fun testInsertAnItem():Unit = runBlocking{
        val testItem = Item(id = "1", tipoCompra = "Efectivo")
        dao.insert(testItem)
        val insertedItem = dao.getById(testItem.id, testItem.tipoCompra)
        assertNotNull(insertedItem)
        assertEquals(insertedItem,testItem)
    }

}