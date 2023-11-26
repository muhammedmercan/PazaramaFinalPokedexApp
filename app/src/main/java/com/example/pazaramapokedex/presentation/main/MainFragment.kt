package com.example.pazaramapokedex.presentation.main

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pazaramapokedex.R
import com.example.pazaramapokedex.databinding.FragmentMainBinding
import com.example.pazaramapokedex.presentation.adapters.MainAdapter
import com.example.pazaramapokedex.utils.removeLeadingZeros
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment @Inject constructor(
    val pokemonRecyclerAdapter: MainAdapter
) : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: MainViewModel

    private var currentPage = 1
    private var totalAvailablePages = 1

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

        onClick()
        viewModel.getPokemons()
        subscribeToObservers()
        setColor()
        onScroll()

        binding.recyclerView.adapter = pokemonRecyclerAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        binding.searchBar.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.search,0,0,0)
    }

    private fun subscribeToObservers() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pokemonList.collect {mainState ->

                binding.progressbar.isVisible = mainState.isLoading

                mainState.error.let {
                    Toast.makeText(requireContext(),"hata",Toast.LENGTH_SHORT)
                }

                    val data = pokemonRecyclerAdapter.pokemonResponseList
                    pokemonRecyclerAdapter.pokemonResponseList = mainState.pokemons!!

            }
        }
    }

        viewModel.choice.observe(viewLifecycleOwner, Observer {

            when(it) {

                "number" -> {
                    binding.searchBar.inputType = InputType.TYPE_CLASS_NUMBER
                    binding.btnSort.setImageResource(R.drawable.text_format)
                }
                "name" -> {
                    binding.searchBar.inputType = InputType.TYPE_CLASS_TEXT
                    binding.btnSort.setImageResource(R.drawable.tag)
                }
            }
        })
    }

    private fun searchPokemon(){

        if (binding.searchBar.text.toString().isEmpty() || binding.searchBar.text.toString().equals("0")) {
            viewModel.getPokemons()
        }
        else {
        when(viewModel.choice.value) {
            "number" -> viewModel.getSinglePokemon(binding.searchBar.text.toString().removeLeadingZeros())

            "name" -> viewModel.getSinglePokemon(binding.searchBar.text.toString())
        }
        }
    }

    private fun hideKeyboard() {

        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0)
    }

    private fun onScroll() {

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                if (dy > 0) {
                    val canScrollDown = recyclerView.canScrollVertically(1)

                    if (!canScrollDown) {
                        viewModel.getPokemons()
                    }
                }
            }
        })
    }

    private fun onClick() {

        binding.searchBar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                searchPokemon()
                return@setOnEditorActionListener true
            }
            false
        }

        binding.btnSort.setOnClickListener() {

            val layoutResource = R.layout.sort_popup

            val popupView = LayoutInflater.from(requireContext()).inflate(layoutResource, null)
            val popup = PopupWindow(requireContext())
            popup.contentView = popupView
            popup.showAtLocation(
                this.requireView(),
                Gravity.NO_GRAVITY,
                binding.btnSort.left,
                binding.btnSort.top
            )

            val radioButtonNumberSortPopup = popupView.findViewById<RadioButton>(R.id.radioButtonNumberSortPopup)
            val radioButtonNameSortPopup = popupView.findViewById<RadioButton>(R.id.radioButtonNameSortPopup)

            when(viewModel.choice.value) {

                "number" -> radioButtonNumberSortPopup.isChecked = true

                "name" -> radioButtonNameSortPopup.isChecked = true

            }

            radioButtonNumberSortPopup.setOnClickListener() {

                viewModel.setChoice("number")
                popup.dismiss()
            }

            radioButtonNameSortPopup.setOnClickListener() {

                viewModel.setChoice("name")
                popup.dismiss()

            }
        }

        pokemonRecyclerAdapter.setOnItemClickListener {
            val args = Bundle()
            args.putInt("id", it)
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment,args)
        }
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