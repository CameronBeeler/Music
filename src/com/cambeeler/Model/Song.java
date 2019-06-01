package com.cambeeler.Model;

public
class Song
{
    private int id;
    private String name;
    private int album_id;
    private int track;

    public
    String getStrTrack()
    {
        Integer intVal = this.track;
        return intVal.toString();
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
    String getStrId(){
        Integer intVal = this.id;
        return intVal.toString();
    }

    public
    int getId()
    {
        return id;
    }

    public
    void setId(int id)
    {
        this.id = id;
    }

    public
    String getName()
    {
        return name;
    }

    public
    void setName(String name)
    {
        this.name = name;
    }

    public
    String getAlbumStr_id()
    {
        Integer intVal = this.album_id;
        return intVal.toString();
    }
    public
    int getAlbum_id()
    {
        return album_id;
    }

    public
    void setAlbum_id(int album_id)
    {
        this.album_id = album_id;
    }
}
