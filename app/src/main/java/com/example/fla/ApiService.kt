package com.example.fla.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/api/v1/food/upload-label") // Change endpoint accordingly
    suspend fun uploadImage(@Part image: MultipartBody.Part): Response<ResponseBody>
}
