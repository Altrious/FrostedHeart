/*
 * Copyright (c) 2022 TeamMoeg
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

package com.teammoeg.frostedheart.events;

import com.teammoeg.frostedheart.network.PacketHandler;
import com.teammoeg.frostedheart.research.network.FHResearchDataSyncPacket;

import dev.ftb.mods.ftbteams.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.event.PlayerChangedTeamEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class FTBTeamsEvents {

	public FTBTeamsEvents() {
	}
	public static void syncDataWhenTeamChange(PlayerChangedTeamEvent event) {
    	PacketHandler.send(PacketDistributor.PLAYER.with(() -> event.getPlayer()),
                new FHResearchDataSyncPacket(
                        FTBTeamsAPI.getPlayerTeam(event.getPlayer()).getId()));
    }
}