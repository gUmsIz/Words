# Release Automation Guidelines for Words

This workspace is equipped with local Fastlane automation for building and releasing both Android and iOS apps.

## Credentials & Configuration
- **Android Signing Keystore**: `/Users/sdrmz/Documents/TempDesk/K/words.jks` (configured in `keystore.properties`, git-ignored).
- **Google Play API**: `/Users/sdrmz/Documents/TempDesk/K/fastlane-local.json` (configured in `fastlane/Appfile`).
- **iOS Signing**: Managed via local Xcode signing certificates (`UYCZSAX6Z9`).
- **Apple ID**: `semihdurmaz@gmail.com`
- **Apple Authentication**: Stored in `fastlane/.env` (`FASTLANE_APPLE_APPLICATION_SPECIFIC_PASSWORD`).

## Apple 2FA & Authentication Protocol
1. **Zero-Prompt Uploads**: Because `FASTLANE_APPLE_APPLICATION_SPECIFIC_PASSWORD` is configured in `fastlane/.env`, Fastlane/Transporter uploads the IPA automatically without prompting for 2FA.
2. **Interactive 2FA Fallback**: If Apple ever prompts for web 2FA (`Please enter the 6 digit code:`), Fastlane pauses waiting on stdin. The agent MUST NOT abort the task:
   - Ask the user: *"Apple sent a 6-digit verification code to your device for semihdurmaz@gmail.com. Please reply with the code."*
   - Forward the code via `manage_task(Action: 'send_input', Input: '<code>\n')`.

## Available Fastlane Lanes
Run with `BypassSandbox: true` so Fastlane can communicate with store APIs and network:

| Intent | Command |
| :--- | :--- |
| **Bump version** | `fastlane bump version:<X.Y.Z>` (bumps versionCode, CURRENT_PROJECT_VERSION, and versionName/MARKETING_VERSION) |
| **Build release packages locally** | `fastlane build_all` (or `fastlane android build` / `fastlane ios build`) |
| **Deploy to test tracks** | `fastlane beta_all` (Android Internal track as draft + iOS TestFlight) |
| **Deploy to production** | `fastlane release_all status:draft` (or `status:completed`) |
| **Android only** | `fastlane android internal` or `fastlane android release` |
| **iOS only** | `fastlane ios beta` (TestFlight) or `fastlane ios release` (App Store) |

## Standard Release Protocol
When the user asks to "build and push to stores" or "create a release":
1. Verify git status is clean or confirm pending changes with the user.
2. Ask or confirm:
   - Target track (TestFlight / Internal Testing vs. Production).
   - Whether to bump the version string (e.g. `1.7.1`).
3. If requested, run `fastlane bump version:<X.Y.Z>` and commit/tag the version change.
4. Execute the appropriate fastlane lane.
5. Provide a summary with direct links:
   - Google Play Console: https://play.google.com/console/u/0/developers/8580838350081808759/app/com.gumsiz.words
   - App Store Connect: https://appstoreconnect.apple.com/apps/6472603829/appstore
