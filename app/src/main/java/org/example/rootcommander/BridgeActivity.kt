package org.example.rootcommander

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import org.json.JSONObject

/**
 * Local, consent-gated command bridge. No remote listener, root or shell exposure.
 * Accepts Android ACTION_SEND text from the user's share sheet.
 */
class BridgeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val status = TextView(this).apply {
            text = "RootCommander Bridge: waiting for approval"
            setPadding(32, 32, 32, 32)
        }
        setContentView(status)
        val raw = intent?.getStringExtra(Intent.EXTRA_TEXT) ?: ""
        val request = try { JSONObject(raw) } catch (_: Exception) { null }
        val action = request?.optString("action") ?: ""
        val requestId = request?.optString("request_id") ?: ""
        if (request == null || request.optInt("version") != 1 ||
            requestId.length !in 1..128 || action != "device_info") {
            status.text = "Unsupported request. Only version 1 device_info is enabled."
            return
        }
        AlertDialog.Builder(this)
            .setTitle("ChatGPT Bridge – Anfrage bestätigen")
            .setMessage("Geräteinformationen teilen?\nAktion: device_info\nID: $requestId\n\nEs werden keine Dateien, Kennungen oder Root-Rechte übertragen.")
            .setNegativeButton("Ablehnen") { _, _ -> finish() }
            .setPositiveButton("Freigeben") { _, _ ->
                val response = JSONObject()
                    .put("version", 1)
                    .put("request_id", requestId)
                    .put("status", "ok")
                    .put("result", JSONObject()
                        .put("android_version", Build.VERSION.RELEASE)
                        .put("sdk", Build.VERSION.SDK_INT)
                        .put("app", "RootCommander")
                        .put("app_version", "1.4"))
                startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, response.toString(2))
                }, "Antwort teilen"))
                finish()
            }.setCancelable(false).show()
    }
}
