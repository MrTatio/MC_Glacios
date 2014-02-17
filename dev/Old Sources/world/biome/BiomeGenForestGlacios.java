package glacios.world.biome;

import glacios.world.gen.feature.WorldGenGlaciosTrees;

import java.util.Random;

import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenForestGlacios extends BiomeGenGlacios {

    public BiomeGenForestGlacios(int par1) {
        super(par1);
        theBiomeDecorator.treesPerChunk = 3;
    }

    /*
     * Gets a WorldGen appropriate for this biome.
     */
    @Override
    public WorldGenerator getRandomWorldGenForTrees(Random par1Random) {
        return new WorldGenGlaciosTrees();
    }

}
