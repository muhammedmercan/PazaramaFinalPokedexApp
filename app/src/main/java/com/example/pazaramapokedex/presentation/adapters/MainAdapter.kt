package com.example.pazaramapokedex.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pazaramapokedex.databinding.ItemPokemonBinding
import com.example.pazaramapokedex.utils.formatId
import com.example.pazaramapokedex.utils.loadImage
import com.example.pokedex.domain.model.PokemonBasic

import javax.inject.Inject


class MainAdapter @Inject constructor() : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var onItemClickListener : ((Int) -> Unit)? = null

    class ViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<PokemonBasic>() {
        override fun areItemsTheSame(oldItem: PokemonBasic, newItem: PokemonBasic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PokemonBasic, newItem: PokemonBasic): Boolean {

            return  oldItem == newItem

        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var pokemonResponseList: List<PokemonBasic>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    fun setOnItemClickListener(listener : (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val pokemonId = pokemonResponseList[position].id

        holder.binding.txtItemPokemonId.text = pokemonId?.formatId()

        holder.binding.name.text = pokemonResponseList[position].name
        holder.binding.imgPokemon.loadImage(pokemonResponseList[position].getImageUrl())

        holder.itemView.setOnClickListener() {
            onItemClickListener?.let {
                it(pokemonId!!)
            }
        }

    }

    override fun getItemCount(): Int {
        return pokemonResponseList.size
    }
}