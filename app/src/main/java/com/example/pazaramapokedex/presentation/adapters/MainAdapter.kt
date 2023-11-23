package com.example.pazaramapokedex.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pazaramapokedex.databinding.ItemPokemonBinding
import com.example.pazaramapokedex.domain.model.PokemonItemList
import com.example.pazaramapokedex.utils.loadImage
import com.example.pokedex.domain.model.Result

import javax.inject.Inject


class MainAdapter @Inject constructor() : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var onItemClickListener : ((Int) -> Unit)? = null

    class ViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<PokemonItemList>() {
        override fun areItemsTheSame(oldItem: PokemonItemList, newItem: PokemonItemList): Boolean {
            return oldItem == newItem
        }

        //TODO .name kısmına bakılacak
        override fun areContentsTheSame(oldItem: PokemonItemList, newItem: PokemonItemList): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var pokemonResponseList: MutableList<PokemonItemList>
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

        holder.binding.name.text = pokemonResponseList[position].name

        holder.binding.imgPokemon.loadImage(pokemonResponseList[position].imgUrl)


        holder.itemView.setOnClickListener() {
            onItemClickListener?.let {
                it(pokemonResponseList[position].id)
            }
        }

    }

    override fun getItemCount(): Int {
        return pokemonResponseList.size
    }
}