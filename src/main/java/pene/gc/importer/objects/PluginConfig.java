package pene.gc.importer.objects;

import java.util.List;

public final class PluginConfig {
    public boolean Weapons = false;
    public int WeaponLimit = 0;
    public List<Integer> WeaponRarity = List.of(1,2,3,4,5);
    public boolean equipWeapon = false;
    public boolean Artifacts = false;
    public int ArtifactLimit = 0;
    public List<Integer> ArtifactRarity = List.of(1,2,3,4,5);
    public boolean equipArtifact = false;
    public boolean Characters = false;
    public int CharacterLimit = 0;
    public List<Integer> CharacterRarity = List.of(4,5);
    public boolean Materials = false;
    public int MaterialLimit = 0;



}
