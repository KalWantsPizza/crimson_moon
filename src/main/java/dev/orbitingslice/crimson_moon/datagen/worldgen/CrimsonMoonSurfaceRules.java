package dev.orbitingslice.crimson_moon.datagen.worldgen;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

/**
 * Defines biome-specific surface layers for the Crimson Moon dimension.
 * Includes top, mid (stone/blackstone), and deep (deepslate) layers.
 */
public class CrimsonMoonSurfaceRules {

    public static SurfaceRules.RuleSource makeRules() {

        // Common transitions
        SurfaceRules.RuleSource deepLayer = SurfaceRules.ifTrue(
                SurfaceRules.yStartCheck(VerticalAnchor.absolute(30), 0),
                SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState())
        );

        SurfaceRules.RuleSource midLayer = SurfaceRules.ifTrue(
                SurfaceRules.yStartCheck(VerticalAnchor.absolute(55), 0),
                SurfaceRules.state(Blocks.BLACKSTONE.defaultBlockState())
        );

        // -----------------
        // Crimson Plains
        // -----------------
        SurfaceRules.RuleSource crimsonPlains = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(CrimsonMoonBiomes.CRIMSON_PLAINS),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState())),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                SurfaceRules.state(Blocks.DIRT.defaultBlockState())),
                        midLayer,
                        deepLayer
                )
        );

        // -----------------
        // Warped Oasis
        // -----------------
        SurfaceRules.RuleSource warpedOasis = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(CrimsonMoonBiomes.WARPED_OASIS),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.state(Blocks.WARPED_NYLIUM.defaultBlockState())),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState())),
                        midLayer,
                        deepLayer
                )
        );

        // -----------------
        // Crimson River
        // -----------------
        SurfaceRules.RuleSource crimsonRiver = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(CrimsonMoonBiomes.CRIMSON_RIVER),
                SurfaceRules.sequence(
                    // Riverbed: Magma + gravel mix under Y=62
                    SurfaceRules.ifTrue(
                        SurfaceRules.yStartCheck(VerticalAnchor.absolute(62), 0),
                        SurfaceRules.sequence(
                            SurfaceRules.ifTrue(SurfaceRules.steep(),
                                SurfaceRules.state(Blocks.GRAVEL.defaultBlockState())),
                            SurfaceRules.state(Blocks.MAGMA_BLOCK.defaultBlockState())
                        )
                    ),

                    // Banks: crimson nylium + red sand + sand mix
                    SurfaceRules.ifTrue(
                        SurfaceRules.yStartCheck(VerticalAnchor.absolute(63), 0),
                        SurfaceRules.sequence(
                            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.state(Blocks.CRIMSON_NYLIUM.defaultBlockState())),
                            SurfaceRules.state(Blocks.RED_SAND.defaultBlockState())
                        )
                    ),

                    // Fallback deeper layer
                    SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState())),
                    midLayer,
                    deepLayer
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
                        midLayer,
                        deepLayer
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
                        deepLayer,
                        deepLayer
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
                        midLayer,
                        deepLayer
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
                        midLayer,
                        deepLayer
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
                        deepLayer
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

                // Fallback for any undefined biome
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState())),
                        midLayer,
                        deepLayer
                )
        );
    }
}