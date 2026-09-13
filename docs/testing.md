# Testing

This document describes how testing is set up for PinDNS and how to run the tests.

## What is tested

### Unit tests (JVM)

Unit tests run on your machine's JVM. No device or emulator is required. They are fast and are used to guard against regressions in pure logic and, via Robolectric, in the code that reads and writes Android settings.

| What | Where | Purpose |
|------|--------|---------|
| Hostname/IP validation | `DnsManager.validateHostnameSyntax()` | Ensures valid hostnames and IPs are accepted and invalid input is rejected with the right error messages. |
| Debug logging system | `DebugLogger` | Ensures circular buffer, log retrieval, and logging methods work correctly. |
| Private DNS settings and saved hostname | `DnsManager` (`enableDns`, `disableDns`, `toggle`, `saveHostname`, `getSavedHostname`, `hasPermission`) | Ensures the toggle state machine, the settings keys, the saved-hostname default and trimming, and the safe fallbacks for unset settings do not regress. Runs under Robolectric. |

**Test classes:**
- `app/src/test/kotlin/com/privdnstoggle/app/DnsManagerTest.kt`
- `app/src/test/kotlin/com/privdnstoggle/app/DebugLoggerTest.kt`
- `app/src/test/kotlin/com/privdnstoggle/app/DnsManagerSettingsTest.kt`

**Cases covered:**

- **Valid hostnames** — e.g. `dns.adguard.com`, `dns.nextdns.io`, `one.one.one.one` → accepted (no error).
- **Valid IPv4** — e.g. `1.1.1.1`, `192.168.1.1` → accepted.
- **Valid IPv6** — e.g. `::1`, `2001:4860:4860::8888` → accepted.
- **Empty or blank input** → error: "Hostname cannot be empty".
- **Scheme in input** (e.g. `https://dns.example.com`) → error about not including a scheme.
- **Path or slash** (e.g. `dns.example.com/path`) → error about paths/slashes.
- **Port in input** (e.g. `dns.example.com:853`) → error about not including a port.
- **Invalid format** (e.g. `not valid..hostname`, `-leading.com`) → error: "Invalid hostname or IP address format".

**Test class:** `app/src/test/kotlin/com/privdnstoggle/app/DebugLoggerTest.kt`

**Cases covered:**
- **Circular buffer** — maintains maximum 100 entries, removes oldest when limit exceeded
- **Log retrieval** — `getRecentLogEntries()` returns last N entries (default 50), handles edge cases (empty buffer, count > total, zero count)
- **Log formatting** — `getAllLogs()` returns formatted string with newlines, `getLogEntries()` returns list copy
- **Clear functionality** — `clear()` removes all entries
- **Logging methods** — `d()` and `e()` add entries with timestamps, optional throwable includes stack traces

**Test class:** `app/src/test/kotlin/com/privdnstoggle/app/DnsManagerSettingsTest.kt` (Robolectric)

**Cases covered:**
- **Unset settings** - `getCurrentMode()` returns `off` and `getCurrentHostname()` returns an empty string when the system values are absent (never throws).
- **Enable/disable** - `enableDns()` writes `private_dns_mode=hostname` and `private_dns_specifier`; `disableDns()` writes `off` and leaves the specifier in place; `opportunistic` mode reads as inactive.
- **Toggle** - off to on with the saved hostname (or the default `dns.adguard.com`), on to off, and a blank saved hostname returns false without changing settings.
- **Saved hostname** - default is `dns.adguard.com`; `saveHostname()` trims whitespace.
- **Permission probe** - `hasPermission()` returns true when writes succeed and leaves the current mode unchanged.

### Not covered by automated tests

- **Connection test** (`DnsManager.testConnection`) — Depends on the network and real TLS; not unit-tested. Manually verify by saving a hostname in the app and checking that success/failure and errors behave as expected.
- **Permission-denied paths and real devices** - Robolectric never denies `WRITE_SECURE_SETTINGS`, so the `SecurityException` branches of `enableDns()`, `disableDns()` and `hasPermission()` are not automated. Real-device behavior (for example Samsung resetting Private DNS) is verified by hand after granting the permission (see [quick-start.md](../quick-start.md)).
- **Grant with Shizuku** (`ShizukuHelper`, `ShizukuUserService`) - Depends on the Shizuku app and binder IPC; not automated. Verify on a device with Shizuku running: tap the button in Setup Instructions, allow the request, and confirm the "Required" badge disappears.
- **UI (Compose)** — No UI tests. Verify the main screen and Quick Settings tile by hand.

## How to run unit tests

### From Android Studio

1. Open the project in Android Studio.
2. In the **Project** view, go to `app/src/test/kotlin/com/privdnstoggle/app/`.
3. Right-click **DnsManagerTest.kt** or **DebugLoggerTest.kt**.
4. Choose **Run 'DnsManagerTest'** or **Run 'DebugLoggerTest'**.

You can also open either test file, then click the run icon in the gutter next to the class or a single test method to run all tests or that method only. To run all unit tests, right-click the `app/src/test` directory and choose **Run Tests**.

