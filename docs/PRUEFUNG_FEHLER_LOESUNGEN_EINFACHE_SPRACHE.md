# RootCommander: Prüfung, Fehler und Lösungen – einfache Sprache

Stand: 14. November 2026. Geprüft wurde der Code auf GitHub. Ein Test auf dem echten Moto G14 wurde **nicht** durchgeführt.

## Was wurde untersucht?
Die Haupt-App, die Datei-Funktionen, die Shell, die Teilen-Verbindung zu ChatGPT, das Android-Manifest, die GitHub-Anmeldung, die Hilfe-Texte und der automatische APK-Bau auf GitHub.

## Fehler und Verbesserungen

### 1. Der automatische APK-Bau ist fehlgeschlagen
**Gefunden:** Mehrere GitHub-Actions-Läufe sind fehlgeschlagen. Im Protokoll steht: `Failed to find package 'tools'`. Das passiert beim Schritt `android-actions/setup-android@v3`. Die App selbst wurde dabei noch gar nicht übersetzt.

**Lösung:** Diesen fehlerhaften Vorbereitungsschritt entfernt. Der GitHub-Rechner bringt bereits ein Android-SDK mit. Der neue Ablauf prüft und ergänzt die nötigen Android-Werkzeuge direkt.

**Zweiter Fehler im echten Test:** Danach meldete GitHub: `sdkmanager: command not found`. Das Programm war nicht über den normalen Suchpfad erreichbar. **Zweiter Workaround:** Der Build sucht jetzt den vollständigen Dateipfad des SDK-Managers und startet ihn direkt. **Noch offen:** Ein erfolgreicher neuer Lauf muss das bestätigen.

### 2. Ordner nach einem Neustart verschwinden
**Gefunden:** Die App merkte sich die gewählten Ordner nur, solange sie lief.

**Lösung:** Die App speichert jetzt die Adresse des gewählten Ordners. Beim Start versucht sie, den bereits erlaubten Zugriff wiederherzustellen. Ist die Freigabe nicht mehr gültig, fordert sie zur erneuten Auswahl auf.

**Ausweichlösung:** Den Ordner über die Android-Dateiauswahl erneut freigeben. Android 14 kann manche Systemordner grundsätzlich sperren.

### 3. Beim Kopieren kann die Auswahl wechseln
**Gefunden:** Die App benutzte für eine laufende Kopie den veränderlichen Namen der gerade gewählten Datei.

**Lösung:** Name, Quelle und Ziel werden jetzt vor dem Kopieren festgehalten. Wenn die Zieldatei schon vorhanden ist, wird sie nicht überschrieben. Bei einem Fehler versucht die App, eine unvollständige neue Datei zu löschen.

**Ausweichlösung:** Bei einem Fehler einen anderen freigegebenen Zielordner wählen und den freien Speicher prüfen.

### 4. Fehlende Ordner oder Datei
**Gefunden:** Manche Schaltflächen taten nichts, wenn noch keine Datei oder kein Ziel ausgewählt war.

**Lösung:** Die App erklärt jetzt, was zuerst ausgewählt werden muss.

### 5. Shell-Ausgabe und lange Befehle
**Gefunden:** Programme können lange laufen oder sehr viel Text ausgeben.

**Lösung:** Der Shell-Aufruf läuft nicht auf dem Bildschirm-Thread. Er hat eine Zeitgrenze von 15 Sekunden und eine begrenzte Textausgabe. Die App versucht, den Prozess nach Ablauf zu beenden.

**Grenze:** Ein beendeter Hauptprozess kann eigene Unterprozesse zurücklassen. Die Shell ist **keine** sichere Umgebung für unbekannte Befehle. Besonders bei `su` nur bekannte Befehle verwenden.

### 6. ChatGPT-Verbindung
**Gefunden:** Die Teilen-Verbindung akzeptiert nur vier bekannte Funktionen: `capabilities`, `device_info`, `diagnostics`, `list_files`. Jede Anfrage muss bestätigt werden.

**Lösung:** Bestätigung und Fehlertexte sind jetzt verständlicher auf Deutsch.

**Grenze:** Das ist keine direkte Fernsteuerung. Die Verbindung hat keinen offenen Netzwerk-Port und darf keine Root-Befehle ausführen.

### 7. GitHub-Anmeldung
**Gefunden:** Die App-Installation ist für alle Repositories des Kontos freigegeben. Der geheime Token selbst ist nicht einsehbar.

**Lösung:** Verständliche Fehlermeldungen und eine Sicherheitsanleitung sind im Projekt vorhanden.

**Ausweichlösung:** Die Rechte bei GitHub prüfen: https://github.com/settings/installations/181589891

### 8. APK installieren
**Möglicher Fehler:** Android erlaubt die Installation aus dieser App noch nicht.

**Ausweichlösung:** Die Freigabe für Installationen aus dieser Quelle in den Android-Einstellungen erteilen und die APK erneut auswählen. Android muss die Installation weiterhin bestätigen. Nur vertrauenswürdige APK-Dateien verwenden.

### 9. Root und AutoFlash
**Grenze:** Ein unverändertes Moto G14 mit Android 14 kann seinen gesperrten Bootloader nicht mit einer normalen App umgehen.

**Ausweichlösung:** Erst Daten sichern und die genaue Firmware prüfen. Für echte Fastboot-Vorgänge ist in der Regel ein Windows-/Linux-Rechner oder ein geeignetes zweites Android-Gerät über USB-OTG nötig. Niemals eine Partition oder ein Image raten.

## Was ist noch nicht fertig?
- Ein erfolgreicher GitHub-Build nach den Änderungen ist noch zu prüfen.
- Die App wurde nicht auf dem Moto G14 gestartet und getestet.
- Die zwei Dateibereiche werden bisher nicht gleichzeitig angezeigt; die App schaltet zwischen ihnen um.
- Die GitHub-Fehlermeldungen sind noch nicht mit einer echten Anmeldung in der Android-App verbunden.
- Der AutoFlash-Prototyp aus einem früheren ZIP ist nicht vollständig in dieses GitHub-Projekt integriert.
- Es gibt keine direkte ChatGPT-Fernsteuerung und keinen automatischen Root-Zugriff.

## So prüfst du den neuen APK-Bau
1. Öffne https://github.com/aurabin/Root-Commander/actions
2. Öffne den neuesten Lauf von **Android AutoCompiler**.
3. **Grünes Häkchen:** Der Bau war erfolgreich. Die APK steht dann normalerweise unter **Artifacts**.
4. **Rotes Kreuz:** Öffne den fehlerhaften Schritt. Dort steht, warum es nicht geklappt hat.
5. **Gelber Punkt:** Der Bau läuft noch.

Ein grüner Build beweist nur, dass die APK gebaut wurde. Er beweist **nicht**, dass alle Funktionen auf dem Handy richtig arbeiten.
