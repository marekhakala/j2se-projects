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

import cz.hakala.dpo.dragonland.api.CommandInterface;
import cz.hakala.dpo.dragonland.api.MovePointInterface;
import cz.hakala.dpo.dragonland.api.RoomInterface;

public class CommandUseDoor implements CommandInterface {

	@Override
	public String name() {
		return "usedoor";
	}

	@Override
	public String execute(String[] params) {
		if (params.length != 2)
			return help();

		String name = params[1];

		PlayerInfo playerInfo = GameApplication.getInstance().getPlayerInfo();
		RoomInterface room = playerInfo.getRoom();

		for (MovePointInterface movePoint : room.movePoints()) {
			if (movePoint.name().toLowerCase().equals(name.toLowerCase())) {
				if (movePoint.isLock())
					return "You can't use a move point, because it's locked.";

				playerInfo.setRoom(movePoint.to());
				return "You have moved into the room "
						+ playerInfo.getRoom().name() + ".";
			}
		}
		return "Move isn't possible!";
	}

	@Override
	public String help() {
		return "usedoor door_name - moves you between rooms";
	}
}
