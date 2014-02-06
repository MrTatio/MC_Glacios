package roundaround.mcmods.glacios.world.biome;

import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import roundaround.mcmods.glacios.GlaciosConfig;

public class WorldChunkManagerGlacios extends WorldChunkManager {

    public WorldChunkManagerGlacios(World world) {
        super(world);
    }

    @Override
    public ChunkPosition func_150795_a(int x, int z, int radius, List biomesToSpawnIn, Random random) {
        int spawnSearchRadius = GlaciosConfig.spawnSearchRadius;

        return super.func_150795_a(x, z, spawnSearchRadius, biomesToSpawnIn, random);
    }
}
