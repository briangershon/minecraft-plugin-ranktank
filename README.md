# RankTank

A Minecraft plugin for assigning players a rank and showing top ranks on a scoreboard.

    /ranktank <playerName> <newRank>

`<playerName>` is an valid Minecraft player name. `<newRank>` can be any one-word value (no spaces).

However if your rank is on the following list, it will be color-coded and sorted from highest to lowest.

Ranks are:

1. MVP++ (highest rank)
2. MVP+
3. MVP
4. Crafty+
5. Crafty
6. VIP+
7. VIP
8. Member (default)
9. (any other text not on the list) (lowest rank)

Notes:

- All players joining the server will start at the `Member` rank.
- Player data is saved on the server in a custom YAML file.
- What server permissions are needed to run the `/ranktank` command? Player needs to have the `ranktank.set` permission, which is on by default for Operators.

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
