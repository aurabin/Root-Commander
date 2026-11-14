# GitHub und RootCommander – alle wichtigen Informationen in einfacher Sprache

Stand: 14. November 2026.

## 1. Was wurde geprüft?

RootCommander liegt auf GitHub. GitHub ist ein Dienst, auf dem man Programm-Code speichern und gemeinsam bearbeiten kann.

- **GitHub-Konto:** `aurabin`
- **Nummer der GitHub-App-Installation:** `181589891`. Diese Nummer ist **kein geheimes Passwort**.
- **Projekt:** `aurabin/Root-Commander`
- **Sichtbarkeit:** öffentlich. Jeder kann den veröffentlichten Programm-Code ansehen.
- **Zugriffsrechte des verbundenen Kontos auf dieses Projekt:** lesen, ändern und verwalten.
- **Auswahl der Projekte für die GitHub-App:** alle Projekte des Kontos.

Die Verbindung hat bei der Prüfung funktioniert. Das heißt **nicht**, dass wir den geheimen Zugangsschlüssel kennen oder geprüft haben.

## 2. Was ist ein Installationstoken?

Ein Token ist ein geheimer, zeitlich begrenzter Zugangsschlüssel. GitHub-Apps verwenden solche Schlüssel, um erlaubte Aufgaben auszuführen.

**Wichtig:** Die verfügbaren Werkzeuge zeigen uns den Token selbst, seine genaue Laufzeit und seine einzelnen Berechtigungen **nicht**. Auch die Rechte auf ein Projekt beweisen nicht, welche Einzelrechte der Token hat.

Du musst den Token nicht in ChatGPT einfügen. Bitte veröffentliche ihn auch nicht in Dateien, Bildern oder Fehlerberichten.

## 3. Warum sind die Rechte wichtig?

Die GitHub-App ist derzeit für **alle** Projekte deines Kontos eingerichtet. Für RootCommander allein ist das mehr Zugriff als nötig.

Du kannst den Zugriff auf ausgewählte Projekte begrenzen. Dann kann die App nur noch diese Projekte sehen. Andere Projekte, die dieselbe GitHub-App brauchen, funktionieren danach vielleicht nicht mehr.

**Einstellungen öffnen:** https://github.com/settings/installations/181589891

Dort kannst du die Installation ansehen und – soweit GitHub es für dein Konto erlaubt – die Projektauswahl ändern.

## 4. Was bedeuten die Fehlermeldungen?

| Fehler | Bedeutung in einfacher Sprache | Was du tun kannst |
| --- | --- | --- |
| 200–299 | GitHub hat die Anfrage angenommen. | Keine Änderung nötig. |
| 401 | Die Anmeldung ist nicht gültig oder der Zugang ist abgelaufen. | GitHub-App-Verbindung prüfen und bei Bedarf erneut verbinden. |
| 403 | GitHub erlaubt die Anfrage nicht. Das kann an fehlenden Rechten oder einer Begrenzung liegen. | Rechte, Projektauswahl und Anfragebegrenzung prüfen. |
| 404 | GitHub findet das Projekt oder die Datei nicht – oder du darfst sie nicht sehen. | Namen und Zugriffsrechte prüfen. |
| 429 | Es wurden zu viele Anfragen gestellt. | Warten und später erneut versuchen. |
| 500–599 | GitHub hat ein Problem auf seinen Servern. | Später erneut versuchen. |
| Netzwerkfehler | Das Gerät erreicht GitHub nicht. | Internetverbindung und Namensauflösung (DNS) prüfen. |
| Zeitüberschreitung | Die Antwort hat zu lange gedauert. | Erneut versuchen. |

## 5. Was tun, wenn ein Token bekannt geworden ist?

Wenn du einen geheimen Token aus Versehen veröffentlicht hast, solltest du ihn nicht weiterverwenden. Widerrufe oder erneuere den betroffenen Zugang in den GitHub-Einstellungen. Ersetze auch andere Geheimnisse, die dadurch möglicherweise sichtbar wurden. Entferne den Token aus öffentlich erreichbaren Dateien und prüfe, ob er noch in früheren Versionen vorhanden ist.

## 6. Was ist bereits in RootCommander eingebaut?

Die Datei `GitHubAuthMessages.kt` enthält verständliche deutsche Fehlermeldungen. Sie zeigt keine geheimen Zugangsschlüssel an.

**Noch nicht fertig:** Diese Meldungen sind bisher nicht mit einer echten GitHub-Anmeldung innerhalb der Android-App verbunden. Die Android-App wurde nicht nachweislich auf deinem Moto G14 getestet.

## 7. Was bedeuten die anderen technischen Begriffe?

- **APK:** Installationsdatei für Android.
- **AutoCompiler:** Ein Programm, das aus dem Quellcode eine APK erstellt.
- **GitHub Actions:** GitHub führt automatisch Arbeitsschritte aus, zum Beispiel einen Android-Build.
- **Root:** Besonders weitreichende Rechte auf einem Android-Gerät. Auf einem unveränderten Android 14 sind sie normalerweise nicht vorhanden.
- **Bootloader:** Startprogramm des Handys. Ein gesperrter Bootloader verhindert viele Änderungen am System.
- **Fastboot:** Werkzeug, das mit dem Handy im Bootloader-Modus kommuniziert.
- **Flashen:** Schreiben von Systemdaten auf bestimmte Speicherbereiche. Fehler können verhindern, dass das Handy startet.
- **SHA-256:** Eine Prüfsumme. Sie hilft festzustellen, ob eine Datei unverändert ist. Sie beweist nicht, dass die Datei zum Handy passt.
- **Shizuku:** Hilfsprogramm für bestimmte zusätzliche Android-Funktionen. Es ist kein Ersatz für Root.
- **Termux:** Eine Terminal-Umgebung auf Android.
- **ChatGPT Bridge:** Geplante Verbindung zwischen RootCommander und ChatGPT. Der bisherige Android-Code verwendet die Teilen-Funktion und benötigt deine Zustimmung. Eine direkte Fernsteuerung ist noch nicht fertig.

## 8. Was kann RootCommander bisher nicht?

Die App kann auf einem unveränderten Moto G14 nicht selbstständig Root-Rechte erzeugen, den gesperrten Bootloader umgehen oder das Handy während des normalen Betriebs vollständig neu flashen. Ein tatsächlicher Flash-Vorgang braucht ein passendes Image, geprüfte Geräteinformationen, meist einen externen Host und eine bewusste Freigabe.

Diese Grenzen sind wichtig, damit niemand versehentlich das Handy beschädigt oder private Daten freigibt.
