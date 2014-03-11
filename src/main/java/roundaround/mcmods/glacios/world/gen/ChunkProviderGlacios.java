package roundaround.mcmods.glacios.world.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import roundaround.mcmods.glacios.GlaciosBlocks;
import cpw.mods.fml.common.eventhandler.Event.Result;

public class ChunkProviderGlacios implements IChunkProvider {
    /**
     * RNG.
     */
    private Random rand;
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    private NoiseGeneratorPerlin noiseGen4;
    /**
     * A NoiseGeneratorOctaves used in generating terrain
     */
    public NoiseGeneratorOctaves noiseGen5;
    /**
     * A NoiseGeneratorOctaves used in generating terrain
     */
    public NoiseGeneratorOctaves noiseGen6;
    public NoiseGeneratorOctaves mobSpawnerNoise;
    /**
     * Reference to the World object.
     */
    private World worldObj;
    /**
     * are map structures going to be generated (e.g. strongholds)
     */
    private final boolean mapFeaturesEnabled;
    private WorldType worldType;
    private final double[] field_147434_q;
    private final float[] parabolicField;
    private double[] stoneNoise = new double[256];
    /**
     * The biomes that are used to generate the chunk
     */
    private BiomeGenBase[] biomesForGeneration;
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    int[][] field_73219_j = new int[32][32];

