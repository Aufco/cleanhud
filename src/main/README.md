# Clean HUD

A fully configurable HUD mod for Minecraft 1.21.4 Forge that displays useful information on screen.

## Features

- **9 HUD Elements**:
  - Block coordinates (X Y Z)
  - FPS counter
  - Chunk coordinates
  - Facing direction
  - Light level
  - Current biome
  - Memory usage
  - What you're looking at (with coordinates)
  - Player movement speed

- **Full Customization**:
  - Toggle any element on/off
  - Customize all labels
  - Reorder elements by editing config list

## Configuration

The mod creates a configuration file where you can:

1. **Enable/Disable Elements**: Turn any HUD element on or off
2. **Custom Labels**: Change the text label for each element
3. **Display Order**: Reorder elements by modifying the `elementOrder` list in config

### Example Configuration

```toml
[HUD Elements]
showCoordinates = true
showFPS = true
showMemory = false

[Labels]
coordinatesLabel = "Pos:"
fpsLabel = "Frames:"
memoryLabel = "RAM:"

[Display Order]
elementOrder = ["coordinates", "fps", "biome", "facing"]
```

## Example Display

```
Block: 123 64 -275
FPS: 92
Chunk: 7 4 -18
Facing: South
Light: 15
Biome: plains
Mem: 512 MB / 2048 MB
Looking at: Stone (123, 63, -275)
Player Speed: 4.37 m/s
```

## Installation

1. Download Minecraft Forge 1.21.4
2. Place the mod JAR in your `mods` folder
3. Launch Minecraft
4. Configure via Forge config menu or edit config file

## Author

Created by AufCo