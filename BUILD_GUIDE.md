# How to Make a Mod Using This Mod as a Base

## 1. Download and extract the recommended Minecraft Forge - MC 1.21.4 MDK

[https://files.minecraftforge.net/net/minecraftforge/forge/index\_1.21.4.html](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.21.4.html)

## 2. Install Java 21 JDK if you don't already have it

```sh
winget install EclipseAdoptium.Temurin.21.JDK
```

## 3. Download and Install Eclipse

[https://www.eclipse.org/downloads/](https://www.eclipse.org/downloads/)

## 4. Set up your project

Copy just the contents of the extracted `forge-1.21.4-54.1.0-mdk` folder into your project directory.

Then, **Shift + Right Click** inside the folder and select **"Open command window here."** Run:

```sh
gradlew genEclipseRuns
```

## 5. Generate Eclipse project files

```sh
gradlew eclipse
```

## 6. Add the base mod files

Download the GitHub repo and paste just the contents of the folder into your project directory.
This will replace some existing files — that’s okay.

## 7. Test that everything works

Go into your project directory, **Shift + Right Click**, and select **"Open command window here."** Run:

```sh
gradlew runClient
```

## 8. Build your mod

When you're happy with your mod, open a command window in your project directory and run:

```sh
gradlew build
```

## 9.  To find the compiled mod JAR

Go to:

```
build/libs/
```

Place the `.jar` file in your Minecraft `mods` folder:
Press `Windows key + R`, type `%appdata%`, and press Enter.
Navigate to `.minecraft/mods` (create the folder if it doesn’t exist).

## 10. Create a new Minecraft 1.21.4 installation

Launch Minecraft, create a new installation using version 1.21.4, and enter a world at least once.

## 11. Install Minecraft Forge 1.21.4

Download and run the recommended installer from:

[https://files.minecraftforge.net/net/minecraftforge/forge/index\_1.21.4.html](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.21.4.html)


