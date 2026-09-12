# Changelog

All notable changes to PinDNS will be documented in this file.

## [0.6] - 2026-09-12

### Changed
- **Renamed to PinDNS** - the app, repository and documentation now use the name PinDNS (formerly PrivDNS Toggle). The package ID `com.privdnstoggle.app` is unchanged, so existing installs update in place and keep their `WRITE_SECURE_SETTINGS` grant. The Quick Settings tile is now labelled "PinDNS Toggle" and release files are named `PinDNS-v<version>.apk`.

### Documentation
- **Project website** - PinDNS now has a home at [pindns.xyz](https://pindns.xyz).
- **Pick a DNS Provider** - the README setup section now has an optional step pointing at NextDNS, labelled as a referral link. Any Private DNS provider still works; NextDNS is not required.
- **Corrections** - build output paths, the Gradle and JDK versions, the logcat filter tags and three places that claimed Save turns Private DNS on (it only tests the connection and stores the hostname) now match the code.

## [0.5] - 2026-09-09

### Added
- **Grant with Shizuku** - one-time in-app grant of `WRITE_SECURE_SETTINGS` through a Shizuku user service, for devices without access to a computer. The ADB command remains supported.
- **CI pipeline** - GitHub Actions run unit and Robolectric regression tests, Android Lint, a release build and OWASP Dependency-Check on every pull request.
- **README badges, About and donations** - Obtainium install badge, release/download/CI/stack badges, Shizuku and Samsung Galaxy device notes, an About section (single developer, v1.0 targeted for Q4 2026) and a PayPal donation link; `.github/FUNDING.yml` adds a GitHub Sponsor button.
- **Publish workflow** - merging a version bump to main builds, tests, signs and publishes the GitHub Release automatically.

### Changed
- **Release signing key** - releases are signed with a dedicated release key (certificate SHA-256 `5344da60b330aca1109d26bac272f437d72c9a1a456aa1c9b7ea9c587ce19289`) instead of a debug key. Upgrading from 0.4.1 or earlier: uninstall the old version first, then install and grant `WRITE_SECURE_SETTINGS` again.

### Fixed
- **Quick Settings tile on Android 9** - the tile service no longer calls `Tile.setSubtitle` (API 29) on API 28 devices, which crashed the tile; the subtitle is simply omitted there.

## [0.4.1] - 2026-02-12

### Changed
- **OLED black background** — Main screen and surfaces use true black (#000000) for OLED displays.
- **Save button** — Save now only persists the DNS hostname; enabling Private DNS is manual via the toggle or Quick Settings tile.

## [0.4] - 2026-02-11

### Changed / Fixed / Added
- Version bump to 0.4 for release preparation.

## [0.3.1] - 2026-02-09

### Changed

- **Version source** — Version is now read from `version.properties` (single source of truth for release automation).
- **Release workflow** — Tag-based GitHub Action: `beta/v*` publishes a prerelease, `v*` from main publishes a full release.

### Fixed

- **Compose BOM** — Build uses Compose BOM 2023.10.01 to avoid KeyframesSpec NoSuchMethodError on device (fixes crash after first frame on some devices).

## [0.3] - 2026-02-09

### Fixed

- **Release APK signing** — release builds are now signed (keystore or debug fallback), fixing "App not installed as package appears to be invalid" on devices such as Samsung Galaxy S21+.

## [0.2-beta] - 2026-02-09

First public beta release.

### Added

- **Quick Settings Tile** — toggle Private DNS on/off directly from the notification shade.
- **Large Toggle Switch** — prominent horizontal switch in the app with animated state transitions.
- **DNS Validation** — syntax validation for hostnames, IPv4, and IPv6 addresses, plus a TLS connection test (port 853, 10s timeout) before saving.
- **Dark Mode UI** — Material 3 dark theme by default, with dynamic color support on Android 12+.
- **In-App Setup Instructions** — collapsible card with the ADB permission grant command and copy-to-clipboard.
- **Custom App Icons** — launcher icons, adaptive icons, and Quick Settings tile icon generated from project branding.
- **Unit Tests** — hostname validation covered by JUnit 4 tests.

### Technical Details

- Min SDK: 28 (Android 9)
- Target SDK: 34
- Requires one-time ADB grant of `WRITE_SECURE_SETTINGS`
- No root, no Device Admin, no background services, no analytics
