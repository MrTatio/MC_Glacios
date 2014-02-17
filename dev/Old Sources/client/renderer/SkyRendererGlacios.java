package glacios.client.renderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;

public class SkyRendererGlacios extends IRenderHandler {

	private int starGLCallList;
	private int glSkyList;
	private int glSkyList2;

	public SkyRendererGlacios() {
		RenderGlobal renderGlobal = FMLClientHandler.instance().getClient().renderGlobal;
		try {
			starGLCallList = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, renderGlobal, "v");
			glSkyList = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, renderGlobal, "w");
			glSkyList2 = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, renderGlobal, "x");
		} catch (Exception e) {
			starGLCallList = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, renderGlobal, "starGLCallList");
			glSkyList = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, renderGlobal, "glSkyList");
			glSkyList2 = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, renderGlobal, "glSkyList2");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void render(float partialTicks, WorldClient world, Minecraft minecraft) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Vec3 vec3 = world.getSkyColor(minecraft.renderViewEntity, partialTicks);
		float cR = (float) vec3.xCoord;
		float cG = (float) vec3.yCoord;
		float cB = (float) vec3.zCoord;

		if (minecraft.gameSettings.anaglyph) {
			float cWeightedRGB = (cR * 30.0F + cG * 59.0F + cB * 11.0F) / 100.0F;
			float cWeightedRG = (cR * 30.0F + cG * 70.0F) / 100.0F;
			float cWeightedRB = (cR * 30.0F + cB * 70.0F) / 100.0F;
			cR = cWeightedRGB;
			cG = cWeightedRG;
			cB = cWeightedRB;
		}

		GL11.glColor3f(cR, cG, cB);
		Tessellator tessellator = Tessellator.instance;
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glColor3f(cR, cG, cB);
		GL11.glCallList(glSkyList);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.disableStandardItemLighting();
		float[] dawnDuskColors = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);

		if (dawnDuskColors != null) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glPushMatrix();
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
			float cDR = dawnDuskColors[0];
			float cDG = dawnDuskColors[1];
			float cDB = dawnDuskColors[2];

			if (minecraft.gameSettings.anaglyph) {
				float cDWeightedRGB = (cDR * 30.0F + cDG * 59.0F + cDB * 11.0F) / 100.0F;
				float cDWeightedRG = (cDR * 30.0F + cDG * 70.0F) / 100.0F;
				float cDWeightedRB = (cDR * 30.0F + cDB * 70.0F) / 100.0F;
				cDR = cDWeightedRGB;
				cDG = cDWeightedRG;
				cDB = cDWeightedRB;
			}

			tessellator.startDrawing(6);
			tessellator.setColorRGBA_F(cDR, cDG, cDB, dawnDuskColors[3]);
			tessellator.addVertex(0.0D, 100.0D, 0.0D);
			byte points = 16;
			tessellator.setColorRGBA_F(dawnDuskColors[0], dawnDuskColors[1], dawnDuskColors[2], 0.0F);

			for (int j = 0; j <= points; ++j) {
				float angle = j * (float) Math.PI * 2.0F / points;
				float yUnit = MathHelper.sin(angle);
				float xUnit = MathHelper.cos(angle);
				tessellator.addVertex(yUnit * 120.0F, xUnit * 120.0F, -xUnit * 40.0F * dawnDuskColors[3]);
			}

			tessellator.draw();
			GL11.glPopMatrix();
			GL11.glShadeModel(GL11.GL_FLAT);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glPushMatrix();
		float rainShadow = 1.0F - world.getRainStrength(partialTicks);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, rainShadow);
		GL11.glTranslatef(0F, 0F, 0F);
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
		float sunSize = 10.0F;
		minecraft.renderEngine.bindTexture("/environment/sun.png");
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((-sunSize), 100.0D, (-sunSize), 0.0D, 0.0D);
		tessellator.addVertexWithUV(sunSize, 100.0D, (-sunSize), 1.0D, 0.0D);
		tessellator.addVertexWithUV(sunSize, 100.0D, sunSize, 1.0D, 1.0D);
		tessellator.addVertexWithUV((-sunSize), 100.0D, sunSize, 0.0D, 1.0D);
		tessellator.draw();
		float moonSize = 30.0F;
		minecraft.renderEngine.bindTexture("/environment/moon.png");
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((-moonSize), -100.0D, moonSize, 0.0D, 0.0D);
		tessellator.addVertexWithUV(moonSize, -100.0D, moonSize, 1.0D, 0.0D);
		tessellator.addVertexWithUV(moonSize, -100.0D, (-moonSize), 1.0D, 1.0D);
		tessellator.addVertexWithUV((-moonSize), -100.0D, (-moonSize), 0.0D, 1.0D);
		tessellator.draw();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		float adjStarBrightness = world.getStarBrightness(partialTicks) * rainShadow;

		if (adjStarBrightness > 0.0F) {
			GL11.glColor4f(adjStarBrightness, adjStarBrightness, adjStarBrightness, adjStarBrightness);
			GL11.glCallList(starGLCallList);
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0.0F, 0.0F, 0.0F);
		double adjHeight = minecraft.thePlayer.getPosition(partialTicks).yCoord - world.getHorizon();

		if (adjHeight < 0.0D) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 12.0F, 0.0F);
			GL11.glCallList(glSkyList2);
			GL11.glPopMatrix();
			float unit = 1.0F;
			float invUnit = -unit;
			float skyStart = -((float) (adjHeight + 65.0D));
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(0, 255);
			tessellator.addVertex((-unit), skyStart, unit);
			tessellator.addVertex(unit, skyStart, unit);
			tessellator.addVertex(unit, invUnit, unit);
			tessellator.addVertex((-unit), invUnit, unit);
			tessellator.addVertex((-unit), invUnit, (-unit));
			tessellator.addVertex(unit, invUnit, (-unit));
			tessellator.addVertex(unit, skyStart, (-unit));
			tessellator.addVertex((-unit), skyStart, (-unit));
			tessellator.addVertex(unit, invUnit, (-unit));
			tessellator.addVertex(unit, invUnit, unit);
			tessellator.addVertex(unit, skyStart, unit);
			tessellator.addVertex(unit, skyStart, (-unit));
			tessellator.addVertex((-unit), skyStart, (-unit));
			tessellator.addVertex((-unit), skyStart, unit);
			tessellator.addVertex((-unit), invUnit, unit);
			tessellator.addVertex((-unit), invUnit, (-unit));
			tessellator.addVertex((-unit), invUnit, (-unit));
			tessellator.addVertex((-unit), invUnit, unit);
			tessellator.addVertex(unit, invUnit, unit);
			tessellator.addVertex(unit, invUnit, (-unit));
			tessellator.draw();
		}

		GL11.glColor3f(cR, cG, cB);

		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, -((float) (adjHeight - 16.0D)), 0.0F);
		GL11.glCallList(glSkyList2);
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(true);
	}

}
