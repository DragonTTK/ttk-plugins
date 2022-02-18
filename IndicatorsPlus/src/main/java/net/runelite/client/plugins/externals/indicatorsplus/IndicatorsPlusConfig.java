/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.externals.indicatorsplus;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("indicators-plus")
public interface IndicatorsPlusConfig extends Config
{
	@ConfigSection(
			name = "Highlight Options",
			description = "Toggle highlighted players by type (self, friends, etc.) and choose their highlight colors",
			position = 99
	)
	String highlightSection = "section";

	@ConfigItem(
			position = 10,
			keyName = "drawNonClanMemberNames",
			name = "Highlight others",
			description = "Configures whether or not other players should be highlighted",
			section = highlightSection
	)
	default boolean highlightOthers()
	{
		return true;
	}

	@ConfigItem(
			position = 11,
			keyName = "nonClanMemberColor",
			name = "Others",
			description = "Color of other players names",
			section = highlightSection
	)
	default Color getOthersColor()
	{
		return Color.RED;
	}

	@ConfigItem(
			position = 10,
			keyName = "drawPlayerTiles",
			name = "Draw tiles under players",
			description = "Configures whether or not tiles under highlighted players should be drawn"
	)
	default boolean drawTiles()
	{
		return false;
	}

	@ConfigItem(
			position = 11,
			keyName = "playerNamePosition",
			name = "Text Position:",
			description = "Configures the position of drawn player cb lvl, or if they should be disabled"
	)
	default PlayerNameLocation playerNamePosition()
	{
		return PlayerNameLocation.ABOVE_HEAD;
	}

	@ConfigItem(
			position = 13,
			keyName = "colorPlayerMenu",
			name = "Colorize player menu",
			description = "Color right click menu for players"
	)
	default boolean colorPlayerMenu()
	{
		return true;
	}
}