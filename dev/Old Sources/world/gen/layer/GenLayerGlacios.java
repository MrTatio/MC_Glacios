package glacios.world.gen.layer;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenLayerGlacios extends GenLayer {

    public GenLayerGlacios(long par1) {
        super(par1);
    }

    /*
     * The first array item is a linked list of the biomes, the second is the zoom function, the third is the same as
     * the first.
     */
    public static GenLayer[] initializeAllBiomeGenerators(long seed, WorldType worldType) {

        byte biomeSize = (worldType == WorldType.LARGE_BIOMES) ? (byte) 6 : 4;

        GenLayerBiomeGlacios genlayerbiomeglacios = new GenLayerBiomeGlacios(200L);
        GenLayerFuzzyZoom genlayerfuzzyzoom = new GenLayerFuzzyZoom(2000L, genlayerbiomeglacios);
        GenLayerZoom genlayerzoom = new GenLayerZoom(2001L, genlayerfuzzyzoom);
        genlayerzoom = new GenLayerZoom(2002L, genlayerzoom);
        genlayerzoom = new GenLayerZoom(2003L, genlayerzoom);
        GenLayer genlayermagnify = GenLayerZoom.func_75915_a(1000L, genlayerzoom, biomeSize + 2);
        GenLayerRiverGlacios genlayerriverglacios = new GenLayerRiverGlacios(1L, genlayermagnify);
        GenLayerSmooth genlayersmooth = new GenLayerSmooth(1000L, genlayerriverglacios);
        GenLayer genlayermagnify2 = GenLayerZoom.func_75915_a(1000L, genlayerzoom, 0);
        GenLayerBiomeGlacios genlayerbiomeglacios2 = new GenLayerBiomeGlacios(200L, genlayermagnify2);
        genlayermagnify2 = GenLayerZoom.func_75915_a(1000L, genlayerbiomeglacios2, 2);
        Object genlayer = new GenLayerHillsGlacios(1000L, genlayermagnify2);

        for (int i = 0; i < biomeSize; i++)
            genlayer = new GenLayerZoom(1000L + i, (GenLayer) genlayer);

        GenLayerSmooth genlayersmooth2 = new GenLayerSmooth(1000L, (GenLayer) genlayer);
        GenLayerRiverMix genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth2, genlayersmooth);
        GenLayerVoronoiZoom genlayervoronoizoom = new GenLayerVoronoiZoom(10L, genlayerrivermix);
        genlayerrivermix.initWorldGenSeed(seed);
        genlayervoronoizoom.initWorldGenSeed(seed);

        return new GenLayer[] { genlayerrivermix, genlayervoronoizoom, genlayerrivermix };

        //
        //
        //

        // GenLayerIsland genlayerisland = new GenLayerIsland(1L);
        // GenLayerFuzzyZoom genlayerfuzzyzoom = new GenLayerFuzzyZoom(2000L, genlayerisland);
        // GenLayerAddIsland genlayeraddisland = new GenLayerAddIsland(1L, genlayerfuzzyzoom);
        // GenLayerZoom genlayerzoom = new GenLayerZoom(2001L, genlayeraddisland);
        // genlayeraddisland = new GenLayerAddIsland(2L, genlayerzoom);
        // GenLayerAddSnow genlayeraddsnow = new GenLayerAddSnow(2L, genlayeraddisland);
        // genlayerzoom = new GenLayerZoom(2002L, genlayeraddsnow);
        // genlayeraddisland = new GenLayerAddIsland(3L, genlayerzoom);
        // genlayerzoom = new GenLayerZoom(2003L, genlayeraddisland);
        // genlayeraddisland = new GenLayerAddIsland(4L, genlayerzoom);
        // GenLayerAddMushroomIsland genlayeraddmushroomisland = new GenLayerAddMushroomIsland(5L, genlayeraddisland);
        // byte b0 = 4;
        //
        // if (worldType == WorldType.LARGE_BIOMES) {
        // b0 = 6;
        // }
        // b0 = getModdedBiomeSize(worldType, b0);
        //
        // GenLayer genlayer = GenLayerZoom.func_75915_a(1000L, genlayeraddmushroomisland, 0);
        // GenLayerRiverInit genlayerriverinit = new GenLayerRiverInit(100L, genlayer);
        // genlayer = GenLayerZoom.func_75915_a(1000L, genlayerriverinit, b0 + 2);
        // GenLayerRiverGlacios genlayerriver = new GenLayerRiverGlacios(1L, genlayer);
        // GenLayerSmooth genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
        // GenLayer genlayer1 = GenLayerZoom.func_75915_a(1000L, genlayeraddmushroomisland, 0);
        // GenLayerBiomeGlacios genlayerbiome = new GenLayerBiomeGlacios(200L, genlayer1);
        // genlayer1 = GenLayerZoom.func_75915_a(1000L, genlayerbiome, 2);
        // Object object = new GenLayerHillsGlacios(1000L, genlayer1);
        //
        // for (int j = 0; j < b0; ++j) {
        // object = new GenLayerZoom((long) (1000 + j), (GenLayer) object);
        //
        // if (j == 0) {
        // object = new GenLayerAddIsland(3L, (GenLayer) object);
        // }
        //
        // if (j == 1) {
        // object = new GenLayerShore(1000L, (GenLayer) object);
        // }
        //
        // if (j == 1) {
        // object = new GenLayerSwampRivers(1000L, (GenLayer) object);
        // }
        // }
        //
        // GenLayerSmooth genlayersmooth1 = new GenLayerSmooth(1000L, (GenLayer) object);
        // GenLayerRiverMix genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);
        // GenLayerVoronoiZoom genlayervoronoizoom = new GenLayerVoronoiZoom(10L, genlayerrivermix);
        // genlayerrivermix.initWorldGenSeed(seed);
        // genlayervoronoizoom.initWorldGenSeed(seed);
        // return new GenLayer[] { genlayerrivermix, genlayervoronoizoom, genlayerrivermix };
    }

}
