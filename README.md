# UI Automation

Kotlin UI tests for IntelliJ IDEA using the JetBrains IDE Starter/Driver framework.

## Run

```powershell
.\gradlew.bat test
```

Downloads IntelliJ IDEA on first run. Reports in `build/reports/tests/`.

## Administrator privileges

The IDE requests elevated privileges via `elevator.exe` (JetBrains UAC helper) during startup. To avoid manual UAC prompts mid-test, run the suite as Administrator using the included wrapper script:

```powershell
.\run-tests-admin.ps1
```

If the current shell is not elevated, the script relaunches itself with a single UAC prompt, then runs the tests uninterrupted. See the script for a full explanation.

## What it tests

Opens a live IDE instance and verifies the "Create changelists automatically" checkbox in Settings → Version Control → Changelists can be enabled.
