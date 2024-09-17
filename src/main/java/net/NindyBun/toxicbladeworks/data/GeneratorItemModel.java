package net.NindyBun.toxicbladeworks.data;

import net.NindyBun.toxicbladeworks.ToxicBladeworks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class GeneratorItemModel extends ItemModelProvider {
    public GeneratorItemModel(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ToxicBladeworks.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
