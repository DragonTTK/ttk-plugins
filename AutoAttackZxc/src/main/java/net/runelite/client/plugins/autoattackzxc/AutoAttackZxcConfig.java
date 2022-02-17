/*
 * Copyright (c) 2018, DragonTTK <https://github.com/dragonttk>
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
package net.runelite.client.plugins.autoattackzxc;

import net.runelite.client.config.*;

@ConfigGroup("AutoAttackZxc")
public interface AutoAttackZxcConfig extends Config {

    String spells = "Spells";
    String targets = "Targets";

    /*
    @ConfigItem(
            keyName = "quickPrayers",
            name = "Enable quick prayers",
            description = "Enables your set quick prayers.",
            position = 1,
            section = "quickPrayer"

    )
    default boolean quickPrayers() {
        return false;
    }

    @ConfigItem(
            keyName = "tickDelay",
            name = "Allow tick delays",
            description = "This will allow the plugin to wait a random configurable amount of ticks between " +
                    "auto attacking. This will be slower but potentially less detectable. Tick delay options" +
                    "can be configured in iUtils config"
    )
    default boolean tickDelay() {
        return true;
    }*/

    @ConfigItem(
            keyName = "selectedSpell",
            name = "Select spell",
            description = "Select the spell you want to use on login.",
            position = 2,
            section = spells
    )
    default Spells selectedSpell()
    {
        return Spells.TELE_BLOCK;
    }
    /*
    @ConfigItem(
            keyName = "selectedTarget",
            name = "Select target",
            description = "Select priority who do we target first on login.",
            position = 3,
            section = targets
    )
    default Targets selectedTarget()
    {
        return Targets.DISTANCE_TO_PLAYER;
    }
    */

    @ConfigSection(
            keyName = "delayConfig",
            name = "Sleep Delay Configuration",
            description = "Configure how the bot handles sleep delays",
            closedByDefault = true,
            position = 0
    )
    String delayConfig = "delayConfig";

    @Range(
            min = 0,
            max = 550
    )
    @ConfigItem(
            keyName = "sleepMin",
            name = "Sleep Min",
            description = "",
            position = 1,
            section = "delayConfig"
    )
    default int sleepMin() {
        return 60;
    }

    @Range(
            min = 0,
            max = 550
    )
    @ConfigItem(
            keyName = "sleepMax",
            name = "Sleep Max",
            description = "",
            position = 2,
            section = "delayConfig"
    )
    default int sleepMax() {
        return 350;
    }

    @Range(
            min = 0,
            max = 550
    )
    @ConfigItem(
            keyName = "sleepTarget",
            name = "Sleep Target",
            description = "",
            position = 3,
            section = "delayConfig"
    )
    default int sleepTarget() {
        return 100;
    }

    @Range(
            min = 0,
            max = 550
    )
    @ConfigItem(
            keyName = "sleepDeviation",
            name = "Sleep Deviation",
            description = "",
            position = 4,
            section = "delayConfig"
    )
    default int sleepDeviation() {
        return 10;
    }

    @ConfigItem(
            keyName = "sleepWeightedDistribution",
            name = "Sleep Weighted Distribution",
            description = "Shifts the random distribution towards the lower end at the target, otherwise it will be an even distribution",
            position = 5,
            section = "delayConfig"
    )
    default boolean sleepWeightedDistribution() {
        return false;
    }

}
