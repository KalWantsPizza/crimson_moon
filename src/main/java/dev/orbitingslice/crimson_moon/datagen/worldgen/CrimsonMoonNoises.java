package dev.orbitingslice.crimson_moon.datagen.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import static dev.orbitingslice.crimson_moon.CrimsonMoon.resource;

/**
 * Registers Crimson Moon's NormalNoise parameter sets.
 * Valid for NeoForge 21.1.211 + Parchment 2024-11-17 (MC 1.21.1).
 */
public class CrimsonMoonNoises {

    // noise: crimson_moon:crimson_base_noise
    public static final ResourceKey<NormalNoise.NoiseParameters> CRIMSON_BASE_NOISE =
            ResourceKey.create(
                    Registries.NOISE,
                    resource("crimson_base_noise")
            );
    public static final ResourceKey<NormalNoise.NoiseParameters> CRIMSON_SURFACE_NOISE =
            ResourceKey.create(
                    Registries.NOISE,
                    resource("crimson_surface_noise")
            );

    public static void bootstrap(BootstrapContext<NormalNoise.NoiseParameters> context) {
        context.register(
                CRIMSON_BASE_NOISE,
                new NormalNoise.NoiseParameters(-7, 1, 0, 0.5, 0.25, 0.25)
        );

        context.register(
                CRIMSON_SURFACE_NOISE,
                new NormalNoise.NoiseParameters(-6, 1)
        );
    }
}