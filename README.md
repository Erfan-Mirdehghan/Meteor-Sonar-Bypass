# Sonar Bypass — Meteor Client Addon

An addon for [Meteor Client](https://meteorclient.com/) that automatically bypasses [Sonar Anti-Bot](https://github.com/jonesdevelopment/sonar) verification on Minecraft servers.

## ⚠️ Disclaimer

This project is intended for **educational purposes** and **local testing only**. Using it to bypass anti-bot protection on servers you do not own is against their terms of service and may result in a permanent ban. The author is not responsible for any misuse.

## ✨ Features

- **Automatic Bypass**: Handles Sonar's verification stages (Gravity, Protocol, Vehicle) automatically
- **Toggle Button**: A convenient ON/OFF button in the multiplayer server list screen
- **Auto-Disable**: Automatically disables itself when you disconnect from a server
- **Packet-level Handling**: Uses Mixins to intercept and respond to Sonar's verification packets in real-time

## 🎮 How to Use

1. Install [Meteor Client](https://meteorclient.com/) and [Fabric Loader](https://fabricmc.net/) for Minecraft **1.21.11**
2. Download the latest release of **Sonar Bypass** from [Releases](../../releases)
3. Drop the `.jar` file into your `.minecraft/mods/` folder
4. Launch Minecraft with the Fabric profile
5. Open the **Multiplayer** screen — you'll see a **Sonar Bypass: OFF** button in the bottom-right corner
6. Click it to turn it **ON**
7. Connect to any server protected by Sonar — the bypass will run automatically
8. The module will auto-disable when you leave the server

## 🛠️ Building from Source

```bash
git clone https://github.com/YOUR_USERNAME/Meteor-Sonar-Bypass.git
cd Meteor-Sonar-Bypass
./gradlew build
```

The compiled `.jar` will be in `build/libs/`.

## 📦 Requirements

- Minecraft **1.21.11**
- Fabric Loader **0.19.3+**
- Meteor Client **1.21.11-82+**
- Java **21**

## 🧩 How It Works

Sonar's verification pipeline consists of several stages:

| Stage | What Sonar Checks | How This Addon Bypasses |
|-------|-------------------|-------------------------|
| **Login** | KeepAlive, Client Brand, Settings | Handled by vanilla client |
| **Gravity** | Fall physics (`dy = (dy - 0.08) * 0.98`) | Handled by vanilla physics |
| **Protocol** | Transaction (Ping), SetHeldItem, Swing | Mixin intercepts packets and responds |
| **Vehicle** | Boat/Minecart movement | Handled by vanilla client |

The key bypass is in `ClientConnectionMixin`, which intercepts `UpdateSelectedSlotS2CPacket` and `EntityAnimationS2CPacket` packets and responds with the correct `UpdateSelectedSlotC2SPacket` and `HandSwingC2SPacket`.

## 📁 Project Structure

```
src/main/java/com/erfanmirdehghan/sonarbypass/
├── SonarBypassAddon.java          # Main addon entry point
├── modules/
│   └── SonarBypass.java           # The bypass module
└── mixins/
    ├── ClientConnectionMixin.java # Packet interception
    └── MultiplayerScreenMixin.java # Toggle button
```

## 🙏 Credits

- [Meteor Client](https://github.com/MeteorDevelopment/meteor-client) — the client this addon is built for
- [Sonar Anti-Bot](https://github.com/jonesdevelopment/sonar) — the anti-bot this addon bypasses
- [Sonar Bypass (Node.js)](https://github.com/SirYadav1/sonar-bypass) — reference implementation that inspired parts of this project

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

پلاگین Sonar با این ادان میگه قاپس