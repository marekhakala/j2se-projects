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

import java.util.List;
import java.util.ArrayList;

import cz.hakala.dpo.dragonland.api.CharacterInterface;
import cz.hakala.dpo.dragonland.api.ItemInterface;
import cz.hakala.dpo.dragonland.api.RoomInterface;
import cz.hakala.dpo.dragonland.api.MovePointInterface;

public class Room implements RoomInterface {
	protected String innerName;
	protected List<ItemInterface> innerItems = new ArrayList<ItemInterface>();
	protected List<MovePointInterface> innerMovePoints = new ArrayList<MovePointInterface>();
	protected List<CharacterInterface> innerPeople = new ArrayList<CharacterInterface>();

	public String name() {
		return innerName;
	}

	public void setName(String name) {
		innerName = name;
	}

	public void dropItem(ItemInterface item) {
		innerItems.add(item);
	}

	public ItemInterface getItem(String name) {
		for (ItemInterface elem : innerItems) {
			if (elem.name().toLowerCase().equals(name.toLowerCase())) {
				innerItems.remove(elem);
				return elem;
			}
		}
		return null;
	}

	@Override
	public List<ItemInterface> items() {
		return innerItems;
	}

	public List<CharacterInterface> people() {
		return innerPeople;
	}

	@Override
	public List<MovePointInterface> movePoints() {
		return innerMovePoints;
	}
}