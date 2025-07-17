package de.MCmoderSD.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@SuppressWarnings("ALL")
public class GZIP {

    public static File deflate(File inputFile, File outputFile) throws IOException {
        return deflate(inputFile, outputFile, calculateBufferSize(inputFile, outputFile));
    }

    public static File inflate(File inputFile, File outputFile) throws IOException {
        return inflate(inputFile, outputFile, calculateBufferSize(inputFile, outputFile));
    }

    public static File deflate(byte[] bytes, File outputFile) throws IOException {
        return deflate(bytes, outputFile, calculateBufferSize(outputFile));
    }

    public static File inflate(byte[] bytes, File outputFile) throws IOException {
        return inflate(bytes, outputFile, calculateBufferSize(outputFile));
    }

    public static byte[] deflate(File inputFile) throws IOException {
        return deflate(inputFile, calculateBufferSize(inputFile));
    }

    public static byte[] inflate(File inputFile) throws IOException {
        return inflate(inputFile, calculateBufferSize(inputFile));
    }

    public static byte[] deflate(byte[] bytes) throws IOException {
        return deflate(bytes, calculateBufferSize());
    }

    public static byte[] inflate(byte[] bytes) throws IOException {
        return inflate(bytes, calculateBufferSize());
    }

    public static File deflate(File inputFile, File outputFile, int bufferSize) throws IOException {

        // Create Streams
        FileInputStream fileInputStream = new FileInputStream(inputFile);
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);

        // Write the input file's content to the GZIP output stream
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            gzipOutputStream.write(buffer, 0, bytesRead);
        }

        // Finish the GZIP output stream
        gzipOutputStream.finish();

        // Close Streams
        gzipOutputStream.close();
        fileInputStream.close();
        fileOutputStream.close();

        // Return the output file
        return outputFile;
    }

    public static File inflate(File inputFile, File outputFile, int bufferSize) throws IOException {

        // Create Streams
        FileInputStream fileInputStream = new FileInputStream(inputFile);
        GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

        // Read the GZIP input stream and write to the output file
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        while ((bytesRead = gzipInputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, bytesRead);
        }

        // Close all streams
        gzipInputStream.close();
        fileOutputStream.close();
        fileInputStream.close();

        // Return the output file
        return outputFile;
    }

    public static File deflate(byte[] bytes, File outputFile, int bufferSize) throws IOException {

        // Create Streams
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);

        // Write the input file's content to the GZIP output stream
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        while ((bytesRead = byteArrayInputStream.read(buffer)) != -1) {
            gzipOutputStream.write(buffer, 0, bytesRead);
        }

        // Finish the GZIP output stream
        gzipOutputStream.finish();

        // Close Streams
        gzipOutputStream.close();
        byteArrayInputStream.close();
        fileOutputStream.close();

        // Return the output file
        return outputFile;
    }

    public static File inflate(byte[] bytes, File outputFile, int bufferSize) throws IOException {

        // Create Streams
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

        // Read the GZIP input stream and write to the output file
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        while ((bytesRead = gzipInputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, bytesRead);
        }

        // Close all streams
        gzipInputStream.close();
        fileOutputStream.close();
        byteArrayInputStream.close();

        // Return the output file
        return outputFile;
    }

    public static byte[] deflate(File inputFile, int bufferSize) throws IOException {

        // Create Streams
        FileInputStream fileInputStream = new FileInputStream(inputFile);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);

        // Write the input file's content to the GZIP output stream
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            gzipOutputStream.write(buffer, 0, bytesRead);
        }

        // Finish the GZIP output stream
        gzipOutputStream.finish();

        // Close Streams
        gzipOutputStream.close();
        fileInputStream.close();
        byteArrayOutputStream.close();

        // Return the compressed byte array
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] inflate(File inputFile, int bufferSize) throws IOException {

        // Create Streams
        FileInputStream fileInputStream = new FileInputStream(inputFile);
        GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Read the GZIP input stream and write to the output file
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        while ((bytesRead = gzipInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }

        // Close all streams
        gzipInputStream.close();
        byteArrayOutputStream.close();
        fileInputStream.close();

        // Return the decompressed byte array
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] deflate(byte[] bytes, int bufferSize) throws IOException {

        // Create Streams
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);

        // Write the input file's content to the GZIP output stream
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        while ((bytesRead = byteArrayInputStream.read(buffer)) != -1) {
            gzipOutputStream.write(buffer, 0, bytesRead);
        }

        // Finish the GZIP output stream
        gzipOutputStream.finish();

        // Close Streams
        gzipOutputStream.close();
        byteArrayInputStream.close();
        byteArrayOutputStream.close();

        // Return the compressed byte array
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] inflate(byte[] bytes, int bufferSize) throws IOException {

        // Create Streams
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Read the GZIP input stream and write to the output file
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        while ((bytesRead = gzipInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }

        // Close all streams
        gzipInputStream.close();
        byteArrayOutputStream.close();
        byteArrayInputStream.close();

        // Return the decompressed byte array
        return byteArrayOutputStream.toByteArray();
    }

    private static int calculateBufferSize() throws IOException {
        return Math.toIntExact(Files.getFileStore(Paths.get("")).getBlockSize() * Runtime.getRuntime().availableProcessors());
    }

    private static int calculateBufferSize(File file) throws IOException {
        return Math.toIntExact(Files.getFileStore(file.toPath()).getBlockSize() * Runtime.getRuntime().availableProcessors());
    }

    private static int calculateBufferSize(File input, File output) throws IOException {
        var threads = Runtime.getRuntime().availableProcessors();
        var inputSize = Files.getFileStore(input.toPath()).getBlockSize();
        var outputSize = Files.getFileStore(output.toPath()).getBlockSize();
        return Math.toIntExact(Math.max(inputSize, outputSize) * threads);
    }
}