package com.moon.android.mondaysally.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainViewModel() : ViewModel() {
    var navigationFlag: MutableLiveData<Int> = MutableLiveData()

    fun whenTvGoHomeClicked() {
        navigationFlag.value = 1
    }
}