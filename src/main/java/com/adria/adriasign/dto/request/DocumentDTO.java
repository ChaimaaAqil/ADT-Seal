package com.adria.adriasign.dto.request;

import com.adria.adriasign.configuration.validators.ContainsPDFsOnly;
import com.adria.adriasign.configuration.validators.IsAnImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Valid
@ToString
public class DocumentDTO {
    @NotEmpty
    @Size(min = 1, message = "Should at least contain one file!")
    @ContainsPDFsOnly
    private MultipartFile[] documents;

    @Size(max = 1, message = "Should at max contain one image!")
    @IsAnImage
    private MultipartFile[] signatureImage;
}
