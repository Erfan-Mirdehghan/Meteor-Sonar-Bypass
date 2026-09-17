package com.erfanmirdehghan.sonarbypass;

import com.erfanmirdehghan.sonarbypass.modules.SonarBypass;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SonarBypassAddon extends MeteorAddon {
    public static final Logger LOG = LoggerFactory.getLogger("SonarBypass");

    @Override
    public void onInitialize() {
        LOG.info("Fuck Sonar.");
        Modules.get().add(new SonarBypass());
    }

    @Override
    public String getPackage() {
        return "com.erfanmirdehghan.sonarbypass";
    }
}