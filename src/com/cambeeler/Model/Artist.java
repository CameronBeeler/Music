package com.cambeeler.Model;

public
class Artist
{
    private int id;
    private String name;

    Artist()
    {
    }

    public
    String getStrId()
    {
        Integer int2String = this.id;
        return int2String.toString();
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
}
