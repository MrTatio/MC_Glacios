package roundaround.mcmods.glacios.world.biome;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import roundaround.mcmods.glacios.GlaciosBlocks;
import roundaround.mcmods.glacios.world.decoration.IGlaciosDecoration;
import roundaround.mcmods.glacios.world.gen.feature.WorldGenGlaciosFlora;

public abstract class BiomeGenGlacios extends BiomeGenBase implements IGlaciosDecoration {

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
    public void genTerrainBlocks(World world, Random rand, Block[] blockArr, byte[] metaArr, int p_150573_5_, int p_150573_6_, double p_150573_7_) {
        boolean flag = true;
        Block block = this.topBlock;
        byte b0 = (byte) (this.field_150604_aj & 255);
        Block block1 = this.fillerBlock;
        int k = -1;
        int l = (int) (p_150573_7_ / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int i1 = p_150573_5_ & 15;
        int j1 = p_150573_6_ & 15;
        int k1 = blockArr.length / 256;

        for (int l1 = 255; l1 >= 0; --l1) {
            int i2 = (j1 * 16 + i1) * k1 + l1;

            if (l1 <= 0 + rand.nextInt(5)) {
                blockArr[i2] = Blocks.bedrock;
            } else {
                Block block2 = blockArr[i2];

                if (block2 != null && block2.getMaterial() != Material.air) {
                    if (block2 == GlaciosBlocks.slate) {
                        if (k == -1) {
                            if (l <= 0) {
                                block = null;
                                b0 = 0;
                                block1 = GlaciosBlocks.slate;
                            } else if (l1 >= 59 && l1 <= 64) {
                                block = this.topBlock;
                                b0 = (byte) (this.field_150604_aj & 255);
                                block1 = this.fillerBlock;
                            }

                            if (l1 < 63 && (block == null || block.getMaterial() == Material.air)) {
                                if (this.getFloatTemperature(p_150573_5_, l1, p_150573_6_) < 0.15F) {
                                    block = Blocks.ice;
                                    b0 = 0;
                                } else {
                                    block = Blocks.water;
                                    b0 = 0;
                                }
                            }

                            k = l;

                            if (l1 >= 62) {
                                blockArr[i2] = block;
                                metaArr[i2] = b0;
                            } else {
                                blockArr[i2] = block1;
                            }
                        } else if (k > 0) {
                            --k;
                            blockArr[i2] = block1;
                        }
                    }
                } else {
                    k = -1;
                }
            }
        }
    }

    @Override
    public WorldGenGlaciosFlora getRandomWorldGenForFlowers(Random random) {
        HashMap<WorldGenGlaciosFlora, Integer> noRand = getWeightedWorldGenForFlowers();
        if (noRand != null && !noRand.isEmpty()) {
            return getRandomWeightedWorldGenerator(noRand);
        } else {
            return null;
        }
    }

    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random random) {
        if (getWeightedWorldGenForGrass() != null && !getWeightedWorldGenForGrass().isEmpty()) {
            return getRandomWeightedWorldGenerator(getWeightedWorldGenForGrass());
        } else {
            return super.getRandomWorldGenForGrass(random);
        }
    }

    @Override
    public HashMap<WorldGenerator, Double> getWeightedWorldGenForGrass() {
        return null;
    }

    @Override
    public HashMap<WorldGenGlaciosFlora, Integer> getWeightedWorldGenForFlowers() {
        return null;
    }

    public static <T extends WorldGenerator> T getRandomWeightedWorldGenerator(HashMap<T, ? extends Number> worldGeneratorMap) {
        double completeWeight = 0D;

        for (Number weight : worldGeneratorMap.values()) {
            completeWeight += Double.parseDouble(weight.toString());
        }

        double random = Math.random() * completeWeight;
        double countWeight = 0D;

        for (Entry<T, ? extends Number> entry : worldGeneratorMap.entrySet()) {
            countWeight += Double.parseDouble(entry.getValue().toString());

            if (countWeight >= random)
                return entry.getKey();
        }

        return null;
    }
}
