package com.pegbeer.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Item (
    @PrimaryKey
    val id: String = "",
    val tipoCompra: String = "",
    val origen: String = "",
    val concepto: String = "",
    val signo: String = "",
    val delimitador: String = "",
    val hexadecimal: String = "",
    val tipoCargoID: String = "",
    val tipoAbonoID: String = ""
)