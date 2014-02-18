package roundaround.mcmods.glacios;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class GlaciosItems {
    
    public static void init() {
    }
    
    private static void registerItem(Item item) {
        GameRegistry.registerItem(item, item.getUnlocalizedName(), Glacios.MODID);
    }
}
