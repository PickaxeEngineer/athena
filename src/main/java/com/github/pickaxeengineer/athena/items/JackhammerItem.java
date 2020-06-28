package com.github.pickaxeengineer.athena.items;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jackhammer: 3x3 mining thing
 */
public class JackhammerItem extends ToolItem {

    private static final Set<Material> EFFECTIVE_ON = ImmutableSet.of(Material.ROCK, Material.EARTH, Material.CLAY, Material.PACKED_ICE, Material.ORGANIC, Material.SAND, Material.PISTON, Material.ANVIL);

    // TODO Custom harvest speed

    protected JackhammerItem(@Nonnull ItemGroup group) {
        super(1, -2.8f, ItemTier.DIAMOND, Collections.emptySet(),
                new Item.Properties().group(group)
                        .addToolType(ToolType.PICKAXE, ItemTier.DIAMOND.getHarvestLevel())
                        .addToolType(ToolType.SHOVEL, ItemTier.DIAMOND.getHarvestLevel())
                        .maxStackSize(1)); // Its a tool, after all
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        // TODO Only abel to mine dirt?
        Material mat = blockIn.getMaterial();
        int requiredLevel = blockIn.getHarvestLevel();
        ToolType requiredToolType = blockIn.getHarvestTool();
        int ourLevel = ItemTier.DIAMOND.getHarvestLevel();
        // Check for correct tooltype
        if (requiredToolType == ToolType.PICKAXE || requiredToolType == ToolType.SHOVEL) {
            // respect harvest level
            if (ourLevel >= requiredLevel) {
                // is effectiv against
                return EFFECTIVE_ON.stream().anyMatch(m -> m == mat);
            }
        }
        return false;
    }

    @Override
    public boolean onBlockDestroyed(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull BlockState state, @Nonnull BlockPos pos, @Nonnull LivingEntity entityLiving) {
        // Copied from ToolItem, for statistics (and no damage, as this item currently isn't damageable // TODO game balance
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(0, entityLiving, (living) -> {
                living.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }
        // Server side actual breaking of blocks
        if(!worldIn.isRemote){
            // get facing
            Vec3d playerLooking = entityLiving.getLookVec();
            Direction facing = Direction.getFacingFromVector(playerLooking.x, playerLooking.y, playerLooking.z);
            List<BlockPos> coords = new ArrayList<>();
            // https://github.com/Direwolf20-MC/MiningGadgets/blob/750a8ef8c643238e0e9b4dc274d506bba2400ba6/src/main/java/com/direwolf20/mininggadgets/common/items/gadget/MiningCollect.java#L29
            boolean vertical = facing.getAxis().isVertical();
            // TODO horizontal mining is not working --> own lookup and remove DW20's
            Direction up = vertical ? entityLiving.getHorizontalFacing() : Direction.UP;
            Direction down = up.getOpposite();
            Direction right = vertical ? up.rotateY() : up.rotateYCCW();
            Direction left = right.getOpposite();
            coords.add(pos.offset(up).offset(left));
            coords.add(pos.offset(up));
            coords.add(pos.offset(up).offset(right));
            coords.add(pos.offset(left));
            coords.add(pos);
            coords.add(pos.offset(right));
            coords.add(pos.offset(down).offset(left));
            coords.add(pos.offset(down));
            coords.add(pos.offset(down).offset(right));
            // Only keep actually harvestable blocks
            List<BlockPos> toMine = coords.stream().filter( e -> canHarvestBlock(worldIn.getBlockState(e))).collect(Collectors.toList());
            List<ItemStack> drops = new ArrayList<>(9);
            toMine.forEach(p -> {
                // te is null, as there is no te and its nullable
                drops.addAll(Block.getDrops(worldIn.getBlockState(p), (ServerWorld)worldIn, p, null));
                worldIn.setBlockState(p, Blocks.AIR.getDefaultState());
            });
            // Spawn the drops
            drops.forEach(itemStack -> worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack)));
        }
        return true;
    }

    @Override
    public boolean getIsRepairable(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return false;
    }
}
