# Multi-version (Forge-only) restructure

## Decision

Gradle multi-project, Forge-only, no shared source tree (option "a").
Each MC version is an independent subproject with its own full source.
The root project centralizes mod metadata and build orchestration.

Rejected: a shared `common/` source set. The mod's only real code is the
`MixinLocalPlayer` class, and it references Minecraft classes whose APIs
differ between 1.18.2 (`isOnGround()`) and 1.20.1 (`onGround()`). The
`NoCreativeDrift` Forge entry class also diverges across versions (1.18.2
uses `FMLJavaModLoadingContext.get()`, 1.20.1 takes context in the
constructor). A shared source set would either force an abstraction layer
larger than the mod itself, or not compile.

## Target layout

```
nocreativedrift/
├── .gitignore
├── .gitattributes
├── LICENSE.txt
├── README.txt
├── settings.gradle              # includes :forge-1.18.2 and :forge-1.20.1
├── build.gradle                 # minimal; no plugins applied at root
├── gradle.properties            # shared mod metadata + jvm settings
├── gradle/wrapper/              # single shared wrapper
├── gradlew / gradlew.bat
├── docs/
│   └── multi-version-plan.md
├── releases/                    # both subprojects write here
├── forge-1.18.2/
│   ├── build.gradle
│   ├── gradle.properties        # mc + forge + mapping versions
│   └── src/main/{java,resources}
└── forge-1.20.1/
    ├── build.gradle
    ├── gradle.properties
    └── src/main/{java,resources}
```

## Property ownership

**Root `gradle.properties`** (applies to all projects):

- `org.gradle.jvmargs`, `org.gradle.daemon`
- `mod_id`, `mod_name`, `mod_version`, `mod_group_id`
- `mod_authors`, `mod_license`, `mod_description`

**Subproject `gradle.properties`** (overrides root for that subproject):

- `minecraft_version`, `minecraft_version_range`
- `forge_version`, `forge_version_range`
- `loader_version_range`
- `mapping_channel`, `mapping_version`

## Jar output

Each subproject produces a version-qualified jar to the root `releases/`:

- `releases/nocreativedrift-1.18.2-1.0.0.jar`
- `releases/nocreativedrift-1.20.1-1.0.0.jar`

Achieved via `archivesName = "${mod_id}-${minecraft_version}"` and
`destinationDirectory = file("${rootProject.projectDir}/releases")` in each
subproject's `jar` task configuration.

## Source origins

- `forge-1.20.1/` — `git mv` from the current root. Identical content.
- `forge-1.18.2/` — copy of `G:\projects\forge-1.18.2-40.3.0-mdk\src\main`
  and `build.gradle`. Excludes `src/generated`, `src/test`, `build/`,
  `run/`, `run-data/`, `CREDITS.txt`, `changelog.txt`.

The 1.18.2 `NoCreativeDrift.java` is the unmodified MDK template (not
updated in parallel with the 1.20.1 rewrite); kept as-is because the mod's
functionality lives entirely in the mixin.

## Known version differences

- Mixin: `isOnGround()` (1.18.2) vs `onGround()` (1.20.1) on `LocalPlayer`.
- Entry class constructor signature (see above).
- `mods.toml`: minor ordering; both use `${...}` token expansion.
- `pack.mcmeta`: `pack_format=9` + forge format keys (1.18.2) vs
  `pack_format=15` (1.20.1).
- `mixins.json`: `minVersion` 0.7 (1.18.2) vs 0.8 (1.20.1).
- 1.18.2 jar manifest adds `MixinConfigs` attribute (required on 1.18.2,
  not on 1.20.1).

## Build commands

- Build both: `./gradlew build`
- Build one: `./gradlew :forge-1.20.1:build` or `:forge-1.18.2:build`
- Clean all: `./gradlew clean`

## Out of scope

Fabric and NeoForge variants. Installed jars for those exist on disk
(`nocreativedrift-1.20.1-5.0.0.0-fabric.jar`,
`nocreativedrift-neoforge-1.21.1-6.0.0.1.jar`) but no matching source on
this machine; likely built by the upstream author.
