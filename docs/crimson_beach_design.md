## Crimson Beach — Architect Design Document (Complete)

### Section 1: Core Environmental Identity
A tranquil cooling shoreline biome where the red sands of the Crimson Moon meet the calm waters of the Warm Ocean. Soft crimson tones and warm mist define the atmosphere. It represents recovery — the transition from heat and desolation to balance and life.

### Section 2: Surface Composition
- **Surface (Shoreline):** Red Sand dominant, small amounts of vanilla Sand near the waterline.
- **Inland:** Crimson-tinted Grass Blocks forming gentle vegetated dunes.
- **Underwater (Shallow Floor):** Red Sand and Gravel, occasional Vanilla Sand.
- **Subsurface:** Netherrack with scattered Basalt.
- **Stone Layer:** Blackstone and Stone — cooling crust beneath the sediment.
- **Underlayer:** Deepslate.
- **Fluid:** Water (vanilla) — faintly tinted near shore, blending to Warm Ocean hue.

### Section 3: Vegetation & Features
- **Flora:** Crimson-tinted Tall Grass, Small Crimson Fungi, Dead Bushes inland; Dead Bushes and rare Sugar Cane along the red sand edge.
- **Underwater Flora:** Seagrass (uncommon), Kelp (rare), Coral Fans (Warm Ocean crossover).
- **Geological Features:** Red Sand Dunes (common), Basalt Pebbles (occasional), Magma Pebbles (rare faint glow), Driftwood (future conceptual feature).
- **Visual Tone:** Soft motion, sparse life, warm reflection — signs of recovery and calm.

### Section 4: Terrain & Noise Shape
- **Elevation:** ~Y61, smooth and sea-level.
- **Variation:** Low; gentle undulation and rolling dunes.
- **Erosion:** Moderate; wind-sculpted and soft.
- **Shore Slope:** Gradual; 5–10 block gradient from grass to sand to water.
- **Underwater Slope:** Smooth descent; red sand and gravel blend with vanilla ocean floor.
- **Integration:** Naturally transitions to Crimson Plains, Rivers, Scorched Delta, Warm Ocean, Desert, and Badlands.
- **Special Features:** Dune lines, tidal flats, rare basalt outcrops, cosmetic steam wisps.

### Section 5: Entity & Spawn Ecology
- **Passive:** Chickens (common), Sheep (uncommon), Pigs (rare).
- **Coastal Wildlife:** Turtles (uncommon), Frogs (rare).
- **Aquatic:** Cod, Salmon (uncommon), Squid (rare); Tropical Fish, Pufferfish, Dolphins (rare, Warm Ocean overlap).
- **Hostile (surface):** Zombies, Skeletons, Spiders (rare).
- **Hostile (underwater):** Drowned (rare).
- **Edge Spawns:** Husks (rare) along Desert/Badlands borders.
- **Tone:** Peaceful recolonization; the moon’s gentlest ecosystem.

### Section 6: Visual & Atmospheric Hooks
- **Sky Color:** Pale crimson to rose-gold gradient.
- **Fog:** Light pinkish mist; warm and clear.
- **Water:** Rust-red near shore, turning turquoise toward Warm Ocean.
- **Particles:** Light ash drift, faint mist near waterline.
- **Soundscape:** Gentle waves, warm wind, soft animal ambience; faint echoes of distant Crimson River hum.
- **Mood:** Safe, serene, wistful — the moon’s sigh of peace.

### Section 7: Integration & Adjacency
| Neighbor Biome | Transition Type | Purpose / Effect |
|------------------|------------------|------------------|
| **Crimson Plains** | Elevation gradient | Red sand rises to crimson grass. |
| **Crimson River** | Shared water level | Smooth sediment and color blending. |
| **Scorched Delta** | Cooling boundary | Basalt fades to sand with blackstone debris. |
| **Lava Rivers** | Rare vertical merge | Ancient cooled flows meet sediment. |
| **Warm Ocean (vanilla)** | Direct merge | Red-tinted water fades to blue; coral and kelp carry over. |
| **Vanilla Desert** | Temperature blend | Red sand lightens to standard sand and sandstone. |
| **Vanilla Badlands** | Erosion slope | Red sand merges with terracotta layers. |
| **Deepslate Peaks (underground)** | Subsurface contact | Geological link through basalt inclusions. |

### Section 8: Development Status & Next Steps
| Category | Status | Notes |
|-----------|---------|-------|
| **Core Concept** | ✅ Finalized | Cooling shoreline bridging crimson terrain and Warm Ocean. |
| **Surface Composition** | ✅ Defined | Red sand and crimson grass with volcanic crust beneath. |
| **Vegetation & Features** | ✅ Finalized | Sparse flora, dunes, coral, kelp, and seagrass. |
| **Terrain & Noise** | ✅ Balanced | Gentle slopes and smooth transitions. |
| **Entity Ecology** | ✅ Tuned | Peaceful coastal fauna with rare hostiles. |
| **Atmosphere** | ✅ Locked | Rose-gold light, mist, and soft soundscape. |
| **Integration** | ✅ Finalized | Connects to all crimson and vanilla coastal/arid biomes. |
| **Development Readiness** | ✅ Ready for JSON | Schema-safe design validated for 1.21.1 worldgen.

### Implementation To-Do
- Create biome JSON in `data/crimson_moon/worldgen/biome/crimson_beach.json`.
- Integrate into noise_settings (shoreline generation bias near water level).
- Configure vegetation, coral, and kelp features.
- Apply passive and aquatic spawn modifiers.
- Validate fog, lighting, and sediment blending into Warm Ocean.

### Narrative Function
> The **Crimson Beach** is the Crimson Moon’s horizon —  
> where heat yields to life, and red sand sighs into blue water.  
> It is peace born of fire — the edge where the world begins to heal.

