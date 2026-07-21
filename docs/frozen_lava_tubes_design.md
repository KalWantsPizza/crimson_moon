## Frozen Lava Tubes — Architect Design Document (Complete)

### Section 1: Core Environmental Identity
Ancient magma tunnels that cooled eons ago, now preserved beneath the Crimson Moon’s crust as vast, frozen arteries. The **Frozen Lava Tubes** represent the planet’s final geological stage — once rivers of molten fire, now quiet corridors of deepslate and ice. These tunnels glow faintly with trapped light and residual minerals, embodying the silence left when heat dies.

### Section 2: Surface Composition
- **Floor & Walls:** Deepslate, Cobbled Deepslate, occasional Basalt, Packed Ice, and Blue Ice.
- **Subsurface:** Deepslate and Tuff with rare Magma Block or Basalt veins — remnants of past flows.
- **Ceiling:** Cobbled Deepslate, Basalt (rare), Dripstone Blocks, Pointed Dripstone, and sparse Glow Lichen.
- **Underlayer:** Solid Deepslate.
- **Fluids:** Small Water or Lava Pockets — water surrounded by Packed Ice, lava enclosed by Basalt.
- **Additional Details:** Frozen magma veins, calcite and amethyst clusters, and natural dripstone columns. No permafrost or snow accumulation.

### Section 3: Vegetation & Features
- **Flora:** Glow Lichen (common), Warped Fungi (uncommon, luminous near water or heat).  
- **Mineral Features:** Amethyst Buds and Clusters (uncommon), Calcite + Tuff Bands (occasional), Blue Ice Stalactites (rare).  
- **Geological Features:**
  - **Lava Pockets:** Rare sealed magma chambers surrounded by Basalt and Calcite.
  - **Water Chambers:** Uncommon shallow frozen pools bordered by ice and lichen.
  - **Collapsed Passages:** Occasional rubble of Cobbled Deepslate and broken Dripstone.
  - **Crystal Falls:** Very rare frozen waterfalls formed mid-flow, now suspended sheets of ice.
  - **Thermal Residue Veins:** Occasional glowing magma traces beneath ice.
- **Atmosphere:** Still, blue-lit, echoing. Cold air shimmer with soft condensation and drifting frost motes.

### Section 4: Terrain & Noise Shape
- **Average Elevation:** Y5–Y35 — lowest crustal layer before bedrock.
- **Tunnel Width:** 5–15 blocks; alternating narrow veins and open caverns.
- **Height:** 6–20 blocks; domed ceilings and cathedral-like chambers.
- **Curvature:** Smooth, flow-like — the shape of ancient magma rivers.
- **Branching:** Low–Moderate; main tunnels fork rarely and reconnect naturally.
- **Verticality:** Gentle slopes; no abrupt drops or climbs.
- **Chambers:** Occur every 60–80 blocks; large ovals containing frozen lakes or crystal deposits.
- **Integration:** Naturally merges with Deepslate Caves; smooth blending of stone and ice.

### Section 5: Entity & Spawn Ecology
- **Passive:** Glow Squid (uncommon) in frozen pools; Bats (uncommon) near ceilings.
- **Hostile:** Strays (rare), Skeletons (rare), Spiders (uncommon) — sparse and slow-moving.
- **Neutral:** Endermen (rare) near amethyst or magma chambers.
- **Behavior:** Low-density, non-aggressive presence; sound and light carry far through silence.

### Section 6: Visual & Atmospheric Hooks
- **Lighting:** Dim cyan ambient light (~5–7); brighter around ice or magma veins.
- **Fog:** Pale blue-gray, thick in open chambers.
- **Water:** Dark cyan, slightly opaque.
- **Lava:** Deep amber, muted by surrounding ice.
- **Particles:** Slow frost motes and condensation trails.
- **Soundscape:** Dripping water, echoing wind, faint crackling ice; occasional ambient chimes.
- **Mood:** Sacred stillness — a frozen memory of movement.

### Section 7: Integration & Adjacency
| Neighbor Biome | Transition Type | Purpose / Effect |
|----------------|-----------------|------------------|
| **Deepslate Peaks (above)** | Vertical gradient | Mountain roots transition into frozen tunnels below; ice veins mark ancient flow paths. |
| **Deepslate Caves (upper connection)** | Open tunnel link | Smooth integration with cave systems; glow lichen and ice signals the descent into colder zones. |
| **Scorched Delta (above)** | Thermal fissure | Tubes trace beneath cooled basalt fields, retaining residual heat for rare water pockets. |
| **Lava Rivers (lateral)** | Parallel flow channels | Frozen relics of prior molten systems; amber glow visible through basalt cracks. |
| **Warped Oasis (rare link)** | Frozen aquifer connection | Small vertical shafts freeze into icicle-lined corridors linking to upper oases. |
| **Bedrock Boundary (below)** | Geological terminus | Deepest biome before bedrock; stable and silent foundation of the moon. |

- **Biome Type:** Deep subterranean.
- **Frequency:** Moderate rarity; long networks spaced regionally.
- **Noise Integration:** Soft transitions between deepslate and ice-lined tunnels.
- **Visual Transition:** Gray stone shifts into blue ice glow; subtle cyan fog marks entry.  

### Section 8: Development Status & Next Steps
| Category | Status | Notes |
|-----------|---------|-------|
| **Core Concept** | ✅ Finalized | Ancient frozen magma tunnels beneath the Crimson Moon. |
| **Surface Composition** | ✅ Defined | Deepslate and ice-dominant, no snow. |
| **Vegetation & Features** | ✅ Finalized | Lichen, fungi, amethyst, and dripstone. |
| **Terrain & Noise** | ✅ Balanced | Smooth lava-flow geometry integrated with caves. |
| **Entity Ecology** | ✅ Tuned | Sparse glow squid, bats, undead, and rare Endermen. |
| **Atmosphere** | ✅ Locked | Cyan light, echoing stillness, frozen air. |
| **Integration** | ✅ Finalized | Lies beneath Deepslate Peaks and Caves; foundation layer of the world. |
| **Development Readiness** | ✅ Ready for JSON | Schema-safe for 1.21.1 worldgen.

### Implementation To-Do
- Define biome JSON at `data/crimson_moon/worldgen/biome/frozen_lava_tubes.json`.
- Implement icy deepslate block palette and light properties.
- Add tunnel noise carver with low-frequency flow curvature.
- Configure limited mob spawns (Glow Squid, Stray, etc.).
- Validate smooth biome blending with Deepslate Caves and Peaks layers.

### Narrative Function
> The **Frozen Lava Tubes** are the Crimson Moon’s buried past —  
> molten veins turned to stone, light imprisoned in ice.  
> Here, the fire sleeps, and silence remembers the world that once burned.

