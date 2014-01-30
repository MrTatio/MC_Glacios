package roundaround.mcmods.glacios.world.gen.feature;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import roundaround.mcmods.glacios.GlaciosBlocks;
import roundaround.mcmods.glacios.world.decoration.IGlaciosDecoration;
import roundaround.mcmods.glacios.world.gen.WorldGeneratorGlacios;

public class WorldGenGlaciosFlora extends WorldGeneratorGlacios {

    public Block flora;
    public int floraMeta;
    private int groupCount = 64;

    public WorldGenGlaciosFlora() {
    }

    public WorldGenGlaciosFlora(Block flora, int floraMeta) {
        this.flora = flora;
        this.floraMeta = floraMeta;
    }

    public WorldGenGlaciosFlora(Block flora, int floraMeta, int groupCount) {
        this.flora = flora;
        this.floraMeta = floraMeta;
        this.groupCount = groupCount;
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        for (int l = 0; l < groupCount; ++l) {
            int i1 = x + random.nextInt(8) - random.nextInt(8);
            int j1 = y + random.nextInt(4) - random.nextInt(4);
            int k1 = z + random.nextInt(8) - random.nextInt(8);

            // TODO: isAirBlock()
            if (world.func_147437_c(i1, j1, k1)
                    && (!world.provider.hasNoSky || j1 < 255)
                    && (flora == Blocks.web ? world.func_147439_a(i1, j1 + 1, k1) == GlaciosBlocks.soulLeaves
                            || world.func_147439_a(i1, j1 - 1, k1) == Blocks.grass : this.flora.func_149705_a(world, i1, j1, k1, 0,
                            new ItemStack(flora, 1, floraMeta)))) {
                // TODO: setBlock()
                world.func_147465_d(i1, j1, k1, this.flora, this.floraMeta, 2);
            }
        }

        return true;
    }

    @Override
    public void doGeneration(World world, Random random, Field worldGeneratorField, WorldGenerator worldGenerator, BiomeGenBase biome,
            IGlaciosDecoration glaciosDecoration, int x, int z) throws Exception {
        
        for (int i = 0; i < worldGeneratorField.getInt(glaciosDecoration.getWorldFeatures()); i++) {
            int randX = x + random.nextInt(16) + 8;
            int randZ = z + random.nextInt(16) + 8;
            int randY = random.nextInt(world.getHeightValue(randX, randZ) * 2);

            worldGenerator.generate(world, random, randX, randY, randZ);
        }
    }

}
