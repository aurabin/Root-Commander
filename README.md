# RootCommander 1.3

Android file manager prototype with a dark GUI, two user-selected folders, copy operations, APK handoff, shell commands and optional `su` with confirmation. It does not automatically root a device, bypass Android install permissions or include embedded Bash/Shizuku yet.

## Automatic build

On each push to `main`, GitHub Actions runs `gradle :app:assembleDebug`. After a successful run, download the `RootCommander-debug-apk` artifact from the Actions tab. Android installation confirmation is still required.

## Verification

Source and workflow have been uploaded. Android compilation and on-device testing have **not** yet been confirmed. Do not assume a working APK until the Actions job succeeds.
