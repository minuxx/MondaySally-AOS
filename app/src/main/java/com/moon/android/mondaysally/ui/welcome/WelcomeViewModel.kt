package com.moon.android.mondaysally.ui.welcome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class WelcomeViewModel : ViewModel() {

    var goNextActivity: MutableLiveData<Boolean> = MutableLiveData()

    fun whenGoHomeBtnClicked() {
        goNextActivity.value = true
    }
}