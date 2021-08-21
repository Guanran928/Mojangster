package i.am.cal.mojangster.client;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Prelaunch implements PreLaunchEntrypoint {

    /**
     * Copy a file from source to destination.
     *
     * @param source
     *        the source
     * @param destination
     *        the destination
     * @return True if succeeded , False if not
     */
    public static boolean copy(InputStream source , String destination) {
        boolean succeess = true;

        System.out.println("Copying ->" + source + "\n\tto ->" + destination);

        try {
            Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            succeess = false;
            ex.printStackTrace();
        }

        return succeess;

    }
    public static final Path gameDir = FabricLoader.getInstance().getGameDir();
    public static final Path mojankDir = Paths.get(gameDir.toString(), "/mojank");
    public static final Path pngPath = Paths.get(mojankDir.toString(), "/ms.png");

    @Override
    public void onPreLaunch() {
        try {
            if(Files.exists(mojankDir)) {
                return;
            }
            Path dirs = Files.createDirectories(Paths.get(gameDir.toString(), "mojank"));
            Files.writeString(Paths.get(dirs.toString(), "readme.txt"), "This folder is used by mojangster for the animated loading screens.\n" +
                    "Only remove this folder if you have disabled or uninstalled mojangster.");
            copy(Prelaunch.class.getResourceAsStream("/mojangster/mojank.png"), Paths.get(dirs.toString(), "ms.png").toString());
            copy(Prelaunch.class.getResourceAsStream("/mojangster/ms.png.mcmeta"), Paths.get(dirs.toString(), "ms.png.mcmeta").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
