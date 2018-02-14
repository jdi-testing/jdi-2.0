package com.epam.jdi.tools;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {
    public static List<String> getFiles(String pathToDir) {
        try {
            return Files.walk(Paths.get(pathToDir))
                    .filter(Files::isRegularFile).map(Path::toString)
                    .collect(Collectors.toList());
        }
        catch (Exception ex) {
            return new ArrayList<>();
        }
    }
}
