# Crimson Moon — Worldgen Status (Session Reference)

Written at the end of a long worldgen session, for a fresh Claude Code session
(or you) to pick up from. Covers: what's actually working, the mental model
needed to keep tuning the climate table without reintroducing old bugs, every
open bug, and what's explicitly deferred. Read this before touching
`CrimsonMoonDimension.java`, `CrimsonMoonNoiseGeneratorSettings.java`, or
`CrimsonMoonBiomes.java` — a lot of non-obvious lessons are baked into the
current numbers and would otherwise get relearned the hard way.

`docs/crimson_moon_session_notes.md` is the **older** doc from the session
before this one (the zip-merge/initial-bugfix session). This doc supersedes it
for anything about terrain/climate/biomes; that one is still fine as a record
of what happened first.

---

## 1. The big picture

Crimson Moon is a custom NeoForge 1.21.1 dimension (mod id `crimson_moon`,
package `dev.orbitingslice.crimson_moon`) built entirely through Java datagen
(`RegistrySetBuilder` bootstrap methods), not hand-written JSON. This session's
work was almost entirely about **making terrain height actually correlate with
the same climate noise used to place biomes** — it didn't when the session
started (fixed 47–87 Y band regardless of biome), and fixing that properly
took several rounds because of a recurring failure mode explained in detail
below (Section 3). By the end of the session, 15 biomes exist and mostly
generate where they're supposed to; a handful of items are explicitly parked
(Section 5) rather than "fixed."

