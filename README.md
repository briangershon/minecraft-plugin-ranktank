# RankTank

A Minecraft plugin for assigning players a rank and showing top ranks on a scoreboard.

    /ranktank <playerName> <newRank>

All players joining the server will start at the `Member` rank.

Ranks are (highest rank listed first):

1. MVP++
2. MVP+
3. MVP
4. VIP+
5. VIP
6. Member (default)

Player data is saved on the server in a custom YAML file.

To run `/ranktank` a player needs to have the `ranktank.set` permission, which is on for Operators.

## Which server API does this plugin run on?

Supports API version 1.13 or higher.

Tested plugin on these servers:
* Spigot 1.16.4-R0.1-SNAPSHOT (compile with Java 14 on MacOS) when testing plugin locally.
* Paper 1.16.4 (compiled with Java 8) running on a paid hosting service using an older version of Java.

## Development Environment Setup and Workflow

If you haven't create a plugin before, you'll need to setup your local development environment and understand the compile and test workflow. I've created a [Minecraft Plugin Development Guide](https://gist.github.com/briangershon/7a009cad2a1e11a7b785e8b8bf6ada1a) to cover this.

## To Release Plugin

Make sure you first update the plugin version in `pom.xml` in `<version>1.0.0</version>`.

    mvn clean package

You should now have your new plugin jar file in `target` folder.

## To install on your Spigot compatable Minecraft Server

Copy `target/RankTankPlugin-n.n.n.jar` to your server `/plugin` folder, and reload server configuration (or just restart server).

You should see these two messages in your server console:

```
[11:50:26] [Server thread/INFO]: [PluginDemo] Enabling PluginDemo v0.0.1
[11:50:26] [Server thread/INFO]: [PluginDemo] Hello, SpigotMC!
```
