package glacios.world.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderGlacios implements IChunkProvider {
    private World world;
    private Random rand;
    private MapGenBase caveGenerator = new MapGenCavesGlacios();

    private double[] noiseField;
    private double[] stoneNoise = new double[256];
    float[] parabolicField;

    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    private NoiseGeneratorOctaves noiseGen4;
    public NoiseGeneratorOctaves noiseGen5;
    public NoiseGeneratorOctaves noiseGen6;
    public NoiseGeneratorOctaves mobSpawnerNoise;

    double[] noise1;
    double[] noise2;
    double[] noise3;
    double[] noise4;
    double[] noise5;
    double[] noise6;

    private BiomeGenBase[] biomesForGeneration;

    public ChunkProviderGlacios(World worldObj, long seed) {
        world = worldObj;
        rand = new Random(seed);

        noiseGen1 = new NoiseGeneratorOctaves(rand, 16);
        noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
        noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
        noiseGen4 = new NoiseGeneratorOctaves(rand, 4);
        noiseGen5 = new NoiseGeneratorOctaves(rand, 10);
        noiseGen6 = new NoiseGeneratorOctaves(rand, 16);
        mobSpawnerNoise = new NoiseGeneratorOctaves(rand, 8);

        NoiseGeneratorOctaves[] noiseGens = { noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5, noiseGen6, mobSpawnerNoise };
        noiseGens = TerrainGen.getModdedNoiseGenerators(worldObj, rand, noiseGens);
        noiseGen1 = noiseGens[0];
        noiseGen2 = noiseGens[1];
        noiseGen3 = noiseGens[2];
        noiseGen4 = noiseGens[3];
        noiseGen5 = noiseGens[4];
        noiseGen6 = noiseGens[5];
        mobSpawnerNoise = noiseGens[6];
    }

    /*
     * Used in provideChunk to generate the terrain.
     */
    private void generateTerrain(int posX, int posZ, byte[] chunkBlocks) {
        byte b0 = 4;
        byte b1 = 16;
        byte waterLevel = 63;
        int k = b0 + 1;
        byte b3 = 17;
        int l = b0 + 1;
        biomesForGeneration = world.getWorldChunkManager().getBiomesForGeneration(biomesForGeneration, posX * 4 - 2, posZ * 4 - 2, k + 5, l + 5);
        noiseField = initializeNoiseField(noiseField, posX * b0, 0, posZ * b0, k, b3, l);

        for (int i1 = 0; i1 < b0; ++i1) {
            for (int j1 = 0; j1 < b0; ++j1) {
                for (int k1 = 0; k1 < b1; ++k1) {
                    double d0 = 0.125D;
                    double d1 = noiseField[((i1 + 0) * l + j1 + 0) * b3 + k1 + 0];
                    double d2 = noiseField[((i1 + 0) * l + j1 + 1) * b3 + k1 + 0];
                    double d3 = noiseField[((i1 + 1) * l + j1 + 0) * b3 + k1 + 0];
                    double d4 = noiseField[((i1 + 1) * l + j1 + 1) * b3 + k1 + 0];
                    double d5 = (noiseField[((i1 + 0) * l + j1 + 0) * b3 + k1 + 1] - d1) * d0;
                    double d6 = (noiseField[((i1 + 0) * l + j1 + 1) * b3 + k1 + 1] - d2) * d0;
                    double d7 = (noiseField[((i1 + 1) * l + j1 + 0) * b3 + k1 + 1] - d3) * d0;
                    double d8 = (noiseField[((i1 + 1) * l + j1 + 1) * b3 + k1 + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 8; ++l1) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 4; ++i2) {
                            int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
                            short short1 = 128;
                            j2 -= short1;
                            double d14 = 0.25D;
                            double d15 = (d11 - d10) * d14;
                            double d16 = d10 - d15;

                            for (int k2 = 0; k2 < 4; ++k2) {
                                if ((d16 += d15) > 0.0D) {
                                    chunkBlocks[j2 += short1] = (byte) /* GlaciosBlocks.slate.blockID */Block.stone.blockID;
                                } else if (k1 * 8 + l1 == waterLevel - 1) {
                                    chunkBlocks[j2 += short1] = (byte) Block.ice.blockID;
                                } else if (k1 * 8 + l1 < waterLevel - 1) {
                                    chunkBlocks[j2 += short1] = (byte) Block.waterStill.blockID;
                                } else {
                                    chunkBlocks[j2 += short1] = 0;
                                }
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    /*
     * Replaces the stone that was placed in with blocks that match the biome.
     */
    public void replaceBlocksForBiome(int posX, int posZ, byte[] chunkBlocks, BiomeGenBase[] biomesForGeneration2) {
        byte waterLevel = 63;
        double scale = 0.03125D;
        stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, posX * 16, posZ * 16, 0, 16, 16, 1, scale * 2.0D, scale * 2.0D, scale * 2.0D);

        for (int k = 0; k < 16; ++k) {
            for (int l = 0; l < 16; ++l) {
                BiomeGenBase biomegenbase = biomesForGeneration2[l + k * 16];
                int i1 = (int) (stoneNoise[k + l * 16] / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
                int j1 = -1;
                byte blockTop = biomegenbase.topBlock;
                byte blockFill = biomegenbase.fillerBlock;

                for (int height = 127; height >= 0; --height) {
                    int l1 = (l * 16 + k) * 128 + height;

                    if (height <= 0 + rand.nextInt(5)) {
                        chunkBlocks[l1] = (byte) Block.bedrock.blockID;
                    } else {
                        byte block = chunkBlocks[l1];

                        if (block == 0) {
                            j1 = -1;
                        } else if (block == /* GlaciosBlocks.slate.blockID */Block.stone.blockID) {
                            if (j1 == -1) {
                                if (i1 <= 0) {
                                    if (height < waterLevel)
                                        blockTop = (byte) Block.ice.blockID;
                                    else
                                        blockTop = 0;
                                    blockFill = (byte) /* GlaciosBlocks.slate.blockID */Block.stone.blockID;
                                } else if (height >= waterLevel - 4 && height <= waterLevel + 1) {
                                    blockTop = biomegenbase.topBlock;
                                    blockFill = biomegenbase.fillerBlock;
                                }

                                j1 = i1;

                                if (height >= waterLevel - 1) {
                                    chunkBlocks[l1] = blockTop;
                                } else {
                                    chunkBlocks[l1] = blockFill;
                                }
                            } else if (j1 > 0) {
                                --j1;
                                chunkBlocks[l1] = blockFill;
                            }
                        }
                    }
                }
            }
        }
    }

    /*
     * Loads or generates the chunk at the chunk location specified.
     */
    @Override
    public Chunk loadChunk(int par1, int par2) {
        return provideChunk(par1, par2);
    }

    /*
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    @Override
    public Chunk provideChunk(int posX, int posZ) {
        rand.setSeed(posX * 341873128712L + posZ * 132897987541L);
        byte[] chunkBlocks = new byte[32768];
        generateTerrain(posX, posZ, chunkBlocks);
        biomesForGeneration = world.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, posX * 16, posZ * 16, 16, 16);
        replaceBlocksForBiome(posX, posZ, chunkBlocks, biomesForGeneration);

        caveGenerator.generate(this, world, posX, posZ, chunkBlocks);

        Chunk chunk = new Chunk(world, chunkBlocks, posX, posZ);
        byte[] biomeArr = chunk.getBiomeArray();

        for (int i = 0; i < biomeArr.length; ++i) {
            biomeArr[i] = (byte) biomesForGeneration[i].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    /*
     * Checks to see if a chunk exists at x, y
     */
    @Override
    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    /*
     * Populates chunk with ores etc etc
     */
    @Override
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
        int k = par2 * 16;
        int l = par3 * 16;
        BiomeGenBase biomegenbase = world.getBiomeGenForCoords(k + 16, l + 16);

        rand.setSeed(world.getSeed());
        long var4 = rand.nextLong() / 2L * 2L + 1L;
        long var6 = rand.nextLong() / 2L * 2L + 1L;
        rand.setSeed(par2 * var4 + par3 * var6 ^ world.getSeed());

        biomegenbase.decorate(world, rand, k, l);
        SpawnerAnimals.performWorldGenSpawning(world, biomegenbase, k + 8, l + 8, 16, 16, rand);
    }

    /*
     * Two modes of operation: if passed true, save all Chunks in one go. If passed false, save up to two chunks. Return
     * true if all chunks have been saved.
     */
    @Override
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
        return true;
    }

    /*
     * Unloads the 100 oldest chunks from memory, due to a bug with chunkSet.add() never being called it thinks the list
     * is always empty and will not remove any chunks.
     */
    public boolean unload100OldestChunks() {
        return false;
    }

    /*
     * Returns if the IChunkProvider supports saving.
     */
    @Override
    public boolean canSave() {
        return true;
    }

    /*
     * Converts the instance data to a readable string.
     */
    @Override
    public String makeString() {
        return "GlaciosLevelSource";
    }

    /*
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    @Override
    public List<?> getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
        BiomeGenBase var5 = world.getBiomeGenForCoords(par2, par4);
        return var5 == null ? null : var5.getSpawnableList(par1EnumCreatureType);
    }

    /*
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    @Override
    public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5) {
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public void recreateStructures(int var1, int var2) {

    }

    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    /*
     * Generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
     * size.
     */
    private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7) {
        if (par1ArrayOfDouble == null) {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }

        if (parabolicField == null) {
            parabolicField = new float[25];

            for (int k1 = -2; k1 <= 2; ++k1) {
                for (int l1 = -2; l1 <= 2; ++l1) {
                    float f = 10.0F / MathHelper.sqrt_float(k1 * k1 + l1 * l1 + 0.2F);
                    parabolicField[k1 + 2 + (l1 + 2) * 5] = f;
                }
            }
        }

        double d0 = 684.412D;
        double d1 = 684.412D;
        noise5 = noiseGen5.generateNoiseOctaves(noise5, par2, par4, par5, par7, 1.121D, 1.121D, 0.5D);
        noise6 = noiseGen6.generateNoiseOctaves(noise6, par2, par4, par5, par7, 200.0D, 200.0D, 0.5D);
        noise3 = noiseGen3.generateNoiseOctaves(noise3, par2, par3, par4, par5, par6, par7, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
        noise1 = noiseGen1.generateNoiseOctaves(noise1, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        noise2 = noiseGen2.generateNoiseOctaves(noise2, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        int i2 = 0;
        int j2 = 0;

        for (int k2 = 0; k2 < par5; ++k2) {
            for (int l2 = 0; l2 < par7; ++l2) {
                float f1 = 0.0F;
                float f2 = 0.0F;
                float f3 = 0.0F;
                byte b0 = 2;
                BiomeGenBase biomegenbase = biomesForGeneration[k2 + 2 + (l2 + 2) * (par5 + 5)];

                for (int i3 = -b0; i3 <= b0; ++i3) {
                    for (int j3 = -b0; j3 <= b0; ++j3) {
                        BiomeGenBase biomegenbase1 = biomesForGeneration[k2 + i3 + 2 + (l2 + j3 + 2) * (par5 + 5)];
                        float f4 = parabolicField[i3 + 2 + (j3 + 2) * 5] / (biomegenbase1.minHeight + 2.0F);

                        if (biomegenbase1.minHeight > biomegenbase.minHeight) {
                            f4 /= 2.0F;
                        }

                        f1 += biomegenbase1.maxHeight * f4;
                        f2 += biomegenbase1.minHeight * f4;
                        f3 += f4;
                    }
                }

                f1 /= f3;
                f2 /= f3;
                f1 = f1 * 0.9F + 0.1F;
                f2 = (f2 * 4.0F - 1.0F) / 8.0F;
                double d2 = noise6[j2] / 8000.0D;

                if (d2 < 0.0D) {
                    d2 = -d2 * 0.3D;
                }

                d2 = d2 * 3.0D - 2.0D;

                if (d2 < 0.0D) {
                    d2 /= 2.0D;

                    if (d2 < -1.0D) {
                        d2 = -1.0D;
                    }

                    d2 /= 1.4D;
                    d2 /= 2.0D;
                } else {
                    if (d2 > 1.0D) {
                        d2 = 1.0D;
                    }

                    d2 /= 8.0D;
                }

                ++j2;

                for (int k3 = 0; k3 < par6; ++k3) {
                    double d3 = f2;
                    double d4 = f1;
                    d3 += d2 * 0.2D;
                    d3 = d3 * par6 / 16.0D;
                    double d5 = par6 / 2.0D + d3 * 4.0D;
                    double d6 = 0.0D;
                    double d7 = (k3 - d5) * 12.0D * 128.0D / 128.0D / d4;

                    if (d7 < 0.0D) {
                        d7 *= 4.0D;
                    }

                    double d8 = noise1[i2] / 512.0D;
                    double d9 = noise2[i2] / 512.0D;
                    double d10 = (noise3[i2] / 10.0D + 1.0D) / 2.0D;

                    if (d10 < 0.0D) {
                        d6 = d8;
                    } else if (d10 > 1.0D) {
                        d6 = d9;
                    } else {
                        d6 = d8 + (d9 - d8) * d10;
                    }

                    d6 -= d7;

                    if (k3 > par6 - 4) {
                        double d11 = (k3 - (par6 - 4)) / 3.0F;
                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    par1ArrayOfDouble[i2] = d6;
                    ++i2;
                }
            }
        }

        return par1ArrayOfDouble;
    }

}
