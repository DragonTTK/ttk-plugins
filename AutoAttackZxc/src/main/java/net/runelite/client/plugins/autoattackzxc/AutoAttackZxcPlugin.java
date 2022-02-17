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

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.iutils.*;
import org.pf4j.Extension;
import javax.inject.Inject;
import static net.runelite.api.MenuAction.SPELL_CAST_ON_PLAYER;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuAction;
import net.runelite.client.util.PvPUtil;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.plugins.iutils.*;
import net.runelite.client.plugins.iutils.iUtils;
import net.runelite.client.plugins.iutils.scripts.iScript;
import java.time.Instant;
import java.util.List;


@Extension
@PluginDependency(iUtils.class)
@PluginDescriptor(
        name = "Auto Attack",
        enabledByDefault = false,
        description = "MythicalZxc - Automatically casts spell on login.",
        tags = {"mythical", "dragonttk", "auto", "bot", "autoattack", "auto attack"}
)
@Slf4j
public class AutoAttackZxcPlugin extends iScript {

    @Inject
    private AutoAttackZxcConfig config;

    @Inject
    private iUtils utils;

    @Inject
    private Client client;

    @Inject
    private CalculationUtils calc;

    LegacyMenuEntry targetMenu;
    private int actionsThisTick;
    Spells curSpell;
    Instant botTimer;
    long sleepLength;
    boolean stopAttack;

    @Provides
    AutoAttackZxcConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(AutoAttackZxcConfig.class);
    }

    @Override
    protected void startUp() {
        start();
    }

    @Override
    protected void shutDown() {
        stop();
    }

    @Override
    public void onStart() {
        log.info("Started AutoAttack");

        if (client != null && game.localPlayer() != null && client.getGameState() == GameState.LOGGED_IN) {
            botTimer = Instant.now();
            curSpell = config.selectedSpell();
            stopAttack = false;
        } else {
            log.info("Start logged in!");
            stop();
        }
    }

    @Override
    public void onStop() {
        log.info("Stopped AutoAttack");
        botTimer = null;
        curSpell = null;
        stopAttack = true;
    }

    @Subscribe
    private void onGameTick(GameTick event) {
        actionsThisTick = 0;

        List<Player> players = client.getPlayers();
        for (Player p : players) {
            if (isPlayerKillable(p) && !stopAttack) {
                    targetMenu = new LegacyMenuEntry("Cast " + client.getSelectedSpellName() + " -> ", "", p.getPlayerId(), SPELL_CAST_ON_PLAYER,
                            0, 0, true);

                utils.doActionMsTime(targetMenu, p.getConvexHull().getBounds(), sleepDelay());
                stop();
            }
        }
    }

    @Override
    protected void loop() {
        setSelectSpell(curSpell.getSpell());
    }

    @Subscribe
    private void onChatMessage(ChatMessage event) {
        final String msg = event.getMessage();

        /*if (event.getType() == ChatMessageType.ENGINE) {
            utils.sendGameMessage("Casted spell.");
            stop();
        }*/
        if (event.getType() == ChatMessageType.ENGINE && (msg.contains("I can't reach that"))) {
            log.info("Failed to reach target too many times, stopping");
            utils.sendGameMessage("Couldn't reach target stopping.");
            stop();
        }
    }

    /*@Subscribe
    public void onMenuEntryAdded(MenuEntryAdded event)
    {
        if (client.isMenuOpen())
        {
            return;
        }
        if (event.isForceLeftClick())
        {
            return;
        }
        if (event.getType() == MenuAction.PLAYER_SECOND_OPTION.getId()) {
            curSpell = config.selectedSpell();
            setSelectSpell(curSpell.getSpell());
            client.createMenuEntry(-1)
                    .setOption("Cast " + client.getSelectedSpellName() + " -> ")
                    .setTarget(event.getTarget())
                    .setType(SPELL_CAST_ON_PLAYER)
                    .setIdentifier(event.getIdentifier())
                    .setParam0(0)
                    .setParam1(0)
                    .setForceLeftClick(true);
        }
    }*/

    private long sleepDelay() {
        sleepLength = calc.randomDelay(config.sleepWeightedDistribution(), config.sleepMin(), config.sleepMax(), config.sleepDeviation(), config.sleepTarget());
        return calc.randomDelay(config.sleepWeightedDistribution(), config.sleepMin(), config.sleepMax(), config.sleepDeviation(), config.sleepTarget());
    }
    @Subscribe
    public void onPlayerSpawned(PlayerSpawned event) {
        if (isPlayerKillable(event.getPlayer()) && !stopAttack) {
            targetMenu = new LegacyMenuEntry("Cast " + client.getSelectedSpellName() + " -> ", "", event.getPlayer().getPlayerId(), SPELL_CAST_ON_PLAYER,
                    0, 0, true);

            utils.doActionMsTime(targetMenu, event.getPlayer().getConvexHull().getBounds(), sleepDelay());
            stop();
            //selectTarget();
        }
    }

    public boolean isPlayerKillable(Player player) {
        if (player == client.getLocalPlayer())
            return false;

        if (!PvPUtil.isAttackable(client, player))
            return false;

        //if (!isPlayerSkulled(player))
        //return false;

        if (!inWilderness())
            return false;

        return true;
    }

    public boolean inWilderness() {
        return client.getVar(Varbits.IN_WILDERNESS) == 1;
    }

    private boolean isPlayerSkulled(Player player) {
        if (player == null) {
            return false;
        }

        return player.getSkullIcon() == SkullIcon.SKULL;
    }

    /*private void selectTarget() {
        if(config.selectedTarget() == Targets.DISTANCE_TO_PLAYER && nearPlayer()) {
            targetMenu = new LegacyMenuEntry("", "", targetNPC.getIndex(), opcode, 0, 0, false);
            utils.doActionMsTime(targetMenu, targetNPC.getConvexHull().getBounds(), random(250));
        }
        if(config.selectedTarget() == Targets.SKULLED) {
            if(player.getSkullIcon() != null && player.getSkullIcon() == SkullIcon.SKULL && nearPlayer()) {

            } else {
                // no skulled players nearby attack by distance now.
            }
        }
        //if(config.selectedTarget() == Targets.WEALTH) {
            // check wealth if selected
        //}
    }*/

    private void setSelectSpell(WidgetInfo info)
    {
        Widget widget = client.getWidget(info);
        if (widget == null)
        {
            log.info("Unable to locate spell widget.");
            return;
        }
        client.setSelectedSpellName("<col=00ff00>" + widget.getName() + "</col>");
        client.setSelectedSpellWidget(widget.getId());
        client.setSelectedSpellChildIndex(-1);
    }

}
