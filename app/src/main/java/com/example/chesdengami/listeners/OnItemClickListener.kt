package com.example.chesdengami.listeners

import android.view.ActionMode

interface OnItemClickListener { //todo потом подумать как убрать эту фигню - могу ли я просто передать объект
    fun onItemClick(itemId: Long)
    fun onEditItemClick(itemId: Long)
    fun onDeleteItemClick(itemIds: List<Long>);
}