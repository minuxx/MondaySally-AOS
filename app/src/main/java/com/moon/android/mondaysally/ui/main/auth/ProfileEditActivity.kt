package com.moon.android.mondaysally.ui.main.auth

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityProfileEditBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.SallyDialog
import com.moon.android.mondaysally.ui.main.twinkle.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class ProfileEditActivity : BaseActivity<ActivityProfileEditBinding>() {

    private val authViewModel: AuthViewModel by viewModel()
    private val bottomDialogFragment = BottomSheetDialogFragment()
    private val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_profile_edit

    private val imageFromGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK)
                cropImage(result.data?.data)
        }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            // 퍼미션 허용 여부 값 처리
            if (isGranted) {

            } else {
                //권한미동의시
            }
        }

    private val cropImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            when (result.resultCode) {
                RESULT_OK -> {
                    result.data?.data?.let {
                        authViewModel.profileUri = it
                        setCircleImageFromUri(it, binding.activityProfileEditIvProfile)
                    }
                }
                RESULT_CANCELED -> {
                }
            }
        }

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = authViewModel

        authViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
                showLottieDialog(this)
            } else {
                hideLottieDialog()
            }
        })

        authViewModel.finish.observe(this, { finish ->
            if (finish) {
                finish()
            }
        })

        authViewModel.editDoneClick.observe(this, { editDoneClick ->
            if (editDoneClick) {
                if (authViewModel.validateCheck()) {
                    //dialog check
                    showSallyDialog(
                        this,
                        getString(R.string.profile_edit_check),
                        getString(R.string.ok),
                        object : SallyDialog.DialogClickListener {
                            override fun onOKClicked() {
                                authViewModel.uploadToFirebase()
                            }
                        })
                }
            }
        })

        authViewModel.validateMessage.observe(this, { validateMessage ->
            showToast(validateMessage)
        })

        authViewModel.profileEditSuccess.observe(this, { profileEditSuccess ->
            if (profileEditSuccess) {
                finish()
            }
        })

        authViewModel.bottomSheetOpen.observe(this, { bottomSheetOpen ->
            if (bottomSheetOpen) {
                bottomDialogFragment.show(supportFragmentManager, bottomDialogFragment.tag)
                authViewModel.bottomSheetOpen.value = false
            }
        })


        authViewModel.fail.observe(this, { fail ->
//            341	"존재하지 않는 사용자입니다."
//            378	"코드 형식을 정확하게 입력해주세요."
//            378	"코드를 입력해주세요."
//            404	"네트워크 오류가 발생했습니다."
            when (fail.code) {
                341, 402, 378 -> {
                    showToast(fail.message)
                }
                else -> {
                    showToast(getString(R.string.default_fail))
                }
            }
        })
    }

    override fun initAfterBinding() {
        authViewModel.editTextNicknameString.set(intent.getStringExtra("nickname"))
        authViewModel.editTextPhoneString.set(intent.getStringExtra("phone"))
        authViewModel.editTextBankString.set(intent.getStringExtra("bank"))
        authViewModel.editTextEmailString.set(intent.getStringExtra("email"))
        authViewModel.profileUrl = intent.getStringExtra("imgUrl")
        if (intent.getStringExtra("imgUrl").isNullOrEmpty()) {
            authViewModel.profileUrl = ""
        }

        bottomDialogFragment.setOnDeleteClickListener {
            bottomDialogFragment.dismiss()
            authViewModel.profileUrl = ""
            binding.activityProfileEditIvProfile.setImageResource(R.drawable.ic_photo_mid)
        }
        bottomDialogFragment.setOnEditClickListener {
            bottomDialogFragment.dismiss()
            getImageFromGallery()
        }

//        binding.activityMyPageEtPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
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

        cropImageLauncher.launch(cropImageIntent)
    }

    private fun getImageFromGallery() {
        //권한체크
        permissionLauncher.launch(permission)
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.type = "image/*"
        imageFromGalleryLauncher.launch(intent)
    }

}