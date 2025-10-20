package dev.orbitingslice.crimson_moon.datagen.worldgen;

import dev.orbitingslice.crimson_moon.datagen.worldgen.util.CrimsonClimateHelper;

import dev.orbitingslice.crimson_moon.CrimsonMoon;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

/**
 * Registers the Crimson Moon dimension for DataGen.
 * Compatible with Minecraft 1.21.1 (NoiseGeneratorSettings schema).
 */
public class CrimsonMoonDimension {

    public static final ResourceKey<LevelStem> CRIMSON_DIMENSION =
            ResourceKey.create(
                    Registries.LEVEL_STEM,
                    ResourceLocation.fromNamespaceAndPath("crimson_moon", "crimson_moon")
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
                        ResourceLocation.fromNamespaceAndPath("crimson_moon", "crimson_moon")
                )
        );
        // === Biome Source (inline definition for DataGen, using CrimsonMoon.resource helper) ===
        HolderGetter<net.minecraft.world.level.biome.Biome> biomes = context.lookup(Registries.BIOME);

        // === Biome Source using Climate Helper ===
        var pairs = java.util.List.of(
            CrimsonClimateHelper.biomePair(biomes, "crimson_plains")
                .temp(0.9F, 1.3F).humidity(0.3F, 0.6F)
                .continentalness(0.2F, 0.6F).erosion(0.5F, 0.8F)
                .weirdness(-0.2F, 0.3F).depth(-0.2F, 0.2F)
                .build(),

            CrimsonClimateHelper.biomePair(biomes, "warped_oasis")
                .temp(0.5F, 0.9F).humidity(0.5F, 0.9F)
                .continentalness(0.3F, 0.5F).erosion(0.2F, 0.4F)
                .weirdness(0.0F, 0.3F).depth(0.3F, 0.7F)
                .build(),

            CrimsonClimateHelper.biomePair(biomes, "scorched_delta")
                .temp(1.2F, 1.6F).humidity(0.0F, 0.3F)
                .continentalness(0.0F, 0.3F).erosion(0.7F, 1.0F)
                .weirdness(-0.1F, 0.2F).depth(-0.8F, -0.4F)
                .build(),

            CrimsonClimateHelper.biomePair(biomes, "crimson_river")
                .temp(0.6F, 0.9F).humidity(0.1F, 0.4F)
                .continentalness(-0.2F, 0.2F).erosion(0.6F, 0.8F)
                .weirdness(-0.1F, 0.1F).depth(0.8F, 1.0F)
                .build(),

            // === Phase 2 Biomes ===
            CrimsonClimateHelper.biomePair(biomes, "lava_river")
                .temp(1.1F, 1.5F).humidity(0.0F, 0.3F)
                .continentalness(-0.2F, 0.2F).erosion(0.5F, 0.8F)
                .weirdness(-0.1F, 0.1F).depth(0.8F, 1.0F)
                .build(),

            CrimsonClimateHelper.biomePair(biomes, "frozen_lava_tubes")
                .temp(0.0F, 0.3F).humidity(0.2F, 0.5F)
                .continentalness(-0.5F, -0.2F).erosion(0.6F, 0.9F)
                .weirdness(-0.1F, 0.1F).depth(1.0F, 1.3F)
                .build(),

            CrimsonClimateHelper.biomePair(biomes, "crimson_beach")
                .temp(0.7F, 1.1F).humidity(0.3F, 0.7F)
                .continentalness(0.0F, 0.3F).erosion(0.4F, 0.6F)
                .weirdness(-0.1F, 0.1F).depth(0.2F, 0.5F)
                .build(),

            // === Phase 3 Biomes ===
            CrimsonClimateHelper.biomePair(biomes, "deepslate_caves")
                .temp(0.5F, 0.9F).humidity(0.4F, 0.7F)
                .continentalness(-0.6F, -0.3F).erosion(0.5F, 0.8F)
                .weirdness(-0.1F, 0.1F).depth(1.2F, 1.5F)
                .build(),

            CrimsonClimateHelper.biomePair(biomes, "deepslate_peaks")
                .temp(0.8F, 1.2F).humidity(0.2F, 0.5F)
                .continentalness(0.6F, 0.9F).erosion(0.3F, 0.5F)
                .weirdness(-0.2F, 0.2F).depth(-0.9F, -0.6F)
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