## Scorched Delta — Architect Design Document (Complete)

### Section 1: Core Environmental Identity
A flat, volcanic-glassy delta biome acting as the cooling edge between Crimson Rivers and Lava Rivers. The terrain consists of hardened basalt and blackstone plates interspersed with magma fissures and obsidian crusts. The air is dense with ash and ember drift, illuminated by lava glow beneath the surface.

### Section 2: Surface Composition
- **Surface:** Basalt, Blackstone, and patchy Magma Blocks.
- **Subsurface:** Netherrack with scattered Granite veins.
- **Stone Layer:** Blackstone with Obsidian inclusions.
- **Underlayer:** Deepslate.
- **Fluid:** Lava (no aquifers).
- **Notes:** Occasional crimson nylium patches on cooled basalt near biome edges.

### Section 3: Vegetation & Features
Sparse flora and strong geological presence:
- **Flora:** Crimson roots, warped roots, occasional nether sprouts; rare dead bushes and crimson fungi on cooler shelves.
- **Geological Features:** Basalt columns (common), magma fissures (uncommon), obsidian pools (rare), steam vents (rare), lava seep patches (uncommon), blackstone boulders (common), crimson nylium outcrops (rare).
- **Visual Tone:** Red-orange light sources from magma fissures contrast against matte blackstone and basalt surfaces.

### Section 4: Terrain & Noise Shape
- **Elevation:** ~Y58 average; flat with shallow fissures and gentle bowls.
- **Variation:** Low; smooth planar surface with occasional cracks.
- **Erosion:** Minimal; terrain appears hardened and fused.
- **Peaks / Craters:** Rare, small bowls or vents.
- **Integration:** Smooth transitions into Crimson River (ash-covered slopes), Lava Rivers (active flows), and Crimson Plains (nylium blend zones).
- **Hydrology:** Lava replaces water pockets; no aquifers.

### Section 5: Entity & Spawn Ecology
- **Passive:** None typical.
- **Ambient / Neutral:** Endermen (uncommon).
- **Hostile (surface):** Magma Cubes (uncommon), Zombified Piglins (uncommon).
- **Hostile (underground):** Skeletons, Zombies, Spiders (rare).
- **Edge Spawns:** Piglins (rare), Hoglins (rare) near Lava River borders.
- **Notes:** Low overall density; atmosphere-driven tension over combat.

### Section 6: Visual & Atmospheric Hooks
- **Lighting:** Red-orange ambient emission from magma; harsh contrast shadows.
- **Fog:** Burnt orange-gray; denser near fissures.
- **Sky:** Deep maroon-red with ash haze.
- **Particles:** Constant ash drift, occasional flame embers.
- **Sounds:** Basalt Deltas ambient loop; rumbling wind and lava pops.
- **Mood:** Oppressive, smoldering, yet hauntingly beautiful.

### Section 7: Integration & Adjacency
| Neighbor Biome | Transition Type | Purpose / Effect |
|------------------|------------------|------------------|
| **Crimson River** | Thermal gradient slope | Basalt shelves and steam at the waterline; visual shift from red reflection to glowing rock. |
| **Lava Rivers** | Direct volcanic merge | Seamless continuation into molten terrain. |
| **Crimson Plains** | Elevation soft blend | Basalt to nylium and sand transition. |
| **Deepslate Peaks** | Subsurface connection | Lava tubes and fissures link to deeper crust. |
| **Crimson Beach** | Rare lateral merge | Cooling basins with basalt sand edges. |
| **Frozen Lava Tubes** | Vertical inversion | Contrasts cold depths beneath hot crust. |

### Section 8: Development Status & Next Steps
| Category | Status | Notes |
|-----------|---------|-------|
| **Core Concept** | ✅ Finalized | Volcanic-glassy delta bridging lava and river systems. |
| **Surface Composition** | ✅ Defined | Basalt/Blackstone crust with magma, netherrack, granite, obsidian, and crimson nylium accents. |
| **Vegetation & Features** | ✅ Finalized | Sparse flora; strong geological features. |
| **Terrain & Noise** | ✅ Balanced | Flat and fissured; stable and walkable. |
| **Entity Ecology** | ✅ Tuned | Sparse, heat-adapted mobs only. |
| **Atmosphere** | ✅ Locked | Ash haze, ember drift, basalt deltas ambience, red fog. |
| **Integration** | ✅ Finalized | Thermal bridge between Crimson River, Lava Rivers, and Plains. |
| **Development Readiness** | ✅ Ready for JSON implementation | Design validated against 1.21.1 schema. |

### Implementation To-Do
- Define biome JSON in `data/crimson_moon/worldgen/biome/scorched_delta.json`.
- Integrate surface rules and noise settings (lava-focused).
- Register basalt and magma features.
- Configure spawn modifiers for low-density heat-tolerant mobs.
- Validate lighting, fog, and sound consistency with Crimson River transitions.

### Narrative Function
> The **Scorched Delta** is the world’s forge —  
> where the lifeblood of rivers cools into stone and magma breathes beneath the surface.  
> It is the scar where fire and life meet,  
> glowing forever in the crimson dusk.

