package com.example.pazaramapokedex.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pazaramapokedex.R
import com.example.pazaramapokedex.databinding.FragmentMainBinding
import com.example.pazaramapokedex.domain.model.PokemonImage
import com.example.pazaramapokedex.domain.model.PokemonItemList
import com.example.pazaramapokedex.domain.model.Type
import com.example.pazaramapokedex.presentation.adapters.MainAdapter
import com.example.pazaramapokedex.utils.Status
import com.example.pazaramapokedex.utils.extractId
import com.example.pokedex.domain.model.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment @Inject constructor(
    val pokemonRecyclerAdapter: MainAdapter
) : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        //pokemonRecyclerAdapter = MainAdapter()

        viewModel.getPokemons(0, 0)
        subscribeToObservers()
        setColor()

        binding.recyclerView.adapter = pokemonRecyclerAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),3)

        pokemonRecyclerAdapter.setOnItemClickListener {
            val args = Bundle()
            args.putInt("url", it)
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment,args)
        }
    }

    private fun subscribeToObservers() {

        viewModel.pokemonList.observe(viewLifecycleOwner, Observer { pokemonResponse ->

            when (pokemonResponse.status) {
                Status.SUCCESS -> {
                    binding.progressbar.visibility = View.GONE

                    //pokemonResponse.data?.results?.map { viewModel.getImage(it?.url!!) }

                    val data = pokemonResponse.data?.results as MutableList<Result>

                    for (i in data) {
                        viewModel.getPokemonImage(i.url?.extractId()!!)
                    }

                    viewModel.imageList.observe(viewLifecycleOwner, Observer {

                        if (it.size.equals(20)) {
                            viewModel.setPokemonItemData()

                            val data = viewModel.pokemonItemList.value

                            pokemonRecyclerAdapter.pokemonResponseList = data as MutableList<PokemonItemList>
                        }
                    })
                }

                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        pokemonResponse.message ?: "Error",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.progressbar.visibility = View.GONE
                }

                Status.LOADING -> {
                    binding.progressbar.visibility = View.VISIBLE

                }
            }

        })
    }

    private fun setColor() {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(),R.color.colorPrimary)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}