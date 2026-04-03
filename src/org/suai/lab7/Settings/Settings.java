package org.suai.lab7.Settings;

import java.io.*;
import java.util.HashMap;

public class Settings {
    private final HashMap<String, Integer> data = new HashMap<>();

    public HashMap<String, Integer> getData() {
        return data;
    }

    public void put(String name, int val) {
        getData().put(name, val);
    }

    public int get(String name) {
        return getData().get(name);
    }

    public void delete(String name) {
        getData().remove(name);
    }

    public void saveToBinaryFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {

            oos.writeObject(getData());

        } catch (Exception e) {
            throw new RuntimeException("Failed to save settings", e);
        }
    }

    public void loadFromBinaryFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {

            Object obj = ois.readObject();

            if (!(obj instanceof HashMap)) {
                throw new IllegalStateException("Invalid file format");
            }

            @SuppressWarnings("unchecked") HashMap<String, Integer> loaded = (HashMap<String, Integer>) obj;

            getData().clear();
            getData().putAll(loaded);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load settings", e);
        }
    }

    public void saveToTextFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {

            for (var entry : getData().entrySet()) {
                bw.write(entry.getKey() + "=" + entry.getValue());
                bw.newLine();
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to save settings", e);
        }
    }

    public void loadFromTextFile(String filename) {
        HashMap<String, Integer> temp = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");

                if (parts.length == 2) {
                    temp.put(parts[0], Integer.parseInt(parts[1]));
                }
            }

            getData().clear();
            getData().putAll(temp);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load from text file", e);
        }
    }

    @Override
    public String toString() {
        return "Settings{" +
                "data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Settings other)) return false;
        return data.equals(other.data);
    }
}
