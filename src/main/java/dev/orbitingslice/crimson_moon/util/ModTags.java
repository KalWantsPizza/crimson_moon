package dev.orbitingslice.crimson_moon.util;

import dev.orbitingslice.crimson_moon.CrimsonMoon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks{
        private static TagKey<Block> createTag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(CrimsonMoon.MOD_ID, name));
        }
    }

    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(CrimsonMoon.MOD_ID, name));
        }

        //public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");
    }

}
