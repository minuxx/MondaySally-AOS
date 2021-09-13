package com.moon.android.mondaysally.ui.main.auth

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.moon.android.mondaysally.data.entities.AuthResult
import com.moon.android.mondaysally.data.entities.ProfileBody
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.SharedPrefRepository
import com.moon.android.mondaysally.data.repository.network.AuthNetworkRepository
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class AuthViewModel(
    private val authNetworkRepository: AuthNetworkRepository,
    private val sharedPrefRepository: SharedPrefRepository,
) : ViewModel() {

    var authResult: MutableLiveData<AuthResult> = MutableLiveData()
    var finish: MutableLiveData<Boolean> = MutableLiveData()
    var fail: MutableLiveData<Fail> = MutableLiveData()
    var goProfileEdit: MutableLiveData<Boolean> = MutableLiveData()
    var profileEditSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var validateMessage: MutableLiveData<String> = MutableLiveData()

    var profileUrl: String? = null
    var editTextNicknameString = ObservableField("")
    var editTextPhoneString = ObservableField("")
    var editTextBankString = ObservableField("")
    var editTextEmailString = ObservableField("")

    fun getMyPageData() = viewModelScope.launch {
        try {
            val authResponse = authNetworkRepository.getMyPage()
            if (authResponse.code == 200) {
                authResult.value = authResponse.result
            } else {
                fail.value = Fail(authResponse.message, authResponse.code)
            }
        } catch (e: ApiException) {
            fail.value = Fail(e.message!!, 404)
        } catch (e: Exception) {
            fail.value = Fail(e.message!!, 404)
        }
    }

    private fun postProfile() = viewModelScope.launch {
        try {
            val authResponse =
                authNetworkRepository.postProfile(
                    ProfileBody(
                        editTextNicknameString.get()!!,
                        profileUrl!!,
                        editTextPhoneString.get()!!,
                        editTextBankString.get()!!,
                        editTextEmailString.get()!!
                    )
                )
            if (authResponse.code == 200) {
                profileEditSuccess.value = true
            } else {
                fail.value = Fail(authResponse.message, authResponse.code)
            }
        } catch (e: Exception) {
            Log.d("네트워크", e.toString())
            fail.value = Fail(e.message!!, 404)
        } catch (e: Exception) {
            Log.d("네트워크", e.toString())
            fail.value = Fail(e.message!!, 404)
        }
    }


    fun whenBackClicked() {
        finish.value = true
    }

    fun whenProfileEditClicked() {
        goProfileEdit.value = true
    }

    fun whenEditDoneClicked() {
        //validation check
        if (validateCheck()) {
            //dialog check
            postProfile()
        }
    }

    private fun validateCheck(): Boolean {
        return if (editTextNicknameString.get()?.isEmpty() == true) {
            validateMessage.value = "닉네임을 입력해주세요"
            false
        } else if (editTextPhoneString.get()?.length != 11) {
            validateMessage.value = "올바른 연락처를 입력해주세요."
            false
        } else if (editTextBankString.get()?.isEmpty() == true) {
            validateMessage.value = "(은행) 계좌번호를 입력해주세요."
            false
        } else if (editTextEmailString.get()
                ?.isEmpty() == true || !emailValidate(editTextEmailString.get()!!)
        ) {
            validateMessage.value = "올바른 이메일을 입력해주세요."
            false
        } else {
            true
        }
    }

    private fun emailValidate(email: String): Boolean {
        val pattern = Pattern.compile(
            "\\w+([-+.]\\w+)*" + "\\@" + "\\w+([-.]\\w+)*" + "\\." + "\\w+([-.]\\w+)*"
        )
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun phoneValidate(phone: String): Boolean {
        Log.d("네트워크", phone)
        val pattern = Pattern.compile(
            "\\d{3}-\\d{4}-\\d{4}"
        )
        val matcher = pattern.matcher(phone)
        return matcher.matches()
    }

    fun dialogCheck(): Boolean {
        return true
    }
}