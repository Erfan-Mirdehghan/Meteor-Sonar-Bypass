package com.erfanmirdehghan.sonarbypass.mixins;

import com.erfanmirdehghan.sonarbypass.modules.SonarBypass;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public abstract class MultiplayerScreenMixin extends Screen {

    private ButtonWidget sonarToggleButton;

    protected MultiplayerScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        SonarBypass bypass = Modules.get().get(SonarBypass.class);
        if (bypass == null) return;

        this.sonarToggleButton = ButtonWidget.builder(
            getToggleText(bypass),
            button -> {
                bypass.toggle();
                button.setMessage(getToggleText(bypass));
            }
        ).dimensions(this.width - 110, this.height - 28, 100, 20).build();

        this.addDrawableChild(this.sonarToggleButton);
    }

    private Text getToggleText(SonarBypass bypass) {
        return Text.literal(
            "Sonar Bypass: " + (bypass.isActive() ? "§aON" : "§cOFF")
        );
    }
}