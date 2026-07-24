package dev.orbitingslice.crimson_moon.datagen.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CubicSpline;
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

// C ~ continents / inlandness. Clamped to its nominal +-1 range -- raw NormalNoise
// can spike past that in its tails, and an unclamped input let the height spline
// below extrapolate way past its last point (e.g. continentalness 1.3 shooting
// terrain height up past y200), which is what put Badlands/Lava River/Crimson
// Forest in the sky.
        DensityFunction continents = DensityFunctions.noise(
                noiseParams.getOrThrow(Noises.CONTINENTALNESS),
                1.0D,
                0.0D
        ).clamp(-1.0D, 1.0D);

// E ~ erosion / rugged vs smooth. Same clamping rationale as continentalness.
        DensityFunction erosion = DensityFunctions.noise(
                noiseParams.getOrThrow(Noises.EROSION),
                1.0D,
                0.0D
        ).clamp(-1.0D, 1.0D);

// Height spline: base terrain elevation as a function of continentalness, so land
// height actually correlates with the same axis used to place biomes (ocean =
// low continentalness = low ground, peaks = high continentalness = high ground).
// Breakpoints chosen to land each biome's existing continentalness band at
// roughly the elevation its own design doc describes.
        DensityFunctions.Spline.Coordinate continentCoordinate =
                new DensityFunctions.Spline.Coordinate(Holder.direct(continents));
        CubicSpline<DensityFunctions.Spline.Point, DensityFunctions.Spline.Coordinate> heightCurve =
                CubicSpline.<DensityFunctions.Spline.Point, DensityFunctions.Spline.Coordinate>builder(continentCoordinate)
                        .addPoint(-1.00F, 32F)
                        .addPoint(-0.55F, 46F)
                        .addPoint(-0.30F, 54F)
                        .addPoint(-0.10F, 60F)
                        .addPoint(0.00F, 63F)
                        .addPoint(0.20F, 68F)
                        .addPoint(0.35F, 74F)
                        .addPoint(0.50F, 80F)
                        .addPoint(0.70F, 92F)
                        .addPoint(0.80F, 110F)
                        .addPoint(0.90F, 165F)
                        .addPoint(1.00F, 230F)
                        .build();
        DensityFunction heightSpline = DensityFunctions.spline(heightCurve);

// Erosion amplitude: low erosion = rugged (large amplitude), high erosion = flat
// (small amplitude) -- matches the existing per-biome erosion assignments
// (e.g. deepslate_peaks' low erosion + "preserves steep relief" comment).
        DensityFunctions.Spline.Coordinate erosionCoordinate =
                new DensityFunctions.Spline.Coordinate(Holder.direct(erosion));
        CubicSpline<DensityFunctions.Spline.Point, DensityFunctions.Spline.Coordinate> amplitudeCurve =
                CubicSpline.<DensityFunctions.Spline.Point, DensityFunctions.Spline.Coordinate>builder(erosionCoordinate)
                        .addPoint(-1.00F, 28F)
                        .addPoint(0.00F, 20F)
                        .addPoint(0.50F, 12F)
                        .addPoint(1.00F, 4F)
                        .build();
        DensityFunction amplitudeSpline = DensityFunctions.spline(amplitudeCurve);

// Effective terrain height: baseline elevation plus local noise scaled by the
// erosion-driven amplitude.
        DensityFunction effectiveHeight = DensityFunctions.add(
                heightSpline,
                DensityFunctions.mul(
                        amplitudeSpline,
                        DensityFunctions.noise(
                                noiseParams.getOrThrow(CrimsonMoonNoises.CRIMSON_BASE_NOISE),
                                1.0,
                                0.25
                        )
                )
        );

// Raw Y coordinate as a density function, for computing distance from the
// locally-generated surface.
        DensityFunction rawY = DensityFunctions.yClampedGradient(-64, 320, -64.0D, 320.0D);

// D ~ distance below the *locally generated* surface (not raw absolute Y like
// before). At the generated surface, depth ~= 0; underground is positive;
// above-surface/sky is negative. This is what makes the per-biome depth ranges
// in CrimsonMoonDimension actually mean something again.
// IMPORTANT: uses effectiveHeight (spline + local noise), the same basis
// final_density uses -- using the smooth heightSpline alone here (as an earlier
// version did) let depth and the real generated terrain disagree by up to the
// local noise amplitude (~4-28 blocks), which was enough to paint a surface
// biome's label at a y-slice with no solid ground underneath it at all.
        DensityFunction depth = DensityFunctions.mul(
                DensityFunctions.add(effectiveHeight, DensityFunctions.mul(rawY, DensityFunctions.constant(-1.0D))),
                DensityFunctions.constant(1.0D / 50.0D)
        ).clamp(-3.0D, 3.0D);

// Shared shift noise (1.21.1: only Noises.SHIFT exists)
// Derive X/Z shifts directly from the same parameter set
        DensityFunction shiftX = DensityFunctions.shiftA(
                noiseParams.getOrThrow(Noises.SHIFT)
        );
        DensityFunction shiftZ = DensityFunctions.shiftB(
                noiseParams.getOrThrow(Noises.SHIFT)
        );

// W ~ ridges / weirdness. Drives biome patchiness (e.g. Warped Oasis' "micro" pockets) --
// was previously DensityFunctions.zero(), which made weirdness constant everywhere and
// silently disabled any biome sizing/exclusion that depended on it.
        DensityFunction ridges = DensityFunctions.shiftedNoise2d(
                shiftX,
                shiftZ,
                0.25D,
                noiseParams.getOrThrow(Noises.RIDGE)
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
                // final_density: solid below the effective terrain height, air above it --
                // now genuinely driven by continentalness/erosion via effectiveHeight,
                // instead of the old fixed y47-87 band that ignored climate entirely.
                DensityFunctions.mul(
                        DensityFunctions.add(effectiveHeight, DensityFunctions.mul(rawY, DensityFunctions.constant(-1.0D))),
                        DensityFunctions.constant(1.0D / 20.0D)
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