# Backlog

Known work that is understood but not yet scheduled. Each item records what is wrong, why it
matters, and where the relevant code lives, so it can be picked up without re-investigating.

## Build and release

### Pin the Gradle distribution by checksum

`gradle/wrapper/gradle-wrapper.properties` pins Gradle 9.7.1 by URL but sets no
`distributionSha256Sum`, so the wrapper accepts whatever that URL serves. Adding the checksum closes
a supply-chain gap and makes the build reproducible against a known distribution.

### Upload the R8 mapping file with each release

Release builds set `isMinifyEnabled = true` (`app/build.gradle.kts`) and produce
`builds/app/outputs/mapping/release/mapping.txt`, but `.github/workflows/publish.yml` never uploads
it. Without that file, stack traces reported from the wild cannot be deobfuscated, and the mapping
for an already-published release cannot be reconstructed after the fact. Attach it to the GitHub
Release, or upload it as a workflow artifact with a long retention.

### Upgrade Robolectric to 4.17

`app/build.gradle.kts` pins `org.robolectric:robolectric:4.16.1`; 4.17 is available. Android Lint
reports this as `NewerVersionAvailable`. It is deliberately not recorded in `app/lint-baseline.xml`,
so the warning stays visible until the upgrade is done. Robolectric drives
`DnsManagerSettingsTest.kt`, so re-run `:app:testDebugUnitTest` after bumping it.

## App behaviour

### Permission badge does not refresh while the app is in the foreground

Running the `adb shell pm grant` command with the app open changes nothing on screen. The permission
flips to granted at the OS level immediately, but the app keeps showing the red **Required** badge.
A user following the on-screen instructions with the app in front of them would reasonably conclude
the command failed. Backgrounding and reopening the app clears it; no device restart is needed, and
the command does not need re-running.

**Root cause:** `DnsSettingsViewModel.refresh()` is the only thing that updates `_hasPermission`, and
it is called from `init`, from user-driven actions, and from `MainActivity.onResume()`. Nothing
observes the permission while the activity is foregrounded, so a grant made from outside the app
stays invisible until the next `onResume`.

The badge is trustworthy once it does refresh: `DnsManager.hasPermission()` writes the current mode
back onto itself rather than inspecting the permission list, so a cleared badge proves the app can
actually write the setting.

**Two ways to fix it,** either of which is small:

- Register a `ContentObserver` on the Private DNS settings keys, or re-check on a short timer while
  the setup card is visible.
- Document the reopen step. Neither the README Setup section nor `docs/troubleshooting.md` currently
  mentions it, and it reads as a failed grant to anyone following along with the app open.
