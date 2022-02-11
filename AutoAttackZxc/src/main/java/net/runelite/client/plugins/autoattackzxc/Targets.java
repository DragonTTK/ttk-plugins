package net.runelite.client.plugins.autoattackzxc;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Targets
{
    WEALTH("Wealth"),
    SKULLED("Skulled"),
    DISTANCE_TO_PLAYER("Distances");

    private String name;

    @Override
    public String toString()
    {
        return getName();
    }
}
