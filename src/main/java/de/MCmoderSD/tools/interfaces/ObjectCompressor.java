package de.MCmoderSD.tools.interfaces;

import java.io.File;

@SuppressWarnings("ALL")
public interface ObjectCompressor {

    // Object ---> File
    File deflateObject(Object object, File outputFile);

    // File ---> Object
    Object inflateObject(File inputFile);

    // Object ---> byte[]
    byte[] deflateObject(Object object);

    // byte[] ---> Object
    Object inflateObject(byte[] bytes);
}