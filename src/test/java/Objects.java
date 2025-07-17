import de.MCmoderSD.tools.GZIP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Objects {

    public record Vector(float... coordinates) implements Serializable {

        public Vector {
            if (coordinates.length == 0) throw new IllegalArgumentException("Coordinates cannot be empty");
        }

        public byte[] serialize() throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        }
    }

    // Example usage of GZIP compression and decompression for objects
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // Create an n dimensional Vector with random coordinates
        int n = 8192;
        float[] coordinates = new float[n];
        for (var i = 0; i < n; i++) coordinates[i] = Math.round(Math.random());
        Vector vector = new Vector(coordinates);

        // Serialize the vector
        byte[] serializedVector = vector.serialize();
        System.out.println("Serialized Vector size: " + serializedVector.length + " bytes");

        // Compress the serialized vector
        byte[] compressedVector = GZIP.deflateObject(vector);
        System.out.println("Compressed Vector size: " + compressedVector.length + " bytes");

        // Decompress the vector
        Vector decompressedVector = (Vector) GZIP.inflateObject(compressedVector);
        System.out.println("Decompressed Vector size: " + decompressedVector.serialize().length + " bytes");

        // Print Compression Ratio
        System.out.println("Compression Ratio: " + (serializedVector.length / (double) compressedVector.length) + ":1");
    }
}