import de.MCmoderSD.tools.GZIP;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.IOException;

import static java.lang.IO.println;

record Vector(float... coordinates) implements Serializable {

    Vector {
        if (coordinates.length == 0) throw new IllegalArgumentException("Coordinates cannot be empty");
    }

    byte[] serialize() throws IOException {
        var byteArrayOutputStream = new ByteArrayOutputStream();
        var objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }
}

// Example usage of GZIP compression and decompression for objects
void main() throws IOException, ClassNotFoundException {

    // Create an n dimensional Vector with random coordinates
    var n = 8192;
    var coordinates = new float[n];
    for (var i = 0; i < n; i++) coordinates[i] = Math.round(Math.random());
    var vector = new Vector(coordinates);

    // Serialize the vector
    var serializedVector = vector.serialize();
    println("Serialized Vector size: " + serializedVector.length + " bytes");

    // Compress the serialized vector
    var compressedVector = GZIP.deflateObject(vector);
    println("Compressed Vector size: " + compressedVector.length + " bytes");

    // Decompress the vector
    var decompressedVector = (Vector) GZIP.inflateObject(compressedVector);
    println("Decompressed Vector size: " + decompressedVector.serialize().length + " bytes");

    // Print Compression Ratio
    println("Compression Ratio: " + (serializedVector.length / (double) compressedVector.length) + ":1");
}