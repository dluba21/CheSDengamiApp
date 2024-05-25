package com.example.chesdengami.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build.VERSION_CODES.M
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chesdengami.MainActivity
import com.example.chesdengami.R
import com.example.chesdengami.databinding.EventItemBinding
import com.example.chesdengami.listeners.OnItemClickListener
import com.example.chesdengami.model.Event
import java.nio.file.Files.delete

class EventAdapter(val context: Context, private val onItemClickListener: OnItemClickListener) : ListAdapter<Event, EventAdapter.EventViewHolder>(EVENT_COMPARATOR) {

    var isMultiSelectionModeEnabled = false
    val selectedIds = mutableListOf<Long>()
    var actionMode: ActionMode? = null //todo проверить на выход закрывается ли меню

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(EventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClickListener)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class EventViewHolder(private val binding: EventItemBinding, private val listener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            with(binding) {
                eventItemTextView.text = event.eventName

                if (selectedIds.contains(event.id)) eventItemTextView.setBackgroundColor(Color.LTGRAY)
                else eventItemTextView.setBackgroundColor(Color.WHITE)

                eventItemTextView.setOnClickListener {
                    if (!isMultiSelectionModeEnabled) {
                        listener.onItemClick(event.id)
                    }
                    else if (selectedIds.contains(event.id)) {
                        selectedIds.remove(event.id)
                        notifyItemChanged(adapterPosition)
                    }
                    else if (!selectedIds.contains(event.id)) {
                        selectedIds.add(event.id)
                        notifyItemChanged(adapterPosition)
                    }
                    if (selectedIds.size == 0) actionMode?.finish()
                }

                eventItemTextView.setOnLongClickListener {
                    if (!isMultiSelectionModeEnabled) {
                        isMultiSelectionModeEnabled = true
                        actionMode = (context as MainActivity).startSupportActionMode(ItemsActionsModeCallback())

                        selectedIds.add(event.id)
                        notifyItemChanged(adapterPosition)
                    }
                    true
                }
                //todo конфигурация actionMode - title установил - счетчик, а потом и динамиески элементы меню
                actionMode?.apply {
                    title = selectedIds.size.toString()
                }
            }
        }
    }

    companion object {
        private var EVENT_COMPARATOR = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem.eventName == newItem.eventName
            }
        }
    }

    inner class ItemsActionsModeCallback : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.action_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return true
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.delete_item -> {
                    if (selectedIds.size > 0) {
                        AlertDialog.Builder(context).apply {
                            setTitle("Delete record")
                            setMessage("Do you want to delete records?")
                            setPositiveButton("Delete") { _, _, ->
                                onItemClickListener.onDeleteItemClick(selectedIds.toList())
                                actionMode?.finish()
                            }
                            setNegativeButton("Cancel") { _, _ -> }
                        }.create().show()
                    }
                    return false
                }
                R.id.edit_item -> {
                    if (selectedIds.size == 1) {
                        onItemClickListener.onEditItemClick(selectedIds.first())
                        actionMode?.finish() // return не требуется
                        return true
                    }
                    Toast.makeText(context, "Please, select only one record", Toast.LENGTH_SHORT).show()
                    return false
                }
                else -> false
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onDestroyActionMode(mode: ActionMode?) {
            selectedIds.clear()
            notifyDataSetChanged() //todo мб попробовать потом по item сделать
            isMultiSelectionModeEnabled = false
            actionMode = null
        }
    }
}