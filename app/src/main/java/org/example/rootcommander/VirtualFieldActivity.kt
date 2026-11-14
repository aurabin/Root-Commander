package org.example.rootcommander

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.net.Uri
import android.provider.Settings
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin

/**
 * Virtual Field: an animated, offline dashboard. No elevated permissions or hidden execution.
 * Each action requires a visible user tap.
 */
class VirtualFieldActivity : Activity() {
    private fun t(de: String, en: String, fr: String, pt: String): String = when (Locale.getDefault().language) {
        "de" -> de
        "fr" -> fr
        "pt" -> pt
        else -> en
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
            setBackgroundColor(Color.rgb(9, 18, 35))
        }
        root.addView(TextView(this).apply {
            text = t("VIRTUELLES FELD", "VIRTUAL FIELD", "CHAMP VIRTUEL", "CAMPO VIRTUAL")
            textSize = 24f
            setTextColor(Color.CYAN)
        })
        root.addView(FieldView(), LinearLayout.LayoutParams(-1, 0, 1f))
        val actions = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        fun action(label: String, task: () -> Unit) {
            actions.addView(Button(this).apply {
                text = label
                isAllCaps = false
                setOnClickListener { task() }
            })
        }
        action(t("Dateimanager öffnen", "Open file manager", "Ouvrir fichiers", "Abrir arquivos")) {
            startActivity(Intent(this, MainActivity::class.java))
        }
        action(t("Ordner auswählen", "Choose folder", "Choisir un dossier", "Escolher pasta")) {
            startActivityForResult(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }, 201)
        }
        action(t("Android-Einstellungen", "Android settings", "Paramètres Android", "Configurações Android")) {
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }
        action(t("Entwickleroptionen (falls verfügbar)", "Developer options (if available)", "Options développeur (si disponibles)", "Opções do desenvolvedor (se disponíveis)")) {
            try { startActivity(Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)) }
            catch (_: Exception) { startActivity(Intent(Settings.ACTION_SETTINGS)) }
        }
        action(t("Hilfe und Sicherheit", "Help and safety", "Aide et sécurité", "Ajuda e segurança")) {
            startActivity(Intent(this, GuideActivity::class.java))
        }
        root.addView(ScrollView(this).apply { addView(actions) }, LinearLayout.LayoutParams(-1, 0, 2f))
        root.addView(TextView(this).apply {
            text = t("Das Feld ist eine Oberfläche, kein Root- oder Sicherheits-Bypass.", "This field is a dashboard, not a root or security bypass.", "Ce champ est une interface, pas un contournement de sécurité.", "Este campo é uma interface, não contorna a segurança.")
            setTextColor(Color.LTGRAY)
        })
        setContentView(root)
    }

    private inner class FieldView : View(this@VirtualFieldActivity) {
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val started = SystemClock.uptimeMillis()
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            val w = width.toFloat()
            val h = height.toFloat()
            val centerX = w / 2f
            val centerY = h / 2f
            val radius = minOf(w, h) * .32f
            val phase = (SystemClock.uptimeMillis() - started) / 1500.0
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 2f
            for (ring in 1..3) {
                paint.color = Color.argb(130, 50, 185, 230)
                canvas.drawCircle(centerX, centerY, radius * ring / 3f, paint)
            }
            for (node in 0 until 8) {
                val a = phase * .35 + node * Math.PI / 4
                val x = centerX + radius * cos(a).toFloat()
                val y = centerY + radius * sin(a).toFloat()
                paint.color = Color.rgb(80, 200, 245)
                canvas.drawLine(centerX, centerY, x, y, paint)
                paint.style = Paint.Style.FILL
                canvas.drawCircle(x, y, 7f, paint)
                paint.style = Paint.Style.STROKE
            }
            paint.style = Paint.Style.FILL
            paint.color = Color.WHITE
            canvas.drawCircle(centerX, centerY, 11f, paint)
            postInvalidateDelayed(33L)
        }
    }
}
