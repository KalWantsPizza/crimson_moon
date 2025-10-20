package dev.orbitingslice.crimson_moon.datagen.worldgen;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class CrimsonMoonSurfaceRules {

    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                        SurfaceRules.state(Blocks.CRIMSON_NYLIUM.defaultBlockState())),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState())),
                SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState())
        );
    }
}
