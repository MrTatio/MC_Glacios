package glacios.item;

import glacios.block.GlaciosBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class GlaciosItems {

    public static EnumToolMaterial SILVER = EnumHelper.addToolMaterial("SILVER", 2, 250, 6.0F, 2, 22);

    public static final Item glaciteCrystal = new ItemGlaciteCrystal(4999).setUnlocalizedName("glaciteCrystal").setCreativeTab(CreativeTabs.tabMaterials);
    public static final Item ingotSilver = new ItemGlacios(5000).setUnlocalizedName("ingotSilver").setCreativeTab(CreativeTabs.tabMaterials);
    public static final Item pickaxeSilver = new ItemPickaxeGlacios(5001, SILVER).setUnlocalizedName("pickaxeSilver").setCreativeTab(CreativeTabs.tabTools);
    public static final Item axeSilver = new ItemAxeGlacios(5002, SILVER).setUnlocalizedName("axeSilver").setCreativeTab(CreativeTabs.tabTools);
    public static final Item shovelSilver = new ItemSpadeGlacios(5003, SILVER).setUnlocalizedName("shovelSilver").setCreativeTab(CreativeTabs.tabTools);
    public static final Item hoeSilver = new ItemHoeGlacios(5004, SILVER).setUnlocalizedName("hoeSilver").setCreativeTab(CreativeTabs.tabTools);
    public static final Item swordSilver = new ItemSwordGlacios(5005, SILVER).setUnlocalizedName("swordSilver").setCreativeTab(CreativeTabs.tabCombat);

    public static final Item recordFreeze = new ItemIceRecord(5006, "Freeze").setUnlocalizedName("record");
    public static final Item recordGlide = new ItemIceRecord(5007, "Glide").setUnlocalizedName("record");
    public static final Item recordMelt = new ItemIceRecord(5008, "Melt").setUnlocalizedName("record");
    public static final Item recordIce = new ItemIceRecord(5009, "Ice").setUnlocalizedName("record");

    public static void RegisterItems() {
        LanguageRegistry.addName(glaciteCrystal, "Glacite Crystal");

        LanguageRegistry.addName(ingotSilver, "Silver Ingot");
        OreDictionary.registerOre("ingotSilver", ingotSilver);
        GameRegistry.addSmelting(GlaciosBlocks.oreSilver.blockID, new ItemStack(ingotSilver), 0.7F);

        LanguageRegistry.addName(pickaxeSilver, "Silver Pickaxe");
        MinecraftForge.setToolClass(pickaxeSilver, "pickaxe", 2);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pickaxeSilver, 1), new Object[] { "XXX", " Y ", " Y ", Character.valueOf('X'), "ingotSilver", Character.valueOf('Y'), Item.stick }));

        LanguageRegistry.addName(axeSilver, "Silver Axe");
        MinecraftForge.setToolClass(axeSilver, "axe", 2);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(axeSilver, 1), new Object[] { "XX ", "XY ", " Y ", Character.valueOf('X'), "ingotSilver", Character.valueOf('Y'), Item.stick }));

        LanguageRegistry.addName(shovelSilver, "Silver Shovel");
        MinecraftForge.setToolClass(shovelSilver, "shovel", 2);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(shovelSilver, 1), new Object[] { " X ", " Y ", " Y ", Character.valueOf('X'), "ingotSilver", Character.valueOf('Y'), Item.stick }));

        LanguageRegistry.addName(hoeSilver, "Silver Hoe");
        MinecraftForge.setToolClass(hoeSilver, "hoe", 2);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hoeSilver, 1), new Object[] { "XX ", " Y ", " Y ", Character.valueOf('X'), "ingotSilver", Character.valueOf('Y'), Item.stick }));

        LanguageRegistry.addName(swordSilver, "Silver Sword");
        MinecraftForge.setToolClass(swordSilver, "sword", 2);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hoeSilver, 1), new Object[] { " X ", " X ", " Y ", Character.valueOf('X'), "ingotSilver", Character.valueOf('Y'), Item.stick }));

    }

}
