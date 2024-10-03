package com.erha.dogsrf.data.remote.model

import com.google.gson.annotations.SerializedName

class DogDto (

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("image")
    var image: String? = null,

    @SerializedName("title")
    var title: String? = null

)
