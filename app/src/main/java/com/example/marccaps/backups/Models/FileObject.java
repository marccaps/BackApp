package com.example.marccaps.backups.Models;

import java.io.File;

/**
 * Created by MarcCaps on 21/5/17.
 */

public class FileObject {

    private File file;

    public FileObject(File file) {
        this.file = file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
