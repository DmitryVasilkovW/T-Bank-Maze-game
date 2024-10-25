package backend.academy.maze.io;

import backend.academy.maze.service.io.impl.PrintStreamPrinterImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.PrintStream;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PrintStreamPrinterImplTest {
    @Mock
    private PrintStream mockPrintStream;

    @InjectMocks
    private PrintStreamPrinterImpl printer;

    @Test
    @DisplayName("check printer")
    void testPrintln() {
        String message = "239!";

        printer.println(message);

        verify(mockPrintStream).println(message);
    }
}
