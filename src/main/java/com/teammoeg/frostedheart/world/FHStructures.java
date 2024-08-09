/*
 * Copyright (c) 2022-2024 TeamMoeg
 *
 * This file is part of Frosted Heart.
 *
 * Frosted Heart is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Frosted Heart is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Frosted Heart. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.teammoeg.frostedheart.world;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.teammoeg.frostedheart.util.RegistryUtils;
import com.teammoeg.frostedheart.world.structure.FrozenWhalePiece;
import com.teammoeg.frostedheart.world.structure.FrozenWhaleStructure;
import com.teammoeg.frostedheart.world.structure.ObservatoryPiece;
import com.teammoeg.frostedheart.world.structure.ObservatoryStructure;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

public class FHStructures {

    public static final IStructurePieceType OBSERVATORY_PIECE = registerPiece(ObservatoryPiece::new, "observatory");
//    public static final IStructurePieceType VOLCANIC_VENT_PIECE = registerPiece(VolcanicVentPiece::new, "volcanic_vent");
    public static final IStructurePieceType FROZEN_WHALE_PIECE = registerPiece(FrozenWhalePiece::new, "frozen_whale");

    public static final Structure<NoFeatureConfig> OBSERVATORY = new ObservatoryStructure(NoFeatureConfig.CODEC);
//    public static final Structure<NoFeatureConfig> VOLCANIC_VENT = new VolcanicVentStructure(NoFeatureConfig.CODEC);
    public static final Structure<NoFeatureConfig> FROZEN_WHALE = new FrozenWhaleStructure(NoFeatureConfig.CODEC);

    private static IStructurePieceType registerPiece(IStructurePieceType type, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key, type);
    }

    public static void registerStructureGenerate() {
        Structure.NAME_STRUCTURE_BIMAP.put(RegistryUtils.getRegistryName(FHStructures.OBSERVATORY).toString(), FHStructures.OBSERVATORY);
//        Structure.NAME_STRUCTURE_BIMAP.put(FHStructures.VOLCANIC_VENT.getRegistryName().toString(), FHStructures.VOLCANIC_VENT);
        Structure.NAME_STRUCTURE_BIMAP.put(RegistryUtils.getRegistryName(FHStructures.FROZEN_WHALE).toString(), FHStructures.FROZEN_WHALE);

        HashMap<Structure<?>, StructureSeparationSettings> StructureSettingMap = new HashMap<>();
        StructureSettingMap.put(OBSERVATORY, new StructureSeparationSettings(30, 15, 545465463));
//        StructureSettingMap.put(VOLCANIC_VENT,new StructureSeparationSettings(12,8,123456));
        StructureSettingMap.put(FROZEN_WHALE, new StructureSeparationSettings(15, 8, 1919810));

        DimensionStructuresSettings.field_236191_b_ = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                .putAll(DimensionStructuresSettings.field_236191_b_)
                .putAll(StructureSettingMap)
                .build();
        Structure.field_236384_t_ = ImmutableList.<Structure<?>>builder()
                .addAll(Structure.field_236384_t_)
                .add(FHStructures.OBSERVATORY.getStructure())
                .add(FHStructures.FROZEN_WHALE.getStructure())
                .build();
        WorldGenRegistries.NOISE_SETTINGS.forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getStructures().func_236195_a_();
            if (structureMap instanceof ImmutableMap) {
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.putAll(StructureSettingMap);
                settings.getStructures().field_236193_d_ = tempMap;
            } else structureMap.putAll(StructureSettingMap);
        });
    }
}
