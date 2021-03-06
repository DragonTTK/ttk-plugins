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
package net.runelite.client.plugins.motherloadzxc;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.iutils.*;
import org.pf4j.Extension;

import javax.inject.Inject;

import static net.runelite.api.MenuAction.SPELL_CAST_ON_PLAYER;

@Extension
@PluginDependency(iUtils.class)
@PluginDescriptor(
        name = "Motherload Zxc",
        enabledByDefault = false,
        description = "Start inside motherload mine with pickaxe equipped.",
        tags = {"zxc", "mythical", "dragonttk", "auto", "bot", "motherload", "minigame"}
)
@Slf4j
public class MotherloadZxcPlugin extends Plugin {

    @Inject
    private MotherloadZxcConfig config;

    @Inject
    private iUtils utils;

    @Inject
    private Client client;

    Player player;
    LegacyMenuEntry targetMenu;
    public boolean startBot;


    private final WorldPoint ROCKFALL_1 = new WorldPoint(3733, 5680, 0);
    private final WorldPoint ROCKFALL_2 = new WorldPoint(3731, 5683, 0);
    private final WorldPoint ROCKFALL_3 = new WorldPoint(3727, 5683, 0);
    private final WorldPoint ROCKFALL_4 = new WorldPoint(3726, 5655, 0);
    private final WorldPoint ROCKFALL_5 = new WorldPoint(3727, 5652, 0);

    private final WorldPoint HOPPER = new WorldPoint(3749, 5672, 0);

    @Provides
    MotherloadZxcConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(MotherloadZxcConfig.class);
    }

    @Override
    protected void startUp() {
        //chinBreakHandler.registerPlugin(this);
    }

    @Override
    protected void shutDown() {
        stopPlugin();
        //chinBreakHandler.unregisterPlugin(this);
    }

    @Subscribe
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
        if (startBot) {

        }
    }

    @Subscribe
    private void onConfigButtonPressed(ConfigButtonClicked configButtonClicked) {
        if (!configButtonClicked.getGroup().equalsIgnoreCase("MotherloadZxc")) {
            return;
        }
        log.info("button {} pressed!", configButtonClicked.getKey());
        switch (configButtonClicked.getKey()) {
            case "startButton":
                if (!startBot) {
                    startBot = true;
                    //chinBreakHandler.startPlugin(this);
                    startPlugin();
                } else {
                    stopPlugin();
                }
                break;
        }
    }

    private void stopPlugin() {
        log.debug("Stopping auto attack zxc.");
        //chinBreakHandler.stopPlugin(this);
        startBot = false;
    }

    private void startPlugin() {
        log.debug("Starting auto attack zxc.");
        if (client == null || client.getLocalPlayer() == null || client.getGameState() != GameState.LOGGED_IN) {
            log.info("startup failed, log in before starting");
            return;
        }
        //chinBreakHandler.startPlugin(this);
        startBot = true;
    }

    @Subscribe
    private void onGameTick(GameTick tick) {
        if (!startBot /*|| chinBreakHandler.isBreakActive(this)*/) {
            return;
        }
        player = client.getLocalPlayer();
        if (client != null && player != null && client.getGameState() == GameState.LOGGED_IN) {
            if (!client.isResized()) {
                utils.sendGameMessage("Please set client to resizeable.");
                startBot = false;
                return;
            }
        }
    }
}
