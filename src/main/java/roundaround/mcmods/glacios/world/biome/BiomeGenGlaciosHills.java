package roundaround.mcmods.glacios.world.biome;

import java.util.Random;

import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import roundaround.mcmods.glacios.block.BlockSaplingGlacios;
import roundaround.mcmods.glacios.world.gen.feature.WorldGenHomeTree;
import roundaround.mcmods.glacios.world.gen.feature.WorldGenSoulTree;


// Very similar in terrain formation as overworld extreme hills.

public class BiomeGenGlaciosHills extends BiomeGenGlacios {

    public BiomeGenGlaciosHills(int biomeId) {
        this(biomeId, true);
    }

    public BiomeGenGlaciosHills(int biomeId, boolean register) {
        super(biomeId, register);
        this.theGlaciosBiomeDecorator.treesPerChunk = 2;
    }

    @Override
    public WorldGenAbstractTree func_150567_a(Random rand) {
        return rand.nextInt(80) == 0 ? new WorldGenHomeTree(false, BlockSaplingGlacios.soul).setThisScale(2.0D, 2.0D, 1.2D)
            : new WorldGenSoulTree(false);
    }

}
