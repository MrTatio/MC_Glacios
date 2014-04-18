package roundaround.mcmods.glacios.world.gen.structure;

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

public class MapGenVolcano extends MapGenGlacios {

    private static List biomesLand = Arrays.asList(new BiomeGenBase[] { GlaciosBiomes.volcano });
    private static List biomesOcean = Arrays.asList(new BiomeGenBase[] {});

    private final Block structure;
    private final Block magma;

    public MapGenVolcano(Block structure, Block magma, World world) {
        super(world);
        this.structure = structure;
        this.magma = magma;
    }

    @Override
    public void generateInChunk(Block[] blockArray, byte[] metaArray, BiomeGenBase[] biomeArray, int chunkX, int chunkZ) {
        int chunkNumX = chunkX >> 4;
        int chunkNumZ = chunkZ >> 4;
        for (ChunkCoordinates volcano : getGenLocations(biomeArray, chunkNumX, chunkNumZ))
            this.generate(blockArray, metaArray, biomeArray, volcano, chunkX, chunkZ);
    }

    private boolean generate(Block[] blockArray, byte[] metaArray, BiomeGenBase[] biomeArray, ChunkCoordinates coords, int chunkMinX, int chunkMinZ) {
        try {
            Random rand = new Random(coords.hashCode() + this.world.getSeed());
            int baseX = coords.posX;
            int baseZ = coords.posZ;

            populateHeightMap(blockArray);

            boolean[] trigFunctions = new boolean[] { rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean() };
            double[] phaseShifts = new double[] { rand.nextInt(24) / 12., rand.nextInt(24) / 12., rand.nextInt(24) / 12. };

            int radiusScaler = rand.nextInt(21) + 30;
            int height = rand.nextInt(16) + 25;
            int capRadius = rand.nextInt(5) + 10;
            int capHeight = (int) Math.round(height * radiusAtHeight((double) capRadius / (double) radiusScaler));
            int lavaHeight = (int) Math.round(capHeight * (rand.nextBoolean() ? (rand.nextDouble() * 0.5) + 0.5 : (rand.nextDouble() * 0.15) + 0.85)) - (rand.nextBoolean() ? 1 : 0);

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    int radius = (int) Math.round(Math.sqrt(Math.pow(chunkMinX + x - baseX, 2) + Math.pow(chunkMinZ + z - baseZ, 2)));
                    
                    if (radius > radiusScaler + 1)
                        continue;
                    
                    double theta = chunkMinZ + z - baseZ != 0 || chunkMinX + x - baseX != 0 ? Math.atan2(chunkMinZ + z - baseZ, chunkMinX + x - baseX) : 0;
                    if (theta < 0)
                        theta += 2 * Math.PI;

                    double boundary = boundary(trigFunctions, phaseShifts, theta);

                    int groundHeight = this.heightMap[x * 16 + z];

                    for (int posY = groundHeight; posY <= groundHeight + capHeight && posY < 256; posY++) {
                        
                        double heightScaler = radiusAtHeight((double) (posY - groundHeight) / (double) height);
                        int adjustedBoundary = (int) Math.round(heightScaler * radiusScaler * boundary);

                        if (radius <= adjustedBoundary) {
                            if (adjustedBoundary - radius >= 2 + ((double) 2 * (posY - groundHeight) / height) && posY == groundHeight + capHeight) {
                                
                                replaceBlock(blockArray, metaArray, x, posY, z, Blocks.air);
                                
                            } else if (adjustedBoundary - radius >= 4) {
                                
                                if (posY <= groundHeight + lavaHeight) {
                                    
                                    for (int magmaY = posY - new Random().nextInt(3) - 1; magmaY <= posY; magmaY++) {
                                        replaceBlock(blockArray, metaArray, x, magmaY, z, this.magma);
                                    }
                                    
                                } else {
                                    replaceBlock(blockArray, metaArray, x, posY, z, Blocks.air);
                                }
                                
                            } else {
                                replaceBlock(blockArray, metaArray, x, posY, z, this.structure);
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
    public int canGenAtCoords(BiomeGenBase[] biomeArray, int chunkX, int chunkZ) {
        int maxDist = 8;
        int minDist = 4;

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
        Random rand = new Random(randX * 341873128712L + randZ * 132897987541L + this.world.getSeed() + 4291726);
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

    @Override
    public ChunkCoordinates[] getGenLocations(BiomeGenBase[] biomeArray, int startNumX, int startNumZ) {
        HashSet<ChunkCoordinates> volcanos = new HashSet<ChunkCoordinates>();

        int range = 4;
        for (int chunkNumX = startNumX - range; chunkNumX <= startNumX + range; chunkNumX++) {
            for (int chunkNumZ = startNumZ - range; chunkNumZ <= startNumZ + range; chunkNumZ++) {
                int genStatus = canGenAtCoords(biomeArray, chunkNumX, chunkNumZ);
                if (genStatus != 0) {
                    Random rand = new Random(chunkNumX * 341873128712L + chunkNumZ * 132897987541L + this.world.getSeed() + 4291726);
                    volcanos.add(new ChunkCoordinates(chunkNumX * 16 + rand.nextInt(8) + 4, genStatus, chunkNumZ * 16 + rand.nextInt(8) + 4));
                }
            }
        }

        return volcanos.toArray(new ChunkCoordinates[volcanos.size()]);
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
    
    private int posInChunk(int pos) {
        int mod = pos % 16;
        return mod < 0 ? mod + 16 : mod;
    }
}
