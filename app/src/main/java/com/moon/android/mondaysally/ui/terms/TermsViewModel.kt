package com.moon.android.mondaysally.ui.terms

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