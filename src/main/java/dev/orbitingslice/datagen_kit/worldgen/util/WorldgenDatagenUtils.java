package dev.orbitingslice.datagen_kit.worldgen.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;

/**
 * General-purpose helper functions used throughout the Orbiting Slice Datagen Kit.
 *
 * This class focuses on:
 *  - consistent ResourceLocation creation
 *  - registry key utilities for worldgen registries
 *  - file path and PackOutput helpers for JSON validation or manual debugging
 *
 * Keep this class lightweight — no NeoForge or mod-specific dependencies.
 */
public final class WorldgenDatagenUtils {

    private WorldgenDatagenUtils() {} // Static-only class — no instances.

    /**
     * Creates a ResourceLocation within a given mod namespace.
     * Useful when generating or referencing worldgen assets dynamically.
     */
    public static ResourceLocation rl(String modid, String path) {
        return ResourceLocation.fromNamespaceAndPath(modid, path);
    }

    /** Returns a ResourceKey for a biome. */
    public static ResourceKey<Biome> biomeKey(String modid, String name) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(modid, name));
    }

    /** Returns a ResourceKey for a noise_settings entry. */
    public static ResourceKey<NoiseGeneratorSettings> noiseKey(String modid, String name) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, ResourceLocation.fromNamespaceAndPath(modid, name));
    }

    /**
     * Resolves an absolute output path for a given worldgen registry JSON.
     * This can be used in debugging or validation scenarios to confirm that DataGen output exists.
     *
     * Example: logs absolute path to generated biome JSONs for visual inspection.
     */
    public static Path resolveWorldgenPath(PackOutput output, String modid, String folder, String fileName) {
        return output.getOutputFolder()
                .resolve("data/" + modid + "/worldgen/" + folder + "/" + fileName + ".json");
    }

    /**
     * Simple console logging utility for DataGen processes.
     * Safe for use during datagen; does not rely on Log4j/Minecraft logger.
     */
    public static void log(String context, String message) {
        System.out.printf("[DatagenKit:%s] %s%n", context, message);
    }
}
