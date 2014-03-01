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
        HashMap<Long, Double> boundaries = new HashMap<Long, Double>();
        int neededAngles = 0;
        
        boolean[] trigFunctions = new boolean[] { rand.nextBoolean(), rand.nextBoolean() };
        double firstMagnitude = rand.nextInt(21) / 20.;
        double[] magnitudes = new double[] { firstMagnitude, 1. - firstMagnitude };
        double[] phaseShifts = new double[] { rand.nextInt(24) / 12, rand.nextInt(16) / 8 };
        
        System.out.println("First sinusoid: " + magnitudes[0] + (trigFunctions[0] ? "cos" : "sin") + "(3x + " + phaseShifts[0] + "pi)");
        System.out.println("Second sinusoid: " + magnitudes[1] + (trigFunctions[1] ? "cos" : "sin") + "(2x + " + phaseShifts[1] + "pi)");
        
        int radiusScaler = rand.nextInt(20) + 21;
        int height = rand.nextInt(25) + 21;
        
        for (int posX = x - radiusScaler; posX <= x + radiusScaler; posX++) {
            for (int posZ = z - radiusScaler; posZ <= z + radiusScaler; posZ++) {
                int radius = (int)Math.round(Math.sqrt(Math.pow(posX - x, 2) + Math.pow(posZ - z,  2)));
                long slope = (long)Math.floor(((posZ - z) / (posX - x)) * Math.pow(10, 6));
                double boundary;
                
                if (boundaries.containsKey(slope)) {
                    boundary = boundaries.get(slope);
                } else {
                    double theta = Math.atan2(posZ - z, posX - x);
                    if (theta < 0)
                        theta += 2 * Math.PI;
                    boundary = radius(trigFunctions, magnitudes, phaseShifts, theta);
                    boundaries.put(slope, boundary);
                }
                
                for (int posY = y; posY <= y + height; posY++) {
                    double heightScaler = 1 - ((double)(posY - y) / (double)height);
                    int adjustedBoundary = (int)Math.round(heightScaler * radiusScaler * boundary);
                    
                    if (radius <= adjustedBoundary) {
                        world.setBlock(posX, posY, posZ, GlaciosBlocks.ashStone, 0, 3);
                    }
                }
                
                neededAngles++;
            }
        }
        
        System.out.println("Needed: " + neededAngles + "; Calculated: " + boundaries.size());

        System.out.println("Done 1?");
        return true;
    }

    @Override
    public void doGeneration(World world, Random rand, Field worldGeneratorField, BiomeGenBase biome, int chunkX, int chunkZ) throws Exception {
        int randX = chunkX + rand.nextInt(16) + 8;
        int randZ = chunkZ + rand.nextInt(16) + 8;
        int randY = world.getHeightValue(randX, randZ);

        this.generate(world, rand, randX, randY, randZ);
        System.out.println("Done 2?");
    }
    
    private double radius(boolean[] trigFunctions, double[] magnitudes, double[] phaseShifts, double theta) {
        double submissive = magnitudes[0];
        double dominant = magnitudes[1];
        
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
        
        return ((submissive + dominant) / 3.) + (2. / 3.);
    }

}
