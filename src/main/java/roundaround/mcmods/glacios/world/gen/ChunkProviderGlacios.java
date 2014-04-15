package roundaround.mcmods.glacios.world.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
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
import roundaround.mcmods.glacios.world.gen.structure.MapGenVolcano;
import cpw.mods.fml.common.eventhandler.Event.Result;

public class ChunkProviderGlacios implements IChunkProvider {
    private Random rand;
    private World worldObj;
    private WorldType worldType;
    
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    private NoiseGeneratorPerlin noiseGen4;
    public NoiseGeneratorOctaves noiseGen5;
    public NoiseGeneratorOctaves noiseGen6;
    public NoiseGeneratorOctaves mobSpawnerNoise;
    
    private MapGenVolcano volcanoGen;
    
    private final double[] positionalField;
    private final float[] parabolicField;
    private double[] stoneNoise = new double[256];

    private BiomeGenBase[] biomesForGeneration;
    private double[] noiseData1;
    private double[] noiseData2;
    private double[] noiseData3;
    private double[] noiseData4;

    public ChunkProviderGlacios(World world, long seed) {
        this.rand = new Random(seed);
        this.worldObj = world;
        this.worldType = world.getWorldInfo().getTerrainType();
        
        this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGen4 = new NoiseGeneratorPerlin(this.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
        
        this.volcanoGen = new MapGenVolcano(GlaciosBlocks.ash, Blocks.lava, this.worldObj);
        
        this.positionalField = new double[825];
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

    public void generateChunk(int p_147424_1_, int p_147424_2_, Block[] blockArray) {
        byte b0 = 63;
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, p_147424_1_ * 4 - 2, p_147424_2_ * 4 - 2, 10, 10);
        this.generateNoise(p_147424_1_ * 4, 0, p_147424_2_ * 4);

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
                    double d1 = this.positionalField[k1 + k2];
                    double d2 = this.positionalField[l1 + k2];
                    double d3 = this.positionalField[i2 + k2];
                    double d4 = this.positionalField[j2 + k2];
                    double d5 = (this.positionalField[k1 + k2 + 1] - d1) * d0;
                    double d6 = (this.positionalField[l1 + k2 + 1] - d2) * d0;
                    double d7 = (this.positionalField[i2 + k2 + 1] - d3) * d0;
                    double d8 = (this.positionalField[j2 + k2 + 1] - d4) * d0;

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
                                    blockArray[j3 += short1] = GlaciosBlocks.slate;
                                } else if (k2 * 8 + l2 < b0) {
                                    blockArray[j3 += short1] = GlaciosBlocks.crystalWater;
                                } else {
                                    blockArray[j3 += short1] = null;
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

    public void replaceBlocksForBiome(int chunkX, int chunkZ, Block[] blockArray, byte[] metaArray, BiomeGenBase[] biomeArray) {
        ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, chunkX, chunkZ, blockArray, biomeArray);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY)
            return;

        double d0 = 0.03125D;
        this.stoneNoise = this.noiseGen4.func_151599_a(this.stoneNoise, chunkX * 16, chunkZ * 16, 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                BiomeGenBase biomegenbase = biomeArray[z + x * 16];
                biomegenbase.genTerrainBlocks(this.worldObj, this.rand, blockArray, metaArray, chunkX * 16 + x, chunkZ * 16 + z, this.stoneNoise[z + x * 16]);
            }
        }
    }

    @Override
    public Chunk loadChunk(int chunkX, int chunkZ) {
        return this.provideChunk(chunkX, chunkZ);
    }

    @Override
    public Chunk provideChunk(int chunkX, int chunkZ) {
        this.rand.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        Block[] blockArray = new Block[65536];
        byte[] metaArray = new byte[65536];
        this.generateChunk(chunkX, chunkZ, blockArray);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);
        this.replaceBlocksForBiome(chunkX, chunkZ, blockArray, metaArray, this.biomesForGeneration);
        
        this.volcanoGen.generateInChunk(blockArray, metaArray, this.biomesForGeneration, chunkX, chunkZ);

        Chunk chunk = new Chunk(this.worldObj, blockArray, metaArray, chunkX, chunkZ);
        byte[] biomeArray = chunk.getBiomeArray();

        for (int k = 0; k < biomeArray.length; ++k) {
            biomeArray[k] = (byte) this.biomesForGeneration[k].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    private void generateNoise(int offsetX, int offsetY, int offsetZ) {
        this.noiseData4 = this.noiseGen6.generateNoiseOctaves(this.noiseData4, offsetX, offsetZ, 5, 5, 200.0D, 200.0D, 0.5D);
        this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, offsetX, offsetY, offsetZ, 5, 33, 5, 8.55515D, 4.277575D, 8.55515D);
        this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, offsetX, offsetY, offsetZ, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, offsetX, offsetY, offsetZ, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        int noiseIndexPrimary = 0;
        int noiseIndexSecondary = 0;

        for (int x = 0; x < 5; ++x) {
            for (int z = 0; z < 5; ++z) {
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;
                byte b0 = 2;
                BiomeGenBase biomegenbase = this.biomesForGeneration[x + 2 + (z + 2) * 10];

                for (int blendX = -b0; blendX <= b0; ++blendX) {
                    for (int blendZ = -b0; blendZ <= b0; ++blendZ) {
                        BiomeGenBase biomegenbase1 = this.biomesForGeneration[x + blendX + 2 + (z + blendZ + 2) * 10];
                        float height = biomegenbase1.rootHeight;
                        float variation = biomegenbase1.heightVariation;

                        if (this.worldType == WorldType.AMPLIFIED && height > 0.0F) {
                            height = 1.0F + height * 2.0F;
                            variation = 1.0F + variation * 4.0F;
                        }

                        float f5 = this.parabolicField[blendX + 2 + (blendZ + 2) * 5] / (height + 2.0F);

                        if (biomegenbase1.rootHeight > biomegenbase.rootHeight) {
                            f5 /= 2.0F;
                        }

                        f += variation * f5;
                        f1 += height * f5;
                        f2 += f5;
                    }
                }

                f /= f2;
                f1 /= f2;
                f = f * 0.9F + 0.1F;
                f1 = (f1 * 4.0F - 1.0F) / 8.0F;
                double d13 = this.noiseData4[noiseIndexSecondary] / 8000.0D;

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

                ++noiseIndexSecondary;
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

                    double d7 = this.noiseData2[noiseIndexPrimary] / 512.0D;
                    double d8 = this.noiseData3[noiseIndexPrimary] / 512.0D;
                    double d9 = (this.noiseData1[noiseIndexPrimary] / 10.0D + 1.0D) / 2.0D;
                    double d10 = MathHelper.denormalizeClamp(d7, d8, d9) - d6;

                    if (j2 > 29) {
                        double d11 = (j2 - 29) / 3.0F;
                        d10 = d10 * (1.0D - d11) + -10.0D * d11;
                    }

                    this.positionalField[noiseIndexPrimary] = d10;
                    ++noiseIndexPrimary;
                }
            }
        }
    }

    @Override
    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    @Override
    public void populate(IChunkProvider chunkProvider, int chunkX, int chunkZ) {
        BlockFalling.fallInstantly = true;
        boolean generateStructures = false;
        this.rand.setSeed(this.worldObj.getSeed());
        
        int posX = chunkX * 16;
        int posZ = chunkZ * 16;
        
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(posX + 16, posZ + 16);
        
        long randX = this.rand.nextLong() / 2L * 2L + 1L;
        long randZ = this.rand.nextLong() / 2L * 2L + 1L;
        
        this.rand.setSeed(chunkX * randX + chunkZ * randZ ^ this.worldObj.getSeed());
        
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(chunkProvider, worldObj, rand, chunkX, chunkZ, generateStructures));

        biomegenbase.decorate(this.worldObj, this.rand, posX, posZ);
        SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, posX + 8, posZ + 8, 16, 16, this.rand);

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(chunkProvider, worldObj, rand, chunkX, chunkZ, generateStructures));

        BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean saveChunks(boolean saveAll, IProgressUpdate progressUpdate) {
        return true;
    }

    @Override
    public void saveExtraData() {
    }

    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String makeString() {
        return "RandomLevelSource";
    }

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
