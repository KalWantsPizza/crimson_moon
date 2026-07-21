## Crimson River — Architect Design Document (Complete)

### Section 1: Core Environmental Identity
A warm, serene red-tinted river biome flowing through the Crimson Plains. Its still, reflective waters bring rare calm to the Crimson Moon. Inspired by the vanilla River biome but tinted crimson, maintaining a soft, tranquil tone.

### Section 2: Surface Composition
- **Shoreline:** Crimson-tinted grass with red sand patches.
- **Riverbed:** Gravel with red sand pockets.
- **Subsurface:** Netherrack.
- **Stone Layer:** Blackstone.
- **Underlayer:** Deepslate.
- **Fluid:** Water (default), aquifers disabled.

### Section 3: Vegetation & Features
- **Banks:** Crimson grass, small crimson fungi, warped roots (low–medium density).
- **Water’s Edge:** Sugar cane and rare bamboo chutes (low density).
- **Shallows:** Rare short seagrass.
- **Deeper Water:** Very rare kelp.
- **Shoreline Trees:** Uncommon jungle trees with crimson-tinted leaves.
- **Large Flora:** Rare huge crimson fungus.
- **Geological Features:** Rare blackstone boulders, occasional micro-islands, and common glow lichen on nearby cliffs.

### Section 4: Terrain & Noise Shape
- **Elevation:** ~Y50, lower than Crimson Plains (Y60–80).
- **Depth:** 4–8 blocks, basins down to Y45.
- **Width:** 8–16 blocks, broad and calm.
- **Slope:** Gentle, easily walkable.
- **Integration:** Transitions smoothly into Crimson Plains and Crimson Beach; bordered by Scorched Delta between it and Lava Rivers.
- **Hydrology:** Water only; slow, reflective current.

### Section 5: Entity & Spawn Ecology
- **Passive:** Chickens, sheep (uncommon), pigs (rare).
- **Aquatic/Ambient:** Cod, salmon (uncommon); squid (rare).
- **Special Passive:** Frogs (rare).
- **Hostile (surface):** Husks, spiders, skeletons (rare).
- **Hostile (underwater):** Drowned (rare).
- **Nether Crossovers:** Piglins (very rare, near Scorched Delta border).

### Section 6: Visual & Atmospheric Hooks
- **Lighting:** Soft, reflective; subdued shadows.
- **Fog:** Light crimson mist over water.
- **Particles:** Occasional ash drift or subtle mist.
- **Ambient Sound:** Overworld river ambience; calm wind and water.
- **Tone:** Peaceful, reflective, slightly eerie at night.

### Section 7: Integration & Adjacency
| Neighbor Biome | Transition Type | Purpose / Effect |
|------------------|------------------|------------------|
| **Crimson Plains** | Smooth elevation gradient | Natural banks and soft blending between grass and sand; shared crimson color palette ensures seamless transition. |
| **Crimson Beach** | Delta or widening event | Occurs where the river broadens into shallow pools or basins, providing sandy transition to open terrain or lakes. |
| **Scorched Delta** | Thermal and erosion boundary | Separates the water-based Crimson River system from the lava-based Lava Rivers; terrain becomes cracked, dark, and mineral-rich. |
| **Lava Rivers** | Distinct network beyond Scorched Delta | Prevents water–lava mixing while maintaining thematic coherence of “heat meets life.” |
| **Deepslate Peaks** | Subsurface proximity | Gradual descent toward darker geological zones; occasional cave openings along cliffs. |
| **Frozen Lava Tubes** | Vertical thermal inversion | Beneath or adjacent to colder underground caverns; contrasting ecosystems. |

### Section 8: Development Status & Next Steps
| Category | Status | Notes |
|-----------|---------|-------|
| **Core Concept** | ✅ Finalized | Warm, serene red-tinted river biome connecting Crimson Plains and other surface regions. |
| **Surface Composition** | ✅ Defined | Crimson grass and red sand banks; gravel, netherrack, blackstone, deepslate layering; water fluid, no aquifers. |
| **Vegetation & Features** | ✅ Finalized | Crimson grasses, bamboo and sugar cane, kelp, crimson-tinted jungle trees, huge crimson fungus, glow lichen. |
| **Terrain & Noise** | ✅ Balanced | Gentle slopes, wide channels, moderate erosion; elevation ~Y50; Scorched Delta buffer from lava systems. |
| **Entity Ecology** | ✅ Tuned | Chickens, sheep, pigs, frogs, aquatic life, minimal hostiles; calm and low-density ecosystem. |
| **Atmosphere** | ✅ Locked | Reflective water, light crimson fog, Overworld river ambience, subtle ash drift. |
| **Integration** | ✅ Finalized | Smooth transition to Crimson Plains, Beach, Delta, and subsurface biomes. |
| **Development Readiness** | ✅ Ready for implementation | All conceptual details validated for JSON creation under 1.21.1 schema. |

### Implementation To-Do
| Task | Priority | Notes |
|------|-----------|-------|
| **Biome JSON creation** | High | Define effects, temperature, downfall, and block palette in data/crimson_moon/worldgen/biome/crimson_river.json. |
| **Noise & Surface rules** | High | Integrate with Crimson Moon’s noise_settings file (disable aquifers, assign river band depth). |
| **Vegetation & Feature Registration** | Medium | Add custom biome tags for vegetation placement; reuse shared crimson flora and fungi features. |
| **Spawn rules** | Medium | Define biome modifiers for mob distribution as per Section 5. |
| **Testing & Tuning** | Medium | Check lighting, fog, and water tone in-game for balance with Crimson Plains. |

### Narrative Function
> The **Crimson River** represents the lifeblood of the Crimson Moon —  
> a rare sanctuary of water and motion amid still red plains,  
> reflecting the world’s glow in a calm, endless mirror.

