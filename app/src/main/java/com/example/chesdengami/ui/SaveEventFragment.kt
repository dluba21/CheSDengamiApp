package com.example.chesdengami.ui

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.chesdengami.MainViewModel
import com.example.chesdengami.application.MyApplication
import com.example.chesdengami.databinding.FragmentSaveEventBinding
import com.example.chesdengami.model.Event

class SaveEventFragment : Fragment() {
    private val mainViewModel by activityViewModels<MainViewModel> {
        MainViewModel.MainViewModelFactory((requireActivity().application as MyApplication).appRepository)
    }
    private val args: SaveEventFragmentArgs by navArgs()
    private lateinit var binding: FragmentSaveEventBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaveEventBinding.inflate(inflater, container, false)
        mainViewModel.getEventById(args.eventId)
        if (!args.isNewItem) {
            mainViewModel.currentEvent.observe(viewLifecycleOwner) { event ->
                    binding.eventNameTextEdit.text = Editable.Factory.getInstance().newEditable(event.eventName)
                }
        }

        binding.saveEventButton.setOnClickListener {
            binding.eventNameTextLayout.isErrorEnabled = false
            if (validateInput(binding.eventNameTextEdit.text.toString())) {
                if (args.isNewItem) {
                    mainViewModel.addEvent(Event(eventName = binding.eventNameTextEdit.text.toString()))
                } else {
                    mainViewModel.updateEvent(eventName = binding.eventNameTextEdit.text.toString())
                }
                findNavController().popBackStack()
            } else {
                binding.eventNameTextLayout.isErrorEnabled = true
                binding.eventNameTextLayout.error = "Input can't be empty"
                binding.eventNameTextLayout.requestFocus()
            }
        }
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    private fun validateInput(input: String): Boolean {
        return (input.isNotBlank())
    }
}