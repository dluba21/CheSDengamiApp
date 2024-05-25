package com.example.chesdengami.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chesdengami.MainViewModel
import com.example.chesdengami.R
import com.example.chesdengami.adapters.MemberAdapter
import com.example.chesdengami.application.MyApplication
import com.example.chesdengami.databinding.FragmentMemberListBinding
import com.example.chesdengami.listeners.OnItemClickListener


class MemberListFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((requireActivity().application as MyApplication).appRepository)
    }
    private val args by navArgs<MemberListFragmentArgs>()
    private lateinit var memberAdapter: MemberAdapter
    private lateinit var binding: FragmentMemberListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMemberListBinding.inflate(inflater, container, false)

        mainViewModel.getMemberListByCurrentEventId(args.eventId)
        memberAdapter = MemberAdapter(object : OnItemClickListener {
            override fun onItemClick(itemId: Long) {
//                findNavController().navigate(R.id.action_memberListFragment_to_addMemberFragment)
            }

            override fun onDeleteItemClick(itemIds: List<Long>) {

            }

            override fun onEditItemClick(itemId: Long) {
            }
        })
        mainViewModel.memberList.observe(viewLifecycleOwner) { memberList ->
            memberAdapter.submitList(
                memberList
            )
        }
        binding.memberListRecyclerView.adapter = memberAdapter
        binding.memberListRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.addMemberFab.setOnClickListener {
            findNavController().navigate(MemberListFragmentDirections.actionMemberListFragmentToAddMemberFragment())
        }

        return binding.root
    }
}