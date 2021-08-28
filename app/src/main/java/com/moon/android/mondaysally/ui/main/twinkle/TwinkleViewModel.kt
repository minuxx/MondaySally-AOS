package com.moon.android.mondaysally.ui.main.twinkle

import android.net.Uri
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.moon.android.mondaysally.BR
import com.moon.android.mondaysally.data.entities.*
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.FirebaseImageUploadRepository
import com.moon.android.mondaysally.data.repository.network.TwinkleNetworkRepository
import com.moon.android.mondaysally.ui.main.twinkle.paging.MyTwinklePagingSource
import com.moon.android.mondaysally.ui.main.twinkle.paging.TwinklePagingSource
import com.moon.android.mondaysally.utils.ApiException
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.TWINKLE_FOLDER
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.VALIDATE_RECEIPT_PHOTO
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.VALIDATE_TWINKLE_CONTENT
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.VALIDATE_TWINKLE_PHOTO
import com.moon.android.mondaysally.utils.ListLiveData
import kotlinx.coroutines.launch

class TwinkleViewModel(
    private val twinkleNetworkRepository: TwinkleNetworkRepository
) :
    ViewModel() {

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var finishActivity: MutableLiveData<Boolean> = MutableLiveData()
    var twinkleIndex: MutableLiveData<Int> = MutableLiveData()
    var showDialogText: MutableLiveData<Int> = MutableLiveData()

    //TwinkleDetail
    var twinkleResult: MutableLiveData<TwinkleResult> = MutableLiveData()
    var commentPostSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var likePostSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var hideKeyboard: MutableLiveData<Boolean> = MutableLiveData()
    var commentRefresh: MutableLiveData<Boolean> = MutableLiveData()
    val commentList = ListLiveData<TwinkleComment>()
    var editTextCommentString = ObservableField("")

    //TwinklePost
    var getTwinklePhoto: MutableLiveData<Boolean> = MutableLiveData()
    var getReceiptPhoto: MutableLiveData<Boolean> = MutableLiveData()
    var twinklePostSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var selectedPhotoIndex: MutableLiveData<Int> = MutableLiveData()
    var uploadDoneReceipt: MutableLiveData<Uri> = MutableLiveData()
    var _twinkleImgList = ObservableField(arrayOfNulls<Uri>(3))
    var _receiptImgUrl = ObservableField<Uri?>()

    //TwinklePostBody
    var editTextContentString = ObservableField("")
    var twinkleImgList = arrayOfNulls<String>(3)
    var receiptImgUrl: String? = null

    var photoCount: Int = 0
    var uploadDoneCount: Int = 0

    var fail: MutableLiveData<Fail> = MutableLiveData()

    val myTwinkleFlow = Pager(PagingConfig(pageSize = 20)) {
        MyTwinklePagingSource(twinkleNetworkRepository.twinkleService)
    }.flow.cachedIn(viewModelScope)

    val twinkleFlow = Pager(PagingConfig(pageSize = 20)) {
        TwinklePagingSource(twinkleNetworkRepository.twinkleService)
    }.flow.cachedIn(viewModelScope)

    fun getTwinkleDetail(idx: Int) = viewModelScope.launch {
        try {
            val twinkleResponse = twinkleNetworkRepository.getTwinkleDetail(idx)
            Log.d("네트워크", twinkleResponse.result.toString())
            if (twinkleResponse.code == 200) {
                twinkleResponse.result?.let {
                    twinkleResult.value = twinkleResponse.result
                    commentList.clear()
                    commentList.addAll(twinkleResponse.result.commentLists)
                }
            } else {
                fail.value = Fail(twinkleResponse.message, twinkleResponse.code)
            }
        } catch (e: ApiException) {
            fail.value = Fail(e.message!!, 404)
        } catch (e: Exception) {
            fail.value = Fail(e.message!!, 404)
        }
    }

    private fun postComment() = viewModelScope.launch {
        try {
            val body: CommentPostBody
            if (twinkleIndex.value != null) {
                body = CommentPostBody(editTextCommentString.get()!!)
            } else {
                fail.value = Fail("", 404)
                return@launch
            }
            val twinkleResponse = twinkleNetworkRepository.postComment(twinkleIndex.value!!, body)
            Log.d("네트워크", body.toString())
            Log.d("네트워크", twinkleResponse.toString())
            if (twinkleResponse.code == 200) {
                commentRefresh.value = true
                commentPostSuccess.value = true
            } else {
                fail.value = Fail(twinkleResponse.message, twinkleResponse.code)
            }
        } catch (e: ApiException) {
            fail.value = Fail(e.message!!, 404)
        } catch (e: Exception) {
            fail.value = Fail(e.message!!, 404)
        }
    }

    private fun postTwinkle() = viewModelScope.launch {
        val twinkleImgArrayList = ArrayList<String>()
        for (i in 0 until 3) {
            twinkleImgList[i]?.let { twinkleImgArrayList.add(it) }
        }
        try {
            val body: TwinklePostBody
            if (twinkleIndex.value != null) {
                body = TwinklePostBody(
                    twinkleIndex.value!!,
                    editTextContentString.get()!!,
                    receiptImgUrl!!,
                    twinkleImgArrayList
                )
            } else {
                fail.value = Fail("", 404)
                return@launch
            }
            Log.d("네트워크", body.toString())
            val twinkleResponse = twinkleNetworkRepository.postTwinkle(body)
            isLoading.value = false
            Log.d("네트워크", twinkleResponse.toString())
            if (twinkleResponse.code == 200) {
                twinklePostSuccess.value = true
            } else {
                fail.value = Fail(twinkleResponse.message, twinkleResponse.code)
            }
        } catch (e: ApiException) {
            isLoading.value = false
            fail.value = Fail(e.message!!, 404)
        } catch (e: Exception) {
            isLoading.value = false
            fail.value = Fail(e.message!!, 404)
        }
    }

    fun postLike(idx: Int) = viewModelScope.launch {
        try {
            val twinkleResponse = twinkleNetworkRepository.postLike(idx)
            Log.d("네트워크", twinkleResponse.toString())
            if (twinkleResponse.code == 200) {
                //결과와 상관없이 로컬에서 변경처리
//                likePostSuccess.value = true
            } else {
                fail.value = Fail(twinkleResponse.message, twinkleResponse.code)
            }
        } catch (e: ApiException) {
            fail.value = Fail(e.message!!, 404)
        } catch (e: Exception) {
            fail.value = Fail(e.message!!, 404)
        }
    }

    private fun uploadToFirebase() {
        isLoading.value = true
        for (i in 0 until 3) {
            _twinkleImgList.get()?.get(i).let {
                it?.let { uri ->
                    twinkleImageUploadToFirebaseStorage(
                        uri, i
                    )
                }
            }
        }
    }

    private fun twinkleImageUploadToFirebaseStorage(uri: Uri, index: Int) {
        val firebaseImageUploadRepository = FirebaseImageUploadRepository()
        firebaseImageUploadRepository.setonUploadDoneListener { downloadUrl ->
            if (downloadUrl != "Fail") {
                twinkleImgList[index] = downloadUrl
                uploadDoneCount++
                if (photoCount == uploadDoneCount) {
                    uploadDoneCount = 0
                    _receiptImgUrl.get()?.let { receiptImageUploadToFirebaseStorage(it) }
                }
            } else {
                fail.value = Fail("이미지 업로드에 실패하였습니다.", 404)
            }
        }
        //업로드 진행률
        firebaseImageUploadRepository.uploadImage(uri, TWINKLE_FOLDER)
            .addOnProgressListener { (bytesTransferred, totalByteCount) ->
                val progress = (100.0 * bytesTransferred) / totalByteCount
                Log.d("네트워크", "Upload is $progress% done")
            }.addOnPausedListener {
            }
    }

    private fun receiptImageUploadToFirebaseStorage(uri: Uri) {
        val firebaseImageUploadRepository = FirebaseImageUploadRepository()
        firebaseImageUploadRepository.setonUploadDoneListener { downloadUrl ->
            if (downloadUrl != "Fail") {
                receiptImgUrl = downloadUrl
                postTwinkle()
            } else {
                fail.value = Fail("이미지 업로드에 실패하였습니다.", 404)
            }
        }
        //업로드 진행률
        firebaseImageUploadRepository.uploadImage(uri, TWINKLE_FOLDER)
            .addOnProgressListener { (bytesTransferred, totalByteCount) ->
                val progress = (100.0 * bytesTransferred) / totalByteCount
                Log.d("네트워크", "Upload is $progress% done")
            }.addOnPausedListener {
            }
    }

    fun whenBtnBackClicked() {
        finishActivity.value = true
    }

    fun whenTvPostClicked() {
        postComment()
        hideKeyboard.value = true
    }

    fun whenIvLikeClicked() {
        twinkleIndex.value?.let {
            postLike(it)
            likePostSuccess.value = true
        }
    }

    fun whenIvPhotoClicked(i: Int) {
        selectedPhotoIndex.value = i
        getTwinklePhoto.value = true
    }

    fun whenIvPhotoDeleteClicked(i: Int) {
        _twinkleImgList.get()?.set(i, null)
        _twinkleImgList.notifyPropertyChanged(BR._all)
        photoCount--
    }

    fun whenIvReceiptClicked() {
        getReceiptPhoto.value = true
    }

    fun whenIvReceiptDeleteClicked() {
        _receiptImgUrl.set(null)
        _receiptImgUrl.notifyPropertyChanged(BR._all)
    }

    fun whenTvTwinklePostClicked() {
        if (validateCheck())
            uploadToFirebase()
    }

    fun deletePhotoButtonVisiblillty(uri: Uri?): Int {
        return if (uri == null)
            GONE
        else
            VISIBLE
    }

    private fun validateCheck(): Boolean {
        return when {
            photoCount < 1 -> {
                showDialogText.value = VALIDATE_TWINKLE_PHOTO
                false
            }
            _receiptImgUrl.get() == null -> {
                showDialogText.value = VALIDATE_RECEIPT_PHOTO
                false
            }
            editTextContentString.get()?.isEmpty() == true -> {
                showDialogText.value = VALIDATE_TWINKLE_CONTENT
                false
            }
            else -> true
        }
    }
}