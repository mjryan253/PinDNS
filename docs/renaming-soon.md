# Renaming PrivDNS Toggle to PinDNS

Record of the naming work done on 2026-09-11. The decision is made; the rename has **not** been applied. This document holds everything a later pass needs so the research does not have to be repeated.

## Status

- Future name: **PinDNS**
- Applied: no. The app, repo, package and docs still use "PrivDNS Toggle".
- Owner's preferred scope: display name, docs and GitHub repo rename (see [Scope options](#scope-options)).
- Quick Settings tile label: not yet decided (see [Tile label options](#tile-label-options)).

## Why rename

Searches on 2026-09-11 found projects and apps whose names are hard to tell apart from "PrivDNS Toggle":

| Collision | Where | Notes |
|-----------|-------|-------|
| PrivateDNSToggle | https://github.com/PoppaBuzz/PrivateDNSToggle | Same feature set: Quick Settings tile, widgets, Shizuku or ADB grant |
| PrivateDNSAndroid | https://github.com/karasevm/PrivateDNSAndroid | Quick Settings tile to switch Private DNS server |
| Private DNS Quick Setting | Google Play (`com.flashsphere.privatednsqs`) | Toggles Private DNS from Quick Settings |

## Candidates considered

GitHub column: `gh search repos <name> --match name`. Stores / web column: web search for the name plus "android app".

| Name | Idea | GitHub | Stores / web | Verdict |
|------|------|--------|--------------|---------|
| **PinDNS** | Pins your DNS provider in place | Zero repos | Nothing found | **Chosen** |
| QuietDNS | Private DNS keeps lookups quiet | Zero repos | Nothing found | Clean, descriptive; not picked |
| TapDNS | One tap | Zero repos | Nothing found | Clean but literal; not picked |
| Sluice | A gate that controls flow; matches the funnel icon | Unrelated libraries only | Same-name business app on Play and App Store | Researched in depth below |
| Decant | Pour off the clean part, leave the sediment; matches the icon | Unrelated libraries only | Several consumer apps on Play and App Store | Researched in depth below |
| NameSwitch | DNS is name resolution, the app is a switch | Tiny inactive repos | NameSwitch Ltd, an established UK name-change service | Active brand; dropped |
| Funnel | Literal icon match | Generic word, many unrelated repos | Not checked | Too generic; dropped |

Rejected early, with reason:

| Name | Reason |
|------|--------|
| Spigot | Minecraft server ecosystem (SpigotMC) |
| Valve | Valve Software |
| Latch | Telefónica's Latch security app on Android |
| DNSwitch | Nintendo Switch DNS tools and a macOS DoT switcher already use it |
| HushDNS | Existing shell project with the same idea |
| Flick, Sift, Sieve, Shutter, Breaker | Generic words, heavily used |

## Deep research on the three finalists

Method, applied to Sluice, PinDNS and Decant on 2026-09-11:

- Google Play and Apple App Store via web search
- F-Droid search (`search.f-droid.org`)
- GitHub by name and by description, all languages, plus Kotlin and Java filters, plus the pairings "<name> android" and "<name> dns"
- Web search for any DNS, VPN, privacy, firewall or network product with the name
- Domain status by DNS resolution, confirmed with RDAP (`https://rdap.org/domain/<domain>`)
- Trademark: **not checked**. USPTO search is not scriptable from here; do a manual TESS search before any store listing.

### Sluice

- Google Play and App Store: "Sluice" by ClearValue Consulting (`com.inmotionsoftware.sluice`, iOS id 1231070095), a field-workflow app for property inspectors. Active, last updated August 2025. Different category, identical name.
- "Sluice AI" (app.sluiceai.com) is an unrelated AI product.
- DNS, VPN, privacy or network software named Sluice: none found.
- F-Droid: none. GitHub: only unrelated libraries (rate limiting, ETL, static file server). No Android, no DNS.
- Domains: sluice.app, sluice.io, sluice.dev and getsluice.com registered. sluiceapp.com available.
- Verdict: clean inside the DNS and privacy space, but a same-name app on both stores and the good domains gone. Fine for a GitHub-distributed project; a problem if a Play Store listing is ever planned.

### PinDNS

- Google Play, App Store, F-Droid: nothing under this name.
- GitHub: zero repos by name or description, in any language.
- Web: pindns.com is registered (2014) to an Argentine IT firm and used for nameservers only; no product or app behind it. "DNS pinning" and "pinpoint DNS" are existing networking terms, so the name reads slightly like a technique.
- Domains: pindns.com registered. pindns.app, pindns.io, pindns.dev and pindns.net all available (RDAP, 2026-09-11).
- Verdict: cleanest of the three in the software space. Descriptive, short, no store collisions.

### Decant

- Google Play: "Decant" perfume shop (`com.app.Decant`) and "Decant Index" spirits and wine marketplace (`com.decantindex`). App Store: Decant Index, Prim Decant, Decant Your Potential. decantapp.com is "Decant Sommelier". Decant Group Limited publishes several of these.
- DNS, VPN, privacy or network software named Decant: none found.
- F-Droid: none. GitHub: unrelated libraries plus one Android puzzle game called "decanting".
- Domains: decant.app, decant.dev, decant.io, decantapp.com and getdecant.com all registered. Nothing obvious left.
- Verdict: nicest metaphor, but the most crowded name. Five or more consumer apps already use it and every obvious domain is taken.

## Decision

**PinDNS**, chosen by the owner on 2026-09-11. Rationale: cleanest in the software space, descriptive, short, domains free.

Before or alongside the rename:

- Consider registering pindns.app (and pindns.io, pindns.dev, pindns.net) while they are available.
- Run a manual USPTO TESS search on "PinDNS" before any store listing.

## Scope options

Three scopes were laid out. The package ID is excluded from all of them (see [Never changes](#never-changes)).

| Option | What changes | What stays |
|--------|--------------|------------|
| README only | README title and text, with a note that repo and package still carry the old name | Everything else |
| Display name + docs | `app_name` in `strings.xml`, hardcoded title in `MainActivity.kt`, README, website brief, CHANGELOG entry | Repo name, package ID, APK filename, Obtainium link |
| Display name + docs + repo rename (**owner's preference**) | Everything above, plus `gh repo rename PinDNS`, local remote update, every URL swapped to `mjryan253/PinDNS` | Package ID, keystore alias, APK filename unless changed as a follow-up |

GitHub keeps a redirect from the old repository URL after a rename, so existing links, clones and the Obtainium source keep working.

## Tile label options

| Label | Length | Note |
|-------|--------|------|
| PinDNS Toggle | 13 | Owner's wording. Says name and action. May truncate to two lines or an ellipsis on narrow tile grids. |
| PinDNS | 6 | Never truncates. Matches the launcher name. |
| Pin DNS | 7 | Reads as the action itself. |
| Private DNS | 11 | Current label. Found by function in the tile picker. |

## Never changes

| Item | Why |
|------|-----|
| Package ID `com.privdnstoggle.app` | A new package ID is a new app to Android. Every user would lose the `WRITE_SECURE_SETTINGS` grant and saved hostname and have to reinstall. The Obtainium `id` field also depends on it. |
| Keystore alias `privdnstoggle` | The release signing key must stay the same key forever or updates stop installing over existing installs. |
| Style name `Theme.PrivDNSToggle` | Internal identifier in `themes.xml` and `AndroidManifest.xml`; not user-visible. |
| `privdnstoggle` logcat filters | They match the package name, which does not change. |
| Historical text | Past `CHANGELOG.md` entries and `agent/*.md` describe what was true at the time. |

## Footprint when the rename happens

Line references are as of 2026-09-11. `README.md` is listed without line numbers because it was rewritten on the same day.

| File | Change |
|------|--------|
| `app/src/main/res/values/strings.xml` | `app_name` to `PinDNS`; `tile_label` to the chosen tile label |
| `app/src/main/kotlin/com/privdnstoggle/app/MainActivity.kt:226` | Hardcoded `Text("PrivDNS Toggle")` in the top bar |
| `settings.gradle.kts:21` | `rootProject.name`, the project name Android Studio shows. No effect on package or APK. |
| `README.md` | Title, every mention, every URL, shields.io badge paths, Obtainium deep link `url` and `name` fields (`id` unchanged) |
| `quick-start.md` | Name at lines 3, 106, 150, 168, 188; URLs and clone folder at lines 5, 23, 24, 31, 269 |
| `docs/troubleshooting.md` | Title (line 1) and line 86. The `privdnstoggle` logcat filters stay. |
| `docs/testing.md` | Line 3 name; line 75 Gradle tool-window project name |
| `docs/website-brief.md` | Title, name, all URLs; add one line "formerly PrivDNS Toggle" so the designer recognises old references |
| `CHANGELOG.md` | Line 3 name; new `## [Unreleased]` / `### Changed` entry: renamed to PinDNS, package ID unchanged, existing installs update in place |

Optional follow-ups, not part of the scopes above:

- `.github/workflows/publish.yml:99`: APK filename `PrivDNSToggle-v${VERSION}.apk` to `PinDNS-v${VERSION}.apk`. If changed, update the `apksigner verify` example in the README and the brief.
- `gh repo edit --description "..."`: current description is "An android private dns toggle".

Not touched in any scope: `agent/agent-rules.md`, `agent/agent-history.md`, `docs/release-workflow.md`, `docs/original-plan-idea.md`, `app/proguard-rules.pro`, `themes.xml`, `AndroidManifest.xml`, `lint-baseline.xml`, workflows other than the optional filename change.

## Procedure and order

1. Rename the GitHub repository first. This is an outward-facing change; confirm immediately before running it.

   ```sh
   flatpak-spawn --host gh repo rename PinDNS -R mjryan253/PrivDNSToggle --yes
   git remote set-url origin git@github.com:mjryan253/PinDNS.git
   ```

   Checked 2026-09-11: `mjryan253/PinDNS` does not exist and no repository on GitHub is named PinDNS.

2. If the rename is declined, stop. Do not point documentation at a repository that does not exist.
3. Apply the file edits in the footprint table, README first.
4. Verify (next section).
5. Commit locally when asked. Do not push unless told to.

## Verification checklist for the rename pass

- `flatpak-spawn --host gh repo view mjryan253/PinDNS --json url` returns the new URL.
- `curl -sI https://github.com/mjryan253/PrivDNSToggle` returns a 301 to the new name.
- `git remote -v` shows the new remote and `git fetch --dry-run` succeeds.
- `grep -rn -iE 'privdns toggle|PrivDNSToggle' README.md quick-start.md docs/*.md CHANGELOG.md app/src/main/res/values/strings.xml app/src/main/kotlin settings.gradle.kts` returns only the allowed leftovers: the APK filename (if unchanged), the "formerly PrivDNS Toggle" lines, and historical CHANGELOG entries.
- `com.privdnstoggle`, the `privdnstoggle` keystore alias and the logcat filters are unchanged everywhere.
- Build and lint still pass with the string and Gradle-name changes. From inside the flatpak sandbox:

  ```sh
  JAVA_HOME=/home/mjr/.gradle/jdks/jetbrains_s_r_o_-21-amd64-linux.2 ANDROID_HOME=/home/mjr/Android/Sdk ./gradlew --continue :app:testDebugUnitTest :app:lintDebug
  ```

  Lint must show no new findings against `app/lint-baseline.xml`.
- Install the debug build on a device and confirm the launcher label, the top bar title and the Quick Settings tile label all show the new name, and that the existing permission grant still works without re-granting.
