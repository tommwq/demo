package com.tommwq;

import java.io.*;

public final class BuildInfo {
    // TODO 支持多种版本管理工具
    String origHead = "";
    String head = "";
    String branch = "";
    boolean isClean;
    String projectPackage = "";
}
