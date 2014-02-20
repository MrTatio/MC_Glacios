package roundaround.mcmods.glacios.world.gen.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import roundaround.mcmods.glacios.GlaciosBiomes;
import roundaround.mcmods.glacios.GlaciosBiomes.BiomeMapping;

public class GenLayerBiomeGlacios extends GenLayer {

    protected BiomeMapping[] allowedBiomes;

    public GenLayerBiomeGlacios(long seed, GenLayer genlayer) {
        this(seed);
        this.parent = genlayer;
    }

    public GenLayerBiomeGlacios(long seed) {
        super(seed);
        this.allowedBiomes = GlaciosBiomes.getBiomeList();
    }

    @Override
    public int[] getInts(int x, int z, int width, int depth) {
        int[] dest = IntCache.getIntCache(width * depth);
        
        for (int dz = 0; dz < depth; dz++) {
            for (int dx = 0; dx < width; dx++) {
                this.initChunkSeed(dx + x, dz + z);
                
                boolean found = false;
                while (!found) {
                    BiomeMapping mapping = this.allowedBiomes[nextInt(this.allowedBiomes.length)];
                    if (nextInt(mapping.rarity) == 0) {
                        dest[(dx + dz * width)] = mapping.biomeId;
                        found = true;
                    }
                }
            }
        }
        return dest;
    }

}
