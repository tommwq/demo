package com.tq.gateway.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Generated source file name and content.
 */
public class SourceFile {
  private String sourceCode = "";
  private String fileName = "";

  public SourceFile() {}

  public SourceFile(String aFileName, String aSourceCode) {
    fileName = aFileName;
    sourceCode = aSourceCode;
  }

  public String getSourceCode() {
    return sourceCode;
  }

  public String getFileName() {
    return fileName;
  }

  @Override
  public String toString() {
    return String.format("<SourceCode: fileName=%s,sourceCode=...>", fileName);
  }

  public void write() throws IOException {
    Path path = Paths.get(fileName);
    File parent = path.getParent().toFile();
    if (!parent.exists() && !parent.mkdirs()) {
      throw new RuntimeException("cannot create output directory");
    }
    
    Files.write(path, sourceCode.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
  }
}
