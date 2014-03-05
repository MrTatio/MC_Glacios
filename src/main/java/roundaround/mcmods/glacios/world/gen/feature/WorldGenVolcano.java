package roundaround.mcmods.glacios.world.gen.feature;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class WorldGenVolcano extends WorldGeneratorGlacios {
    
    private final Block structure;
    private final Block magma;

    public WorldGenVolcano(Block structure, Block magma) {
        super(false);
        this.structure = structure;
        this.magma = magma;
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        boolean[] trigFunctions = new boolean[] { rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean() };
        double[] phaseShifts = new double[] { rand.nextInt(24) / 12., rand.nextInt(24) / 12., rand.nextInt(24) / 12. };
        
        int radiusScaler = rand.nextInt(16) + 35;
        int height = rand.nextInt(16) + 20;
        int capRadius = rand.nextInt(5) + 10;
        int capHeight = (int)Math.round(height * radiusAtHeight((double)capRadius / (double)radiusScaler));
        int lavaHeight = (int)Math.round(capHeight * ((rand.nextDouble() * 0.5) + 0.5)) - 1;
        
//        y = minHeightForRadius(world, radiusScaler, x, y, z) - 2;
        
        for (int posX = x - radiusScaler; posX <= x + radiusScaler; posX++) {
            for (int posZ = z - radiusScaler; posZ <= z + radiusScaler; posZ++) {
                int radius = (int)Math.round(Math.sqrt(Math.pow(posX - x, 2) + Math.pow(posZ - z,  2)));
                double theta = posZ - z != 0 || posX - x != 0 ? Math.atan2(posZ - z, posX - x) : 0;
                if (theta < 0)
                    theta += 2 * Math.PI;
                
                double boundary = boundary(trigFunctions, phaseShifts, theta);
                
                for (int posY = y; posY <= y + capHeight; posY++) {
                    double heightScaler = radiusAtHeight((double)(posY - y) / (double)height);
                    int adjustedBoundary = (int)Math.round(heightScaler * radiusScaler * boundary);
                    
                    if (radius <= adjustedBoundary) {
                        if (adjustedBoundary - radius >= 2 && posY == y + capHeight) {
                            world.setBlockToAir(posX, posY, posZ);
                        } else if (adjustedBoundary - radius >= 4) {
                            if (posY <= y + lavaHeight) {
                                for (int carveY = posY - rand.nextInt(3) - 1; carveY <= posY; carveY++)
                                    world.setBlock(posX, carveY, posZ, this.magma, 0, 2);
                            } else {
                                world.setBlockToAir(posX, posY, posZ);
                            }
                        } else {
                            world.setBlock(posX, posY, posZ, this.structure, 0, 2);
                        }
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
    
    private int minHeightForRadius(World world, int radius, int x, int y, int z) {
        int minY = y;
        
        for (int searchX = x - radius; searchX <= x + radius; searchX++) {
            for (int searchZ = z - radius; searchZ <= z + radius; searchZ++) {
                int currRadius = (int)Math.round(Math.sqrt(Math.pow(searchX - x, 2) + Math.pow(searchZ - z,  2)));
                
                if (currRadius <= radius) {
                    int height = world.getHeightValue(searchX, searchZ);
                    if (height < minY - 4) {
                        minY = height + (int)((minY - height) / 2.);
                    } else if (height < minY) {
                        minY = height;
                    }
                }
            }
        }
        
        return minY;
    }
    
    private double radiusAtHeight(double height) {
        if (height < 0)
            return 1;
        if (height > 1)
            return 0;
        
        return (3./5.) * (((2./3.) * (height - (5./3.))) + Math.pow(height - (5./3.), 2));
    }
    
    private double boundary(boolean[] trigFunctions, double[] phaseShifts, double theta) {
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
