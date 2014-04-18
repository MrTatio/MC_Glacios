package roundaround.mcmods.glacios.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import roundaround.mcmods.glacios.GlaciosBlocks;
import roundaround.mcmods.glacios.block.BlockSaplingGlacios;

public class WorldGenHomeTree extends WorldGenAbstractTree {
    static final byte[] otherCoordPairs = new byte[] { (byte) 2, (byte) 0, (byte) 0, (byte) 1, (byte) 2, (byte) 1 };

    Random rand = new Random();
    World worldObj;

    int[] basePos = new int[] { 0, 0, 0 };

    final double heightAttenuation = 0.45D;
    final double branchDensity = 3.0D;
    final double branchSlope = 0.45D;
    final double minHeightScaler = 38D;
    final double heightVarianceScaler = 12D;

    int heightLimit;
    int height;
    double scaleWidth;
    double leafDensity;
    int minHeight;
    int hightVariance;
    int leafDistanceLimit;

    int[][] leafNodes;

    final int meta;

    public WorldGenHomeTree(boolean doBlockNotify, int meta) {
        super(doBlockNotify);
        this.meta = meta;
        this.setScale(1.0D, 1.0D, 1.0D);
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        this.worldObj = par1World;
        long l = par2Random.nextLong();
        this.rand.setSeed(l);
        this.basePos[0] = par3;
        this.basePos[1] = par4;
        this.basePos[2] = par5;

        if (this.heightLimit == 0) {
            this.heightLimit = this.minHeight + this.rand.nextInt(this.hightVariance);
        }

        if (!this.validTreeLocation()) {
            return false;
        } else {
            this.generateLeafNodeList();
            this.generateLeaves();
            this.generateTrunk();
            this.generateLeafNodeBases();
            return true;
        }
    }

    void generateLeafNodeList() {
        this.height = (int) (this.heightLimit * this.heightAttenuation);

        if (this.height >= this.heightLimit) {
            this.height = this.heightLimit - 1;
        }

        int i = (int) (1.382D + Math.pow(this.leafDensity * this.heightLimit / 13.0D, 2.0D));

        if (i < 1) {
            i = 1;
        }

        int[][] aint = new int[i * this.heightLimit][4];
        int j = this.basePos[1] + this.heightLimit - this.leafDistanceLimit;
        int k = 1;
        int l = this.basePos[1] + this.height;
        int i1 = j - this.basePos[1];
        aint[0][0] = this.basePos[0];
        aint[0][1] = j;
        aint[0][2] = this.basePos[2];
        aint[0][3] = l;
        --j;

        while (i1 >= 0) {
            int j1 = 0;
            float f = this.layerSize(i1);

            if (f < 0.0F) {
                --j;
                --i1;
            } else {
                for (double d0 = 0.5D; j1 < i; ++j1) {
                    double d1 = this.scaleWidth * f * (this.rand.nextFloat() + 0.328D);
                    double d2 = this.rand.nextFloat() * 2.0D * Math.PI;
                    int k1 = MathHelper.floor_double(d1 * Math.sin(d2) + this.basePos[0] + d0);
                    int l1 = MathHelper.floor_double(d1 * Math.cos(d2) + this.basePos[2] + d0);
                    int[] aint1 = new int[] { k1, j, l1 };
                    int[] aint2 = new int[] { k1, j + this.leafDistanceLimit, l1 };

                    if (this.checkBlockLine(aint1, aint2) == -1) {
                        int[] aint3 = new int[] { this.basePos[0], this.basePos[1], this.basePos[2] };
                        double d3 = Math.sqrt(Math.pow(Math.abs(this.basePos[0] - aint1[0]), 2.0D) + Math.pow(Math.abs(this.basePos[2] - aint1[2]), 2.0D));
                        double d4 = d3 * this.branchSlope;

                        if (aint1[1] - d4 > l) {
                            aint3[1] = l;
                        } else {
                            aint3[1] = (int) (aint1[1] - d4);
                        }

                        if (this.checkBlockLine(aint3, aint1) == -1) {
                            aint[k][0] = k1;
                            aint[k][1] = j;
                            aint[k][2] = l1;
                            aint[k][3] = aint3[1];
                            ++k;
                        }
                    }
                }

                --j;
                --i1;
            }
        }

        this.leafNodes = new int[k][4];
        System.arraycopy(aint, 0, this.leafNodes, 0, k);
    }

