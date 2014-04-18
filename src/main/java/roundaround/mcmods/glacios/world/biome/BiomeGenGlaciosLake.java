package roundaround.mcmods.glacios.world.biome;

// Small to medium sized lake covered in ice, glowing crystals underneath.

public class BiomeGenGlaciosLake extends BiomeGenGlacios {

    public BiomeGenGlaciosLake(int biomeId) {
        this(biomeId, true);
    }

    public BiomeGenGlaciosLake(int biomeId, boolean register) {
        super(biomeId, register);
        this.theGlaciosBiomeDecorator.grassPerChunk = 0;
    }

}
