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

package cz.hakala.dpo.dragonland.api;

// Game view interface advantages for print game output
public interface GameViewInterface {
	// Method for print topic box area
	void printTopicBox(String text);

	// Method for print text with linebreak
	void printText(String text);

	// Method for print text with or without linebreak (by input arg)
	void printText(String text, boolean newLine);

	// Method for print system message
	public void printSystemMessage(String text);

	// Method for print command message
	public void printCommandMessage(String text);

	// Method for print dash line with count of dash by n arg
	void printLine(Integer n);

	// Method for print game state help by input game state
	void printHelp(GameStateInterface state);
}
