package roundaround.mcmods.glacios;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import roundaround.mcmods.glacios.block.BlockPortalGlacios;
import roundaround.mcmods.glacios.block.BlockSlate;
import cpw.mods.fml.common.registry.GameRegistry;

public class GlaciosBlocks {
    public static Block slate;
    public static Block portalGlacios;
    
    public static void initBlocks() {
        slate = new BlockSlate().func_149663_c("slate").func_149658_d(Glacios.MODID + ":slate");
        portalGlacios = new BlockPortalGlacios().func_149663_c("portalGlacios").func_149658_d(Glacios.MODID + ":portalGlacios");
        
        registerBlock(slate, ItemBlock.class);
        registerBlock(portalGlacios, ItemBlock.class);
    }
    
    private static void registerBlock(Block block) {
        GameRegistry.registerBlock(block, block.func_149739_a().substring(5));
    }
    
    private static void registerBlock(Block block, Class pickup) {
        GameRegistry.registerBlock(block, pickup, block.func_149739_a().substring(5), Glacios.MODID);
    }

}
