# ZIP-Tools

## Description
ZIP-Tools is a Java library designed to simplify the process of compressing and decompressing files using GZIP.


## Usage

### Maven
Make sure you have my Sonatype Nexus OSS repository added to your `pom.xml` file:
```xml
<repositories>
    <repository>
        <id>Nexus</id>
        <name>Sonatype Nexus</name>
        <url>https://mcmodersd.de/nexus/repository/maven-releases/</url>
    </repository>
</repositories>
```
Add the dependency to your `pom.xml` file:
```xml
<dependency>
    <groupId>de.MCmoderSD</groupId>
    <artifactId>ZIP-Tools</artifactId>
    <version>1.2.0</version>
</dependency>
```

### Compress and Decompress Files
```java
import de.MCmoderSD.tools.GZIP;

import java.io.File;
import java.io.IOException;

// Input files for testing
File input25 = new File("storage/input/25-Mio.txt");
File input50 = new File("storage/input/50-Mio.txt");
File input100 = new File("storage/input/100-Mio.txt");

// Example usage of GZIP compression and decompression for files
@SuppressWarnings("ResultOfMethodCallIgnored")
void main() throws IOException {
    
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
    IO.println("Decompressed 25-Mio size: " + decompressed25.length + " bytes");
    IO.println("Decompressed 50-Mio size: " + decompressed50.length + " bytes");
    IO.println("Decompressed 100-Mio size: " + decompressed100.length + " bytes");

    // Print Compression Ratios
    IO.println("Compression Ratio 25-Mio: " + (input25.length() / (double) output25.length()) + ":1");
    IO.println("Compression Ratio 50-Mio: " + (input50.length() / (double) output50.length()) + ":1");
    IO.println("Compression Ratio 100-Mio: " + (input100.length() / (double) output100.length()) + ":1");
}
```

### Compress and Decompress Objects
```java
import de.MCmoderSD.tools.GZIP;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.IOException;

record Vector(float... coordinates) implements Serializable {

    Vector {
        if (coordinates.length == 0) throw new IllegalArgumentException("Coordinates cannot be empty");
    }

    byte[] serialize() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }
}

// Example usage of GZIP compression and decompression for objects
void main() throws IOException, ClassNotFoundException {

    // Create an n dimensional Vector with random coordinates
    int n = 8192;
    float[] coordinates = new float[n];
    for (var i = 0; i < n; i++) coordinates[i] = Math.round(Math.random());
    Vector vector = new Vector(coordinates);

    // Serialize the vector
    byte[] serializedVector = vector.serialize();
    IO.println("Serialized Vector size: " + serializedVector.length + " bytes");

    // Compress the serialized vector
    byte[] compressedVector = GZIP.deflateObject(vector);
    IO.println("Compressed Vector size: " + compressedVector.length + " bytes");

    // Decompress the vector
    Vector decompressedVector = (Vector) GZIP.inflateObject(compressedVector);
    IO.println("Decompressed Vector size: " + decompressedVector.serialize().length + " bytes");

    // Print Compression Ratio
    IO.println("Compression Ratio: " + (serializedVector.length / (double) compressedVector.length) + ":1");
}
```