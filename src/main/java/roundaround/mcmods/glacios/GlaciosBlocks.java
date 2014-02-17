package roundaround.mcmods.glacios;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import roundaround.mcmods.glacios.block.BlockAsh;
import roundaround.mcmods.glacios.block.BlockAshStone;
import roundaround.mcmods.glacios.block.BlockDynamicLiquidGlacios;
import roundaround.mcmods.glacios.block.BlockGelisol;
import roundaround.mcmods.glacios.block.BlockGelisolFrost;
import roundaround.mcmods.glacios.block.BlockIceVine;
import roundaround.mcmods.glacios.block.BlockPortalGlacios;
import roundaround.mcmods.glacios.block.BlockPrismShard;
import roundaround.mcmods.glacios.block.BlockRazorGrass;
import roundaround.mcmods.glacios.block.BlockSlate;
import roundaround.mcmods.glacios.block.BlockSoulLeaves;
import roundaround.mcmods.glacios.block.BlockSoulLog;
import roundaround.mcmods.glacios.block.BlockSoulSapling;
import roundaround.mcmods.glacios.block.BlockStaticLiquidGlacios;
import roundaround.mcmods.glacios.block.BlockWhiteObsidian;
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
    public static Block gelisol;
    public static Block gelisolFrost;
    public static Block prismShard;
    public static Block whiteObsidian;
    public static Block portalGlacios;
    public static Block crystalWater;
    public static Block flowing_crystalWater;

    public static void init() {
        razorGrass = new BlockRazorGrass().setBlockName("razorGrass").setBlockTextureName(Glacios.MODID + ":razorGrass");
        soulLog = new BlockSoulLog().setBlockName("soulLog").setBlockTextureName(Glacios.MODID + ":soulLog");
        soulLeaves = new BlockSoulLeaves().setBlockName("soulLeaves").setBlockTextureName(Glacios.MODID + ":soulLeaves");
        soulSapling = new BlockSoulSapling().setBlockName("soulSapling").setBlockTextureName(Glacios.MODID + ":soulSapling");
        iceVine = new BlockIceVine().setBlockName("iceVine").setBlockTextureName(Glacios.MODID + ":iceVine");
        ash = new BlockAsh().setBlockName("ash").setBlockTextureName(Glacios.MODID + ":ash");
        ashStone = new BlockAshStone().setBlockName("ashStone").setBlockTextureName(Glacios.MODID + ":ashStone");
        slate = new BlockSlate().setBlockName("slate").setBlockTextureName(Glacios.MODID + ":slate");
        gelisol = new BlockGelisol().setBlockName("gelisol").setBlockTextureName(Glacios.MODID + ":gelisol");
        gelisolFrost = new BlockGelisolFrost().setBlockName("gelisolFrost").setBlockTextureName(Glacios.MODID + ":gelisolFrost");
        prismShard = new BlockPrismShard().setBlockName("prismShard").setBlockTextureName(Glacios.MODID + ":prismShard");
        whiteObsidian = new BlockWhiteObsidian().setBlockName("whiteObsidian").setBlockTextureName(Glacios.MODID + ":whiteObsidian");
        portalGlacios = new BlockPortalGlacios().setBlockName("portalGlacios").setBlockTextureName(Glacios.MODID + ":portalGlacios");
        crystalWater = new BlockStaticLiquidGlacios(Material.water).setBlockName("crystalWater_still").setBlockTextureName(Glacios.MODID + ":crystalWater_still");
        flowing_crystalWater = new BlockDynamicLiquidGlacios(Material.water).setBlockName("crystalWater_flow").setBlockTextureName(Glacios.MODID + ":crystalWater_flow");

        registerBlock(razorGrass);
        registerBlock(soulLog);
        registerBlock(soulLeaves);
        // registerBlock(soulSapling);
        registerBlock(iceVine);
        registerBlock(ash);
        registerBlock(ashStone);
        registerBlock(slate);
        registerBlock(gelisol);
        registerBlock(gelisolFrost);
        // registerBlock(prismShard);
        // registerBlock(whiteObsidian);
        registerBlock(portalGlacios);
        registerBlock(crystalWater);
        registerBlock(flowing_crystalWater);
    }

    private static void registerBlock(Block block) {
        GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
    }

}
