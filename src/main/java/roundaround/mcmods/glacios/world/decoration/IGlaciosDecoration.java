package roundaround.mcmods.glacios.world.decoration;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.world.gen.feature.WorldGenerator;
import roundaround.mcmods.glacios.world.gen.feature.WorldGenFloraGlacios;

public interface IGlaciosDecoration {
    public WorldGenFloraGlacios getRandomWorldGenForFlowers(Random random);

    public HashMap<WorldGenerator, Double> getWeightedWorldGenForGrass();

    public HashMap<WorldGenFloraGlacios, Integer> getWeightedWorldGenForFlowers();
}
