package com.example.lab6

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonDao {

    @Query("SELECT * FROM PERSON")
    fun getAllPersons(): MutableList<ListItem>

    @Insert
    fun insertIntoPerson(person: ListItem)

    @Query("DELETE FROM PERSON WHERE ID IN (:idList)")
    fun deleteByIdList(idList: ArrayList<Int>)

}