package roundaround.mcmods.glacios;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import roundaround.mcmods.glacios.block.*;
import roundaround.mcmods.glacios.client.renderer.tileentity.RendererPrismShard;
import roundaround.mcmods.glacios.item.ItemBlockLeaves;
import roundaround.mcmods.glacios.item.ItemBlockLog;
import roundaround.mcmods.glacios.item.ItemBlockSapling;
import roundaround.mcmods.glacios.tileentity.TileEntityPrismShard;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GlaciosBlocks {
    public static Block razorGrass;
    public static Block log;
    public static Block leaves;
    public static Block sapling;
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
        log = new BlockLogGlacios().setBlockName("log").setBlockTextureName(Glacios.MODID + ":log");
        leaves = new BlockLeavesGlacios().setBlockName("leaves").setBlockTextureName(Glacios.MODID + ":leaves");
        sapling = new BlockSaplingGlacios().setBlockName("sapling").setBlockTextureName(Glacios.MODID + ":sapling");
        iceVine = new BlockIceVine().setBlockName("iceVine").setBlockTextureName(Glacios.MODID + ":iceVine");
        ash = new BlockAsh().setBlockName("ash").setBlockTextureName(Glacios.MODID + ":ash");
        ashStone = new BlockAshStone().setBlockName("ashStone").setBlockTextureName(Glacios.MODID + ":ashStone");
        slate = new BlockSlate().setBlockName("slate").setBlockTextureName(Glacios.MODID + ":slate");
        gelisol = new BlockGelisol().setBlockName("gelisol").setBlockTextureName(Glacios.MODID + ":gelisol");
        gelisolFrost = new BlockGelisolFrost().setBlockName("gelisolFrost").setBlockTextureName(Glacios.MODID + ":gelisolFrost");
        prismShard = new BlockPrismShard().setBlockName("prismShard").setBlockTextureName(Glacios.MODID + ":prismShard_square");
        whiteObsidian = new BlockWhiteObsidian().setBlockName("whiteObsidian").setBlockTextureName(Glacios.MODID + ":whiteObsidian");
        portalGlacios = new BlockPortalGlacios().setBlockName("portalGlacios").setBlockTextureName(Glacios.MODID + ":portalGlacios");
        crystalWater = new BlockStaticLiquidGlacios(Material.water).setBlockName("crystalWater_still").setBlockTextureName(Glacios.MODID + ":crystalWater_still");
        flowing_crystalWater = new BlockDynamicLiquidGlacios(Material.water).setBlockName("crystalWater_flow").setBlockTextureName(Glacios.MODID + ":crystalWater_flow");

        registerBlock(razorGrass);
        registerBlock(log, ItemBlockLog.class);
        registerBlock(leaves, ItemBlockLeaves.class);
        registerBlock(sapling, ItemBlockSapling.class);
        registerBlock(iceVine);
        registerBlock(ash);
        registerBlock(ashStone);
        registerBlock(slate);
        registerBlock(gelisol);
        registerBlock(gelisolFrost);
        registerBlock(prismShard);
        registerBlock(whiteObsidian);
        registerBlock(portalGlacios);
        registerBlock(crystalWater);
        registerBlock(flowing_crystalWater);

        GameRegistry.registerTileEntity(TileEntityPrismShard.class, "tileEntityPrismShard");
        
        registerRenderers();
    }

    @SideOnly(Side.CLIENT)
    private static void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPrismShard.class, new RendererPrismShard());
        RenderingRegistry.registerBlockHandler(new RendererPrismShard());
    }

    private static void registerBlock(Block block) {
        GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
    }
    
    private static void registerBlock(Block block, Class<? extends ItemBlock> itemBlockClass) {
        GameRegistry.registerBlock(block, itemBlockClass, block.getUnlocalizedName().substring(5));
    }
    
    private static void registerBlock(Block block, Class<? extends ItemBlock> itemBlockClass, Object... itemBlockArgs) {
        GameRegistry.registerBlock(block, itemBlockClass, block.getUnlocalizedName().substring(5), Glacios.MODID, itemBlockArgs);
    }

}
