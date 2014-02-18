package roundaround.mcmods.glacios.world.biome;

//Wide open ocean covered in thick ice.

public class BiomeGenGlaciosOcean extends BiomeGenGlacios {

    public BiomeGenGlaciosOcean(int biomeId) {
        this(biomeId, true);
    }

    public BiomeGenGlaciosOcean(int biomeId, boolean register) {
        super(biomeId, register);
        this.spawnableCreatureList.clear();
    }

}
