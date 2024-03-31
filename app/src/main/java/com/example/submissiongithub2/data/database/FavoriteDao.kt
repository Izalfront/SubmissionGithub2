package com.example.submissiongithub2.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFav(favorite: FavoriteUser)

    @Delete
    fun deleteFav(favorite: FavoriteUser)

    @Query("SELECT * from favoriteuser ORDER BY name_user ASC")
    fun getFav(): LiveData<List<FavoriteUser>>
}