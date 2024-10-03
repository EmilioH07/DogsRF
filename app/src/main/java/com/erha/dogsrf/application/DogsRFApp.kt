package com.erha.dogsrf.application

import android.app.Application
import com.erha.dogsrf.data.DogRepository
import com.erha.dogsrf.data.remote.RetrofitHelper

class DogsRFApp : Application() {

    private val retrofit by lazy{
        RetrofitHelper().getRetrofit()
    }

    val repository by lazy {
        DogRepository(retrofit)
    }

}