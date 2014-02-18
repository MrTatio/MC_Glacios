package roundaround.mcmods.glacios.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import roundaround.mcmods.glacios.GlaciosBlocks;
import roundaround.mcmods.glacios.block.BlockLeavesGlacios;
import roundaround.mcmods.glacios.block.BlockLogGlacios;
import roundaround.mcmods.glacios.block.BlockSaplingGlacios;

public class WorldGenSoulTree extends WorldGenAbstractTree {

    private final int minHeight;
    private final int heightVariance;

    public WorldGenSoulTree(boolean doBlockNotify) {
        this(doBlockNotify, 6, 12);
    }

    public WorldGenSoulTree(boolean doBlockNotify, int minHeight, int heightVariance) {
        super(doBlockNotify);
        this.minHeight = minHeight;
        this.heightVariance = heightVariance;
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        int l = random.nextInt(this.heightVariance) + this.minHeight;

        boolean flag = true;

        if (y >= 1 && y + l + 1 <= 256) {
            int j1;
            int k1;

            for (int i1 = y; i1 <= y + 1 + l; ++i1) {
                byte b0 = 1;

                if (i1 == y) {
                    b0 = 0;
                }

                if (i1 >= y + 1 + l - 2) {
                    b0 = 3;
                }

                for (j1 = x - b0; j1 <= x + b0 && flag; ++j1) {
                    for (k1 = z - b0; k1 <= z + b0 && flag; ++k1) {
                        if (i1 >= 0 && i1 < 256) {
                            Block block = world.getBlock(j1, i1, k1);

                            if (!block.isAir(world, j1, i1, k1) && !block.isLeaves(world, j1, i1, k1)) {
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
                Block baseBlock = world.getBlock(x, y - 1, z);
                if (BlockSaplingGlacios.isSupportedByBlock(baseBlock, BlockSaplingGlacios.taigaGiant))
                    return false;
                
                if (y < 256 - l - 1) {
                    baseBlock.onPlantGrow(world, x, y - 1, z, x, y, z);
                    int l1;
                    int l2;
                    int k2;

                    for (k2 = y - 3 + l; k2 <= y + l; ++k2) {
                        j1 = k2 - (y + l);
                        k1 = 3 - j1;

                        for (l2 = x - k1; l2 <= x + k1; ++l2) {
                            l1 = l2 - x;

                            for (int i2 = z - k1; i2 <= z + k1; ++i2) {
                                int j2 = i2 - z;

                                if ((Math.abs(l1) != k1 || Math.abs(j2) != k1 || random.nextInt(2) != 0 && j1 != 0)
                                        && world.getBlock(l2, k2, i2).canBeReplacedByLeaves(world, l2, k2, i2)) {
                                    this.setBlockAndNotifyAdequately(world, l2, k2, i2, GlaciosBlocks.leaves, BlockLeavesGlacios.soul);
                                }
                            }
                        }
                    }

                    for (k2 = 0; k2 < l; ++k2) {
                        Block block2 = world.getBlock(x, y + k2, z);

                        if (block2.isAir(world, x, y + k2, z) || block2.isLeaves(world, x, y + k2, z) || block2 == Blocks.flowing_water || block2 == Blocks.water) {
                            this.setBlockAndNotifyAdequately(world, x, y + k2, z, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x - 1, y + k2, z, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x + 1, y + k2, z, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x, y + k2, z - 1, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x, y + k2, z + 1, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x - 1, y, z - 1, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x + 1, y, z + 1, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x - 1, y, z + 1, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x + 1, y, z - 1, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x - 1, y + 1, z - 1, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x + 1, y + 1, z + 1, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x - 1, y + 1, z + 1, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x + 1, y + 1, z - 1, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x - 2, y, z, GlaciosBlocks.log, BlockLogGlacios.soul + 4);
                            this.setBlockAndNotifyAdequately(world, x + 2, y, z, GlaciosBlocks.log, BlockLogGlacios.soul + 4);
                            this.setBlockAndNotifyAdequately(world, x, y, z - 2, GlaciosBlocks.log, BlockLogGlacios.soul + 8);
                            this.setBlockAndNotifyAdequately(world, x, y, z + 2, GlaciosBlocks.log, BlockLogGlacios.soul + 8);
                            this.setBlockAndNotifyAdequately(world, x - 1, y + (l - 4), z - 1, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x + 1, y + (l - 4), z + 1, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x - 1, y + (l - 4), z + 1, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x + 1, y + (l - 4), z - 1, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x - 2, y + (l - 4), z, GlaciosBlocks.log, BlockLogGlacios.soul + 4);
                            this.setBlockAndNotifyAdequately(world, x + 2, y + (l - 4), z, GlaciosBlocks.log, BlockLogGlacios.soul + 4);
                            this.setBlockAndNotifyAdequately(world, x, y + (l - 4), z - 2, GlaciosBlocks.log, BlockLogGlacios.soul + 8);
                            this.setBlockAndNotifyAdequately(world, x, y + (l - 4), z + 2, GlaciosBlocks.log, BlockLogGlacios.soul + 8);
                            this.setBlockAndNotifyAdequately(world, x - 3, y + (l - 3), z, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x + 3, y + (l - 3), z, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x, y + (l - 3), z - 3, GlaciosBlocks.log, BlockLogGlacios.soul);
                            this.setBlockAndNotifyAdequately(world, x, y + (l - 3), z + 3, GlaciosBlocks.log, BlockLogGlacios.soul);
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

}
