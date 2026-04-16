package model;

public class Fotograf extends Furnizor {
    // pret (mostenit din Furnizor) reprezinta costul pachetului standard de baza (fotograful + editarea pozelor)
    // serviciile video sau albumele fizice se adauga ca taxe extra, prin pretVideo si pretAlbum
    private boolean oferaVideo;
    private double pretVideo;
    // daca ofera album fizic si costul acestuie
    private boolean oferaAlbum;
    private double pretAlbum;
    private int timpLivrare; // in cate zile trimite pozele/albumele

    public Fotograf(int id, String nume, String telefon, String email, double pret, double rating, boolean oferaVideo, double pretAlbum, int timpLivrare) {
        super(id, nume, telefon, email, pret, rating);
        this.oferaVideo = oferaVideo;
        this.pretVideo = pretVideo;
        this.oferaAlbum = oferaAlbum;
        this.pretAlbum = pretAlbum;
        this.timpLivrare = timpLivrare;
    }

    // setteri & getteri
    public boolean isOferaVideo() { return oferaVideo;}
    public void setOferaVideo(boolean oferaVideo) { this.oferaVideo = oferaVideo;}

    public double getPretVideo() { return pretVideo;}
    public void setPretVideo(double pretVideo) { this.pretVideo = pretVideo;}

    public boolean isOferaAlbum() { return oferaAlbum;}
    public void setOferaAlbum(boolean oferaAlbum) { this.oferaAlbum = oferaAlbum;}

    public double getPretAlbum() { return pretAlbum;}
    public void setPretAlbum(double pretAlbum) { this.pretAlbum = pretAlbum;}

    public int getTimpLivrare() { return timpLivrare;}
    public void setTimpLivrare(int timpLivrare) { this.timpLivrare = timpLivrare;}

    // afisare
    @Override
    public String toString(){
        return "Fotograf: " + super.toString() +
                " | Video: " + (oferaVideo ? "Da (" + pretVideo + "RON)" : "Nu") +
                " | Album Fizic: " + (oferaAlbum ? "Da (" + pretAlbum + "RON)" : "Nu") +
                " | Livrare: " +  timpLivrare;
    }
}
