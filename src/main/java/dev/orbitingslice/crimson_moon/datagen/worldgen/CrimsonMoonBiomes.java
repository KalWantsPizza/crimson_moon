package dev.orbitingslice.crimson_moon.datagen.worldgen;

import dev.orbitingslice.crimson_moon.datagen.worldgen.util.ColorPalettes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class CrimsonMoonBiomes {

    public static final ResourceKey<Biome> CRIMSON_PLAINS = key("crimson_plains");
    public static final ResourceKey<Biome> CRIMSON_RIVER = key("crimson_river");
    public static final ResourceKey<Biome> LAVA_RIVER = key("lava_river");
    public static final ResourceKey<Biome> CRIMSON_BEACH = key("crimson_beach");
    public static final ResourceKey<Biome> SCORCHED_DELTA = key("scorched_delta");
    public static final ResourceKey<Biome> DEEPSLATE_CAVES = key("deepslate_caves");
    public static final ResourceKey<Biome> DEEPSLATE_PEAKS = key("deepslate_peaks");
    public static final ResourceKey<Biome> WARPED_OASIS = key("warped_oasis");
    public static final ResourceKey<Biome> FROZEN_LAVA_TUBES = key("frozen_lava_tubes");

    private static ResourceKey<Biome> key(String name) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("crimson_moon", name));
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        var placed = context.lookup(Registries.PLACED_FEATURE);
        var carvers = context.lookup(Registries.CONFIGURED_CARVER);

        register(context, CRIMSON_PLAINS, createBiome(1.8F, 0.0F, ColorPalettes.CRIMSON_FOG, ColorPalettes.CRIMSON_WATER, ColorPalettes.CRIMSON_WATER, ColorPalettes.CRIMSON_SKY, placed, carvers));
        register(context, CRIMSON_RIVER, createBiome(1.4F, 0.0F, ColorPalettes.CRIMSON_FOG, ColorPalettes.CRIMSON_WATER, ColorPalettes.CRIMSON_WATER, ColorPalettes.CRIMSON_SKY, placed, carvers));
        register(context, LAVA_RIVER, createBiome(1.6F, 0.0F, ColorPalettes.CRIMSON_FOG, ColorPalettes.CRIMSON_LAVA, ColorPalettes.CRIMSON_LAVA, ColorPalettes.CRIMSON_SKY, placed, carvers));
        register(context, CRIMSON_BEACH, createBiome(1.6F, 0.0F, ColorPalettes.CRIMSON_FOG, ColorPalettes.CRIMSON_WATER, ColorPalettes.CRIMSON_WATER, ColorPalettes.CRIMSON_SKY, placed, carvers));
        register(context, SCORCHED_DELTA, createBiome(2.0F, 0.0F, ColorPalettes.CRIMSON_FOG, ColorPalettes.CRIMSON_LAVA, ColorPalettes.CRIMSON_LAVA, ColorPalettes.CRIMSON_SKY, placed, carvers));
        register(context, DEEPSLATE_CAVES, createBiome(0.8F, 0.0F, ColorPalettes.DEEPSLATE_FOG, ColorPalettes.DEEPSLATE_WATER, ColorPalettes.DEEPSLATE_WATER, ColorPalettes.DEEPSLATE_SKY, placed, carvers));
        register(context, DEEPSLATE_PEAKS, createBiome(0.5F, 0.0F, ColorPalettes.DEEPSLATE_FOG, ColorPalettes.DEEPSLATE_WATER, ColorPalettes.DEEPSLATE_WATER, ColorPalettes.DEEPSLATE_SKY, placed, carvers));
        register(context, WARPED_OASIS, createBiome(1.2F, 0.0F, ColorPalettes.WARPED_FOG, ColorPalettes.WARPED_WATER, ColorPalettes.WARPED_WATER, ColorPalettes.WARPED_SKY, placed, carvers));
        register(context, FROZEN_LAVA_TUBES, createBiome(0.0F, 0.0F, ColorPalettes.FROZEN_FOG, ColorPalettes.FROZEN_WATER, ColorPalettes.FROZEN_WATER, ColorPalettes.FROZEN_SKY, placed, carvers));
    }

    private static void register(BootstrapContext<Biome> context, ResourceKey<Biome> key, Biome biome) {
        context.register(key, biome);
    }

    private static Biome createBiome(float temperature, float downfall, int fogColor, int waterColor, int waterFogColor, int skyColor,
                                     net.minecraft.core.HolderGetter<PlacedFeature> placed,
                                     net.minecraft.core.HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(fogColor)
                .waterColor(waterColor)
                .waterFogColor(waterFogColor)
                .skyColor(skyColor)
                .build();

        BiomeGenerationSettings generation = new BiomeGenerationSettings.Builder(placed, carvers).build();
        MobSpawnSettings spawns = new MobSpawnSettings.Builder().build();

        return new Biome.BiomeBuilder()
                .temperature(temperature)
                .downfall(downfall)
                .hasPrecipitation(false)
                .specialEffects(effects)
                .mobSpawnSettings(spawns)
                .generationSettings(generation)
                .build();
    }
}