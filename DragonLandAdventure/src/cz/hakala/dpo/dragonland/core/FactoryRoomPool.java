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

import java.util.HashMap;
import java.util.Map;

import cz.hakala.dpo.dragonland.api.RoomInterface;

public class FactoryRoomPool {
	protected RoomInterface head = null;
	protected static FactoryRoomPool instance = null;
	protected Map<String, RoomInterface> roomMap = new HashMap<String, RoomInterface>();

	protected FactoryRoomPool() {
	}

	public void insert(RoomInterface room) {
		if (head == null)
			head = room;
		roomMap.put(room.name(), room);
	}

	public void remove(String name) {
		roomMap.remove(name);
	}

	public RoomInterface get(String name) {
		return roomMap.get(name);
	}

	public static FactoryRoomPool getInstance() {
		if (instance == null)
			instance = new FactoryRoomPool();
		return instance;
	}

	public RoomInterface getRoom() {
		return head;
	}

	public RoomInterface firstRoom() {
		if (roomMap.size() > 0) {
			String[] es = (String[]) roomMap.entrySet().toArray();
			return roomMap.get(es[0]);
		}
		return null;
	}
}
