import java.io.*;

import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        File savegamesDir = new File("F://Game/savegames/");
        String savegamesPath = "F://Game/savegames/";

        GameProgress progress1 = new GameProgress(100, 1, 5, 211.22);
        GameProgress progress2 = new GameProgress(80, 3, 15, 1458.34);
        GameProgress progress3 = new GameProgress(55, 5, 31, 5351.87);

        saveGame(savegamesPath, progress1, progress2, progress3);

        String zipPath = savegamesPath + "ZipSaves.zip";
        zipFiles(zipPath, Objects.requireNonNull(savegamesDir.listFiles()));
    }

    public static void saveGame(String path, GameProgress... gameProgress) {
        for (int i = 0; i < gameProgress.length; i++) {
            try (FileOutputStream outStream = new FileOutputStream(path + "save" + (i + 1) + ".dat");
                 ObjectOutputStream outObj = new ObjectOutputStream(outStream)) {
                outObj.writeObject(gameProgress[i]);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static void zipFiles(String zipPath, File[] filesToZip) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (File file : filesToZip) {
                try (FileInputStream zipInput = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[zipInput.available()];
                    zipInput.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                }
               file.delete();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
