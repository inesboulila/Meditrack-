package tn.meditrackplus.meditrack;

public class Medication {
    int id;
    String name;
    String time;

    public Medication(int id, String name, String time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    @Override
    public String toString() {
        return name + " at " + time;
    }
}