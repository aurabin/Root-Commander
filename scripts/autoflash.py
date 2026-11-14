#!/usr/bin/env python3
"""RootCommander: sichere Fastboot-Vorprüfung. Dieses Programm flasht NICHT."""
import argparse
import hashlib
import shutil
import subprocess
import sys
from pathlib import Path


def check(image: Path, expected_sha256: str, expected_product: str) -> int:
    if not image.is_file():
        print("Fehler: Die angegebene Datei ist nicht vorhanden.")
        return 2
    if len(expected_sha256) != 64 or any(c not in "0123456789abcdef" for c in expected_sha256.lower()):
        print("Fehler: Bitte die vollständige SHA-256-Prüfsumme angeben.")
        return 2
    digest = hashlib.sha256()
    with image.open("rb") as source:
        for block in iter(lambda: source.read(1024 * 1024), b""):
            digest.update(block)
    if digest.hexdigest() != expected_sha256.lower():
        print("STOPP: Die Prüfsumme stimmt nicht. Datei nicht verwenden.")
        return 3
    if not shutil.which("fastboot"):
        print("STOPP: Fastboot ist nicht installiert oder nicht im Suchpfad.")
        return 4
    try:
        result = subprocess.run(["fastboot", "devices"], capture_output=True, text=True, timeout=12, check=True)
        devices = [line.split()[0] for line in result.stdout.splitlines() if line.strip().endswith("fastboot")]
        if len(devices) != 1:
            print("STOPP: Es muss genau ein Gerät im Fastboot-Modus verbunden sein.")
            return 5
        result = subprocess.run(["fastboot", "-s", devices[0], "getvar", "product"],
                                capture_output=True, text=True, timeout=12)
        product = result.stdout + result.stderr
        if result.returncode != 0 or expected_product not in product:
            print("STOPP: Das Gerät meldet nicht die erwartete Produktkennung.")
            return 6
    except (subprocess.TimeoutExpired, subprocess.CalledProcessError, OSError) as error:
        print(f"STOPP: Fastboot-Abfrage fehlgeschlagen: {error}")
        return 7
    print("Prüfsumme und Produktkennung passen zu den eingegebenen Erwartungen.")
    print("ACHTUNG: Das beweist NICHT, dass Image, Firmware und Partition zusammenpassen.")
    print("Es wurde NICHT geflasht. Für Systemänderungen sind weitere manuelle Prüfungen nötig.")
    return 0


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Datei und angeschlossenes Fastboot-Gerät prüfen. Kein Flashen.")
    parser.add_argument("--image", required=True, type=Path, help="Pfad zur Image-Datei")
    parser.add_argument("--sha256", required=True, help="Erwartete SHA-256-Prüfsumme")
    parser.add_argument("--product", required=True, help="Erwartete Produktkennung aus der offiziellen Firmware")
    args = parser.parse_args()
    sys.exit(check(args.image, args.sha256, args.product))
