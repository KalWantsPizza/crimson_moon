package dev.orbitingslice.crimson_moon.datagen.worldgen;

import dev.orbitingslice.crimson_moon.datagen.worldgen.util.ColorPalettes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
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
    public static final ResourceKey<Biome> CRIMSON_GROVE = key("crimson_grove");
    public static final ResourceKey<Biome> CRIMSON_TAIGA = key("crimson_taiga");
    public static final ResourceKey<Biome> CRIMSON_JUNGLE = key("crimson_jungle");
    public static final ResourceKey<Biome> CRIMSON_MANGROVE = key("crimson_mangrove");
    public static final ResourceKey<Biome> DEEPSLATE_SHORE = key("deepslate_shore");
    public static final ResourceKey<Biome> CRIMSON_MEADOW = key("crimson_meadow");

    private static ResourceKey<Biome> key(String name) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("crimson_moon", name));
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        var placed = context.lookup(Registries.PLACED_FEATURE);
        var carvers = context.lookup(Registries.CONFIGURED_CARVER);

        register(context, CRIMSON_PLAINS, createBiome(0.5F, 0.4F, ColorPalettes.CRIMSON_FOG, ColorPalettes.CRIMSON_WATER, ColorPalettes.CRIMSON_WATER, ColorPalettes.CRIMSON_SKY, ColorPalettes.CRIMSON_GRASS, ColorPalettes.CRIMSON_FOLIAGE, crimsonPlainsGeneration(placed, carvers), crimsonPlainsSpawns()));
        register(context, CRIMSON_RIVER, createBiome(0.5F, 0.5F, ColorPalettes.CRIMSON_FOG, ColorPalettes.CRIMSON_WATER, ColorPalettes.CRIMSON_WATER, ColorPalettes.CRIMSON_SKY, ColorPalettes.CRIMSON_GRASS, ColorPalettes.CRIMSON_FOLIAGE, crimsonRiverGeneration(placed, carvers), crimsonRiverSpawns()));
        register(context, LAVA_RIVER, createBiome(1.6F, 0.0F, ColorPalettes.CRIMSON_FOG, ColorPalettes.CRIMSON_LAVA, ColorPalettes.CRIMSON_LAVA, ColorPalettes.CRIMSON_SKY, ColorPalettes.CRIMSON_GRASS, ColorPalettes.CRIMSON_FOLIAGE, lavaRiverGeneration(placed, carvers), lavaRiverSpawns()));
        register(context, CRIMSON_BEACH, createBiome(1.6F, 0.0F, ColorPalettes.CRIMSON_FOG, ColorPalettes.CRIMSON_WATER, ColorPalettes.CRIMSON_WATER, ColorPalettes.CRIMSON_SKY, ColorPalettes.CRIMSON_GRASS, ColorPalettes.CRIMSON_FOLIAGE, crimsonBeachGeneration(placed, carvers), crimsonBeachSpawns()));
        register(context, SCORCHED_DELTA, createBiome(2.0F, 0.0F, ColorPalettes.CRIMSON_FOG, ColorPalettes.CRIMSON_LAVA, ColorPalettes.CRIMSON_LAVA, ColorPalettes.CRIMSON_SKY, ColorPalettes.CRIMSON_GRASS, ColorPalettes.CRIMSON_FOLIAGE, scorchedDeltaGeneration(placed, carvers), scorchedDeltaSpawns()));
        register(context, DEEPSLATE_CAVES, createBiome(0.8F, 0.0F, ColorPalettes.DEEPSLATE_FOG, ColorPalettes.DEEPSLATE_WATER, ColorPalettes.DEEPSLATE_WATER, ColorPalettes.DEEPSLATE_SKY, ColorPalettes.DEEPSLATE_GRASS, ColorPalettes.DEEPSLATE_FOLIAGE, deepslateCavesGeneration(placed, carvers), deepslateCavesSpawns()));
        register(context, DEEPSLATE_PEAKS, createBiome(0.5F, 0.0F, ColorPalettes.DEEPSLATE_FOG, ColorPalettes.DEEPSLATE_WATER, ColorPalettes.DEEPSLATE_WATER, ColorPalettes.DEEPSLATE_SKY, ColorPalettes.DEEPSLATE_GRASS, ColorPalettes.DEEPSLATE_FOLIAGE, deepslatePeaksGeneration(placed, carvers), deepslatePeaksSpawns()));
        register(context, WARPED_OASIS, createBiome(1.2F, 0.0F, ColorPalettes.WARPED_FOG, ColorPalettes.WARPED_WATER, ColorPalettes.WARPED_WATER, ColorPalettes.WARPED_SKY, ColorPalettes.WARPED_GRASS, ColorPalettes.WARPED_FOLIAGE, warpedOasisGeneration(placed, carvers), warpedOasisSpawns()));
        register(context, FROZEN_LAVA_TUBES, createBiome(0.0F, 0.0F, ColorPalettes.FROZEN_FOG, ColorPalettes.FROZEN_WATER, ColorPalettes.FROZEN_WATER, ColorPalettes.FROZEN_SKY, ColorPalettes.FROZEN_GRASS, ColorPalettes.FROZEN_FOLIAGE, frozenLavaTubesGeneration(placed, carvers), frozenLavaTubesSpawns()));
        register(context, CRIMSON_GROVE, createBiome(0.8F, 0.5F, ColorPalettes.GROVE_FOG, ColorPalettes.GROVE_WATER, ColorPalettes.GROVE_WATER, ColorPalettes.GROVE_SKY, ColorPalettes.GROVE_GRASS, ColorPalettes.GROVE_FOLIAGE, crimsonGroveGeneration(placed, carvers), crimsonGroveSpawns()));
        register(context, CRIMSON_TAIGA, createBiome(0.4F, 0.4F, ColorPalettes.TAIGA_FOG, ColorPalettes.TAIGA_WATER, ColorPalettes.TAIGA_WATER, ColorPalettes.TAIGA_SKY, ColorPalettes.TAIGA_GRASS, ColorPalettes.TAIGA_FOLIAGE, crimsonTaigaGeneration(placed, carvers), crimsonTaigaSpawns()));
        register(context, CRIMSON_JUNGLE, createBiome(1.1F, 0.8F, ColorPalettes.JUNGLE_FOG, ColorPalettes.JUNGLE_WATER, ColorPalettes.JUNGLE_WATER, ColorPalettes.JUNGLE_SKY, ColorPalettes.JUNGLE_GRASS, ColorPalettes.JUNGLE_FOLIAGE, crimsonJungleGeneration(placed, carvers), crimsonJungleSpawns()));
        register(context, CRIMSON_MANGROVE, createBiome(0.9F, 0.8F, ColorPalettes.MANGROVE_FOG, ColorPalettes.MANGROVE_WATER, ColorPalettes.MANGROVE_WATER, ColorPalettes.MANGROVE_SKY, ColorPalettes.MANGROVE_GRASS, ColorPalettes.MANGROVE_FOLIAGE, crimsonMangroveGeneration(placed, carvers), crimsonMangroveSpawns()));
        register(context, DEEPSLATE_SHORE, createBiome(0.6F, 0.0F, ColorPalettes.DEEPSLATE_FOG, ColorPalettes.DEEPSLATE_WATER, ColorPalettes.DEEPSLATE_WATER, ColorPalettes.DEEPSLATE_SKY, ColorPalettes.DEEPSLATE_GRASS, ColorPalettes.DEEPSLATE_FOLIAGE, deepslateShoreGeneration(placed, carvers), deepslateShoreSpawns()));
        register(context, CRIMSON_MEADOW, createBiome(0.5F, 0.6F, ColorPalettes.MEADOW_FOG, ColorPalettes.MEADOW_WATER, ColorPalettes.MEADOW_WATER, ColorPalettes.MEADOW_SKY, ColorPalettes.MEADOW_GRASS, ColorPalettes.MEADOW_FOLIAGE, crimsonMeadowGeneration(placed, carvers), crimsonMeadowSpawns()));
    }

    private static void register(BootstrapContext<Biome> context, ResourceKey<Biome> key, Biome biome) {
        context.register(key, biome);
    }

    private static Biome createBiome(float temperature, float downfall, int fogColor, int waterColor, int waterFogColor, int skyColor, int grassColor, int foliageColor,
                                     BiomeGenerationSettings generation, MobSpawnSettings spawns) {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(fogColor)
                .waterColor(waterColor)
                .waterFogColor(waterFogColor)
                .skyColor(skyColor)
                .grassColorOverride(grassColor)
                .foliageColorOverride(foliageColor)
                .build();

        return new Biome.BiomeBuilder()
                .temperature(temperature)
                .downfall(downfall)
                .hasPrecipitation(false)
                .specialEffects(effects)
                .mobSpawnSettings(spawns)
                .generationSettings(generation)
                .build();
    }

    // -----------------
    // Crimson Plains -- dominant "livable default" biome: sparse crimson grass and
    // shrubs, base-level herbivores, hostile presence balanced by sparse flora.
    // -----------------
    private static BiomeGenerationSettings crimsonPlainsGeneration(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, carvers);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
        BiomeDefaultFeatures.addDefaultOres(gen);
        return gen.build();
    }

    private static MobSpawnSettings crimsonPlainsSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 3, 1, 2));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 3, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 60, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 40, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 20, 1, 2));
        return spawns.build();
    }

    // -----------------
    // Crimson River -- calm red-tinted river cutting through the plains.
    // -----------------
    private static BiomeGenerationSettings crimsonRiverGeneration(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, carvers);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.PATCH_CRIMSON_ROOTS);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BAMBOO_LIGHT);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_RIVER);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.KELP_WARM);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_SPARSE_JUNGLE);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TreePlacements.CRIMSON_FUNGI);
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.GLOW_LICHEN);
        return gen.build();
    }

    private static MobSpawnSettings crimsonRiverSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 1, 3));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 1, 2));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 2, 1, 2));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 3, 1, 2));
        spawns.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, 8, 2, 4));
        spawns.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 8, 2, 4));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 3, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.HUSK, 10, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 10, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 10, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 5, 1, 1));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PIGLIN, 1, 1, 1));
        return spawns.build();
    }

    // -----------------
    // Lava River -- molten channels; Striders and Magma Cubes define its motion.
    // -----------------
    private static BiomeGenerationSettings lavaRiverGeneration(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, carvers);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.PATCH_CRIMSON_ROOTS);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.NETHER_SPROUTS);
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SMALL_BASALT_COLUMNS);
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.LARGE_BASALT_COLUMNS);
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_DELTA);
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_MAGMA);
        return gen.build();
    }

    private static MobSpawnSettings lavaRiverSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 15, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 80, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 30, 2, 3));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 8, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 8, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 8, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PIGLIN, 5, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.HOGLIN, 5, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 4, 1, 1));
        return spawns.build();
    }

    // -----------------
    // Scorched Delta -- volcanic-glassy wasteland; sparse flora, heat-tolerant mobs only.
    // -----------------
    private static BiomeGenerationSettings scorchedDeltaGeneration(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, carvers);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.PATCH_CRIMSON_ROOTS);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.NETHER_SPROUTS);
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BASALT_BLOBS);
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BLACKSTONE_BLOBS);
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.DELTA);
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_DELTA);
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_MAGMA);
        return gen.build();
    }

    private static MobSpawnSettings scorchedDeltaSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 30, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 30, 2, 3));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 8, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 8, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 8, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PIGLIN, 5, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.HOGLIN, 5, 1, 2));
        return spawns.build();
    }

    // -----------------
    // Crimson Beach -- cooling shoreline connecting red sands to the Warm Ocean.
    // -----------------
    private static BiomeGenerationSettings crimsonBeachGeneration(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, carvers);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.WARM_OCEAN_VEGETATION);
        return gen.build();
    }

    private static MobSpawnSettings crimsonBeachSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 2, 4));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 1, 2));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 2, 1, 2));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.TURTLE, 4, 2, 3));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 2, 1, 2));
        spawns.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, 8, 2, 4));
        spawns.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 6, 2, 3));
        spawns.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 4, 2, 3));
        spawns.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.PUFFERFISH, 2, 1, 2));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 3, 1, 2));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 2, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 6, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 6, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 6, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.HUSK, 3, 1, 1));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 5, 1, 1));
        return spawns.build();
    }

    // -----------------
    // Warped Oasis -- rare teal micro-biome; peaceful, no surface hostiles.
    // Allay omitted: vanilla has no natural biome spawn for it.
    // -----------------
    private static BiomeGenerationSettings warpedOasisGeneration(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, carvers);
        // Modest real water source -- not the guaranteed central lake from the design doc
        // (that needs real terrain shaping, still deferred), but enough for water-creature
        // spawns to actually have somewhere to happen.
        gen.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_WATER);
        // Dark Oak goes first, deliberately -- vanilla places features within a step in
        // registration order, and each one can fail if an earlier feature already claimed
        // that spot. Dark Oak needs a clear 2x2 patch (rare to begin with, on an already
        // tiny biome); registering it after the denser warped vegetation/bamboo meant it
        // was competing for ground that was likely already taken by the time its turn came.
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TreePlacements.DARK_OAK_CHECKED);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.WARPED_FOREST_VEGETATION);
        // WARPED_FOREST_VEGETATION is only ground cover (roots/sprouts/wart block) -- the
        // actual huge warped fungus trees are a separate placement, same as
        // TreePlacements.CRIMSON_FUNGI needed adding separately for Crimson River earlier.
        // This was the missing piece for "no warped trees appearing naturally."
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TreePlacements.WARPED_FUNGI);
        // NOTE: a PlacedFeature key can only appear once per biome -- listing it multiple
        // times to "boost density" causes a feature-order cycle crash (vanilla's
        // cross-biome FeatureSorter can't resolve a feature that must precede itself).
        // Reverted; density stays at each feature's own vanilla-defined rarity.
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BAMBOO_LIGHT);
        BiomeDefaultFeatures.addMossyStoneBlock(gen);
        return gen.build();
    }

    private static MobSpawnSettings warpedOasisSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 2, 3));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 4, 1, 2));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.TURTLE, 2, 1, 2));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 2, 1, 1));
        return spawns.build();
    }

    // -----------------
    // Deepslate Peaks -- sparse survival species depending on geothermal moisture.
    // -----------------
    private static BiomeGenerationSettings deepslatePeaksGeneration(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, carvers);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.PATCH_CRIMSON_ROOTS);
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.GLOW_LICHEN);
        BiomeDefaultFeatures.addMossyStoneBlock(gen);
        BiomeDefaultFeatures.addDefaultOres(gen);
        BiomeDefaultFeatures.addExtraEmeralds(gen);
        return gen.build();
    }

    private static MobSpawnSettings deepslatePeaksSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GOAT, 6, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 8, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 8, 1, 2));
        return spawns.build();
    }

    // -----------------
    // Deepslate Caves -- moist, luminescent underworld; the planet's "lungs."
    // Warped Mooshroom omitted: no such vanilla entity, parked for a future custom mob.
    // -----------------
    private static BiomeGenerationSettings deepslateCavesGeneration(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, carvers);
        BiomeDefaultFeatures.addLushCavesVegetationFeatures(gen);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.WARPED_FOREST_VEGETATION);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TreePlacements.WARPED_FUNGI);
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.GLOW_LICHEN);
        BiomeDefaultFeatures.addDripstone(gen);
        BiomeDefaultFeatures.addDefaultOres(gen);
        BiomeDefaultFeatures.addExtraEmeralds(gen);
        return gen.build();
    }

    private static MobSpawnSettings deepslateCavesSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 2, 4));
        spawns.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 2, 4));
        spawns.addSpawn(MobCategory.AXOLOTLS, new MobSpawnSettings.SpawnerData(EntityType.AXOLOTL, 6, 2, 3));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.STRAY, 3, 1, 1));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 6, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 3, 1, 1));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 3, 1, 1));
        return spawns.build();
    }

    // -----------------
    // Frozen Lava Tubes -- ancient magma tunnels cooled to deepslate and ice.
    // -----------------
    private static BiomeGenerationSettings frozenLavaTubesGeneration(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, carvers);
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.GLOW_LICHEN);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.WARPED_FOREST_VEGETATION);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TreePlacements.WARPED_FUNGI);
        BiomeDefaultFeatures.addDripstone(gen);
        BiomeDefaultFeatures.addDefaultCrystalFormations(gen);
        return gen.build();
    }

    private static MobSpawnSettings frozenLavaTubesSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 6, 1, 2));
        spawns.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 6, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.STRAY, 4, 1, 1));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 4, 1, 1));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 6, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 3, 1, 1));
        return spawns.build();
    }

    // -----------------
    // Crimson Grove -- recolored Forest. Uses vanilla's common, dense oak/birch
    // tree placements (not the sparse/rare variants used elsewhere this session)
    // so it's reliably, visibly tree-covered.
    // -----------------
    private static BiomeGenerationSettings crimsonGroveGeneration(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, carvers);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_BIRCH_AND_OAK);
        BiomeDefaultFeatures.addForestFlowers(gen);
        BiomeDefaultFeatures.addForestGrass(gen);
        BiomeDefaultFeatures.addDefaultMushrooms(gen);
        BiomeDefaultFeatures.addDefaultOres(gen);
        return gen.build();
    }

    private static MobSpawnSettings crimsonGroveSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 1, 2));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 4, 1, 2));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 3, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 40, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 30, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 15, 1, 2));
        return spawns.build();
    }

    // -----------------
    // Crimson Taiga -- recolored Taiga; cooler side of this dimension's palette.
    // Mixed with giant taiga vegetation (occasional mega spruce + huge mushrooms)
    // and a rare Dark Oak mixed in -- Dark Oak leaves use the tintable biome
    // foliage color (unlike spruce/birch, which are hardcoded and ignore biome
    // color overrides entirely), so it's the one tree here whose canopy will
    // actually show the red tint.
    // -----------------
    private static BiomeGenerationSettings crimsonTaigaGeneration(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, carvers);
        // Giant taiga vegetation (mega spruce, needs a 2x2 clear patch) and Dark Oak go
        // FIRST, deliberately -- vanilla places features within a step in registration
        // order, and each can fail if an earlier feature already claimed that spot.
        // Registering them after the dense regular addTaigaTrees meant they were
        // starved of open ground by the time their turn came, which is very likely why
        // neither ever appeared despite exploring multiple large Taiga instances.
        BiomeDefaultFeatures.addGiantTaigaVegetation(gen);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TreePlacements.DARK_OAK_CHECKED);
        BiomeDefaultFeatures.addTaigaTrees(gen);
        BiomeDefaultFeatures.addTaigaGrass(gen);
        BiomeDefaultFeatures.addCommonBerryBushes(gen);
        BiomeDefaultFeatures.addDefaultOres(gen);
        return gen.build();
    }

    private static MobSpawnSettings crimsonTaigaSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 6, 1, 2));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 4, 1, 2));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 3, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 40, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 30, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 15, 1, 2));
        return spawns.build();
    }

    // -----------------
    // Crimson Jungle -- recolored Jungle; hot, humid, dense.
    // -----------------
    private static BiomeGenerationSettings crimsonJungleGeneration(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, carvers);
        BiomeDefaultFeatures.addJungleTrees(gen);
        BiomeDefaultFeatures.addJungleGrass(gen);
        BiomeDefaultFeatures.addJungleVines(gen);
        BiomeDefaultFeatures.addJungleMelons(gen);
        BiomeDefaultFeatures.addDefaultOres(gen);
        return gen.build();
    }

    private static MobSpawnSettings crimsonJungleSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PARROT, 5, 1, 2));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.OCELOT, 4, 1, 2));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 40, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 30, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 15, 1, 2));
        return spawns.build();
    }

    // -----------------
    // Crimson Mangrove -- recolored Mangrove Swamp; coastal, wet, coral-toned.
    // -----------------
    private static BiomeGenerationSettings crimsonMangroveGeneration(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, carvers);
        BiomeDefaultFeatures.addMangroveSwampVegetation(gen);
        BiomeDefaultFeatures.addDefaultOres(gen);
        return gen.build();
    }

    private static MobSpawnSettings crimsonMangroveSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 8, 2, 3));
        spawns.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 4, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 40, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 30, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 15, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 5, 1, 1));
        return spawns.build();
    }

    // -----------------
    // Deepslate Shore -- barren, rocky coastline; ore-rich and exposed, like vanilla
    // Stony Shore. Fills a climate gap between Warm Ocean and Crimson River/Beach
    // that Deepslate Peaks was previously bleeding into.
    // -----------------
    private static BiomeGenerationSettings deepslateShoreGeneration(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, carvers);
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.GLOW_LICHEN);
        BiomeDefaultFeatures.addMossyStoneBlock(gen);
        // Cobbled deepslate boulders, interspersed with the mossy ones above rather than
        // replacing them -- discrete formations instead of the broad surface-level cobbled
        // deepslate swap that felt like too much.
        gen.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, CrimsonMoonPlacedFeatures.COBBLED_DEEPSLATE_BOULDER_SHORE);
        BiomeDefaultFeatures.addDefaultOres(gen);
        BiomeDefaultFeatures.addExtraEmeralds(gen);
        return gen.build();
    }

    private static MobSpawnSettings deepslateShoreSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, 5, 2, 3));
        spawns.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 4, 2, 3));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 40, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 30, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 15, 1, 2));
        return spawns.build();
    }

    // -----------------
    // Crimson Meadow -- recolored Meadow; cooler Plains-to-Peaks foothills transition.
    // -----------------
    private static BiomeGenerationSettings crimsonMeadowGeneration(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, carvers);
        BiomeDefaultFeatures.addMeadowVegetation(gen);
        BiomeDefaultFeatures.addCommonBerryBushes(gen);
        BiomeDefaultFeatures.addMossyStoneBlock(gen);
        // Same cobbled deepslate boulders as Deepslate Shore, at a lower rate here.
        gen.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, CrimsonMoonPlacedFeatures.COBBLED_DEEPSLATE_BOULDER_MEADOW);
        BiomeDefaultFeatures.addDefaultOres(gen);
        return gen.build();
    }

    private static MobSpawnSettings crimsonMeadowSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 6, 1, 2));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 4, 1, 2));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 3, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 40, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 30, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 15, 1, 2));
        return spawns.build();
    }
}
