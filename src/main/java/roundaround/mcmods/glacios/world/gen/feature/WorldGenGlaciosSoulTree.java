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
        boolean validLocation = true;

        if (y >= 1 && y + treeHeight + 1 <= 256) {
            byte width;
            int posY;

            for (int ycheck = y; ycheck <= y + 1 + treeHeight; ++ycheck) {
                width = 1;

                if (ycheck == y) {
                    width = 0;
                }

                if (ycheck >= y + 1 + treeHeight - 2) {
                    width = 2;
                }

                for (int j1 = x - width; j1 <= x + width && validLocation; ++j1) {
                    for (posY = z - width; posY <= z + width && validLocation; ++posY) {
                        if (ycheck >= 0 && ycheck < 256) {
                            if (!this.isReplaceable(world, j1, ycheck, posY)) {
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
                Block baseBlock = world.func_147439_a(x, y - 1, z);

                boolean isSoil = baseBlock.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (BlockSoulSapling) GlaciosBlocks.soulSapling);
                if (isSoil && y < 256 - treeHeight - 1) {
                    baseBlock.onPlantGrow(world, x, y - 1, z, x, y, z);
                    width = 3;
                    byte b1 = 0;
                    int l1;
                    int posX;
                    int j2;
                    int i3;

                    for (posY = y - width + treeHeight; posY <= y + treeHeight; ++posY) {
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
                        Block block = world.func_147439_a(x, y + posY, z);

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

    private void growVines(World world, int x, int starty, int z, int meta) {
        this.func_150516_a(world, x, starty, z, GlaciosBlocks.iceVine, meta);
        int i1 = 4;

        while (true) {
            --starty;

            if (world.func_147439_a(x, starty, z).isAir(world, x, starty, z) || i1 <= 0) {
                return;
            }

            this.func_150516_a(world, x, starty, z, GlaciosBlocks.iceVine, meta);
            --i1;
        }
    }

}
