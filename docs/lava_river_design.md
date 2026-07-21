## Lava River — Architect Design Document (Complete)

### Section 1: Core Environmental Identity
A flowing molten river biome forming the fiery arteries of the Crimson Moon. Vast, slow-moving lava channels carve through basalt valleys, illuminating the world with deep orange glow and drifting ash. The biome embodies motion, danger, and beauty—the planet’s molten heart made visible.

### Section 2: Surface Composition
- **Fluid Channel (Core):** Lava (stationary + flowing); 4–8 blocks deep.
- **Riverbed / Crust Edges:** Magma Blocks, Smooth Basalt, Blackstone.
- **Outer Bank (Stabilized Zone):** Basalt, Netherrack, Red Sand patches (ash-fused sediment).
- **Subsurface:** Netherrack with Granite streaks.
- **Underlayer:** Deepslate.
- **Notes:** No aquifers; heat gradient transitions from molten core to cooler red sand banks.

### Section 3: Vegetation & Features
- **Flora (Cooler Banks Only):** Dead bushes, Crimson roots, Nether sprouts, small crimson fungi (very rare).
- **Geological Features:** Lavafalls (common), magma fissures (common), basalt columns (uncommon), gas vents (rare), natural basalt bridges (rare).
- **Visual Tone:** Constant motion and light; edges dotted with scorched remnants of life.

### Section 4: Terrain & Noise Shape
- **Elevation:** ~Y48 average, lowest surface biome.
- **Depth:** 6–10 blocks typical, up to Y40 in deep basins.
- **Width:** 12–24 blocks; broad, deliberate flow with natural curvature.
- **Bank Slope:** Steep basalt cliffs transitioning to smoother red sand deltas.
- **Noise Detail:** Low frequency; smooth curvature emphasizing river continuity.
- **Integration:** Deep molten channels running below Scorched Delta and Crimson Plains; separated from Crimson River by heat gradient buffer.

### Section 5: Entity & Spawn Ecology
- **Passive:** Striders (uncommon); glide across lava surfaces.
- **Ambient / Neutral:** Endermen (rare); appear on cooled basalt shelves.
- **Hostile (surface):** Magma Cubes (common), Zombified Piglins (uncommon).
- **Hostile (underground):** Skeletons, Zombies, Spiders (rare).
- **Edge Spawns:** Piglins (rare), Hoglins (rare) near Scorched Delta.
- **Notes:** Striders define the biome’s motion; Magma Cubes create rhythm and light. No water or aquatic mobs.

### Section 6: Visual & Atmospheric Hooks
- **Lighting:** Dominated by lava emission; high red-orange luminance.
- **Fog:** Red-orange near flows; deep maroon at elevation.
- **Particles:** Constant ash drift and small flame embers.
- **Soundscape:** Basalt Deltas ambient loop; bubbling, rumbling, occasional hissing.
- **Visual Tone:** Heat shimmer, thick atmosphere, living light. Danger rendered beautiful.

### Section 7: Integration & Adjacency
| Neighbor Biome | Transition Type | Purpose / Effect |
|------------------|------------------|------------------|
| **Scorched Delta** | Smooth basalt shelf / cooling zone | Lava gradually solidifies into basalt; frequent vents and fissures. |
| **Crimson Plains** | Sharp elevation fault | Deep fissures glowing through plains; visual contrast and heat boundary. |
| **Crimson River** | Indirect adjacency | Always buffered by Scorched Delta; prevents water–lava conflict. |
| **Crimson Beach** | Rare vertical transition | Occurs where cooled lava forms brittle red-sand terraces. |
| **Deepslate Peaks** | Subsurface connection | Lava flows descend into crustal magma chambers. |
| **Frozen Lava Tubes** | Thermal inversion boundary | Cooled ancient flows below surface. |
| **Vanilla Desert** | Temperature blend | Molten seams beneath arid terrain; soft visual merge. |
| **Vanilla Badlands** | Erosion blend | Lava rivers carve through clay mesas; orange and red palette unity. |

### Section 8: Development Status & Next Steps
| Category | Status | Notes |
|-----------|---------|-------|
| **Core Concept** | ✅ Finalized | Flowing molten river network defining the Crimson Moon’s volcanic cycle. |
| **Surface Composition** | ✅ Defined | Lava core with basalt, blackstone, and red sand edges. |
| **Vegetation & Features** | ✅ Finalized | Sparse scorched flora; strong geological features. |
| **Terrain & Noise** | ✅ Balanced | Wide, flowing channels with steep edges and smooth noise. |
| **Entity Ecology** | ✅ Tuned | Striders, magma cubes, and piglins create motion and threat. |
| **Atmosphere** | ✅ Locked | Red fog, ash drift, dynamic lava light, Basalt Deltas ambience. |
| **Integration** | ✅ Finalized | Borders Scorched Delta, Crimson Plains, and select vanilla heat biomes. |
| **Development Readiness** | ✅ Ready for JSON | Schema-safe design validated for 1.21.1 worldgen.

### Implementation To-Do
- Define biome JSON in `data/crimson_moon/worldgen/biome/lava_river.json`.
- Integrate into noise_settings (no aquifers; lava flow bias).
- Register basalt and magma features.
- Configure Strider and magma cube spawn modifiers.
- Validate fog, particle, and light levels across biome transitions.

### Narrative Function
> The **Lava River** is the Crimson Moon’s living bloodstream—  
> molten life coursing beneath blackened crust.  
> It flows endlessly, illuminating the planet’s heart in rivers of fire.

