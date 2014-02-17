package glacios.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class BlockIceLog extends BlockLog {

	@SideOnly(Side.CLIENT)
	private Icon iconSide;
	@SideOnly(Side.CLIENT)
	private Icon iconTop;

	public BlockIceLog(int id) {
		super(id);
	}

	/*
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This is
	 * the only chance you get to register icons.
	 */
	@Override
    @SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		iconSide = iconRegister.registerIcon("Glacios:" + this.getUnlocalizedName().substring(5) + "Side");
		iconTop = iconRegister.registerIcon("Glacios:" + this.getUnlocalizedName().substring(5) + "Top");
	}

	/*
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return GlaciosBlocks.iceLog.blockID;
	}

	/*
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTextureFromSideAndMetadata(int side, int metadata) {
		int dir = metadata & 12;
		return dir == 0 && (side == 1 || side == 0) ? iconTop : (dir == 4 && (side == 5 || side == 4) ? iconTop
				: (dir == 8 && (side == 2 || side == 3) ? iconTop : iconSide));
	}

	/*
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	@Override
	public int damageDropped(int metadata) {
		return 0;
	}

	/*
	 * Returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List subBlocks) {
		subBlocks.add(new ItemStack(id, 1, 0));
	}

}
