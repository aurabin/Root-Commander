# GitHub-App-Installation: verständliche Erklärung

## Aktueller Prüfstand (14. November 2026)

- Verbundenes Konto: `aurabin`
- Installation: `181589891`
- Repository: `aurabin/Root-Commander` (öffentlich)
- Repository-Rechte des verbundenen Kontos: Lesen, Schreiben und Administration
- Repository-Auswahl der GitHub-App: **alle Repositories**
- Tokeninhalt, Ablaufzeit und einzelne GitHub-App-Berechtigungsscopes sind über die verwendeten Funktionen **nicht einsehbar**. Die Repository-Rechte allein beweisen keine spezifischen Token-Scopes.

## Was bedeutet das?

Die Verbindung ist nutzbar. Da die App für alle Repositories installiert ist, ist der Zugriff weiter gefasst als für RootCommander allein erforderlich. Eine Einschränkung auf ausgewählte Repositories reduziert das Risiko. Dabei können andere Projekte, die diese App verwenden, ihren Zugriff verlieren.

## GitHub-Einstellungen

[Installation prüfen und Repository-Auswahl ändern](https://github.com/settings/installations/181589891)

Falls ein Token versehentlich veröffentlicht wurde: GitHub-App-Zugriff widerrufen oder neu autorisieren und betroffene Geheimnisse rotieren. Keine Tokens in GitHub-Dateien, App-Logs, Screenshots oder Chat-Nachrichten kopieren.

## Fehler verständlich übersetzen

- 401: Anmeldung abgelaufen oder ungültig
- 403: Berechtigung fehlt oder Anfrage begrenzt
- 404: Repository/Datei fehlt oder nicht zugänglich
- 429: Zu viele Anfragen
- 5xx: GitHub-Dienstfehler

Der Kotlin-Übersetzer liegt unter `GitHubAuthMessages.kt`. Er ist als wiederverwendbare Komponente hinzugefügt, **noch nicht mit einer echten GitHub-API-Anmeldung in der Android-App verbunden**.
