package org.suai.lab7.PhoneDictionary;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class PhoneDictionary {
    private final HashMap<Person, PhoneNumber> data = new HashMap<>();

    public HashMap<Person, PhoneNumber> getData() {
        return data;
    }

    public void put(Person person, PhoneNumber pn) {
        getData().put(person, pn);
    }

    public PhoneNumber get(Person person) {
        return getData().get(person);
    }

    public void delete(Person person) {
        getData().remove(person);
    }

    public void saveToBinaryFile(String filename) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            for (var entry : getData().entrySet()) {
                Person p = entry.getKey();
                byte[] nameBytes = p.getName().getBytes(StandardCharsets.UTF_8);

                dos.writeByte(nameBytes.length);
                dos.write(nameBytes);
                dos.writeByte(p.getAge());
                dos.writeInt(entry.getValue().getNumber());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save phone dictionary", e);
        }
    }

    public void loadFromBinaryFile(String filename) {
        HashMap<Person, PhoneNumber> temp = new HashMap<>();

        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            while (dis.available() > 0) {
                int nameLen = dis.readByte() & 0xFF;
                byte[] nameBytes = dis.readNBytes(nameLen);
                String name = new String(nameBytes, StandardCharsets.UTF_8);
                byte age = dis.readByte();
                int number = dis.readInt();

                temp.put(new Person(name, age), new PhoneNumber(number));
            }

            getData().clear();
            getData().putAll(temp);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load phone dictionary", e);
        }
    }

    public void saveToTextFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (var entry : getData().entrySet()) {
                Person p = entry.getKey();
                bw.write(p.getName() + "," + p.getAge() + "=" + entry.getValue().getNumber());
                bw.newLine();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save phone dictionary to text file", e);
        }
    }

    public void loadFromTextFile(String filename) {
        HashMap<Person, PhoneNumber> temp = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length != 2) continue;

                String[] personParts = parts[0].split(",");
                if (personParts.length != 2) continue;

                String name = personParts[0];
                byte age = Byte.parseByte(personParts[1]);
                int number = Integer.parseInt(parts[1]);

                temp.put(new Person(name, age), new PhoneNumber(number));
            }

            getData().clear();
            getData().putAll(temp);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load phone dictionary from text file", e);
        }
    }

    @Override
    public String toString() {
        return "PhoneDictionary{" + "data=" + data + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PhoneDictionary other)) return false;
        return data.equals(other.data);
    }

    public static class PhoneNumber {
        private final int number;

        public PhoneNumber(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }

        @Override
        public String toString() {
            return String.valueOf(number);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof PhoneNumber other)) return false;
            return number == other.number;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(number);
        }
    }

    public static class Person {
        private String name;
        private byte age;

        public Person(String name, byte age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public byte getAge()    { return age; }

        public void setName(String name) { this.name = name; }
        public void setAge(byte age)     { this.age = age; }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Person other)) return false;
            return age == other.age && name.equals(other.name);
        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + age;
            return result;
        }
    }
}