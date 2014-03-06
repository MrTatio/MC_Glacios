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
        
        int radiusScaler = rand.nextInt(21) + 30;
        int height = rand.nextInt(16) + 25;
        int capRadius = rand.nextInt(5) + 10;
        int capHeight = (int)Math.round(height * radiusAtHeight((double)capRadius / (double)radiusScaler));
        int lavaHeight = (int)Math.round(capHeight * (rand.nextBoolean() ? (rand.nextDouble() * 0.5) + 0.5 : (rand.nextDouble() * 0.15) + 0.85)) - (rand.nextBoolean() ? 1 : 0);
        
        for (int posX = x - radiusScaler; posX <= x + radiusScaler; posX++) {
            for (int posZ = z - radiusScaler; posZ <= z + radiusScaler; posZ++) {
                int radius = (int)Math.round(Math.sqrt(Math.pow(posX - x, 2) + Math.pow(posZ - z,  2)));
                double theta = posZ - z != 0 || posX - x != 0 ? Math.atan2(posZ - z, posX - x) : 0;
                if (theta < 0)
                    theta += 2 * Math.PI;
                
                double boundary = boundary(trigFunctions, phaseShifts, theta);
                
                int groundHeight = world.getHeightValue(posX, posZ);
                int heightDiff = y - groundHeight;
                
                for (int posY = groundHeight; posY <= groundHeight + capHeight; posY++) {
                    double heightScaler = radiusAtHeight((double)(posY - groundHeight) / (double)height);
                    int adjustedBoundary = (int)Math.round(heightScaler * radiusScaler * boundary);
                    
                    if (radius <= adjustedBoundary) {
                        if (adjustedBoundary - radius >= 2 && posY == groundHeight + capHeight) {
                            world.setBlockToAir(posX, posY, posZ);
                        } else if (adjustedBoundary - radius >= 4) {
                            if (posY <= y + lavaHeight) {
                                for (int magmaY = posY - rand.nextInt(3) - 1; magmaY <= posY; magmaY++) {
                                    world.setBlock(posX, magmaY, posZ, this.magma, 0, 2);
                                    for (int magmaX = posX - 1; magmaX <= posX + 1; magmaX++) {
                                        for (int magmaZ = posZ - 1; magmaZ <= posZ + 1; magmaZ++) {
                                            if (world.isAirBlock(magmaX, magmaY, magmaZ))
                                                world.setBlock(magmaX, magmaY, magmaZ, this.magma, 0, 2);
                                        }
                                    }
                                }
                            } else {
                                world.setBlockToAir(posX, posY, posZ);
                            }
                        } else {
                            world.setBlock(posX, posY, posZ, this.structure, 0, 2);
                            if (Math.abs(heightDiff) > 2) {
                                for (int padY = posY + Integer.signum(heightDiff); padY <= posY + (Integer.signum(heightDiff) * 3); padY += Integer.signum(heightDiff))
                                    world.setBlock(posX, padY, posZ, this.structure, 0, 2);
                            }
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
