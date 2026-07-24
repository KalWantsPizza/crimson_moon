package dev.orbitingslice.crimson_moon.datagen.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static dev.orbitingslice.crimson_moon.CrimsonMoon.resource;

/**
 * Custom placed features. Two rates of the same cobbled deepslate boulder
 * (CrimsonMoonFeatures.COBBLED_DEEPSLATE_BOULDER) -- more common on Deepslate Shore,
 * sparser on Crimson Meadow, same pattern vanilla uses for its own forest_rock.
 */
public class CrimsonMoonPlacedFeatures {

    public static final ResourceKey<PlacedFeature> COBBLED_DEEPSLATE_BOULDER_SHORE =
            ResourceKey.create(Registries.PLACED_FEATURE, resource("cobbled_deepslate_boulder_shore"));
    public static final ResourceKey<PlacedFeature> COBBLED_DEEPSLATE_BOULDER_MEADOW =
            ResourceKey.create(Registries.PLACED_FEATURE, resource("cobbled_deepslate_boulder_meadow"));

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> boulder = configuredFeatures.getOrThrow(CrimsonMoonFeatures.COBBLED_DEEPSLATE_BOULDER);

        PlacementUtils.register(
                context,
                COBBLED_DEEPSLATE_BOULDER_SHORE,
                boulder,
                CountPlacement.of(3),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                COBBLED_DEEPSLATE_BOULDER_MEADOW,
                boulder,
                CountPlacement.of(1),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
    }
}
