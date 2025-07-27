package de.MCmoderSD.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@SuppressWarnings("ALL")
public class GZIP {

    // Error messages for exceptions
    private static final String FILE_NOT_FOUND = "File not found: ";
    private static final String NOT_A_FILE = "Provided path is not a file: ";
    private static final String FILE_CREATION_FAILED = "Failed to create output file: ";
    private static final String INPUT_OUTPUT_SAME = "Input and output files are the same";
    private static final String DECOMPRESSION_ERROR = "Error during GZIP decompression: ";
    private static final String COMPRESSION_ERROR = "Error during GZIP compression: ";
    private static final String BUFFER_SIZE_ERROR = "Buffer size must be greater than 0 and a multiple of the file system block size: ";

    /**
     * Checks if the given input file exists and is a regular file.
     *
     * @param inputFile the file to check
     * @throws FileNotFoundException if the file does not exist
     * @throws IllegalArgumentException if the path is not a regular file
     */
    private static void checkInputFile(File inputFile) throws FileNotFoundException {
        if (!inputFile.exists()) throw new FileNotFoundException(FILE_NOT_FOUND + inputFile.getAbsolutePath());
        if (!inputFile.isFile()) throw new IllegalArgumentException(NOT_A_FILE + inputFile.getAbsolutePath());
    }

    /**
     * Checks if the given output file exists or creates it if necessary.
     *
     * @param outputFile the file to check or create
     * @throws IOException if the file cannot be created
     * @throws IllegalArgumentException if the path is not a regular file
     */
    private static void checkOutputFile(File outputFile) throws IOException {
        if (!outputFile.exists() && !outputFile.createNewFile()) throw new IOException(FILE_CREATION_FAILED + outputFile.getAbsolutePath());
        if (!outputFile.isFile()) throw new IllegalArgumentException(NOT_A_FILE + outputFile.getAbsolutePath());
    }

    /**
     * Validates both input and output files and ensures they are not the same.
     *
     * @param inputFile the input file
     * @param outputFile the output file
     * @throws IOException if validation fails
     */
    private static void checkFiles(File inputFile, File outputFile) throws IOException {
        checkInputFile(inputFile);
        checkOutputFile(outputFile);
        if (inputFile.equals(outputFile)) throw  new IllegalArgumentException(INPUT_OUTPUT_SAME);
    }

    /**
     * Validates the provided buffer size against file system block size.
     *
     * @param bufferSize the buffer size to validate
     * @return true if buffering should be used, false if not
     * @throws IOException if buffer size is invalid
     */
    private static boolean validateBufferSize(int bufferSize) throws IOException {
        if (bufferSize <= 0) return false;
        else if (bufferSize % Files.getFileStore(Paths.get(".")).getBlockSize() != 0) throw new IllegalArgumentException(BUFFER_SIZE_ERROR + bufferSize);
        return true;
    }

    /**
     * Compresses the input file using GZIP with default buffer size.
     *
     * @param inputFile  the file to compress
     * @param outputFile the destination file for compressed data
     * @return the compressed file
     * @throws IOException if an I/O error occurs
     */
    public static File deflate(File inputFile, File outputFile) throws IOException {
        return deflate(inputFile, outputFile, 0);
    }

    /**
     * Decompresses the input file using GZIP with default buffer size.
     *
     * @param inputFile  the GZIP-compressed file to decompress
     * @param outputFile the destination file for decompressed data
     * @return the decompressed file
     * @throws IOException if an I/O error occurs
     */
    public static File inflate(File inputFile, File outputFile) throws IOException {
        return inflate(inputFile, outputFile, 0);
    }

    /**
     * Compresses the given byte array and writes it to the specified output file using default buffer size.
     *
     * @param bytes      the data to compress
     * @param outputFile the destination file for compressed data
     * @return the compressed file
     * @throws IOException if an I/O error occurs
     */
    public static File deflate(byte[] bytes, File outputFile) throws IOException {
        return deflate(bytes, outputFile, 0);
    }

    /**
     * Decompresses the given byte array and writes it to the specified output file using default buffer size.
     *
     * @param bytes      the GZIP-compressed data
     * @param outputFile the destination file for decompressed data
     * @return the decompressed file
     * @throws IOException if an I/O error occurs
     */
    public static File inflate(byte[] bytes, File outputFile) throws IOException {
        return inflate(bytes, outputFile, 0);
    }

    /**
     * Compresses the input file and returns the compressed data as a byte array using default buffer size.
     *
     * @param inputFile the file to compress
     * @return compressed data as a byte array
     * @throws IOException if an I/O error occurs
     */
    public static byte[] deflate(File inputFile) throws IOException {
        return deflate(inputFile, 0);
    }