**Workflow used the entire session, still the right one:**
```
edit → ./gradlew compileJava → ./gradlew runData → spot-check generated JSON → user tests in-game → repeat
```
`runData` regenerates everything under `src/generated/resources/` — never
hand-edit those files. A recurring, harmless warning
(`Placed feature minecraft:dark_oak_checked in biome X is missing
BiomeFilter.biome()`) appears for every biome using `TreePlacements
.DARK_OAK_CHECKED` (Warped Oasis, Crimson Taiga) — this is a vanilla-side
quirk on that specific feature, not something introduced here; it's never
correlated with an actual functional problem this session (unlike the "no
Dark Oak spawns" issue, which is still unexplained — see Section 5).

---

## 2. Key files

| File | Role |
|---|---|
| [CrimsonMoonNoiseGeneratorSettings.java](../src/main/java/dev/orbitingslice/crimson_moon/datagen/worldgen/CrimsonMoonNoiseGeneratorSettings.java) | The `NoiseRouter` / terrain shape. Height spline, erosion-amplitude spline, `depth` axis definition. **Read Section 3 before touching this.** |
| [CrimsonMoonDimension.java](../src/main/java/dev/orbitingslice/crimson_moon/datagen/worldgen/CrimsonMoonDimension.java) | The full climate table — all 15 biomes' temp/humidity/continentalness/erosion/weirdness/depth ranges. Every range has a comment explaining *why* it's shaped that way; read them before changing a number, they usually encode a hard-won lesson. |
| [CrimsonMoonBiomes.java](../src/main/java/dev/orbitingslice/crimson_moon/datagen/worldgen/CrimsonMoonBiomes.java) | Registers all 15 `Biome` objects: colors, `BiomeGenerationSettings` (vegetation/ores), `MobSpawnSettings`. |
| [CrimsonMoonSurfaceRules.java](../src/main/java/dev/orbitingslice/crimson_moon/datagen/worldgen/CrimsonMoonSurfaceRules.java) | Per-biome surface blocks (`SurfaceRules.RuleSource`, keyed by `isBiome(...)`). |
| [CrimsonMoonFeatures.java](../src/main/java/dev/orbitingslice/crimson_moon/datagen/worldgen/CrimsonMoonFeatures.java) / [CrimsonMoonPlacedFeatures.java](../src/main/java/dev/orbitingslice/crimson_moon/datagen/worldgen/CrimsonMoonPlacedFeatures.java) | This project's **first** hand-authored custom features (not reused vanilla ones) — the cobbled deepslate boulders. Template to copy for the deferred blackstone ore veins (Section 5). |
| [CrimsonClimateHelper.java](../src/main/java/dev/orbitingslice/crimson_moon/datagen/worldgen/util/CrimsonClimateHelper.java) | Builder wrapping `Climate.parameters(...)`. **Already fixed once this session** — see Section 4's first entry, don't re-break it. |
| [ColorPalettes.java](../src/main/java/dev/orbitingslice/crimson_moon/datagen/worldgen/util/ColorPalettes.java) | All biome tint colors, grouped by biome family. |
| [ModWorldGenProvider.java](../src/main/java/dev/orbitingslice/crimson_moon/datagen/ModWorldGenProvider.java) | The `RegistrySetBuilder` wiring every bootstrap method into datagen. Add new registries (e.g. `Registries.CONFIGURED_CARVER`) here first. |

---

## 3. The core mental model: climate gaps cause "floating biomes"

This is the single most important thing to understand before touching the
climate table. It was rediscovered the hard way, repeatedly, this session.

**How biome selection actually works:** `MultiNoiseBiomeSource` picks the
biome whose 6D parameter box (temp, humidity, continentalness, erosion,
weirdness, depth) is *nearest* to the real sampled values at that point — not
which biome's box *contains* the point. If a real combination of values isn't
close to *any* biome's assigned box, the "least bad" biome still wins by
default, even with a wildly mismatched continentalness. Since terrain height
is driven by continentalness (Section 3.1), a biome selected with a badly
wrong continentalness paints its label at a wildly wrong elevation — that's
"biome floating in the sky with no ground under it," or its opposite,
appearing underground/underwater.

**The fix pattern, used successfully many times this session:** when biome X
keeps appearing somewhere it shouldn't, look for a *narrow* axis on the
biome(s) that legitimately own that territory, and widen it until there's no
gap left for X to win by default. Concrete examples from this session's
commit history:
- Deepslate Peaks kept losing high-continentalness territory to Lava River and
  Crimson Forest because its humidity/erosion/weirdness bands were too narrow
  — widened humidity to full range, erosion to full range, weirdness to
  ±0.50. Fixed both.
- Warped Oasis appeared 30+ blocks underground because Deepslate
  Caves/Frozen Lava Tubes had narrow humidity that didn't reach Oasis's
  0.90–0.98 requirement, so Oasis won "underground, humid" combinations by
  default. Widened both caves biomes' humidity to full range.
- Scorched Delta was nearly unfindable and formed sub-chunk fragments because
  5 axes were narrowed *simultaneously* — the intersection of 5 independent
  noise fields is small even if each axis alone looks reasonable. Fixed by
  cutting it down to 2 defining axes (temp, erosion) and widening the rest to
  match Crimson Plains' own range.

**The opposite lesson, also learned the hard way:** a biome can be *too*
narrow on **too many axes at once** and become nearly unfindable even with no
competing biome nearby — same mechanism, self-inflicted. Crimson Jungle was
originally placed in the most extreme weirdness tail *and* had tight
temp/humidity — compounding narrowness made it vanish. Fixed by widening
temp/humidity and moving it to a less extreme (bigger) weirdness slice.

**Unclaimed climate space is dangerous *and* useful.** Any gap in coverage is
a landing zone for the wrong biome. This session's 6 newest biomes (Grove,
Taiga, Jungle, Mangrove, Deepslate Shore, Meadow) were deliberately placed
*in* previously-unclaimed continentalness/weirdness territory — partly for
their own sake, partly because doing so closes gaps other biomes were
bleeding into. When adding a new biome, check whether it can double as a
gap-filler before picking arbitrary climate values.

### 3.1 How terrain height actually works now

In `CrimsonMoonNoiseGeneratorSettings.java`:
- `continents`/`erosion` are **clamped to ±1.0** — raw `NormalNoise` can spike
  past that in its tails, and unclamped input let the height spline
  extrapolate absurdly (this literally put biomes at y200+ early in the
  session — confirmed root cause, not speculation).
