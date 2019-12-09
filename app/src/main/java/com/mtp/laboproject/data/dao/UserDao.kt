package com.mtp.laboproject.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mtp.laboproject.data.model.CURRENT_USERID
import com.mtp.laboproject.data.model.UserResponse

@Dao
 interface UserDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserResponse):Long

    @Query("SELECT * FROM userResponse WHERE id_db_user= $CURRENT_USERID")
    suspend fun getUser():LiveData<UserResponse>
}