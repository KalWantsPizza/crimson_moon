package dev.orbitingslice.datagen_kit.worldgen.base;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;

/**
 * Shared factory for building surface rule chains.
 *
 * Think of this as a DSL for terrain layers:
 *  - basicLayer() → top + sub layer
 *  - threeLayer() → top + sub + deep
 *  - biomeConditional() → biome-specific layer
 *  - sequence() → merges multiple rules
 *
 * Mods subclass this or call these statics to compose complex rule graphs.
 */
public class ModSurfaceRules {

    /** A simple fallback: stone top with deepslate below. */
    public static SurfaceRules.RuleSource defaultSurface() {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                        SurfaceRules.state(Blocks.STONE.defaultBlockState())),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState()))
        );
    }

    /** Basic two-layer structure (e.g., nylium over netherrack). */
    public static SurfaceRules.RuleSource basicLayer(BlockState top, BlockState under) {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state(top)),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.state(under))
        );
    }

    /** Three-layer structure (top → under → deep). */
    public static SurfaceRules.RuleSource threeLayer(BlockState top, BlockState under, BlockState deep) {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state(top)),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.state(under)),
                SurfaceRules.state(deep)
        );
    }

    /** Biome-specific surface override — useful for multi-biome dimensions. */
    public static SurfaceRules.RuleSource biomeConditional(ResourceKey<Biome> biomeKey, Block top, Block under) {
        return SurfaceRules.ifTrue(
                SurfaceRules.isBiome(biomeKey),
                basicLayer(top.defaultBlockState(), under.defaultBlockState())
        );
    }

    /** Compose multiple rules together safely. */
    public static SurfaceRules.RuleSource sequence(SurfaceRules.RuleSource... rules) {
        return SurfaceRules.sequence(rules);
    }
}