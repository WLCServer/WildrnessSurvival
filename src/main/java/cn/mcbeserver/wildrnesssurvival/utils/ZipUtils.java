package cn.mcbeserver.wildrnesssurvival.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author DongShaoNB
 */
public class ZipUtils {
    public static void zip(String folderPath, String zipFilePath, String[] excludedFiles) throws IOException {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFilePath));
            zipFolder(folderPath, folderPath, excludedFiles, zos);
        } finally {
            if (zos != null) {
                zos.close();
            }
        }
    }

    private static void zipFolder(String rootPath, String folderPath, String[] excludedFiles, ZipOutputStream zos) throws IOException {
        File folder = new File(folderPath);
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                zipFolder(rootPath, file.getAbsolutePath(), excludedFiles, zos);
            } else {
                String filePath = file.getAbsolutePath();
                if (!isExcluded(filePath, excludedFiles)) {
                    String relativePath = filePath.substring(rootPath.length() + 1);
                    ZipEntry zipEntry = new ZipEntry(relativePath);
                    zos.putNextEntry(zipEntry);
                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(file);
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fis.read(buffer)) > 0) {
                            zos.write(buffer, 0, length);
                        }
                    } finally {
                        if (fis != null) {
                            fis.close();
                        }
                    }
                    zos.closeEntry();
                }
            }
        }
    }

    private static boolean isExcluded(String filePath, String[] excludedFiles) {
        for (String excludedFile : excludedFiles) {
            if (filePath.endsWith(excludedFile)) {
                return true;
            }
        }
        return false;
    }
}
