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

import cz.hakala.dpo.dragonland.api.RoomInterface;
import cz.hakala.dpo.dragonland.core.CharacterFactoryPool;
import cz.hakala.dpo.dragonland.core.Door;
import cz.hakala.dpo.dragonland.core.FactoryRoomPool;
import cz.hakala.dpo.dragonland.core.GameApplication;
import cz.hakala.dpo.dragonland.core.GameSceneDirector;

public class MyFirstGame {
	RoomInterface room1 = null;
	RoomInterface room2 = null;
	RoomInterface room3 = null;

	GameApplication app = GameApplication.getInstance();
	GameSceneDirector director = new GameSceneDirector();

	public MyFirstGame() {
	}

	public void createCharacterFactories() {
		CharacterFactoryPool pool = app.getCharacterFactoryPool();

		pool.insert(new ElfCharacterFactory());
		pool.insert(new MagicianCharacterFactory());
		pool.insert(new BarbarCharacterFactory());
	}

	public void createRooms() {
		createRoom1();
		createRoom2();
		createRoom3();

		// Door 1
		Door d1 = new Door("toElfMine", room1, room2, true);
		room1.movePoints().add(d1);

		// Door 2
		Door d2 = new Door("toDarkWood", room2, room3, false);
		room2.movePoints().add(d2);

		// Door 3
		Door d3 = new Door("toLongPlain", room3, room1, true);
		room3.movePoints().add(d3);
	}

	public void createRoom1() {
		director.setRoomBuilder(new RoomBuilderLongPlain());
		director.constructRoom();
		room1 = director.getRoom();
		FactoryRoomPool.getInstance().insert(director.getRoom());
	}

	public void createRoom2() {
		director.setRoomBuilder(new RoomBuilderElfMine());
		director.constructRoom();
		room2 = director.getRoom();
		FactoryRoomPool.getInstance().insert(director.getRoom());
	}

	public void createRoom3() {
		director.setRoomBuilder(new RoomBuilderDarkWood());
		director.constructRoom();
		room3 = director.getRoom();
		FactoryRoomPool.getInstance().insert(director.getRoom());
	}

	public void init() {
		// Init
		app.setupName("DragonLang v.1.0 beta");

		// Rooms
		createRooms();

		// Character factories
		createCharacterFactories();
		app.init();
	}

	public static void main(String[] args) {
		MyFirstGame game = new MyFirstGame();
		game.init();
	}
}
