package pene.gc.importer.objects;

import java.util.List;

public class Settings {
    public static class Parameters{
        public boolean Weapons;
        public boolean Artifacts;
        public boolean Characters;
        public boolean Materials;
        public List<Integer> WeaponRarity;
        public List<Integer> ArtifactRarity;
        public int WeaponLimit;
        public int ArtifactLimit;
        public int CharacterLimit;
        public int MaterialLimit;
    }
}
