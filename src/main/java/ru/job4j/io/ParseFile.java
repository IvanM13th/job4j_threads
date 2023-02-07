package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent() {
        return content(a -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return content(a -> a < 0x80);
    }

    private String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = in.read()) != -1) {
                if (filter.test((char) data))
                    output.append((char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public static void main(String[] args) {
        FileSaver fileSaver = new FileSaver();
        File f = Path.of("C:\\Users\\User\\Desktop\\newTextDoc.txt").toFile();
        ParseFile parseFile = new ParseFile(f);
        String out = parseFile.getContentWithoutUnicode();
        fileSaver.saveContent(out);

    }
}
