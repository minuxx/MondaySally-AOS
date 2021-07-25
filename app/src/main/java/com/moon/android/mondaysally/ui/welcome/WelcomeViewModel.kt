package com.moon.android.mondaysally.ui.welcome

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.android.mondaysally.data.entities.Code
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.SharedPrefRepository
import com.moon.android.mondaysally.data.repository.auth.AuthNetworkRepository
import com.moon.android.mondaysally.utils.ApiException
import kotlinx.coroutines.launch


class WelcomeViewModel : ViewModel() {

    var goNextActivity: MutableLiveData<Boolean> = MutableLiveData()
    var allAgree: MutableLiveData<Boolean> = MutableLiveData()
    var serviceAgree: MutableLiveData<Boolean> = MutableLiveData()
    var privacyAgree: MutableLiveData<Boolean> = MutableLiveData()


    fun whenGoHomeBtnClicked() {
        serviceAgree.value = serviceAgree.value != true
//        allCheck()
    }
}