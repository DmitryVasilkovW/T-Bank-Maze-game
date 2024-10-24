package backend.academy.maze.service.io.impl;

import backend.academy.maze.service.io.Printer;
import java.io.PrintStream;

public class PrintStreamPrinterImpl implements Printer {
    private final PrintStream printStream;

    public PrintStreamPrinterImpl(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void println(String message) {
        printStream.println(message);
    }
}
