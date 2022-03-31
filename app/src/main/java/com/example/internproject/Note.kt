package com.example.internproject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")

class Note(@ColumnInfo(name = "notes")val Note:String,@ColumnInfo(name = "userid")val userid:Int) {
    @PrimaryKey(autoGenerate = true) var id :Int =0

}