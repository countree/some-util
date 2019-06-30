package com.yyh.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUnzipUtil {
    public static void main(String[] args) throws IOException {
        String sourceFilePath = "d:\\zipTest";
        String outFilePath = "d:\\zipTest.tar";
        zip(sourceFilePath, outFilePath);
    }

    public static File zip(String sourceFileName, String outputFileName) throws IOException {
        File outFile = new File(outputFileName);
        FileOutputStream fos = new FileOutputStream(outFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File sourceFile = new File(sourceFileName);
        zipFile(sourceFile, sourceFile.getName(), zipOut);
        zipOut.close();
        fos.close();
        return outFile;
    }

    public static File unzip() {

        return null;
    }

    private static void zipFile(File sourceFile, String sourceChildFileName, ZipOutputStream zipOut) throws IOException {
        if (sourceFile.isHidden()) {
            return;
        }
        if (sourceFile.isDirectory()) {
            if (sourceChildFileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(sourceChildFileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(sourceChildFileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = sourceFile.listFiles();
            for (File childFile : children) {
                zipFile(childFile, sourceChildFileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(sourceFile);
        ZipEntry zipEntry = new ZipEntry(sourceChildFileName);
        zipOut.putNextEntry(zipEntry);
        IOUtils.copy(fis, zipOut);
        fis.close();
    }

    public static File newFile(File destinationParentFile, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationParentFile, zipEntry.getName());

        String destDirPath = destinationParentFile.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("文件名字不对照: " + zipEntry.getName());
        }

        return destFile;
    }
}
