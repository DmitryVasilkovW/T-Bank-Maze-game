package backend.academy;

import backend.academy.maze.service.launcher.factory.impl.MazeLauncherFactoryImpl;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {

    public static void main(String[] args) {
        new MazeLauncherFactoryImpl()
                .create()
                .launch();
    }
}
