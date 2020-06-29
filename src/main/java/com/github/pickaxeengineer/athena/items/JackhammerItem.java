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

    // TODO Better model and animation?

    private static final Set<Material> EFFECTIVE_ON = ImmutableSet.of(Material.ROCK, Material.EARTH, Material.CLAY, Material.PACKED_ICE, Material.ORGANIC, Material.SAND, Material.PISTON, Material.ANVIL);

    protected JackhammerItem(@Nonnull ItemGroup group) {
        super(1, -2.8f, ItemTier.DIAMOND, Collections.emptySet(),
                new Item.Properties().group(group)
                        .addToolType(ToolType.PICKAXE, ItemTier.DIAMOND.getHarvestLevel())
                        .addToolType(ToolType.SHOVEL, ItemTier.DIAMOND.getHarvestLevel())
                        .maxStackSize(1)); // Its a tool, after all
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        Material mat = blockIn.getMaterial();
        int requiredLevel = blockIn.getHarvestLevel();
        ToolType requiredToolType = blockIn.getHarvestTool();
        int ourLevel = ItemTier.DIAMOND.getHarvestLevel();
        // Check for correct tooltype
        if (requiredToolType == ToolType.PICKAXE || requiredToolType == ToolType.SHOVEL) {
            // respect harvest level
            if (ourLevel >= requiredLevel) {
                // is effectiv against
                return true;
            }
        }
        // PickaxeItem has this additional check as well
        return EFFECTIVE_ON.contains(mat);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        // check for material effectiveness
        Material material = state.getMaterial();
        boolean pickaxeEffective = material == Material.ROCK || material == Material.IRON || material == Material.ANVIL;
        if (pickaxeEffective || getToolTypes(stack).stream().anyMatch(state::isToolEffective)) {
            // Pretend to be diamond level
            return ItemTier.DIAMOND.getEfficiency(); // Mekanism RefinedObsidian: 25
        }
        return 1; // Seems to be the default else
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
        if (!worldIn.isRemote) {
            // get facing
            Vec3d playerLooking = entityLiving.getLookVec();
            Direction facing = Direction.getFacingFromVector(playerLooking.x, playerLooking.y, playerLooking.z);
            // TODO replace facing from above with actually "side looking at"

            List<BlockPos> coords = new ArrayList<>();
            switch (facing) {
                case UP:
                case DOWN:
                    // regular 2d case in x and z axis
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            coords.add(pos.add(i, 0, j));
                        }
                    }
                    break;
                case EAST:
                case WEST:
                    // regular 2d case in y and z axis
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            coords.add(pos.add(0,i,j));
                        }
                    }
                    break;
                case NORTH:
                case SOUTH:
                    // regular 2d case in y and x axis
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            coords.add(pos.add(i,j,0));
                        }
                    }
                    break;
            }
            // Only keep actually harvestable blocks
            List<BlockPos> toMine = coords.stream().filter(e -> canHarvestBlock(worldIn.getBlockState(e))).collect(Collectors.toList());
            List<ItemStack> drops = new ArrayList<>(9);
            toMine.forEach(p -> {
                // te is null, as there is no te and its nullable
                drops.addAll(Block.getDrops(worldIn.getBlockState(p), (ServerWorld) worldIn, p, null));
                worldIn.removeBlock(p, false);
            });
            // Spawn the drops
            // TODO spawning location TBD
            drops.forEach(itemStack -> worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack)));
        }
        return true;
    }

    @Override
    public boolean getIsRepairable(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return false;
    }
}