    public ChunkProviderGlacios(World world, long seed, boolean mapFeaturesEnabled) {
        this.worldObj = world;
        this.mapFeaturesEnabled = mapFeaturesEnabled;
        this.worldType = world.getWorldInfo().getTerrainType();
        this.rand = new Random(seed);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGen4 = new NoiseGeneratorPerlin(this.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.field_147434_q = new double[825];
        this.parabolicField = new float[25];

        for (int j = -2; j <= 2; ++j) {
            for (int k = -2; k <= 2; ++k) {
                float f = 10.0F / MathHelper.sqrt_float(j * j + k * k + 0.2F);
                this.parabolicField[j + 2 + (k + 2) * 5] = f;
            }
        }

        NoiseGenerator[] noiseGens = { noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5, noiseGen6, mobSpawnerNoise };
        noiseGens = TerrainGen.getModdedNoiseGenerators(world, this.rand, noiseGens);
        this.noiseGen1 = (NoiseGeneratorOctaves) noiseGens[0];
        this.noiseGen2 = (NoiseGeneratorOctaves) noiseGens[1];
        this.noiseGen3 = (NoiseGeneratorOctaves) noiseGens[2];
        this.noiseGen4 = (NoiseGeneratorPerlin) noiseGens[3];
        this.noiseGen5 = (NoiseGeneratorOctaves) noiseGens[4];
        this.noiseGen6 = (NoiseGeneratorOctaves) noiseGens[5];
        this.mobSpawnerNoise = (NoiseGeneratorOctaves) noiseGens[6];
    }

    public void func_147424_a(int p_147424_1_, int p_147424_2_, Block[] p_147424_3_) {
        byte b0 = 63;
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, p_147424_1_ * 4 - 2, p_147424_2_ * 4 - 2, 10, 10);
        this.func_147423_a(p_147424_1_ * 4, 0, p_147424_2_ * 4);

        for (int k = 0; k < 4; ++k) {
            int l = k * 5;
            int i1 = (k + 1) * 5;

            for (int j1 = 0; j1 < 4; ++j1) {
                int k1 = (l + j1) * 33;
                int l1 = (l + j1 + 1) * 33;
                int i2 = (i1 + j1) * 33;
                int j2 = (i1 + j1 + 1) * 33;

                for (int k2 = 0; k2 < 32; ++k2) {
                    double d0 = 0.125D;
                    double d1 = this.field_147434_q[k1 + k2];
                    double d2 = this.field_147434_q[l1 + k2];
                    double d3 = this.field_147434_q[i2 + k2];
                    double d4 = this.field_147434_q[j2 + k2];
                    double d5 = (this.field_147434_q[k1 + k2 + 1] - d1) * d0;
                    double d6 = (this.field_147434_q[l1 + k2 + 1] - d2) * d0;
                    double d7 = (this.field_147434_q[i2 + k2 + 1] - d3) * d0;
                    double d8 = (this.field_147434_q[j2 + k2 + 1] - d4) * d0;

                    for (int l2 = 0; l2 < 8; ++l2) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i3 = 0; i3 < 4; ++i3) {
                            int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
                            short short1 = 256;
                            j3 -= short1;
                            double d14 = 0.25D;
                            double d16 = (d11 - d10) * d14;
                            double d15 = d10 - d16;

                            for (int k3 = 0; k3 < 4; ++k3) {
                                if ((d15 += d16) > 0.0D) {
                                    p_147424_3_[j3 += short1] = GlaciosBlocks.slate;
                                } else if (k2 * 8 + l2 < b0) {
                                    p_147424_3_[j3 += short1] = GlaciosBlocks.crystalWater;
                                } else {
                                    p_147424_3_[j3 += short1] = null;
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

    public void func_147422_a(int chunkX, int chunkZ, Block[] blockArray, byte[] metaArray, BiomeGenBase[] biomeArray) {
        ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, chunkX, chunkZ, blockArray, biomeArray);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY)
            return;

        double d0 = 0.03125D;
        this.stoneNoise = this.noiseGen4.func_151599_a(this.stoneNoise, chunkX * 16, chunkZ * 16, 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

        for (int k = 0; k < 16; ++k) {
            for (int l = 0; l < 16; ++l) {
                BiomeGenBase biomegenbase = biomeArray[l + k * 16];
                biomegenbase.genTerrainBlocks(this.worldObj, this.rand, blockArray, metaArray, chunkX * 16 + k, chunkZ * 16 + l, this.stoneNoise[l + k * 16]);
            }
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    @Override
    public Chunk loadChunk(int chunkX, int chunkZ) {
        return this.provideChunk(chunkX, chunkZ);
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the specified chunk from the map seed and chunk seed
     */
    @Override
    public Chunk provideChunk(int chunkX, int chunkZ) {
        this.rand.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        Block[] blockArray = new Block[65536];
        byte[] metaArray = new byte[65536];
        this.func_147424_a(chunkX, chunkZ, blockArray);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);
        this.func_147422_a(chunkX, chunkZ, blockArray, metaArray, this.biomesForGeneration);

        Chunk chunk = new Chunk(this.worldObj, blockArray, metaArray, chunkX, chunkZ);
        byte[] biomeArray = chunk.getBiomeArray();

        for (int k = 0; k < biomeArray.length; ++k) {
            biomeArray[k] = (byte) this.biomesForGeneration[k].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    private void func_147423_a(int offsetX, int offsetY, int offsetZ) {
        this.noiseData4 = this.noiseGen6.generateNoiseOctaves(this.noiseData4, offsetX, offsetZ, 5, 5, 200.0D, 200.0D, 0.5D);
        this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, offsetX, offsetY, offsetZ, 5, 33, 5, 8.55515D, 4.277575D, 8.55515D);
        this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, offsetX, offsetY, offsetZ, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, offsetX, offsetY, offsetZ, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        int l = 0;
        int i1 = 0;

        for (int j1 = 0; j1 < 5; ++j1) {
            for (int k1 = 0; k1 < 5; ++k1) {
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;
                byte b0 = 2;
                BiomeGenBase biomegenbase = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

                for (int l1 = -b0; l1 <= b0; ++l1) {
                    for (int i2 = -b0; i2 <= b0; ++i2) {
                        BiomeGenBase biomegenbase1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
                        float f3 = biomegenbase1.rootHeight;
                        float f4 = biomegenbase1.heightVariation;

                        if (this.worldType == WorldType.AMPLIFIED && f3 > 0.0F) {
                            f3 = 1.0F + f3 * 2.0F;
                            f4 = 1.0F + f4 * 4.0F;
                        }

                        float f5 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);

                        if (biomegenbase1.rootHeight > biomegenbase.rootHeight) {
                            f5 /= 2.0F;
                        }

                        f += f4 * f5;
                        f1 += f3 * f5;
                        f2 += f5;
                    }
                }

                f /= f2;
                f1 /= f2;
                f = f * 0.9F + 0.1F;
                f1 = (f1 * 4.0F - 1.0F) / 8.0F;
                double d13 = this.noiseData4[i1] / 8000.0D;

                if (d13 < 0.0D) {
                    d13 = -d13 * 0.3D;
                }

                d13 = d13 * 3.0D - 2.0D;

                if (d13 < 0.0D) {
                    d13 /= 2.0D;

                    if (d13 < -1.0D) {
                        d13 = -1.0D;
                    }

                    d13 /= 1.4D;
                    d13 /= 2.0D;
                } else {
                    if (d13 > 1.0D) {
                        d13 = 1.0D;
                    }

                    d13 /= 8.0D;
                }

                ++i1;
                double d12 = f1;
                double d14 = f;
                d12 += d13 * 0.2D;
                d12 = d12 * 8.5D / 8.0D;
                double d5 = 8.5D + d12 * 4.0D;

                for (int j2 = 0; j2 < 33; ++j2) {
                    double d6 = (j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

                    if (d6 < 0.0D) {
                        d6 *= 4.0D;
                    }

                    double d7 = this.noiseData2[l] / 512.0D;
                    double d8 = this.noiseData3[l] / 512.0D;
                    double d9 = (this.noiseData1[l] / 10.0D + 1.0D) / 2.0D;
                    double d10 = MathHelper.denormalizeClamp(d7, d8, d9) - d6;

                    if (j2 > 29) {
                        double d11 = (j2 - 29) / 3.0F;
                        d10 = d10 * (1.0D - d11) + -10.0D * d11;
                    }

                    this.field_147434_q[l] = d10;
                    ++l;
                }
            }
        }
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    @Override
    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    @Override
    public void populate(IChunkProvider chunkProvider, int chunkX, int chunkZ) {
        BlockFalling.fallInstantly = true;
        int posX = chunkX * 16;
        int posZ = chunkZ * 16;
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(posX + 16, posZ + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        long i1 = this.rand.nextLong() / 2L * 2L + 1L;
        long j1 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed(chunkX * i1 + chunkZ * j1 ^ this.worldObj.getSeed());
        boolean generateStructures = false;

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(chunkProvider, worldObj, rand, chunkX, chunkZ, generateStructures));

        biomegenbase.decorate(this.worldObj, this.rand, posX, posZ);
        SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, posX + 8, posZ + 8, 16, 16, this.rand);

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(chunkProvider, worldObj, rand, chunkX, chunkZ, generateStructures));

        BlockFalling.fallInstantly = false;
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go. If passed false, save up to two chunks. Return true if all chunks have been saved.
     */
    @Override
    public boolean saveChunks(boolean saveAll, IProgressUpdate progressUpdate) {
        return true;
    }

    /**
     * Save extra data not associated with any Chunk. Not saved during autosave, only during world unload. Currently unimplemented.
     */
    @Override
    public void saveExtraData() {
    }

    /**
     * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
     */
    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    @Override
    public boolean canSave() {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    @Override
    public String makeString() {
        return "RandomLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    @Override
    public List getPossibleCreatures(EnumCreatureType creatureType, int posX, int posY, int posZ) {
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(posX, posZ);
        return biomegenbase.getSpawnableList(creatureType);
    }

    @Override
    public ChunkPosition func_147416_a(World world, String p_147416_2_, int chunkX, int chunkY, int chunkZ) {
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public void recreateStructures(int chunkX, int chunkZ) { }

}
