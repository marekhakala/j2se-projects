/***************************************************************************
 *   Copyright (C) 2012 by Marek Hakala   *
 *   hakala.marek@gmail.com   *
 *
 *   Semester project for MI-DPO @ CTU FIT
 *   Topic: Adventure Game Engine - DragonLand 
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU Library General Public License as       *
 *   published by the Free Software Foundation; either version 2 of the    *
 *   License, or (at your option) any later version.                       *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU Library General Public     *
 *   License along with this program; if not, write to the                 *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 ***************************************************************************/

package cz.hakala.dpo.dragonland.core;

import cz.hakala.dpo.dragonland.api.CharacterInterface;
import cz.hakala.dpo.dragonland.api.CommandInterface;
import cz.hakala.dpo.dragonland.api.ItemInterface;
import cz.hakala.dpo.dragonland.api.RoomInterface;

public class CommandBuyItem implements CommandInterface {

        @Override
	public String name() {
		return "buy";
	}

	@Override
	public String execute(String[] params) {
		if (params.length != 3)
			return help();

		String name = params[1];
		String itemName = params[2];

		PlayerInfo info = GameApplication.getInstance().getPlayerInfo();
		RoomInterface room = info.getRoom();
		CharacterInterface sellerCharacter = null;

		for (CharacterInterface p : room.people()) {
			if (p != null && (p instanceof NPC)
					&& p.name().toLowerCase().equals(name.toLowerCase())) {
				sellerCharacter = p;
				break;
			}
		}

		if (sellerCharacter == null)
			return "NPC seller wasn't found!";

		ItemInterface insertItem = null;

		for (ItemInterface item : sellerCharacter.inventory()) {
			if (item.name().toLowerCase().equals(itemName.toLowerCase())) {
				insertItem = item;
				break;
			}
		}

		if (insertItem != null) {
			if (info.getCharacter().money() < insertItem.cost())
				return "You haven't enough money for purchase!";

			if (sellerCharacter.sell(insertItem)) {
				info.getCharacter().buy(insertItem);
				return "Item " + insertItem.name() + " purchased!";
			}
			return "Item " + insertItem.name() + " couldn't be bought!";
		}
		return "Item " + itemName + " wasn't found!";
	}

	@Override
	public String help() {
		return "buy npc item - buy the item from NPC";
	}
}
