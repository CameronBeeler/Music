package com.cambeeler.Model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.cambeeler.Model.CONSTANTS.*;

public
class DataSource
{

    private Connection conn;
    private PreparedStatement querySongInView;
    private PreparedStatement insertIntoArtists;
    private PreparedStatement insertIntoAlbums;
    private PreparedStatement insertIntoSongs;
    private PreparedStatement queryAlbum;
    private PreparedStatement queryArtist;

    public boolean open()
    {
        try{
            conn = DriverManager.getConnection(CONNECTION_STRING);
            querySongInView = conn.prepareStatement(QUERY_VIEW_SONG_INFO_PREP);
            insertIntoArtists = conn.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS);
            insertIntoAlbums = conn.prepareStatement(INSERT_ALBUMS, Statement.RETURN_GENERATED_KEYS);
            insertIntoSongs = conn.prepareStatement(INSERT_SONG);
            queryAlbum = conn.prepareStatement(QUERY_ALBUM);
            queryArtist = conn.prepareStatement(QUERY_ARTIST);
            return true;
        }catch(SQLException e)
        {
            System.out.println("Couldn't connect to database" + e.getMessage());
            return false;
        }

    }

    public void close()
    {
        try
        {
            if (querySongInView != null)
                querySongInView.close();

            if (insertIntoArtists != null)
                insertIntoArtists.close();

            if(insertIntoAlbums != null)
                insertIntoAlbums.close();

            if(insertIntoSongs != null)
                insertIntoSongs.close();

            if(queryAlbum != null)
                queryAlbum.close();

            if(queryArtist != null)
                queryArtist.close();

            if(conn!=null)
                conn.close();

        }catch(SQLException e){
            System.out.println("Connection failed to close " + e.getMessage());
        }

    }

    public
    List<Artist> queryArtists(int sortOrder)
    {
        StringBuilder sb = new StringBuilder(QUERY_ARTISTS_START);
        if(sortOrder == ORDER_BY_NONE)
        {
            sb.append(QUERY_ALBUMS_BY_ARTIST_SORT);
            if(sortOrder==ORDER_BY_DESC)
                sb.append(" DESC ");
            else
                sb.append(" ASC ");
        }
        try (Statement statement = conn.createStatement();
        ResultSet results = statement.executeQuery( sb.toString() ))
        {

            List<Artist> artists = new ArrayList<>();
            while (results.next())
            {
                Artist artist = new Artist();
                artist.setId(results.getInt(INDEX_ARTIST_ID));
                artist.setName(results.getString(INDEX_ARTIST_NAME));
                artists.add(artist);
            }

            int count = 0;
            for(int i = 0;i<artists.size(); i++)
            {
                count++;
                System.out.println(padLeft(artists.get(i).getStrId(), 5) + " | "
                                   + artists.get(i).getName());
            }
            System.out.println("Precisely " + count + " Artists accounted for");
            return artists;

        }catch (SQLException e)
        {
            System.out.println("#3 Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<Album> queryAlbums()
    {
        try (Statement statement = conn.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM albums");)
        {
            List<Album> albums = new ArrayList<>();
            while (results.next())
            {
                Album album = new Album();
                album.setId(results.getInt(INDEX_ALBUM_ID));
                album.setName(results.getString(INDEX_ALBUM_NAME));
                album.setArtist_id(results.getInt(INDEX_ALBUM_ARTIST));
                albums.add(album);
            }

            int count = 0;
            for (int i = 0; i < albums.size(); i++)
            {
                count++;
                System.out.println(padLeft(albums.get(i).getStrId(), 5) + " | "
                                   + padRight(albums.get(i).getName(), 68) + " | "
                                   + padRight(albums.get(i).getArtistStr_id(), 6));
            }
            System.out.println("Precisely " + count + " Albums accounted for");
            return albums;

        }
        catch (SQLException e)
        {
            System.out.println("#4 Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<Song> querySongs()
    {
        try( Statement statement = conn.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM songs");)
        {
            List<Song> songs = new ArrayList<>();
            while (results.next())
            {
                Song song = new Song();
                song.setId(results.getInt(INDEX_SONGS_ID));
                song.setName(results.getString(INDEX_SONGS_TITLE));
                song.setTrack(results.getInt(INDEX_SONGS_TRACK));
                song.setAlbum_id(results.getInt(INDEX_SONGS_ALBUM_ID));
                songs.add(song);
            }

            int count = 0;
            for (int i = 0; i < songs.size(); i++)
            {
                count++;
                System.out.println(padLeft(songs.get(i).getStrId(), 5) + " | "
                                    + padLeft(songs.get(i).getAlbumStr_id(), 5) + " | "
                                    + padLeft(songs.get(i).getStrTrack(), 4) + " | "
                                    + songs.get(i).getName() );
            }
            System.out.println("Precisely " + count + " Songs accounted for");
            return songs;

        }
        catch (SQLException e)
        {
            System.out.println("#5 Query failed: " + e.getMessage());
            return null;
        }
    }

    public static
    String padLeft(String s, int n)
    {
//        int    length = n - s.length() ;
        String x      = String.format("%" + n + "s", s);
        return x;
    }


    public static
    String padRight(String s, int n)
    {
//        int    length = n - s.length() ;
        String x      = String.format("%-" + n + "s", s);
        return x;
    }

    public List<String> queryAlbumsForArtist(String artistName, int sortOrder)
    {
        List<String> albums = new ArrayList<>();

        StringBuilder sb = new StringBuilder (QUERY_ALBUMS_BY_ARTIST_START);
        sb.append(artistName);
        sb.append("\"");
        if (sortOrder != ORDER_BY_NONE)
        {
            sb.append(QUERY_ALBUMS_BY_ARTIST_SORT);
            if(sortOrder==ORDER_BY_DESC){
                sb.append(" DESC ");
            }
            else {
                sb.append(" ASC ");
            }
        }

        System.out.println("SQL statement = " + sb.toString());

        try(Statement stmt = conn.createStatement();
        ResultSet sqlResults = stmt.executeQuery(sb.toString());)
        {
            while(sqlResults.next())
            {

                albums.add(sqlResults.getString(1));
            }
            for(int i=0;i<albums.size();i++)
            {
                System.out.println(albums.get(i).toString());
            }

        }catch(SQLException e)
        {
            System.out.println("Failure in queryAlbumsForArtist " + e.getMessage());
            return null;
        }
        return albums;
    }

    public List<SongInfo> queryAlbumAndArtistBySong(String song)
    {
        List<SongInfo> queryInfo = new ArrayList<>();

        StringBuilder sb = new StringBuilder(QUERY_SONG_ARTIST_ALBUM_TRACK_START);
        sb.append(song);
        sb.append("\" ");

//        System.out.println(sb.toString());
        try(Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(sb.toString()))
        {
            StringBuilder record = new StringBuilder();
            while(rset.next())
            {
                SongInfo si = new SongInfo();
                si.setArtist(rset.getString(1));
                si.setSongName(rset.getString(2));
                si.setAlbum(rset.getString(3));
                si.setTrack(rset.getInt(4));
                queryInfo.add(si);
            }

        }catch (SQLException e)
        {
            System.out.println("queryAlbumAndArtistBySong " + e.getMessage());
            return null;
        }
        return queryInfo;
    }

    public void querySongsMetaData()
    {
        String sql = "SELECT * FROM " + TABLE_SONGS;
        try(Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery(sql))
        {
            ResultSetMetaData meta = results.getMetaData();
            int numColumns = meta.getColumnCount();
            for (int i=1;i<=numColumns;i++)
            {
                System.out.format("Columns %d in the songs table names %s\n",
                                   i, meta.getColumnName(i));
            }
        }catch(SQLException e)
        {
            System.out.println("querySongsMetaData " + e.getMessage() );
        }
    }

    public int getCount(String table)
    {
        String sql = "SELECT COUNT(*) AS count FROM " + table ;
        try(Statement statement = conn.createStatement();
        ResultSet results = statement.executeQuery(sql))
        {
            int count = results.getInt("count");
//            int count = results.getInt(1); // if the column is not named, then use the return ref
            System.out.format("Count = %d\n", count);
            return count;
        } catch(SQLException e)
        {
            System.out.println("#6 Query failed " + e.getMessage());
            return -1;
        }
    }

    public boolean createViewForSongArtist()
    {
        try(Statement statement = conn.createStatement())
        {
            statement.executeQuery(CREATE_ARTIST_FOR_SONG_VIEW);
            return true;
        } catch(SQLException e)
        {
            System.out.println("#7 Query failed " + e.getMessage());
            return false;
        }
    }

    public List<SongInfo> querySongInfoView(String title)
    {
        StringBuilder sb = new StringBuilder(QUERY_VIEW_SONG_INFO);
        sb.append(title);
        sb.append("\"");
        System.out.println(sb.toString());

//        try (Statement statement = conn.createStatement();
//        ResultSet results = statement.executeQuery(sb.toString()))
        try
        {
            querySongInView.setString(1,title); // the 1st param is a placeholder position...
            ResultSet results = querySongInView.executeQuery();

            List<SongInfo> songArtist = new ArrayList<>();
            while(results.next())
            {
                SongInfo si = new SongInfo();
                si.setSongName(title);
                si.setArtist(results.getString(1));
                si.setAlbum(results.getString(2));
                si.setTrack(results.getInt(3));
                songArtist.add(si);
            }
            return songArtist;
        }
        catch (SQLException e)
        {
            System.out.println("#1 Query failed " + e.getMessage());
            return null;
        }
    }

    private int insertArtist(String artistName) throws SQLException
    {
        queryArtist.setString(1, artistName);
        ResultSet results = queryArtist.executeQuery();
        if(results.next())
            return results.getInt(1);
        else
        {
            insertIntoArtists.setString(1, artistName);
            int affectedRows = insertIntoArtists.executeUpdate();
            if (affectedRows != 1)
                throw new SQLException("Could not insert an Artist record");
            ResultSet generatedKeys = insertIntoArtists.getGeneratedKeys();
            if (generatedKeys.next())
                return generatedKeys.getInt(1);
            else
                throw new SQLException("Could not acquire _id for artist");
        }
    }

    private int insertAlbum(int artistID, String album) throws SQLException
    {
        System.out.println("In the insertAlbum method");
        queryAlbum.setString(1,album); // the 1st param is a placeholder position...
        queryAlbum.setInt(2, artistID); // the 2nd param is a placeholder position...
        ResultSet results = queryAlbum.executeQuery();
        if(results.next())
        {
            System.out.println("found the album and returning");
            return results.getInt(1);
        }
        else
        {
            System.out.println("Did not find the album, creating...");
            insertIntoAlbums.setString(1, album);
            insertIntoAlbums.setInt(2  , artistID);
            System.out.println("Album name " + album + "\nArtist ID " + artistID);
            int affectedRows = insertIntoAlbums.executeUpdate();
            if(affectedRows != 1)
            {
                System.out.println("Did not successfully create the album.  affected Rows = " + affectedRows);
                throw new SQLException("Could not create a new album");
            }
            else
            {
                ResultSet generatedKeys = insertIntoAlbums.getGeneratedKeys();
                if(generatedKeys.next())
                {
                    System.out.println("There were generated keys - " + generatedKeys);
                    return generatedKeys.getInt(1);
                }
                else
                {
                    System.out.println("There were NO generated keys");
                    throw new SQLException("Could not acquire the album _id");
                }
            }
        }


    }

    public boolean AddNewSongTransaction(String songName, String artistName, String albumName, int trackNbr)
    {
/* artist name, Album name, Song track#, Song title, Album ID */
        try
        {
            conn.setAutoCommit(false);
            int artistID = insertArtist(artistName);
            if (artistID != 0)
            {
                System.out.println("Successfully found or added artist " + artistName);
            }
            int albumID = insertAlbum(artistID, albumName);
            System.out.println("albumID = " + albumID);
            if (albumID != 0)
            {
                System.out.println("Successfully found or added album " + albumName);
            }

            insertIntoSongs.setInt(1, trackNbr);
            insertIntoSongs.setString(2, songName);
            insertIntoSongs.setInt(3, albumID);
            int affectedRows = insertIntoSongs.executeUpdate();

            if(affectedRows == 1)
                conn.commit();
            else
                throw new SQLException("Failed to insert the song");
        }
        catch(Exception e)
        {
            System.out.println("Failed to enter the song" + e.getMessage());
            try
            {
                System.out.println("Performing rollback");
                conn.rollback();
                return false;
            }
            catch(SQLException e2)
            {
                System.out.println(e2.getMessage() + " -> failed to execute rollback");
            }
        }
        finally
        {
            System.out.println("In the finally loop");
            try
            {
                conn.setAutoCommit(true);
                return true;
            }catch (SQLException e3)
            {
                System.out.println(" Failed to restore autocommit " + e3.getMessage());
            }
          return false;
        }
    }

}
