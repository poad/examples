package com.github.poad.test.deviceflowexample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import com.github.poad.test.deviceflowexample.api.Client
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.coroutines.*
import java.util.*

/**
 * Loads [MainFragment].
 */
class MainActivity() : Activity() {


    private var deviceCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val oauthProps = Properties();
        oauthProps.load(resources.openRawResource((R.raw.oauth)))

        val client = Client("https://%s/".format(oauthProps.getProperty("auth0_domain")))
            .createService()

        val imageViewQRCode = findViewById<ImageView>(R.id.imageView)
        val verificationUriTextView = findViewById<TextView>(R.id.verificationUriTextView)
        val userCodeTextView = findViewById<TextView>(R.id.userCodeTextView)

        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
            try {
                val response = client
                    .oauthDeviceCode(
                        oauthProps.getProperty("oauth_client_id"),
                        "profile",
                        "https://%s/userinfo".format(oauthProps.getProperty("auth0_domain"))
                    )

                Log.v("oauth", response.toString())
                val bitmap = BarcodeEncoder().encodeBitmap(response.verificationUriComplete, BarcodeFormat.QR_CODE, 300, 300)

                imageViewQRCode.setImageBitmap(bitmap)
                imageViewQRCode.visibility = VISIBLE
                verificationUriTextView.text = response.verificationUri
                verificationUriTextView.visibility = VISIBLE
                userCodeTextView.text = response.userCode
                userCodeTextView.visibility = VISIBLE
                deviceCode = response.deviceCode
            } catch (e: Exception) {
                Log.d("[ERROR]", e.toString())
            }
        }

        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
            while (true) {
                try {
                    if (Objects.nonNull(deviceCode)) {
                        val response = client.token(
                            oauthProps.getProperty("oauth_client_id"),
                            deviceCode!!
                        )
                        Log.v("oauth", response.toString())
                        startActivity(Intent(applicationContext, AuthenticatedActivity::class.java))
                        break
                    }
                } catch (e: Exception) {
                    // TODO UserCode が expire した時の動きを追加する
//                    Log.d("[ERROR]", e.toString())
                }
                delay((1.5 * 1000L).toLong())
            }
        }
    }
}