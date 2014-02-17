package glacios.world.biome;

import glacios.world.gen.layer.GenLayerGlacios;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class WorldChunkManagerGlacios extends WorldChunkManager {

    private GenLayer genBiomes;

    /* A GenLayer containing the indices into BiomeGenBase.biomeList[] */
    private GenLayer biomeIndexLayer;

    /* The BiomeCache object for this world. */
    private BiomeCache biomeCache;

    /* A list of biomes that the player can spawn in. */
    private List<BiomeGenBase> biomesToSpawnIn;

    protected WorldChunkManagerGlacios() {
        biomeCache = new BiomeCache(this);
        biomesToSpawnIn = new ArrayList<BiomeGenBase>();
    }

    public WorldChunkManagerGlacios(long seed, WorldType world) {
        this();
        GenLayer[] genLayerArr = GenLayerGlacios.initializeAllBiomeGenerators(seed, world);
        genLayerArr = getModdedBiomeGenerators(world, seed, genLayerArr);
        genBiomes = genLayerArr[0];
        biomeIndexLayer = genLayerArr[1];
    }

    public WorldChunkManagerGlacios(World world) {
        this(world.getSeed(), world.getWorldInfo().getTerrainType());
    }

    @Override
    public List<BiomeGenBase> getBiomesToSpawnIn() {
        return biomesToSpawnIn;
    }

    /*
     * Returns the BiomeGenBase related to the x, z position on the world.
     */
    @Override
    public BiomeGenBase getBiomeGenAt(int x, int z) {
        return biomeCache.getBiomeGenAt(x, z);
    }

    /*
     * Returns a list of rainfall values for the specified blocks. Args: listToReuse, x, z, width, length.
     */
    @Override
    public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5) {
        IntCache.resetIntCache();

        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5) {
            par1ArrayOfFloat = new float[par4 * par5];
        }

        int[] aint = biomeIndexLayer.getInts(par2, par3, par4, par5);

        for (int i1 = 0; i1 < par4 * par5; ++i1) {
            float f = BiomeGenBase.biomeList[aint[i1]].getIntRainfall() / 65536.0F;

            if (f > 1.0F) {
                f = 1.0F;
            }

            par1ArrayOfFloat[i1] = f;
        }

        return par1ArrayOfFloat;
    }

    /*
     * Returns a list of temperatures to use for the specified blocks. Args: listToReuse, x, y, width, length
     */
    @Override
    public float[] getTemperatures(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5) {
        IntCache.resetIntCache();

        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5) {
            par1ArrayOfFloat = new float[par4 * par5];
        }

        int[] aint = biomeIndexLayer.getInts(par2, par3, par4, par5);

        for (int i1 = 0; i1 < par4 * par5; ++i1) {
            float f = BiomeGenBase.biomeList[aint[i1]].getIntTemperature() / 65536.0F;

            if (f > 1.0F) {
                f = 1.0F;
            }

            par1ArrayOfFloat[i1] = f;
        }

        return par1ArrayOfFloat;
    }

    /*
     * Returns an array of biomes for the location input.
     */
    @Override
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, int par2, int par3, int par4, int par5) {
        return getBiomeGenAt(biomes, par2, par3, par4, par5, true);
    }

    /*
     * Return a list of biomes for the specified blocks. Args: listToReuse, x, y, width, length, cacheFlag (if false,
     * don't check biomeCache to avoid infinite loop in BiomeCacheBlock)
     */
    @Override
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5, boolean par6) {
        IntCache.resetIntCache();

        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5) {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
        }

        if (par6 && par4 == 16 && par5 == 16 && (par2 & 15) == 0 && (par3 & 15) == 0) {
            BiomeGenBase[] abiomegenbase1 = biomeCache.getCachedBiomes(par2, par3);
            System.arraycopy(abiomegenbase1, 0, par1ArrayOfBiomeGenBase, 0, par4 * par5);
            return par1ArrayOfBiomeGenBase;
        } else {
            int[] aint = biomeIndexLayer.getInts(par2, par3, par4, par5);

            for (int i1 = 0; i1 < par4 * par5; ++i1) {
                par1ArrayOfBiomeGenBase[i1] = BiomeGenBase.biomeList[aint[i1]];
            }

            return par1ArrayOfBiomeGenBase;
        }
    }

    /*
     * Checks given Chunk's Biomes against List of allowed ones
     */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean areBiomesViable(int par1, int par2, int par3, List par4List) {
        IntCache.resetIntCache();
        int l = par1 - par3 >> 2;
        int i1 = par2 - par3 >> 2;
        int j1 = par1 + par3 >> 2;
        int k1 = par2 + par3 >> 2;
        int l1 = j1 - l + 1;
        int i2 = k1 - i1 + 1;
        int[] aint = genBiomes.getInts(l, i1, l1, i2);

        for (int j2 = 0; j2 < l1 * i2; ++j2) {
            BiomeGenBase biomegenbase = BiomeGenBase.biomeList[aint[j2]];

            if (!par4List.contains(biomegenbase)) {
                return false;
            }
        }

        return true;
    }

    /*
     * Finds a valid position within a range, that is in one of the listed biomes. Searches {par1,par2} +-par3 blocks.
     * Strongly favors positive y positions.
     */
    @SuppressWarnings("rawtypes")
    @Override
    public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random) {
        IntCache.resetIntCache();
        int l = par1 - par3 >> 2;
        int i1 = par2 - par3 >> 2;
        int j1 = par1 + par3 >> 2;
        int k1 = par2 + par3 >> 2;
        int l1 = j1 - l + 1;
        int i2 = k1 - i1 + 1;
        int[] aint = genBiomes.getInts(l, i1, l1, i2);
        ChunkPosition chunkposition = null;
        int j2 = 0;

        for (int k2 = 0; k2 < l1 * i2; ++k2) {
            int l2 = l + k2 % l1 << 2;
            int i3 = i1 + k2 / l1 << 2;
            BiomeGenBase biomegenbase = BiomeGenBase.biomeList[aint[k2]];

            if (par4List.contains(biomegenbase) && (chunkposition == null || par5Random.nextInt(j2 + 1) == 0)) {
                chunkposition = new ChunkPosition(l2, 0, i3);
                ++j2;
            }
        }

        return chunkposition;
    }

}
