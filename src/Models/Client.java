package Models;

import java.util.List;

public class Client {
    int id;
    String prenom;
    Emprunt emprunt;
    List<Amende> amendes;

    public Client(int id, String prenom) {
        this.id = id;
        this.prenom = prenom;
    }

    public int getId() {
        return id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setEmprunt(Emprunt emprunt) {
        this.emprunt = emprunt;
    }

    public void addAmendes(Amende amende) {
        this.amendes.add(amende);
    }

    public Emprunt getEmprunt() {
        return emprunt;
    }

    public List<Amende> getAmendes() {
        return amendes;
    }
}
