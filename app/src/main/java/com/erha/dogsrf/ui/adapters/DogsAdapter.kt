package com.erha.dogsrf.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erha.dogsrf.data.remote.model.DogDto
import com.erha.dogsrf.databinding.DogElementBinding

class DogsAdapter (
    private val games: MutableList<DogDto>,
    private val onGameClicked: (DogDto) -> Unit
): RecyclerView.Adapter<DogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = DogElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogViewHolder(binding)
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {

        val game = games[position]

        holder.bind(game)

        holder.itemView.setOnClickListener {
            //Para los clicks a los juegos
            onGameClicked(game)
        }

    }


}