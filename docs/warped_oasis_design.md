## Warped Oasis — Architect Design Document (Complete, Surface Revision)

### Section 1: Core Environmental Identity
A rare **surface biome** on the Crimson Moon — the **Warped Oasis** is a luminous teal basin where life has reemerged under the crimson sky. Found only within Crimson Plains, Desert, or Badlands regions, these oases are small, open sanctuaries where heat and dryness meet moisture and renewal.

### Section 2: Surface Composition
- **Surface:** Warped Nylium, Tinted Moss Blocks, and Teal Grass Blocks forming glowing carpets.
- **Subsurface:** Netherrack — heat-fed substrate sustaining warped and moss vegetation.
- **Stone Layer:** Blackstone — volcanic crust anchoring the oasis basin.
- **Underlayer:** Deepslate — deep crustal foundation.
- **Fluid:** Water (teal-tinted) — bioluminescent and calm; fills central surface lake.
- **Additional Notes:** Rare magma blocks under the lake for soft bubbles; faint Shroomlight clusters near moss edges.

### Section 3: Vegetation & Features
- **Flora:** Warped Roots and Sprouts, Tinted Moss Carpet, Uncommon Bamboo Shoots, Small Warped Fungi, and Dead Bushes (teal variant).
- **Trees:** Rare Dark Oak Trees with softly glowing teal-tinted leaves.
- **Fungal Structures:** Huge Warped Fungus (rare) as natural landmarks near lake edges.
- **Decorative Flora:** Warped Wart Blocks embedded in moss.
- **Hydrological Features:** Large central teal lake (8–16 blocks across) surrounded by warped vegetation and mossy slopes.
- **Atmosphere:** Constant teal glow from flora and water; spores and mist particles drift slowly above surface.

### Section 4: Terrain & Noise Shape
- **Average Elevation:** Y60–Y70, matching surface terrain.
- **Depth Variation:** Shallow 3–6 block depression; smooth curves forming natural basins.
- **Lake Depth:** 4–6 blocks; reflective, bioluminescent teal water.
- **Basin Width:** 20–40 blocks.
- **Slope Type:** Gentle inner mossy slopes, red sand and nylium blending outward.
- **Generation Behavior:** Appears as small surface basins with smooth noise transitions; never cliffed or abrupt.
- **Integration:** Naturally replaces small patches of Crimson Plains or Desert terrain; teal light visible at a distance.

### Section 5: Entity & Spawn Ecology
- **Passive:** Glow Squid (common), Frogs (uncommon), Turtles (rare), Sheep (uncommon).
- **Special Passive:** Allay (very rare) — ethereal presence drifting above lake or near trees.
- **Hostile (surface):** None typical; biome is neutral and safe.
- **Hostile (underground):** Zombies, Skeletons, Spiders (rare) only in deep subsurface caves.
- **Edge Spawns:** Endermen (rare) along outer rim cracks.
- **Behavior:** Low-density, peaceful fauna; slow, reflective motion and soundscape harmony.

### Section 6: Visual & Atmospheric Hooks
- **Lighting:** Uniform teal bioluminescence (light 9–11) from flora and water.
- **Fog:** Light cyan-green mist hugging the basin.
- **Water:** Deep teal-blue glow with gentle reflections and luminous motes.
- **Particles:** Warped spores and mist wisps above vegetation.
- **Soundscape:** Soft wind, gentle water trickles, frog croaks, sheep bleats, Allay chimes.
- **Mood:** Serene and restorative — a visual and emotional break in the crimson world.

### Section 7: Integration & Adjacency (Surface Biome)
| Neighbor Biome | Transition Type | Purpose / Effect |
|----------------|-----------------|------------------|
| **Crimson Plains** | Smooth elevation dip / basin | Common origin; crimson grass fades into teal moss and warped nylium around the water’s edge. |
| **Vanilla Desert** | Moisture faultline blend | Red sand lightens to pale sandstone; the teal water contrasts with dry terrain. |
| **Vanilla Badlands** | Canyon-floor pocket | Appears within terracotta valleys; vibrant teal against orange cliffs. |

- **Biome Type:** Surface micro-biome.
- **Frequency:** Extremely rare (one per several thousand chunks).
- **Size:** Small (20–40 block diameter).
- **Elevation:** Y60–Y70.
- **Water Behavior:** Always includes one central teal lake.  
- **Noise Shape:** Gentle circular basin with smooth red sand → moss → nylium transition.  
- **Visual Integration:** Teal vegetation visible from afar; particles mark boundaries.  
- **World Role:** The visible sign of balance — life reborn under the crimson sky.

### Section 8: Development Status & Next Steps
| Category | Status | Notes |
|-----------|---------|-------|
| **Core Concept** | ✅ Finalized | Rare surface oasis biome of teal renewal. |
| **Surface Composition** | ✅ Defined | Warped nylium, teal moss, and water basin on volcanic crust. |
| **Vegetation & Features** | ✅ Finalized | Bamboo, teal-leaf dark oaks, fungi, central glowing lake. |
| **Terrain & Noise** | ✅ Balanced | Smooth shallow basins appearing within arid zones. |
| **Entity Ecology** | ✅ Tuned | Peaceful fauna; no hostile presence. |
| **Atmosphere** | ✅ Locked | Teal mist, spores, calm ambient hum. |
| **Integration** | ✅ Finalized | Appears only within Crimson Plains, Desert, and Badlands. |
| **Development Readiness** | ✅ Ready for JSON | Schema-safe for 1.21.1 worldgen.

### Implementation To-Do
- Create biome JSON at `data/crimson_moon/worldgen/biome/warped_oasis.json`.
- Set surface rules for red sand and warped nylium blend.  
- Add water color override (teal glow) and ambient fog.  
- Configure spawn modifiers for Allay, Frogs, Sheep, and Glow Squid.  
- Verify natural blending with Crimson Plains, Desert, and Badlands terrain.

### Narrative Function
> The **Warped Oasis** is the Crimson Moon’s visible heartbeat —  
> a living pool of teal light where crimson heat gives way to calm.  
> Here, life is not hidden — it flourishes under the open sky, a promise that the world endures.

