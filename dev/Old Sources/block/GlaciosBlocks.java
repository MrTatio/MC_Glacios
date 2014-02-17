package glacios.block;

import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class GlaciosBlocks {

    public static final Block glacite = new BlockGlacite(200).setHardness(3F).setResistance(8F).setLightOpacity(5).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("glacite");
    public static final Block stairsGlacite = new BlockStairsGlacios(201, glacite, 0).setLightOpacity(5).setUnlocalizedName("stairsGlacite");
    public static final Block oreSilver = new BlockOreGlacios(202, -1).setHardness(3F).setResistance(5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreSilver");
    public static final BlockPortalGlacios portalGlacios = (BlockPortalGlacios) new BlockPortalGlacios(203).setHardness(-1F).setStepSound(Block.soundGlassFootstep).setLightValue(0.75F)
            .setUnlocalizedName("portalGlacios");
    public static final BlockIceGrass iceGrass = (BlockIceGrass) new BlockIceGrass(204).setHardness(0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("iceGrass");
    public static final Block iceLog = new BlockIceLog(205).setHardness(2.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("iceLog");
    public static final BlockIceLeaves iceLeaves = (BlockIceLeaves) new BlockIceLeaves(206).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("iceLeaves");
    public static final Block iceVine = new BlockIceVine(207).setHardness(0.2F).setStepSound(Block.soundGrassFootstep).setLightValue(0.35F).setUnlocalizedName("iceVine");
    public static final Block slate = new BlockOreGlacios(208, -1).setHardness(1F).setResistance(8F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("slate");
    public static final Block stairsSlate = new BlockStairsGlacios(209, slate, 0).setUnlocalizedName("stairsSlate");
    public static final Block wallSlate = new BlockWallGlacios(210, slate).setUnlocalizedName("wallSlate");
    public static final Block bricksSlate = new BlockOreGlacios(211, -1).setUnlocalizedName("bricksSlate");

    public static void RegisterBlocks() {
        GameRegistry.registerBlock(glacite, "glacite");
        LanguageRegistry.addName(glacite, "Glacite");
        MinecraftForge.setBlockHarvestLevel(glacite, "pickaxe", 1);

        GameRegistry.registerBlock(stairsGlacite, "stairsGlacite");
        LanguageRegistry.addName(stairsGlacite, "Glacite Stairs");

        GameRegistry.registerBlock(oreSilver, "oreSilver");
        LanguageRegistry.addName(oreSilver, "Silver Ore");
        MinecraftForge.setBlockHarvestLevel(oreSilver, "pickaxe", 2);
        OreDictionary.registerOre("oreSilver", oreSilver);

        GameRegistry.registerBlock(portalGlacios, "portalGlacios");
        LanguageRegistry.addName(portalGlacios, "Glacios Portal");

        GameRegistry.registerBlock(iceGrass, "iceGrass");
        LanguageRegistry.addName(iceGrass, "Ice Grass");

        GameRegistry.registerBlock(iceLog, "iceLog");
        LanguageRegistry.addName(iceLog, "Ice Log");

        GameRegistry.registerBlock(iceLeaves, "iceLeaves");
        LanguageRegistry.addName(iceLeaves, "Ice Leaves");

        GameRegistry.registerBlock(iceVine, "iceVine");
        LanguageRegistry.addName(iceVine, "Ice Vine");

        GameRegistry.registerBlock(slate, "slate");
        LanguageRegistry.addName(slate, "Slate");

        GameRegistry.registerBlock(stairsSlate, "stairsSlate");
        LanguageRegistry.addName(stairsSlate, "Slate Stairs");

        GameRegistry.registerBlock(wallSlate, "wallSlate");
        LanguageRegistry.addName(wallSlate, "Slate Wall");

        GameRegistry.registerBlock(bricksSlate, "bricksSlate");
        LanguageRegistry.addName(bricksSlate, "Slate Bricks");
    }

}
