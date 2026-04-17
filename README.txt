No Creative Drift (for Forge)

Dampens horizontal drift in creative flight: when movement keys are released
while flying, the player's horizontal velocity diminishes to zero over a few
ticks instead of coasting indefinitely.

Supported Minecraft versions:
  - 1.18.2  (Forge 40.3.0+)   forge-1.18.2/
  - 1.20.1  (Forge 47.4.6+)   forge-1.20.1/

Each version is an independent Gradle subproject with its own source set.
Shared mod metadata (mod_id, mod_version, etc.) lives in the root
gradle.properties; per-version properties (minecraft_version, forge_version,
mappings) live in each subproject's gradle.properties.

Build:
  ./gradlew build                       # builds both versions
  ./gradlew :forge-1.20.1:build         # builds just 1.20.1
  ./gradlew :forge-1.18.2:build         # builds just 1.18.2

Outputs land in ./releases/ as:
  nocreativedrift-1.18.2-1.0.0.jar
  nocreativedrift-1.20.1-1.0.0.jar

See docs/multi-version-plan.md for layout rationale.
