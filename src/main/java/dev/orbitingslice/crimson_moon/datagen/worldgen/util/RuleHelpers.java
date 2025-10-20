package dev.orbitingslice.crimson_moon.datagen.worldgen.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

/**
 * Fluent helpers for dynamically composing or transforming SurfaceRules.
 *
 * The vanilla API is verbose and nested — this adds composable, readable
 * syntax while remaining schema-safe and deterministic.
 *
 * Example usage:
 *  var rules = RuleHelpers.sequence(
 *      RuleHelpers.biome("crimson_moon:crimson_plains",
 *          RuleHelpers.layer(Blocks.CRIMSON_NYLIUM, Blocks.NETHERRACK))
 *  );
 */
public final class RuleHelpers {

    private RuleHelpers() {}

    /** Builds a rule that applies only for a specific biome. */
    public static SurfaceRules.RuleSource biome(ResourceKey<Biome> biomeKey, SurfaceRules.RuleSource rule) {
        return SurfaceRules.ifTrue(SurfaceRules.isBiome(biomeKey), rule);
    }

    /** Builds a simple top + sub layer sequence (safe wrapper for ModSurfaceRules.basicLayer). */
    public static SurfaceRules.RuleSource layer(Block top, Block under) {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state(top.defaultBlockState())),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.state(under.defaultBlockState()))
        );
    }

    /** Builds a three-layer rule with deep filler, ideal for planetary crust or nether biomes. */
    public static SurfaceRules.RuleSource layerDeep(Block top, Block under, Block deep) {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state(top.defaultBlockState())),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.state(under.defaultBlockState())),
                SurfaceRules.state(deep.defaultBlockState())
        );
    }

    /**
     * Applies `from` between [minY, maxY] (inclusive) by height; uses `to` elsewhere.
     * This avoids non-existent float-based APIs and extra noise files.
     */

    public static SurfaceRules.RuleSource verticalBlend(BlockState from, BlockState to, int minY, int maxY) {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.yStartCheck(VerticalAnchor.absolute(minY), 0),
                        SurfaceRules.ifTrue(
                                SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(maxY + 1), 0)),
                                SurfaceRules.state(from)
                        )
                ),
                SurfaceRules.state(to)
        );
    }

    /** Chains multiple rule sources together safely. */
    public static SurfaceRules.RuleSource sequence(SurfaceRules.RuleSource... rules) {
        return SurfaceRules.sequence(rules);
    }
}