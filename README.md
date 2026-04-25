# No Creative Drift (Forge) — unofficial build

Dampens horizontal drift in creative flight: when movement keys are released
while flying, the player's horizontal velocity diminishes to zero over a few
ticks instead of coasting indefinitely.

> **This is an unofficial reimplementation.** It is not affiliated with,
> endorsed by, or derived from the official *No Creative Drift* project
> linked below. The behavior is similar by design, but the code here was
> written from scratch.

## The official mod

The official *No Creative Drift* — with Fabric, Forge, and NeoForge builds —
is published on Modrinth:

<https://modrinth.com/mod/nocreativedrift/versions?l=forge>

**Use the official mod whenever it covers the Minecraft version you need.**
As of writing, its Forge coverage is:

- **1.20.4** — single build, nothing above it
- **1.19.3 and below** — well covered

That leaves a gap above 1.20.4 (1.20.5 onward, 1.21.x) and a smaller gap
between 1.20.4 and 1.19.3 (Minecraft 1.19.4, 1.20.0, 1.20.1, 1.20.2, 1.20.3).

## Purpose of this repo

Provide Forge jars for MC versions the official release page doesn't cover.
If the version you need is already on Modrinth, use that — this repo won't
produce anything better.

## Supported Minecraft versions in this repo

| MC version | Forge    | Subproject      | Notes                                                |
| ---------- | -------- | --------------- | ---------------------------------------------------- |
| 1.18.2     | 40.3.0+  | `forge-1.18.2/` | Redundant with official — kept from earlier work     |
| 1.19.4     | 45.3.0+  | `forge-1.19.4/` | In the official coverage gap (1.19.4 – 1.20.3)       |
| 1.20       | 46.0.14+ | `forge-1.20.0/` | In the official coverage gap (1.19.4 – 1.20.3)       |
| 1.20.1     | 47.4.6+  | `forge-1.20.1/` | In the official coverage gap (1.19.4 – 1.20.3)       |
| 1.20.2     | 48.1.0+  | `forge-1.20.2/` | In the official coverage gap (1.19.4 – 1.20.3)       |
| 1.20.3     | 49.0.2   | `forge-1.20.3/` | Forge only ever shipped 49.0.1 and 49.0.2 for 1.20.3 |
| 1.20.6     | 50.1.0+  | `forge-1.20.6/` | Above the official ceiling (no official Forge build) |
| 1.21.1     | 52.1.0+  | `forge-1.21.1/` | Above the official ceiling (no official Forge build) |

Each version is an independent Gradle subproject with its own source set.
Shared mod metadata (`mod_id`, `mod_version`, etc.) lives in the root
`gradle.properties`; per-version properties (`minecraft_version`,
`forge_version`, mappings) live in each subproject's `gradle.properties`.

## Build

```sh
./gradlew build                 # builds every subproject
./gradlew :forge-1.20.1:build   # builds just one
./gradlew :forge-1.21.1:build   # builds just one
```

Outputs land in `./releases/`, one per subproject:

- `nocreativedrift-1.18.2-1.0.0.jar`
- `nocreativedrift-1.19.4-1.0.0.jar`
- `nocreativedrift-1.20-1.0.0.jar`
- `nocreativedrift-1.20.1-1.0.0.jar`
- `nocreativedrift-1.20.2-1.0.0.jar`
- `nocreativedrift-1.20.3-1.0.0.jar`
- `nocreativedrift-1.20.6-1.0.0.jar`
- `nocreativedrift-1.21.1-1.0.0.jar`

## Future work

Remaining gap-fillers not yet covered here:

Between 1.19.3 and 1.20.4:

- [x] 1.19.4
- [x] 1.20.0
- [x] 1.20.2
- [x] 1.20.3

Above 1.20.4 (no official Forge at all):

- [x] 1.20.6 (1.20.5 has no Forge release; Forge skipped from 1.20.4 to 1.20.6)
- [x] 1.21.1
- [ ] 1.21.0, 1.21.2, 1.21.3, 1.21.4 — not yet covered; 1.21.1 is the most
      common in the wild

To add one, create a new subproject directory `forge-<mc>/` with its own
`build.gradle`, `gradle.properties`, and `src/main/`, then add
`include 'forge-<mc>'` to the root `settings.gradle`.

## Layout rationale

See [`docs/multi-version-plan.md`](docs/multi-version-plan.md).
