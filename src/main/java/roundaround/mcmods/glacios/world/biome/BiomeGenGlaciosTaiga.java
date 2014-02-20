package roundaround.mcmods.glacios.world.biome;

import java.util.Random;

import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import roundaround.mcmods.glacios.block.BlockSaplingGlacios;
import roundaround.mcmods.glacios.world.gen.feature.WorldGenHomeTree;
import roundaround.mcmods.glacios.world.gen.feature.WorldGenSoulTree;
import roundaround.mcmods.glacios.world.gen.feature.WorldGenTaigaGiant;

// Forested biome heavily packed with tall pine trees.

public class BiomeGenGlaciosTaiga extends BiomeGenGlacios {

    public BiomeGenGlaciosTaiga(int biomeId) {
        this(biomeId, true);
    }

    public BiomeGenGlaciosTaiga(int biomeId, boolean register) {
        super(biomeId, register);
        this.theGlaciosBiomeDecorator.treesPerChunk = 10;
    }

    @Override
    public WorldGenAbstractTree func_150567_a(Random rand) {
        return rand.nextInt(1000) == 0 ? new WorldGenHomeTree(false, BlockSaplingGlacios.soul)
            : rand.nextInt(80) == 0 ? new WorldGenSoulTree(false, true)
            : rand.nextInt(40) == 0 ? new WorldGenSoulTree(false)
            : rand.nextInt(12) == 0 ? new WorldGenTaigaGiant(false, true, 20, 35)
            : new WorldGenTaigaGiant(false);
    }

}
