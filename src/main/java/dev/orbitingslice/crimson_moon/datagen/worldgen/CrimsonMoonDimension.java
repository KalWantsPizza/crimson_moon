package dev.orbitingslice.crimson_moon.datagen.worldgen;

import dev.orbitingslice.crimson_moon.datagen.worldgen.util.CrimsonClimateHelper;

import dev.orbitingslice.crimson_moon.CrimsonMoon;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import static dev.orbitingslice.crimson_moon.CrimsonMoon.resource;

/**
 * Registers the Crimson Moon dimension for DataGen.
 * Compatible with Minecraft 1.21.1 (NoiseGeneratorSettings schema).
 */
public class CrimsonMoonDimension {

    public static final ResourceKey<LevelStem> CRIMSON_DIMENSION =
            ResourceKey.create(
                    Registries.LEVEL_STEM,
                    resource( "crimson_moon")
            );

    public static void bootstrap(BootstrapContext<LevelStem> context) {

        // === Lookups ===
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        // === Dimension Type & Noise Settings ===
        Holder<DimensionType> dimensionType = dimTypes.getOrThrow(CrimsonMoonDimensionType.CRIMSON_TYPE);
        Holder<NoiseGeneratorSettings> noiseSettings = noiseGenSettings.getOrThrow(
                ResourceKey.create(
                        Registries.NOISE_SETTINGS,
                        resource( "crimson_moon")
                )
        );
        // === Biome Source (inline definition for DataGen, using CrimsonMoon.resource helper) ===
        HolderGetter<net.minecraft.world.level.biome.Biome> biomes = context.lookup(Registries.BIOME);

        // === Biome Source using Climate Helper ===
        var pairs = java.util.List.of(
                // Dominant, mid-height building ground (broad box)
                CrimsonClimateHelper.biomePair(biomes, "crimson_plains")
                        .temp(0.7F, 1.3F)
                        .humidity(0.20F, 0.80F)
                        .continentalness(0.05F, 0.80F)  // raised floor so plains stays at/above sea level
                        .erosion(0.20F, 0.80F)
                        .weirdness(-0.40F, 0.40F)
                        .depth(-0.15F, 0.15F)   // surface-hugging, depth now means "distance below local ground"
                        .build(),

                // MICRO biome: make it tiny by shrinking every range
                CrimsonClimateHelper.biomePair(biomes, "warped_oasis")
                        .temp(0.55F, 0.70F)
                        .humidity(0.9F, 0.98F)     // requires very high humidity pocket
                        .continentalness(0.25F, 0.35F)
                        .erosion(0.10F, 0.25F)      // gentle basin
                        .weirdness(-0.03F, 0.03F)
                        .depth(-0.10F, 0.25F)        // shallow surface dips/basins
                        .build(),

                // Cooling flats between rivers and lava; slightly below plains
                // NOTE: still forming sub-chunk-sized, half-underwater specks after the
                // first widening pass. Root cause: narrowing temp+humidity+continentalness+
                // erosion+weirdness ALL AT ONCE means the resulting territory is the
                // *intersection* of 5 independent noise fields -- even with each individually
                // reasonable, the overlap is inherently small and fragmented. Now down to just
                // 2 defining axes (very hot + flat/hardened); humidity/continentalness/weirdness
                // widened to essentially match Crimson Plains' own range so this reads as a
                // "hot flat variant of normal territory" instead of a 5-way-constrained pocket.
                // Continentalness floor also raised to match Plains'/Desert's proven
                // above-sea-level value.
                CrimsonClimateHelper.biomePair(biomes, "scorched_delta")
                        .temp(1.15F, 1.60F)
                        .humidity(0.00F, 0.60F)
                        .continentalness(0.05F, 0.50F)
                        .erosion(0.45F, 0.85F)
                        .weirdness(-0.40F, 0.40F)
                        .depth(-0.15F, 0.15F)        // surface-hugging
                        .build(),

                // Water river system: low, humid, eroded channels cutting through plains
                CrimsonClimateHelper.biomePair(biomes, "crimson_river")
                        .temp(0.50F, 0.90F)
                        .humidity(0.60F, 1.00F)
                        .continentalness(-0.45F, -0.15F)  // pushed below sea level so it's actually wet
                        .erosion(0.50F, 1.00F)
                        .weirdness(-0.20F, 1.00F)  // ceiling extended to fully cover lava_river's exclusive
                                                    // 0.50-1.00 weirdness tail -- their continentalness ranges
                                                    // overlap, and lava_river was still winning at real
                                                    // continentalness values river should dominate whenever
                                                    // weirdness landed above river's old 0.20 ceiling
                        .depth(-0.20F, 0.15F)  // surface-hugging
                        .build(),

                // LAVA river network: rare, deep, hot channels
                CrimsonClimateHelper.biomePair(biomes, "lava_river")
                        .temp(1.00F, 1.60F)
                        .humidity(0.00F, 0.40F)
                        .continentalness(-0.40F, 0.10F)
                        .erosion(0.70F, 1.00F)  // very eroded
                        .weirdness(0.50F, 1.00F)
                        .depth(-0.20F, 0.20F)  // surface-hugging; distinguished from deepslate_peaks by
                                                // continentalness now, not by an artificial depth conflict
                        .build(),

                // Deep subterranean bands -- underground-only, real positive depth now that
                // depth means "distance below the locally generated surface."
                CrimsonClimateHelper.biomePair(biomes, "frozen_lava_tubes")
                        .temp(0.00F, 0.30F)
                        .humidity(0.00F, 1.00F)  // widened -- narrow humidity here left a gap at very-high
                                                  // humidity + underground depth that warped_oasis (humidity
                                                  // 0.90-0.98) was winning by default, painting its label deep
                                                  // underground instead of at the surface it's meant for
                        .continentalness(-0.60F, -0.30F)
                        .erosion(0.60F, 0.90F)
                        .weirdness(-0.10F, 0.10F)
                        .depth(1.00F, 2.00F)
                        .build(),

                // Shoreline near sea level, edges of landmass -- narrowed further (humidity,
                // erosion, continentalness) so it reads as a thin coastal fringe instead of an
                // expansive red-sand plain; weirdness ceiling extended to fully cover
                // lava_river's exclusive tail in the small continentalness range they share.
                CrimsonClimateHelper.biomePair(biomes, "crimson_beach")
                        .temp(0.60F, 1.10F)
                        .humidity(0.40F, 0.75F)
                        .continentalness(-0.03F, 0.08F)
                        .erosion(0.30F, 0.55F)
                        .weirdness(0.10F, 1.00F)
                        .depth(-0.15F, 0.15F)  // surface-hugging
                        .build(),

                // Mid-deep caves (subsurface) -- underground-only, real positive depth.
                CrimsonClimateHelper.biomePair(biomes, "deepslate_caves")
                        .temp(0.50F, 0.90F)
                        .humidity(0.00F, 1.00F)  // widened for the same reason as frozen_lava_tubes below
                        .continentalness(-0.60F, -0.30F)
                        .erosion(0.50F, 0.80F)
                        .weirdness(-0.10F, 0.10F)
                        .depth(0.40F, 1.20F)
                        .build(),

                // TALLEST by far: high continentalness + very low erosion. Surface biome --
                // its height comes from continentalness now (via the terrain height spline),
                // not from an artificially negative depth.
                CrimsonClimateHelper.biomePair(biomes, "deepslate_peaks")
                        .temp(0.80F, 1.20F)
                        .humidity(0.00F, 1.00F)  // full range -- narrow humidity left gaps at this
                                                  // continentalness that other biomes won by default
                        .continentalness(0.65F, 1.00F) // far inland ridges, widened for a larger footprint
                        .erosion(0.00F, 1.00F)   // widened to full range -- 0.00-0.60 still left a gap at
                                                  // erosion 0.60-1.00 that lava_river (erosion 0.70-1.00)
                                                  // was winning by default at this continentalness, which is
                                                  // exactly why it kept floating even after prior fixes
                        .weirdness(-0.50F, 0.50F) // widened -- 0.20 ceiling left a gap at higher weirdness
                                                   // that crimson_forest (weirdness up to 0.45) was winning
                                                   // by default; this fully covers it and most of lava_river's
                                                   // weirdness tail too, leaving continentalness (correctly)
                                                   // as the deciding axis in the remaining sliver
                        .depth(-0.15F, 0.15F)          // surface-hugging
                        .build(),

                // --- Vanilla biomes, integrated per dimension design doc adjacency notes ---
                // Provisional bands: these carve out edges/pockets of the existing climate space
                // rather than a whole new "column," to keep them feeling like natural neighbors
                // of the custom biomes. All four need in-game verification and retuning, same as
                // the rest of the climate space -- this is a first pass, not a final answer.

                // Warm Ocean: sits just past Crimson Beach's coastline, pushed to more negative
                // continentalness (true "ocean" band) and slightly deeper than the beach's depth.
                CrimsonClimateHelper.biomePair(biomes, "minecraft:warm_ocean")
                        .temp(0.60F, 1.10F)
                        .humidity(0.30F, 1.00F)
                        .continentalness(-1.00F, -0.55F)  // beyond crimson_beach's -0.50..0.00
                        .erosion(0.20F, 1.00F)
                        .weirdness(-0.20F, 0.40F)
                        .depth(-0.15F, 0.15F)  // surface-hugging; its own continentalness band already
                                                // computes a below-sea-level height via the terrain spline
                        .build(),

                // Badlands: hot + very dry + low erosion (preserves tall mesa-style plateaus).
                // Sits at a different weirdness/continentalness slice than Scorched Delta so the
                // two dry-hot biomes don't directly compete for the same climate niche.
                CrimsonClimateHelper.biomePair(biomes, "minecraft:badlands")
                        .temp(1.00F, 1.50F)
                        .humidity(0.00F, 0.20F)
                        .continentalness(0.30F, 0.70F)
                        .erosion(0.00F, 0.20F)   // low erosion -> tall plateaus
                        .weirdness(0.10F, 0.50F)
                        .depth(-0.20F, 0.15F)  // surface-hugging
                        .build(),

                // Desert: drier than Crimson Plains, milder than Badlands. Also serves as one of
                // Warped Oasis's documented host biomes.
                CrimsonClimateHelper.biomePair(biomes, "minecraft:desert")
                        .temp(0.80F, 1.15F)  // ceiling lowered -- was overlapping almost all of scorched_delta's
                                              // niche (temp/humidity/continentalness/weirdness all shared), leaving
                                              // it almost no exclusive territory to ever be selected in
                        .humidity(0.00F, 0.20F)
                        .continentalness(0.05F, 0.50F)  // raised floor, was dipping below sea level
                        .erosion(0.30F, 0.70F)
                        .weirdness(-0.30F, 0.10F)
                        .depth(-0.15F, 0.15F)
                        .build(),

                // Crimson Forest: a rarer, denser fungal-jungle variant carved out of Crimson
                // Plains' own climate envelope via a distinct weirdness/humidity slice -- same
                // technique vanilla uses for e.g. Sunflower Plains as a Plains variant. Kept as
                // pure nylium/netherrack (no grass), so it stays visually distinct from the now
                // grass-dominant Crimson Plains rather than competing with it.
                CrimsonClimateHelper.biomePair(biomes, "minecraft:crimson_forest")
                        .temp(0.70F, 1.30F)
                        .humidity(0.55F, 0.85F)
                        .continentalness(-0.10F, 0.60F)
                        .erosion(0.25F, 0.65F)
                        .weirdness(0.15F, 0.45F)
                        .depth(-0.20F, 0.15F)
                        .build(),

                // --- Recolored forest-type biomes, using dense vanilla tree placements.
                // Placed in weirdness -1.00..-0.40, previously entirely unclaimed territory
                // (everything else occupies -0.40..1.00), so these claim new ground instead
                // of competing with anything already tuned this session.
                //
                // NOTE: Jungle/Mangrove were originally given the narrower, more extreme tail
                // (-1.00..-0.75) while Grove/Taiga got the wider, less-extreme slice
                // (-0.75..-0.40) -- backwards. Jungle/Mangrove already have the tightest
                // temp+humidity requirements (hot+very humid), so cramming them into the most
                // extreme weirdness tail on top of that made them nearly unfindable (confirmed
                // by /locate biome coming up empty for Jungle). Swapped: Jungle/Mangrove now get
                // the bigger slice to compensate for their tighter other axes; Grove/Taiga keep
                // a smaller slice but gain a bit more temp/humidity room to compensate in turn.

                // Crimson Grove (Forest): temperate, separated from Taiga by temperature only.
                CrimsonClimateHelper.biomePair(biomes, "crimson_grove")
                        .temp(0.55F, 1.05F)
                        .humidity(0.40F, 0.78F)
                        .continentalness(0.10F, 0.70F)
                        .erosion(0.20F, 0.70F)
                        .weirdness(-0.55F, -0.40F)
                        .depth(-0.15F, 0.15F)
                        .build(),

                // Crimson Taiga: cooler side of the range, same weirdness slice as Grove.
                CrimsonClimateHelper.biomePair(biomes, "crimson_taiga")
                        .temp(0.15F, 0.60F)
                        .humidity(0.25F, 0.68F)
                        .continentalness(0.10F, 0.70F)
                        .erosion(0.20F, 0.70F)
                        .weirdness(-0.55F, -0.40F)
                        .depth(-0.15F, 0.15F)
                        .build(),

                // Crimson Jungle: hot + very humid -- widened temp/humidity too, not just
                // weirdness, since 3 simultaneously-narrow axes was the same mistake that made
                // Scorched Delta unfindable earlier.
                CrimsonClimateHelper.biomePair(biomes, "crimson_jungle")
                        .temp(0.85F, 1.30F)
                        .humidity(0.65F, 0.98F)
                        .continentalness(0.10F, 0.70F)
                        .erosion(0.20F, 0.70F)
                        .weirdness(-1.00F, -0.55F)
                        .depth(-0.15F, 0.15F)
                        .build(),

                // Crimson Mangrove: coastal counterpart to Jungle -- same weirdness slice,
                // separated by continentalness (coastal, overlapping Beach/River on purpose).
                CrimsonClimateHelper.biomePair(biomes, "crimson_mangrove")
                        .temp(0.65F, 1.10F)
                        .humidity(0.70F, 1.00F)
                        .continentalness(-0.20F, 0.15F)
                        .erosion(0.30F, 0.70F)
                        .weirdness(-1.00F, -0.55F)
                        .depth(-0.15F, 0.15F)
                        .build(),

                // Deepslate Shore: fills the two small gaps between Warm Ocean's ceiling
                // (-0.55) / Crimson River's floor (-0.45), and River's ceiling (-0.15) /
                // Beach's floor (-0.03) -- territory Deepslate Peaks was bleeding into
                // after being widened to stop losing ground to Lava River/Crimson Forest.
                // Differentiated from River (continentalness overlaps) by humidity: dry
                // and rocky here, River needs 0.60+.
                CrimsonClimateHelper.biomePair(biomes, "deepslate_shore")
                        .temp(0.30F, 0.90F)
                        .humidity(0.10F, 0.50F)
                        .continentalness(-0.55F, -0.03F)
                        .erosion(0.60F, 1.00F)
                        .weirdness(-0.40F, 0.30F)
                        .depth(-0.15F, 0.15F)
                        .build(),

                // Crimson Meadow: cooler Plains-to-Peaks foothills transition. Overlaps
                // Plains/Badlands/Crimson Forest on continentalness but is cleanly
                // separated by temperature alone (cooler, no overlap with their 0.70+
                // floors). Doesn't touch Grove/Taiga's exclusive weirdness slice.
                CrimsonClimateHelper.biomePair(biomes, "crimson_meadow")
                        .temp(0.30F, 0.65F)
                        .humidity(0.40F, 0.75F)
                        .continentalness(0.45F, 0.80F)
                        .erosion(0.20F, 0.60F)
                        .weirdness(-0.30F, 0.30F)
                        .depth(-0.15F, 0.15F)
                        .build()
        );

        var parameterList = CrimsonClimateHelper.buildList(pairs);

        MultiNoiseBiomeSource biomeSource = MultiNoiseBiomeSource.createFromList(parameterList);
        // === Chunk Generator ===
        NoiseBasedChunkGenerator generator = new NoiseBasedChunkGenerator(biomeSource, noiseSettings);

        // === Register Dimension ===
        LevelStem stem = new LevelStem(dimensionType, generator);
        context.register(CRIMSON_DIMENSION, stem);
    }
}