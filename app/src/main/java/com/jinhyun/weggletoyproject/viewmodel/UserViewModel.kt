package com.jinhyun.weggletoyproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jinhyun.weggletoyproject.database.UserRepository
import com.jinhyun.weggletoyproject.model.UserModel

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val mUserRepository: UserRepository
    private var mUserItem: LiveData<UserModel>

    init {
        mUserRepository = UserRepository(application)
        mUserItem = mUserRepository.getUser()
    }

    fun getUser(): LiveData<UserModel> {
        return mUserItem
    }

    fun insertUser(userModel: UserModel) {
        mUserRepository.insertUser(userModel)
    }

    fun updateUser(userModel: UserModel) {
        mUserRepository.updateUser(userModel)
    }

    fun deleteUser(userModel: UserModel) {
        mUserRepository.deleteUser(userModel)
    }
}