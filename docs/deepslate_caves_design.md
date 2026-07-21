## Deepslate Caves — Architect Design Document (Complete)

### Section 1: Core Environmental Identity
The **Deepslate Caves** form the Crimson Moon’s living underworld — a vast, humid, and mineral-rich environment stretching from Y = 50 down to Y = –30. These caverns represent the planet’s equilibrium layer, where ancient heat has softened into moisture and stone. Glowing warped fungi and teal moss thrive among the deepslate walls, turning this biome into a breathing labyrinth of mineral and life.

### Section 2: Surface Composition
- **Floor & Walls:** Deepslate, Cobbled Deepslate, Tuff, Dripstone Blocks, Clay, and Teal/Blue Moss patches.
- **Flora:** Warped Nylium and warped sprouts interspersed with clay and moss; huge warped fungi clusters.
- **Ceiling:** Deepslate and Tuff with Pointed Dripstone, Moss, and rare teal-tinted Glow Berries and Vines.
- **Underlayer:** Solid Deepslate connecting to Frozen Lava Tubes.
- **Fluids:** Water pockets and rare lava pockets (1–2 blocks each) — water surrounded by clay and dripstone, lava framed by deepslate.
- **Ores:** High Diamond, Redstone, and Lapis yields; Uncommon Emerald veins; balanced Iron, Gold, and Copper.

### Section 3: Vegetation & Features
- **Ground Cover:** Deepslate, Clay, Warped Nylium, Teal Moss, and Dripstone mix.
- **Warped Fungi Clusters:** Common; small and huge fungi appear around moist basins and clay depressions, surrounded by sprouts and roots.
- **Walls:** Moss and Tuff patches soften stone color; provide moisture realism.
- **Ceilings:** Hanging vines and glow berries tinted teal/blue emit faint cyan light.
- **Water Pockets:** Surrounded by clay and moss; occasional Dripleaf plants grow near edges.
- **Dripstone Columns:** Common; merge with moss or fungi growth for organic shapes.
- **Echo Chambers:** Very rare; wide teal-lit caverns with huge warped fungi and still pools at center.

### Section 4: Terrain & Noise Shape
- **Elevation Range:** Y50 → Y–30.
- **Width:** 6–20 blocks; varied corridors and open galleries.
- **Height:** 5–18 blocks; high domes and smooth tunnels.
- **Curvature:** Smooth, organic wave-like tunnels.
- **Branching:** Moderate; looping paths reconnect naturally.
- **Verticality:** Gentle slopes with slow descent into deeper zones.
- **Chamber Frequency:** Every 80–100 blocks — open domes with reflective pools and fungi clusters.
- **Integration:** Smooth blending with upper Scorched Delta and lower Frozen Lava Tubes.

### Section 5: Entity & Spawn Ecology
- **Passive:** Glow Squid (common), Bats (common), Axolotl (uncommon) — ambient life in water and air.
- **Neutral:** Warped Mooshroom (uncommon) — teal variant, spawns near warped fungus clusters.
- **Hostile:** Stray (rare), Spider (uncommon), Drowned (rare) — limited environmental threats.
- **Special Ambient:** Enderman (rare) — appear near reflective pools or fungal chambers.
- **Behavior:** Calm, ambient; most mobs coexist passively. Axolotls prey on Drowned.
- **Tone:** Quiet but alive — the hum of light, water, and occasional echoes of movement.

### Section 6: Visual & Atmospheric Hooks
- **Lighting:** Dim cyan (light 4–6), accented by warped glow and redstone flicker.
- **Fog:** Deep blue-gray near floor, teal toward ceiling.
- **Water:** Turquoise reflections; lava glows dull amber.
- **Particles:** Slow drifting mist, water droplets, faint teal spores.
- **Soundscape:** Dripping water, gentle bubbling, bat flaps, distant hums from warped fungi.
- **Mood:** Peaceful, introspective, and immersive — a breathing cave system beneath a sleeping planet.

### Section 7: Integration & Adjacency
| Neighbor Biome | Transition Type | Description |
|----------------|-----------------|--------------|
| **Scorched Delta (above)** | Vertical fissures | Hot basalt fades into tuff and deepslate; crimson light transitions to teal mist. |
| **Crimson Plains (above)** | Collapsed shafts | Rare surface entry points, framed by warped vines. |
| **Warped Oasis (above)** | Aquifer seep | Moisture veins feed warped growth and clay pockets. |
| **Frozen Lava Tubes (below)** | Gradual slope | Smooth transition; humidity fades and ice begins forming. |
| **Deepslate Peaks (lateral)** | Geological overlap | Denser stone, higher ore density, fewer fungi. |
| **Vanilla Caves (outer)** | Smooth blend | Shared block palette for modpack compatibility. |

- **Biome Type:** Mid-to-deep subsurface.  
- **Elevation Range:** Y50 to Y–30.  
- **Noise Integration:** Dual-band generation with soft fade transitions.  
- **World Role:** Central connective biome — the Crimson Moon’s breathing core.

### Section 8: Development Status & Next Steps
| Category | Status | Notes |
|-----------|---------|-------|
| **Core Concept** | ✅ Finalized | The living underworld of the Crimson Moon. |
| **Surface Composition** | ✅ Defined | Rich deepslate, clay, and moss palette with fungi integration. |
| **Vegetation & Features** | ✅ Finalized | Warped flora, moss walls, clay pools, and fungi groves. |
| **Terrain & Noise** | ✅ Balanced | Smooth, looping tunnels with large chambers. |
| **Entity Ecology** | ✅ Tuned | Passive aquatic and fungal life with sparse strays and drowned. |
| **Atmosphere** | ✅ Locked | Cyan light, dripping sound, reflective water, teal fog. |
| **Integration** | ✅ Finalized | Extends from Scorched Delta to Frozen Lava Tubes, Y50→Y–30. |
| **Development Readiness** | ✅ Ready for JSON | Schema-safe for 1.21.1 worldgen. |

### Implementation To-Do
- Create biome JSON at `data/crimson_moon/worldgen/biome/deepslate_caves.json`.
- Implement dual-band carver noise and moisture-weighted cave generation.
- Add teal fog, water color, and light levels.
- Configure fungal flora and mob spawn rules.
- Validate smooth integration with adjacent biomes (Delta, Peaks, Lava Tubes).

### Narrative Function
> The **Deepslate Caves** are the Crimson Moon’s lungs —  
> warm, wet, and echoing with slow breath.  
> Here, the world still lives — quietly, patiently, between fire above and ice below.

