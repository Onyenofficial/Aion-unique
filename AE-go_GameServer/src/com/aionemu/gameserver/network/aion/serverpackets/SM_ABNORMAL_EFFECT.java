/*
 * This file is part of aion-unique <www.aion-unique.com>.
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
package com.aionemu.gameserver.network.aion.serverpackets;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author ATracer
 */
public class SM_ABNORMAL_EFFECT extends AionServerPacket
{
	private static final Logger	log	= Logger.getLogger(SM_ABNORMAL_EFFECT.class);
	
	private int effectedId;
	private Effect[] effects;
	
	public SM_ABNORMAL_EFFECT(int effectedId, Effect[] effects)
	{
		this.effects = effects;
		this.effectedId = effectedId;
	}


	@Override
	protected void writeImpl(AionConnection con, ByteBuffer buf)
	{
		writeD(buf, effectedId); 
		writeD(buf, 1); //unk
		writeC(buf, 0); //unk
		if(effects != null && effects.length > 0)
		{
			writeD(buf, 16); //unk 
			writeH(buf, effects.length); //effects size
			
			for(Effect effect : effects)
			{
				writeH(buf, effect.getSkillId()); 
				writeC(buf, effect.getSkillLevel()); //unk level?
				writeC(buf, 1); //unk
				writeD(buf, effect.getDuration() * 1000); // TODO elapsed time
			}	
		}else
		{
			writeD(buf, 0);
			writeC(buf, 0);
		}	
	}
}