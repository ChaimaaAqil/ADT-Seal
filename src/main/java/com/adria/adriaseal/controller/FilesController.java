package com.adria.adriaseal.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@Validated
public class FilesController {
    // GetPDF

    @Value("${app.save.path}")
    private String uploadPath;
    // TODO change the size limit and regex pattern
    @GetMapping(value = "${app.files.pdf.endpoint}/{fileName:.+}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getPDF(
            @PathVariable
            @NotBlank
            @Size(min = 17, max = 17)
            @Pattern(regexp = "^[\\w,-]+\\.pdf$", message = "This not a valid filename!")
                    String fileName) {

        Path filePath = Paths.get(uploadPath, fileName);
        try {
            byte[] contents = Files.readAllBytes(filePath);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=" + fileName);
            headers.add("mimeType", MediaType.APPLICATION_PDF_VALUE);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return ResponseEntity.ok().headers(headers).body(contents);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileSystemNotFoundException(e.getLocalizedMessage());
        }
    }

}
