package com.erha.dogsrf.ui.fragments

import android.content.pm.ActivityInfo
import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.erha.dogsrf.R
import com.erha.dogsrf.application.DogsRFApp
import com.erha.dogsrf.data.DogRepository
import com.erha.dogsrf.data.remote.model.DogDetailDto
import com.erha.dogsrf.databinding.FragmentDogDetailBinding
import com.erha.dogsrf.utils.Constants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val GAME_ID = "game_id"

class DogDetailFragment : Fragment() {

    private var gameId: String? = null

    private var _binding: FragmentDogDetailBinding? = null
    private val binding get()  = _binding!!

    private lateinit var repository: DogRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            gameId = args.getString(GAME_ID)
            Log.d(Constants.LOGTAG, getString(R.string.log_id_received))

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDogDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Se manda llamar ya cuando el fragment es visible en pantalla
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Obteniendo la instancia al repositorio
        repository = (requireActivity().application as DogsRFApp).repository

        gameId?.let{ id ->
            //Hago la llamada al endpoint para consumir los detalles del juego

            //val call: Call<GameDetailDto> = repository.getGameDetail(id)

            //Para apiary
            val call: Call<DogDetailDto> = repository.getDogDetail(id)

            call.enqueue(object: Callback<DogDetailDto> {
                override fun onResponse(p0: Call<DogDetailDto>, response: Response<DogDetailDto>) {

                    binding.apply {
                        pbLoading.visibility = View.GONE

                        //Aquí utilizamos la respuesta exitosa y asignamos los valores a las vistas
                        tvTitle.text = response.body()?.title

                        Glide.with(requireActivity())
                            .load(response.body()?.image)
                            .into(ivImage)

                        /*Picasso.get()
                            .load(response.body()?.image)
                            .into(ivImage)*/


                        tvSize.text = response.body()?.size

                        tvDaily.text = response.body()?.dailyfood

                        tvLife.text = response.body()?.lifeexpectancy

                        tvCoat.text = response.body()?.coattype

                        tvTemperament.text = response.body()?.temperament

                        tvExercise.text = response.body()?.exerciseneeds

                        val videoId = response.body()?.video

                        binding.ytpvVideo.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                val videoId = response.body()?.video
                                if (videoId != null && videoId.isNotEmpty()) {
                                    youTubePlayer.cueVideo(videoId, 0f)
                                }
                            }
                        })


                    }



                }

                override fun onFailure(p0: Call<DogDetailDto>, p1: Throwable) {
                    //Manejo del error de conexión
                }

            })
        }

        lifecycle.addObserver(binding.ytpvVideo)
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.ytpvVideo.release() // Liberar recursos del reproductor
    }

    companion object {
        @JvmStatic
        fun newInstance(gameId: String) =
            DogDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(GAME_ID, gameId)
                }
            }
    }
}