package backend.academy;

import backend.academy.maze.service.launcher.factory.impl.LauncherFactoryImpl;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {

    public static void main(String[] args) {
        new LauncherFactoryImpl()
                .create()
                .launch();
    }
}
