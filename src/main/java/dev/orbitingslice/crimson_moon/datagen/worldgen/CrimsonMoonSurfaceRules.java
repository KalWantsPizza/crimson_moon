package dev.orbitingslice.crimson_moon.datagen.worldgen;

import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import static dev.orbitingslice.crimson_moon.datagen.worldgen.CrimsonMoonNoises.CRIMSON_SURFACE_NOISE;

/**
 * Defines biome-specific surface layers for the Crimson Moon dimension.
 * Includes top, mid (stone/blackstone), and deep (deepslate) layers.
 */
public class CrimsonMoonSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
    private static final SurfaceRules.RuleSource BLACKSTONE = makeStateRule(Blocks.BLACKSTONE);
    private static final SurfaceRules.RuleSource NETHERRACK = makeStateRule(Blocks.NETHERRACK);
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource CRIMSON_NYLIUM = makeStateRule(Blocks.CRIMSON_NYLIUM);
    private static final SurfaceRules.RuleSource WARPED_NYLIUM = makeStateRule(Blocks.WARPED_NYLIUM);
    private static final SurfaceRules.RuleSource RED_SAND = makeStateRule(Blocks.RED_SAND);
    private static final SurfaceRules.RuleSource RED_SANDSTONE = makeStateRule(Blocks.RED_SANDSTONE);
    private static final SurfaceRules.RuleSource MOSS_BLOCK = makeStateRule(Blocks.MOSS_BLOCK);
    private static final SurfaceRules.RuleSource TERRACOTTA = makeStateRule(Blocks.TERRACOTTA);
    private static final SurfaceRules.RuleSource ORANGE_TERRACOTTA = makeStateRule(Blocks.ORANGE_TERRACOTTA);
    private static final SurfaceRules.RuleSource RED_TERRACOTTA = makeStateRule(Blocks.RED_TERRACOTTA);


    public static SurfaceRules.RuleSource makeRules() {

        // Common transitions: blackstone ↔ deepslate with a noisy band between

        // Helper: noisy transition band between 0 and 20
        SurfaceRules.RuleSource BLACKSTONE_DEEPSLATE_MIX = SurfaceRules.sequence(
                // In the transition band, use noise to choose between the two
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(CRIMSON_SURFACE_NOISE, -0.2D, 1.0D),
                        BLACKSTONE
                ),
                // Fallback in the band when noiseCondition fails
                DEEPSLATE
        );

        // Full vertical “gradient” column
        SurfaceRules.RuleSource STONE_GRADIENT = SurfaceRules.sequence(
                // Top: pure blackstone above Y=20
                SurfaceRules.ifTrue(
                        SurfaceRules.yBlockCheck(VerticalAnchor.absolute(15), 0),
                        BLACKSTONE
                ),
                // Middle: mixed band between Y=0 and Y=20
                SurfaceRules.ifTrue(
                        SurfaceRules.yBlockCheck(VerticalAnchor.absolute(0), 0),
                        BLACKSTONE_DEEPSLATE_MIX
                ),
                // Bottom: pure deepslate below Y=0
                DEEPSLATE
        );

        SurfaceRules.RuleSource bedrockFloor =
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor",
                        VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK);

        // -----------------
        // Crimson Plains
        // -----------------
        SurfaceRules.RuleSource crimsonPlains = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(CrimsonMoonBiomes.CRIMSON_PLAINS),
                SurfaceRules.sequence(
                        // Default surface: red-tinted grass + dirt. Crimson Plains is meant to be
                        // the dimension's "livable default" biome (like vanilla Plains).
                        // Nylium + crimson fungus groves are NOT handled here anymore -- they'll
                        // come from a vegetation-patch-style configured/placed feature instead,
                        // so they appear as small, localized groves tied to fungus tree placement
                        // rather than a broad noise-threshold swap across ~50% of the biome.
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, GRASS_BLOCK),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT),

                        // ↓ let the vertical bands handle the rest
                        bedrockFloor,
                        STONE_GRADIENT
                )
        );

        // -----------------
        // Warped Oasis
        // -----------------
        SurfaceRules.RuleSource warpedOasis = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(CrimsonMoonBiomes.WARPED_OASIS),
                SurfaceRules.sequence(
                        // Warped nylium base with moss and teal-tinted grass patches (grass
                        // renders teal via this biome's grassColorOverride). Also gives Dark
                        // Oak trees dirt-tag ground to root in -- nylium itself doesn't count.
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(CRIMSON_SURFACE_NOISE, 0.55D, 1.0D), GRASS_BLOCK),
                                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(CRIMSON_SURFACE_NOISE, 0.3D, 0.55D), MOSS_BLOCK),
                                        SurfaceRules.state(Blocks.WARPED_NYLIUM.defaultBlockState())
                                )),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState())),
                        bedrockFloor,
                        STONE_GRADIENT
                )
        );

        // -----------------
