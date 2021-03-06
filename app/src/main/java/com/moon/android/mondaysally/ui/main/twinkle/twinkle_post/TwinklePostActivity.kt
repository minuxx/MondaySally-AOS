package com.moon.android.mondaysally.ui.main.twinkle.twinkle_post

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.text.Editable
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.moon.android.mondaysally.BR
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.TwinkleImageUpload
import com.moon.android.mondaysally.data.entities.TwinkleResult
import com.moon.android.mondaysally.databinding.ActivityTwinklePostBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.SallyDialog
import com.moon.android.mondaysally.ui.main.MainActivity
import com.moon.android.mondaysally.ui.main.twinkle.TwinkleViewModel
import com.moon.android.mondaysally.ui.main.twinkle.twinkle_detail.TwinkleDetailActivity
import com.moon.android.mondaysally.utils.GlobalConstant
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.RECEIPT_IMAGE_MODE
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.TWINKLE_IMAGE_MODE
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.VALIDATE_RECEIPT_PHOTO
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.VALIDATE_TWINKLE_CONTENT
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.VALIDATE_TWINKLE_PHOTO
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class TwinklePostActivity : BaseActivity<ActivityTwinklePostBinding>() {

    private val twinkleViewModel: TwinkleViewModel by viewModel()
    lateinit var context: Context
    override fun getLayoutResId() = R.layout.activity_twinkle_post
    private var imageMode: Int = 0

    private val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
    private lateinit var twinkleResult: TwinkleResult

    private val twinkleImageFromGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK)
                cropImage(result.data?.data)
        }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            // ????????? ?????? ?????? ??? ??????
            if (isGranted) {

            } else {
                //??????????????????
                finish()
                showToast("????????? ????????? ????????? ???????????????.")
            }
        }

    private val twinkleCropImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            when (result.resultCode) {
                RESULT_OK -> {
                    if (imageMode == TWINKLE_IMAGE_MODE) {
                        result.data?.data?.let {
                            twinkleViewModel.twinkleImageUploadFlag[twinkleViewModel.selectedPhotoIndex.value!!] = TwinkleImageUpload(true, false)
                            twinkleViewModel._twinkleImgList.get()?.set(twinkleViewModel.selectedPhotoIndex.value!!, it)
                            twinkleViewModel._twinkleImgList.notifyPropertyChanged(BR._all)
                            twinkleViewModel.editTwinkleImgList[twinkleViewModel.selectedPhotoIndex.value!!] = true
                        }
                    } else {
                        result.data?.data?.let {
                            twinkleViewModel._receiptImgUrl.set(it)
                            twinkleViewModel._receiptImgUrl.notifyPropertyChanged(BR._all)
                            twinkleViewModel.editReceiptImgUrl = true
                        }
                    }
                }
                RESULT_CANCELED -> {
                }
            }
        }

    override fun initDataBinding() {
        context = this;
        binding.lifecycleOwner = this;
        binding.viewModel = twinkleViewModel

        twinkleViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
                showLottieDialog(this)
            } else {
                hideLottieDialog()
            }
        })

        twinkleViewModel.hideKeyboard.observe(this, { hideKeyboard ->
            if (hideKeyboard)
                hideKeyboard(binding.activityTwinklePostEtContent)
        })

        twinkleViewModel.firebaseUploadSuccess.observe(this, { firebaseUploadSuccess ->
            if (firebaseUploadSuccess)
                if (intent.getStringExtra("mode") == GlobalConstant.EDIT_MODE) {
                    twinkleViewModel.patchTwinkle()
                } else {
                    twinkleViewModel.postTwinkle()
                }
        })

        twinkleViewModel.getTwinklePhoto.observe(this, { getPhoto ->
            if (getPhoto) {
                imageMode = TWINKLE_IMAGE_MODE
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                intent.type = "image/*"
                twinkleImageFromGalleryLauncher.launch(intent)
            }
        })

        twinkleViewModel.getReceiptPhoto.observe(this, { getReceiptPhoto ->
            if (getReceiptPhoto) {
                imageMode = RECEIPT_IMAGE_MODE
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                intent.type = "image/*"
                twinkleImageFromGalleryLauncher.launch(intent)
            }
        })

        twinkleViewModel.twinklePostSuccess.observe(this, { twinklePostSuccess ->
            if (twinklePostSuccess) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                intent.putExtra("navigation","twinkle")
                startActivity(intent)
                finish()
            }
        })

        twinkleViewModel.twinklePatchSuccess.observe(this, { twinklePatchSuccess ->
            if (twinklePatchSuccess) {
                val intent = Intent(context, TwinklePostActivity::class.java)
                setResult(RESULT_OK, intent)
                finish()
            }
        })

        twinkleViewModel.finishActivity.observe(this, { finishActivity ->
            if (finishActivity)
                finish()
        })

        twinkleViewModel.commentPostSuccess.observe(this, { commentPostSuccess ->
            if (commentPostSuccess) {
                //ReLoading ?
                twinkleViewModel.getTwinkleDetail(twinkleViewModel.twinkleIndex.value!!)
                twinkleViewModel.editTextCommentString.set("")
                binding.activityTwinklePostEtContent.clearFocus()
                twinkleViewModel.commentPostSuccess.value = false
            }
        })

        twinkleViewModel.showDialogText.observe(this, { showDialogText ->
            var text = ""
            when (showDialogText) {
                VALIDATE_TWINKLE_PHOTO -> text = getString(R.string.twinkle_validate_twinkle_photo)
                VALIDATE_RECEIPT_PHOTO -> text = getString(R.string.twinkle_validate_receipt_photo)
                VALIDATE_TWINKLE_CONTENT -> text =
                    getString(R.string.twinkle_validate_twinkle_content)
            }
            showSallyDialog(
                this,
                text,
                getString(R.string.ok),
                object : SallyDialog.DialogClickListener {
                    override fun onOKClicked() {

                    }
                })
        })

        twinkleViewModel.fail.observe(this, { fail ->
//            341	"???????????? ?????? ??????????????????."
//            346	"?????? ????????? ?????????????????????."
//            353	"????????? ????????? ???????????? ?????? ??????????????? ????????????."
//            370	"???????????? ?????? ???????????????."
//            377	"????????? ???????????????."
//            388	"JWT????????? ??????????????????."
//            389	"???????????? ?????? JWT???????????????."
//            404	"???????????? ????????? ??????????????????."
            when (fail.code) {
                353, 346, 370, 377 -> {
                    showToast(fail.message)
                }
                341, 388, 389, 404 -> {
                    showToast(getString(R.string.default_fail))
                }
                else -> showToast(fail.message)
            }
        })
    }

    override fun initAfterBinding() {
        binding.activityTwinklePostTvGiftName.text = intent.getStringExtra("name")
        binding.activityTwinklePostTvClover.text = intent.getIntExtra("usedClover", 0).toString()
        twinkleViewModel.twinkleIndex.value = intent.getIntExtra("idx", 0)
        permissionLauncher.launch(permission)

        if (intent.getStringExtra("mode") == GlobalConstant.EDIT_MODE) {
            setEditMode()
        }
    }

    private fun setEditMode(){
        twinkleResult = intent.getSerializableExtra("twinkleResult") as TwinkleResult
        binding.activityTwinklePostBtnDone.text = getString(R.string.modify)
        binding.activityTwinklePostTitle.text = getString(R.string.twinkle_edit)
        twinkleViewModel.editTextContentString.set(twinkleResult.content)

        twinkleResult.twinkleImglists.forEachIndexed { index, url ->
            twinkleViewModel.twinkleImgList[index] = url
            twinkleViewModel.twinkleImageUploadFlag[index] = TwinkleImageUpload(true, true)
//            twinkleViewModel.photoCount++
//            twinkleViewModel.uploadDoneCount++
        }
        twinkleViewModel.receiptImgUrl = twinkleResult.receiptImgUrl
    }

    @SuppressLint("SimpleDateFormat", "QueryPermissionsNeeded")
    private fun cropImage(photoDirectory: Uri?) {
        var photoUri: Uri? = photoDirectory

        val intent = Intent("com.android.camera.action.CROP").apply {
            type = "image/*"
            data = photoUri
        }
        intent.setDataAndType(photoUri, "image/*")

        val list: MutableList<ResolveInfo> = packageManager.queryIntentActivities(intent, 0)

        if (list.size == 0) {
            return
        }
        val getCropImageActivity: ResolveInfo = list[0]

        grantUriPermission(
            getCropImageActivity.activityInfo.packageName,
            photoUri,
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        intent.putExtra("crop", "true")
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("scale", true)

        val storageDirectory: Array<File> =
            ContextCompat.getExternalFilesDirs(applicationContext, null)

        val croppedFileName: File = File.createTempFile(
            "${SimpleDateFormat("HHmmss").format(Date())}_",
            ".jpg",
            storageDirectory[0]
        )

        val folder: Array<File> =
            ContextCompat.getExternalFilesDirs(applicationContext, null)
        val tempFile = File(folder[0].toString(), croppedFileName.name)
        photoUri = FileProvider.getUriForFile(
            this,
            "com.moon.android.mondaysally.provider",
            tempFile
        )
        intent.putExtra("return-data", false)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())

        val cropImageIntent = Intent(intent)
        cropImageIntent.component = ComponentName(
            getCropImageActivity.activityInfo.packageName,
            getCropImageActivity.activityInfo.name
        )

        grantUriPermission(
            getCropImageActivity.activityInfo.packageName, photoUri,
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        twinkleCropImageLauncher.launch(cropImageIntent)
    }
}