- `heightSpline` — a `CubicSpline` keyed on `continents`, mapping
  continentalness → base elevation in blocks (currently -1.0→32 up to
  1.0→230, with intermediate points; see the file for the full breakpoint
  table).
- `amplitudeSpline` — a second `CubicSpline` keyed on `erosion`, mapping low
  erosion → large amplitude (rugged) and high erosion → small amplitude
  (flat).
- `effectiveHeight = heightSpline + amplitudeSpline * CRIMSON_BASE_NOISE` —
  the actual noisy terrain height.
- `final_density` (the real terrain shape) = `(effectiveHeight − y) / 20`,
  solid below the surface, air above.
- `depth` (the climate axis used for biome selection) = `(effectiveHeight − y)
  / 50`, clamped to ±3. **This must use `effectiveHeight`, not the smooth
  `heightSpline`** — an earlier version used the smooth spline here, which let
  depth (biome selection) and the real noisy terrain surface disagree by up to
  the local noise amplitude, painting biome labels at heights with no solid
  ground underneath. This was a distinct bug from the climate-gap issue above,
  found and fixed the same session.
- Depth semantics: ~0 = at the local generated surface, positive = underground,
  negative = above-surface/sky. Every surface biome in the climate table uses
  roughly `-0.15` to `0.15`; the two underground-only biomes (Deepslate Caves,
  Frozen Lava Tubes) use `0.4`–`2.0`.

---

## 4. Other confirmed bugs fixed this session (worth knowing about even though closed)

- **`CrimsonClimateHelper.build()` had `depth` and `weirdness` swapped** when
  calling `Climate.parameters(...)` — vanilla's real `Climate.ParameterPoint`
  record order is `(temperature, humidity, continentalness, erosion, depth,
  weirdness, offset)`. This was a pre-existing bug (predates this session)
  that silently mislabeled every biome's climate point the entire project's
  history. Fixed; if this ever looks wrong again, the field order is the
  first thing to check.
- **A `PlacedFeature` can only appear once per biome's generation settings.**
  Listing the same key twice (attempted as a manual "boost density" trick for
  Warped Oasis) causes a **hard crash** at chunk-generation time: `Feature
  order cycle found` (vanilla's cross-biome `FeatureSorter` can't resolve a
  feature that must precede itself). Don't do this — if you want more of a
  feature, you need a second `PlacedFeature` wrapping the same
  `ConfiguredFeature` with different placement modifiers (this is exactly
  what `CrimsonMoonPlacedFeatures.java` does for the two cobbled-deepslate
  boulder rates).
