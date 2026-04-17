# No Creative Drift (Forge)

Dampens horizontal drift in creative flight: when movement keys are released
while flying, the player's horizontal velocity diminishes to zero over a few
ticks instead of coasting indefinitely.

## Official releases

Official Fabric, Forge, and NeoForge builds are published on Modrinth:

<https://modrinth.com/mod/nocreativedrift/versions?l=forge>

Forge coverage as of writing: the latest 1.21.x line down through **1.20.4**,
then a gap, then **1.19.3** and earlier. Minecraft **1.19.4, 1.20.0, 1.20.1,
1.20.2, and 1.20.3** have no official Forge build.

## Purpose of this repo

Builds Forge jars for MC versions that aren't covered by the official release
page. If the version you need is already on Modrinth, grab it from there —
this repo won't produce anything different or newer.

## Supported Minecraft versions in this repo

| MC version | Forge    | Subproject       | Notes                            |
| ---------- | -------- | ---------------- | -------------------------------- |
| 1.18.2     | 40.3.0+  | `forge-1.18.2/`  | Also covered on Modrinth         |
| 1.20.1     | 47.4.6+  | `forge-1.20.1/`  | In the official coverage gap     |

Each version is an independent Gradle subproject with its own source set.
Shared mod metadata (`mod_id`, `mod_version`, etc.) lives in the root
`gradle.properties`; per-version properties (`minecraft_version`,
`forge_version`, mappings) live in each subproject's `gradle.properties`.

## Build

```sh
./gradlew build                 # builds both versions
./gradlew :forge-1.20.1:build   # builds just 1.20.1
./gradlew :forge-1.18.2:build   # builds just 1.18.2
```

Outputs land in `./releases/`:

- `nocreativedrift-1.18.2-1.0.0.jar`
- `nocreativedrift-1.20.1-1.0.0.jar`

## Future work

Gap-filling Forge builds for the uncovered MC versions between 1.19.3 and
1.20.4:

- [ ] 1.19.4
- [ ] 1.20.0
- [ ] 1.20.2
- [ ] 1.20.3

To add one, create a new subproject directory `forge-<mc>/` with its own
`build.gradle`, `gradle.properties`, and `src/main/`, then add
`include 'forge-<mc>'` to the root `settings.gradle`.

## Layout rationale

See [`docs/multi-version-plan.md`](docs/multi-version-plan.md).
