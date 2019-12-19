package fr.efrei.project.map;

public class Case {

    private static int global_mapid = 0;

    private String id;

    public Case()
    {
        this.id = "map_" + String.valueOf(++global_mapid);
    }

    public String getId() {
        return id;
    }
}
