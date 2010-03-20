/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 *  aion-unique is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.aion.clientpackets;

import org.apache.log4j.Logger;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.legion.Legion;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_TABS;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * 
 * @author Simple
 * 
 */
public class CM_LEGION_TABS extends AionClientPacket
{
	private static final Logger	log	= Logger.getLogger(CM_LEGION_TABS.class);

	/**
	 * exOpcode and the rest
	 */
	private int					page;
	private int					tab;

	/**
	 * Constructs new instance of CM_LEGION packet
	 * 
	 * @param opcode
	 */
	public CM_LEGION_TABS(int opcode)
	{
		super(opcode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void readImpl()
	{
		page = readD();
		tab = readC();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void runImpl()
	{
		Player activePlayer = getConnection().getActivePlayer();
		Legion legion = activePlayer.getLegion();
		switch(tab)
		{
			/**
			 * History Tab
			 */
			case 0:
				log.info("Requested History Tab Page: " + page);
				if(!legion.getLegionHistory().isEmpty())
					PacketSendUtility.sendPacket(activePlayer, new SM_LEGION_TABS(legion.getLegionHistory(), page));
				break;
			/**
			 * Reward Tab
			 */
			case 1:
				log.info("Requested Reward Tab Page: " + page);
				break;
		}
	}
}