import org.suai.lab7.PhoneDictionary.PhoneDictionary;
import org.suai.lab7.Settings.Settings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class Main {
    public static void main() throws IOException {

        Settings settings = new Settings();
        settings.put("myage", 19);
        System.out.println("Setting: " + settings);

        Files.createDirectories(Path.of("files"));
        Files.createDirectories(Path.of("files/settings"));
        String path1 = "files/settings/test_text";
        String path2 = "files/settings/test_bin";
        String path3 = "files/settings/test_text2";

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

        System.out.println();


        PhoneDictionary dictionary = new PhoneDictionary();
        dictionary.put(new PhoneDictionary.Person("Vasya",   (byte) 12), new PhoneDictionary.PhoneNumber(824838428));
        System.out.println("Dictionary: " + dictionary);

        Files.createDirectories(Path.of("files/dictionary"));
        String path4 = "files/dictionary/dict_text";
        String path5 = "files/dictionary/dict_bin";
        String path6 = "files/dictionary/dict_text2";

        dictionary.saveToTextFile(path4);
        dictionary.saveToBinaryFile(path5);

        PhoneDictionary newDictionary = new PhoneDictionary();
        newDictionary.loadFromBinaryFile(path5);
        newDictionary.saveToTextFile(path6);

        System.out.println("newDictionary: " + newDictionary);
        System.out.println();

        System.out.println("Dictionary is equals newDictionary: " + dictionary.equals(newDictionary));

        long diff2 = Files.mismatch(Path.of(path4), Path.of(path6));

        if (diff2 == -1) {
            System.out.println("Files are equal");
        } else {
            System.out.println("Files differ at byte: " + diff2);
        }
        System.out.println("dict_text size: " + Files.size(Path.of(path4)) + " bytes");
        System.out.println("dict_bin  size: " + Files.size(Path.of(path5)) + " bytes");
    }
}