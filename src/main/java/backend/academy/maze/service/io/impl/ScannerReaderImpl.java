package backend.academy.maze.service.io.impl;

import backend.academy.maze.service.io.Reader;
import java.io.InputStream;
import java.util.Scanner;

public class ScannerReaderImpl implements Reader {
    private final Scanner scanner;

    public ScannerReaderImpl(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public String readLineAsString() {
        return scanner.nextLine();
    }
}
