package com.erha.dogsrf.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.erha.dogsrf.R
import com.erha.dogsrf.databinding.ActivityMainBinding
import com.erha.dogsrf.ui.fragments.DogsListFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private var user: FirebaseUser? = null
    private var userId: String? = null

    /*private lateinit var repository: GameRepository
    private lateinit var retrofit: Retrofit*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        //Mostramos el fragment inicial DogsListFragment
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DogsListFragment())
                .commit()
        }


        /*//Obteniendo la instancia de retrofit
        retrofit = RetrofitHelper().getRetrofit()

        //Obteniendo el repositorio
        repository = GameRepository(retrofit)*/

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        firebaseAuth = FirebaseAuth.getInstance()

        user = firebaseAuth.currentUser
        userId = user?.uid


    }


}

    /*fun click(view: View) {

        val call: Call<MutableList<GameDto>> = repository.getGames("cm/games/games_list.php")

        call.enqueue(object: Callback<MutableList<GameDto>>{
            override fun onResponse(
                call: Call<MutableList<GameDto>>,
                response: Response<MutableList<GameDto>>
            ) {
                Log.d(Constants.LOGTAG, "Respuesta del servidor: ${response.body()}")
            }

            override fun onFailure(p0: Call<MutableList<GameDto>>, p1: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Error: No hay conexión disponible",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }*/
