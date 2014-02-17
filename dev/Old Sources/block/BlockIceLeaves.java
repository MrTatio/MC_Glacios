package glacios.block;

import glacios.core.Glacios;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIceLeaves extends BlockLeaves {

    Icon blockIconOpaque;

    public BlockIceLeaves(int id) {
        super(id);
    }

    /*
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon("Glacios:" + this.getUnlocalizedName().substring(5));
        blockIconOpaque = iconRegister.registerIcon("Glacios:" + this.getUnlocalizedName().substring(5));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        return 16777215;
    }

    /*
     * Returns the color this block should be rendered. Used by leaves.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int par1) {
        return 16777215;
    }

    /*
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        return 16777215;
    }

    /*
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return GlaciosBlocks.iceGrass.blockID;
    }

    /*
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(int par1) {
        return 0;
    }

    /*
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2) {
        return graphicsLevel ? blockIcon : blockIconOpaque;
    }

    /*
     * Returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
    }

    /*
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    @Override
    protected ItemStack createStackedBlock(int par1) {
        return new ItemStack(blockID, 1, 0);
    }

    /*
     * A randomly called display update to be able to add particles or other items for display
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int posX, int posY, int posZ, Random rand) {
        if (rand.nextInt(4) != 0)
            return;

        int i = rand.nextInt(4);
        double x = posX + rand.nextFloat();
        double y = (float) posY - 1 + rand.nextFloat();
        double z = posZ + rand.nextFloat();
        // double motX = ((double) rand.nextFloat() - 0.5D) * 0.5D;
        // double motY = ((double) rand.nextFloat() - 0.5D) * 0.5D;
        // double motZ = ((double) rand.nextFloat() - 0.5D) * 0.5D;

        switch (i) {
        case 0:
            x += 0.5F;
            break;
        case 1:
            z += 0.5F;
            break;
        case 2:
            x -= 0.5F;
            break;
        case 3:
            z -= 0.5F;
            break;
        }

        EntityFX entityFX = new EntityPortalFX(world, x, y, z, 0, -0.2 - rand.nextDouble() * 0.8, 0);
        float f = rand.nextFloat() * 0.6F + 0.4F;
        entityFX.setRBGColorF(1.0F * f, 1.0F * f, 1.0F * f);
        Glacios.mc.effectRenderer.addEffect(entityFX);
    }

}
