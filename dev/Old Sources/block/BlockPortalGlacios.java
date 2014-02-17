package glacios.block;

import glacios.core.Glacios;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class BlockPortalGlacios extends BlockPortal {

	public BlockPortalGlacios(int id) {
		super(id);
	}

	/*
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This is
	 * the only chance you get to register icons.
	 */
	@Override
    @SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon("Glacios:" + this.getUnlocalizedName().substring(5));
	}

	/*
	 * Ticks the block if it's been scheduled
	 */
	@Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
	}

	/*
	 * Checks to see if this location is valid to create a portal and will
	 * return True if it does. Args: world, x, y, z
	 */
	@Override
    public boolean tryToCreatePortal(World world, int x, int y, int z) {
		byte b0 = 0;
		byte b1 = 0;

		if (world.getBlockId(x - 1, y, z) == Block.blockNetherQuartz.blockID && world.getBlockMetadata(x - 1, y, z) == 1
				|| world.getBlockId(x + 1, y, z) == Block.blockNetherQuartz.blockID &&  world.getBlockMetadata(x + 1, y, z) == 1) {
			b0 = 1;
		}

		if (world.getBlockId(x, y, z - 1) == Block.blockNetherQuartz.blockID && world.getBlockMetadata(x, y, z - 1) == 1
				|| world.getBlockId(x, y, z + 1) == Block.blockNetherQuartz.blockID && world.getBlockMetadata(x, y, z + 1) == 1) {
			b1 = 1;
		}

		if (b0 == b1) {
			return false;
		} else {
			if (world.getBlockId(x - b0, y, z - b1) == 0) {
				x -= b0;
				z -= b1;
			}

			int l;
			int i1;

			for (l = -1; l <= 2; ++l) {
				for (i1 = -1; i1 <= 3; ++i1) {
					boolean flag = l == -1 || l == 2 || i1 == -1 || i1 == 3;

					if (l != -1 && l != 2 || i1 != -1 && i1 != 3) {
						int j1 = world.getBlockId(x + b0 * l, y + i1, z + b1 * l);

						if (flag) {
							if (j1 != Block.blockNetherQuartz.blockID || world.getBlockMetadata(x + b0 * l, y + i1, z + b1 * l) != 1) {
								return false;
							}
						} else if (j1 != 0) {
							return false;
						}
					}
				}
			}

			for (l = 0; l < 2; ++l) {
				for (i1 = 0; i1 < 3; ++i1) {
					world.setBlockAndMetadataWithNotify(x + b0 * l, y + i1, z + b1 * l, GlaciosBlocks.portalGlacios.blockID, 0, 2);
				}
			}

			return true;
		}
	}

	/*
	 * Lets the block know when one of its neighbor changes. Doesn't know which
	 * neighbor changed (coordinates passed are their own) Args: x, y, z,
	 * neighbor blockID
	 */
	@Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborID) {
		byte b0 = 0;
		byte b1 = 1;

		if (world.getBlockId(x - 1, y, z) == blockID || world.getBlockId(x + 1, y, z) == blockID) {
			b0 = 1;
			b1 = 0;
		}

		int i1;

		for (i1 = y; world.getBlockId(x, i1 - 1, z) == blockID; --i1) {
			;
		}

		if (world.getBlockId(x, i1 - 1, z) != Block.blockNetherQuartz.blockID || world.getBlockMetadata(x, i1 - 1, z) != 1) {
			world.setBlockToAir(x, y, z);
		} else {
			int j1;

			for (j1 = 1; j1 < 4 && world.getBlockId(x, i1 + j1, z) == blockID; ++j1) {
				;
			}

			if (j1 == 3 && world.getBlockId(x, i1 + j1, z) == Block.blockNetherQuartz.blockID && world.getBlockMetadata(x, i1 + j1, z) == 1) {
				boolean flag = world.getBlockId(x - 1, y, z) == blockID || world.getBlockId(x + 1, y, z) == blockID;
				boolean flag1 = world.getBlockId(x, y, z - 1) == blockID || world.getBlockId(x, y, z + 1) == blockID;

				if (flag && flag1) {
					world.setBlockToAir(x, y, z);
				} else {
					if ((world.getBlockId(x + b0, y, z + b1) != Block.blockNetherQuartz.blockID || world.getBlockMetadata(x + b0, y, z + b1) != 1 || world.getBlockId(x - b0, y, z - b1) != blockID)
							&& (world.getBlockId(x - b0, y, z - b1) != Block.blockNetherQuartz.blockID || world.getBlockMetadata(x - b0, y, z - b1) != 1 || world.getBlockId(x + b0, y, z + b1) != blockID)) {
						world.setBlockToAir(x, y, z);
					}
				}
			} else {
				world.setBlockToAir(x, y, z);
			}
		}
	}

	/*
	 * Triggered whenever an entity collides with this block (enters into the
	 * block). Args: world, x, y, z, entity
	 */
	@Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (entity.ridingEntity == null && entity.riddenByEntity == null && entity instanceof EntityPlayer && !world.isRemote) {
			EntityPlayerMP player = (EntityPlayerMP) entity;
			
			if (player.getEntityData().getInteger("GlaciosTimeUntilPortal") > 0) {
				player.getEntityData().setInteger("GlaciosTimeUntilPortal", player.getPortalCooldown());
			} else {
				double d0 = player.prevPosX - player.posX;
				double d1 = player.prevPosZ - player.posZ;
	
				if (!world.isRemote && !player.getEntityData().getBoolean("GlaciosInPortal")) {
					player.getEntityData().setInteger("GlaciosTeleportDirection", Direction.getMovementDirection(d0, d1));
				}
				
				player.getEntityData().setBoolean("GlaciosInPortal", true);
			}
		}
	}

	/*
	 * A randomly called display update to be able to add particles or other
	 * items for display
	 */
	@Override
    @SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int posX, int posY, int posZ, Random rand) {
		if (rand.nextInt(100) == 0) {
			world.playSound(posX + 0.5D, posY + 0.5D, posZ + 0.5D, "portal.portal", 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int l = 0; l < 5; ++l) {
			double x = posX + rand.nextFloat();
			double y = posY + rand.nextFloat();
			double z = posZ + rand.nextFloat();
			double motX = (rand.nextFloat() - 0.5D) * 0.5D;
			double motY = (rand.nextFloat() - 0.5D) * 0.5D;
			double motZ = (rand.nextFloat() - 0.5D) * 0.5D;
			int i1 = rand.nextInt(2) * 2 - 1;

			if (world.getBlockId(posX - 1, posY, posZ) != blockID && world.getBlockId(posX + 1, posY, posZ) != blockID) {
				x = posX + 0.5D + 0.25D * i1;
				motX = rand.nextFloat() * 2.0F * i1;
			} else {
				z = posZ + 0.5D + 0.25D * i1;
				motZ = rand.nextFloat() * 2.0F * i1;
			}

			EntityFX entityFX = new EntityPortalFX(world, x, y, z, motX, motY, motZ);
	        float f = rand.nextFloat() * 0.4F + 0.6F;
	        entityFX.setRBGColorF(1.0F * f, 1.0F * f, 1.0F * f);
			Glacios.mc.effectRenderer.addEffect(entityFX);
		}
	}

}
