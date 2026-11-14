import hashlib
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

from scripts.autoflash import check


class AutoFlashSafetyTests(unittest.TestCase):
    def test_missing_file_stops(self):
        self.assertEqual(check(Path("/does-not-exist.img"), "0" * 64, "device"), 2)

    def test_wrong_hash_stops_before_fastboot(self):
        with tempfile.TemporaryDirectory() as directory:
            image = Path(directory) / "image.img"
            image.write_bytes(b"test")
            with patch("scripts.autoflash.shutil.which") as which:
                self.assertEqual(check(image, "0" * 64, "device"), 3)
                which.assert_not_called()

    def test_missing_fastboot_stops(self):
        with tempfile.TemporaryDirectory() as directory:
            image = Path(directory) / "image.img"
            image.write_bytes(b"test")
            digest = hashlib.sha256(b"test").hexdigest()
            with patch("scripts.autoflash.shutil.which", return_value=None):
                self.assertEqual(check(image, digest, "device"), 4)


if __name__ == "__main__":
    unittest.main()