    /**
     * Decompresses the input file and returns the decompressed data as a byte array using default buffer size.
     *
     * @param inputFile the GZIP-compressed file
     * @return decompressed data as a byte array
     * @throws IOException if an I/O error occurs
     */
    public static byte[] inflate(File inputFile) throws IOException {
        return inflate(inputFile, 0);
    }

    /**
     * Compresses the given byte array and returns the compressed data using default buffer size.
     *
     * @param bytes the data to compress
     * @return compressed data as a byte array
     * @throws IOException if an I/O error occurs
     */
    public static byte[] deflate(byte[] bytes) throws IOException {
        return deflate(bytes, 0);
    }

    /**
     * Decompresses the given byte array and returns the decompressed data using default buffer size.
     *
     * @param bytes the GZIP-compressed data
     * @return decompressed data as a byte array
     * @throws IOException if an I/O error occurs
     */
    public static byte[] inflate(byte[] bytes) throws IOException {
        return inflate(bytes, 0);
    }

    /**
     * Compresses the given object and writes it to the specified output file using default buffer size.
     *
     * @param object     the object to compress
     * @param outputFile the destination file for compressed data
     * @return the compressed file
     * @throws IOException if an I/O error occurs
     */
    public static File deflateObject(Object object, File outputFile) throws IOException {
        return deflateObject(object, outputFile, 0);
    }

    /**
     * Decompresses the specified GZIP file and deserializes the object using default buffer size.
     *
     * @param inputFile the GZIP-compressed file
     * @return the decompressed and deserialized object
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if the object's class cannot be found
     */
    public static Object inflateObject(File inputFile) throws IOException, ClassNotFoundException {
        return inflateObject(inputFile, 0);
    }

    /**
     * Compresses the given object and returns it as a byte array using default buffer size.
     *
     * @param object the object to compress
     * @return compressed object data as a byte array
     * @throws IOException if an I/O error occurs
     */
    public static byte[] deflateObject(Object object) throws IOException {
        return deflateObject(object, 0);
    }

    /**
     * Decompresses the given byte array and deserializes the object using default buffer size.
     *
     * @param bytes the GZIP-compressed object data
     * @return the decompressed and deserialized object
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if the object's class cannot be found
     */
    public static Object inflateObject(byte[] bytes) throws IOException, ClassNotFoundException {
        return inflateObject(bytes, 0);
    }

    /**
     * Compresses the specified input file to the given output file using GZIP with an optional buffer size.
     *
     * @param inputFile  the file to compress
     * @param outputFile the destination file
     * @param bufferSize buffer size in bytes; if 0 or invalid, default buffer size is used
     * @return the compressed file
     * @throws IOException if an I/O error occurs during compression
     */
    public static File deflate(File inputFile, File outputFile, int bufferSize) throws IOException {
        checkFiles(inputFile, outputFile);
        boolean buffered = validateBufferSize(bufferSize);
        try (
                var bis = buffered ? new BufferedInputStream(new FileInputStream(inputFile), bufferSize) : new BufferedInputStream(new FileInputStream(inputFile));
                var gos = buffered ? new GZIPOutputStream(new FileOutputStream(outputFile), bufferSize) : new GZIPOutputStream(new FileOutputStream(outputFile))
        ) {
            bis.transferTo(gos);
            gos.finish();
            return outputFile;
        } catch (IOException e) {
            throw new IOException(COMPRESSION_ERROR + e.getMessage(), e);
        }
    }

    /**
     * Decompresses the specified GZIP input file to the given output file using an optional buffer size.
     *
     * @param inputFile  the GZIP-compressed file
     * @param outputFile the destination file
     * @param bufferSize buffer size in bytes; if 0 or invalid, default buffer size is used
     * @return the decompressed file
     * @throws IOException if an I/O error occurs during decompression
     */
    public static File inflate(File inputFile, File outputFile, int bufferSize) throws IOException {
        checkFiles(inputFile, outputFile);
        boolean buffered = validateBufferSize(bufferSize);
        try (
                var gis = buffered ? new GZIPInputStream(new FileInputStream(inputFile), bufferSize) : new GZIPInputStream(new FileInputStream(inputFile));
                var bos = buffered ? new BufferedOutputStream(new FileOutputStream(outputFile), bufferSize) : new BufferedOutputStream(new FileOutputStream(outputFile))
        ) {
            gis.transferTo(bos);
            return outputFile;
        } catch (IOException e) {
            throw new IOException(DECOMPRESSION_ERROR + e.getMessage(), e);
        }
    }

