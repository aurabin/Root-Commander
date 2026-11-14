# Motorola Moto G14 – Diagnose ohne Root

**Gerät:** Motorola Moto G14, Android 14 (vom Nutzer angegeben). Diese Anleitung führt keine Befehle auf dem Gerät aus.

## 1. Grundprüfung
- Starte das Handy neu und prüfe, ob ausreichend Speicher frei ist: Einstellungen → Speicher.
- Prüfe Android-Updates: Einstellungen → System → Systemupdate (Menünamen können abweichen).
- Installiere Apps nur aus vertrauenswürdigen Quellen. Prüfe die APK-Datei und ihre Herkunft.

## 2. RootCommander installieren
- Öffne https://github.com/aurabin/Root-Commander/actions und wähle einen **erfolgreichen** Android-Build.
- Lade das Artefakt `RootCommander-debug-apk` herunter, entpacke es und öffne `app-debug.apk`.
- Erlaube bei Nachfrage **Apps aus dieser Quelle installieren** nur für die verwendete Datei-App.
- Wenn die Installation fehlschlägt, notiere die **genaue Fehlermeldung**. Prüfe Speicher, vorhandene Versionen und Android-Sicherheitsabfragen.

## 3. Ordnerzugriff
- Tippe in RootCommander auf Links oder Rechts und wähle einen Ordner.
- Bestätige die Android-Ordnerfreigabe. Manche Systemordner bleiben unter Android 14 gesperrt.
- Wenn eine Freigabe ungültig wird, wähle den Ordner erneut.
- Das ist **kein Root-Zugriff**.

## 4. Entwickleroptionen
- Einstellungen → Über das Telefon → Build-Nummer siebenmal antippen, gegebenenfalls PIN eingeben.
- Einstellungen → System → Entwickleroptionen.
- USB-Debugging nur bei Bedarf aktivieren. OEM-Entsperrung nicht ohne vorherige Datensicherung und Prüfung verwenden.

## 5. Root prüfen
- Nutze in RootCommander **Root prüfen / Check root**.
- Falls kein `su` installiert oder autorisiert ist, meldet die App keinen Root-Zugriff.
- RootCommander kann Root nicht automatisch herstellen.

## 6. Termux und Shizuku
- Termux: https://f-droid.org/packages/com.termux/
- Shizuku: https://shizuku.rikka.app/
- Beide müssen getrennt installiert und vom Nutzer eingerichtet werden.
- Shizuku allein ist kein Root und RootCommander nutzt Shizuku bislang nur als Start-Hilfe, nicht als Dateizugriffs-Backend.

## 7. Fehler melden
Schreibe die **genaue Fehlermeldung** auf und teile mit, bei welchem Schritt sie erscheint. Für tiefe Fehleranalyse sind freiwillig bereitgestellte, vorab auf private Daten geprüfte Android-Protokolle erforderlich. Keine Passwörter, Tokens, privaten Dateinamen oder Kontodaten veröffentlichen.

## Grenzen
Ein erfolgreicher GitHub-Build ersetzt keinen Test auf dem Moto G14. Ohne Gerätedaten und Fehlerprotokolle lassen sich nicht alle Probleme erkennen oder beheben.
