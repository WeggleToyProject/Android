package com.jinhyun.weggletoyproject.database

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import com.jinhyun.weggletoyproject.model.UserModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class UserRepository(application: Application) {
    private var mDatabase: WeggleToyDatabase
    private var mUserDAO: UserDAO
    private var mUserItem: LiveData<UserModel>

    init {
        mDatabase = WeggleToyDatabase.getInstance(application)
        mUserDAO = mDatabase.userDao()
        mUserItem = mUserDAO.getUser()
    }

    fun getUser(): LiveData<UserModel> {
        return mUserItem
    }

    @SuppressLint("CheckResult")
    fun insertUser(userModel: UserModel) {
        Observable.just(userModel)
            .subscribeOn(Schedulers.io())
            .subscribe({
                mUserDAO.insertUser(userModel)
            }, {

            })
    }

    @SuppressLint("CheckResult")
    fun updateUser(userModel: UserModel) {
        Observable.just(userModel)
            .subscribeOn(Schedulers.io())
            .subscribe({
                mUserDAO.updateUser(userModel)
            }, {

            })
    }

    @SuppressLint("CheckResult")
    fun deleteUser(userModel: UserModel) {
        Observable.just(userModel)
            .subscribeOn(Schedulers.io())
            .subscribe({
                mUserDAO.deleteUser(userModel)
            }, {

            })
    }
}