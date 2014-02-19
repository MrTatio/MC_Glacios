package roundaround.mcmods.glacios.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import roundaround.mcmods.glacios.GlaciosBlocks;
import roundaround.mcmods.glacios.block.BlockLeavesGlacios;
import roundaround.mcmods.glacios.block.BlockLogGlacios;
import roundaround.mcmods.glacios.block.BlockSaplingGlacios;

public class WorldGenTaigaGiant extends WorldGenAbstractTree {

    private final boolean huge;
    private final int minHeight;
    private final int heightVariance;

    public WorldGenTaigaGiant(boolean doBlockNotify) {
        this(doBlockNotify, false, 10, 25);
    }

    public WorldGenTaigaGiant(boolean doBlockNotify, int minHeight, int heightVariance) {
        this(doBlockNotify, false, minHeight, heightVariance);
    }

    public WorldGenTaigaGiant(boolean doBlockNotify, boolean huge, int minHeight, int heightVariance) {
        super(doBlockNotify);
        this.huge = huge;
        this.minHeight = minHeight;
        this.heightVariance = heightVariance;
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        int l = random.nextInt(heightVariance) + minHeight;

        int i1, j1, k1;
        if (this.huge) {
            i1 = 10 + random.nextInt(5);
            j1 = l - i1;
            k1 = 4;
        } else {
            i1 = 1 + random.nextInt(2);
            j1 = l - i1;
            k1 = 2 + random.nextInt(2);
        }

        if (y < 1 || y + l + 1 > 256)
            return false;

        int i2;
        int l3;

        for (int l1 = y; l1 <= y + 1 + l; ++l1) {
            if (l1 - y < i1) {
                l3 = 0;
            } else {
                l3 = k1;
            }

            for (i2 = x - l3; i2 <= x + l3; ++i2) {
                for (int j2 = z - l3; j2 <= z + l3; ++j2) {
                    if (l1 >= 0 && l1 < 256) {
                        Block block = world.getBlock(i2, l1, j2);

                        if (!block.isAir(world, i2, l1, j2) && !block.isLeaves(world, i2, l1, j2)) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }

        if (this.huge) {
            Block block1 = world.getBlock(x, y - 1, z);
            Block block2 = world.getBlock(x + 1, y - 1, z);
            Block block3 = world.getBlock(x - 1, y - 1, z);
            Block block4 = world.getBlock(x, y - 1, z + 1);
            Block block5 = world.getBlock(x, y - 1, z - 1);

            if (!BlockSaplingGlacios.isSupportedByBlock(block1, BlockSaplingGlacios.taigaGiant)
                    || !BlockSaplingGlacios.isSupportedByBlock(block2, BlockSaplingGlacios.taigaGiant)
                    || !BlockSaplingGlacios.isSupportedByBlock(block3, BlockSaplingGlacios.taigaGiant)
                    || !BlockSaplingGlacios.isSupportedByBlock(block4, BlockSaplingGlacios.taigaGiant)
                    || !BlockSaplingGlacios.isSupportedByBlock(block5, BlockSaplingGlacios.taigaGiant))
                return false;

            block1.onPlantGrow(world, x, y - 1, z, x, y, z);
            block2.onPlantGrow(world, x + 1, y - 1, z, x + 1, y, z);
            block3.onPlantGrow(world, x - 1, y - 1, z, x - 1, y, z);
            block4.onPlantGrow(world, x, y - 1, z + 1, x, y, z + 1);
            block5.onPlantGrow(world, x, y - 1, z - 1, x, y, z - 1);
            
            l3 = random.nextInt(2);
            i2 = 1;
            byte b0 = 0;
            int k2;
            int i4;

            for (i4 = 0; i4 <= j1; ++i4) {
                k2 = y + l - i4;

                for (int l2 = x - l3; l2 <= x + l3; ++l2) {
                    int i3 = l2 - x;

                    for (int j3 = z - l3; j3 <= z + l3; ++j3) {
                        int k3 = j3 - z;

                        if ((Math.abs(i3) != l3 || Math.abs(k3) != l3 || l3 <= 0) && world.getBlock(l2, k2, j3).canBeReplacedByLeaves(world, l2, k2, j3)) {
                            this.setBlockAndNotifyAdequately(world, l2, k2, j3, GlaciosBlocks.leaves, BlockLeavesGlacios.taigaGiant);
                            this.setBlockAndNotifyAdequately(world, l2 + 1, k2, j3, GlaciosBlocks.leaves, BlockLeavesGlacios.taigaGiant);
                            this.setBlockAndNotifyAdequately(world, l2 - 1, k2, j3, GlaciosBlocks.leaves, BlockLeavesGlacios.taigaGiant);
                            this.setBlockAndNotifyAdequately(world, l2, k2, j3 + 1, GlaciosBlocks.leaves, BlockLeavesGlacios.taigaGiant);
                            this.setBlockAndNotifyAdequately(world, l2, k2, j3 - 1, GlaciosBlocks.leaves, BlockLeavesGlacios.taigaGiant);
                        }
                    }
                }

                if (l3 >= i2) {
                    l3 = b0;
                    b0 = 1;
                    ++i2;

                    if (i2 > k1) {
                        i2 = k1;
                    }
                } else {
                    ++l3;
                }
            }

            i4 = random.nextInt(3);

            for (int i = 0; i < l - i4; ++i) {
                Block block6 = world.getBlock(x, y + i, z);

                if (block6.isAir(world, x, y + i, z) || block6.isLeaves(world, x, y + i, z)) {
                    this.setBlockAndNotifyAdequately(world, x, y + i, z, GlaciosBlocks.log, BlockLogGlacios.taigaGiant);
                    this.setBlockAndNotifyAdequately(world, x + 1, y + i, z, GlaciosBlocks.log, BlockLogGlacios.taigaGiant);
                    this.setBlockAndNotifyAdequately(world, x - 1, y + i, z, GlaciosBlocks.log, BlockLogGlacios.taigaGiant);
                    this.setBlockAndNotifyAdequately(world, x, y + i, z + 1, GlaciosBlocks.log, BlockLogGlacios.taigaGiant);
                    this.setBlockAndNotifyAdequately(world, x, y + i, z - 1, GlaciosBlocks.log, BlockLogGlacios.taigaGiant);
                }
            }

            return true;
        } else {
            Block baseBlock = world.getBlock(x, y - 1, z);
            if (!BlockSaplingGlacios.isSupportedByBlock(baseBlock, BlockSaplingGlacios.taigaGiant))
                return false;

            baseBlock.onPlantGrow(world, x, y - 1, z, x, y, z);
            
            l3 = random.nextInt(2);
            i2 = 1;
            byte b0 = 0;
            int k2;
            int i4;

            for (i4 = 0; i4 <= j1; ++i4) {
                k2 = y + l - i4;

                for (int l2 = x - l3; l2 <= x + l3; ++l2) {
                    int i3 = l2 - x;

                    for (int j3 = z - l3; j3 <= z + l3; ++j3) {
                        int k3 = j3 - z;

                        if ((Math.abs(i3) != l3 || Math.abs(k3) != l3 || l3 <= 0) && world.getBlock(l2, k2, j3).canBeReplacedByLeaves(world, l2, k2, j3)) {
                            this.setBlockAndNotifyAdequately(world, l2, k2, j3, GlaciosBlocks.leaves, BlockLeavesGlacios.taigaGiant);
                        }
                    }
                }

                if (l3 >= i2) {
                    l3 = b0;
                    b0 = 1;
                    ++i2;

                    if (i2 > k1) {
                        i2 = k1;
                    }
                } else {
                    ++l3;
                }
            }

            i4 = random.nextInt(3);

            for (k2 = 0; k2 < l - i4; ++k2) {
                Block block2 = world.getBlock(x, y + k2, z);

                if (block2.isAir(world, x, y + k2, z) || block2.isLeaves(world, x, y + k2, z)) {
                    this.setBlockAndNotifyAdequately(world, x, y + k2, z, GlaciosBlocks.log, BlockLogGlacios.taigaGiant);
                }
            }

            return true;
        }
    }
}
