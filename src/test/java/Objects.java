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