public class Station {
    private int zone;
    private String name;
    public Station(){
        name = "";
        zone = 0;
    }
    public Station(String stName, int zn){
        zone = zn;
        name = stName;
    }
    public int getZone(){
        return zone;
    }
    public String getName(){
        return name;
    }
    public String toString(){
        return "station name is: " + name + ", the zone is: " + zone;
    }
}