package roundaround.mcmods.glacios.world.gen.feature;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import roundaround.mcmods.glacios.world.decoration.IGlaciosDecoration;
import roundaround.mcmods.glacios.world.gen.WorldGeneratorGlacios;

public class WorldGenGlaciosTallGrass extends WorldGeneratorGlacios {

    public WorldGenGlaciosTallGrass() {
    }

    public WorldGenGlaciosTallGrass(boolean doBlockNotify) {
        super(doBlockNotify);
    }

    @Override
    public void doGeneration(World world, Random random, Field worldGeneratorField, WorldGenerator worldGenerator, BiomeGenBase biome,
            IGlaciosDecoration bopDecoration, int randX, int randZ) throws Exception {
    }

}
