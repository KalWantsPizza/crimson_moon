package dev.orbitingslice.crimson_moon.datagen.worldgen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import static dev.orbitingslice.crimson_moon.CrimsonMoon.resource;

/**
 * Registers Crimson Moon's noise generator settings for terrain.
 * Valid for NeoForge 21.1.211 + Parchment 2024-11-17 (MC 1.21.1).
 */
public class CrimsonMoonNoiseGeneratorSettings {

    public static final ResourceKey<NoiseGeneratorSettings> CRIMSON_NOISE =
            ResourceKey.create(
                    Registries.NOISE_SETTINGS,
                    resource("crimson_moon")
            );

    public static void bootstrap(BootstrapContext<NoiseGeneratorSettings> context) {
        HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise.NoiseParameters> noiseParams = context.lookup(Registries.NOISE);

        // Define basic vertical range and cell sizes
        NoiseSettings settings = new NoiseSettings(
                -64, // minY
                384, // height
                1,   // horizontal cell size
                2   // vertical cell size,
        );

        // Custom router combining realistic climate noise with a stable terrain gradient
        NoiseRouter router = new NoiseRouter(
                DensityFunctions.zero(), // barrier
                DensityFunctions.zero(), // fluid_level_floodedness
                DensityFunctions.zero(), // fluid_level_spread
                DensityFunctions.zero(), // lava
                DensityFunctions.noise(noiseParams.getOrThrow(Noises.TEMPERATURE)), // temperature variation
                DensityFunctions.noise(noiseParams.getOrThrow(Noises.VEGETATION)), // vegetation
                DensityFunctions.noise(noiseParams.getOrThrow(Noises.CONTINENTALNESS)), // continents
                DensityFunctions.noise(noiseParams.getOrThrow(Noises.EROSION)), // erosion
                DensityFunctions.yClampedGradient(
                        settings.minY(),
                        settings.minY() + settings.height(),
                        -1.0,
                        1.0
                ), // depth gradient
                DensityFunctions.noise(noiseParams.getOrThrow(Noises.RIDGE)), // ridges
                DensityFunctions.yClampedGradient(
                        settings.minY(),
                        settings.minY() + settings.height(),
                        -1.0,
                        1.0
                ), // initial_density_without_jaggedness
                // final_density (customized)
                DensityFunctions.add(
                        DensityFunctions.mul(
                                DensityFunctions.noise(noiseParams.getOrThrow(Noises.CONTINENTALNESS)),
                                DensityFunctions.noise(noiseParams.getOrThrow(Noises.EROSION))
                        ),
                        DensityFunctions.add(
                                DensityFunctions.yClampedGradient(settings.minY(), settings.minY() + settings.height(), 1.0, -1.0),
                                DensityFunctions.constant(-0.15)
                        )
                ),
                DensityFunctions.zero(), // vein_toggle
                DensityFunctions.zero(), // vein_ridged
                DensityFunctions.zero()  // vein_gap
        );

        context.register(CRIMSON_NOISE, new NoiseGeneratorSettings(
                settings,
                Blocks.STONE.defaultBlockState(),
                Fluids.WATER.defaultFluidState().createLegacyBlock(),
                router,
                CrimsonMoonSurfaceRules.makeRules(),
                new OverworldBiomeBuilder().spawnTarget(),
                63, // sea level
                false, // disable mob generation
                true,  // aquifers
                true,  // ore veins
                false  // legacy random source
        ));
    }
}