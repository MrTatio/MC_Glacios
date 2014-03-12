package roundaround.mcmods.glacios.world.gen.feature;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
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
    public static List biomesOcean = Arrays.asList(new BiomeGenBase[] {});

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
        return generate(world, coords, (coords.posX >> 4) << 4, (coords.posZ >> 4) << 4);
    }

    private boolean generate(World world, ChunkCoordinates coords, int chunkMinX, int chunkMinZ) {
        try {
            Random rand = new Random(coords.hashCode() + world.getSeed());
            int baseX = coords.posX;
            int baseZ = coords.posZ;

            int[] heightMap = new int[256];
            int chunkPosX, chunkPosZ;
            for (chunkPosX = 0; chunkPosX < 16; chunkPosX++) {
                for (chunkPosZ = 0; chunkPosZ < 16; chunkPosZ++) {
                    for (int heightY = 255; heightY > 0; heightY--) {
                        Block block = world.getBlock(chunkMinX + chunkPosX, heightY, chunkMinZ + chunkPosZ);
                        if (block != Blocks.air && block != GlaciosBlocks.crystalWater) {
                            heightMap[(chunkPosX << 4) + chunkPosZ] = heightY;
                            break;
                        }
                    }
                }
            }

            int baseY = heightMap[(absMod(baseX, 16) << 4) + absMod(baseZ, 16)];

            boolean[] trigFunctions = new boolean[] { rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean() };
            double[] phaseShifts = new double[] { rand.nextInt(24) / 12., rand.nextInt(24) / 12., rand.nextInt(24) / 12. };

            int radiusScaler = rand.nextInt(21) + 30;
            int height = rand.nextInt(16) + 25;
            int capRadius = rand.nextInt(5) + 10;
            int capHeight = (int) Math.round(height * radiusAtHeight((double) capRadius / (double) radiusScaler));
            int lavaHeight = (int) Math.round(capHeight * (rand.nextBoolean() ? (rand.nextDouble() * 0.5) + 0.5 : (rand.nextDouble() * 0.15) + 0.85)) - (rand.nextBoolean() ? 1 : 0);

            for (chunkPosX = 0; chunkPosX < 16; chunkPosX++) {
                for (chunkPosZ = 0; chunkPosZ < 16; chunkPosZ++) {
                    int radius = (int) Math.round(Math.sqrt(Math.pow(chunkMinX + chunkPosX - baseX, 2) + Math.pow(chunkMinZ + chunkPosZ - baseZ, 2)));
                    double theta = chunkMinZ + chunkPosZ - baseZ != 0 || chunkMinX + chunkPosX - baseX != 0 ? Math.atan2(chunkMinZ + chunkPosZ - baseZ, chunkMinX + chunkPosX - baseX) : 0;
                    if (theta < 0)
                        theta += 2 * Math.PI;

                    double boundary = boundary(trigFunctions, phaseShifts, theta);

                    int groundHeight = heightMap[(chunkPosX << 4) + chunkPosZ];
                    int heightDiff = baseY - groundHeight;

                    for (int posY = groundHeight; posY <= groundHeight + capHeight; posY++) {
                        double heightScaler = radiusAtHeight((double) (posY - groundHeight) / (double) height);
                        int adjustedBoundary = (int) Math.round(heightScaler * radiusScaler * boundary);

                        if (radius <= adjustedBoundary) {
                            if (adjustedBoundary - radius >= 2 && posY == groundHeight + capHeight) {
                                world.setBlockToAir(chunkMinX + chunkPosX, posY, chunkMinZ + chunkPosZ);
                            } else if (adjustedBoundary - radius >= 4) {
                                if (posY <= baseY + lavaHeight) {
                                    for (int magmaY = posY - new Random().nextInt(3) - 1; magmaY <= posY; magmaY++) {
                                        world.setBlock(chunkMinX + chunkPosX, magmaY, chunkMinZ + chunkPosZ, this.magma, 0, 6);
//                                        world.setBlockToAir(chunkMinX + chunkPosX, magmaY, chunkMinZ + chunkPosZ);
//                                        for (int magmaX = chunkPosX - 1; magmaX <= chunkPosX + 1; magmaX++) {
//                                            for (int magmaZ = chunkPosZ - 1; magmaZ <= chunkPosZ + 1; magmaZ++) {
//                                                if (world.isAirBlock(chunkMinX + magmaX, magmaY, chunkMinZ + magmaZ))
//                                                    world.setBlock(chunkMinX + magmaX, magmaY, chunkMinZ + magmaZ, this.magma, 0, 6);
//                                            }
//                                        }
                                    }
                                } else {
                                    world.setBlockToAir(chunkMinX + chunkPosX, posY, chunkMinZ + chunkPosZ);
                                }
                            } else {
                                world.setBlock(chunkMinX + chunkPosX, posY, chunkMinZ + chunkPosZ, this.structure, 0, 6);
                                if (Math.abs(heightDiff) > 2) {
                                    for (int padY = posY + Integer.signum(heightDiff); padY <= posY + (Integer.signum(heightDiff) * 3); padY += Integer.signum(heightDiff))
                                        world.setBlock(chunkMinX + chunkPosX, padY, chunkMinZ + chunkPosZ, this.structure, 0, 6);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            while (true) {
                
            }
        }

        return true;
    }

    @Override
    public void doGeneration(World world, Random rand, Field worldGeneratorField, BiomeGenBase biome, int chunkX, int chunkZ) {
        int chunkNumX = chunkX >> 4;
        int chunkNumZ = chunkZ >> 4;
        for (ChunkCoordinates volcano : getVolcanosNear(world, chunkNumX, chunkNumZ))
            this.generate(world, volcano, chunkX, chunkZ);
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
        int maxDist = 8;
        int minDist = 2;

        int k = chunkX;
        int l = chunkZ;

        if (chunkX < 0) {
            chunkX -= maxDist - 1;
        }

        if (chunkZ < 0) {
            chunkZ -= maxDist - 1;
        }

        int i1 = chunkX / maxDist;
        int j1 = chunkZ / maxDist;
        Random random = world.setRandomSeed(i1, j1, 14357617);
        i1 *= maxDist;
        j1 *= maxDist;
        i1 += random.nextInt(maxDist - minDist);
        j1 += random.nextInt(maxDist - minDist);

        if (k == i1 && l == j1) {
            BiomeGenBase biomegenbase = world.getWorldChunkManager().getBiomeGenAt(k * 16 + 8, l * 16 + 8);
            Iterator iterator = biomesLand.iterator();

            while (iterator.hasNext()) {
                BiomeGenBase biomegenbase1 = (BiomeGenBase) iterator.next();

                if (biomegenbase == biomegenbase1) {
                    return 1;
                }
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
                if (genStatus != 0) {
                    long seed = chunkNumX * 341873128712L + chunkNumZ * 132897987541L + world.getSeed() + 4291726;
                    Random rand = new Random(seed);

                    volcanos.add(new ChunkCoordinates(chunkNumX * 16 + rand.nextInt(16) + 8, genStatus, chunkNumZ * 16 + rand.nextInt(16) + 8));
                }
            }
        }

        return volcanos.toArray(new ChunkCoordinates[volcanos.size()]);
    }
    
    private int absMod(int a, int b) {
        int mod = a % b;
        return mod < 0 ? mod + b : mod;
    }
}
