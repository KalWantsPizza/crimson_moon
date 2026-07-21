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
        // Look up vanilla density functions
        HolderGetter<DensityFunction> df = context.lookup(Registries.DENSITY_FUNCTION);

        // Look up vanilla noise parameter sets (temperature, vegetation, shift_x, etc.)
        HolderGetter<NormalNoise.NoiseParameters> noiseParams = context.lookup(Registries.NOISE);

        // Climate parameters built directly from noise params / gradients,
// so we don't touch the DENSITY_FUNCTION registry during datagen.

// C ~ continents / inlandness
        DensityFunction continents = DensityFunctions.noise(
                noiseParams.getOrThrow(Noises.CONTINENTALNESS),
                1.0D,
                0.0D
        );

// E ~ erosion / rugged vs smooth
        DensityFunction erosion = DensityFunctions.noise(
                noiseParams.getOrThrow(Noises.EROSION),
                1.0D,
                0.0D
        );

// D ~ simple vertical gradient: high = positive, low = negative
        DensityFunction depth = DensityFunctions.yClampedGradient(
                -64,
                320,
                -1.0D,
                1.0D
        );

// W ~ ridges / weirdness
// For now, keep this flat so we don't rely on a specific Noises.* key.
        DensityFunction ridges = DensityFunctions.zero();

// Shared shift noise (1.21.1: only Noises.SHIFT exists)
// Derive X/Z shifts directly from the same parameter set
        DensityFunction shiftX = DensityFunctions.shiftA(
                noiseParams.getOrThrow(Noises.SHIFT)
        );
        DensityFunction shiftZ = DensityFunctions.shiftB(
                noiseParams.getOrThrow(Noises.SHIFT)
        );

        DensityFunction temperature = DensityFunctions.shiftedNoise2d(
                shiftX,
                shiftZ,
                0.25D,
                noiseParams.getOrThrow(Noises.TEMPERATURE)
        );

        DensityFunction vegetation = DensityFunctions.shiftedNoise2d(
                shiftX,
                shiftZ,
                0.25D,
                noiseParams.getOrThrow(Noises.VEGETATION)
        );

        // Custom router combining realistic climate noise with a stable terrain gradient
        NoiseRouter router = new NoiseRouter(
                DensityFunctions.zero(), // barrier
                DensityFunctions.zero(), // fluid_level_floodedness
                DensityFunctions.zero(), // fluid_level_spread
                DensityFunctions.zero(), // lava
                temperature,
                vegetation,
                continents,
                erosion,
                depth,
                ridges,
                DensityFunctions.zero(), // initial_density_without_jaggedness
                DensityFunctions.add(
                        DensityFunctions.yClampedGradient(
                                47,
                                87,
                                1.25,
                                -1.25
                        ),
                        DensityFunctions.noise(
                                noiseParams.getOrThrow(CrimsonMoonNoises.CRIMSON_BASE_NOISE),
                                1.0,
                                0.25
                        )
                ),
                DensityFunctions.zero(), // vein_toggle
                DensityFunctions.zero(), // vein_ridged
                DensityFunctions.zero()  // vein_gap
        );

        NoiseSettings settings = new NoiseSettings(
                -64,   // min_y
                384,   // height
                4,     // noise size horizontal
                1      // noise size vertical
        );

        context.register(CRIMSON_NOISE, new NoiseGeneratorSettings(
                settings,
                Blocks.BLACKSTONE.defaultBlockState(),
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