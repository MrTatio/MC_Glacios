package roundaround.mcmods.glacios;

import net.minecraft.block.Block;
import roundaround.mcmods.glacios.block.BlockAsh;
import roundaround.mcmods.glacios.block.BlockAshStone;
import roundaround.mcmods.glacios.block.BlockIceVine;
import roundaround.mcmods.glacios.block.BlockPortalGlacios;
import roundaround.mcmods.glacios.block.BlockRazorGrass;
import roundaround.mcmods.glacios.block.BlockSlate;
import roundaround.mcmods.glacios.block.BlockSoulLeaves;
import roundaround.mcmods.glacios.block.BlockSoulLog;
import roundaround.mcmods.glacios.block.BlockSoulSapling;
import cpw.mods.fml.common.registry.GameRegistry;

public class GlaciosBlocks {
    public static Block razorGrass;
    public static Block soulLog;
    public static Block soulLeaves;
    public static Block soulSapling;
    public static Block iceVine;
    public static Block ash;
    public static Block ashStone;
    public static Block slate;
    public static Block portalGlacios;
    
    public static void init() {
        razorGrass = new BlockRazorGrass().func_149663_c("razorGrass").func_149658_d(Glacios.MODID + ":razorGrass");
        soulLog = new BlockSoulLog().func_149663_c("soulLog").func_149658_d(Glacios.MODID + ":soulLog");
        soulLeaves = new BlockSoulLeaves().func_149663_c("soulLeaves").func_149658_d(Glacios.MODID + ":soulLeaves");
        soulSapling = new BlockSoulSapling().func_149663_c("soulSapling").func_149658_d(Glacios.MODID + ":soulSapling");
        iceVine = new BlockIceVine().func_149663_c("iceVine").func_149658_d(Glacios.MODID + ":iceVine");
        ash = new BlockAsh().func_149663_c("ash").func_149658_d(Glacios.MODID + ":ash");
        ashStone = new BlockAshStone().func_149663_c("ashStone").func_149658_d(Glacios.MODID + ":ashStone");
        slate = new BlockSlate().func_149663_c("slate").func_149658_d(Glacios.MODID + ":slate");
        portalGlacios = new BlockPortalGlacios().func_149663_c("portalGlacios").func_149658_d(Glacios.MODID + ":portalGlacios");
        
        registerBlock(razorGrass);
        registerBlock(soulLog);
        registerBlock(soulLeaves);
        registerBlock(soulSapling);
        registerBlock(iceVine);
        registerBlock(ash);
        registerBlock(ashStone);
        registerBlock(slate);
        registerBlock(portalGlacios);
    }
    
    private static void registerBlock(Block block) {
        GameRegistry.registerBlock(block, block.func_149739_a().substring(5));
    }

}
