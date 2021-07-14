package com.moon.android.mondaysally.data.repository

import android.content.Context

class SharedPrefRepository(private val context: Context) {
 
    private val userDao = mDatabase.userDao()
    val allUsers: LiveData<List<UserEntity>> = userDao.getAlphabetizedUsers()
 
    companion object{
        private var sInstance: SharedPrefRepository? = null
        fun getInstance(database: AppDatabase): SharedPrefRepository {
            return sInstance
                    ?: synchronized(this){
                        val instance = SharedPrefRepository(database)
                        sInstance = instance
                        instance
                    }
        }
    }
    
    suspend fun insert(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }
}