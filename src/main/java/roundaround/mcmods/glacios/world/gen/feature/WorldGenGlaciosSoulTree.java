package roundaround.mcmods.glacios.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;
import roundaround.mcmods.glacios.GlaciosBlocks;
import roundaround.mcmods.glacios.block.BlockSoulSapling;

public class WorldGenGlaciosSoulTree extends WorldGenAbstractTree {

    private final boolean vinesGrow;

    public WorldGenGlaciosSoulTree(boolean doBlockNotify) {
        this(doBlockNotify, false);
    }

    public WorldGenGlaciosSoulTree(boolean doBlockNotify, boolean vinesGrow) {
        super(doBlockNotify);
        this.vinesGrow = vinesGrow;
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        int treeHeight = rand.nextInt(3) + 6;
        boolean flag = true;

        if (y >= 1 && y + treeHeight + 1 <= 256) {
            byte b0;
            int posY;
            Block block;

            for (int i1 = y; i1 <= y + 1 + treeHeight; ++i1) {
                b0 = 1;

                if (i1 == y) {
                    b0 = 0;
                }

                if (i1 >= y + 1 + treeHeight - 2) {
                    b0 = 2;
                }

                for (int j1 = x - b0; j1 <= x + b0 && flag; ++j1) {
                    for (posY = z - b0; posY <= z + b0 && flag; ++posY) {
                        if (i1 >= 0 && i1 < 256) {
                            block = world.func_147439_a(j1, i1, posY);

                            if (!this.isReplaceable(world, j1, i1, posY)) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else {
                Block block2 = world.func_147439_a(x, y - 1, z);

                boolean isSoil = block2.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (BlockSoulSapling) GlaciosBlocks.soulSapling);
                if (isSoil && y < 256 - treeHeight - 1) {
                    block2.onPlantGrow(world, x, y - 1, z, x, y, z);
                    b0 = 3;
                    byte b1 = 0;
                    int l1;
                    int posX;
                    int j2;
                    int i3;

                    for (posY = y - b0 + treeHeight; posY <= y + treeHeight; ++posY) {
                        i3 = posY - (y + treeHeight);
                        l1 = b1 + 1 - i3 / 2;

                        for (posX = x - l1; posX <= x + l1; ++posX) {
                            j2 = posX - x;

                            for (int posZ = z - l1; posZ <= z + l1; ++posZ) {
                                int l2 = posZ - z;

                                if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || rand.nextInt(2) != 0 && i3 != 0) {
                                    Block block1 = world.func_147439_a(posX, posY, posZ);

                                    if (block1.isAir(world, posX, posY, posZ) || block1.isLeaves(world, posX, posY, posZ)) {
                                        this.func_150516_a(world, posX, posY, posZ, GlaciosBlocks.soulLeaves, 0);
                                    }
                                }
                            }
                        }
                    }

                    for (posY = 0; posY < treeHeight; ++posY) {
                        block = world.func_147439_a(x, y + posY, z);

                        if (block.isAir(world, x, y + posY, z) || block.isLeaves(world, x, y + posY, z)) {
                            this.func_150516_a(world, x, y + posY, z, GlaciosBlocks.soulLog, 0);

                            if (this.vinesGrow && posY > 0) {
                                if (rand.nextInt(3) > 0 && world.func_147437_c(x - 1, y + posY, z)) {
                                    this.func_150516_a(world, x - 1, y + posY, z, GlaciosBlocks.iceVine, 8);
                                }

                                if (rand.nextInt(3) > 0 && world.func_147437_c(x + 1, y + posY, z)) {
                                    this.func_150516_a(world, x + 1, y + posY, z, GlaciosBlocks.iceVine, 2);
                                }

                                if (rand.nextInt(3) > 0 && world.func_147437_c(x, y + posY, z - 1)) {
                                    this.func_150516_a(world, x, y + posY, z - 1, GlaciosBlocks.iceVine, 1);
                                }

                                if (rand.nextInt(3) > 0 && world.func_147437_c(x, y + posY, z + 1)) {
                                    this.func_150516_a(world, x, y + posY, z + 1, GlaciosBlocks.iceVine, 4);
                                }
                            }
                        }
                    }

                    if (this.vinesGrow) {
                        for (posY = y - 3 + treeHeight; posY <= y + treeHeight; ++posY) {
                            i3 = posY - (y + treeHeight);
                            l1 = 2 - i3 / 2;

                            for (posX = x - l1; posX <= x + l1; ++posX) {
                                for (j2 = z - l1; j2 <= z + l1; ++j2) {
                                    if (world.func_147439_a(posX, posY, j2).isLeaves(world, posX, posY, j2)) {
                                        if (rand.nextInt(4) == 0 && world.func_147439_a(posX - 1, posY, j2).isAir(world, posX - 1, posY, j2)) {
                                            this.growVines(world, posX - 1, posY, j2, 8);
                                        }

                                        if (rand.nextInt(4) == 0 && world.func_147439_a(posX + 1, posY, j2).isAir(world, posX + 1, posY, j2)) {
                                            this.growVines(world, posX + 1, posY, j2, 2);
                                        }

                                        if (rand.nextInt(4) == 0 && world.func_147439_a(posX, posY, j2 - 1).isAir(world, posX, posY, j2 - 1)) {
                                            this.growVines(world, posX, posY, j2 - 1, 1);
                                        }

                                        if (rand.nextInt(4) == 0 && world.func_147439_a(posX, posY, j2 + 1).isAir(world, posX, posY, j2 + 1)) {
                                            this.growVines(world, posX, posY, j2 + 1, 4);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    /**
     * Grows vines downward from the given block for a given length. Args: World, x, starty, z, vine-length
     */
    private void growVines(World par1World, int par2, int par3, int par4, int par5) {
        this.func_150516_a(par1World, par2, par3, par4, GlaciosBlocks.iceVine, par5);
        int i1 = 4;

        while (true) {
            --par3;

            if (par1World.func_147439_a(par2, par3, par4).isAir(par1World, par2, par3, par4) || i1 <= 0) {
                return;
            }

            this.func_150516_a(par1World, par2, par3, par4, GlaciosBlocks.iceVine, par5);
            --i1;
        }
    }

}
