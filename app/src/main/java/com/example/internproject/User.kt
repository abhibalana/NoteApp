package com.example.internproject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
 class User(@ColumnInfo(name = "user_name")val name:String, @ColumnInfo(name="phn_number")val number:String, @ColumnInfo(name="user_mail")val email:String, @ColumnInfo(name="password")val password:String) {
@PrimaryKey(autoGenerate = true) var user_id=0
}