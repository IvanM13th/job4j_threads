package ru.job4j.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NioDemo {
    public static void main(String[] args) {
        int count;
        /**
         * newByteChannel создает новй канал,
         * при помощи allocate() создается буффер,
         * далее в цикле читает данные в буфер, пока не достигнут конец файла (-1)
         */
        try (SeekableByteChannel byteChannel = Files.newByteChannel(Paths.get("data/nio.txt"))) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            do {
                count = byteChannel.read(buffer);
                /**
                 * проверяем, что в файле еще есть данные.
                 * вызываем метод rewind, который установит курсор в нулевую позицию
                 * тк после вызовы метода read курсор будет находится в конце буфера
                 */
                        if (count != -1) {
                            buffer.rewind();
                            /**
                             * выводим данные в консоль в виде символов
                             */
                            for (int i = 0; i < count; i++) {
                                System.out.print((char) buffer.get());
                            }
                        }
            } while (count != -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
