package net.NindyBun.toxicbladeworks.data;

import net.NindyBun.toxicbladeworks.ToxicBladeworks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class GeneratorLang extends LanguageProvider {
    public GeneratorLang(PackOutput output, String locale) {
        super(output, ToxicBladeworks.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + ToxicBladeworks.MODID + ".creativeTab", "Toxic Bladeworks");
        add("tooltip."+ToxicBladeworks.MODID+".potion_volume", "%d/250mb");
    }
}
