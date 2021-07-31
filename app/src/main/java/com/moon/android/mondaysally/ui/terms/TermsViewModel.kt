package com.moon.android.mondaysally.ui.terms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class TermsViewModel : ViewModel() {

    var goNextActivity: MutableLiveData<Boolean> = MutableLiveData()
    var allAgree: MutableLiveData<Boolean> = MutableLiveData()
    var serviceAgree: MutableLiveData<Boolean> = MutableLiveData()
    var privacyAgree: MutableLiveData<Boolean> = MutableLiveData()


    fun whenAllAgreeBtnClicked() {
        if (allAgree.value != true) {
            allAgree.value = true
            serviceAgree.value = true
            privacyAgree.value = true
        } else {
            allAgree.value = false
            serviceAgree.value = false
            privacyAgree.value = false
        }
    }

    fun whenServiceBtnClicked() {
        serviceAgree.value = serviceAgree.value != true
        allCheck()
    }

    fun whenPrivacyBtnClicked() {
        privacyAgree.value = privacyAgree.value != true
        allCheck()
    }

    fun whenOkBtnClicked() {
        goNextActivity.value = true
    }

    fun allCheck() {
        allAgree.value = serviceAgree.value == true && privacyAgree.value == true
    }

}