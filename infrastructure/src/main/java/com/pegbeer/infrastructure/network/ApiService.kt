package com.pegbeer.infrastructure.network

import com.pegbeer.domain.model.SwaggerResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/api/TipoCompraMovimiento")
    suspend fun getAllItems(): Response<SwaggerResponse>
}