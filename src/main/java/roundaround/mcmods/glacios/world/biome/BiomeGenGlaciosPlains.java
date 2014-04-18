package roundaround.mcmods.glacios.world.biome;

// Wide open, soft rolling hills with the occasional tree and widespread grass.

public class BiomeGenGlaciosPlains extends BiomeGenGlacios {

    public BiomeGenGlaciosPlains(int biomeId) {
        super(biomeId);
    }

    public BiomeGenGlaciosPlains(int biomeId, boolean register) {
        super(biomeId, register);
        this.theGlaciosBiomeDecorator.grassPerChunk = 10;
    }

}
