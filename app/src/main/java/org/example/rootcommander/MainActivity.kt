package org.example.rootcommander

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.Gravity
import android.widget.*
import java.util.concurrent.TimeUnit

class MainActivity : Activity() {
    private val trees = arrayOfNulls<Uri>(2)
    private val folders = arrayOfNulls<Uri>(2)
    private var selected: Uri? = null
    private var selectedName = ""
    private var selectedPane = 0
    private lateinit var listing: LinearLayout
    private lateinit var log: TextView
    private lateinit var command: EditText
    private val fg = Color.rgb(228, 241, 251)
    private fun background(): GradientDrawable = GradientDrawable().apply {
        setColor(Color.rgb(28, 44, 65)); cornerRadius = 18f * resources.displayMetrics.density
    }
    private fun button(text: String, action: () -> Unit): Button = Button(this).apply {
        this.text = text; isAllCaps = false; setTextColor(fg); background = background()
        setOnClickListener { action() }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(11, 19, 32)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL; setPadding(12, 12, 12, 12)
            setBackgroundColor(Color.rgb(11, 19, 32))
        }
        root.addView(TextView(this).apply {
            text = "ROOTCOMMANDER 1.3"; textSize = 24f
            setTextColor(Color.rgb(90, 204, 230)); gravity = Gravity.CENTER
        })
        val row = LinearLayout(this)
        row.addView(button("◀ Links") { choose(0) }, LinearLayout.LayoutParams(0, -2, 1f))
        row.addView(button("Rechts ▶") { choose(1) }, LinearLayout.LayoutParams(0, -2, 1f))
        root.addView(row)
        root.addView(button("📘 Wissen & Sicherheit") { startActivity(Intent(this, GuideActivity::class.java)) })
        listing = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        root.addView(ScrollView(this).apply { addView(listing) }, LinearLayout.LayoutParams(-1, 0, 2f))
        val operations = LinearLayout(this)
        operations.addView(button("◀ Kopieren") { copyTo(0) }, LinearLayout.LayoutParams(0, -2, 1f))
        operations.addView(button("Kopieren ▶") { copyTo(1) }, LinearLayout.LayoutParams(0, -2, 1f))
        root.addView(operations)
        root.addView(button("APK öffnen") {
            startActivityForResult(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "application/vnd.android.package-archive"
                addCategory(Intent.CATEGORY_OPENABLE)
            }, 103)
        })
        command = EditText(this).apply { hint = "Shell-Befehl"; setTextColor(fg); setHintTextColor(Color.LTGRAY) }
        root.addView(command)
        val terminals = LinearLayout(this)
        terminals.addView(button("Shell") { runShell(false) }, LinearLayout.LayoutParams(0, -2, 1f))
        terminals.addView(button("Root (su)") {
            AlertDialog.Builder(this).setTitle("Root-Befehl bestätigen")
                .setMessage(command.text.toString()).setNegativeButton("Abbrechen", null)
                .setPositiveButton("Ausführen") { _, _ -> runShell(true) }.show()
        }, LinearLayout.LayoutParams(0, -2, 1f))
        root.addView(terminals)
        log = TextView(this).apply { text = "Bereit"; setTextColor(fg); setPadding(8, 8, 8, 8) }
        root.addView(ScrollView(this).apply { addView(log) }, LinearLayout.LayoutParams(-1, 0, 1f))
        setContentView(root)
    }
    private fun choose(pane: Int) {
        startActivityForResult(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }, 101 + pane)
    }
    @Deprecated("Legacy result")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) return
        val uri = data?.data ?: return
        if (requestCode == 103) {
            if (!packageManager.canRequestPackageInstalls()) {
                startActivity(Intent(android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                    Uri.parse("package:$packageName")))
                log.text = "Installationsfreigabe erteilen und APK erneut auswählen"
                return
            }
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/vnd.android.package-archive")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            })
            return
        }
        val pane = requestCode - 101
        if (pane !in 0..1) return
        try {
            contentResolver.takePersistableUriPermission(uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            trees[pane] = uri
            folders[pane] = DocumentsContract.buildDocumentUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri))
            show(pane)
        } catch (e: Exception) { log.text = "Ordnerfehler: ${e.message}" }
    }
    private fun show(pane: Int) {
        val tree = trees[pane] ?: return
        val folder = folders[pane] ?: return
        selected = null; selectedPane = pane
        listing.removeAllViews()
        val children = DocumentsContract.buildChildDocumentsUriUsingTree(tree, DocumentsContract.getDocumentId(folder))
        try {
            contentResolver.query(children, arrayOf(
                DocumentsContract.Document.COLUMN_DOCUMENT_ID,
                DocumentsContract.Document.COLUMN_DISPLAY_NAME,
                DocumentsContract.Document.COLUMN_MIME_TYPE), null, null, null)?.use { c ->
                while (c.moveToNext()) {
                    val uri = DocumentsContract.buildDocumentUriUsingTree(tree, c.getString(0))
                    val name = c.getString(1)
                    val isFolder = c.getString(2) == DocumentsContract.Document.MIME_TYPE_DIR
                    listing.addView(button((if (isFolder) "📁 " else "📄 ") + name) {
                        if (isFolder) { folders[pane] = uri; show(pane) }
                        else { selected = uri; selectedName = name; selectedPane = pane; log.text = "Ausgewählt: $name" }
                    })
                }
            }
        } catch (e: Exception) { log.text = "Lesefehler: ${e.message}" }
    }
    private fun copyTo(targetPane: Int) {
        val source = selected ?: return
        val dest = folders[targetPane] ?: return
        if (selectedPane == targetPane) { log.text = "Anderes Ziel wählen"; return }
        AlertDialog.Builder(this).setTitle("Datei kopieren").setMessage(selectedName)
            .setNegativeButton("Abbrechen", null).setPositiveButton("Kopieren") { _, _ ->
                Thread {
                    val result = try {
                        val tree = trees[targetPane]!!
                        val children = DocumentsContract.buildChildDocumentsUriUsingTree(tree, DocumentsContract.getDocumentId(dest))
                        var exists = false
                        contentResolver.query(children, arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME), null, null, null)?.use { c ->
                            while (c.moveToNext()) if (c.getString(0) == selectedName) exists = true
                        }
                        if (exists) "Datei existiert bereits"
                        else {
                            val outUri = DocumentsContract.createDocument(contentResolver, dest,
                                contentResolver.getType(source) ?: "application/octet-stream", selectedName)
                                ?: error("Zieldatei konnte nicht angelegt werden")
                            try {
                                contentResolver.openInputStream(source)!!.use { input ->
                                    contentResolver.openOutputStream(outUri, "w")!!.use { output -> input.copyTo(output) }
                                }
                                "Kopiert: $selectedName"
                            } catch (e: Exception) {
                                DocumentsContract.deleteDocument(contentResolver, outUri)
                                throw e
                            }
                        }
                    } catch (e: Exception) { "Kopierfehler: ${e.message}" }
                    runOnUiThread { log.text = result }
                }.start()
            }.show()
    }
    private fun runShell(root: Boolean) {
        val cmd = command.text.toString()
        if (cmd.isBlank()) return
        log.text = "Ausführen…"
        Thread {
            val result = try {
                val p = ProcessBuilder(if (root) listOf("su", "-c", cmd)
                    else listOf("/system/bin/sh", "-c", cmd)).redirectErrorStream(true).start()
                val output = StringBuilder()
                val reader = Thread {
                    try { p.inputStream.bufferedReader().useLines { lines ->
                        for (line in lines) if (output.length < 12000) output.append(line.take(1000)).append('\n')
                    } } catch (_: Exception) {}
                }
                reader.start()
                val finished = p.waitFor(15, TimeUnit.SECONDS)
                if (!finished) p.destroyForcibly()
                reader.join(1000)
                (if (finished) "Exit ${p.exitValue()}" else "Timeout") + "\n" + output.toString()
            } catch (e: Exception) { "Shell-Fehler: ${e.message}" }
            runOnUiThread { log.text = result }
        }.start()
    }
}