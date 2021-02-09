package by.bytechs.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @author Romanovich Andrei 09.02.2021 - 15:06
 */
public class FilePathHelper {

    public static String findPathWithResource(String filePath) {
        try {
            if (filePath != null && filePath.startsWith("classpath:")) {
                ClassPathResource resource = new ClassPathResource(filePath.replace("classpath:", ""));
                return resource.getFile().getAbsolutePath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
