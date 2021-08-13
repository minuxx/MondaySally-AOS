package com.moon.android.mondaysally.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moon.android.mondaysally.data.entities.User
import com.moon.android.mondaysally.data.repository.network.HomeNetworkRepository
import com.moon.android.mondaysally.utils.SharedPreferencesManager

class LoginViewModel(
    private val repository: HomeNetworkRepository,
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {
    val id: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            postValue("")
        }
    }

    val pw: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            postValue("")
        }
    }

    private fun getUser(): User = User(id = id.value.toString(), pw = pw.value.toString())


}
