package de.MCmoderSD.tools.interfaces;

import java.io.File;

@SuppressWarnings("unused")
public interface ObjectCompressor {

    // Object ---> File
    File deflateObject(Object object, File outputFile);

    // File ---> Object
    Object inflateObject(File inputFile);

    // Object ---> byte[]
    byte[] deflateObjectToBytes(Object object);

    // byte[] ---> Object
    Object inflateObjectFromBytes(byte[] bytes);
}