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

import cz.hakala.dpo.dragonland.api.CharacterFactory;

public class CharacterFactoryPool {
	protected Map<String, CharacterFactory> factoryMap = new HashMap<String, CharacterFactory>();

	public void insert(CharacterFactory factory) {
		factoryMap.put(factory.name(), factory);
	}

	public void remove(String name) {
		factoryMap.remove(name);
	}

	public CharacterFactory get(String name) {
		return factoryMap.get(name);
	}

	public CharacterFactory getByFactoryName(String name) {
		String factoryName = name.toLowerCase() + "factory";

		for (Map.Entry<String, CharacterFactory> entry : factoryMap.entrySet()) {
			if (factoryName.equals(entry.getValue().name().toLowerCase()))
				return entry.getValue();
		}
		return null;
	}
	
	public String factoriesList() {
		String output = "";
		
		for (Map.Entry<String, CharacterFactory> entry : factoryMap.entrySet()) {
				String name = entry.getValue().name();
				name = name.replace("Factory", "");
				output += name + " ";
		}
		return output;
	}
}
