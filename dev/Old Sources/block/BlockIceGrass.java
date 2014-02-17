package glacios.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IShearable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIceGrass extends BlockFlower implements IShearable {
    protected BlockIceGrass(int par1) {
        super(par1, Material.vine);
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
    }

    /*
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon("Glacios:" + this.getUnlocalizedName().substring(5));
    }

    /*
     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
     * blockID passed in. Args: blockID
     */
    @Override
    protected boolean canThisPlantGrowOnThisBlockID(int blockID) {
        return blockID == Block.blockSnow.blockID || blockID == GlaciosBlocks.glacite.blockID;
    }

    /*
     * Can this block stay at this position. Similar to canPlaceBlockAt except gets checked often with plants.
     */
    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        return canThisPlantGrowOnThisBlockID(world.getBlockId(x, y - 1, z));
    }

    /*
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return -1;
    }

    /*
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    @Override
    public int quantityDroppedWithBonus(int par1, Random par2Random) {
        return 1 + par2Random.nextInt(par1 * 2 + 1);
    }

    /*
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    @Override
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6) {
        super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
    }

    /*
     * Get the block's damage value (for use with pick block).
     */
    @Override
    public int getDamageValue(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    /*
     * Returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks).
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(int id, CreativeTabs creativeTab, List subBlocks) {
        subBlocks.add(new ItemStack(id, 1, 0));
    }

    @Override
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int meta, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        if (world.rand.nextInt(8) != 0) {
            return ret;
        }

        ItemStack item = ForgeHooks.getGrassSeed(world);
        if (item != null) {
            ret.add(item);
        }
        return ret;
    }

    @Override
    public boolean isShearable(ItemStack item, World world, int x, int y, int z) {
        return true;
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, World world, int x, int y, int z, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
        return ret;
    }
}
