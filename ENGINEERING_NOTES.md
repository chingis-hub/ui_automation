# Engineering Notes

Key observations about the test environment and pipeline behavior.

---

## UAC Elevation (Windows)

Tests trigger a UAC prompt via `elevator.exe` (a JetBrains utility for requesting admin rights).

**Pipeline:** IDE → `elevator.exe` → UAC prompt → elevated execution

**Problems:** breaks automation, requires manual confirmation each time.

**Solution:** self-elevating script — checks for admin rights and relaunches itself if needed.
- Single entry point, one UAC prompt per run.
- UAC itself cannot be bypassed (Windows security model).

---

## Multiple IDE Launches

Multiple IDEA instances start during a single test run — this is expected, not a bug.

Each launch is a distinct pipeline stage (example for `splitMode = false`):

| Stage | Action |
|---|---|
| 1 | Bootstrap |
| 2 | Project import |
| 3 | Apply configuration |
| 4 | Final test execution |

---

## Parameterized Test → Double Execution

The test runs with `splitMode = false` and `splitMode = true`, so the full pipeline executes **twice**. Split mode may add extra stages, further increasing launch count and total runtime.

---

## LICENSE_KEY Environment Variable

`setLicense(System.getenv("LICENSE_KEY"))` — if unset, the value is `null` and the failure may surface deep in the pipeline with a misleading error.

Set it before running:
```powershell
$env:LICENSE_KEY = "your-key-here"
```

---

## LATEST-EAP-SNAPSHOT Dependencies

All IDE Starter/Driver deps resolve to the latest published snapshot at sync time. A new snapshot can silently break the build with no local changes. Pin to a specific version once a known-good one is found.

---

## Pinned Project Commit

The test project (`project-mirai/mirai-hello-world`) uses a fixed `commitHash`. This prevents upstream repo changes from introducing flakiness.

---

