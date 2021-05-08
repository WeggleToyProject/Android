package com.jinhyun.weggletoyproject.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jinhyun.weggletoyproject.model.UserModel

@Dao
interface UserDAO {

    @Query("SELECT * FROM User")
    fun getUser(): LiveData<UserModel>

    @Insert
    fun insertUser(userModel: UserModel)

    @Update
    fun updateUser(userModel: UserModel)

    @Delete
    fun deleteUser(userModel: UserModel)
}