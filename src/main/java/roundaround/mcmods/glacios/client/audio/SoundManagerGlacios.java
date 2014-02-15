package glacios.client.audio;

import glacios.core.Glacios;

import java.io.File;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundPool;
import net.minecraft.client.audio.SoundPoolEntry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SoundManagerGlacios {

    public int MUSIC_INTERVAL = 12000;
    public Random rand = new Random();
    public int musicTicks;

    public boolean soundSwapped;

    public SoundPool soundPoolMusicBackup;
    public SoundPool soundPool;

    public SoundManagerGlacios() {
        musicTicks = rand.nextInt(MUSIC_INTERVAL);
    }

    public void onTick() {
        Minecraft mc = Glacios.mc;
        SoundManager sndManager = mc.sndManager;

        if (SoundManager.sndSystem != null) {
            if (mc.thePlayer != null && mc.thePlayer.dimension == Glacios.dimID) {
                if (!soundSwapped) {
                    SoundManager.sndSystem.stop("BgMusic");

                    soundPoolMusicBackup = sndManager.soundPoolMusic;
                    sndManager.soundPoolMusic = new SoundPool();
                    tryInstallSound("mod/music/Wasteland.ogg");
                    tryInstallSound("mod/music/Aurora.ogg");
                    soundPool = sndManager.soundPoolMusic;
                    soundSwapped = true;
                }

                if (!SoundManager.sndSystem.playing("BgMusic") && mc.gameSettings.musicVolume != 0.0F) {
                    if (musicTicks > 0) {
                        musicTicks--;
                        return;
                    }

                    SoundPoolEntry song = sndManager.soundPoolMusic.getRandomSound();

                    if (song != null) {
                        musicTicks = rand.nextInt(MUSIC_INTERVAL) + MUSIC_INTERVAL;
                        SoundManager.sndSystem.backgroundMusic("BgMusic", song.soundUrl, song.soundName, false);
                        SoundManager.sndSystem.setVolume("BgMusic", mc.gameSettings.musicVolume);
                        SoundManager.sndSystem.play("BgMusic");
                    }
                }
            } else if (soundSwapped) {
                SoundManager.sndSystem.stop("BgMusic");
                sndManager.soundPoolMusic = soundPoolMusicBackup;
                soundSwapped = false;
            }
        }
    }

    public void tryInstallSound(String str) {
        File soundFile = new File(Glacios.mc.mcDataDir, "resources/" + str);
        if (soundFile.exists() && soundFile.canRead() && soundFile.isFile()) {
            Glacios.mc.sndManager.addMusic(str, soundFile);
        } else {
            System.err.println("Could not load " + str);
        }
    }

}
