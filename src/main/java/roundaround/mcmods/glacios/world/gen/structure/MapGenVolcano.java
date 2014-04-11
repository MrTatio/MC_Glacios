package roundaround.mcmods.glacios.world.gen.structure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.MapGenBase;
import roundaround.mcmods.glacios.GlaciosBiomes;

public class MapGenVolcano extends MapGenBase {
    
    private static final int CHUNK_SIZE = 16;

    public static List biomesLand = Arrays.asList(new BiomeGenBase[] { GlaciosBiomes.volcano });
    public static List biomesOcean = Arrays.asList(new BiomeGenBase[] {});

    private final Block structure;
    private final Block magma;
    private final long worldSeed;
    
    private int[] heightMap = new int[256];

    public MapGenVolcano(Block structure, Block magma, long worldSeed) {
        super();
        this.structure = structure;
        this.magma = magma;
        this.worldSeed = worldSeed;
    }

    public boolean generateInChunk(Block[] blockArray, byte[] metaArray, BiomeGenBase[] biomeArray, Random rand, int chunkX, int chunkZ) {
        int chunkNumX = chunkX >> 4;
        int chunkNumZ = chunkZ >> 4;
        for (ChunkCoordinates volcano : getVolcanosNear(biomeArray, chunkNumX, chunkNumZ))
            this.generate(blockArray, metaArray, biomeArray, volcano, chunkX, chunkZ);
        return false;
    }
    
    private void populateHeightMap(Block[] blockArray, BiomeGenBase[] biomeArray) {
        for (int chunkPosX = 0; chunkPosX < 16; chunkPosX++) {
            for (int chunkPosZ = 0; chunkPosZ < 16; chunkPosZ++) {
                int heightY;
                for (heightY = 255; heightY >= 0; heightY--) {
                    Block block = blockArray[((chunkPosX * 16 + chunkPosZ) * (blockArray.length / 256)) + heightY];
                    if (block == biomeArray[(chunkPosX * 16) + chunkPosZ].topBlock || block == biomeArray[(chunkPosX * 16) + chunkPosZ].fillerBlock) {
                        this.heightMap[(chunkPosX * 16) + chunkPosZ] = heightY;
                        break;
                    }
                }
            }
        }
    }
    
    private void replaceBlock(Block[] blockArray, byte[] metaArray, int index, Block block, byte meta) {
        blockArray[index] = block;
        metaArray[index] = meta;
    }

