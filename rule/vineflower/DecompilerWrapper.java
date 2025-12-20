import org.jetbrains.java.decompiler.api.Decompiler;
import org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.util.*;
import java.util.jar.*;

public class DecompilerWrapper {
    private static void setJarEntryTime(JarEntry entry) {
        entry.setTime(0L);
        entry.setCreationTime(FileTime.fromMillis(0L));
        entry.setLastModifiedTime(FileTime.fromMillis(0L));
    }

    public static void main(String[] args) throws Exception {
        ConsoleDecompiler.main(args);

        var outputPathStr = args[args.length - 1];
        var path = Paths.get(outputPathStr);
        var tempPath = Paths.get(outputPathStr + ".tmp");

        try (var jarFile = new JarFile(path.toFile());
             var jos = new JarOutputStream(new FileOutputStream(tempPath.toFile()))) {

            List<JarEntry> entries = Collections.list(jarFile.entries());
            entries.sort(Comparator.comparing(JarEntry::getName));

            for (var entry : entries) {
                var newEntry = new JarEntry(entry.getName());
                setJarEntryTime(newEntry);

                jos.putNextEntry(newEntry);
                try (var is = jarFile.getInputStream(entry)) {
                    is.transferTo(jos);
                }
                jos.closeEntry();
            }
        }

        Files.move(tempPath, path, StandardCopyOption.REPLACE_EXISTING);
    }
}
