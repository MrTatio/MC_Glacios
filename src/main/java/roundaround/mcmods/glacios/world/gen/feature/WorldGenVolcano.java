package roundaround.mcmods.glacios.world.gen.feature;

import java.lang.reflect.Field;
import java.util.HashMap;
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
        HashMap<Double, Double> radii = new HashMap<Double, Double>();
        
        boolean[] trigFunctions = new boolean[] { rand.nextBoolean(), rand.nextBoolean() };
        double firstMagnitude = rand.nextDouble();
        double[] magnitudes = new double[] { firstMagnitude, 1F - firstMagnitude };
        double[] phaseShifts = new double[] { rand.nextInt(30) / (double)((rand.nextInt(4) + 1) * 3), rand.nextInt(30) / (double)((rand.nextInt(4) + 1) * 2) };
        
        int radiusScaler = rand.nextInt(10) + 11;
        int height = rand.nextInt(15) + 16;
        
        for (int posX = x - radiusScaler; posX <= x + radiusScaler; posX++) {
            for (int posZ = z - radiusScaler; posZ <= z + radiusScaler; posZ++) {
                int radius = (int)Math.round(Math.sqrt(Math.pow(posX - x, 2) + Math.pow(posZ - z,  2)));
                double theta = Math.floor((Math.atan2(posZ - z, posX - x)) * 10000.) / 10000.;
                double boundary;
                
                if (radii.containsKey(theta))
                    boundary = radii.get(theta);
                else
                    boundary = radius(trigFunctions, magnitudes, phaseShifts, theta);
                
                for (int posY = y; posY <= y + height; posY++) {
                    double heightScaler = (y + height - posY) / height;
                    int adjustedBoundary = (int)Math.round(heightScaler * radiusScaler * boundary);
                    System.out.println(adjustedBoundary);
                    
                    if (radius <= adjustedBoundary) {
                        world.setBlock(posX, posY, posZ, GlaciosBlocks.ashStone, 0, 3);
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void doGeneration(World world, Random rand, Field worldGeneratorField, BiomeGenBase biome, int chunkX, int chunkZ) throws Exception {
        int randX = chunkX + rand.nextInt(16) + 8;
        int randZ = chunkZ + rand.nextInt(16) + 8;
        int randY = rand.nextInt(world.getHeightValue(randX, randZ) * 2);

        this.generate(world, rand, randX, randY, randZ);
    }
    
    private double radius(boolean[] trigFunctions, double[] magnitudes, double[] phaseShifts, double theta) {
        theta = 2 * Math.PI * theta;
        
        double submissive = magnitudes[0];
        double dominant = magnitudes[1];
        
        if (trigFunctions[0]) {
            submissive *= Math.cos(3*theta + phaseShifts[0]);
        } else {
            submissive *= Math.sin(3*theta + phaseShifts[0]);
        }
        
        if (trigFunctions[1]) {
            submissive *= Math.cos(2*theta + phaseShifts[1]);
        } else {
            submissive *= Math.sin(2*theta + phaseShifts[1]);
        }
        
        return (submissive + dominant) / 3 + 0.5;
    }

}
