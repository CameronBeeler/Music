package com.cambeeler;

import com.cambeeler.Model.CONSTANTS;
import com.cambeeler.Model.DataSource;
import com.cambeeler.Model.SongInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.cambeeler.Model.CONSTANTS.ORDER_BY_ASC;
import static com.cambeeler.Model.CONSTANTS.ORDER_BY_DESC;

public class Main {

    public static void main(String[] args) {

        Main m = new Main();
        DataSource ds = new DataSource();
        if(!ds.open())
        {
            System.out.println("Cannot open our datasource");
            return;
        }
        ds.AddNewSongTransaction("Good Ole Boys Like Me", "Don Williams", "Portrait", 1);
//        m.insertANewSong();
//        m.findSongInfo();
//        ds.queryArtists(ORDER_BY_ASC);
//        ds.queryAlbums();
//        ds.querySongs();
//        ds.queryAlbumsForArtist("Carole King", ORDER_BY_ASC);
//        ds.queryAlbumsForArtist("Iron Maiden", ORDER_BY_ASC);
//        ds.queryAlbumsForArtist("Black Sabbath", ORDER_BY_ASC);
//        ds.queryAlbumsForArtist("Pink Floyd", ORDER_BY_DESC);
//        List<SongInfo> lsi = new ArrayList<>(); // error here?
//        lsi = ds.queryAlbumAndArtistBySong("Evil Woman");
//        lsi = ds.queryAlbumAndArtistBySong("Go Your Own Way");
//        for(int i=0; i< lsi.size();i++)
//        {
//            System.out.println(lsi.get(i).toString());
//        }
//        ds.querySongsMetaData();

//        int count = ds.getCount(CONSTANTS.TABLE_SONGS);
//        System.out.println("Number of songs is " + count);
//        ds.createViewForSongArtist();

        ds.close();
    }

    public void findSongInfo()
    {
        DataSource ds = new DataSource();
        Scanner scan = new Scanner(System.in);
        String song = scan.nextLine();
        System.out.println(song);
        List<SongInfo> list = ds.querySongInfoView(song);
        if (list.isEmpty())
        {
            System.out.println("Could not find any artists for the song");
            return;
        }
        for (SongInfo si : list)
        {
            System.out.println(si);
        }
    }

    public void insertANewSong()
    {
    }
}
