package roundaround.mcmods.glacios.world.gen.feature;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenGlaciosTaiga extends WorldGenAbstractTree {

    public WorldGenGlaciosTaiga(boolean doBlockNotify) {
        super(doBlockNotify);
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        return false;
    }

}
