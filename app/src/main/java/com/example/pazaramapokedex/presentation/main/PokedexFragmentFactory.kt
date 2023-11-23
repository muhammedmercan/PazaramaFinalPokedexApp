package com.example.pazaramapokedex.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.pazaramapokedex.presentation.adapters.MainAdapter
import com.example.pazaramapokedex.presentation.adapters.TypesAdapter
import com.example.pazaramapokedex.presentation.detail.DetailFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class PokedexFragmentFactory @Inject constructor(
    private val mainRecyclerAdapter: MainAdapter,
    private val typesRecyclerAdapter: TypesAdapter,
    private val glide : RequestManager,
) : FragmentFactory() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            MainFragment::class.java.name -> MainFragment(mainRecyclerAdapter)
            DetailFragment::class.java.name -> DetailFragment(typesRecyclerAdapter)

            //ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            //ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            //ArtFragment::class.java.name -> ArtFragment(artRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}