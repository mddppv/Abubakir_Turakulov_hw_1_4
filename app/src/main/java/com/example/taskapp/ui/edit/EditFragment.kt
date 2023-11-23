package com.example.taskapp.ui.edit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.taskapp.data.local.Pref
import com.example.taskapp.databinding.FragmentEditBinding
import com.example.taskapp.model.Profile

class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textCheck()

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val description = binding.etDescription.text.toString()

            val data = Profile(
                name = name, description = description
            )

            val pref = Pref(requireContext())
            pref.saveProfile(name, description)

            setFragmentResult(PROFILE_RESULT_KEY, bundleOf(PROFILE_KEY to data))
            findNavController().navigateUp()
        }
    }

    private fun textCheck() {
        val textCheck = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateSaveButtonVisibility()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        with(binding) {
            etName.addTextChangedListener(textCheck)
            etDescription.addTextChangedListener(textCheck)
        }
    }

    private fun updateSaveButtonVisibility() {
        val nameLength = binding.etName.text.length
        val descriptionLength = binding.etDescription.text.length

        binding.btnSave.visibility = when {
            nameLength > 0 -> View.VISIBLE
            nameLength == 0 && descriptionLength > 0 -> View.GONE
            else -> View.GONE
        }
    }

    companion object {
        const val PROFILE_RESULT_KEY = "profile_result_key"
        const val PROFILE_KEY = "profile_key"
    }
}