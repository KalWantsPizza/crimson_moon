package dev.orbitingslice.crimson_moon.datagen;

import dev.orbitingslice.crimson_moon.CrimsonMoon;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CrimsonMoon.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
