package de.MCmoderSD.tools;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@SuppressWarnings("ALL")
public class GZIP {

    /**
     * Compresses the given input file and writes the result to the specified output file.
     *
     * @param inputFile the file to compress
     * @param outputFile the file to write the compressed data to
     * @return the compressed output file
     * @throws IOException if an I/O error occurs
     */
    public static File deflate(File inputFile, File outputFile) throws IOException {
        return deflate(inputFile, outputFile, calculateBufferSize(inputFile, outputFile));
    }

    /**
     * Decompresses the given input file and writes the result to the specified output file.
     *
     * @param inputFile the GZIP-compressed input file
     * @param outputFile the file to write the decompressed data to
     * @return the decompressed output file
     * @throws IOException if an I/O error occurs
     */
    public static File inflate(File inputFile, File outputFile) throws IOException {
        return inflate(inputFile, outputFile, calculateBufferSize(inputFile, outputFile));
    }

    /**
     * Compresses the given byte array and writes the result to the specified output file.
     *
     * @param bytes the input byte array to compress
     * @param outputFile the file to write the compressed data to
     * @return the compressed output file
     * @throws IOException if an I/O error occurs
     */
    public static File deflate(byte[] bytes, File outputFile) throws IOException {
        return deflate(bytes, outputFile, calculateBufferSize(outputFile));
    }

    /**
     * Decompresses the given byte array and writes the result to the specified output file.
     *
     * @param bytes the GZIP-compressed input data
     * @param outputFile the file to write the decompressed data to
     * @return the decompressed output file
     * @throws IOException if an I/O error occurs
     */
    public static File inflate(byte[] bytes, File outputFile) throws IOException {
        return inflate(bytes, outputFile, calculateBufferSize(outputFile));
    }

    /**
     * Compresses the given input file and returns the result as a byte array.
     *
     * @param inputFile the file to compress
     * @return a byte array containing the compressed data
     * @throws IOException if an I/O error occurs
     */
    public static byte[] deflate(File inputFile) throws IOException {
        return deflate(inputFile, calculateBufferSize(inputFile));
    }

    /**
     * Decompresses the given input file and returns the result as a byte array.
     *
     * @param inputFile the GZIP-compressed input file
     * @return a byte array containing the decompressed data
     * @throws IOException if an I/O error occurs
     */
    public static byte[] inflate(File inputFile) throws IOException {
        return inflate(inputFile, calculateBufferSize(inputFile));
    }

    /**
     * Compresses the given byte array and returns the result as a new byte array.
     *
     * @param bytes the input data to compress
     * @return the compressed byte array
     * @throws IOException if an I/O error occurs
     */
    public static byte[] deflate(byte[] bytes) throws IOException {
        return deflate(bytes, calculateBufferSize());
    }

    /**
     * Decompresses the given byte array and returns the result as a new byte array.
     *
     * @param bytes the GZIP-compressed input data
     * @return the decompressed byte array
     * @throws IOException if an I/O error occurs
     */
    public static byte[] inflate(byte[] bytes) throws IOException {
        return inflate(bytes, calculateBufferSize());
    }