    private boolean generate(Block[] blockArray, byte[] metaArray, BiomeGenBase[] biomeArray, ChunkCoordinates coords, int chunkMinX, int chunkMinZ) {
        int chunkSize = blockArray.length / 256;
        try {
            Random rand = new Random(coords.hashCode() + this.worldSeed);
            int baseX = coords.posX;
            int baseZ = coords.posZ;

            populateHeightMap(blockArray, biomeArray);

            int baseY = this.heightMap[(absMod(baseX, 16) * 16) + absMod(baseZ, 16)];

            boolean[] trigFunctions = new boolean[] { rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean() };
            double[] phaseShifts = new double[] { rand.nextInt(24) / 12., rand.nextInt(24) / 12., rand.nextInt(24) / 12. };

            int radiusScaler = rand.nextInt(21) + 30;
            int height = rand.nextInt(16) + 25;
            int capRadius = rand.nextInt(5) + 10;
            int capHeight = (int) Math.round(height * radiusAtHeight((double) capRadius / (double) radiusScaler));
            int lavaHeight = (int) Math.round(capHeight * (rand.nextBoolean() ? (rand.nextDouble() * 0.5) + 0.5 : (rand.nextDouble() * 0.15) + 0.85)) - (rand.nextBoolean() ? 1 : 0);

            for (int chunkPosX = 0; chunkPosX < 16; chunkPosX++) {
                for (int chunkPosZ = 0; chunkPosZ < 16; chunkPosZ++) {
                    int radius = (int) Math.round(Math.sqrt(Math.pow(chunkMinX + chunkPosX - baseX, 2) + Math.pow(chunkMinZ + chunkPosZ - baseZ, 2)));
                    
                    if (radius > radiusScaler + 1)
                        continue;
                    
                    double theta = chunkMinZ + chunkPosZ - baseZ != 0 || chunkMinX + chunkPosX - baseX != 0 ? Math.atan2(chunkMinZ + chunkPosZ - baseZ, chunkMinX + chunkPosX - baseX) : 0;
                    if (theta < 0)
                        theta += 2 * Math.PI;

                    double boundary = boundary(trigFunctions, phaseShifts, theta);

                    int groundHeight = heightMap[(chunkPosX * 16) + chunkPosZ];
                    int heightDiff = baseY - groundHeight;

                    for (int posY = groundHeight; posY <= groundHeight + capHeight; posY++) {
                        double heightScaler = radiusAtHeight((double) (posY - groundHeight) / (double) height);
                        int adjustedBoundary = (int) Math.round(heightScaler * radiusScaler * boundary);

                        if (radius <= adjustedBoundary) {
                            if (adjustedBoundary - radius >= 2 && posY == groundHeight + capHeight) {
                                replaceBlock(blockArray, metaArray, ((chunkPosX * 16 + chunkPosZ) * chunkSize) + posY, Blocks.air, (byte) 0);
                            } else if (adjustedBoundary - radius >= 4) {
                                if (posY <= baseY + lavaHeight) {
                                    for (int magmaY = posY - new Random().nextInt(3) - 1; magmaY <= posY; magmaY++) {
                                        if (((chunkPosX * 16 + chunkPosZ) * chunkSize) + magmaY < 0 || ((chunkPosX * 16 + chunkPosZ) * chunkSize) + magmaY >= blockArray.length) {
                                            System.out.println(chunkPosX + ":" + chunkPosZ + ":" + magmaY);
                                        }
                                        replaceBlock(blockArray, metaArray, ((chunkPosX * 16 + chunkPosZ) * chunkSize) + magmaY, this.magma, (byte) 0);
                                    }
                                } else {
                                    replaceBlock(blockArray, metaArray, ((chunkPosX * 16 + chunkPosZ) * chunkSize) + posY, Blocks.air, (byte) 0);
                                }
                            } else {
                                replaceBlock(blockArray, metaArray, ((chunkPosX * 16 + chunkPosZ) * chunkSize) + posY, this.structure, (byte) 0);
                                if (Math.abs(heightDiff) > 2) {
                                    for (int padY = posY + Integer.signum(heightDiff); padY <= posY + (Integer.signum(heightDiff) * 3); padY += Integer.signum(heightDiff))
                                        replaceBlock(blockArray, metaArray, ((chunkPosX * 16 + chunkPosZ) * chunkSize) + padY, this.structure, (byte) 0);
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

    public int canGenVolcanoAtCoords(BiomeGenBase[] biomeArray, int chunkX, int chunkZ) {
        int maxDist = 4;
        int minDist = 2;

        int k = chunkX;
        int l = chunkZ;

        if (k < 0) {
            k -= maxDist - 1;
        }

        if (l < 0) {
            l -= maxDist - 1;
        }

        int randX = k / maxDist;
        int randZ = l / maxDist;
        Random rand = new Random(randX * 341873128712L + randZ * 132897987541L + this.worldSeed + 4291726);
        randX *= maxDist;
        randZ *= maxDist;
        randX += rand.nextInt(maxDist - minDist);
        randZ += rand.nextInt(maxDist - minDist);

        if (chunkX == randX && chunkZ == randZ) {
            BiomeGenBase currentBiome = biomeArray[(8 * 16) + 8];
            
            Iterator iterator = biomesLand.iterator();
            while (iterator.hasNext()) {
                BiomeGenBase compareBiome = (BiomeGenBase)iterator.next();
                if (currentBiome == compareBiome) {
                    return 1;
                }
            }
            
            iterator = biomesOcean.iterator();
            while (iterator.hasNext()) {
                BiomeGenBase compareBiome = (BiomeGenBase)iterator.next();
                if (currentBiome == compareBiome) {
                    return 2;
                }
            }
        }

        return 0;
    }

    public ChunkCoordinates[] getVolcanosNear(BiomeGenBase[] biomeArray, int startNumX, int startNumZ) {
        HashSet<ChunkCoordinates> volcanos = new HashSet<ChunkCoordinates>();

        int range = 4;
        for (int chunkNumX = startNumX - range; chunkNumX <= startNumX + range; chunkNumX++) {
            for (int chunkNumZ = startNumZ - range; chunkNumZ <= startNumZ + range; chunkNumZ++) {
                int genStatus = canGenVolcanoAtCoords(biomeArray, chunkNumX, chunkNumZ);
                if (genStatus != 0) {
                    Random rand = new Random(chunkNumX * 341873128712L + chunkNumZ * 132897987541L + this.worldSeed + 4291726);
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
