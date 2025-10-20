package dev.orbitingslice.crimson_moon.datagen.worldgen.util;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import com.mojang.datafixers.util.Pair;

import java.util.List;

import static dev.orbitingslice.crimson_moon.CrimsonMoon.resource;

public class CrimsonClimateHelper {

    public static BiomeClimateBuilder biomePair(HolderGetter<Biome> biomes, String biomeId) {
        return new BiomeClimateBuilder(biomes, biomeId);
    }

    public static Climate.ParameterList<Holder<Biome>> buildList(List<Pair<Climate.ParameterPoint, Holder<Biome>>> pairs) {
        return new Climate.ParameterList<>(pairs);
    }

    public static class BiomeClimateBuilder {
        private float minTemp = 0.0f, maxTemp = 0.0f;
        private float minHumidity = 0.0f, maxHumidity = 0.0f;
        private float minContinentalness = 0.0f, maxContinentalness = 0.0f;
        private float minErosion = 0.0f, maxErosion = 0.0f;
        private float minWeirdness = 0.0f, maxWeirdness = 0.0f;
        private float minDepth = 0.0f, maxDepth = 0.0f;
        private long offset = 0L;
        private final Holder<Biome> biomeHolder;

        public BiomeClimateBuilder(HolderGetter<Biome> biomes, String biomeId) {
            ResourceLocation rl = biomeId.contains(":") ? ResourceLocation.parse(biomeId) : resource(biomeId);
            ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, rl);
            this.biomeHolder = biomes.getOrThrow(key);
        }

        public BiomeClimateBuilder temp(float min, float max) {
            this.minTemp = min;
            this.maxTemp = max;
            return this;
        }

        public BiomeClimateBuilder humidity(float min, float max) {
            this.minHumidity = min;
            this.maxHumidity = max;
            return this;
        }

        public BiomeClimateBuilder continentalness(float min, float max) {
            this.minContinentalness = min;
            this.maxContinentalness = max;
            return this;
        }

        public BiomeClimateBuilder erosion(float min, float max) {
            this.minErosion = min;
            this.maxErosion = max;
            return this;
        }

        public BiomeClimateBuilder weirdness(float min, float max) {
            this.minWeirdness = min;
            this.maxWeirdness = max;
            return this;
        }

        public BiomeClimateBuilder depth(float min, float max) {
            this.minDepth = min;
            this.maxDepth = max;
            return this;
        }

        public BiomeClimateBuilder offset(long offset) {
            this.offset = offset;
            return this;
        }

        public Pair<Climate.ParameterPoint, Holder<Biome>> build() {
            return Pair.of(
                Climate.parameters(
                    Climate.Parameter.span(minTemp, maxTemp),
                    Climate.Parameter.span(minHumidity, maxHumidity),
                    Climate.Parameter.span(minContinentalness, maxContinentalness),
                    Climate.Parameter.span(minErosion, maxErosion),
                    Climate.Parameter.span(minWeirdness, maxWeirdness),
                    Climate.Parameter.span(minDepth, maxDepth),
                    offset
                ),
                biomeHolder
            );
        }
    }
}
