package com.peer.vault.file_service.util;

import java.text.DecimalFormat;

public class FileSizeUtil {

    public static String convertToReadableSize(long sizeInBytes) {
        if (sizeInBytes <= 0) return "0 B";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(sizeInBytes) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(sizeInBytes / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
