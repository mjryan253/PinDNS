# PinDNS — Website Page Brief

## Purpose of this document

This is a reference for building one page on the owner's website about the PinDNS Android app. The page should explain what the app is, who it is for, and send visitors to the GitHub repository. Nothing here needs to be copied word for word. Adapt the wording to fit the site, but keep the facts as stated and do not add claims that are not listed here.

The one required element is a clear link to the GitHub repo.

**Where the page lives:** the project's domain is `pindns.xyz`, which redirects to this page at https://honesttech.org/software/privdns-toggle. Publish `pindns.xyz` as the project's address everywhere; it is the one place to repoint if the page ever moves.

## Primary link

**GitHub repository:** https://github.com/mjryan253/PinDNS

This is the main call to action on the page. Everything else (downloads, issues, donations) is secondary.

## What the app is

- **Name:** PinDNS
- **Formerly:** PrivDNS Toggle (renamed 2026-09-12)
- **Platform:** Android
- **Package name:** `com.privdnstoggle.app`
- **Current version:** 0.6 (a v1.0 release is targeted for Q4 2026)
- **Status:** working, actively developed, pre-1.0
- **License:** GNU General Public License v3.0 or later (free and open source). Full text: https://github.com/mjryan253/PinDNS/blob/main/LICENSE

One-line description: a minimal Android app that turns Private DNS on and off from the Quick Settings shade or from the app itself.

The problem it solves: Android supports Private DNS (encrypted DNS pointed at a provider such as NextDNS, AdGuard or Cloudflare), but the switch lives several menus deep in Settings. Some networks, apps or captive portals break when Private DNS is on, so people end up toggling it often. PinDNS puts that switch one tap away in the notification shade.

## Who it is for

- Android users who already use a Private DNS provider and want to switch it on and off quickly.
- People comfortable with a one-time technical setup step (running one ADB command from a computer, or using the Shizuku app on the phone).
- Privacy-minded users who want a small tool with no background services and no analytics.

This is a niche utility for a technical audience, not a mass-market consumer app. Write for someone who knows what DNS is.

## Key features

- **Quick Settings tile.** Pull down the notification shade and tap "PinDNS Toggle" to toggle it.
- **Large in-app toggle.** A prominent on/off switch on the main screen.
- **DNS connection test.** The app tests the entered hostname before saving it (10 second timeout).
- **Dark, OLED-black interface.** Pure black background by default.
- **Lightweight.** No background services, no analytics, no tracking.
- **Shizuku support.** The required permission can be granted from inside the app via Shizuku, with no computer needed.

## How it works, in plain language

Android stores the Private DNS setting as a system value. The app flips that value between "off" and the hostname you saved. That is all it does.

Writing that system value requires a permission called `WRITE_SECURE_SETTINGS`, which Android will not grant through a normal permission pop-up. The user grants it once, either by running a single ADB command from a computer or by tapping "Grant with Shizuku" inside the app if Shizuku is installed. The grant persists across app updates. Root is not required.

## Requirements

- Android 9 or newer (API 28)
- One-time permission grant via ADB or Shizuku
- Internet connection during setup, for the DNS connection test
- Officially tested on Samsung Galaxy devices. Other devices have worked but are not officially supported.

## Where to get it

- **GitHub Releases:** https://github.com/mjryan253/PinDNS/releases/latest
  Release files are named `PinDNS-v<version>.apk`.
- **Obtainium** (an Android app that installs and updates apps straight from GitHub). Direct install link:
  https://apps.obtainium.imranr.dev/redirect?r=obtainium://app/%7B%22id%22%3A%22com.privdnstoggle.app%22%2C%22url%22%3A%22https%3A%2F%2Fgithub.com%2Fmjryan253%2FPinDNS%22%2C%22author%22%3A%22mjryan253%22%2C%22name%22%3A%22PinDNS%22%7D
  An "Get it on Obtainium" badge image is in the repo at `docs/images/badge_obtainium.png`.

