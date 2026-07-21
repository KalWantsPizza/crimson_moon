# Crimson Moon — Session Notes (Reference)

Project: NeoForge 1.21.1 mod (`neo_version=21.1.211`), mod id `crimson_moon`,
package `dev.orbitingslice.crimson_moon`. Custom dimension built via Java
datagen (not hand-written JSON) — the correct modern approach for 1.18+.

This doc summarizes what was reviewed, what was changed, and what's still
open, so this session can be used as a reference alongside local work in
Claude Code. **The zip attached to this chat message is the current state
after all changes below.** It has not been synced to your local machine —
you'll need to merge/replace manually.

---

## Confirmed working / solid
- `CrimsonMoonSurfaceRules.java` and `CrimsonMoonNoiseGeneratorSettings.java`
  use current, correct 1.21.1 APIs (`SurfaceRules`, `DensityFunctions`,
  `NormalNoise`). Genuinely good work, not junk.
- Datagen pipeline structure (`CrimsonMoonDataGenerator` →
  `ModWorldGenProvider` → per-registry bootstrap classes) is
  textbook-correct for NeoForge 1.21.x.
- `event.getLookupProvider()` → `DatapackBuiltinEntriesProvider` setup
  correctly gives datagen access to **vanilla** registry entries alongside
  the custom ones, confirmed by tracing `CrimsonMoonDataGenerator` →
  `ModWorldGenProvider`. This is what makes referencing vanilla biomes
  directly (see below) work without redefining them.

## Issues found and fixed this session

1. **`biome-X/` folder (deleted)** — hand-written biome JSON using
   pre-1.18 schema fields (`surface_builder`, `category`, `scale`, `depth`
   at top level, `precipitation` instead of `has_precipitation`). Not
   wired into the build, so it was inert clutter, not a live bug — but
   concrete evidence of the "old-version guidance" problem from the prior
   LLM. Deleted.

2. **`datagen_kit` package (deleted)** — originally an intentional,
   reasonable idea: a reusable datagen template kit (base classes for
   biomes, noise, dimension setup) meant to be copied into future
   projects, built while wrestling with hand-writing noise/surface rules
   before the datapack→mod pivot. Got abandoned mid-implementation:
   `ModDataGenerators.gatherData()` and `ModBiomes.bootstrap()` had their
   bodies commented out, referenced nonexistent classes
   (`ModOverworldBiomes` etc.), and the actual working Crimson Moon code
   never ended up building on it — it re-implemented the same logic
   standalone instead. Decision: park the templating idea, delete the
   disconnected scaffold, revisit generalizing a real kit later from a
   working example instead of building the abstraction first.

   **Follow-up bug caught during deletion:** `CrimsonMoon.java` and
   `CrimsonMoonDataGenerator.java` both had dangling references into the
   deleted package (`import ... ModBiomes`, `extends ModDataGenerators`)
   that would have broken compilation. Both fixed. **Worth a full project
   grep for `datagen_kit` before building, in case Claude Code's local
   copy has other references this session didn't touch.**

3. **5 of 9 biomes never generated (fixed)** — `CrimsonMoonDimension.java`'s
   multi_noise biome source only had climate parameters for 4 of 9
   registered biomes. The other 5 (Scorched Delta, Deepslate Caves,
   Deepslate Peaks, Warped Oasis, Frozen Lava Tubes) turned out to
   already have parameter blocks drafted — just commented out. Re-enabled
   as originally drafted (values unchanged).

   **Flagged, not resolved:** `lava_river`'s depth range
   (`-1.00F, -0.70F`) numerically overlaps/exceeds `deepslate_peaks`'
   "tallest, highest elevation" range, despite lava_river's comment
   saying it should be "even lower" than crimson_river. If the depth
   convention is "more negative = higher elevation" (consistent with the
   peaks comment), lava_river's range looks backwards. Left as-is with a
   code comment — needs in-game verification before retuning blind.

4. **Crimson Plains surface rule bug (fixed)** — was using
   `noiseCondition(CRIMSON_SURFACE_NOISE, 0, 100)` to decide nylium vs.
   grass. Since the noise sampler outputs roughly in [-1, 1], this
   threshold effectively just checked "noise ≥ 0," producing large
   contiguous nylium patches across ~50% of the biome — not the
   grass-dominant "livable default" biome intended. Fixed: Crimson Plains
   surface is now 100% grass+dirt. Nylium/fungus groves are intentionally
   *not* handled via surface rule anymore — deferred to a proper
   feature-based vegetation patch (same mechanism vanilla uses for e.g.
   Lush Caves moss), which places ground block + vegetation together as
   one localized feature instead of a biome-wide noise split. **This
   feature is not yet built — still open, see below.**

5. **4 vanilla biomes added** — per re-reading the full
   `crimson_moon_dimension_design.md`:
   - **Warm Ocean** and **Badlands** were already explicitly planned as
     neighbors of Crimson Beach in the docs, just never implemented.
   - **Desert** turned out to be implicitly planned too — referenced as a
     Warped Oasis host biome and Crimson Beach neighbor in two separate
     docs, even though never mentioned by name in this chat until now.
   - **Crimson Forest** was not in any doc, added per explicit request —
     kept as pure nylium/netherrack (no grass) with default vanilla
     Hoglin/Piglin spawns and dense fungus, placed as a rarer
     weirdness/humidity-sliced variant *within* Crimson Plains' climate
     envelope (same technique vanilla uses for Sunflower Plains as a
     Plains variant) — deliberately kept distinct from the now
     grass-dominant Crimson Plains rather than competing with it.

   All 4 referenced directly via vanilla registry IDs
   (`minecraft:warm_ocean` etc.) in `CrimsonClimateHelper.biomePair(...)`
   — no new biome classes needed, since vanilla biomes bring their own
   vegetation/mobs/features already. Only surface rules and climate
   tuning are custom-project concerns. **All 4 climate parameter bands
   are first-pass/provisional — flagged in code comments, need
   playtesting.**

## Still open / not yet done
- **Nylium-grove feature for Crimson Plains** — configured_feature +
  placed_feature for scattered fungus groves, wiring into
  `CrimsonMoonBiomes`'s generation settings. Explicitly deferred this
  session pending a sequencing decision.
- **lava_river vs. deepslate_peaks depth conflict** — flagged, not fixed.
- **Vegetation & mob spawns for all 9 custom biomes** — currently only
  colors/temperature/downfall are implemented in `CrimsonMoonBiomes.java`;
  `MobSpawnSettings` and `BiomeGenerationSettings` are built empty. None
  of the flora/fauna described in the design docs (crimson fungi,
  Striders, Magma Cubes, Allays in Warped Oasis, etc.) are wired up yet.
- **Badlands terracotta banding / surface rules for the 4 new vanilla
  biomes** — vanilla biomes bring their own default surface behavior,
  but should be checked against the project's custom `STONE_GRADIENT`
  fallback logic to confirm no conflicts.
- **Climate parameter playtesting** — essentially all 13 biomes' climate
  bands (9 custom + 4 vanilla) are first drafts needing in-game
  verification, not final-tuned values.

## Housekeeping note
This chat's memory/session is separate from Claude Code's access to your
local files. Nothing here syncs automatically — treat the attached zip as
the authoritative "what changed" snapshot to merge into your local copy
before continuing in Claude Code, or continue from your local copy and
treat this doc as a change log / catch-up reference instead.
