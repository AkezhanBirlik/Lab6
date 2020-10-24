package com.example.lab6

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class ListItem (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var image_id: Int,
    var titleText: String,
    var contentText: String,
    var checked: Boolean = false
)