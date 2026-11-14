package org.example.rootcommander

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Button

/** In-app educational guide; does not execute flashing or grant privileges. */
class GuideActivity : Activity() {
    private val sections = listOf(
        "Überblick" to "RootCommander verwaltet nur die Daten und Funktionen, die Android freigibt. Eine installierte App kann einen gesperrten Bootloader nicht umgehen. ChatGPT erhält keine privaten Root-Rechte.",
        "Android 14 ohne Root" to "Dateien: Ordner über die Android-Dateiauswahl freigeben. APKs: Android muss die Installation bestätigen. Termux bietet eine eigene Shell, aber keinen automatischen Root-Zugriff.",
        "Shizuku und ADB" to "Shizuku kann nach ausdrücklicher Einrichtung ausgewählte ADB-Funktionen ermöglichen. Es ersetzt weder einen entsperrten Bootloader noch Magisk. Wireless Debugging muss auf dem Gerät verfügbar und freigegeben sein.",
        "Privater Root-Zugriff" to "Magisk benötigt ein kompatibles, korrekt gepatchtes Boot-Image und in der Regel einen entsperrten Bootloader. Root-Zugriff bleibt lokal; die ChatGPT Bridge darf keine su-Befehle ausführen.",
        "AutoFlash: Voraussetzungen" to "Vorbereitung: Daten sichern, genaue Modell-/Firmwareversion und Build prüfen, Bootloader-Status kontrollieren, Akkuladung und USB-Verbindung prüfen. Ein falsches Image oder eine falsche Partition kann das Gerät unbrauchbar machen.",
        "Windows / Linux" to "Ein externer Rechner kann ADB und Fastboot bereitstellen. Gerätekennung, Partitionsschema und Firmware müssen vor dem Flashen geprüft werden. Der Bootloader kann eine Bestätigung direkt am Telefon verlangen.",
        "Android USB-OTG" to "Ein zweites Android-Gerät kann nur mit geeigneter OTG-Hardware, USB-Berechtigung und kompatibler Fastboot-Software als Host dienen. Nicht jedes Android-Gerät unterstützt dies.",
        "OEM-Entsperrung" to "Entwickleroptionen können OEM-Entsperrung anbieten. Verfügbarkeit hängt von Modell und Gerätevariante ab. Eine Bootloader-Entsperrung löscht normalerweise Benutzerdaten und kann die Gerätesicherheit reduzieren.",
        "Prüfsummen und Images" to "SHA-256 zeigt, ob eine Datei mit einer erwarteten Prüfsumme übereinstimmt. Das beweist NICHT, dass sie zur Gerätevariante, zum Build oder zur Boot-Partition passt. Partition nie raten.",
        "ChatGPT Bridge" to "Die vorhandene Bridge nutzt die Android-Teilen-Funktion. Unterstützt werden capabilities, device_info, diagnostics und list_files nach Bestätigung. Sie ist kein aktiver MCP-/HTTPS-Fernsteuerungsdienst.",
        "Sicherheitsprinzipien" to "Keine geheimen Zugangsdaten in Logs oder GitHub. Keine offenen Shell-/Root-Ports. Kritische Änderungen nur nach Prüfung und ausdrücklicher Zustimmung. Backups vor Systemänderungen erstellen.",
        "Status und Grenzen" to "Dies ist eine Anleitung in der App, kein automatischer Root- oder Flash-Vorgang. Android-APK-Build und Tests auf dem Moto G14 sind weiterhin gesondert zu verifizieren."
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
            setBackgroundColor(Color.rgb(12, 20, 33))
        }
        root.addView(TextView(this).apply {
            text = "RootCommander · Wissen & Sicherheit"
            textSize = 22f
            setTextColor(Color.rgb(110, 212, 232))
        })
        val scroll = ScrollView(this)
        val body = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        for ((title, description) in sections) {
            body.addView(Button(this).apply {
                text = title
                isAllCaps = false
                setOnClickListener {
                    AlertDialog.Builder(this@GuideActivity)
                        .setTitle(title).setMessage(description)
                        .setPositiveButton("Verstanden", null).show()
                }
            })
        }
        scroll.addView(body)
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        setContentView(root)
    }
}
