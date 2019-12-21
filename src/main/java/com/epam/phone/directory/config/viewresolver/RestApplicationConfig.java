package com.epam.phone.directory.config.viewresolver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.epam.phone.directory.model.db.User;
import com.epam.phone.directory.service.pdf.PdfGenerator;
import com.google.common.io.ByteStreams;

@Configuration
public class RestApplicationConfig implements WebMvcConfigurer {

    PdfGenerator pdfGenerator;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(pdfHttpMessageConverter());
    }

    @Bean
    public HttpMessageConverter pdfHttpMessageConverter() {
        return new HttpMessageConverter() {
            @Override
            public boolean canRead(Class clazz, MediaType mediaType) {
                return false;
            }

            @Override
            public boolean canWrite(Class clazz, MediaType mediaType) {
                return mediaType == null || MediaType.APPLICATION_PDF.equals(mediaType);
            }

            @Override
            public List<MediaType> getSupportedMediaTypes() {
                return Collections.singletonList(MediaType.APPLICATION_PDF);
            }

            @Override
            public Object read(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
                return null;
            }

            @Override
            public void write(Object o, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                List<User> users;
                if (o instanceof Collection) {
                    users = ((Collection<com.epam.phone.directory.model.json.User>) o).stream().map(com.epam.phone.directory.model.json.User::toJPA).collect(Collectors.toList());
                } else if (o instanceof com.epam.phone.directory.model.json.User) {
                    users = Collections.singletonList(((com.epam.phone.directory.model.json.User) o).toJPA());
                } else {
                    users = new ArrayList<>();
                }
                ByteArrayInputStream pdfInputStream = getPdfGenerator().generateUsersPDF(users);
                ByteStreams.copy(pdfInputStream, outputMessage.getBody());
            }

        };
    }

    public PdfGenerator getPdfGenerator() {
        return pdfGenerator;
    }

    @Autowired
    public void setPdfGenerator(PdfGenerator pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
    }
}
