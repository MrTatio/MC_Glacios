package glacios.world.gen.feature;

import glacios.block.GlaciosBlocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGlaciosTrees extends WorldGenerator {
    @Override
    public boolean generate(World world, Random rand, int posX, int posY, int posZ) {
        int l = rand.nextInt(9) + 9;
        int i1 = l - rand.nextInt(5) - 6;
        int j1 = l - i1;
        int k1 = 1 + rand.nextInt(j1 + 3);
        boolean flag = true;

        if (posY >= 1 && posY + l + 1 <= 128) {
            int l1;
            int i2;
            int j2;
            int k2;
            int l2;

            for (l1 = posY; l1 <= posY + 1 + l && flag; ++l1) {
                if (l1 - posY < i1) {
                    l2 = 0;
                } else {
                    l2 = k1;
                }

                for (i2 = posX - l2; i2 <= posX + l2 && flag; ++i2) {
                    for (j2 = posZ - l2; j2 <= posZ + l2 && flag; ++j2) {
                        if (l1 >= 0 && l1 < 128) {
                            k2 = world.getBlockId(i2, l1, j2);

                            Block block = Block.blocksList[k2];

                            if (k2 != 0 && (block == null || !block.isLeaves(world, i2, l1, j2))) {
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
                l1 = world.getBlockId(posX, posY - 1, posZ);
                if ((l1 == Block.blockSnow.blockID || l1 == GlaciosBlocks.glacite.blockID) && posY < 128 - l - 1) {
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            Block block = Block.blocksList[world.getBlockId(posX + i, posY - 1, posZ + j)];
                            if (block == null || block.canBeReplacedByLeaves(world, posX + i, posY - 1, posZ + j)) {
                                setBlock(world, posX + i, posY - 1, posZ + j, Block.blockSnow.blockID);
                            }
                        }
                    }
                    l2 = 0;

                    for (i2 = posY + l; i2 >= posY + i1; --i2) {
                        for (j2 = posX - l2; j2 <= posX + l2; ++j2) {
                            k2 = j2 - posX;

                            for (int i3 = posZ - l2; i3 <= posZ + l2; ++i3) {
                                int j3 = i3 - posZ;

                                Block block = Block.blocksList[world.getBlockId(j2, i2, i3)];

                                if ((Math.abs(k2) != l2 || Math.abs(j3) != l2 || l2 <= 0) && (block == null || block.canBeReplacedByLeaves(world, j2, i2, i3))) {
                                    setBlockAndMetadata(world, j2, i2, i3, GlaciosBlocks.iceLeaves.blockID, 1);
                                }
                            }
                        }

                        if (l2 >= 1 && i2 == posY + i1 + 1) {
                            --l2;
                        } else if (l2 < k1) {
                            ++l2;
                        }
                    }

                    for (int i3 = -1; i3 <= 1; i3++) {
                        for (int j3 = -1; j3 <= 1; j3++) {
                            if (i3 == 0 && j3 == 0) {
                                int dir = rand.nextInt(4);
                                for (i2 = 0; i2 < l - 1; ++i2) {
                                    setBlockAndMetadata(world, posX, posY + i2, posZ, GlaciosBlocks.iceVine.blockID, dir);
                                }
                            } else {
                                for (i2 = 0; i2 < l - 1; ++i2) {
                                    j2 = world.getBlockId(posX + i3, posY + i2, posZ + j3);
                                    Block block = Block.blocksList[j2];

                                    if (j2 == 0 || block == null || block.isLeaves(world, posX + i3, posY + i2, posZ + j3)) {
                                        setBlockAndMetadata(world, posX + i3, posY + i2, posZ + j3, GlaciosBlocks.iceLog.blockID, 1);
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
}
