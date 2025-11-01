package dev.orbitingslice.crimson_moon.datagen.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.common.Tags;

import java.util.OptionalLong;

import static dev.orbitingslice.crimson_moon.CrimsonMoon.resource;

public class CrimsonMoonDimensionType {

    public static final ResourceKey<DimensionType> CRIMSON_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE, resource( "crimson_moon_type"));

    public static void bootstrap(BootstrapContext<DimensionType> context) {
        DimensionType type = new DimensionType(
                OptionalLong.empty(), // normal day/night cycle
                true,  // has skylight
                false, // no ceiling
                false, // not ultrawarm (Nether-like)
                true,  // natural
                1.0,   // coordinate scale
                true,  // beds work
                true,  // respawn anchor works
                -64,   // minY
                384,   // height
                384,   // logical height
                BlockTags.INFINIBURN_OVERWORLD,
                ResourceLocation.withDefaultNamespace("overworld"),
                0,
                new DimensionType.MonsterSettings(true, false, net.minecraft.util.valueproviders.ConstantInt.of(0), 7)
        );
        context.register(CRIMSON_TYPE, type);
    }
}
