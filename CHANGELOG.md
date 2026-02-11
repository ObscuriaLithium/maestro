This update finalizes the core feature set and prepares the project for porting to the latest game versions. The data-driven API can now be considered stable. All future updates will maintain full backward compatibility.

### Changes
- Refactored the `maestro:biome_tag` condition schema for improved flexibility and extensibility.
- Refactored the `maestro:dimension` condition schema for improved flexibility and extensibility.
- Refactored the `maestro:height` condition schema for improved flexibility and extensibility.
- Extended the `maestro:weather` condition to support sandstorm detection.

### New Conditions
- Added `maestro:entity_tag` – counts entities by tag with configurable radius.
- Added `maestro:day_cycle` – detects day, night, sunrise, and sunset phases.
- Added `maestro:time` – evaluates time of day using numeric tick ranges.
- Added `maestro:vehicle` – detects and filters the player's current vehicle.
- Added `maestro:boss_event` – detects active boss health overlays.
- Added `maestro:fishing` – detects when the player is fishing.
- Added `maestro:in_game` – detects an active world and player presence.
- Added `maestro:creative` – detects Creative mode.
- Added `maestro:underwater` – detects when the player is underwater.

For detailed information, please refer to the updated [wiki](https://obscurialithium.github.io/maestro/).