package com.github.pickaxeengineer.athena.entity;

import com.github.pickaxeengineer.athena.AthenaMod;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AthenaEntityRegistry {

    private AthenaEntityRegistry() {
    }

    public static final DeferredRegister<EntityType<?>> ENTITY_REGISTRY = new DeferredRegister<>(ForgeRegistries.ENTITIES, AthenaMod.MODID);

    public static final RegistryObject<EntityType<BasicBulldozerEntity>> BULLDOZER_TYPE = ENTITY_REGISTRY.register("basic_bulldozer", () ->
            EntityType.Builder.<BasicBulldozerEntity>create(
                    BasicBulldozerEntity::new, EntityClassification.MISC)
                    .size(30 * (1f / 16f), 21 * (1f / 16f))
                    .build(new ResourceLocation(AthenaMod.MODID, "basic_bulldozer").toString()));

}
