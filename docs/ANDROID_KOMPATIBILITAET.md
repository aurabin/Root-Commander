# Android-Kompatibilität – RootCommander 1.4

- **Android 7.0 und höher (API 24+)**: Quellcode-Konfiguration unterstützt diese Versionen; ein tatsächlicher Gerätetest steht noch aus.
- **Android 8.0 und höher (API 26+)**: Berechtigung zur APK-Installation aus dieser Quelle wird vor dem Installationsdialog abgefragt.
- **Android 7.x**: verwendet den älteren Installationsablauf; die Installation aus unbekannten Quellen muss gegebenenfalls in den Systemeinstellungen erlaubt werden.
- **Android 11+**: Android Storage Access Framework (SAF) kann den Zugriff auf bestimmte Verzeichnisse begrenzen. RootCommander umgeht diese Grenzen nicht.
- **Android 14 (Moto G14)**: Zielplattform, die App muss noch auf dem Gerät getestet werden.
- **Zukünftige Android-Versionen**: Kompatibilität ist nicht garantiert; neue Android-Sicherheits- und UI-Anforderungen müssen bei Veröffentlichung geprüft werden.
- **Root**: optional, nicht automatisch verfügbar. Eine App kann Android-Berechtigungen oder Bootloader-Sperren nicht aufheben.
- **Große Ordner**: Abfragen erfolgen im Hintergrund; derzeit werden höchstens 500 Einträge pro Ordner angezeigt. Für unbegrenzte Ordnernavigation wäre eine echte paginierte Liste nötig.
- **Debug APK**: Build-Erfolg ist kein vollständiger Hardware- oder Sicherheitstest.

## English

Android 7.0+ is the configured minimum. APK installation permission handling is version-aware. SAF restrictions, optional root access and future Android platform changes still apply. Physical-device verification is pending.
