package glacios.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWallGlacios extends BlockWall {

    private Block modelBlock;

    public BlockWallGlacios(int par1, Block par2Block) {
        super(par1, par2Block);
        modelBlock = par2Block;
    }

    /*
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2) {
        return modelBlock.getBlockTextureFromSide(par1);
    }

    /*
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
    }

    /*
     * Determines if a torch can be placed on the top surface of this block. Useful for creating your own block that
     * torches can be on, such as fences.
     */
    @Override
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        return true;
    }

}
