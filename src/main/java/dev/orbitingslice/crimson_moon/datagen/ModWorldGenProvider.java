package dev.orbitingslice.crimson_moon.datagen;

import dev.orbitingslice.crimson_moon.CrimsonMoon;
import dev.orbitingslice.crimson_moon.datagen.worldgen.CrimsonMoonBiomes;
import dev.orbitingslice.crimson_moon.datagen.worldgen.CrimsonMoonDimension;
import dev.orbitingslice.crimson_moon.datagen.worldgen.CrimsonMoonDimensionType;
import dev.orbitingslice.crimson_moon.datagen.worldgen.CrimsonMoonNoiseGeneratorSettings;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, CrimsonMoonBiomes::bootstrap)
            .add(Registries.NOISE_SETTINGS, CrimsonMoonNoiseGeneratorSettings::bootstrap)
            .add(Registries.DIMENSION_TYPE, CrimsonMoonDimensionType::bootstrap)
            .add(Registries.LEVEL_STEM, CrimsonMoonDimension::bootstrap);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(CrimsonMoon.MOD_ID));
    }
}