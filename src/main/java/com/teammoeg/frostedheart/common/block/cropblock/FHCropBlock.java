package com.teammoeg.frostedheart.common.block.cropblock;

import com.teammoeg.frostedheart.FHContent;
import com.teammoeg.frostedheart.FHMain;
import com.teammoeg.frostedheart.world.chunkdata.ChunkData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.BiFunction;

public class FHCropBlock extends CropsBlock {
    public final String name;
    private int growTemperature;

    public FHCropBlock(String name, int growTemperature, Properties builder, BiFunction<Block, Item.Properties, Item> createItemBlock) {
        super(builder);
        this.name = name;
        this.growTemperature = growTemperature;
        FHContent.registeredFHBlocks.add(this);
        ResourceLocation registryName = createRegistryName();
        setRegistryName(registryName);
        Item item = createItemBlock.apply(this, new Item.Properties().group(FHMain.itemGroup));
        if (item != null) {
            item.setRegistryName(registryName);
            FHContent.registeredFHItems.add(item);
        }
    }

    public ResourceLocation createRegistryName() {
        return new ResourceLocation(FHMain.MODID, name);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getLightSubtracted(pos, 0) >= 9) {
            int i = this.getAge(state);
            ChunkData data = ChunkData.get(worldIn, pos);
            float temp = data.getTemperatureAtBlock(pos);
            if (temp < growTemperature) {
                worldIn.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState(), 2);
            } else if (i < this.getMaxAge()) {
                float f = getGrowthChance(this, worldIn, pos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                    worldIn.setBlockState(pos, this.withAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                }
            }
        }
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        ChunkData data = ChunkData.get(worldIn, pos);
        float temp = data.getTemperatureAtBlock(pos);
        if (temp < growTemperature) {
            return true;
        }
        return false;
    }
}
