package com.teammoeg.frostedheart.world.structure;

import com.teammoeg.frostedheart.FHMain;
import com.teammoeg.frostedheart.world.FHStructures;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class FrozenWhalePiece extends TemplateStructurePiece {
    public final ResourceLocation resource;
    public final Rotation rotation;

    public FrozenWhalePiece(TemplateManager templateManager, BlockPos pos, Rotation rotation) {
        super(FHStructures.FROZEN_WHALE_PIECE, 0);
        this.resource = new ResourceLocation(FHMain.MODID, "relic/frozen_whale");
        this.templatePosition = pos;
        this.rotation = rotation;
        this.loadTemplate(templateManager);
    }

    public FrozenWhalePiece(TemplateManager templateManager, CompoundNBT p_i50566_2_) {
        super(FHStructures.FROZEN_WHALE_PIECE, p_i50566_2_);
        this.resource = new ResourceLocation(p_i50566_2_.getString("Template"));
        this.rotation = Rotation.valueOf(p_i50566_2_.getString("Rot"));
        this.loadTemplate(templateManager);
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {
    }

    private void loadTemplate(TemplateManager manager) {
        Template template = manager.getTemplateDefaulted(this.resource);
        PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
        this.setup(template, this.templatePosition, placementsettings);
    }

    protected void readAdditional(CompoundNBT tagCompound) {
        super.readAdditional(tagCompound);
        tagCompound.putString("Template", this.resource.toString());
        tagCompound.putString("Rot", this.rotation.name());
    }
}
