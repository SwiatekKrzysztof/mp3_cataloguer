package mp3Cataloguer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Song {
    private String artist;
    private String year;
    private String album;
    private String title;
    private String size;

    @Override
    public String toString() {
        return "Artist: " + artist +'\n'+
                "Year: " + year + " Album: "+ album + '\n'+
                "Title: " + title + '\n'+
                "Size(MB)" + size + '\n'+'\n'
                ;
    }
}
