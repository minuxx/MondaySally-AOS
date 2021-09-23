package com.moon.android.mondaysally.ui.main.auth.qr_camera

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.util.Size
import androidx.annotation.LayoutRes
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.repository.SharedPrefRepository
import com.moon.android.mondaysally.databinding.ActivityQrCameraBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.main.auth.AuthViewModel
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.WORK_STATUS_OFF
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.WORK_STATUS_ON
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.Executors

private const val REQUEST_CODE_PERMISSIONS = 10
private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

class QRCameraActivity : BaseActivity<ActivityQrCameraBinding>() {

    private val authViewModel: AuthViewModel by viewModel()
    private lateinit var sharedPrefRepository: SharedPrefRepository

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private val cameraExecutor = Executors.newSingleThreadExecutor()

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_qr_camera

    override fun initDataBinding() {
        sharedPrefRepository = SharedPrefRepository(this)

        binding.lifecycleOwner = this
        binding.viewModel = authViewModel

        authViewModel.finish.observe(this, { finish ->
            if (finish) {
                finish()
            }
        })

        authViewModel.postWorkSuccess.observe(this, { postWorkSuccess ->
            if (postWorkSuccess) {
                if (sharedPrefRepository.getWorkState() == WORK_STATUS_OFF) {
                    sharedPrefRepository.saveWorkStatus(WORK_STATUS_ON)
                    showToast(getString(R.string.qr_on_message))
                } else {
                    sharedPrefRepository.saveWorkStatus(WORK_STATUS_OFF)
                    showToast(getString(R.string.qr_off_message))
                }
                finish()
            }
        })

        authViewModel.fail.observe(
            this,
            { fail ->
//            341	"존재하지 않는 사용자입니다."
//            378	"코드 형식을 정확하게 입력해주세요."
//            378	"코드를 입력해주세요."
//            404	"네트워크 오류가 발생했습니다."
                when (fail.code) {
                    else -> {
                        showToast(getString(R.string.default_fail))
                    }
                }
                authViewModel.qrPossibleStatusForDelay = true;
            })
    }

    override fun initAfterBinding() {
        if (sharedPrefRepository.getWorkState() == WORK_STATUS_OFF) {
            binding.activityQrCameraTvStatus.text = getString(R.string.qr_status_off)
        } else {
            binding.activityQrCameraTvStatus.text = getString(R.string.qr_status_on)
        }
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        // Request camera permissions
        if (allPermissionsGranted()) {
            cameraProvider()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun cameraProvider() {
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            startCamera(cameraProvider)
            binding.activityQrIvGuide.animate()
                .scaleX(1.12f).scaleY(1.12f)
                .setDuration(500)
        }, ContextCompat.getMainExecutor(this))
    }

    private fun startCamera(cameraProvider: ProcessCameraProvider) {
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        val preview = Preview.Builder().apply {
            setTargetResolution(
                Size(
                    binding.activityQrCameraPreview.width,
                    binding.activityQrCameraPreview.height
                )
            )
        }.build()

        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(
                Size(
                    binding.activityQrCameraPreview.width,
                    binding.activityQrCameraPreview.height
                )
            )
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, QrCodeAnalyzer { qrResult ->
                    binding.activityQrCameraPreview.post {
                        Log.d("QRCodeAnalyzer", "Barcode scanned: ${qrResult.text}")
                        if(authViewModel.qrPossibleStatusForDelay){
                            authViewModel.postWork()
                            authViewModel.qrPossibleStatusForDelay = false;
                        }
                    }
                })
            }
        cameraProvider.unbindAll()

        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
        preview.setSurfaceProvider(binding.activityQrCameraPreview.surfaceProvider)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                cameraProvider()
            } else {
                showToast("카메라 권한 허용이 필요합니다")
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

}