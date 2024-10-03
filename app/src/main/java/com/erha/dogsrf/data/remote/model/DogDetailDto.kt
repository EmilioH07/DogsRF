package com.erha.dogsrf.data.remote.model

import com.google.gson.annotations.SerializedName

class DogDetailDto (

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("image")
    var image: String? = null,

    @SerializedName("long_desc")
    var longDesc: String? = null,

    @SerializedName("size")
    var size: String? = null,

    @SerializedName("daily_food")
    var dailyfood: String? = null,

    @SerializedName("life_expectancy")
    var lifeexpectancy: String? = null,

    @SerializedName("coat_type")
    var coattype: String? = null,

    @SerializedName("temperament")
    var temperament: String? = null,

    @SerializedName("exercise_needs")
    var exerciseneeds: String? = null
)