package mirror.normalasm.client.mcfixes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import mirror.normalasm.NormalLogger;
import mirror.normalasm.common.internal.mixins.TextureManagerAccessor;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class SkinDataReleaser {

    @SubscribeEvent
    public static void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            Map<ResourceLocation, ITextureObject> textureObjects = ((TextureManagerAccessor) Minecraft.getMinecraft().getTextureManager()).getMapTextureObjects();
            if (textureObjects != null) {
                int count = 0;
                Iterator<Entry<ResourceLocation, ITextureObject>> entryIter = textureObjects.entrySet().iterator();
                while (entryIter.hasNext()) {
                    Entry<ResourceLocation, ITextureObject> entry = entryIter.next();
                    if (entry.getValue() instanceof ThreadDownloadImageData) {
                        if (entry.getKey().getPath().startsWith("skins/")) {
                            ((ThreadDownloadImageData) entry.getValue()).deleteGlTexture();
                            entryIter.remove();
                            NormalLogger.instance.debug("Released {} texture", entry.getKey());
                            count++;
                        }
                    }
                }
                NormalLogger.instance.info("Released {} skin textures", count);
            }
        });
    }

}