    public static File deflate(File inputFile, File outputFile, int bufferSize) throws IOException {

        // Check if the outputFile exists, if not create it
        if (!outputFile.exists() && !outputFile.createNewFile()) throw new IOException("Failed to create output file: " + outputFile.getAbsolutePath());
        if (!outputFile.isFile()) throw new IllegalArgumentException("Provided path is not a file: " + outputFile.getAbsolutePath());

        // Check if the inputFile exists
        if (!inputFile.isFile()) throw new IllegalArgumentException("Provided path is not a file: " + inputFile.getAbsolutePath());
        if (!inputFile.exists()) throw new FileNotFoundException("File not found: " + inputFile.getAbsolutePath());

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

        // Check if the outputFile exists, if not create it
        if (!outputFile.exists() && !outputFile.createNewFile()) throw new IOException("Failed to create output file: " + outputFile.getAbsolutePath());
        if (!outputFile.isFile()) throw new IllegalArgumentException("Provided path is not a file: " + outputFile.getAbsolutePath());

        // Check if the inputFile exists
        if (!inputFile.isFile()) throw new IllegalArgumentException("Provided path is not a file: " + inputFile.getAbsolutePath());
        if (!inputFile.exists()) throw new FileNotFoundException("File not found: " + inputFile.getAbsolutePath());

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

        // Check if the outputFile exists, if not create it
        if (!outputFile.exists() && !outputFile.createNewFile()) throw new IOException("Failed to create output file: " + outputFile.getAbsolutePath());
        if (!outputFile.isFile()) throw new IllegalArgumentException("Provided path is not a file: " + outputFile.getAbsolutePath());

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

        // Check if the outputFile exists, if not create it
        if (!outputFile.exists() && !outputFile.createNewFile()) throw new IOException("Failed to create output file: " + outputFile.getAbsolutePath());
        if (!outputFile.isFile()) throw new IllegalArgumentException("Provided path is not a file: " + outputFile.getAbsolutePath());

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

        // Check if the inputFile exists
        if (!inputFile.isFile()) throw new IllegalArgumentException("Provided path is not a file: " + inputFile.getAbsolutePath());
        if (!inputFile.exists()) throw new FileNotFoundException("File not found: " + inputFile.getAbsolutePath());

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

        // Check if the inputFile exists
        if (!inputFile.isFile()) throw new IllegalArgumentException("Provided path is not a file: " + inputFile.getAbsolutePath());
        if (!inputFile.exists()) throw new FileNotFoundException("File not found: " + inputFile.getAbsolutePath());

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

    public static File deflateObject(Object object, File outputFile) throws IOException {

        // Check if the outputFile exists, if not create it
        if (!outputFile.exists() && !outputFile.createNewFile()) throw new IOException("Failed to create output file: " + outputFile.getAbsolutePath());
        if (!outputFile.isFile()) throw new IllegalArgumentException("Provided path is not a file: " + outputFile.getAbsolutePath());

        // Create Streams
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);

        // Write the object to the GZIP output stream
        objectOutputStream.writeObject(object);

        // Finish the GZIP output stream
        objectOutputStream.flush();
        gzipOutputStream.finish();
        fileOutputStream.flush();

        // Close Streams
        objectOutputStream.close();
        gzipOutputStream.close();
        fileOutputStream.close();

        // Return the compressed byte array
        return outputFile;
    }

    public static Object inflateObject(File inputFile) throws IOException, ClassNotFoundException {

        // Check if the inputFile exists
        if (!inputFile.isFile()) throw new IllegalArgumentException("Provided path is not a file: " + inputFile.getAbsolutePath());
        if (!inputFile.exists()) throw new FileNotFoundException("File not found: " + inputFile.getAbsolutePath());

        // Create Streams
        FileInputStream fileInputStream = new FileInputStream(inputFile);
        GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(gzipInputStream);

        // Read the object from the GZIP input stream
        Object object = objectInputStream.readObject();

        // Close all streams
        objectInputStream.close();
        gzipInputStream.close();
        fileInputStream.close();

        // Return the decompressed object
        return object;
    }

    public static byte[] deflateObject(Object object) throws IOException {

        // Create Streams
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);

        // Write the object to the GZIP output stream
        objectOutputStream.writeObject(object);

        // Finish the GZIP output stream
        objectOutputStream.flush();
        gzipOutputStream.finish();

        // Close Streams
        objectOutputStream.close();
        gzipOutputStream.close();
        byteArrayOutputStream.close();

        // Return the compressed byte array
        return byteArrayOutputStream.toByteArray();
    }

    public static Object inflateObject(byte[] bytes) throws IOException, ClassNotFoundException {

        // Create Streams
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(gzipInputStream);

        // Read the object from the GZIP input stream
        Object object = objectInputStream.readObject();

        // Close all streams
        objectInputStream.close();
        gzipInputStream.close();
        byteArrayInputStream.close();

        // Return the decompressed object
        return object;
    }

    /**
     * Calculates a default buffer size using the file store block size and available processors.
     *
     * @return the calculated buffer size
     * @throws IOException if an I/O error occurs
     */
    private static int calculateBufferSize() throws IOException {
        return Math.toIntExact(Files.getFileStore(Paths.get("")).getBlockSize() * Runtime.getRuntime().availableProcessors());
    }

    /**
     * Calculates the buffer size for the given file using file store block size and available processors.
     *
     * @param file the file whose store block size is considered
     * @return the calculated buffer size
     * @throws IOException if an I/O error occurs
     */
    private static int calculateBufferSize(File file) throws IOException {
        return Math.toIntExact(Files.getFileStore(file.toPath()).getBlockSize() * Runtime.getRuntime().availableProcessors());
    }

    /**
     * Calculates the buffer size based on both input and output files.
     *
     * @param input the input file
     * @param output the output file
     * @return the calculated buffer size
     * @throws IOException if an I/O error occurs
     */
    private static int calculateBufferSize(File input, File output) throws IOException {
        var threads = Runtime.getRuntime().availableProcessors();
        var inputSize = Files.getFileStore(input.toPath()).getBlockSize();
        var outputSize = Files.getFileStore(output.toPath()).getBlockSize();
        return Math.toIntExact(Math.max(inputSize, outputSize) * threads);
    }
}