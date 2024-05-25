package com.example.chesdengami.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chesdengami.MainViewModel
import com.example.chesdengami.application.MyApplication
import com.example.chesdengami.databinding.FragmentAddMemberBinding
import com.example.chesdengami.model.Member


class AddMemberFragment : Fragment() {
    private val mainViewModel by activityViewModels<MainViewModel> {
        MainViewModel.MainViewModelFactory((requireActivity().application as MyApplication).appRepository)
    }
    private lateinit var binding: FragmentAddMemberBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddMemberBinding.inflate(inflater, container, false)

        binding.addMemberButton.setOnClickListener {
            if (validateInput(binding.memberNameTextEdit.text.toString())) {
                mainViewModel.currentEvent.observe(viewLifecycleOwner) { event ->
                    mainViewModel.addMember(
                        Member(
                            memberName = binding.memberNameTextEdit.text.toString(),
                            eventId = event.id
                        )
                    )
                }
                findNavController().popBackStack()
            }
        }
        binding.cancelButton.setOnClickListener() {
            findNavController().popBackStack()
        }



        return binding.root
    }
    private fun validateInput(input: String): Boolean {
        return (input.isNotBlank())
    }
}