- **Feature registration order affects what actually places.** Vanilla places
  features within a step in the order you register them; an earlier feature
  can "use up" ground an later one needed (e.g. a 2×2 clear patch for Dark
  Oak/mega spruce). Register rare/large features *before* dense/common ones
  in the same biome. (Applied to Taiga and Warped Oasis this session — **see
  Section 5, this did not fully solve the problem, it's still open.**)
- **`WARPED_FOREST_VEGETATION`/`CRIMSON_FOREST_VEGETATION`-style bundles are
  ground-cover only** (roots/sprouts/wart block) — the actual huge fungus
  trees are a *separate* placement (`TreePlacements.WARPED_FUNGI` /
  `TreePlacements.CRIMSON_FUNGI`). Easy to forget when copying the pattern to
  a new nether-flavored biome.
- **Birch and spruce leaves don't respond to `foliageColorOverride` at all** —
  vanilla hardcodes their color (`FoliageColor.getBirchColor()` /
  `getEvergreenColor()`), unlike oak/dark oak and most other leaves, which use
  the real per-biome tintable map. Not fixable via biome data; would need a
  resource-pack retexture of `birch_leaves.png`/`spruce_leaves.png` with the
  tint baked in. This is why Dark Oak was specifically pulled into Crimson
  Taiga (its leaves *do* tint) — see Section 5 for why that isn't confirmed
  working yet either.
- **Vanilla ore veins (`addDefaultOres` etc.) target `stone`/`deepslate`, not
  `blackstone`.** Since Blackstone is this dimension's dominant near-surface
  "stone," most biomes' ore veins silently fail to place — only worked
  reliably where Deepslate itself was already at/near the surface. **Not
  fixed — deferred, see Section 5.**
- **The old absolute-Y blackstone/deepslate surface cutoff** (blackstone
  everywhere above y=15) made sense when terrain was a fixed y47–87 band, but
  once terrain started varying with continentalness (Section 3.1), it made
  blackstone dominate almost every exposed cliff face across the whole
  dimension, including Deepslate Peaks' summit. Fixed by switching to
  relative depth-from-surface (`DEEP_UNDER_FLOOR`/`VERY_DEEP_UNDER_FLOOR`
  instead of `yBlockCheck`).

---

## 5. Open / parked issues — do not assume these are fixed

- **Dark Oak / giant taiga vegetation (mega spruce, huge mushrooms) never
  confirmed to naturally generate**, in Crimson Taiga *or* Warped Oasis,
  despite two rounds of fixes (ground-compatibility patches, then
  feature-registration reordering). The reordering fix was a reasonable
  hypothesis (features register-order-race for ground) but **the user
  re-tested after the reorder and still found none** — so that hypothesis is
  not confirmed and the real cause is still unknown. Explicitly parked at the
  user's request. If picking this back up: don't just re-try the same two
  fixes. Consider verifying with `/locate` or creepy-close exploration whether
  the feature is placing *at all* anywhere, checking whether `DARK_OAK_CHECKED`
  specifically requires something the "checked" placement type implies
  (the persistent `missing BiomeFilter.biome()` warning on this exact feature,
  present since it was first added, was assumed cosmetic — reconsider that
  assumption if this stays unresolved), or building a tiny standalone test
  biome with much higher density to confirm the feature *can* place at all in
  this dimension before debugging the low-rate case.
- **Lava River never confirmed to generate above ground/at surface level, at
  all, across the whole session** despite four distinct rounds of climate
  fixes (clamping, depth/height sync, widening every neighboring biome's
  competing axis). Explicitly tabled at the user's request — diminishing
  returns on blind climate tuning without the ability to directly inspect
  live sampled values. If picking this back up, the productive next step is
  probably in-game diagnostics (F3 screen climate readout at a "floating lava
  river" location, if such a debug view exists / is addable) rather than more
  speculative widening.
- **Deepslate Peaks still occasionally appears at low elevation** (water's
  edge, small hills) even after the widening in Section 3. This is an
  accepted, understood side effect: full-range humidity/erosion give Peaks a
  competitive edge in close races *outside* its own continentalness territory
  too, not just within it — there's no way to have both "never lose own
  territory" and "never win others'" simultaneously without per-axis tradeoffs.
  Deepslate Shore and Crimson Meadow were added specifically to soak up some
  of this bleed by giving that space something legitimate. The user has said
  further tweaking isn't worth it — leave as-is.
- **Crimson River and Lava River have no real carved channel shape** — they're
  just climate regions that happen to compute low/flat terrain, not an actual
  winding depression. Works well enough for Crimson River (its
  continentalness keeps it consistently below sea level, so it's reliably
  wet) but doesn't produce a "river" shape. Would need a dedicated
  carving/depression term in the density function, not just climate
  placement. Not started.
- **Deepslate Shore is often underwater/at low elevation** — the user's own
  conclusion (and one to trust) is that this is expected, not a bug: its
  climate slot exists specifically to fill a gap between Warm Ocean and
  Crimson River/Beach, which is inherently a low/underwater elevation band.
  Left as-is by choice; a light option if it ever needs revisiting is nudging
  its continentalness ceiling up slightly for more dry frontage.

---

## 6. Explicitly deferred (not attempted, tracked as future work)

In rough priority order per the user's stated interest:

1. **Cave carvers — the user wants to start here next session.** Currently
   *zero* `ConfiguredWorldCarver`s are wired into any biome (`carvers` is
   passed into every `BiomeGenerationSettings.Builder` but nothing ever calls
   `.addCarver(...)`). This is a completely separate system from the noise
   terrain shape — needs a new `Registries.CONFIGURED_CARVER` bootstrap class
   (following the exact same pattern as `CrimsonMoonFeatures.java` for
   configured features, plus wiring into `ModWorldGenProvider.java`), then
   `.addCarver(GenerationStep.Carving.AIR/LIQUID, ...)` calls per biome. Worth
   deciding early: reuse vanilla's cave/canyon carvers as-is (fast, but tuned
   for vanilla's Y range and terrain, which no longer matches ours — Section
   3.1 terrain now spans y32–230, wildly different from vanilla's typical
   ~y-64–320 with sea level 63 assumptions baked into vanilla's carver noise
   thresholds) vs. authoring custom carver configs tuned to this dimension's
   actual elevation profile. The user specifically wants this to connect
   Warped Oasis (confirmed generating underground, not on the surface) with
   the surface and other cave biomes — that's a good design constraint to
   design the carver network around from the start, not retrofit later.
2. **Blackstone-compatible ore veins** — new custom `configured_feature`s
   targeting blackstone (vanilla's target stone/deepslate tags don't include
   it). Directly copy the pattern in `CrimsonMoonFeatures.java`/
   `CrimsonMoonPlacedFeatures.java` (that precedent exists specifically to make
   this easier next time).
3. **Warped Oasis's guaranteed central lake** — currently just a small
   `SPRING_WATER` feature, not the ~8–16 block basin from the original design
   doc. Needs real terrain-shape work (a per-biome depression), not just
   climate placement.
4. **River channel carving** (see Section 5).
5. **Villages in Desert/Badlands** — deliberately left alone. The user
   explicitly wants to *keep* them for now (a source of food/resources not
   otherwise available in this dimension), despite the reused-vanilla-biome
   coloring not matching the theme. If revisited: disabling requires either
   switching Desert/Badlands from directly-reused vanilla `Biome` objects to
   custom copies (same pattern as Grove/Taiga/etc. — also lets you re-theme
   the village) since structures key off the biome's tag membership, not
   anything dimension-specific.
6. **Meadow analogue** — done this session (Section 7). Listed here only to
   note it's *not* still open.
7. **Birch/spruce leaf recoloring** — resource-pack task (texture retint), not
   a datagen task. Not started, no plan made.

---

## 7. Full current biome roster (15 total)

All climate ranges below are exact, current, and load-bearing — copy from
`CrimsonMoonDimension.java` directly if you need to double check, don't trust
transcription errors here over the source.

**Custom biomes (own `Biome` object, full color/vegetation control):**
Crimson Plains, Warped Oasis (micro-biome), Scorched Delta, Crimson River,
Lava River (tabled, see Section 5), Frozen Lava Tubes (underground), Crimson
Beach, Deepslate Caves (underground), Deepslate Peaks, Crimson Grove
(recolored Forest), Crimson Taiga (recolored Taiga), Crimson Jungle
(recolored Jungle), Crimson Mangrove (recolored Mangrove Swamp), Deepslate
Shore (new, rocky coastal), Crimson Meadow (new, Plains→Peaks foothills).

**Vanilla biomes reused wholesale** (real vanilla `Biome` Holder pulled from
the registry — inherits vanilla's own vegetation/mobs/structures for free,
only the surface *blocks* are overridden via `CrimsonMoonSurfaceRules`, not
the tint colors): `minecraft:warm_ocean`, `minecraft:badlands`,
`minecraft:desert`, `minecraft:crimson_forest` (the nether one, reskinned as
a nylium/netherrack Plains-variant, distinct from the new "Crimson Grove"
custom forest biome — don't confuse the two names).

For the exact per-biome vegetation/mob lists, read `CrimsonMoonBiomes.java`
directly — it's well-commented per biome and would just be duplicated here.
