package com.nexus.wifi

import android.app.Activity
import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val VPN_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btnStart)
        btnStart.setOnClickListener {
            startVpnBridge()
        }
    }

    private fun startVpnBridge() {
        // طلب إذن الـ VPN من المستخدم (مهم جداً للعمل بدون روت)
        val intent = VpnService.prepare(this)
        if (intent != null) {
            startActivityForResult(intent, VPN_REQUEST_CODE)
        } else {
            onActivityResult(VPN_REQUEST_CODE, Activity.RESULT_OK, null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VPN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val intent = Intent(this, MainService::class.java)
            startService(intent)
            Toast.makeText(this, "Nexus Bridge Started!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Permission Denied. Cannot boost without VPN.", Toast.LENGTH_LONG).show()
        }
    }
}
