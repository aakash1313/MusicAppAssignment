package ideatree.akashdeep.com.ideatreeassignment.model;

/**
 * Created by akash on 4/27/2018.
 */

public class SongDetail {

    private String songArtWorkUrl;
    private String songName;
    private String collectionName;
    private String genre;
    private String playUrl;
    private String trackViewUrl;
    private String collectionViewUrl;


    private String trackPrice;
    private String collectionPrice;


    private boolean isPlaying;

    public SongDetail() {
        this.songArtWorkUrl = null;
        this.songName = null;
        this.collectionName = null;
        this.playUrl = null;
        this.genre = null;
        this.isPlaying = false;
        this.trackPrice = null;
        this.collectionPrice = null;
    }

    public String getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(String trackPrice) {
        this.trackPrice = trackPrice;
    }

    public String getTrackViewUrl() {
        return trackViewUrl;
    }

    public void setTrackViewUrl(String trackViewUrl) {
        this.trackViewUrl = trackViewUrl;
    }

    public String getCollectionViewUrl() {
        return collectionViewUrl;
    }

    public void setCollectionViewUrl(String collectionViewUrl) {
        this.collectionViewUrl = collectionViewUrl;
    }

    public String getCollectionPrice() {
        return collectionPrice;
    }

    public void setCollectionPrice(String collectionPrice) {
        this.collectionPrice = collectionPrice;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSongArtWorkUrl() {
        return songArtWorkUrl;
    }

    public void setSongArtWorkUrl(String songArtWorkUrl) {
        this.songArtWorkUrl = songArtWorkUrl;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }


}