    void genTreeLayer(int posX, int posY, int posZ, float p_150529_4_, byte p_150529_5_, Block block) {
        int l = (int) (p_150529_4_ + 0.618D);
        byte b1 = otherCoordPairs[p_150529_5_];
        byte b2 = otherCoordPairs[p_150529_5_ + 3];
        int[] basePos = new int[] { posX, posY, posZ };
        int[] posOffset = new int[] { 0, 0, 0 };
        int i1 = -l;
        int j1 = -l;

        for (posOffset[p_150529_5_] = basePos[p_150529_5_]; i1 <= l; ++i1) {
            posOffset[b1] = basePos[b1] + i1;
            j1 = -l;

            while (j1 <= l) {
                double d0 = Math.pow(Math.abs(i1) + 0.5D, 2.0D) + Math.pow(Math.abs(j1) + 0.5D, 2.0D);

                if (d0 > p_150529_4_ * p_150529_4_) {
                    ++j1;
                } else {
                    posOffset[b2] = basePos[b2] + j1;
                    Block block1 = this.worldObj.getBlock(posOffset[0], posOffset[1], posOffset[2]);

                    if (!block1.isAir(worldObj, posOffset[0], posOffset[1], posOffset[2]) && !block1.isLeaves(worldObj, posOffset[0], posOffset[1], posOffset[2])) {
                        ++j1;
                    } else {
                        this.setBlockAndNotifyAdequately(this.worldObj, posOffset[0], posOffset[1], posOffset[2], block, this.meta);
                        ++j1;
                    }
                }
            }
        }
    }

    float layerSize(int par1) {
        if (par1 < (this.heightLimit) * 0.3D) {
            return -1.618F;
        } else {
            float f = this.heightLimit / 2.0F;
            float f1 = this.heightLimit / 2.0F - par1;
            float f2;

            if (f1 == 0.0F) {
                f2 = f;
            } else if (Math.abs(f1) >= f) {
                f2 = 0.0F;
            } else {
                f2 = (float) Math.sqrt(Math.pow(Math.abs(f), 2.0D) - Math.pow(Math.abs(f1), 2.0D));
            }

            f2 *= 0.5F;
            return f2;
        }
    }

    float leafSize(int par1) {
        return par1 >= 0 && par1 < this.leafDistanceLimit ? (par1 != 0 && par1 != this.leafDistanceLimit - 1 ? 3.0F : 2.0F) : -1.0F;
    }

    void generateLeafNode(int par1, int par2, int par3) {
        int l = par2;

        for (int i1 = par2 + this.leafDistanceLimit; l < i1; ++l) {
            float f = this.leafSize(l - par2);
            this.genTreeLayer(par1, l, par3, f, (byte) 1, GlaciosBlocks.leaves);
        }
    }

