package roundaround.mcmods.glacios.world.gen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class WorldGeneratorGlacios extends WorldGenerator implements IWorldGeneratorGlacios {

    public WorldGeneratorGlacios() {
    }

    public WorldGeneratorGlacios(boolean doBlockNotify) {
        super(doBlockNotify);
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        return false;
    }

}
