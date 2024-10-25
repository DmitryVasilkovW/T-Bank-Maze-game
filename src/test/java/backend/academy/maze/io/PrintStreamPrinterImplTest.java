package backend.academy.maze.io;

import backend.academy.maze.service.io.impl.PrintStreamPrinterImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.PrintStream;
import static org.mockito.Mockito.verify;

public class PrintStreamPrinterImplTest {

    @Test
    @DisplayName("check printer")
    void testPrintln() {
        PrintStream mockPrintStream = Mockito.mock(PrintStream.class);
        PrintStreamPrinterImpl printer = new PrintStreamPrinterImpl(mockPrintStream);

        String message = "239!";

        printer.println(message);

        verify(mockPrintStream).println(message);
    }
}
