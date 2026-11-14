# RootCommander Android Bridge v0.1

Implemented: Android share-sheet receiver `BridgeActivity` for JSON text requests. It accepts only `device_info` and asks for explicit on-device consent before returning Android version, SDK and app version via Android share chooser.

Example request to share as text to RootCommander:

```json
{"version":1,"request_id":"test-001","action":"device_info","args":{}}
```

RootCommander opens a confirmation dialog. If approved, select ChatGPT (if offered by Android) or copy/share the resulting JSON back into the conversation.

This is **not** a live remote connection, API endpoint, MCP server, automatic ChatGPT tool, or unattended control. Other commands, including files, shell, APK and root, are not exposed through the bridge. The source was committed; a successful APK build and device test remain unverified.