### If Android Studio doesn't show "Run" for the test

1. **Sync Gradle** — **File → Sync Project with Gradle Files**. Wait for sync to finish.
2. **Mark test folder as test source** — In the **Project** view, right-click `app/src/test` → **Mark Directory as** → **Test Sources Root**. The `test` folder should turn green.
3. **Rebuild** — **Build → Rebuild Project**.
4. **Invalidate caches** — **File → Invalidate Caches…** → **Invalidate and Restart**. After restart, sync Gradle again and open `DnsManagerTest.kt`; the run icon should appear in the gutter next to the class name and each `@Test` method.
5. **Run via Gradle** — If the run icon still doesn't appear, run tests from the command line (see below) or from **View → Tool Windows → Gradle** → expand **PinDNS → app → Tasks → verification** → double-click **testDebugUnitTest**.

The test class uses `@RunWith(JUnit4::class)` so the IDE treats it as a JUnit 4 test. If you see lint or "invalid" on the test file, confirm that `testImplementation` for `junit:junit` and `kotlin-test-junit` are in `app/build.gradle.kts` and that Gradle sync completed without errors.

### From the command line

From the project root (where `build.gradle.kts` or the Gradle wrapper lives):

```bash
./gradlew :app:testDebugUnitTest
```

On Windows, if you use the Gradle wrapper:

```bash
gradlew.bat :app:testDebugUnitTest
```

If Gradle is installed globally:

```bash
gradle :app:testDebugUnitTest
```

Successful runs finish with `BUILD SUCCESSFUL` and test results (e.g. under `builds/app/reports/tests/testDebugUnitTest/`).

## Dependencies

Unit tests use:

- **JUnit 4** (`junit:junit:4.13.2`) — test runner and `@Test`.
- **Kotlin test** (`org.jetbrains.kotlin:kotlin-test-junit`) — Kotlin/JUnit integration.
- **Robolectric** (`org.robolectric:robolectric:4.16.1`) - runs Android framework code (`Settings.Global`, `SharedPreferences`) on the JVM for `DnsManagerSettingsTest`.

These are declared in `app/build.gradle.kts` under `testImplementation`. No extra setup is required after syncing the project. The first Robolectric run downloads an Android framework jar (about 100 MB) from Maven Central; later runs use the local cache.

## Adding or changing tests

- New tests for **hostname validation** → add or edit `@Test` methods in `DnsManagerTest.kt`. Keep assertions in line with the behavior described in [DnsManager.kt](../app/src/main/kotlin/com/privdnstoggle/app/DnsManager.kt) (e.g. exact error strings).
- New tests for **debug logging** → add or edit `@Test` methods in `DebugLoggerTest.kt`. Test new methods or edge cases for existing functionality.
- New tests for **settings or saved-hostname behavior** - add `@Test` methods in `DnsManagerSettingsTest.kt`. It runs under `RobolectricTestRunner`; use `RuntimeEnvironment.getApplication()` for a `Context`.
- To add **instrumented tests** (device/emulator) later, add `androidTestImplementation` dependencies and put tests in `app/src/androidTest/`. See the Android docs on [instrumented tests](https://developer.android.com/training/testing/instrumented-tests) for setup.

## Continuous integration

Two GitHub Actions workflows in `.github/workflows/` run on every pull request and on pushes to `main`:

- **CI** (`ci.yml`) - runs `:app:testDebugUnitTest`, `:app:lintDebug` and `:app:assembleRelease` in one job. Test and lint reports and the (unsigned) release APK are uploaded as run artifacts; lint findings are also uploaded as SARIF to Security > Code scanning. Pre-existing lint findings are recorded in `app/lint-baseline.xml`, so only new findings fail the build; regenerate it with `./gradlew :app:updateLintBaselineDebug` after deliberately fixing or accepting a finding.
- **Dependency Check** (`dependency-check.yml`) - runs OWASP Dependency-Check (`:app:dependencyCheckAnalyze`) against the release runtime classpath, also weekly on a schedule. The build fails on any dependency with a CVSS score of 7.0 or higher. Findings that do not apply to the shipped APK are suppressed in `app/dependency-check-suppressions.xml`, each with a written justification; add to that file rather than raising the threshold. The HTML report is uploaded as an artifact and the SARIF report to Code scanning. The NVD database is cached between runs under `~/.gradle/dependency-check-data`.

Setup: request a free NVD API key at https://nvd.nist.gov/developers/request-an-api-key and add it as the repository Actions secret `NVD_API_KEY`. Without it the workflow still runs, but the NVD API rate-limits keyless clients and the first database download is slow.

Run the same checks locally:

```bash
./gradlew --continue :app:testDebugUnitTest :app:lintDebug :app:assembleRelease
NVD_API_KEY=your-key ./gradlew :app:dependencyCheckAnalyze
```

Reports land under `builds/app/reports/` (this project relocates Gradle output to `builds/`).

## See also

- [README.md](../README.md) — project overview and setup.
- [quick-start.md](../quick-start.md) — build, install, and one-time ADB permission.
