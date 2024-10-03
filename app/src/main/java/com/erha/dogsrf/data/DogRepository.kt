package com.erha.dogsrf.data

import com.erha.dogsrf.data.remote.DogApi
import com.erha.dogsrf.data.remote.model.DogDetailDto
import com.erha.dogsrf.data.remote.model.DogDto
import retrofit2.Call
import retrofit2.Retrofit

class DogRepository(
    private val retrofit: Retrofit
) {
    private val dogApi: DogApi = retrofit.create(DogApi::class.java)

    // Para obtener la lista de perros
    fun getDogList(): Call<List<DogDto>> =
        dogApi.getDogList() // Llama al método correspondiente en DogApi

    // Para obtener los detalles de un perro específico
    fun getDogDetail(id: String): Call<DogDetailDto> =
        dogApi.getDogDetail(id) // Llama al método correspondiente en DogApi
}


