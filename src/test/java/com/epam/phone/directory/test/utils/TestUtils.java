package com.epam.phone.directory.test.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
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
        URL resource = TestUtils.class.getResource(absolutePathToResourceFile);
        if (resource != null) {
            byte[] fileBytes = Files.readAllBytes(new File(resource.toURI()).toPath());
            return new MockMultipartFile("file", absolutePathToResourceFile, mediaType.toString(), fileBytes);
        } else {
            return new MockMultipartFile("file", "", mediaType.toString(), new byte[]{});
        }
    }

}
