package org.example.rootcommander

/** Einfache Meldungen zu GitHub. Geheime Zugangsschlüssel niemals anzeigen. */
object GitHubAuthMessages {
    fun describe(httpStatus: Int?, errorCode: String? = null): String = when {
        httpStatus == 401 -> "Die Anmeldung bei GitHub ist nicht mehr gültig. Vielleicht ist der Zugang abgelaufen. Prüfe die Verbindung zur GitHub-App. Gib deinen geheimen Zugangsschlüssel niemals im Chat oder in der App ein."
        httpStatus == 403 -> "GitHub erlaubt diese Aktion nicht. Vielleicht fehlen Rechte. Vielleicht ist die Anzahl der erlaubten Anfragen erreicht. Prüfe die Rechte der GitHub-App und welche Projekte sie verwenden darf."
        httpStatus == 404 -> "GitHub findet dieses Projekt oder diese Datei nicht. Vielleicht ist der Name falsch. Vielleicht darf die GitHub-App nicht darauf zugreifen."
        httpStatus == 429 -> "Es wurden zu viele Anfragen an GitHub geschickt. Warte etwas und versuche es erneut."
        httpStatus != null && httpStatus in 500..599 -> "GitHub hat gerade ein Problem auf seinen Servern. Versuche es später erneut."
        errorCode == "network" -> "Dein Gerät kann GitHub nicht erreichen. Prüfe die Internetverbindung und die Namensauflösung (DNS)."
        errorCode == "timeout" -> "GitHub hat nicht rechtzeitig geantwortet. Versuche es erneut."
        httpStatus != null && httpStatus in 200..299 -> "Die Anfrage an GitHub war erfolgreich."
        else -> "Der Zustand der GitHub-Verbindung ist noch nicht bekannt. Prüfe die Verbindung und die Rechte der GitHub-App."
    }

    fun safeSummary(connected: Boolean, allRepositories: Boolean): String =
        if (!connected) "Die Verbindung zu GitHub konnte noch nicht bestätigt werden."
        else if (allRepositories)
            "GitHub ist verbunden. Die GitHub-App darf auf alle Projekte des Kontos zugreifen. Du kannst den Zugriff auf ausgewählte Projekte begrenzen. Andere Projekte können dann den Zugriff verlieren."
        else "GitHub ist verbunden. Die GitHub-App darf nur auf ausgewählte Projekte zugreifen."
}
