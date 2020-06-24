package best.reich.ingrosware.util.camera;

import best.reich.ingrosware.mixin.accessors.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;

/**
 * made for Ingros
 *
 * @author Brennan
 * @since 6/23/2020
 **/
public class BaseCamera {
    private boolean capturing, reflected;
    private Framebuffer frameBuffer;

    private int width, height;

    private float cameraRotationYaw, cameraRotationPitch;
    private double cameraPosX, cameraPosY, cameraPosZ;

    public Minecraft mc = Minecraft.getMinecraft();

    public BaseCamera(boolean reflected, int width, int height) {
        this.reflected = reflected;
        this.width = width;
        this.height = height;

        frameBuffer = new Framebuffer(width, height, true);
        frameBuffer.createFramebuffer(width, height);
    }

    private void setCapture(boolean capture) {
        if (capture)
            frameBuffer.bindFramebuffer(true);
        else
            frameBuffer.unbindFramebuffer();

        this.capturing = capture;
    }

    public void updateFramebuffer() {
        if (capturing || !mc.inGameHasFocus) {
            return;
        }

        final Entity renderViewEntity = ((IMinecraft) mc).getRenderViewEntity();

        int displayWidth = mc.displayWidth;
        int displayHeight = mc.displayHeight;
        int thirdPersonView = mc.gameSettings.thirdPersonView;
        float rotationYaw = renderViewEntity.rotationYaw;
        float prevRotationYaw = renderViewEntity.prevRotationYaw;
        float rotationPitch = renderViewEntity.rotationPitch;
        float prevRotationPitch = renderViewEntity.prevRotationPitch;
        boolean hideGUI = mc.gameSettings.hideGUI;
        boolean viewBobbing = mc.gameSettings.viewBobbing;

        double posX = renderViewEntity.posX;
        double prevPosX = renderViewEntity.prevPosX;
        double lastTickPosX = renderViewEntity.lastTickPosX;

        double posY = renderViewEntity.posY;
        double prevPosY = renderViewEntity.prevPosY;
        double lastTickPosY = renderViewEntity.lastTickPosY;

        double posZ = renderViewEntity.posZ;
        double prevPosZ = renderViewEntity.prevPosZ;
        double lastTickPosZ = renderViewEntity.lastTickPosZ;

        renderViewEntity.posX = cameraPosX;
        renderViewEntity.prevPosX = cameraPosX;
        renderViewEntity.lastTickPosX = cameraPosX;

        renderViewEntity.posY = cameraPosY;
        renderViewEntity.prevPosY = cameraPosY;
        renderViewEntity.lastTickPosY = cameraPosY;

        renderViewEntity.posZ = cameraPosZ;
        renderViewEntity.prevPosZ = cameraPosZ;
        renderViewEntity.lastTickPosZ = cameraPosZ;

        mc.displayWidth = width;
        mc.displayHeight = height;
        renderViewEntity.rotationYaw = cameraRotationYaw;
        renderViewEntity.prevRotationYaw = cameraRotationYaw;
        renderViewEntity.rotationPitch = cameraRotationPitch;
        renderViewEntity.prevRotationPitch = cameraRotationPitch;
        mc.gameSettings.thirdPersonView = 0;
        mc.gameSettings.viewBobbing = false;
        mc.gameSettings.hideGUI = true;

        setCapture(true);

        mc.entityRenderer.updateCameraAndRender(mc.timer.renderPartialTicks, System.nanoTime());

        setCapture(false);

        mc.displayWidth = displayWidth;
        mc.displayHeight = displayHeight;
        renderViewEntity.rotationYaw = rotationYaw;
        renderViewEntity.prevRotationYaw = prevRotationYaw;
        renderViewEntity.rotationPitch = rotationPitch;
        renderViewEntity.prevRotationPitch = prevRotationPitch;
        mc.gameSettings.thirdPersonView = thirdPersonView;
        mc.gameSettings.hideGUI = hideGUI;
        mc.gameSettings.viewBobbing = viewBobbing;

        renderViewEntity.posX = posX;
        renderViewEntity.prevPosX = prevPosX;
        renderViewEntity.lastTickPosX = lastTickPosX;

        renderViewEntity.posY = posY;
        renderViewEntity.prevPosY = prevPosY;
        renderViewEntity.lastTickPosY = lastTickPosY;

        renderViewEntity.posZ = posZ;
        renderViewEntity.prevPosZ = prevPosZ;
        renderViewEntity.lastTickPosZ = lastTickPosZ;
    }

    public void draw(double x, double y, double x1, double y1) {
        GlStateManager.pushMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableAlpha();

        GlStateManager.disableBlend();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        frameBuffer.bindFramebufferTexture();

        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferBuilder = tessellator.getBuffer();

        if (reflected) {
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.pos(x, y1, 0.0D).tex(1D, 0.0D).color(255, 255, 255, 255).endVertex();
            bufferBuilder.pos(x1, y1, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
            bufferBuilder.pos(x1, y, 0.0D).tex(0.0D, 1D).color(255, 255, 255, 255).endVertex();
            bufferBuilder.pos(x, y, 0.0D).tex(1D, 1D).color(255, 255, 255, 255).endVertex();
            tessellator.draw();
        } else {
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.pos(x, y1, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
            bufferBuilder.pos(x1, y1, 0.0D).tex(1D, 0.0D).color(255, 255, 255, 255).endVertex();
            bufferBuilder.pos(x1, y, 0.0D).tex(1D, 1D).color(255, 255, 255, 255).endVertex();
            bufferBuilder.pos(x, y, 0.0D).tex(0.0D, 1D).color(255, 255, 255, 255).endVertex();
            tessellator.draw();
        }

        GlStateManager.popMatrix();
        frameBuffer.unbindFramebufferTexture();
    }

    public void setCameraPosX(double cameraPosX) {
        this.cameraPosX = cameraPosX;
    }

    public void setCameraPosY(double cameraPosY) {
        this.cameraPosY = cameraPosY;
    }

    public void setCameraPosZ(double cameraPosZ) {
        this.cameraPosZ = cameraPosZ;
    }

    protected void setCameraRotationPitch(float cameraRotationPitch) {
        this.cameraRotationPitch = cameraRotationPitch;
    }

    protected void setCameraRotationYaw(float cameraRotationYaw) {
        this.cameraRotationYaw = cameraRotationYaw;
    }

    protected void setToEntityPosition(Entity e) {
        cameraPosX = e.lastTickPosX - (e.lastTickPosX - e.posX) * mc.timer.elapsedPartialTicks;
        cameraPosY = e.lastTickPosY - (e.lastTickPosY - e.posY) * mc.timer.elapsedPartialTicks;
        cameraPosZ = e.lastTickPosZ - (e.lastTickPosZ - e.posZ) * mc.timer.elapsedPartialTicks;
    }
}
