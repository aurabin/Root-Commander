# RootCommander – Datei-Verwaltung für Android

RootCommander ist eine Android-App im Aufbau. Sie kann zwei vom Benutzer freigegebene Ordner verwalten, Dateien kopieren, APK-Dateien an Android zum Installieren übergeben und lokale Shell-Befehle starten. Die beiden Ordner werden im aktuellen Quellcode nebeneinander angezeigt. Die Darstellung auf einem echten Moto G14 muss noch getestet werden.

## Was funktioniert im Quellcode?

- Ordner über die Android-Dateiauswahl öffnen.
- Freigegebene Ordner beim nächsten Start wiederfinden, solange Android den Zugriff erlaubt.
- Eine Datei auswählen und in den anderen Ordner kopieren. Vorhandene Dateien werden nicht überschrieben.
- APK-Datei öffnen. Android muss die Installation erlauben und bestätigen.
- Shell-Befehle ausführen. Für **Root (su)** muss das Gerät bereits Root-Rechte besitzen.
- **Wissen & Sicherheit** erklärt technische Begriffe und Risiken.
- Die **ChatGPT Bridge** nutzt die Android-Teilen-Funktion und fragt vor jeder Antwort um Erlaubnis.

## Was ist noch nicht eingebaut?

Kein automatisches Rooten, kein automatisches Flashen des Moto G14, keine automatische Einrichtung von Termux oder Shizuku und kein direkter ChatGPT-Fernsteuerungs-Port. Es gibt Schaltflächen zum Öffnen von Termux und Shizuku oder ihrer offiziellen Webseiten.

## APK herunterladen

1. Öffne [GitHub Actions](https://github.com/aurabin/Root-Commander/actions).
2. Wähle einen erfolgreichen Lauf von **Android AutoCompiler**.
3. Lade unten das Paket **RootCommander-debug-apk** herunter.
4. Entpacke die ZIP-Datei. Darin liegt die APK.
5. Öffne die APK auf Android. Android fragt gegebenenfalls nach einer Installationsfreigabe.

**Geprüft:** Die GitHub-Builds vom 14. November 2026 für die Commits `8779b8e` und `4182203` waren erfolgreich. Neuere Änderungen müssen erneut gebaut werden. **Nicht geprüft:** die Bedienung auf einem echten Moto G14.

## Fehler und Lösungen

Lies [die vollständige Prüfung in einfacher Sprache](docs/PRUEFUNG_FEHLER_LOESUNGEN_EINFACHE_SPRACHE.md) und [die Hinweise zur GitHub-Sicherheit](docs/GITHUB_INSTALLATION_SECURITY.md).

Die App ersetzt keine Android-Sicherheitsabfragen. Bitte sichere wichtige Dateien, bevor du Systemänderungen vornimmst.
