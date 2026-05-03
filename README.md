# UI Automation

Kotlin UI tests for IntelliJ IDEA using the JetBrains IDE Starter/Driver framework.

## Run

```powershell
.\gradlew.bat test
```

Downloads IntelliJ IDEA on first run. Reports in `build/reports/tests/`.

## What it tests

Opens a live IDE instance and verifies the "Create changelists automatically" checkbox in Settings → Version Control → Changelists can be enabled.
