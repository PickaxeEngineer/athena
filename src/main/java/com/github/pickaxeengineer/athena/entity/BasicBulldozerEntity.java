package com.github.pickaxeengineer.athena.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BasicBulldozerEntity extends Entity {

    public BasicBulldozerEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
    // copied from boat
        return entityIn.canBePushed() ? entityIn.getBoundingBox() : null;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return this.getBoundingBox();
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

    }

    /**
     * Called on the logical server to get a packet to send to the client containing data necessary to spawn your entity.
     * Using Forge's method instead of the default vanilla one allows extra stuff to work such as sending extra data,
     * using a non-default entity factory and having {@link IEntityAdditionalSpawnData} work.
     *
     * It is not actually necessary for our WildBoarEntity to use Forge's method as it doesn't need any of this extra
     * functionality, however, this is an example mod and many modders are unaware that Forge's method exists.
     *
     * @return The packet with data about your entity
     * @see FMLPlayMessages.SpawnEntity
     */
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
