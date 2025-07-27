package de.MCmoderSD.tools;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class GZIP {

    // Error messages for exceptions
    private static final String FILE_NOT_FOUND = "File not found: ";
    private static final String NOT_A_FILE = "Provided path is not a file: ";
    private static final String FILE_CREATION_FAILED = "Failed to create output file: ";
    private static final String INPUT_OUTPUT_SAME = "Input and output files are the same";
    private static final String DECOMPRESSION_ERROR = "Error during GZIP decompression: ";
    private static final String COMPRESSION_ERROR = "Error during GZIP compression: ";
    private static final String BUFFER_SIZE_ERROR = "Buffer size must be greater than 0 and a multiple of the file system block size: ";

    private static void checkInputFile(File inputFile) throws FileNotFoundException {
        if (!inputFile.exists()) throw new FileNotFoundException(FILE_NOT_FOUND + inputFile.getAbsolutePath());
        if (!inputFile.isFile()) throw new IllegalArgumentException(NOT_A_FILE + inputFile.getAbsolutePath());
    }

    private static void checkOutputFile(File outputFile) throws IOException {
        if (!outputFile.exists() && !outputFile.createNewFile()) throw new IOException(FILE_CREATION_FAILED + outputFile.getAbsolutePath());
        if (!outputFile.isFile()) throw new IllegalArgumentException(NOT_A_FILE + outputFile.getAbsolutePath());
    }

    private static void checkFiles(File inputFile, File outputFile) throws IOException {
        checkInputFile(inputFile);
        checkOutputFile(outputFile);
        if (inputFile.equals(outputFile)) throw  new IllegalArgumentException(INPUT_OUTPUT_SAME);
    }

    private static boolean validateBufferSize(int bufferSize) throws IOException {
        if (bufferSize <= 0) return false;
        else if (bufferSize % Files.getFileStore(Paths.get(".")).getBlockSize() != 0) throw new IllegalArgumentException(BUFFER_SIZE_ERROR + bufferSize);
        return true;
    }

    public static File deflate(File inputFile, File outputFile) throws IOException {
        return deflate(inputFile, outputFile, 0);
    }

    public static File inflate(File inputFile, File outputFile) throws IOException {
        return inflate(inputFile, outputFile, 0);
    }

    public static File deflate(byte[] bytes, File outputFile) throws IOException {
        return deflate(bytes, outputFile, 0);
    }

    public static File inflate(byte[] bytes, File outputFile) throws IOException {
        return inflate(bytes, outputFile, 0);
    }

    public static byte[] deflate(File inputFile) throws IOException {
        return deflate(inputFile, 0);
    }

    public static byte[] inflate(File inputFile) throws IOException {
        return inflate(inputFile, 0);
    }

    public static byte[] deflate(byte[] bytes) throws IOException {
        return deflate(bytes, 0);
    }

    public static byte[] inflate(byte[] bytes) throws IOException {
        return inflate(bytes, 0);
    }

    public static File deflateObject(Object object, File outputFile) throws IOException {
        return deflateObject(object, outputFile, 0);
    }

    public static Object inflateObject(File inputFile) throws IOException, ClassNotFoundException {
        return inflateObject(inputFile, 0);
    }

    public static byte[] deflateObject(Object object) throws IOException {
        return deflateObject(object, 0);
    }

    public static Object inflateObject(byte[] bytes) throws IOException, ClassNotFoundException {
        return inflateObject(bytes, 0);
    }

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