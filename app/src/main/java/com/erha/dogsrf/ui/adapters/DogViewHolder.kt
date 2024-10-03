package com.erha.dogsrf.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erha.dogsrf.data.remote.model.DogDto
import com.erha.dogsrf.databinding.DogElementBinding

class DogViewHolder (
    private val binding: DogElementBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(game: DogDto){

        binding.tvTitle.text = game.title

        //Con Glide
        Glide.with(binding.root.context)
            .load(game.image)
            .into(binding.ivThumbnail)

        //Con Picasso
        /*Picasso.get()
            .load(game.thumbnail)
            .into(binding.ivThumbnail)*/
    }

}