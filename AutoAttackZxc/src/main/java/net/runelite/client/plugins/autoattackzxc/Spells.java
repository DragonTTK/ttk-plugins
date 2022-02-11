package net.runelite.client.plugins.autoattackzxc;


import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.widgets.WidgetInfo;

@Getter
@AllArgsConstructor
public enum Spells
{
    BIND("Entangle", WidgetInfo.SPELL_ENTANGLE),
    ICE_BARRAGE("Ice Barrage", WidgetInfo.SPELL_ICE_BARRAGE),
    TELE_BLOCK("Tele Block", WidgetInfo.SPELL_TELE_BLOCK);

    private String name;
    private WidgetInfo spell;

    @Override
    public String toString()
    {
        return getName();
    }
}
