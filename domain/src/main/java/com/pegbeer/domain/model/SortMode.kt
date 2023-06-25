package com.pegbeer.domain.model

enum class SortMode(private val value:String) {
    All("All"),
    CreditCard("Tarjeta Credito"),
    Cash("Efectivo"),
    TigoMoney("TigoMoney"),
    Despacho("despacho");

    fun getValue():String{
        return value
    }
}