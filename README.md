# Project EVOLVE Client v0.4.2

## Goliath renderer prototype
- Replaces the old Skeleton / Wither Skeleton / Wither visual stand-ins with the uploaded `Evolve_Goliath.bbmodel` geometry.
- The Blockbench source is included under `source_assets/Evolve_Goliath.bbmodel`.
- Uses the model's bone hierarchy: body/chest/head/jaw/neck/arms/forearms/hands/thighs/lower legs/feet/back/glute groups.
- Implements the existing `animation.goliath.walk` as the first live animation pass: opposing arm/leg swing, forearm/knee follow-through, body roll, neck/head motion.
- Stage 1/2/3 currently use the same Goliath geometry with slight scale growth. Stage-specific geometry changes can be added later.
- The supplied bbmodel contains no embedded texture, so `goliath.png` is a temporary neutral placeholder texture. A final Goliath texture can replace it without changing renderer code.

Next animation hooks: idle, claw_attack, rock_throw, leap_start/air/land, fire_breath, charge, feed, evolve, hurt, death.

## v0.4.3 orientation fix
- Fixed Goliath rendering upside-down when the custom renderer replaces PlayerEntityRenderer.
- Restores Minecraft LivingEntityRenderer-style model-space conversion (X/Y flip).
- Anchors the custom model to the player's feet after conversion.
- Stage scaling remains Stage 1 = 0.92, Stage 2 = 1.00, Stage 3 = 1.08.
