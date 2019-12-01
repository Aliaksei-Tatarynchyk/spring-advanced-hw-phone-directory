package com.epam.phone.directory.test.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public class TestUtils {

    /**
     * @param absolutePathToResourceFile - absolute path to the resource file, e.g. /testPhoneDirectory.json
     * @param mediaType - media type of the mocked file, e.g. MediaType.APPLICATION_JSON
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static MockMultipartFile createMockMultipartFile(String absolutePathToResourceFile, MediaType mediaType) throws URISyntaxException, IOException {
        byte[] fileBytes = Files.readAllBytes(new File(TestUtils.class.getResource(absolutePathToResourceFile).toURI()).toPath());
        return new MockMultipartFile("file", absolutePathToResourceFile, mediaType.toString(), fileBytes);
    }

}
