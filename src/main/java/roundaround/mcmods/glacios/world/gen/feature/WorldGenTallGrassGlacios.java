package roundaround.mcmods.glacios.world.gen.feature;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import roundaround.mcmods.glacios.GlaciosConfig;

public class WorldGenTallGrassGlacios extends WorldGeneratorGlacios {

    private Block genBlock;

    public WorldGenTallGrassGlacios(Block block) {
        super();
        this.genBlock = block;
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        Block block;

        do {
            block = world.getBlock(x, y, z);
            if (!(block.isLeaves(world, x, y, z) || block.isAir(world, x, y, z))) {
                break;
            }
            --y;
        } while (y > 0);

        for (int l = 0; l < 128; ++l) {
            int posX = x + rand.nextInt(8) - rand.nextInt(8);
            int posY = y + rand.nextInt(4) - rand.nextInt(4);
            int posZ = z + rand.nextInt(8) - rand.nextInt(8);

            if (world.isAirBlock(posX, posY, posZ) && this.genBlock.canBlockStay(world, posX, posY, posZ)) {
                world.setBlock(posX, posY, posZ, this.genBlock);
            }
        }

        return true;
    }

    @Override
    public void doGeneration(World world, Random random, Field worldGeneratorField, WorldGenerator worldGenerator, BiomeGenBase biome,
            int x, int z) throws Exception {

        for (int i = 0; i < worldGeneratorField.getInt(GlaciosConfig.instance); i++) {
            int randX = x + random.nextInt(16) + 8;
            int randZ = z + random.nextInt(16) + 8;
            int randY = random.nextInt(world.getHeightValue(randX, randZ) * 2);

            worldGenerator.generate(world, random, randX, randY, randZ);
        }
    }

}
