package com.example.pazaramapokedex.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pazaramapokedex.databinding.ItemPokemonBinding
import com.example.pazaramapokedex.utils.extractId
import com.example.pazaramapokedex.utils.loadImage
import com.example.pokedex.domain.model.Result

import javax.inject.Inject


class MainAdapter @Inject constructor() : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var onItemClickListener : ((Int) -> Unit)? = null

    class ViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

        //TODO .name kısmına bakılacak
        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.url?.extractId() == newItem.url?.extractId()
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var pokemonResponseList: MutableList<Result>
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
        holder.binding.imgPokemon.loadImage(pokemonResponseList[position].getImageUrl())

        holder.itemView.setOnClickListener() {
            onItemClickListener?.let {
                it(pokemonResponseList[position].url?.extractId()!!)
            }
        }

    }

    override fun getItemCount(): Int {
        return pokemonResponseList.size
    }
}