// Crimson River
// -----------------
        SurfaceRules.RuleSource crimsonRiver = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(CrimsonMoonBiomes.CRIMSON_RIVER),
                SurfaceRules.sequence(
                        // Underwater riverbed: red sand top (gravel/magma follows a few blocks
                        // down, below).
                        SurfaceRules.ifTrue(
                                SurfaceRules.ON_FLOOR,
                                SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), RED_SAND)
                        ),

                        // Dry banks: crimson grass dominant with red sand patches. Also gives
                        // jungle trees / crimson fungus dirt-tag ground to actually root in --
                        // red sand alone doesn't count as plantable ground for them.
                        SurfaceRules.ifTrue(
                                SurfaceRules.ON_FLOOR,
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(CRIMSON_SURFACE_NOISE, 0.5D, 1.0D), RED_SAND),
                                        GRASS_BLOCK
                                )
                        ),

                        // Just below the banks: magma + gravel riverbed, 1–4 blocks under the surface
                        SurfaceRules.ifTrue(
                                SurfaceRules.stoneDepthCheck(1, false, 4, CaveSurface.FLOOR),
                                SurfaceRules.sequence(
                                        // steeper spots get gravel instead of magma
                                        SurfaceRules.ifTrue(SurfaceRules.steep(),
                                                SurfaceRules.state(Blocks.GRAVEL.defaultBlockState())),
                                        SurfaceRules.state(Blocks.MAGMA_BLOCK.defaultBlockState())
                                )
                        ),

                        // Fallback deeper layer: netherrack before we hit the global stone gradient
                        SurfaceRules.ifTrue(
                                SurfaceRules.stoneDepthCheck(5, false, 20, CaveSurface.FLOOR),
                                NETHERRACK
                        ),

                        bedrockFloor,
                        STONE_GRADIENT
                )
        );

        // -----------------
        // Scorched Delta
        // -----------------
        SurfaceRules.RuleSource scorchedDelta = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(CrimsonMoonBiomes.SCORCHED_DELTA),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.state(Blocks.BLACKSTONE.defaultBlockState())),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                SurfaceRules.state(Blocks.BASALT.defaultBlockState())),
                        bedrockFloor,
                        STONE_GRADIENT
                )
        );

        // -----------------
        // Deepslate Peaks
        // -----------------
        SurfaceRules.RuleSource deepslatePeaks = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(CrimsonMoonBiomes.DEEPSLATE_PEAKS),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState())),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState())),
                        bedrockFloor,
                        STONE_GRADIENT
                )
        );

        // -----------------
        // Frozen Lava Tubes
        // -----------------
        SurfaceRules.RuleSource frozenLavaTubes = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(CrimsonMoonBiomes.FROZEN_LAVA_TUBES),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.state(Blocks.PACKED_ICE.defaultBlockState())),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                SurfaceRules.state(Blocks.BASALT.defaultBlockState())),
                        bedrockFloor,
                        STONE_GRADIENT
                )
        );

        // -----------------
        // Crimson Beach
        // -----------------
        SurfaceRules.RuleSource crimsonBeach = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(CrimsonMoonBiomes.CRIMSON_BEACH),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.state(Blocks.RED_SAND.defaultBlockState())),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                SurfaceRules.state(Blocks.RED_SANDSTONE.defaultBlockState())),
                        bedrockFloor,
                        STONE_GRADIENT
                )
        );

        // -----------------
        // Deepslate Caves
        // -----------------
        SurfaceRules.RuleSource deepslateCaves = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(CrimsonMoonBiomes.DEEPSLATE_CAVES),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState())),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                SurfaceRules.state(Blocks.TUFF.defaultBlockState())),
                        bedrockFloor,
                        STONE_GRADIENT
                )
        );

        // -----------------
        // Desert (vanilla) -- red sand/sandstone to match the dimension's palette instead
        // of vanilla's tan sand.
        // -----------------
        SurfaceRules.RuleSource desert = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(Biomes.DESERT),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, RED_SAND),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, RED_SANDSTONE),
                        bedrockFloor,
                        STONE_GRADIENT
                )
        );

        // -----------------
        // Badlands (vanilla) -- red sand top with a light noise-based terracotta color
        // mix underneath. Not full vanilla mesa banding (this generator doesn't do
        // biome-driven terracing), just enough variation to read as "badlands" rather
        // than a single flat color.
        // -----------------
        SurfaceRules.RuleSource badlandsTerracottaMix = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(CRIMSON_SURFACE_NOISE, 0.3D, 1.0D), ORANGE_TERRACOTTA),
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(CRIMSON_SURFACE_NOISE, -0.3D, 0.3D), RED_TERRACOTTA),
                TERRACOTTA
        );
        SurfaceRules.RuleSource badlands = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(Biomes.BADLANDS),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, RED_SAND),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, badlandsTerracottaMix),
                        bedrockFloor,
                        STONE_GRADIENT
                )
        );

        // -----------------
        // Warm Ocean (vanilla)
        // -----------------
        SurfaceRules.RuleSource warmOcean = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(Biomes.WARM_OCEAN),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.state(Blocks.SAND.defaultBlockState())),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                SurfaceRules.state(Blocks.SANDSTONE.defaultBlockState())),
                        bedrockFloor,
                        STONE_GRADIENT
                )
        );

        // -----------------
        // Crimson Forest (vanilla)
        // -----------------
        SurfaceRules.RuleSource crimsonForest = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(Biomes.CRIMSON_FOREST),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, CRIMSON_NYLIUM),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, NETHERRACK),
                        bedrockFloor,
                        STONE_GRADIENT
                )
        );

        // Combine all biome rules + fallback
        return SurfaceRules.sequence(
                crimsonPlains,
                warpedOasis,
                crimsonRiver,
                scorchedDelta,
                deepslatePeaks,
                frozenLavaTubes,
                crimsonBeach,
                deepslateCaves,
                desert,
                badlands,
                warmOcean,
                crimsonForest,

                // Fallback for any undefined biome
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState())),
                        bedrockFloor,
                        STONE_GRADIENT
                )
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}