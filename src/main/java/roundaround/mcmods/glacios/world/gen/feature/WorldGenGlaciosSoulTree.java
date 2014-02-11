package roundaround.mcmods.glacios.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import roundaround.mcmods.glacios.GlaciosBlocks;
import roundaround.mcmods.glacios.block.BlockSoulSapling;

public class WorldGenGlaciosSoulTree extends WorldGenAbstractTree {

    private final boolean huge;

    public WorldGenGlaciosSoulTree(boolean doBlockNotify) {
        this(doBlockNotify, false);
    }

    public WorldGenGlaciosSoulTree(boolean doBlockNotify, boolean huge) {
        super(doBlockNotify);
        this.huge = huge;
    }

    @Override
    public boolean generate(World world, Random rand, int startX, int startY, int startZ) {
        int treeHeight = rand.nextInt(3) + 6;
        boolean validLocation = true;

        if (startY >= 1 && startY + treeHeight + 1 <= 256) {
            for (int checkY = startY; checkY <= startY + 1 + treeHeight; ++checkY) {
                byte width = 1;

                if (checkY == startY) {
                    width = 0;
                }
                if (checkY >= startY + 1 + treeHeight - 2) {
                    width = 2;
                }

                for (int checkX = startX - width; checkX <= startX + width && validLocation; ++checkX) {
                    for (int checkZ = startZ - width; checkZ <= startZ + width && validLocation; ++checkZ) {
                        if (checkY >= 0 && checkY < 256) {
                            if (!this.isReplaceable(world, checkX, checkY, checkZ)) {
                                validLocation = false;
                            }
                        } else {
                            validLocation = false;
                        }
                    }
                }
            }

            if (!validLocation) {
                return false;
            } else {
                Block baseBlock = world.getBlock(startX, startY - 1, startZ);
                
                if (!((BlockSoulSapling)GlaciosBlocks.soulSapling).isSupportedByBlock(baseBlock))
                    return false;
                
                if (startY < 256 - treeHeight - 1) {
                    baseBlock.onPlantGrow(world, startX, startY - 1, startZ, startX, startY, startZ);
                    int offset;
                    int heightDiff;

                    for (int posY = startY - 3 + treeHeight; posY <= startY + treeHeight; ++posY) {
                        heightDiff = posY - (startY + treeHeight);
                        offset = 1 - heightDiff / 2;

                        for (int posX = startX - offset; posX <= startX + offset; ++posX) {
                            for (int posZ = startZ - offset; posZ <= startZ + offset; ++posZ) {
                                if (Math.abs(posX - startX) != offset || Math.abs(posZ - startZ) != offset || rand.nextInt(2) != 0 && heightDiff != 0) {
                                    Block block1 = world.getBlock(posX, posY, posZ);

                                    if (block1.isAir(world, posX, posY, posZ) || block1.isLeaves(world, posX, posY, posZ)) {
                                        this.setBlockAndNotifyAdequately(world, posX, posY, posZ, GlaciosBlocks.soulLeaves, 0);
                                    }
                                }
                            }
                        }
                    }

                    for (int posY = 0; posY < treeHeight; ++posY) {
                        Block block = world.getBlock(startX, startY + posY, startZ);

                        if (block.isAir(world, startX, startY + posY, startZ) || block.isLeaves(world, startX, startY + posY, startZ)) {
                            this.setBlockAndNotifyAdequately(world, startX, startY + posY, startZ, GlaciosBlocks.soulLog, 0);

//                            if (this.vinesGrow && posY > 0) {
//                                if (rand.nextInt(3) > 0 && world.isAirBlock(startX - 1, startY + posY, startZ)) {
//                                    this.setBlockAndNotifyAdequately(world, startX - 1, startY + posY, startZ, GlaciosBlocks.iceVine, 8);
//                                }
//
//                                if (rand.nextInt(3) > 0 && world.isAirBlock(startX + 1, startY + posY, startZ)) {
//                                    this.setBlockAndNotifyAdequately(world, startX + 1, startY + posY, startZ, GlaciosBlocks.iceVine, 2);
//                                }
//
//                                if (rand.nextInt(3) > 0 && world.isAirBlock(startX, startY + posY, startZ - 1)) {
//                                    this.setBlockAndNotifyAdequately(world, startX, startY + posY, startZ - 1, GlaciosBlocks.iceVine, 1);
//                                }
//
//                                if (rand.nextInt(3) > 0 && world.isAirBlock(startX, startY + posY, startZ + 1)) {
//                                    this.setBlockAndNotifyAdequately(world, startX, startY + posY, startZ + 1, GlaciosBlocks.iceVine, 4);
//                                }
//                            }
                        }
                    }

//                    if (this.vinesGrow) {
//                        for (int posY = startY - 3 + treeHeight; posY <= startY + treeHeight; ++posY) {
//                            heightDiff = posY - (startY + treeHeight);
//                            offset = 2 - heightDiff / 2;
//
//                            for (int posX = startX - offset; posX <= startX + offset; ++posX) {
//                                for (int posZ = startZ - offset; posZ <= startZ + offset; ++posZ) {
//                                    if (world.getBlock(posX, posY, posZ).isLeaves(world, posX, posY, posZ)) {
//                                        if (rand.nextInt(4) == 0 && world.getBlock(posX - 1, posY, posZ).isAir(world, posX - 1, posY, posZ)) {
//                                            this.growVines(world, posX - 1, posY, posZ, 8);
//                                        }
//
//                                        if (rand.nextInt(4) == 0 && world.getBlock(posX + 1, posY, posZ).isAir(world, posX + 1, posY, posZ)) {
//                                            this.growVines(world, posX + 1, posY, posZ, 2);
//                                        }
//
//                                        if (rand.nextInt(4) == 0 && world.getBlock(posX, posY, posZ - 1).isAir(world, posX, posY, posZ - 1)) {
//                                            this.growVines(world, posX, posY, posZ - 1, 1);
//                                        }
//
//                                        if (rand.nextInt(4) == 0 && world.getBlock(posX, posY, posZ + 1).isAir(world, posX, posY, posZ + 1)) {
//                                            this.growVines(world, posX, posY, posZ + 1, 4);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    private void growVines(World world, int x, int y, int z, int meta) {
        for (int offsetY = 0; !world.getBlock(x, y - offsetY, z).isAir(world, x, y - offsetY, z) && offsetY < 4; ++offsetY) {
            this.setBlockAndNotifyAdequately(world, x, y - offsetY, z, GlaciosBlocks.iceVine, meta);
        }
    }

}
