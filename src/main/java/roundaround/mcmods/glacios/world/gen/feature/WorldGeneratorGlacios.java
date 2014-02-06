package roundaround.mcmods.glacios.world.gen.feature;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class WorldGeneratorGlacios extends WorldGenerator implements IWorldGeneratorGlacios {

    public WorldGeneratorGlacios() {
        this(false);
    }

    public WorldGeneratorGlacios(boolean doBlockNotify) {
        super(doBlockNotify);
    }

    @Override
    public abstract boolean generate(World world, Random rand, int x, int y, int z);
    
    @Override
    public abstract void doGeneration(World world, Random random, Field worldGeneratorField, WorldGenerator worldGenerator, BiomeGenBase biome, int x, int z) throws Exception;

}
