# Release workflow

Releases are published by the `publish` GitHub Actions workflow (`.github/workflows/publish.yml`) when a version bump lands on `main`. Nothing is tagged or uploaded by hand.

## Cutting a release

1. Bump `VERSION_NAME` and `VERSION_CODE` in `version.properties`.
2. Add a `## [x.y] - YYYY-MM-DD` section for that version to `CHANGELOG.md`. Its content becomes the release notes verbatim.
3. Open a PR, let CI pass, merge to `main`.

On the push to `main` the workflow runs two jobs:

- **gate** reads `VERSION_NAME`. If `v<version>` already exists as a GitHub Release it stops, so docs and refactor merges are no-ops and re-runs are safe. Otherwise it requires the matching CHANGELOG section and continues.
- **apk** runs the unit tests, builds the unsigned release APK, signs it with the release key from the repository secrets, checks the signing certificate against the pinned fingerprint, then creates the `v<version>` tag at the merge commit and the GitHub Release with `PinDNS-v<version>.apk` attached and the CHANGELOG section as its body.

The `beta/v*` prerelease convention this document used to describe is retired; every published version is a full release.

## Signing key

- The release key is `.signing/privdnstoggle.keystore` (alias `privdnstoggle`, RSA 4096, valid until 2056) with its password in `.signing/keystore.pass`. The directory is gitignored. **It is the app's permanent identity: back it up outside this machine.** Losing it forces another signature change, which makes every user uninstall and reinstall.
- Certificate SHA-256: `5344da60b330aca1109d26bac272f437d72c9a1a456aa1c9b7ea9c587ce19289`. It is pinned in `publish.yml` (`EXPECTED_CERT_SHA256`) and published in the README; the workflow refuses to publish an APK signed with any other key.
- Releases up to 0.4.1 were signed with an Android debug key (SHA-256 `30bec4cfc3d2e5545f2ab17875e3f9b3033246fba88705d8b624be9bf4cc8c49`). 0.5 is the first release with the release key, so upgrading from 0.4.1 or earlier requires an uninstall first.

## Repository secrets

| Secret | Content |
|---|---|
| `ANDROID_KEYSTORE_B64` | `base64 -w0 .signing/privdnstoggle.keystore` |
| `ANDROID_KEYSTORE_PASS` | contents of `.signing/keystore.pass` |

Set them with the GitHub CLI from the repo root:

```
base64 -w0 .signing/privdnstoggle.keystore | gh secret set ANDROID_KEYSTORE_B64
gh secret set ANDROID_KEYSTORE_PASS < .signing/keystore.pass
```

If a merge to `main` runs the workflow before the secrets exist, the `apk` job fails at the Sign step and nothing is tagged or published. Add the secrets and re-run the failed job, or start `publish` by hand with **Run workflow**.

## Local builds

`./gradlew :app:assembleRelease` produces an **unsigned** APK at `builds/app/outputs/apk/release/app-release-unsigned.apk` unless a gitignored `keystore.properties` exists in the repo root:

```
storeFile=../.signing/privdnstoggle.keystore
storePassword=<contents of .signing/keystore.pass>
keyAlias=privdnstoggle
keyPassword=<contents of .signing/keystore.pass>
```

Debug builds (`assembleDebug`, or **Run** in Android Studio) are signed with the local debug key as usual and are the normal way to test on a device.
