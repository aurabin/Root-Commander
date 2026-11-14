package org.example.rootcommander

/** Human-readable GitHub installation and token diagnostics.
 * Never accept or print raw access tokens.
 */
object GitHubAuthMessages {
    fun describe(httpStatus: Int?, errorCode: String? = null): String = when {
        httpStatus == 401 -> "GitHub-Anmeldung ungültig oder abgelaufen. Verbinde die GitHub-App erneut. Gib niemals einen Token im Chat oder in der App ein."
        httpStatus == 403 -> "GitHub verweigert den Zugriff. Prüfe die App-Berechtigungen, Repository-Auswahl und mögliche API-Limits in GitHub."
        httpStatus == 404 -> "Repository oder Datei nicht gefunden. Prüfe den Repository-Namen und ob die GitHub-App darauf zugreifen darf."
        httpStatus == 429 -> "GitHub begrenzt vorübergehend die Anzahl der Anfragen. Warte und versuche es später erneut."
        httpStatus != null && httpStatus in 500..599 -> "GitHub ist vorübergehend nicht erreichbar. Versuche es später erneut."
        errorCode == "network" -> "Keine Netzwerkverbindung zu GitHub. Prüfe Internetzugang und DNS."
        errorCode == "timeout" -> "GitHub hat nicht rechtzeitig geantwortet. Bitte erneut versuchen."
        httpStatus != null && httpStatus in 200..299 -> "GitHub-Verbindung erfolgreich."
        else -> "GitHub-Status unbekannt. Prüfe die Verbindung und die GitHub-App-Berechtigungen."
    }

    fun safeSummary(connected: Boolean, allRepositories: Boolean): String =
        if (!connected) "GitHub-Verbindung nicht bestätigt."
        else if (allRepositories)
            "GitHub verbunden. Die App ist für alle Repositories freigegeben. Prüfe, ob eine Einschränkung auf RootCommander sinnvoll ist."
        else "GitHub verbunden. Zugriff ist auf ausgewählte Repositories begrenzt."
}
