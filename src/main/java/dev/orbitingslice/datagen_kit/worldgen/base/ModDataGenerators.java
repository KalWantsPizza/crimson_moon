package dev.orbitingslice.datagen_kit.worldgen.base;

import dev.orbitingslice.crimson_moon.CrimsonMoon;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

/**
 * Central NeoForge 1.21.1 DataGen orchestrator.
 *
 * Collects all registry providers (biomes, noise_settings, etc.) and builds them
 * into a DatapackBuiltinEntriesProvider. Mods subclass this to plug in their
 * concrete implementations.
 */
//@EventBusSubscriber(modid = CrimsonMoon.MOD_ID)
public class ModDataGenerators {

    /*
    public static void gatherData(GatherDataEvent event) {
        PackOutput output = event.getGenerator().getPackOutput();
        String modid = event.getModContainer().getModId();

        // Build the registry graph for biomes, noise settings, etc.
        RegistrySetBuilder registryBuilder = new RegistrySetBuilder()
                .add(Registries.BIOME, ctx -> {
                    ModBiomes biomes = event.includeServer() ? createBiomes(output, modid) : null;
                    if (biomes != null) biomes.buildBiomes(ctx);
                })
                .add(Registries.NOISE_SETTINGS, ctx -> {
                    ModNoiseSettings noise = event.includeServer() ? createNoise(output, modid) : null;
                    if (noise != null) noise.buildNoiseSettings(ctx);
                });

        // Register the datapack provider.
        event.getGenerator().addProvider(
                event.includeServer(),
                new DatapackBuiltinEntriesProvider(
                        output,
                        event.getLookupProvider(),
                        registryBuilder,
                        Set.of(modid)
                )
        );
    }

     */

    /** Factory methods to be overridden per mod. */
    protected static ModBiomes createBiomes(PackOutput output, String modid) { return null; }
    protected static ModNoiseSettings createNoise(PackOutput output, String modid) { return null; }
}
