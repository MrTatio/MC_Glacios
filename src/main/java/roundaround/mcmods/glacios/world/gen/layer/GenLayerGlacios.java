package roundaround.mcmods.glacios.world.gen.layer;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenLayerGlacios extends GenLayer {

    public GenLayerGlacios(long seed) {
        super(seed);
    }

    public static GenLayer[] makeTheWorld(long seed, WorldType worldtype) {

        GenLayer biomes = new GenLayerBiomeGlacios(1L);

        // more GenLayerZoom = bigger biomes
        biomes = new GenLayerZoom(1000L, biomes);
        biomes = new GenLayerZoom(1001L, biomes);
        biomes = new GenLayerZoom(1002L, biomes);
        biomes = new GenLayerZoom(1003L, biomes);
        biomes = new GenLayerZoom(1004L, biomes);
        // biomes = new GenLayerZoom(1005L, biomes);

        GenLayer genlayervoronoizoom = new GenLayerVoronoiZoom(10L, biomes);

        biomes.initWorldGenSeed(seed);
        genlayervoronoizoom.initWorldGenSeed(seed);

        return new GenLayer[] { biomes, genlayervoronoizoom };
    }

}
