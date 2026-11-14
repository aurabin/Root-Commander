# ChatGPT Control Bridge – protocol v0.1

This is a **proposed protocol**, not an active remote connection. ChatGPT cannot directly reach an Android app through this repository.

## Design

The Android app owns all permissions. A user-approved companion service may connect it to an authenticated HTTPS endpoint, or a locally paired client can use a short-lived session. Never expose an unauthenticated ADB, shell, HTTP, or root port to the internet.

### Command envelope

```json
{"version":1,"request_id":"uuid","action":"list_files","args":{"directory_id":"user-selected-directory"},"expires_at":"ISO-8601 timestamp"}
```

### Response

```json
{"version":1,"request_id":"uuid","status":"ok","result":{"entries":[]},"audit_id":"uuid"}
```

## Initial allowlist

- `device_info`: Android version and app version (no unique identifiers).
- `list_files`: list entries within an explicitly selected SAF directory.
- `read_text`: read text within a selected directory, with size limit.
- `copy_file`: copy between selected directories, requiring confirmation.
- `diagnostics`: report app diagnostics with secrets redacted.

## High-risk actions

Shell, `su`, APK installation, deletion, permission changes, network transmission, and system modifications require an on-device confirmation showing the exact operation. Never accept arbitrary commands as trusted merely because an AI generated them. No unattended root mode.

## Security requirements

Mutual authentication or authenticated HTTPS, short-lived tokens, per-session pairing, replay protection, strict action schemas, rate limiting, explicit directory scopes, a kill switch, visible audit log, and safe defaults. Do not store API keys in APKs or source control.

## Implementation status

**Protocol specification only.** No Android service, server, OpenAI API integration, ChatGPT plugin, or working pairing has been implemented or tested.
