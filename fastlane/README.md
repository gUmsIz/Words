fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

### bump

```sh
[bundle exec] fastlane bump
```

Bump version code / build number and optionally version name for Android and iOS

### build_all

```sh
[bundle exec] fastlane build_all
```

Build release packages for both Android and iOS

### beta_all

```sh
[bundle exec] fastlane beta_all
```

Release both Android to Internal and iOS to TestFlight

### release_all

```sh
[bundle exec] fastlane release_all
```

Release both Android and iOS to production/stores

----


## Android

### android build

```sh
[bundle exec] fastlane android build
```

Build signed Android Release App Bundle (AAB)

### android internal

```sh
[bundle exec] fastlane android internal
```

Build and upload Release AAB to Google Play Internal Testing

### android release

```sh
[bundle exec] fastlane android release
```

Build and upload Release AAB to Google Play Production

----


## iOS

### ios build

```sh
[bundle exec] fastlane ios build
```

Build signed iOS App (IPA)

### ios beta

```sh
[bundle exec] fastlane ios beta
```

Build and upload to TestFlight

### ios release

```sh
[bundle exec] fastlane ios release
```

Build and upload to App Store Connect for Release

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
