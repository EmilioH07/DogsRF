package com.erha.dogsrf.ui.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.erha.dogsrf.R
import com.erha.dogsrf.application.DogsRFApp
import com.erha.dogsrf.data.DogRepository
import com.erha.dogsrf.data.remote.model.DogDto
import com.erha.dogsrf.databinding.FragmentDogsListBinding
import com.erha.dogsrf.ui.adapters.DogsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogsListFragment : Fragment() {

    private var _binding: FragmentDogsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: DogRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDogsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obteniendo la instancia del repositorio
        repository = (requireActivity().application as DogsRFApp).repository

        // Mostrar la imagen de carga
        binding.ivLoading.visibility = View.VISIBLE
        binding.rvDogs.visibility = View.GONE

        // Obtener la lista de perros desde la API
        val call: Call<List<DogDto>> = repository.getDogList()

        call.enqueue(object : Callback<List<DogDto>> {
            override fun onResponse(
                call: Call<List<DogDto>>,
                response: Response<List<DogDto>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { dogs ->
                        // Convertir a MutableList
                        val mutableDogsList = dogs.toMutableList()

                        // Configurar el RecyclerView
                        binding.rvDogs.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = DogsAdapter(mutableDogsList) { dog ->
                                // Reproducir audio
                                playDogSound()

                                // Acción al seleccionar un perro
                                dog.id?.let { id ->
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, DogDetailFragment.newInstance(id))
                                        .addToBackStack(null)
                                        .commit()
                                }
                            }
                        }


                        // Ocultar la imagen de carga y mostrar el RecyclerView
                        binding.ivLoading.visibility = View.GONE
                        binding.rvDogs.visibility = View.VISIBLE
                    } ?: run {
                        Toast.makeText(requireContext(), "No se encontraron perros", Toast.LENGTH_SHORT).show()
                        binding.ivLoading.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                    binding.ivLoading.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<List<DogDto>>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error: No hay conexión disponible",
                    Toast.LENGTH_SHORT
                ).show()
                binding.ivLoading.visibility = View.GONE
            }
        })
    }

    private fun playDogSound() {
        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.dog_bark)
        mediaPlayer.start() // Reproduce el sonido

        // Libera el recurso una vez que el audio finalice
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}






