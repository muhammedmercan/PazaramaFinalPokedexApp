package com.example.pazaramapokedex.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pazaramapokedex.databinding.ItemTypeBinding
import com.example.pazaramapokedex.domain.model.Type
import com.example.pazaramapokedex.utils.PokemonTypeUtils
import com.example.pazaramapokedex.utils.capitalize
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
        val color = PokemonTypeUtils.getTypeColor(type!!)

        holder.binding.txtItemType.setBackgroundResource(color)
        holder.binding.txtItemType.text = type?.capitalize()

    }

    override fun getItemCount(): Int {
        return typeList.size
    }
}