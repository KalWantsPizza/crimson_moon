package dev.orbitingslice.crimson_moon.datagen.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

import static dev.orbitingslice.crimson_moon.CrimsonMoon.resource;

/**
 * Custom configured features. Same mechanism vanilla uses for the mossy stone
 * boulders (Feature.FOREST_ROCK + BlockStateConfiguration), just a different block --
 * gives Deepslate Shore/Meadow discrete cobbled deepslate boulder formations instead
 * of a broad surface-level block swap.
 */
public class CrimsonMoonFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> COBBLED_DEEPSLATE_BOULDER =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, resource("cobbled_deepslate_boulder"));

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(
                context,
                COBBLED_DEEPSLATE_BOULDER,
                Feature.FOREST_ROCK,
                new BlockStateConfiguration(Blocks.COBBLED_DEEPSLATE.defaultBlockState())
        );
    }
}
