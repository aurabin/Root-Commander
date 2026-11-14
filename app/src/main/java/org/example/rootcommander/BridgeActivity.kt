package org.example.rootcommander

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject

/**
 * Explicit, user-mediated IPC over Android ACTION_SEND and Storage Access Framework.
 * No network listener, private root access, arbitrary shell, or background control.
 */
class BridgeActivity : Activity() {
    private lateinit var status: TextView
    private var requestId = ""
    private var requestedAction = ""
    private val supported = setOf("capabilities", "device_info", "diagnostics", "list_files")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        status = TextView(this).apply {
            text = "RootCommander – Verbindung über Teilen"; setPadding(32, 32, 32, 32)
        }
        setContentView(status)
        val raw = intent?.getStringExtra(Intent.EXTRA_TEXT) ?: ""
        if (raw.length > 16384) { status.text = "Die Anfrage ist zu lang. Bitte weniger Text senden."; return }
        val request = try { JSONObject(raw) } catch (_: Exception) { null }
        requestedAction = request?.optString("action") ?: ""
        requestId = request?.optString("request_id") ?: ""
        if (request == null || request.optInt("version") != 1 ||
            requestId.length !in 1..128 || requestedAction !in supported) {
            status.text = "Die Anfrage ist ungültig oder diese Funktion wird nicht unterstützt."
            return
        }
        AlertDialog.Builder(this).setTitle("RootCommander – Anfrage")
            .setMessage("Gewünschte Funktion: $requestedAction\nAnfrage-Nummer: $requestId\n\nDu entscheidest, ob die App antworten darf.")
            .setNegativeButton("Ablehnen") { _, _ -> finish() }
            .setPositiveButton("Zulassen") { _, _ ->
                when (requestedAction) {
                    "capabilities" -> reply(JSONObject().put("actions", JSONArray(supported.sorted())))
                    "device_info" -> reply(JSONObject()
                        .put("android_version", Build.VERSION.RELEASE)
                        .put("sdk", Build.VERSION.SDK_INT)
                        .put("app", "RootCommander"))
                    "diagnostics" -> reply(JSONObject()
                        .put("bridge", "available")
                        .put("transport", "android-share-intent")
                        .put("network_listener", false)
                        .put("root_access", false))
                    "list_files" -> startActivityForResult(
                        Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }, 501)
                }
            }.setCancelable(false).show()
    }
    @Deprecated("Legacy result callback")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != 501 || resultCode != RESULT_OK) { finish(); return }
        val tree = data?.data ?: run { finish(); return }
        val entries = JSONArray()
        try {
            val childUri = DocumentsContract.buildChildDocumentsUriUsingTree(
                tree, DocumentsContract.getTreeDocumentId(tree))
            contentResolver.query(childUri, arrayOf(
                DocumentsContract.Document.COLUMN_DISPLAY_NAME,
                DocumentsContract.Document.COLUMN_MIME_TYPE), null, null, null)?.use { cursor ->
                while (cursor.moveToNext() && entries.length() < 200) {
                    entries.put(JSONObject()
                        .put("name", cursor.getString(0))
                        .put("directory", cursor.getString(1) == DocumentsContract.Document.MIME_TYPE_DIR))
                }
            }
            reply(JSONObject().put("entries", entries).put("limited_to", 200))
        } catch (e: Exception) {
            reply(JSONObject().put("error", "Der gewählte Ordner konnte nicht gelesen werden. Bitte einen anderen Ordner auswählen."), "error")
        }
    }
    private fun reply(result: JSONObject, state: String = "ok") {
        val response = JSONObject().put("version", 1)
            .put("request_id", requestId).put("status", state).put("result", result)
        startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, response.toString(2))
        }, "Antwort teilen"))
        finish()
    }
}
