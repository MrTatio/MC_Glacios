package glacios.item;

import glacios.block.GlaciosBlocks;
import glacios.core.Glacios;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGlaciteCrystal extends ItemGlacios {

	public ItemGlaciteCrystal(int id) {
		super(id);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if(player.dimension == 0 || player.dimension == Glacios.dimID) {
			for(int i = -2; i <= 2; i++) {
				for(int j = -2; j <= 2; j++) {
					for(int k = -1; k < 1; k++) {
						if(world.getBlockId((int)player.posX + i, (int)(player.posY - player.yOffset) + k, (int)player.posZ + j) == Block.blockNetherQuartz.blockID
								&& world.getBlockMetadata((int)player.posX + i, (int)(player.posY - player.yOffset) + k, (int)player.posZ + j) == 1
								&& world.getBlockId((int)player.posX + i, (int)(player.posY - player.yOffset) + 1 + k, (int)player.posZ + j) == 0)
							GlaciosBlocks.portalGlacios.tryToCreatePortal(world, (int)player.posX + i, (int)(player.posY - player.yOffset) + 1 + k, (int)player.posZ + j);
					}
				}
			}
		}
		return itemStack;
	}

}
