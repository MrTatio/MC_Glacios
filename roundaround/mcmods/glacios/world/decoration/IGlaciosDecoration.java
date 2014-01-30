package roundaround.mcmods.glacios.world.decoration;

import java.util.HashMap;

import net.minecraft.world.gen.feature.WorldGenerator;

public interface IGlaciosDecoration {
    public GlaciosWorldFeatures getWorldFeatures();
    
    //public WorldGenBOPFlora getRandomWorldGenForBOPFlowers(Random random);

    public HashMap<WorldGenerator, Double> getWeightedWorldGenForGrass();

    //public HashMap<WorldGenBOPFlora, Integer> getWeightedWorldGenForBOPFlowers();
}
