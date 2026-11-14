package org.example.rootcommander

import java.util.Locale

/** Einfache Sprache für wichtige Schaltflächen. Android-Sprache wird automatisch erkannt. */
object Languages {
    private val words = mapOf(
        "de" to mapOf("left" to "◀ Links", "right" to "Rechts ▶", "copyLeft" to "◀ Kopieren", "copyRight" to "Kopieren ▶", "apk" to "APK öffnen", "shell" to "Shell", "root" to "Root (su)", "guide" to "📘 Wissen & Sicherheit", "ready" to "Bereit", "termux" to "Termux", "shizuku" to "Shizuku"),
        "en" to mapOf("left" to "◀ Left", "right" to "Right ▶", "copyLeft" to "◀ Copy", "copyRight" to "Copy ▶", "apk" to "Open APK", "shell" to "Shell", "root" to "Root (su)", "guide" to "📘 Help & safety", "ready" to "Ready", "termux" to "Termux", "shizuku" to "Shizuku"),
        "fr" to mapOf("left" to "◀ Gauche", "right" to "Droite ▶", "copyLeft" to "◀ Copier", "copyRight" to "Copier ▶", "apk" to "Ouvrir APK", "shell" to "Terminal", "root" to "Root (su)", "guide" to "📘 Aide et sécurité", "ready" to "Prêt", "termux" to "Termux", "shizuku" to "Shizuku"),
        "pt" to mapOf("left" to "◀ Esquerda", "right" to "Direita ▶", "copyLeft" to "◀ Copiar", "copyRight" to "Copiar ▶", "apk" to "Abrir APK", "shell" to "Terminal", "root" to "Root (su)", "guide" to "📘 Ajuda e segurança", "ready" to "Pronto", "termux" to "Termux", "shizuku" to "Shizuku")
    )
    fun label(key: String): String {
        val language = Locale.getDefault().language
        return words[language]?.get(key) ?: words.getValue("en")[key] ?: key
    }
}
