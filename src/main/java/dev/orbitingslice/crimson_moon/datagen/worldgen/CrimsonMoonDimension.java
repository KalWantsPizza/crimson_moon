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
                        .continentalness(-0.20F, 0.80F)
                        .erosion(0.20F, 0.80F)
                        .weirdness(-0.40F, 0.40F)
                        .depth(-0.25F, 0.20F)   // around mid-height
                        .build(),

                // MICRO biome: make it tiny by shrinking every range
                CrimsonClimateHelper.biomePair(biomes, "warped_oasis")
                        .temp(0.55F, 0.70F)
                        .humidity(0.9F, 0.98F)     // requires very high humidity pocket
                        .continentalness(0.25F, 0.35F)
                        .erosion(0.10F, 0.25F)      // gentle basin
                        .weirdness(-0.03F, 0.03F)
                        .depth(0.10F, 0.30F)        // shallow surface dips/basins
                        .build(),

                // Cooling flats between rivers and lava; slightly below plains
                CrimsonClimateHelper.biomePair(biomes, "scorched_delta")
                        .temp(1.20F, 1.60F)
                        .humidity(0.00F, 0.30F)
                        .continentalness(0.00F, 0.30F)
                        .erosion(0.55F, 0.80F)
                        .weirdness(-0.10F, 0.10F)
                        .depth(0.55F, 0.85F)        // sits lower than plains/ridges
                        .build(),

                // Water river system: low, humid, eroded channels cutting through plains
                CrimsonClimateHelper.biomePair(biomes, "crimson_river")
                        .temp(0.50F, 0.90F)
                        .humidity(0.60F, 1.00F)
                        .continentalness(-0.30F, 0.20F)
                        .erosion(0.50F, 1.00F)
                        .weirdness(-0.20F, 0.20F)
                        .depth(-0.70F, -0.30F)  // lower than plains
                        .build(),

                // LAVA river network: rare, deep, hot channels
                // NOTE: depth range currently overlaps/exceeds deepslate_peaks' "tallest" range below.
                // See flagged inconsistency in chat -- verify in-game and retune if lava rivers
                // are generating at the wrong elevation band.
                CrimsonClimateHelper.biomePair(biomes, "lava_river")
                        .temp(1.00F, 1.60F)
                        .humidity(0.00F, 0.40F)
                        .continentalness(-0.40F, 0.10F)
                        .erosion(0.70F, 1.00F)  // very eroded
                        .weirdness(0.50F, 1.00F)
                        .depth(-1.00F, -0.70F)  // **even lower** than crimson_river
                        .build(),

                // Deep subterranean bands (keep very high depth)
                CrimsonClimateHelper.biomePair(biomes, "frozen_lava_tubes")
                        .temp(0.00F, 0.30F)
                        .humidity(0.20F, 0.50F)
                        .continentalness(-0.60F, -0.30F)
                        .erosion(0.60F, 0.90F)
                        .weirdness(-0.10F, 0.10F)
                        .depth(1.00F, 1.30F)
                        .build(),

                // Shoreline near sea level, edges of landmass
                CrimsonClimateHelper.biomePair(biomes, "crimson_beach")
                        .temp(0.60F, 1.10F)
                        .humidity(0.30F, 0.90F)
                        .continentalness(-0.50F, 0.00F) // coastal-ish / edges
                        .erosion(0.20F, 0.60F)
                        .weirdness(0.10F, 0.40F)
                        .depth(-0.20F, 0.10F)
                        .build(),

                // Mid-deep caves (subsurface)
                CrimsonClimateHelper.biomePair(biomes, "deepslate_caves")
                        .temp(0.50F, 0.90F)
                        .humidity(0.40F, 0.70F)
                        .continentalness(-0.60F, -0.30F)
                        .erosion(0.50F, 0.80F)
                        .weirdness(-0.10F, 0.10F)
                        .depth(1.15F, 1.45F)
                        .build(),

                // TALLEST by far: high continentalness + very low erosion + very negative depth
                CrimsonClimateHelper.biomePair(biomes, "deepslate_peaks")
                        .temp(0.80F, 1.20F)
                        .humidity(0.20F, 0.50F)
                        .continentalness(0.80F, 1.00F) // far inland ridges
                        .erosion(0.00F, 0.25F)         // preserves steep relief
                        .weirdness(-0.20F, 0.20F)
                        .depth(-1.00F, -0.60F)         // pushes to the highest elevations
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
                        .depth(-0.40F, -0.10F)             // below sea level, shallower than lava/river channels
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
                        .depth(-0.10F, 0.30F)
                        .build(),

                // Desert: drier than Crimson Plains, milder than Badlands. Also serves as one of
                // Warped Oasis's documented host biomes.
                CrimsonClimateHelper.biomePair(biomes, "minecraft:desert")
                        .temp(0.80F, 1.30F)
                        .humidity(0.00F, 0.20F)
                        .continentalness(-0.10F, 0.50F)
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