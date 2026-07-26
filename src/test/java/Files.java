import de.MCmoderSD.tools.GZIP;

import java.io.File;
import java.io.IOException;

import static java.lang.IO.println;

// Input files for testing
File input25 = new File("storage/input/25-Mio.txt");
File input50 = new File("storage/input/50-Mio.txt");
File input100 = new File("storage/input/100-Mio.txt");

// Example usage of GZIP compression and decompression for files
@SuppressWarnings("ResultOfMethodCallIgnored")
void main() throws IOException {

    // Output files for compressed data
    var output25 = new File("storage/output/25-Mio.txt.gz");
    var output50 = new File("storage/output/50-Mio.txt.gz");
    var output100 = new File("storage/output/100-Mio.txt.gz");

    // Create output files if they do not exist
    if (!output25.exists()) output25.createNewFile();
    if (!output50.exists()) output50.createNewFile();
    if (!output100.exists()) output100.createNewFile();

    // Compress files
    GZIP.deflate(input25, output25);
    GZIP.deflate(input50, output50);
    GZIP.deflate(input100, output100);

    // Decompress files
    var decompressed25 = GZIP.inflate(output25);
    var decompressed50 = GZIP.inflate(output50);
    var decompressed100 = GZIP.inflate(output100);

    // Print sizes for verification
    println("Decompressed 25-Mio size: " + decompressed25.length + " bytes");
    println("Decompressed 50-Mio size: " + decompressed50.length + " bytes");
    println("Decompressed 100-Mio size: " + decompressed100.length + " bytes");

    // Print Compression Ratios
    println("Compression Ratio 25-Mio: " + (input25.length() / (double) output25.length()) + ":1");
    println("Compression Ratio 50-Mio: " + (input50.length() / (double) output50.length()) + ":1");
    println("Compression Ratio 100-Mio: " + (input100.length() / (double) output100.length()) + ":1");
}