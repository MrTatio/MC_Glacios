package roundaround.mcmods.glacios.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import roundaround.mcmods.glacios.GlaciosBlocks;
import roundaround.mcmods.glacios.block.BlockLeavesGlacios;
import roundaround.mcmods.glacios.block.BlockLogGlacios;
import roundaround.mcmods.glacios.block.BlockSaplingGlacios;

public class WorldGenSoulTree extends WorldGenAbstractTree {

    private final boolean thick;

    public WorldGenSoulTree(boolean doBlockNotify) {
        this(doBlockNotify, false);
    }

    public WorldGenSoulTree(boolean doBlockNotify, boolean thick) {
        super(doBlockNotify);
        this.thick = thick;
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        int l = random.nextInt(5) + 7;

        if (y < 1 || y + l + 1 > 256)
            return false;

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

            for (j1 = x - b0; j1 <= x + b0; ++j1) {
                for (k1 = z - b0; k1 <= z + b0; ++k1) {
                    if (i1 >= 0 && i1 < 256) {
                        Block block = world.getBlock(j1, i1, k1);

                        if (!block.isAir(world, j1, i1, k1) && !block.isLeaves(world, j1, i1, k1)) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
        
        if (thick) {
            Block[] baseBlocks = new Block[13];
            int index = 0;
            for (int posX = -2; posX <= 2; posX++) {
                for (int posZ = -2; posZ <= 2; posZ++) {
                    if (posX == 0 || posZ == 0 || (Math.abs(posX) < 2 && Math.abs(posZ) < 2))
                        baseBlocks[index++] = world.getBlock(x + posX, y - 1, z + posZ);
                }
            }
            
            index = 0;
            for (int posX = -2; posX <= 2; posX++) {
                for (int posZ = -2; posZ <= 2; posZ++) {
                    if (posX == 0 || posZ == 0 || (Math.abs(posX) < 2 && Math.abs(posZ) < 2))
                        if (!BlockSaplingGlacios.isSupportedByBlock(baseBlocks[index++], BlockSaplingGlacios.soul))
                            return false;
                }
            }
            
            index = 0;
            for (int posX = -2; posX <= 2; posX++) {
                for (int posZ = -2; posZ <= 2; posZ++) {
                    if (posX == 0 || posZ == 0 || (Math.abs(posX) < 2 && Math.abs(posZ) < 2))
                        baseBlocks[index++].onPlantGrow(world, x + posX, y - 1, z + posZ, x + posX, y, z + posZ);
                }
            }
            
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

                        if ((Math.abs(l1) != k1 || Math.abs(j2) != k1 || random.nextInt(2) != 0 && j1 != 0) && world.getBlock(l2, k2, i2).canBeReplacedByLeaves(world, l2, k2, i2)) {
                            this.setBlockAndNotifyAdequately(world, l2, k2, i2, GlaciosBlocks.leaves, BlockLeavesGlacios.soul);
                        }
                    }
                }
            }

            for (k2 = 0; k2 < l; ++k2) {
                Block tempBlock = world.getBlock(x, y + k2, z);

                if (tempBlock.isAir(world, x, y + k2, z) || tempBlock.isLeaves(world, x, y + k2, z)) {
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
            Block block1 = world.getBlock(x, y - 1, z);
            Block block2 = world.getBlock(x - 1, y - 1, z);
            Block block3 = world.getBlock(x + 1, y - 1, z);
            Block block4 = world.getBlock(x, y - 1, z - 1);
            Block block5 = world.getBlock(x, y - 1, z + 1);

            if (!BlockSaplingGlacios.isSupportedByBlock(block1, BlockSaplingGlacios.soul)
                    || !BlockSaplingGlacios.isSupportedByBlock(block2, BlockSaplingGlacios.soul)
                    || !BlockSaplingGlacios.isSupportedByBlock(block3, BlockSaplingGlacios.soul)
                    || !BlockSaplingGlacios.isSupportedByBlock(block4, BlockSaplingGlacios.soul)
                    || !BlockSaplingGlacios.isSupportedByBlock(block5, BlockSaplingGlacios.soul))
                return false;

            block1.onPlantGrow(world, x, y - 1, z, x, y, z);
            block2.onPlantGrow(world, x + 1, y - 1, z, x + 1, y, z);
            block3.onPlantGrow(world, x - 1, y - 1, z, x - 1, y, z);
            block4.onPlantGrow(world, x, y - 1, z + 1, x, y, z + 1);
            block5.onPlantGrow(world, x, y - 1, z - 1, x, y, z - 1);
            
            int l1;
            int l2;
            int k2;

            for (k2 = y - 3 + l; k2 <= y + l; ++k2) {
                j1 = k2 - (y + l);
                k1 = 2 - j1 / 2;

                for (l2 = x - k1; l2 <= x + k1; ++l2) {
                    l1 = l2 - x;

                    for (int i2 = z - k1; i2 <= z + k1; ++i2) {
                        int j2 = i2 - z;

                        if ((Math.abs(l1) != k1 || Math.abs(j2) != k1 || random.nextInt(2) != 0 && j1 != 0) && world.getBlock(l2, k2, i2).canBeReplacedByLeaves(world, l2, k2, i2)) {
                            this.setBlockAndNotifyAdequately(world, l2, k2, i2, GlaciosBlocks.leaves, BlockLeavesGlacios.soul);
                        }
                    }
                }
            }

            for (k2 = 0; k2 < l; ++k2) {
                Block tempBlock = world.getBlock(x, y + k2, z);

                if (tempBlock.isAir(world, x, y + k2, z) || tempBlock.isLeaves(world, x, y + k2, z)) {
                    this.setBlockAndNotifyAdequately(world, x, y + k2, z, GlaciosBlocks.log, BlockLogGlacios.soul);
                    this.setBlockAndNotifyAdequately(world, x - 1, y, z, GlaciosBlocks.log, BlockLogGlacios.soul + 4);
                    this.setBlockAndNotifyAdequately(world, x + 1, y, z, GlaciosBlocks.log, BlockLogGlacios.soul + 4);
                    this.setBlockAndNotifyAdequately(world, x, y, z - 1, GlaciosBlocks.log, BlockLogGlacios.soul + 8);
                    this.setBlockAndNotifyAdequately(world, x, y, z + 1, GlaciosBlocks.log, BlockLogGlacios.soul + 8);
                    this.setBlockAndNotifyAdequately(world, x - 1, y + (l - 4), z, GlaciosBlocks.log, BlockLogGlacios.soul + 4);
                    this.setBlockAndNotifyAdequately(world, x + 1, y + (l - 4), z, GlaciosBlocks.log, BlockLogGlacios.soul + 4);
                    this.setBlockAndNotifyAdequately(world, x, y + (l - 4), z - 1, GlaciosBlocks.log, BlockLogGlacios.soul + 8);
                    this.setBlockAndNotifyAdequately(world, x, y + (l - 4), z + 1, GlaciosBlocks.log, BlockLogGlacios.soul + 8);
                    this.setBlockAndNotifyAdequately(world, x - 2, y + (l - 3), z, GlaciosBlocks.log, BlockLogGlacios.soul);
                    this.setBlockAndNotifyAdequately(world, x + 2, y + (l - 3), z, GlaciosBlocks.log, BlockLogGlacios.soul);
                    this.setBlockAndNotifyAdequately(world, x, y + (l - 3), z - 2, GlaciosBlocks.log, BlockLogGlacios.soul);
                    this.setBlockAndNotifyAdequately(world, x, y + (l - 3), z + 2, GlaciosBlocks.log, BlockLogGlacios.soul);
                }
            }

            return true;
        }
    }
}
