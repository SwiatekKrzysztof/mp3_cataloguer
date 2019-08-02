package mp3Cataloguer.service;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import mp3Cataloguer.model.Song;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class SongsService {
    private List<Song> songs = new ArrayList<>();

    public void createCatalog(Path libraryPath, Path catalogPath) throws InvalidDataException, UnsupportedTagException {
        Mp3File mp3File;

        try (DirectoryStream<Path> stream =
                     Files.newDirectoryStream(libraryPath,"*.mp3")) {
            for (Path file: stream) {
                System.out.println(file.getFileName());
                mp3File = new Mp3File(file);
                printProperties(mp3File);
                addToList(mp3File);
            }
        } catch (IOException | DirectoryIteratorException x) {
            System.err.println(x);
        }
        createFile(catalogPath);
    }
    private void printProperties(Mp3File mp3File){
        System.out.println("Title : " + mp3File.getId3v2Tag().getTitle());
        System.out.println("Length: " + (mp3File.getLengthInSeconds()/60.0));
        System.out.println();
    }
    private void addToList(Mp3File mp3File){
        Song song = new Song(
                mp3File.getId3v2Tag().getArtist(),
                mp3File.getId3v2Tag().getYear(),
                mp3File.getId3v2Tag().getAlbum(),
                mp3File.getId3v2Tag().getTitle(),
                String.valueOf(mp3File.getLength()/(1024.0*1024.0)));
        songs.add(song);
    }
    private void createFile(Path catalogPath){
        try{
            FileWriter fw=new FileWriter(catalogPath.toFile().getPath() + "\\description.txt");
            for (int i = 0; i < songs.size(); i++) {
                fw.write(songs.get(i).toString());
            }
            fw.close();

        }catch(Exception e){System.out.println(e);}

    }

}

