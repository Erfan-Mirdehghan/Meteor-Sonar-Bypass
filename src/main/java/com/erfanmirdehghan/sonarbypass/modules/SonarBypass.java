package com.erfanmirdehghan.sonarbypass.modules;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public class SonarBypass extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> autoDisableOnDisconnect = sgGeneral.add(new BoolSetting.Builder()
        .name("auto-disable-on-disconnect")
        .description("Automatically disables when you disconnect from a server")
        .defaultValue(true)
        .build()
    );

    private boolean wasConnected = false;

    public SonarBypass() {
        super(Categories.Misc, "sonar-bypass", "Bypasses Sonar Anti-Bot verification");
    }

    @Override
    public void onActivate() {
        info("Sonar Bypass: ON");
        wasConnected = MinecraftClient.getInstance().getNetworkHandler() != null;
    }

    @Override
    public void onDeactivate() {
        info("Sonar Bypass: OFF");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!autoDisableOnDisconnect.get()) return;

        MinecraftClient mc = MinecraftClient.getInstance();
        boolean isConnected = mc.getNetworkHandler() != null;

        // اگر قبلاً وصل بودیم و الان قطع شده‌ایم → خاموش کن
        if (wasConnected && !isConnected) {
            info("Disconnected from server - disabling Sonar Bypass");
            toggle();
            return;
        }

        wasConnected = isConnected;
    }

    public boolean shouldAutoDisableOnDisconnect() {
        return autoDisableOnDisconnect.get();
    }
}