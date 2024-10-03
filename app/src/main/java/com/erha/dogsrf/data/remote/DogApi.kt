package com.erha.dogsrf.data.remote

import com.erha.dogsrf.data.remote.model.DogDetailDto
import com.erha.dogsrf.data.remote.model.DogDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface DogApi {

    // Endpoint para obtener la lista de perros
    @GET("dogs/dog_list")
    fun getDogList(): Call<List<DogDto>> // Este método debe devolver Call<List<DogDto>>

    // Endpoint para obtener los detalles de un perro específico
    @GET("dogs/dog_list/{id}")
    fun getDogDetail(
        @Path("id") id: String // Este método debe devolver Call<DogDetailDto>
    ): Call<DogDetailDto>
}


    /*//cm/games/games_list.php
    @GET
    fun getDogs(
        @Url url: String?
    ): Call<MutableList<DogDto>>
    //se mandaría llamar así: getGames("cm/games/games_list.php")

    @GET("cm/games/games_list.php")
    fun getDogs(): Call<MutableList<DogDto>>
    //se mandaría llamar así: getGames()

    //cm/games/game_detail.php?id=21357
    @GET("cm/games/game_detail.php?")
    fun getDogDetail(
        @Query("id") id: String?*//*,
        @Query("name") name: String?*//*
    ): Call<DogDetailDto>
    //se mandaría llamar así: getGameDetail("21357")

    //cm/games/21357/amaury
    @GET("cm/games/{id}/{name}")
    fun getGameTest(
        @Path("id") id: String?,
        @Path("name") name: String?
    ): Call<DogDetailDto>
    //se mandaría llamar así: getGameTest("21357","amaury")

    //Para Apiary

    //https://private-a649a-games28.apiary-mock.com/games/games_list
    @GET("games/games_list")
    fun getDogsApiary(): Call<MutableList<DogDto>>

    //https://private-a649a-games28.apiary-mock.com/games/game_detail/21357
    @GET("games/game_detail/{id}")
    fun getDogDetailApiary(
        @Path("id") id: String?*/
    //): Call<DogDetailDto>