The app is **not** on Google Play or F-Droid. Do not show those badges.

Optional "verify your download" detail, if the page has room for it: release APKs are signed with a key whose certificate SHA-256 fingerprint is

```
5344da60b330aca1109d26bac272f437d72c9a1a456aa1c9b7ea9c587ce19289
```

## Optional provider suggestion

The page may include one sentence pointing readers who do not yet have a DNS provider to NextDNS, using this referral link: https://nextdns.io/?from=skn4pmg9. If the link appears, label it as a referral link right next to it, for example: "Referral link. Signing up through it supports the project at no extra cost to you." The app works with any provider; do not present NextDNS as required.

## Tone and messaging

Plain, technical, honest. No hype, no superlatives, no "revolutionary". The README speaks in the first person as a single developer giving a side project back to the community. The page can echo that.

Headline ideas the designer may adapt:

- "Private DNS, one tap away."
- "Toggle Android Private DNS from Quick Settings."
- "A small switch for a buried setting."

Subhead ideas:

- "Free, open source, no background services, no analytics."
- "Grant one permission once. Then it just works."

Owner blurb from the README, usable as-is or shortened:

> I'm a single developer with over 20 years of IT experience, and PinDNS is a side project I'm happy to give back to the community. Testing and feedback are appreciated: please open an issue with bug reports or suggestions.

## Visual direction

The app is pure black with a single green accent. The page can match it or the site's own theme, but the app screenshots and icon will look best on a dark background.

App palette:

| Role | Hex |
|------|-----|
| Background (OLED black) | `#000000` |
| Panel / card surface | `#1A1A1A` |
| Toggle off, muted gray | `#3A3A3C` |
| Toggle on, accent green | `#34C759` |
| Text and toggle thumb | `#FFFFFF` |

App icon: a black square with a light gray funnel. Tangled red lines pour into the top and a single clean green beam exits the bottom. The metaphor is messy DNS traffic filtered into one clean private DNS path. It works well as a hero image.

Asset paths in the repo:

- `filter-icon.png` — highest resolution icon source, 863 x 863 PNG, repo root
- `app/src/main/res/mipmap-xxxhdpi/ic_launcher.png` — launcher icon as shipped
- `app/src/main/res/drawable-xxxhdpi/ic_dns.png` — the Quick Settings tile glyph
- `docs/images/badge_obtainium.png` — "Get it on Obtainium" badge

Screenshots are in `docs/images/screenshots/`: the main screen with Private DNS off and on, and the Quick Settings tile.

## Do not claim

- **Not MIT or Apache.** The license is GPL-3.0 or later. "Open source" and "free software" are both accurate.
- **Not on Google Play or F-Droid.**
- **Not "zero setup".** Root is not required, but a one-time permission grant is.
- **Not officially supported on every device.** Only Samsung Galaxy devices are officially supported.
- **No analytics, no tracking, no accounts.** These are true and can be stated.

## All links

| Purpose | URL |
|---------|-----|
| Repository (primary) | https://github.com/mjryan253/PinDNS |
| Project site (redirects to this page) | https://pindns.xyz |
| Latest release / download | https://github.com/mjryan253/PinDNS/releases/latest |
| All releases | https://github.com/mjryan253/PinDNS/releases |
| Bug reports and suggestions | https://github.com/mjryan253/PinDNS/issues |
| Obtainium install | see "Where to get it" above |
| Shizuku (third-party app used for the no-computer setup) | https://shizuku.rikka.app |
| NextDNS (optional provider, referral link) | https://nextdns.io/?from=skn4pmg9 |
| Donate (PayPal) | https://paypal.me/mryan351 |
| Full README | https://github.com/mjryan253/PinDNS/blob/main/README.md |
| Step-by-step setup guide | https://github.com/mjryan253/PinDNS/blob/main/quick-start.md |

## Open items for the owner

- **Donation link.** Decide whether the PayPal link belongs on the page.
