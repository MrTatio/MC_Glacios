package roundaround.mcmods.glacios.world.biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import roundaround.mcmods.glacios.GlaciosConfig;
import roundaround.mcmods.glacios.world.gen.layer.GenLayerGlacios;

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

    public WorldChunkManagerGlacios(World world) {
        this(world.getSeed(), world.getWorldInfo().getTerrainType());
    }

    public WorldChunkManagerGlacios(long seed, WorldType worldType) {
        this();
        GenLayer[] agenlayer = GenLayerGlacios.makeTheWorld(seed, worldType);
        genBiomes = agenlayer[0];
        biomeIndexLayer = agenlayer[1];
    }

    @Override
    public List<BiomeGenBase> getBiomesToSpawnIn() {
        return biomesToSpawnIn;
    }

    @Override
    public BiomeGenBase getBiomeGenAt(int par1, int par2) {
        return biomeCache.getBiomeGenAt(par1, par2);
    }

    @Override
    public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5) {
        IntCache.resetIntCache();

        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5) {
            par1ArrayOfFloat = new float[par4 * par5];
        }

        int[] var6 = biomeIndexLayer.getInts(par2, par3, par4, par5);

        for (int var7 = 0; var7 < par4 * par5; ++var7) {
            float var8 = BiomeGenBase.getBiomeGenArray()[var6[var7]].getIntRainfall() / 65536.0F;

            if (var8 > 1.0F) {
                var8 = 1.0F;
            }

            par1ArrayOfFloat[var7] = var8;
        }

        return par1ArrayOfFloat;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getTemperatureAtHeight(float par1, int par2) {
        return par1;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random) {
        IntCache.resetIntCache();
        int var6 = par1 - par3 >> 2;
        int var7 = par2 - par3 >> 2;
        int var8 = par1 + par3 >> 2;
        int var9 = par2 + par3 >> 2;
        int var10 = var8 - var6 + 1;
        int var11 = var9 - var7 + 1;
        int[] var12 = genBiomes.getInts(var6, var7, var10, var11);
        ChunkPosition var13 = null;
        int var14 = 0;

        for (int var15 = 0; var15 < var10 * var11; ++var15) {
            int var16 = var6 + var15 % var10 << 2;
            int var17 = var7 + var15 / var10 << 2;
            BiomeGenBase var18 = BiomeGenBase.getBiomeGenArray()[var12[var15]];

            if (par4List.contains(var18) && (var13 == null || par5Random.nextInt(var14 + 1) == 0)) {
                var13 = new ChunkPosition(var16, 0, var17);
                ++var14;
            }
        }

        return var13;
    }

    @Override
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomeArr, int one, int two, int three, int four) {
        IntCache.resetIntCache();

        if (biomeArr == null || biomeArr.length < three * four) {
            biomeArr = new BiomeGenBase[three * four];
        }

        int[] ints = genBiomes.getInts(one, two, three, four);

        for (int i = 0; i < three * four; ++i) {
            biomeArr[i] = BiomeGenBase.getBiomeGenArray()[ints[i]];
        }

        return biomeArr;
    }

    @Override
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] biomeArr, int one, int two, int three, int four, boolean cache) {
        IntCache.resetIntCache();

        if (biomeArr == null || biomeArr.length < three * four) {
            biomeArr = new BiomeGenBase[three * four];
        }

        if (cache && three == 16 && four == 16 && (one & 15) == 0 && (two & 15) == 0) {
            BiomeGenBase[] var9 = biomeCache.getCachedBiomes(one, two);
            System.arraycopy(var9, 0, biomeArr, 0, three * four);
            return biomeArr;
        } else {
            int[] ints = biomeIndexLayer.getInts(one, two, three, four);

            for (int i = 0; i < three * four; ++i) {
                biomeArr[i] = BiomeGenBase.getBiomeGenArray()[ints[i]];
            }

            return biomeArr;
        }
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean areBiomesViable(int x, int y, int z, List biomes) {
        IntCache.resetIntCache();
        int one = x - z >> 2;
        int two = y - z >> 2;
        int five = x + z >> 2;
        int six = y + z >> 2;
        int three = five - one + 1;
        int four = six - two + 1;
        int[] ints = genBiomes.getInts(one, two, three, four);

        for (int i = 0; i < three * four; ++i) {
            BiomeGenBase biome = BiomeGenBase.getBiomeGenArray()[ints[i]];

            if (!biomes.contains(biome))
                return false;
        }

        return true;
    }
}
