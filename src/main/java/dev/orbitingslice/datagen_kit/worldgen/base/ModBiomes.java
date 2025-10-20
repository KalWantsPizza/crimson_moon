package dev.orbitingslice.datagen_kit.worldgen.base;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.concurrent.CompletableFuture;

/**
 * Base biome registration provider for NeoForge 1.21.1 DataGen.
 *
 * Mods extend this class and implement {@link #buildBiomes(BootstrapContext)}.
 * It provides safe, schema-compliant biome construction for JSON generation.
 */
public abstract class ModBiomes {

    protected final String modid;
    protected final PackOutput output;

    public ModBiomes(PackOutput output, String modid, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        this.output = output;
        this.modid = modid;
    }

    /**
     * Entry point for all biome registration.
     * Called automatically by DatapackBuiltinEntriesProvider during DataGen.
     */
    public abstract void buildBiomes(BootstrapContext<Biome> context);

    /** Utility for building a ResourceKey<Biome> safely with the mod’s namespace. */
    protected ResourceKey<Biome> biomeKey(String name) {
        return ResourceKey.create(
                Registries.BIOME,
                ResourceLocation.fromNamespaceAndPath(modid, name)
        );
    }

    /**
     * Returns a minimal, schema-safe Biome object for use in DataGen.
     * Mods can override or expand this (e.g., add features, spawns, or sounds).
     *
     * NOTE: 1.21.1 requires passing lookup holders for PlacedFeature and ConfiguredWorldCarver.
     */
    protected Biome makeBaseBiome(BootstrapContext<Biome> context,
                                  float temperature, float downfall,
                                  int fogColor, int waterColor, int skyColor) {

        // Retrieve registry lookups from bootstrap context
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var carvers = context.lookup(Registries.CONFIGURED_CARVER);

        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(fogColor)
                .waterColor(waterColor)
                .waterFogColor(waterColor)
                .skyColor(skyColor)
                .build();

        BiomeGenerationSettings generation = new BiomeGenerationSettings.Builder(
                placedFeatures, carvers
        ).build();

        MobSpawnSettings spawns = new MobSpawnSettings.Builder().build();

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(effects)
                .mobSpawnSettings(spawns)
                .generationSettings(generation)
                .build();
    }

    /** Hook for integrating with RegistrySetBuilder (optional chaining). */
    public static void registerTo(RegistrySetBuilder builder, ModBiomes instance) {
        builder.add(Registries.BIOME, instance::buildBiomes);
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        var carver = context.lookup(Registries.CONFIGURED_CARVER);
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        /*
        register(context, KAUPEN_VALLEY, ModOverworldBiomes.kaupenValley(placedFeatures, carver));
        register(context, GLOWSTONE_PLAIN, ModNetherBiomes.glowstonePlains(placedFeatures, carver));
        register(context, END_ROT, ModEndBiomes.endRot(placedFeatures, carver));

         */
    }
    private static void register(BootstrapContext<Biome> context, ResourceKey<Biome> key, Biome biome) {
        context.register(key, biome);
    }
}