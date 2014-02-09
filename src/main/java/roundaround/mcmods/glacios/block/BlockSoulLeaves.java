package roundaround.mcmods.glacios.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import roundaround.mcmods.glacios.Glacios;
import roundaround.mcmods.glacios.GlaciosBlocks;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSoulLeaves extends BlockLeavesBase implements IShearable {

    private IIcon icon[] = new IIcon[2];
    private int[] distances;

    public BlockSoulLeaves() {
        super(Material.field_151584_j, false);
        this.func_149675_a(true);
        this.func_149647_a(CreativeTabs.tabDecorations);
        this.func_149711_c(0.2F);
        this.func_149713_g(1);
        this.func_149672_a(field_149779_h);
    }

    @Override
    public boolean func_149662_c() {
        return !FMLClientHandler.instance().getClient().isFancyGraphicsEnabled();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon func_149691_a(int side, int meta) {
        if (!this.func_149662_c())
            return this.icon[0];
        else
            return this.icon[1];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void func_149651_a(IIconRegister iconRegister) {
        this.icon[0] = iconRegister.registerIcon(Glacios.MODID + ":" + this.func_149739_a().substring(5));
        this.icon[1] = iconRegister.registerIcon(Glacios.MODID + ":" + this.func_149739_a().substring(5) + "Opaque");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void func_149666_a(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(item, 1, 0));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean func_149646_a(IBlockAccess blockAccess, int x, int y, int z, int side) {
        Block block = blockAccess.func_147439_a(x, y, z);
        return this.func_149662_c() && block == this ? false : superShouldSideBeRendered(blockAccess, x, y, z, side);
    }

    // Bypass BlockLeavesBase.shouldSideBeRendered
    @SideOnly(Side.CLIENT)
    public boolean superShouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return side == 0 && this.field_149760_C > 0.0D ? true : (side == 1 && this.field_149756_F < 1.0D ? true : (side == 2 && this.field_149754_D > 0.0D ? true : (side == 3
                && this.field_149757_G < 1.0D ? true : (side == 4 && this.field_149759_B > 0.0D ? true : (side == 5 && this.field_149755_E < 1.0D ? true : !blockAccess
                .func_147439_a(x, y, z).func_149662_c())))));
    }

    @Override
    public void func_149749_a(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
        byte b0 = 1;
        int i1 = b0 + 1;

        if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
            for (int j1 = -b0; j1 <= b0; ++j1) {
                for (int k1 = -b0; k1 <= b0; ++k1) {
                    for (int l1 = -b0; l1 <= b0; ++l1) {
                        Block block = world.func_147439_a(x + j1, y + k1, z + l1);
                        if (block.isLeaves(world, x + j1, y + k1, z + l1)) {
                            block.beginLeavesDecay(world, x + j1, y + k1, z + l1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void func_149674_a(World world, int x, int y, int z, Random rand) {
        if (!world.isRemote) {
            int l = world.getBlockMetadata(x, y, z);

            if ((l & 8) != 0 && (l & 4) == 0) {
                byte b0 = 4;
                int i1 = b0 + 1;
                byte b1 = 32;
                int j1 = b1 * b1;
                int k1 = b1 / 2;

                if (this.distances == null) {
                    this.distances = new int[b1 * b1 * b1];
                }

                int l1;

                if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
                    int i2;
                    int j2;

                    for (l1 = -b0; l1 <= b0; ++l1) {
                        for (i2 = -b0; i2 <= b0; ++i2) {
                            for (j2 = -b0; j2 <= b0; ++j2) {
                                Block block = world.func_147439_a(x + l1, y + i2, z + j2);

                                if (!block.canSustainLeaves(world, x + l1, y + i2, z + j2)) {
                                    if (block.isLeaves(world, x + l1, y + i2, z + j2)) {
                                        this.distances[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -2;
                                    } else {
                                        this.distances[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -1;
                                    }
                                } else {
                                    this.distances[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = 0;
                                }
                            }
                        }
                    }

                    for (l1 = 1; l1 <= 4; ++l1) {
                        for (i2 = -b0; i2 <= b0; ++i2) {
                            for (j2 = -b0; j2 <= b0; ++j2) {
                                for (int k2 = -b0; k2 <= b0; ++k2) {
                                    if (this.distances[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1] == l1 - 1) {
                                        if (this.distances[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
                                            this.distances[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1;
                                        }

                                        if (this.distances[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
                                            this.distances[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1;
                                        }

                                        if (this.distances[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] == -2) {
                                            this.distances[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] = l1;
                                        }

                                        if (this.distances[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] == -2) {
                                            this.distances[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] = l1;
                                        }

                                        if (this.distances[(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] == -2) {
                                            this.distances[(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] = l1;
                                        }

                                        if (this.distances[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] == -2) {
                                            this.distances[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] = l1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                l1 = this.distances[k1 * j1 + k1 * b1 + k1];

                if (l1 >= 0) {
                    world.setBlockMetadataWithNotify(x, y, z, l & -9, 4);
                } else {
                    this.func_150126_e(world, x, y, z);
                }
            }
        }
    }

    private void func_150126_e(World world, int x, int y, int z) {
        this.func_149697_b(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        world.func_147468_f(x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void func_149734_b(World world, int x, int y, int z, Random rand) {
        if (world.canLightningStrikeAt(x, y + 1, z) && !World.func_147466_a(world, x, y - 1, z) && rand.nextInt(15) == 1) {
            double randX = x + rand.nextFloat();
            double randY = y - 0.05D;
            double randZ = z + rand.nextFloat();
            world.spawnParticle("dripWater", randX, randY, randZ, 0.0D, 0.0D, 0.0D);
        }

        // TODO: Glow particles!
    }

    @Override
    public Item func_149650_a(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.func_150898_a(GlaciosBlocks.soulSapling);
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z) & 3));
        return ret;
    }

}
