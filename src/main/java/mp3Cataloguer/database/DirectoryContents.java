package mp3Cataloguer.database;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.util.Iterator;

public class DirectoryContents implements DirectoryStream {
    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