    /**
     * Compresses the given byte array and writes it to the specified file using an optional buffer size.
     *
     * @param bytes      the data to compress
     * @param outputFile the destination file
     * @param bufferSize buffer size in bytes; if 0 or invalid, default buffer size is used
     * @return the compressed file
     * @throws IOException if an I/O error occurs
     */
    public static File deflate(byte[] bytes, File outputFile, int bufferSize) throws IOException {
        checkOutputFile(outputFile);
        boolean buffered = validateBufferSize(bufferSize);
        try (
                var bis = buffered ? new BufferedInputStream(new ByteArrayInputStream(bytes), bufferSize) : new BufferedInputStream(new ByteArrayInputStream(bytes));
                var gos = buffered ? new GZIPOutputStream(new FileOutputStream(outputFile), bufferSize) : new GZIPOutputStream(new FileOutputStream(outputFile))
        ) {
            bis.transferTo(gos);
            gos.finish();
            return outputFile;
        } catch (IOException e) {
            throw new IOException(COMPRESSION_ERROR + e.getMessage(), e);
        }
    }

    /**
     * Decompresses the given byte array to the specified file using an optional buffer size.
     *
     * @param bytes      the GZIP-compressed data
     * @param outputFile the destination file
     * @param bufferSize buffer size in bytes; if 0 or invalid, default buffer size is used
     * @return the decompressed file
     * @throws IOException if an I/O error occurs
     */
    public static File inflate(byte[] bytes, File outputFile, int bufferSize) throws IOException {
        checkOutputFile(outputFile);
        boolean buffered = validateBufferSize(bufferSize);
        try (
                var gis = buffered ? new GZIPInputStream(new ByteArrayInputStream(bytes), bufferSize) : new GZIPInputStream(new ByteArrayInputStream(bytes));
                var bos = buffered ? new BufferedOutputStream(new FileOutputStream(outputFile), bufferSize) : new BufferedOutputStream(new FileOutputStream(outputFile))
        ) {
            gis.transferTo(bos);
            return outputFile;
        } catch (IOException e) {
            throw new IOException(DECOMPRESSION_ERROR + e.getMessage(), e);
        }
    }

    /**
     * Compresses the given input file and returns compressed data as a byte array using an optional buffer size.
     *
     * @param inputFile  the file to compress
     * @param bufferSize buffer size in bytes; if 0 or invalid, default buffer size is used
     * @return compressed data as a byte array
     * @throws IOException if an I/O error occurs
     */
    public static byte[] deflate(File inputFile, int bufferSize) throws IOException {
        checkInputFile(inputFile);
        boolean buffered = validateBufferSize(bufferSize);
        try (
                var bis = buffered ? new BufferedInputStream(new FileInputStream(inputFile), bufferSize) : new BufferedInputStream(new FileInputStream(inputFile));
                var baos = new ByteArrayOutputStream();
                var gos = buffered ? new GZIPOutputStream(baos, bufferSize) : new GZIPOutputStream(baos)
        ) {
            bis.transferTo(gos);
            gos.finish();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new IOException(COMPRESSION_ERROR + e.getMessage(), e);
        }
    }

    /**
     * Decompresses the given GZIP file and returns decompressed data as a byte array using an optional buffer size.
     *
     * @param inputFile  the GZIP-compressed file
     * @param bufferSize buffer size in bytes; if 0 or invalid, default buffer size is used
     * @return decompressed data as a byte array
     * @throws IOException if an I/O error occurs
     */
    public static byte[] inflate(File inputFile, int bufferSize) throws IOException {
        checkInputFile(inputFile);
        boolean buffered = validateBufferSize(bufferSize);
        try (
                var gis = buffered ? new GZIPInputStream(new FileInputStream(inputFile), bufferSize) : new GZIPInputStream(new FileInputStream(inputFile));
                var baos = new ByteArrayOutputStream()
        ) {
            gis.transferTo(baos);
            return baos.toByteArray();
        }
    }

    /**
     * Compresses the given byte array and returns compressed data as a byte array using an optional buffer size.
     *
     * @param bytes      the data to compress
     * @param bufferSize buffer size in bytes; if 0 or invalid, default buffer size is used
     * @return compressed data as a byte array
     * @throws IOException if an I/O error occurs
     */
    public static byte[] deflate(byte[] bytes, int bufferSize) throws IOException {
        boolean buffered = validateBufferSize(bufferSize);
        try (
                var bis = buffered ? new BufferedInputStream(new ByteArrayInputStream(bytes), bufferSize) : new BufferedInputStream(new ByteArrayInputStream(bytes));
                var baos = new ByteArrayOutputStream();
                var gos = buffered ? new GZIPOutputStream(baos, bufferSize) : new GZIPOutputStream(baos)
        ) {
            bis.transferTo(gos);
            gos.finish();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new IOException(COMPRESSION_ERROR + e.getMessage(), e);
        }
    }

