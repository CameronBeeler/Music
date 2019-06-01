package com.cambeeler.Model;

import java.util.List;

public
class Album
{
    private int id;
    private String name;
    private int artist_id;

    public
    String getStrId()
    {
        Integer int2Str = this.id;
        return int2Str.toString();

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
    String getArtistStr_id()
    {
        Integer int2Str = this.artist_id;
        return int2Str.toString();

    }

    public
    int getArtist_id()
    {
        return artist_id;
    }

    public
    void setArtist_id(int artist_id)
    {
        this.artist_id = artist_id;
    }
}
