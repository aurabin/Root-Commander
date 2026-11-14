# RootCommander AutoFlash – three-host prototype

Supported host targets: Windows, Linux, and a second Android phone via USB-OTG/Termux **if** a compatible fastboot binary and USB access are available.

The target Moto G14 cannot flash itself while booted normally. A host must communicate with its fastboot bootloader.

## Checks before any flash

1. Exactly one fastboot device is connected.
2. The product string equals an explicit user-supplied expected product.
3. The local image SHA-256 matches an independently verified checksum.
4. The bootloader reports unlocked.
5. The user types an exact confirmation token.

By default the Python host script performs preflight only; `--execute` is required for flashing. No automatic bootloader unlocking, no silent flash, no root access through ChatGPT.

## Example

```bash
python scripts/autoflash.py --expected-product PRODUCT
python scripts/autoflash.py --expected-product PRODUCT --image patched_boot.img --partition boot --sha256 VERIFIED_HASH
# After verifying model, firmware, partition and backups:
python scripts/autoflash.py --expected-product PRODUCT --image patched_boot.img --partition boot --sha256 VERIFIED_HASH --execute
```

**Do not assume the Moto G14 uses the `boot` partition.** Confirm the correct partition and image against the exact firmware/build before any flash. A checksum alone does not prove compatibility. Unlocking a bootloader normally erases user data. This prototype has not been tested on hardware.
