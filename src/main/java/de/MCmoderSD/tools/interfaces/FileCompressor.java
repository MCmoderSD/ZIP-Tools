package de.MCmoderSD.tools.interfaces;

import java.io.File;

@SuppressWarnings("ALL")
public interface FileCompressor {

    // File ---> File
    File deflate(File inputFile, File outputFile);
    File inflate(File inputFile, File outputFile);

    // byte[] ---> File
    File deflate(byte[] bytes, File outputFile);
    File inflate(byte[] bytes, File outputFile);

    // File ---> byte[]
    byte[] deflate(File inputFile);
    byte[] inflate(File inputFile);

    // byte[] ---> byte[]
    byte[] deflate(byte[] bytes);
    byte[] inflate(byte[] bytes);
    
    
    
    // File ---> File with buffer size
    File deflate(File inputFile, File outputFile, int bufferSize);
    File inflate(File inputFile, File outputFile, int bufferSize);

    // byte[] ---> File with buffer size
    File deflate(byte[] bytes, File outputFile, int bufferSize);
    File inflate(byte[] bytes, File outputFile, int bufferSize);

    // File ---> byte[] with buffer size
    byte[] deflate(File inputFile, int bufferSize);
    byte[] inflate(File inputFile, int bufferSize);

    // byte[] ---> byte[] with buffer size
    byte[] deflate(byte[] bytes, int bufferSize);
    byte[] inflate(byte[] bytes, int bufferSize);
}