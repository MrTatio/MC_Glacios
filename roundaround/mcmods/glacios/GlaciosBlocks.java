package roundaround.mcmods.glacios;

import net.minecraft.block.Block;
import roundaround.mcmods.glacios.block.BlockPortalGlacios;
import roundaround.mcmods.glacios.block.BlockSlate;
import cpw.mods.fml.common.registry.GameRegistry;

public class GlaciosBlocks {
    public static Block razerGrass;
    public static Block lifeLog;
    public static Block soulLeaves;
    public static Block slate;
    public static Block portalGlacios;
    
    public static void init() {
        //razerGrass = new BlockRazerGrass().func_149663_c("razerGrass").func_149658_d(Glacios.MODID + ":razerGrass");
        //lifeLog = new BlockLifeLog().func_149663_c("lifeLog").func_149658_d(Glacios.MODID + ":lifeLog");
        //soulLeaves = new BlockSoulLeaves().func_149663_c("soulLeaves").func_149658_d(Glacios.MODID + ":soulLeaves");
        slate = new BlockSlate().func_149663_c("slate").func_149658_d(Glacios.MODID + ":slate");
        portalGlacios = new BlockPortalGlacios().func_149663_c("portalGlacios").func_149658_d(Glacios.MODID + ":portalGlacios");
        
        //registerBlock(razerGrass);
        //registerBlock(lifeLog);
        //registerBlock(soulLeaves);
        registerBlock(slate);
        registerBlock(portalGlacios);
    }
    
    private static void registerBlock(Block block) {
        GameRegistry.registerBlock(block, block.func_149739_a().substring(5));
    }

}
