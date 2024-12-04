package com.example.mateuszskowronlaboratory2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mateuszskowronlaboratory2.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = ResultFragmentArgs.fromBundle(requireArguments())

        binding.bmi = args.bmi
        binding.diagnosis = args.diagnosis

        // Set status image based on diagnosis
        val imageResource = when (args.diagnosis) {
            "Underweight" -> R.drawable.underweight
            "Healthy" -> R.drawable.normal
            "Overweight" -> R.drawable.overweight
            "Obesity" -> R.drawable.obesity
            else -> R.drawable.normal
        }
        binding.statusImage.setImageResource(imageResource)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}