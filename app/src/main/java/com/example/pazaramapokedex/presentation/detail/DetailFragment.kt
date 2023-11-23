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
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pazaramapokedex.R
import com.example.pazaramapokedex.databinding.FragmentDetailBinding
import com.example.pazaramapokedex.domain.model.Type
import com.example.pazaramapokedex.presentation.adapters.TypesAdapter
import com.example.pazaramapokedex.utils.Status
import com.example.pazaramapokedex.utils.capitalize
import com.example.pazaramapokedex.utils.extractId
import com.example.pazaramapokedex.utils.loadImage
import com.example.pokedex.domain.model.SinglePokemonResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class DetailFragment @Inject constructor(
    val typesRecyclerAdapter: TypesAdapter
): Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: DetailViewModel

    var url : Int  = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            url = it?.getInt("id")!!

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

        viewModel.getSinglePokemon(id)

        onClick()
        subscribeToObservers()

    }

    private fun subscribeToObservers() {

        viewModel.pokemonList.observe(viewLifecycleOwner, Observer { singlePokemonResponse ->

            when(singlePokemonResponse.status){
                Status.SUCCESS -> {


                    val data = singlePokemonResponse.data

                    typesRecyclerAdapter.typeList = data?.types as MutableList<Type>

                        setColors(data)
                        setText(data)

                    binding.progressbar.visibility = View.GONE
                    binding.header.visibility = View.VISIBLE

                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),singlePokemonResponse.message ?: "Error", Toast.LENGTH_LONG).show()
                    binding.progressbar.visibility = View.GONE

                }

                Status.LOADING -> {

                    binding.progressbar.visibility = View.VISIBLE

                }
            }
        })

    }

    private fun setText(data: SinglePokemonResponse) {

        var hp = 0
        var attack = 0
        var defense = 0
        var specialAttack = 0
        var specialDefense = 0
        var speed = 0

        for (i in data?.stats!!)
            when (i.stat.name) {
                "hp" -> hp = i.base_stat
                "attack" -> attack = i.base_stat
                "defense" -> defense = i.base_stat
                "special-attack" -> specialAttack= i.base_stat
                "special-defense" -> specialDefense= i.base_stat
                "speed" -> speed= i.base_stat

            }

        binding.imgPokemon.loadImage(data.sprites.other.officialArtwork.frontDefault)

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

    private fun setColors(data: SinglePokemonResponse) {

        var color : Int

        color = ContextCompat.getColor(requireContext(),R.color.colorPrimary)

        when(data?.types?.get(0)?.type?.name!!) {


            //TODO daha iyi bir yöntem var mı bakılacak
            "normal" -> color = ContextCompat.getColor(requireContext(),R.color.type_normal)
            "fire" -> color = ContextCompat.getColor(requireContext(),R.color.type_fire)
            "water" -> color = ContextCompat.getColor(requireContext(),R.color.type_water)
            "electric" -> color = ContextCompat.getColor(requireContext(),R.color.type_electric)
            "grass" -> color = ContextCompat.getColor(requireContext(),R.color.type_grass)
            "ice" -> color = ContextCompat.getColor(requireContext(),R.color.type_ice)
            "fighting" -> color = ContextCompat.getColor(requireContext(),R.color.type_fighting)
            "poison" -> color = ContextCompat.getColor(requireContext(),R.color.type_poison)
            "ground" -> color = ContextCompat.getColor(requireContext(),R.color.type_ground)
            "flying" -> color = ContextCompat.getColor(requireContext(),R.color.type_flying)
            "psychic" -> color = ContextCompat.getColor(requireContext(),R.color.type_psychic)
            "bug" -> color = ContextCompat.getColor(requireContext(),R.color.type_bug)
            "rock" -> color = ContextCompat.getColor(requireContext(),R.color.type_rock)
            "ghost" -> color = ContextCompat.getColor(requireContext(),R.color.type_ghost)
            "dragon" -> color = ContextCompat.getColor(requireContext(),R.color.type_dragon)
            "dark" -> color = ContextCompat.getColor(requireContext(),R.color.type_dark)
            "steel" -> color = ContextCompat.getColor(requireContext(),R.color.type_steel)
            "fairy" -> color = ContextCompat.getColor(requireContext(),R.color.type_fairy)

        }

        binding.header.setBackgroundColor(color)

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


