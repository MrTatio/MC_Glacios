package roundaround.mcmods.glacios.world.gen.feature;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import roundaround.mcmods.glacios.GlaciosBiomes;
import roundaround.mcmods.glacios.GlaciosBlocks;

public class WorldGenVolcano extends WorldGeneratorGlacios {

    public static List biomesLand = Arrays.asList(new BiomeGenBase[] { GlaciosBiomes.volcano });
    public static List biomesOcean = Arrays.asList(new BiomeGenBase[] { });

    private final Block structure;
    private final Block magma;

    public WorldGenVolcano(Block structure, Block magma) {
        super(false);
        this.structure = structure;
        this.magma = magma;
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        return generate(world, new ChunkCoordinates(x, y, z));
    }
    
    private boolean generate(World world, ChunkCoordinates coords) {
        return generate(world, coords, (coords.posX >> 4) * 16, (coords.posZ >> 4) * 16);
    }

    private boolean generate(World world, ChunkCoordinates coords, int chunkX, int chunkZ) {
        Random rand = new Random(coords.hashCode());
        int x = coords.posX;
        int z = coords.posZ;
        
        int[] heightMap = new int[256];

        for (int heightX = 0; heightX < 16; heightX++) {
            for (int heightZ = 0; heightZ < 16; heightZ++) {
                for (int heightY = world.getActualHeight(); heightY > 0; heightY--) {
                    Block block = world.getBlock(heightX, heightY, heightZ);
                    if (block != Blocks.air && block != GlaciosBlocks.crystalWater) {
                        heightMap[heightX * 16 + heightZ] = heightY;
                        break;
                    }
                }
            }
        }
        
        int y = heightMap[(x % 16) * 16 + (z % 16)];
        System.out.println("Generating a volcano at " + x + "," + y + "," + z + " for the chunk at " + chunkX + "," + chunkZ + "!");

        boolean[] trigFunctions = new boolean[] { rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean() };
        double[] phaseShifts = new double[] { rand.nextInt(24) / 12., rand.nextInt(24) / 12., rand.nextInt(24) / 12. };

        int radiusScaler = rand.nextInt(21) + 30;
        int height = rand.nextInt(16) + 25;
        int capRadius = rand.nextInt(5) + 10;
        int capHeight = (int) Math.round(height * radiusAtHeight((double) capRadius / (double) radiusScaler));
        int lavaHeight = (int) Math.round(capHeight * (rand.nextBoolean() ? (rand.nextDouble() * 0.5) + 0.5 : (rand.nextDouble() * 0.15) + 0.85)) - (rand.nextBoolean() ? 1 : 0);

        for (int posX = chunkX; posX < chunkX + 16; posX++) {
            for (int posZ = chunkZ; posZ < chunkZ + 16; posZ++) {
                int radius = (int) Math.round(Math.sqrt(Math.pow(posX - x, 2) + Math.pow(posZ - z, 2)));
                double theta = posZ - z != 0 || posX - x != 0 ? Math.atan2(posZ - z, posX - x) : 0;
                if (theta < 0)
                    theta += 2 * Math.PI;

                double boundary = boundary(trigFunctions, phaseShifts, theta);

                int groundHeight = heightMap[(posX % 16) * 16 + (posZ % 16)];
                int heightDiff = y - groundHeight;

                for (int posY = groundHeight; posY <= groundHeight + capHeight; posY++) {
                    double heightScaler = radiusAtHeight((double) (posY - groundHeight) / (double) height);
                    int adjustedBoundary = (int) Math.round(heightScaler * radiusScaler * boundary);

                    if (radius <= adjustedBoundary) {
                        if (adjustedBoundary - radius >= 2 && posY == groundHeight + capHeight) {
                            world.setBlockToAir(posX, posY, posZ);
                        } else if (adjustedBoundary - radius >= 4) {
                            if (posY <= y + lavaHeight) {
                                for (int magmaY = posY - new Random().nextInt(3) - 1; magmaY <= posY; magmaY++) {
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
    public void doGeneration(World world, Random rand, Field worldGeneratorField, BiomeGenBase biome, int chunkX, int chunkZ) {
        int chunkNumX = chunkX >> 4;
        int chunkNumZ = chunkZ >> 4;
        for (ChunkCoordinates volcano : getVolcanosNear(world, chunkNumX, chunkNumZ))
            this.generate(world, volcano, chunkNumX, chunkNumZ);
    }

    private double radiusAtHeight(double height) {
        if (height < 0)
            return 1;
        if (height > 1)
            return 0;

        return (3. / 5.) * (((2. / 3.) * (height - (5. / 3.))) + Math.pow(height - (5. / 3.), 2));
    }

    private double boundary(boolean[] trigFunctions, double[] phaseShifts, double theta) {
        double submissive = 0.45;
        double dominant = 0.45;
        double noise = 0.1;

        if (trigFunctions[0]) {
            submissive *= Math.cos(3 * theta + Math.PI * phaseShifts[0]);
        } else {
            submissive *= Math.sin(3 * theta + Math.PI * phaseShifts[0]);
        }

        if (trigFunctions[1]) {
            dominant *= Math.cos(2 * theta + Math.PI * phaseShifts[1]);
        } else {
            dominant *= Math.sin(2 * theta + Math.PI * phaseShifts[1]);
        }

        if (trigFunctions[2]) {
            noise *= Math.cos(12 * theta + Math.PI * phaseShifts[2]);
        } else {
            noise *= Math.sin(12 * theta + Math.PI * phaseShifts[2]);
        }

        return ((submissive + dominant + noise) / 6.) + (5. / 6.);
    }

    public int canGenVolcanoAtCoords(World world, int chunkX, int chunkZ) {
        byte numChunks = 1;
        byte offsetChunks = 1;
        int oldChunkX = chunkX;
        int oldChunkZ = chunkZ;

        if (chunkX < 0) {
            chunkX -= numChunks - 1;
        }

        if (chunkZ < 0) {
            chunkZ -= numChunks - 1;
        }

        int randX = chunkX / numChunks;
        int randZ = chunkZ / numChunks;
        long seed = randX * 341873128712L + randZ * 132897987541L + world.getSeed() + 4291726;
        Random rand = new Random(seed);
        randX *= numChunks;
        randZ *= numChunks;
        randX += rand.nextInt(numChunks - offsetChunks);
        randZ += rand.nextInt(numChunks - offsetChunks);

        if (oldChunkX == randX && oldChunkZ == randZ) {
            if (world.getWorldChunkManager().areBiomesViable(oldChunkX * 16 + 8, oldChunkZ * 16 + 8, 0, biomesLand)) {
                return 1;
            }
            if (world.getWorldChunkManager().areBiomesViable(oldChunkX * 16 + 8, oldChunkZ * 16 + 8, 0, biomesOcean)) {
                return 2;
            }
        }

        return 0;
    }

    public ChunkCoordinates[] getVolcanosNear(World world, int startNumX, int startNumZ) {
        HashSet<ChunkCoordinates> volcanos = new HashSet<ChunkCoordinates>();
        
        int range = 3;
        for (int chunkNumX = startNumX - range; chunkNumX <= startNumX + range; chunkNumX++) {
            for (int chunkNumZ = startNumZ - range; chunkNumZ <= startNumZ + range; chunkNumZ++) {
                int genStatus = canGenVolcanoAtCoords(world, chunkNumX, chunkNumZ);
                if (genStatus != 0)
                    volcanos.add(new ChunkCoordinates(chunkNumX * 16 + 8, genStatus, chunkNumZ * 16 + 8));
            }
        }

        return volcanos.toArray(new ChunkCoordinates[volcanos.size()]);
    }
}
