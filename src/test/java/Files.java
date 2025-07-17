import de.MCmoderSD.tools.GZIP;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("ALL")
public class Files {

    // Input files for testing
    private static final File input25 = new File("storage/input/25-Mio.txt");
    private static final File input50 = new File("storage/input/50-Mio.txt");
    private static final File input100 = new File("storage/input/100-Mio.txt");

    // Example usage of GZIP compression and decompression for files
    public static void main(String[] args) throws IOException {

        // Output files for compressed data
        File output25 = new File("storage/output/25-Mio.txt.gz");
        File output50 = new File("storage/output/50-Mio.txt.gz");
        File output100 = new File("storage/output/100-Mio.txt.gz");

        // Create output files if they do not exist
        if (!output25.exists()) output25.createNewFile();
        if (!output50.exists()) output50.createNewFile();
        if (!output100.exists()) output100.createNewFile();

        // Compress files
        GZIP.deflate(input25, output25);
        GZIP.deflate(input50, output50);
        GZIP.deflate(input100, output100);

        // Decompress files
        byte[] decompressed25 = GZIP.inflate(output25);
        byte[] decompressed50 = GZIP.inflate(output50);
        byte[] decompressed100 = GZIP.inflate(output100);

        // Print sizes for verification
        System.out.println("Decompressed 25-Mio size: " + decompressed25.length + " bytes");
        System.out.println("Decompressed 50-Mio size: " + decompressed50.length + " bytes");
        System.out.println("Decompressed 100-Mio size: " + decompressed100.length + " bytes");

        // Print Compression Ratios
        System.out.println("Compression Ratio 25-Mio: " + (input25.length() / (double) output25.length()) + ":1");
        System.out.println("Compression Ratio 50-Mio: " + (input50.length() / (double) output50.length()) + ":1");
        System.out.println("Compression Ratio 100-Mio: " + (input100.length() / (double) output100.length()) + ":1");
    }
}