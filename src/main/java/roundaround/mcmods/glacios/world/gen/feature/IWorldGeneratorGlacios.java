package roundaround.mcmods.glacios.world.gen.feature;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public interface IWorldGeneratorGlacios {
    public void doGeneration(World world, Random random, Field worldGeneratorField, BiomeGenBase biome, int randX, int randZ) throws Exception;
}
