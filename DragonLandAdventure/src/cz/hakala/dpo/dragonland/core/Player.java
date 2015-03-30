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

import java.util.ArrayList;
import java.util.List;

import cz.hakala.dpo.dragonland.api.CharacterInterface;
import cz.hakala.dpo.dragonland.api.ItemInterface;

public class Player implements CharacterInterface {
	protected String innerName = "";
	protected Double innerMoney = 0.0;
	protected List<ItemInterface> innerInventory = null;

	public Player(String name, Double money) {
		this.innerName = name;
		this.innerMoney = money;
		this.innerInventory = new ArrayList<ItemInterface>();
	}

	public Player(String name, Double money, List<ItemInterface> inventory) {
		this.innerName = name;
		this.innerMoney = money;
		this.innerInventory = inventory;
	}

	@Override
	public String name() {
		return this.innerName;
	}

	@Override
	public Double money() {
		return innerMoney;
	}

	@Override
	public boolean buy(ItemInterface item) {
		if (innerMoney >= item.cost()) {
			if (innerInventory.add(item)) {
				innerMoney -= item.cost();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean sell(ItemInterface item) {
		if (innerInventory.remove(item)) {
			innerMoney += item.cost();
			return true;
		}
		return false;
	}

	@Override
	public List<ItemInterface> inventory() {
		return this.innerInventory;
	}

	@Override
	public String type() {
		return "Player";
	}
}
