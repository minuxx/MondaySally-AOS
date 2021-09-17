package com.moon.android.mondaysally.ui.main.auth

import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.moon.android.mondaysally.data.entities.AuthResult
import com.moon.android.mondaysally.data.entities.ProfileBody
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.FirebaseImageUploadRepository
import com.moon.android.mondaysally.data.repository.SharedPrefRepository
import com.moon.android.mondaysally.data.repository.network.AuthNetworkRepository
import com.moon.android.mondaysally.utils.GlobalConstant
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class AuthViewModel(
    private val authNetworkRepository: AuthNetworkRepository,
    private val sharedPrefRepository: SharedPrefRepository,
) : ViewModel() {
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    var finish: MutableLiveData<Boolean> = MutableLiveData()
    var fail: MutableLiveData<Fail> = MutableLiveData()

    var goProfileEdit: MutableLiveData<Boolean> = MutableLiveData()
    var authResult: MutableLiveData<AuthResult> = MutableLiveData()
    var menuClick: MutableLiveData<Int> = MutableLiveData()

    //ProfileEdit
    var editDoneClick: MutableLiveData<Boolean> = MutableLiveData()
    var profileEditSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var bottomSheetOpen: MutableLiveData<Boolean> = MutableLiveData()
    var validateMessage: MutableLiveData<String> = MutableLiveData()
    var profileUrl: String? = null
    var profileUri: Uri? = null
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
                authNetworkRepository.patchProfile(
                    ProfileBody(
                        editTextNicknameString.get()!!,
                        profileUrl!!,
                        editTextPhoneString.get()!!,
                        editTextBankString.get()!!,
                        editTextEmailString.get()!!
                    )
                )
            isLoading.value = false
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

    fun whenIvProfileClicked() {
        bottomSheetOpen.value = true
    }

    fun whenMenuClicked(flag: Int) {
        menuClick.value = flag
    }

    fun whenEditDoneClicked() {
        editDoneClick.value = true
    }

    fun validateCheck(): Boolean {
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

    fun uploadToFirebase() {
        imageUploadToFirebaseStorage(profileUri)
    }

    private fun imageUploadToFirebaseStorage(uri: Uri?) {
        if (uri == null) {
            postProfile()
        } else {
            isLoading.value = true
            val firebaseImageUploadRepository = FirebaseImageUploadRepository()
            firebaseImageUploadRepository.setonUploadDoneListener { downloadUrl ->
                if (downloadUrl != "Fail") {
                    profileUrl = downloadUrl
                    postProfile()
                } else {
                    fail.value = Fail("이미지 업로드에 실패하였습니다.", 404)
                }
            }
            //업로드
            firebaseImageUploadRepository.uploadImage(uri, GlobalConstant.TWINKLE_FOLDER)
                .addOnProgressListener { (bytesTransferred, totalByteCount) ->
                    val progress = (100.0 * bytesTransferred) / totalByteCount
                    Log.d("네트워크", "Upload is $progress% done")
                }.addOnPausedListener {
                }
        }
    }
}