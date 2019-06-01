package com.cambeeler.Model;

public
class SongInfo
{
    private String artist;
    private String album;
    private String songName;
    private int track;

    public
    String getArtist()
    {
        return artist;
    }

    public
    void setArtist(String artist)
    {
        this.artist = artist;
    }

    public
    String getAlbum()
    {
        return album;
    }

    public
    void setAlbum(String album)
    {
        this.album = album;
    }

    public
    String getSongName()
    {
        return songName;
    }

    public
    void setSongName(String songName)
    {
        this.songName = songName;
    }

    public
    int getTrack()
    {
        return track;
    }

    public
    void setTrack(int track)
    {
        this.track = track;
    }

    public
    String toString()
    {
        return "Track#: " + this.track + ", Song name: " + this.songName +
                ", Album Name: " + this.album + ", Artist: " + this.artist;
    }
}
