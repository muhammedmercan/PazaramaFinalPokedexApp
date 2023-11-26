package com.example.pazaramapokedex.presentation.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pazaramapokedex.databinding.FragmentDetailBinding
import com.example.pazaramapokedex.presentation.adapters.TypesAdapter
import com.example.pazaramapokedex.utils.PokemonTypeUtils
import com.example.pazaramapokedex.utils.capitalize
import com.example.pazaramapokedex.utils.formatId
import com.example.pazaramapokedex.utils.loadImage
import com.example.pokedex.domain.model.PokemonDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import javax.inject.Inject


@AndroidEntryPoint
class DetailFragment @Inject constructor(
    val typesRecyclerAdapter: TypesAdapter
): Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: DetailViewModel

    var pokemonId : String = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            pokemonId = it?.getInt("id").toString()!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

        ViewCompat.setTranslationZ(view, 1F)

        binding.ribbonRecyclerView.adapter = typesRecyclerAdapter
        binding.ribbonRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)

        viewModel.getSinglePokemon(pokemonId)

        onClick()
        subscribeToObservers()

    }

    private fun subscribeToObservers() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.pokemonDetail.collect() { detailState ->

                    binding.progressbar.isVisible = detailState.isLoading

                    detailState.error?.let {
                        binding.progressbar.isVisible = detailState.isLoading

                    }

                    detailState.details?.let {
                        println(it)
                        setText(it!!)
                        setColors(it!!)
                        typesRecyclerAdapter.typeList = it.types



                    }
                }
            }
        }
    }

    private fun setText(data: PokemonDetails) {

        var hp = 0
        var attack = 0
        var defense = 0
        var specialAttack = 0
        var specialDefense = 0
        var speed = 0


        for (i in data?.stats!!)
            when (i.stat?.name) {
                "hp" -> hp = i.baseStat!!
                "attack" -> attack = i.baseStat!!
                "defense" -> defense = i.baseStat!!
                "special-attack" -> specialAttack= i.baseStat!!
                "special-defense" -> specialDefense= i.baseStat!!
                "speed" -> speed= i.baseStat!!

            }

        binding.pokemonNumber.text = pokemonId.toInt().formatId()

        binding.imgPokemon.loadImage(data.img!!)

        binding.txtPokemonName.text = data?.name?.capitalize()

        binding.weight.text = (data?.weight?.toFloat()?.div(10)).toString()
        binding.height.text = (data?.height?.toFloat()?.div(10)).toString()

        binding.progressHp.progress = hp
        binding.txtHp.text = hp.toString()
        binding.progressAttack.progress = attack
        binding.txtAttack.text = attack.toString()
        binding.progressDefense.progress = defense
        binding.txtDefense.text = defense.toString()
        binding.progressSpecialAttack.progress = specialAttack
        binding.txtSpecialAttack.text = specialAttack.toString()
        binding.progressSpecialDefense.progress = specialDefense
        binding.txtSpecialDefense.text = specialDefense.toString()
        binding.progressSpeed.progress = speed
        binding.txtSpeed.text = speed.toString()

    }

    private fun setColors(data: PokemonDetails) {

        val color = ContextCompat.getColor(requireContext(),PokemonTypeUtils.getTypeColor(data.types.get(0)))


        binding.layoutFragmentDetail.setBackgroundColor(color)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = color

        binding.txtAboutTittle.setTextColor(color)
        binding.progressHp.progressTintList = ColorStateList.valueOf(color)
        binding.progressAttack.progressTintList = ColorStateList.valueOf(color)
        binding.progressDefense.progressTintList = ColorStateList.valueOf(color)
        binding.progressSpecialAttack.progressTintList = ColorStateList.valueOf(color)
        binding.progressSpecialDefense.progressTintList = ColorStateList.valueOf(color)
        binding.progressSpeed.progressTintList = ColorStateList.valueOf(color)

        binding.baseStatsTitle.setTextColor(color)
        binding.txtHpTittle.setTextColor(color)
        binding.txtAttackTittle.setTextColor(color)
        binding.txtDefenseTittle.setTextColor(color)
        binding.txtSpecialAttackTittle.setTextColor(color)
        binding.txtSpecialDefenceTittle.setTextColor(color)
        binding.txtSpeedTittle.setTextColor(color)

    }

    private fun onClick() {

        val callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                viewModel.resetpokemonDetails()
                typesRecyclerAdapter.resetTypeList()
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        binding.btnBack.setOnClickListener() {
            callBack.handleOnBackPressed()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}


