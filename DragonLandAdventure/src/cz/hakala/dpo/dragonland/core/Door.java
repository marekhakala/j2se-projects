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

import cz.hakala.dpo.dragonland.api.MovePointInterface;
import cz.hakala.dpo.dragonland.api.RoomInterface;

public class Door implements MovePointInterface {
	protected String innerName = "";
	protected RoomInterface from = null;
	protected RoomInterface to = null;
	protected boolean locked = false;

	public Door(String name, RoomInterface from, RoomInterface to,
			boolean locked) {
		this.innerName = name;
		this.from = from;
		this.to = to;

		this.locked = locked;
	}

	public String name() {
		return this.innerName;
	}

	@Override
	public boolean isLock() {
		return locked;
	}

	@Override
	public boolean unlock() {
		if (locked) {
			locked = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean lock() {
		if (!locked) {
			locked = true;
			return true;
		}
		return false;
	}

	@Override
	public RoomInterface from() {
		return this.from;
	}

	@Override
	public RoomInterface to() {
		return this.to;
	}
}
