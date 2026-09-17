package com.erfanmirdehghan.sonarbypass.mixins;

import com.erfanmirdehghan.sonarbypass.modules.SonarBypass;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    @Inject(method = "channelRead0", at = @At("HEAD"))
    private void onPacketReceive(ChannelHandlerContext context, Packet<?> packet, CallbackInfo ci) {
        SonarBypass bypass = Modules.get().get(SonarBypass.class);
        if (bypass == null || !bypass.isActive()) return;

        ClientConnection connection = (ClientConnection) (Object) this;

        // Stage 1: UpdateSelectedSlot -> echo back the SAME slot
        if (packet instanceof UpdateSelectedSlotS2CPacket slotPacket) {
            int slot = slotPacket.slot();
            if (slot >= 0 && slot <= 8) {
                connection.send(new UpdateSelectedSlotC2SPacket(slot));
            }
        }

        // Stage 2: EntityAnimation -> reply with swing
        if (packet instanceof EntityAnimationS2CPacket) {
            connection.send(new HandSwingC2SPacket(Hand.MAIN_HAND));
        }
    }

    // وقتی کانال بسته می‌شه (Disconnect یا Kick)
    @Inject(method = "channelInactive", at = @At("HEAD"))
    private void onChannelInactive(ChannelHandlerContext context, CallbackInfo ci) {
        SonarBypass bypass = Modules.get().get(SonarBypass.class);
        if (bypass != null && bypass.isActive() && bypass.shouldAutoDisableOnDisconnect()) {
            bypass.toggle();
        }
    }
}