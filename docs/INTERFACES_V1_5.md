# RootCommander 1.5 – Non-root interface inventory

## Implemented in Android source

- Android IPC input: `ACTION_SEND`, MIME `text/plain`, JSON request envelope v1.
- Android IPC output: `ACTION_SEND` share chooser, JSON response envelope v1.
- `capabilities`: available commands.
- `device_info`: Android version and SDK.
- `diagnostics`: bridge transport and no-root status.
- `list_files`: user explicitly selects a folder via SAF; names and directory flags returned, capped at 200 entries.
- Every supported request requires an on-device approval dialog.

Example request:

```json
{"version":1,"request_id":"demo-001","action":"capabilities","args":{}}
```

## Not implemented

- TCP, HTTP, WebSocket, MCP or remote ChatGPT network port.
- Persistent authenticated sessions or a relay server.
- Autonomous background device control.
- Private root access, `su`, ADB, system settings changes or unrestricted shell through this bridge.
- APK build/device testing. Inspect GitHub Actions for results.

## Security model

No inbound network port is opened. Android SAF limits filesystem access to the directory selected for each request. Never send credentials or secrets through the share interface. The Android app must be installed and the user must explicitly approve each request.

## Roadmap

A true ChatGPT integration requires a separately deployed, authenticated MCP service/relay plus an Android client that pairs with it. Those components are not supplied by the present share-intent implementation. They should use TLS, short-lived tokens, action allowlists, audit logs and revocation controls.
