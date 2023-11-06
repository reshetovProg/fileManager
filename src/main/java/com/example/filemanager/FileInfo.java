package com.example.filemanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FileInfo {
    private enum FileType{
        DIRECTORY("D"),FILE("F");

        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        FileType(String str) {
            this.name=str;
        }

    }
    private String name;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    private long size;
    private FileType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type.getName();
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public LocalDateTime getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(LocalDateTime dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

    private LocalDateTime dateOfChange;

    public FileInfo(Path path){
        try {
            name = path.getFileName().toString();
            size = Files.size(path);
            type= (Files.isDirectory(path)?FileType.DIRECTORY:FileType.FILE);
            dateOfChange = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneOffset.ofHours(0));

        } catch (IOException e) {
            throw new RuntimeException("file not correct");
        }

    }



}
