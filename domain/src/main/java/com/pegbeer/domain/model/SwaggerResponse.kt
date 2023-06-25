package com.pegbeer.domain.model

data class SwaggerResponse(
    val statusCode:String,
    val errorMessage:String? = null,
    val fieldError:String? = null,
    val data:CustomItem? = null,
    val result:CustomItem? = null
)