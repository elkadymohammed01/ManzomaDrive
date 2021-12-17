import os
import sys
from django.conf.urls import url

if __name__ == "__main__":
    os.environ.setdefault("DJANGO_SETTINGS_MODULE", "SuperDrive.settings")
    try:
        from django.core.management import execute_from_command_line
    except ImportError:
        # The above import may fail for some other reason. Ensure that the
        # issue is really that Django is missing to avoid masking other
        # exceptions on Python 2.
        try:
            import django
        except ImportError:
            print("problem")
        raise
    execute_from_command_line(sys.argv)