    /**
     * Decompresses the given byte array and returns decompressed data as a byte array using an optional buffer size.
     *
     * @param bytes      the GZIP-compressed data
     * @param bufferSize buffer size in bytes; if 0 or invalid, default buffer size is used
     * @return decompressed data as a byte array
     * @throws IOException if an I/O error occurs
     */
    public static byte[] inflate(byte[] bytes, int bufferSize) throws IOException {
        boolean buffered = validateBufferSize(bufferSize);
        try (
                var gis = buffered ? new GZIPInputStream(new ByteArrayInputStream(bytes), bufferSize) : new GZIPInputStream(new ByteArrayInputStream(bytes));
                var baos = new ByteArrayOutputStream()
        ) {
            gis.transferTo(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new IOException(DECOMPRESSION_ERROR + e.getMessage(), e);
        }
    }

    /**
     * Compresses the given object and writes it to the specified file using an optional buffer size.
     *
     * @param object     the object to serialize and compress
     * @param outputFile the destination file
     * @param bufferSize buffer size in bytes; if 0 or invalid, default buffer size is used
     * @return the compressed file
     * @throws IOException if an I/O error occurs
     */
    public static File deflateObject(Object object, File outputFile, int bufferSize) throws IOException {
        checkOutputFile(outputFile);
        boolean buffered = validateBufferSize(bufferSize);
        try (
                var bos = buffered ? new BufferedOutputStream(new FileOutputStream(outputFile), bufferSize) : new BufferedOutputStream(new FileOutputStream(outputFile));
                var gos = buffered ? new GZIPOutputStream(bos, bufferSize) : new GZIPOutputStream(bos);
                var oos = new ObjectOutputStream(gos)
        ) {
            oos.writeObject(object);
            gos.finish();
            return outputFile;
        } catch (IOException e) {
            throw new IOException(COMPRESSION_ERROR + e.getMessage(), e);
        }
    }

    /**
     * Decompresses the specified file and deserializes the contained object using an optional buffer size.
     *
     * @param inputFile  the GZIP-compressed file
     * @param bufferSize buffer size in bytes; if 0 or invalid, default buffer size is used
     * @return the decompressed and deserialized object
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if the object's class cannot be found
     */
    public static Object inflateObject(File inputFile, int bufferSize) throws IOException, ClassNotFoundException {
        checkInputFile(inputFile);
        boolean buffered = validateBufferSize(bufferSize);
        try (
                var bis = buffered ? new BufferedInputStream(new FileInputStream(inputFile), bufferSize) : new BufferedInputStream(new FileInputStream(inputFile));
                var gis = buffered ? new GZIPInputStream(bis, bufferSize) : new GZIPInputStream(bis);
                var ois = new ObjectInputStream(gis)
        ) {
            return ois.readObject();
        } catch (IOException e) {
            throw new IOException(DECOMPRESSION_ERROR + e.getMessage(), e);
        }
    }

    /**
     * Compresses the given object and returns it as a byte array using an optional buffer size.
     *
     * @param object     the object to serialize and compress
     * @param bufferSize buffer size in bytes; if 0 or invalid, default buffer size is used
     * @return compressed object data as a byte array
     * @throws IOException if an I/O error occurs
     */
    public static byte[] deflateObject(Object object, int bufferSize) throws IOException {
        boolean buffered = validateBufferSize(bufferSize);
        try (
                var baos = new ByteArrayOutputStream();
                var gos = buffered ? new GZIPOutputStream(baos, bufferSize) : new GZIPOutputStream(baos);
                var oos = new ObjectOutputStream(gos)
        ) {
            oos.writeObject(object);
            gos.finish();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new IOException(COMPRESSION_ERROR + e.getMessage(), e);
        }
    }

    /**
     * Decompresses the given byte array and deserializes the contained object using an optional buffer size.
     *
     * @param bytes      the GZIP-compressed object data
     * @param bufferSize buffer size in bytes; if 0 or invalid, default buffer size is used
     * @return the decompressed and deserialized object
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if the object's class cannot be found
     */
    public static Object inflateObject(byte[] bytes, int bufferSize) throws IOException, ClassNotFoundException {
        boolean buffered = validateBufferSize(bufferSize);
        try (
                var bis = buffered ? new BufferedInputStream(new ByteArrayInputStream(bytes), bufferSize) : new BufferedInputStream(new ByteArrayInputStream(bytes));
                var gis = buffered ? new GZIPInputStream(bis, bufferSize) : new GZIPInputStream(bis);
                var ois = new ObjectInputStream(gis)
        ) {
            return ois.readObject();
        } catch (IOException e) {
            throw new IOException(DECOMPRESSION_ERROR + e.getMessage(), e);
        }
    }
}