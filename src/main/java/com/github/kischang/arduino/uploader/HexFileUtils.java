package com.github.kischang.arduino.uploader;

import IntelHexFormatReader.Utils.FileLineIterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author KisChang
 * @date 2022-01-14
 */
public class HexFileUtils {

    private static final Logger logger = LoggerFactory.getLogger(HexFileUtils.class);

    public static Iterable<String> fileToHexContents(String filename) throws IOException {
        logger.info("Starting upload process for file '${}'.", filename);
        try {
            return new FileLineIterable(filename);
        } catch (IOException | RuntimeException ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

}
