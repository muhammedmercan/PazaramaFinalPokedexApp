package com.example.pazaramapokedex.presentation.adapters

import android.content.res.Resources.Theme
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pazaramapokedex.R
import com.example.pazaramapokedex.databinding.ItemTypeBinding
import com.example.pazaramapokedex.domain.model.Type
import com.example.pazaramapokedex.utils.Resource
import com.example.pazaramapokedex.utils.capitalize
import com.example.pokedex.domain.model.SinglePokemonResponse
import javax.inject.Inject


class TypesAdapter @Inject constructor() : RecyclerView.Adapter<TypesAdapter.ViewHolder>() {


    class ViewHolder(val binding: ItemTypeBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Type>() {
        override fun areItemsTheSame(oldItem: Type, newItem: Type): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Type, newItem: Type): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var typeList: MutableList<Type>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    fun resetTypeList() {
        typeList = mutableListOf()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTypeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val type = typeList[position].type?.name
        when(type) {

            //TODO daha iyi bir yöntem var mı bakılacak
            "normal" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_normal)
            "fire" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_fire)
            "water" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_water)
            "electric" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_electric)
            "grass" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_grass)
            "ice" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_ice)
            "fighting" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_fighting)
            "poison" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_poison)
            "ground" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_ground)
            "flying" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_flying)
            "psychic" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_psychic)
            "bug" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_bug)
            "rock" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_rock)
            "ghost" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_ghost)
            "dragon" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_dragon)
            "dark" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_dark)
            "steel" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_steel)
            "fairy" -> holder.binding.txtItemType.setBackgroundResource(R.color.type_fairy)
        }

        holder.binding.txtItemType.text = type?.capitalize()

    }

    override fun getItemCount(): Int {
        return typeList.size
    }
}