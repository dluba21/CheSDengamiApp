package com.example.chesdengami.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchase_table")
class Purchase(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "purchase_word") val purchaseName: String,
    @ColumnInfo(name = "purchase_sum") val purchaseSum: Double
)