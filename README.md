# Athena - A Minecraft Modding Sandbox

This is a minecraft mod for (re-) learning modding. 
It does not really follow any target or theme,
it's just a bunch of basic things and experiments,
hence named after the greek goddess of knowledge.

## Contents

A list of features this mod adds in a very technical terminology.
The purpose of these features is, to learn the corresponding mechanics.
Actual in-game functionality of these are explained when implemented.

This is losely based on [Minecraft by Example](https://github.com/TheGreyGhost/MinecraftByExample),
 a truly amazing resource when it comes to modding.

### Blocks
 
 - [x] A simple cuboid block -- Yer standard block
 - [x] A block with custom model
 - [ ] Block interacting with redstone
 - [x] A block whose placing is facing based (i.e. like log)
 - [ ] A multi-block block (like the bed / door)
 - [x] A fence-ish block: Non-cuboid, neighbour sensitive
 
### Items
 
 - [x] A simple item -- Yer standard item
 - [ ] A wrench, an item which interacts with stuff
 - [x] Area of effect mining tool (hammer)
 - [ ] Tools -- Yer standard minecraft tools
 - [ ] Item with inventory (e.g. backpack)
 
### TileEntities

 - [ ] A TE without inventory, but creating something (e.g. a auto-fisher)
 - [ ] A TE with inventory, (e.g. a chest)
 - [ ] A TE generating Forge Energy
 - [ ] A TE using FE and doing stuff
 - [ ] TE interacting with blocks
   - [ ] TE removing blocks (i.e. block breaker)
   - [ ] TE adding blocks (i.e. block placer)
   
 
### Entities

 - [ ] A _drivable_ thing, e.g. something like a boat for land
 - [ ] A custom golem, built like the snowgolem
  
### Crafting

 - [x] Standard crafting recipes (shaped and shapeless)
 - [x] Smelting recipes
 - [x] Additional fuel
 - [ ] Custom Recipes (whatever this will be)
   - [x] Cauldron Recipe (i.e. rightclick w/ block results in stuff)
   - [ ] In-world crafting

### Misc

 - [ ] Multiblock structure
 - [ ] JEI Integration:
    - [x] Dev Setup
    - [ ] Plugin w/ custom recipes 
 - [ ] Event based things to integrate into vanilla
 - [ ] Dependency to other mod and usage of other mod
 
## Software Architecture

Another goal of this mod is to come up with as much well written
architecture and processes to facilitate an ecosystem for future modding
ambitions.

- Registries: Registering simple blocks has lots of boilerplate code

 -[x] Generic Block Registry
 -[x] Generic Item Registry
 -[ ] Tool Registry
 
- Processes / Tools: Very similar external files might be created in an automated way (e.g. gradle tasks)

 -[ ] Process to create external files for simple blocks
 -[ ] Process to create external files for simple items

## Credits

Without the awesome people working on forge, this would not be possible.

Documentation and Code of these resources have contributed (in no particular order):

 - [Forge Docs](https://mcforge.readthedocs.io/en/1.15.x/)
 - [Cadiboo's Example Mod](https://github.com/Cadiboo/Example-Mod)
 - [Minecraft by Example](https://github.com/TheGreyGhost/MinecraftByExample)
 - [MrCrayfish Model Creator](https://mrcrayfish.com/tools?id=mc)
 - [MrCrayfish](https://github.com/MrCrayfish)
 - [Mekanism](https://github.com/mekanism/Mekanism)

## Contributors

 - PickaxeEngineer
 - SphereArchitect
 - Anonymous Coder
 

## License

This work is licensed as GNU GPLv3. See LICENSE for the full license text.
