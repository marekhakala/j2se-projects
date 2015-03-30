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

package cz.hakala.dpo.dragonland.game;

import cz.hakala.dpo.dragonland.api.CharacterFactory;
import cz.hakala.dpo.dragonland.api.CharacterInterface;
import cz.hakala.dpo.dragonland.api.ItemInterface;
import cz.hakala.dpo.dragonland.core.BasicItem;

public class BarbarCharacterFactory implements CharacterFactory {
	@Override
	public String name() {
		return "BarbarFactory";
	}

	@Override
	public CharacterInterface construct(String name) {
		CharacterInterface character = new BarbarCharacter(name, (double) 1000);

		ItemInterface item1 = new BasicItem("Elixir", (double) 259, true);
		character.inventory().add(item1);

		ItemInterface item2 = new BasicItem("Health", (double) 147, true);
		character.inventory().add(item2);

		return character;
	}
}
