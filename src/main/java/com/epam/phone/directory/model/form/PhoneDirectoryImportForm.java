package com.epam.phone.directory.model.form;

import org.springframework.web.multipart.MultipartFile;

public class PhoneDirectoryImportForm {
    MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
