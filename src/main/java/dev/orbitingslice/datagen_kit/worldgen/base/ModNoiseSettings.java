package dev.orbitingslice.datagen_kit.worldgen.base;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;

/**
 * Base class for noise_generator_settings DataGen providers.
 *
 * Defines the vertical range and cell sizes for terrain noise.
 * Mods subclass this to build terrain shape profiles for dimensions.
 */
public abstract class ModNoiseSettings {

    protected final String modid;
    protected final PackOutput output;

    public ModNoiseSettings(PackOutput output, String modid) {
        this.output = output;
        this.modid = modid;
    }

    // Updated to NoiseGeneratorSettings
    public abstract void buildNoiseSettings(BootstrapContext<NoiseGeneratorSettings> context);

    /** Utility for registry key creation */
    protected ResourceKey<NoiseGeneratorSettings> noiseKey(String name) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, ResourceLocation.fromNamespaceAndPath(modid, name));
    }

    /** Creates an Overworld-like base noise settings — override for Nether-style profiles */
    protected NoiseSettings makeBaseNoiseSettings() {
        return NoiseSettings.create(-64, 384, 1, 2);
    }

    /** Optional hook for direct RegistrySetBuilder integration */
    public static void registerTo(RegistrySetBuilder builder, ModNoiseSettings instance) {
        builder.add(Registries.NOISE_SETTINGS, instance::buildNoiseSettings);
    }
}