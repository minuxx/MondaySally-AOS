package com.moon.android.mondaysally.ui.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moon.android.mondaysally.data.remote.Fail


class OnBoardingViewModel() : ViewModel() {

    var serverAccessible: MutableLiveData<Boolean> = MutableLiveData()
    var autoLogin: MutableLiveData<Boolean> = MutableLiveData()
    var fail: MutableLiveData<Fail> = MutableLiveData()

    fun signInBtnClicked() {
//        val mainService = MainService(this)
        //        mainService.trySignIn(id.getValue(), password.getValue());
//        mainService.tryTest()
    }
}