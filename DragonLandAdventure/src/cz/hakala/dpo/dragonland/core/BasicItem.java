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

import cz.hakala.dpo.dragonland.api.ItemInterface;
import cz.hakala.dpo.dragonland.api.MovePointInterface;

public class BasicItem implements ItemInterface {
	protected String innerName = "";
	protected Double innerCost = (double) 0;
	protected boolean dragState = true;

	public BasicItem(String name, Double cost, boolean dragState) {
		this.innerName = name;
		this.innerCost = cost;
		this.dragState = dragState;
	}

	@Override
	public int compareTo(Object obj) {
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;

		if (this == obj)
			return EQUAL;

		if (this.getClass() != obj.getClass())
			return BEFORE;

		BasicItem item = (BasicItem) obj;

		// Compare by inner cost
		if (this.innerCost < item.innerCost)
			return BEFORE;
		if (this.innerCost > item.innerCost)
			return AFTER;
		if (this.innerCost == item.innerCost)
			return EQUAL;

		return BEFORE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((innerCost == null) ? 0 : innerCost.hashCode());
		result = prime * result
				+ ((innerName == null) ? 0 : innerName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicItem other = (BasicItem) obj;
		if (innerCost == null) {
			if (other.innerCost != null)
				return false;
		} else if (!innerCost.equals(other.innerCost))
			return false;
		if (innerName == null) {
			if (other.innerName != null)
				return false;
		} else if (!innerName.equals(other.innerName))
			return false;
		return true;
	}

	@Override
	public String name() {
		return this.innerName;
	}

	@Override
	public Integer use(MovePointInterface movePoint) {
		return -1;
	}

	@Override
	public boolean isDragable() {
		return this.dragState;
	}

	@Override
	public Double cost() {
		return this.innerCost;
	}
}
