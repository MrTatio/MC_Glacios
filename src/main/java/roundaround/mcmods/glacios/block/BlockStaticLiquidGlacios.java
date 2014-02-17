package roundaround.mcmods.glacios.block;

import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import roundaround.mcmods.glacios.Glacios;
import roundaround.mcmods.glacios.GlaciosBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStaticLiquidGlacios extends BlockStaticLiquid {

    public BlockStaticLiquidGlacios(Material material) {
        super(material);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(Glacios.MODID + ":" + this.getUnlocalizedName().substring(5));
    }
    
    @Override
    public IIcon getIcon(int side, int meta) {
        return this.blockIcon;
    }

    @SideOnly(Side.CLIENT)
    public static IIcon getLiquidIcon(String name) {
        return GlaciosBlocks.crystalWater.getIcon(0, 0);
    }

}
