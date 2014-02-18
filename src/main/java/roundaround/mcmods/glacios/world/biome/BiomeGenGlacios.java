package roundaround.mcmods.glacios.world.biome;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import roundaround.mcmods.glacios.GlaciosBlocks;

public abstract class BiomeGenGlacios extends BiomeGenBase {

    public BiomeGenGlacios(int biomeId) {
        this(biomeId, true);
    }

    public BiomeGenGlacios(int biomeId, boolean register) {
        super(biomeId, register);
        this.topBlock = GlaciosBlocks.gelisolFrost;
        this.fillerBlock = GlaciosBlocks.gelisol;
        this.field_150604_aj = 0;
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
    }

    @Override
    public void decorate(World world, Random random, int x, int z) {
        try {
            super.decorate(world, random, x, z);
        } catch (Exception e) {
            Throwable cause = e.getCause();

            if (e.getMessage() != null && e.getMessage().equals("Already decorating!!")
                    || (cause != null && cause.getMessage() != null && cause.getMessage().equals("Already decorating!!"))) {
            } else {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void genTerrainBlocks(World world, Random rand, Block[] blockArr, byte[] metaArr, int chunkPosX, int chunkPosZ, double fillerThicknessChance) {
        Block topBlock = this.topBlock;
        byte blockMeta = (byte) (this.field_150604_aj & 255);
        Block fillerBlock = this.fillerBlock;
        int fillerHeight = -1;
        int fillerThickness = (int) (fillerThicknessChance / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int posX = chunkPosX & 15;
        int posZ = chunkPosZ & 15;
        int sizeOffset = blockArr.length / 256;

        for (int posY = 255; posY >= 0; --posY) {
            int position = (posZ * 16 + posX) * sizeOffset + posY;

            if (posY <= 0 + rand.nextInt(5)) {
                blockArr[position] = Blocks.bedrock;
            } else {
                Block currBlock = blockArr[position];

                if (currBlock != null && currBlock.getMaterial() != Material.air) {
                    if (currBlock == GlaciosBlocks.slate) {
                        if (fillerHeight == -1) {
                            if (fillerThickness <= 0) {
                                topBlock = null;
                                blockMeta = 0;
                                fillerBlock = GlaciosBlocks.slate;
                            } else if (posY >= 59 && posY <= 64) {
                                topBlock = this.topBlock;
                                blockMeta = (byte) (this.field_150604_aj & 255);
                                fillerBlock = this.fillerBlock;
                            }

                            if (posY < 63 && (topBlock == null || topBlock.getMaterial() == Material.air)) {
                                topBlock = GlaciosBlocks.crystalWater;
                                blockMeta = 0;
                            }

                            fillerHeight = fillerThickness;

                            if (posY >= 62) {
                                blockArr[position] = topBlock;
                                metaArr[position] = blockMeta;
                            } else {
                                blockArr[position] = fillerBlock;
                            }
                        } else if (fillerHeight > 0) {
                            --fillerHeight;
                            blockArr[position] = fillerBlock;
                        }
                    }
                } else {
                    fillerHeight = -1;
                }
            }
        }
    }

    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random rand) {
        return null;
    }

    @Override
    public WorldGenAbstractTree func_150567_a(Random rand) {
        return null;
    }
}
