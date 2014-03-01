package roundaround.mcmods.glacios.world.gen.feature;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import roundaround.mcmods.glacios.GlaciosBlocks;

public class WorldGenVolcano extends WorldGeneratorGlacios {

    public WorldGenVolcano() {
        super(false);
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        boolean[] trigFunctions = new boolean[] { rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean() };
        double[] phaseShifts = new double[] { rand.nextInt(24) / 12., rand.nextInt(24) / 12., rand.nextInt(24) / 12. };
        
        int radiusScaler = rand.nextInt(16) + 35;
        int height = rand.nextInt(16) + 20;
        
        for (int posX = x - radiusScaler; posX <= x + radiusScaler; posX++) {
            for (int posZ = z - radiusScaler; posZ <= z + radiusScaler; posZ++) {
                int radius = (int)Math.round(Math.sqrt(Math.pow(posX - x, 2) + Math.pow(posZ - z,  2)));
                double theta = posZ - z != 0 || posX - x != 0 ? Math.atan2(posZ - z, posX - x) : 0;
                if (theta < 0)
                    theta += 2 * Math.PI;
                
                double boundary = radius(trigFunctions, phaseShifts, theta);
                
                for (int posY = y; posY <= y + height; posY++) {
                    double heightScaler = 1 - ((double)(posY - y) / (double)height);
                    int adjustedBoundary = (int)Math.round(heightScaler * radiusScaler * boundary);
                    
                    if (radius <= adjustedBoundary) {
                        world.setBlock(posX, posY, posZ, GlaciosBlocks.ashStone, 0, 3);
                    }
                }
            }
        }
        
        return true;
    }

    @Override
    public void doGeneration(World world, Random rand, Field worldGeneratorField, BiomeGenBase biome, int chunkX, int chunkZ) throws Exception {
        int randX = chunkX + rand.nextInt(16) + 8;
        int randZ = chunkZ + rand.nextInt(16) + 8;
        int randY = world.getHeightValue(randX, randZ);

        this.generate(world, rand, randX, randY, randZ);
    }
    
    private double radius(boolean[] trigFunctions, double[] phaseShifts, double theta) {
        double submissive = 0.45;
        double dominant = 0.45;
        double noise = 0.1;
        
        if (trigFunctions[0]) {
            submissive *= Math.cos(3*theta + Math.PI*phaseShifts[0]);
        } else {
            submissive *= Math.sin(3*theta + Math.PI*phaseShifts[0]);
        }
        
        if (trigFunctions[1]) {
            dominant *= Math.cos(2*theta + Math.PI*phaseShifts[1]);
        } else {
            dominant *= Math.sin(2*theta + Math.PI*phaseShifts[1]);
        }
        
        if (trigFunctions[2]) {
            noise *= Math.cos(12*theta + Math.PI*phaseShifts[2]);
        } else {
            noise *= Math.sin(12*theta + Math.PI*phaseShifts[2]);
        }
        
        
        
        return ((submissive + dominant + noise) / 6.) + (5. / 6.);
    }

}
