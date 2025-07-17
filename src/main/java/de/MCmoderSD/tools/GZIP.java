package de.MCmoderSD.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

    /**
     * Compresses a file into GZIP format with a specific buffer size.
     *
     * @param inputFile   the file to compress
     * @param outputFile  the destination file for the compressed output
     * @param bufferSize  the size of the buffer to use while reading/writing
     * @return the compressed output file
     * @throws IOException if an I/O error occurs during compression
     */
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

    /**
     * Decompresses a GZIP-compressed file with a specific buffer size.
     *
     * @param inputFile   the GZIP-compressed input file
     * @param outputFile  the destination file for the decompressed output
     * @param bufferSize  the size of the buffer to use while reading/writing
     * @return the decompressed output file
     * @throws IOException if an I/O error occurs during decompression
     */
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

    /**
     * Compresses a byte array into GZIP format with a specific buffer size and writes to a file.
     *
     * @param bytes       the byte array to compress
     * @param outputFile  the destination file for the compressed output
     * @param bufferSize  the size of the buffer to use while reading/writing
     * @return the compressed output file
     * @throws IOException if an I/O error occurs during compression
     */
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

    /**
     * Decompresses a GZIP-compressed byte array with a specific buffer size and writes to a file.
     *
     * @param bytes       the GZIP-compressed byte array
     * @param outputFile  the destination file for the decompressed output
     * @param bufferSize  the size of the buffer to use while reading/writing
     * @return the decompressed output file
     * @throws IOException if an I/O error occurs during decompression
     */
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

    /**
     * Compresses a file into a GZIP-compressed byte array using a specific buffer size.
     *
     * @param inputFile   the file to compress
     * @param bufferSize  the size of the buffer to use while reading/writing
     * @return the compressed byte array
     * @throws IOException if an I/O error occurs during compression
     */
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

    /**
     * Decompresses a GZIP-compressed file into a byte array using a specific buffer size.
     *
     * @param inputFile   the GZIP-compressed input file
     * @param bufferSize  the size of the buffer to use while reading/writing
     * @return the decompressed byte array
     * @throws IOException if an I/O error occurs during decompression
     */
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

    /**
     * Compresses a byte array into a GZIP-compressed byte array using a specific buffer size.
     *
     * @param bytes       the input byte array to compress
     * @param bufferSize  the size of the buffer to use while reading/writing
     * @return the compressed byte array
     * @throws IOException if an I/O error occurs during compression
     */
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

    /**
     * Decompresses a GZIP-compressed byte array into a byte array using a specific buffer size.
     *
     * @param bytes       the GZIP-compressed input byte array
     * @param bufferSize  the size of the buffer to use while reading/writing
     * @return the decompressed byte array
     * @throws IOException if an I/O error occurs during decompression
     */
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

    /**
     * Compresses a serializable object and returns the compressed byte array.
     *
     * @param object the object to compress
     * @return the compressed byte array
     * @throws IOException if an I/O error occurs during compression
     */
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

    /**
     * Decompresses a byte array into an object.
     *
     * @param bytes the GZIP-compressed byte array representing a serialized object
     * @return the decompressed object
     * @throws IOException if an I/O error occurs during decompression
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
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