    void placeBlockLine(int[] startPos, int[] endPos, Block block, int[][] wideners, boolean surroundWithLeaves) {
        int[] posOffset = new int[] { 0, 0, 0 };
        byte currIndex = 0;
        byte maxIndex;

        for (maxIndex = 0; currIndex < 3; ++currIndex) {
            posOffset[currIndex] = endPos[currIndex] - startPos[currIndex];

            if (Math.abs(posOffset[currIndex]) > Math.abs(posOffset[maxIndex])) {
                maxIndex = currIndex;
            }
        }

        if (posOffset[maxIndex] != 0) {
            byte b2 = otherCoordPairs[maxIndex];
            byte b3 = otherCoordPairs[maxIndex + 3];
            byte b4;

            if (posOffset[maxIndex] > 0) {
                b4 = 1;
            } else {
                b4 = -1;
            }

            double d0 = (double) posOffset[b2] / (double) posOffset[maxIndex];
            double d1 = (double) posOffset[b3] / (double) posOffset[maxIndex];
            int[] blockPos = new int[] { 0, 0, 0 };
            int i = 0;

            for (int j = posOffset[maxIndex] + b4; i != j; i += b4) {
                blockPos[maxIndex] = MathHelper.floor_double(startPos[maxIndex] + i + 0.5D);
                blockPos[b2] = MathHelper.floor_double(startPos[b2] + i * d0 + 0.5D);
                blockPos[b3] = MathHelper.floor_double(startPos[b3] + i * d1 + 0.5D);
                byte blockMeta = (byte) this.meta;
                int distX = Math.abs(blockPos[0] - startPos[0]);
                int distZ = Math.abs(blockPos[2] - startPos[2]);
                int maxDist = Math.max(distX, distZ);

                if (maxDist > 0) {
                    if (distX == maxDist) {
                        blockMeta += 4;
                    } else {
                        blockMeta += 8;
                    }
                }
                
                if (surroundWithLeaves) {
                    this.setBlockAndSurroundWithLeaves(this.worldObj, blockPos[0], blockPos[1], blockPos[2], block, blockMeta);
    
                    for (int k = 0; k < wideners.length; k++) {
                        this.setBlockAndSurroundWithLeaves(this.worldObj, blockPos[0] + wideners[k][0], blockPos[1] + wideners[k][1], blockPos[2]
                                + wideners[k][2], block, blockMeta);
                    }
                } else {
                    this.setBlockAndNotifyAdequately(this.worldObj, blockPos[0], blockPos[1], blockPos[2], block, blockMeta);
    
                    for (int k = 0; k < wideners.length; k++) {
                        this.setBlockAndNotifyAdequately(this.worldObj, blockPos[0] + wideners[k][0], blockPos[1] + wideners[k][1], blockPos[2]
                                + wideners[k][2], block, blockMeta);
                    }
                }
            }
        }
    }
    
