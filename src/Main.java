import org.suai.lab7.Settings.Settings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class Main {
    public static void main() throws IOException {

        Settings settings = new Settings();
        settings.put("LeftHanded", 1);
        System.out.println("Setting: " + settings);

        Files.createDirectories(Path.of("files"));
        String path1 = "files/test_text2";
        String path2 = "files/test_bin";
        String path3 = "files/test_text2";

        settings.saveToTextFile(path1);
        settings.saveToBinaryFile(path2);

        Settings newSetting = new Settings();
        newSetting.loadFromBinaryFile(path2);
        newSetting.saveToTextFile(path3);

        System.out.println("newSetting: " + newSetting);
        System.out.println();

        System.out.println("Settings is equals newSettings: " + settings.equals(newSetting));

        long diff = Files.mismatch(Path.of(path1), Path.of(path3));

        if (diff == -1) {
            System.out.println("Files are equal");
        } else {
            System.out.println("Files differ at byte: " + diff);
        }
    }
}
