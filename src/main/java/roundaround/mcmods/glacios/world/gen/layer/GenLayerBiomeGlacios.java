package roundaround.mcmods.glacios.world.gen.layer;

import roundaround.mcmods.glacios.GlaciosBiomes;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiome;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomeGlacios extends GenLayer {

    protected BiomeGenBase[] allowedBiomes;

    public GenLayerBiomeGlacios(long seed, GenLayer genlayer) {
        super(seed);
        this.parent = genlayer;
    }

    public GenLayerBiomeGlacios(long seed, WorldType worldType) {
        super(seed);
        this.allowedBiomes = GlaciosBiomes.getBiomeList();
    }

    @Override
    public int[] getInts(int x, int z, int width, int depth) {
        int[] dest = IntCache.getIntCache(width * depth);

        for (int dz = 0; dz < depth; dz++) {
            for (int dx = 0; dx < width; dx++) {
                this.initChunkSeed(dx + x, dz + z);
                dest[(dx + dz * width)] = this.allowedBiomes[nextInt(this.allowedBiomes.length)].biomeID;
            }
        }
        return dest;
    }

}
