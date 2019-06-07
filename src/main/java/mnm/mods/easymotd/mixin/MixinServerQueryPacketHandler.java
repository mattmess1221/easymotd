package mnm.mods.easymotd.mixin;

import mnm.mods.easymotd.QueryListener;
import net.minecraft.client.network.packet.QueryResponseS2CPacket;
import net.minecraft.server.ServerMetadata;
import net.minecraft.server.network.ServerQueryNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerQueryNetworkHandler.class)
public class MixinServerQueryPacketHandler {

    @Redirect(method = "onRequest", at = @At(value = "NEW", target = "net/minecraft/client/network/packet/QueryResponseS2CPacket"))
    private QueryResponseS2CPacket getMetadata(ServerMetadata metadata) {
        QueryListener.EVENT.invoker().onQuery(metadata);
        return new QueryResponseS2CPacket(metadata);
    }
}
