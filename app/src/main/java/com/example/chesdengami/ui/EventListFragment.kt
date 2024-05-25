package com.example.chesdengami.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chesdengami.MainViewModel
import com.example.chesdengami.NEW_ITEM
import com.example.chesdengami.adapters.EventAdapter
import com.example.chesdengami.application.MyApplication
import com.example.chesdengami.databinding.FragmentEventListBinding
import com.example.chesdengami.listeners.OnItemClickListener


class EventListFragment: Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((requireActivity().application as MyApplication).appRepository)
    }
    private lateinit var eventAdapter: EventAdapter
    private lateinit var binding: FragmentEventListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventListBinding.inflate(inflater, container, false)

        eventAdapter = EventAdapter(
            context = requireActivity(),
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(itemId: Long) {
                    findNavController().navigate(EventListFragmentDirections.actionEventListFragmentToMemberListFragment(itemId))
                }

                override fun onEditItemClick(itemId: Long)  {
                    findNavController().navigate(EventListFragmentDirections.actionEventListFragmentToSaveEventFragment(itemId, !NEW_ITEM))
                }

                override fun onDeleteItemClick(itemIds: List<Long>) {
                    mainViewModel.deleteEventsByIds(itemIds)
                }
            }
        )
        mainViewModel.eventList.observe(viewLifecycleOwner) { eventList -> eventAdapter.submitList(eventList)}
        binding.eventListRecyclerView.adapter = eventAdapter
        binding.eventListRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.addEventFab.setOnClickListener {
            findNavController().navigate(EventListFragmentDirections.actionEventListFragmentToSaveEventFragment(-1L, NEW_ITEM))
        }
        return binding.root
    }
}