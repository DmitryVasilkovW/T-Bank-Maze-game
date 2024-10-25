package backend.academy.maze.io;

import backend.academy.maze.service.io.impl.ScannerReaderImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ScannerReaderImplTest {

    @Test
    @DisplayName("checking reading single line")
    void testReadLineAsString() {
        String input = "239";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ScannerReaderImpl reader = new ScannerReaderImpl(inputStream);

        String result = reader.readLineAsString();

        assertEquals("239", result);
    }

    @Test
    @DisplayName("checking reading multiple lines")
    void testReadMultipleLines() {
        String input = "First line\nSecond line\nThird line";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        ScannerReaderImpl reader = new ScannerReaderImpl(inputStream);

        String firstLine = reader.readLineAsString();
        assertEquals("First line", firstLine);

        String secondLine = reader.readLineAsString();
        assertEquals("Second line", secondLine);

        String thirdLine = reader.readLineAsString();
        assertEquals("Third line", thirdLine);
    }
}
