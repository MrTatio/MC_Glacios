package roundaround.mcmods.glacios.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import roundaround.mcmods.glacios.Glacios;
import roundaround.mcmods.glacios.GlaciosBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGelisolFrost extends Block {

    @SideOnly(Side.CLIENT)
    private IIcon iconTop;

    public BlockGelisolFrost() {
        super(Material.ground);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? this.iconTop : (side == 0 ? GlaciosBlocks.gelisol.getBlockTextureFromSide(side) : this.blockIcon);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return side == 1 ? this.iconTop : (side == 0 ? GlaciosBlocks.gelisol.getBlockTextureFromSide(side) : this.blockIcon);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = register.registerIcon(Glacios.MODID + ":" + this.getUnlocalizedName().substring(5) + "_side");
        this.iconTop = register.registerIcon(Glacios.MODID + ":" + this.getUnlocalizedName().substring(5) + "_top");
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (!world.isRemote) { // TODO: Add warmth detection.
//            world.setBlock(x, y, z, GlaciosBlocks.gelisol);
        }
    }

    @Override
    public void onPlantGrow(World world, int x, int y, int z, int sourceX, int sourceY, int sourceZ) {
        world.setBlock(x, y, z, GlaciosBlocks.gelisol, 0, 2);
    }

}