    void setBlockAndSurroundWithLeaves(World world, int x, int y, int z, Block block, int blockMeta) {
        this.setBlockAndNotifyAdequately(world, x, y, z, block, blockMeta);
        
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                for (int k = -2; k <= 2; k++) {
                    if (world.isAirBlock(x + i, y + j, z + k)) {
                        this.setBlockAndNotifyAdequately(world, x + i, y + j, z + k, GlaciosBlocks.leaves, this.meta);
                    }
                }
            }
        }
    }

    void generateLeaves() {
        int i = 0;

        for (int j = this.leafNodes.length; i < j; ++i) {
            int k = this.leafNodes[i][0];
            int l = this.leafNodes[i][1];
            int i1 = this.leafNodes[i][2];
            this.generateLeafNode(k, l, i1);
        }
    }

    boolean leafNodeNeedsBase(int par1) {
        return par1 >= this.heightLimit * 0.2D;
    }

    void generateTrunk() {
        int i = this.basePos[0];
        int j = this.basePos[1];
        int k = this.basePos[1] + this.height;
        int l = this.basePos[2];
        int[] startPos = new int[] { i, j, l };
        int[] endPos = new int[] { i, k, l };
        int[][] wideners = new int[][] {
            { -1, 0, -1 }, { -1, 0, 0 }, { -1, 0, 1 },
            { 0, 0, -1 }, { 0, 0, 1 },
            { 1, 0, -1 }, { 1, 0, 0 }, { 1, 0, 1 }
        };
        
        this.placeBlockLine(startPos, endPos, GlaciosBlocks.log, wideners, false);
    }

    void generateLeafNodeBases() {
        int i = 0;
        int j = this.leafNodes.length;

        for (int[] aint = new int[] { this.basePos[0], this.basePos[1], this.basePos[2] }; i < j; ++i) {
            int[] aint1 = this.leafNodes[i];
            int[] aint2 = new int[] { aint1[0], aint1[1], aint1[2] };
            aint[1] = aint1[3];
            int k = aint[1] - this.basePos[1];

            if (this.leafNodeNeedsBase(k)) {
                byte maxDist = 0;
                if (Math.abs(aint2[1] - aint[1]) >= Math.abs(aint2[maxDist] - aint[maxDist])) // Favor Y
                    maxDist = 1;
                if (Math.abs(aint2[2] - aint[2]) > Math.abs(aint2[maxDist] - aint[maxDist]))
                    maxDist = 2;
                
                boolean which = rand.nextBoolean();
                int[][] wideners = new int[][] {{ 0, 0, 0 }};
                for (int l = 0; l < 3; l++) {
                    if (l != maxDist) {
                        wideners[0][l] = which ? (rand.nextInt(3) - 1) : (rand.nextBoolean() ? 1 : -1);
                        which = !which;
                    }
                }
                
                this.placeBlockLine(aint, aint2, GlaciosBlocks.log, wideners, true);
            }
        }
    }

    int checkBlockLine(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger) {
        int[] aint2 = new int[] { 0, 0, 0 };
        byte b0 = 0;
        byte b1;

        for (b1 = 0; b0 < 3; ++b0) {
            aint2[b0] = par2ArrayOfInteger[b0] - par1ArrayOfInteger[b0];

            if (Math.abs(aint2[b0]) > Math.abs(aint2[b1])) {
                b1 = b0;
            }
        }

        if (aint2[b1] == 0) {
            return -1;
        } else {
            byte b2 = otherCoordPairs[b1];
            byte b3 = otherCoordPairs[b1 + 3];
            byte b4;

            if (aint2[b1] > 0) {
                b4 = 1;
            } else {
                b4 = -1;
            }

            double d0 = (double) aint2[b2] / (double) aint2[b1];
            double d1 = (double) aint2[b3] / (double) aint2[b1];
            int[] aint3 = new int[] { 0, 0, 0 };
            int i = 0;
            int j;

            for (j = aint2[b1] + b4; i != j; i += b4) {
                aint3[b1] = par1ArrayOfInteger[b1] + i;
                aint3[b2] = MathHelper.floor_double(par1ArrayOfInteger[b2] + i * d0);
                aint3[b3] = MathHelper.floor_double(par1ArrayOfInteger[b3] + i * d1);
                Block block = this.worldObj.getBlock(aint3[0], aint3[1], aint3[2]);

                if (!this.isReplaceable(worldObj, aint3[0], aint3[1], aint3[2])) {
                    break;
                }
            }

            return i == j ? -1 : Math.abs(i);
        }
    }

    boolean validTreeLocation() {
        int[] aint = new int[] { this.basePos[0], this.basePos[1], this.basePos[2] };
        int[] aint1 = new int[] { this.basePos[0], this.basePos[1] + this.heightLimit - 1, this.basePos[2] };
        
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(this.basePos[0], this.basePos[2]);
        if (!BlockSaplingGlacios.isSupportedByBlock(biome.topBlock, this.meta))
            return false;
        
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                if (this.isReplaceable(this.worldObj, this.basePos[0] + x, this.basePos[1] - 1, this.basePos[2] + z))
                    this.worldObj.setBlock(this.basePos[0] + x, this.basePos[1] - 1, this.basePos[2] + z, biome.topBlock, biome.field_150604_aj, 3);
            }
        }
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (this.isReplaceable(this.worldObj, this.basePos[0] + x, this.basePos[1] - 2, this.basePos[2] + z))
                    this.worldObj.setBlock(this.basePos[0] + x, this.basePos[1] - 1, this.basePos[2] + z, biome.fillerBlock, biome.field_76754_C, 3);
            }
        }

        int check = this.checkBlockLine(aint, aint1);

        if (check == -1) {
            return true;
        } else if (check < 6) {
            return false;
        } else {
            this.heightLimit = check;
            return true;
        }
    }

    public WorldGenHomeTree setThisScale(double par1, double par3, double par5) {
        this.setScale(par1, par3, par5);
        return this;
    }

    @Override
    public void setScale(double height, double width, double density) {
        this.hightVariance = (int) (height * heightVarianceScaler);
        this.minHeight = (int) (height * minHeightScaler);

        this.leafDistanceLimit = height > 0.5D ? 5 : 4;

        this.scaleWidth = width;
        this.leafDensity = height > 0.5 ? 1.2 * density : density;
    }
}
