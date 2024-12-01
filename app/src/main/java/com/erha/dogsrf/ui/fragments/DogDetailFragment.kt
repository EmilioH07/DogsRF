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
import com.google.android.gms.maps.OnMapReadyCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


private const val GAME_ID = "game_id"

class DogDetailFragment : Fragment(), OnMapReadyCallback {

    private var gameId: String? = null
    private var _binding: FragmentDogDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: DogRepository
    private var mMap: GoogleMap? = null  // Mapa ahora puede ser nulo

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar el repositorio
        repository = (requireActivity().application as DogsRFApp).repository

        // Obtener la instancia del mapa
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this) // Llamamos al callback onMapReady

        gameId?.let { id ->
            val call: Call<DogDetailDto> = repository.getDogDetail(id)

            call.enqueue(object : Callback<DogDetailDto> {
                override fun onResponse(p0: Call<DogDetailDto>, response: Response<DogDetailDto>) {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        val dog = response.body()

                        tvTitle.text = dog?.title
                        Glide.with(requireActivity()).load(dog?.image).into(ivImage)
                        tvSize.text = dog?.size
                        tvDaily.text = dog?.dailyfood
                        tvLife.text = dog?.lifeexpectancy
                        tvCoat.text = dog?.coattype
                        tvTemperament.text = dog?.temperament
                        tvExercise.text = dog?.exerciseneeds

                        val videoId = response.body()?.video

                        binding.ytpvVideo.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                val videoId = response.body()?.video
                                if (videoId != null && videoId.isNotEmpty()) {
                                    youTubePlayer.cueVideo(videoId, 0f)
                                }
                            }
                        })

                        // Si tenemos latitud y longitud, colocamos el marcador en el mapa
                        dog?.latitud?.toDoubleOrNull()?.let { lat ->
                            dog.longitud?.toDoubleOrNull()?.let { lng ->
                                // Solo agregamos el marcador si el mapa ya está listo
                                if (mMap != null) {
                                    try {
                                        val location = LatLng(lat, lng)
                                        mMap?.addMarker(MarkerOptions().position(location).title(dog.title))
                                        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f))
                                    } catch (e: Exception) {
                                        Log.e("DogDetailFragment", "Error al agregar el marcador: ${e.message}")
                                    }
                                } else {
                                    Log.w("DogDetailFragment", "El mapa no está listo aún")
                                }
                            }
                        }
                    }
                }

                override fun onFailure(p0: Call<DogDetailDto>, p1: Throwable) {
                    Log.e("DogDetailFragment", "Error en la respuesta: ${p1.message}")
                }
            })
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.uiSettings?.isZoomControlsEnabled = true
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


