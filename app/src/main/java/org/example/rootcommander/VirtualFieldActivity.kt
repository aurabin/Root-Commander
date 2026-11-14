package org.example.rootcommander

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin

/** Local, user-controlled Android launcher dashboard. No hidden privileges. */
class VirtualFieldActivity : Activity() {
    private val ink = Color.rgb(233, 245, 255)
    private val cyan = Color.rgb(69, 212, 242)
    private fun tr(de: String, en: String, fr: String, pt: String) = when (Locale.getDefault().language) {
        "de" -> de; "fr" -> fr; "pt" -> pt; else -> en
    }
    private fun dp(n: Int) = (n * resources.displayMetrics.density).toInt()
    private fun panel(color: Int, radius: Int = 22) = GradientDrawable().apply {
        setColor(color); cornerRadius = dp(radius).toFloat()
        setStroke(dp(1), Color.rgb(54, 88, 118))
    }
    private fun label(s: String, size: Float, color: Int = ink) = TextView(this).apply {
        text = s; textSize = size; setTextColor(color)
    }
    private fun launch(intent: Intent) {
        try { startActivity(intent) }
        catch (_: Exception) {
            android.app.AlertDialog.Builder(this)
                .setMessage(tr("Diese Funktion ist auf diesem Gerät nicht verfügbar.",
                    "This feature is not available on this device.",
                    "Fonction indisponible sur cet appareil.",
                    "Função indisponível neste dispositivo."))
                .setPositiveButton("OK", null).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(8, 17, 32)
        window.navigationBarColor = Color.rgb(8, 17, 32)
        val screen = ScrollView(this).apply { isFillViewport = true }
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(18), dp(16), dp(12))
            setBackgroundColor(Color.rgb(8, 17, 32))
        }
        root.addView(label("ROOTCOMMANDER", 13f, cyan))
        root.addView(label(tr("Dein virtuelles Feld", "Your virtual field", "Votre champ virtuel", "Seu campo virtual"), 25f))
        root.addView(label(tr("Deine Werkzeuge. Deine Kontrolle.", "Your tools. Your control.",
            "Vos outils. Votre contrôle.", "Suas ferramentas. Seu controle."), 13f, Color.LTGRAY))
        val orbit = OrbitView()
        root.addView(orbit, LinearLayout.LayoutParams(-1, dp(132)).apply { topMargin = dp(12) })
        val grid = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        val singleColumn = resources.configuration.screenWidthDp < 360 || resources.configuration.fontScale >= 1.35f
        fun tile(symbol: String, title: String, subtitle: String, task: () -> Unit): LinearLayout {
            val item = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                background = panel(Color.rgb(21, 40, 63))
                setPadding(dp(7), dp(11), dp(7), dp(11))
                isClickable = true
                isFocusable = true
                contentDescription = "$title. $subtitle"
                setOnClickListener { task() }
            }
            item.addView(label(symbol, 28f, cyan).apply { gravity = Gravity.CENTER })
            item.addView(label(title, 14f).apply { gravity = Gravity.CENTER; maxLines = 2 })
            item.addView(label(subtitle, 12f, Color.LTGRAY).apply { gravity = Gravity.CENTER; maxLines = 3 })
            return item
        }
        fun row(vararg items: LinearLayout) {
            if (singleColumn) {
                items.forEach { item ->
                    grid.addView(item, LinearLayout.LayoutParams(-1, -2).apply {
                        setMargins(dp(4), dp(5), dp(4), dp(5))
                    })
                    item.minimumHeight = dp(100)
                }
            } else {
                val line = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
                items.forEach { item ->
                    line.addView(item, LinearLayout.LayoutParams(0, -2, 1f).apply {
                        setMargins(dp(4), dp(5), dp(4), dp(5))
                    })
                    item.minimumHeight = dp(115)
                }
                grid.addView(line)
            }
        }
        row(
            tile("▣", tr("Dateien", "Files", "Fichiers", "Arquivos"), tr("Zwei Fenster", "Dual pane", "Deux volets", "Dois painéis")) {
                launch(Intent(this, MainActivity::class.java))
            },
            tile("⌁", tr("Ordner", "Folders", "Dossiers", "Pastas"), tr("Zugriff wählen", "Choose access", "Choisir accès", "Escolher acesso")) {
                launch(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                })
            }
        )
        row(
            tile("◈", tr("Einstellungen", "Settings", "Paramètres", "Configurações"), "Android") {
                launch(Intent(Settings.ACTION_SETTINGS))
            },
            tile("⚙", tr("Entwickler", "Developer", "Développeur", "Desenvolvedor"), tr("Optionen", "Options", "Options", "Opções")) {
                launch(Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS))
            }
        )
        row(
            tile("▤", tr("Apps", "Apps", "Applis", "Apps"), tr("Verwalten", "Manage", "Gérer", "Gerenciar")) {
                launch(Intent(Settings.ACTION_APPLICATION_SETTINGS))
            },
            tile("◉", tr("Speicher", "Storage", "Stockage", "Armazenamento"), tr("Übersicht", "Overview", "Aperçu", "Visão geral")) {
                launch(Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS))
            }
        )
        row(
            tile("⌘", "Termux", tr("Terminal öffnen", "Open terminal", "Ouvrir terminal", "Abrir terminal")) {
                val i = packageManager.getLaunchIntentForPackage("com.termux")
                if (i != null) launch(i) else launch(Intent(Intent.ACTION_VIEW, Uri.parse("https://f-droid.org/packages/com.termux/")))
            },
            tile("◇", "Shizuku", tr("Dienst öffnen", "Open service", "Ouvrir service", "Abrir serviço")) {
                val i = packageManager.getLaunchIntentForPackage("moe.shizuku.privileged.api")
                if (i != null) launch(i) else launch(Intent(Intent.ACTION_VIEW, Uri.parse("https://shizuku.rikka.app/")))
            }
        )
        row(
            tile("ⓘ", tr("Hilfe", "Help", "Aide", "Ajuda"), tr("Anleitung", "Guide", "Guide", "Guia")) {
                launch(Intent(this, GuideActivity::class.java))
            },
            tile("↗", tr("Teilen", "Share", "Partager", "Compartilhar"), tr("Projektlink", "Project link", "Lien du projet", "Link do projeto")) {
                launch(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "https://github.com/aurabin/Root-Commander")
                }, null))
            }
        )
        root.addView(grid)
        root.addView(label(tr("Keine heimliche Fernsteuerung. Android-Berechtigungen bleiben geschützt.",
            "No hidden remote control. Android permissions remain protected.",
            "Aucun contrôle à distance caché. Permissions Android protégées.",
            "Sem controle remoto oculto. Permissões Android protegidas."), 11f, Color.LTGRAY))
        screen.addView(root)
        setContentView(screen)
    }

    private inner class OrbitView : View(this@VirtualFieldActivity) {
        private val p = Paint(Paint.ANTI_ALIAS_FLAG)
        private val start = SystemClock.uptimeMillis()
        override fun onDraw(c: Canvas) {
            super.onDraw(c)
            val x = width / 2f; val y = height / 2f
            val r = minOf(width, height) * .39f
            val angle = (SystemClock.uptimeMillis() - start) / 2000.0
            p.style = Paint.Style.STROKE; p.strokeWidth = dp(1).toFloat()
            for (i in 1..3) {
                p.color = Color.argb(120, 65, 200, 240)
                c.drawCircle(x, y, r * i / 3f, p)
            }
            for (i in 0..7) {
                val a = angle * .45 + i * Math.PI / 4
                val px = x + r * cos(a).toFloat()
                val py = y + r * sin(a).toFloat()
                p.color = Color.rgb(42, 119, 160); c.drawLine(x, y, px, py, p)
                p.style = Paint.Style.FILL; p.color = cyan
                c.drawCircle(px, py, dp(4).toFloat(), p)
                p.style = Paint.Style.STROKE
            }
            p.style = Paint.Style.FILL; p.color = Color.WHITE
            c.drawCircle(x, y, dp(8).toFloat(), p)
            if (visibility == VISIBLE && isAttachedToWindow && windowVisibility == VISIBLE) postInvalidateDelayed(50L)
        }
    }
}
