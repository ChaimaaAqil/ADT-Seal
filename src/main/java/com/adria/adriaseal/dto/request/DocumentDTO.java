package com.adria.adriaseal.dto.request;

import com.adria.adriaseal.configuration.validators.ContainsPDFsOnly;
import com.adria.adriaseal.configuration.validators.IsAnImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Valid
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDTO {
    // Validation sur le pdf au minimum 1 pdf
    @NotEmpty
    @Size(min = 1, message = "Should at least contain one file!")
    @ContainsPDFsOnly
    private MultipartFile[] documents;
    // Validation sur l'image de signature au maximum 1 image

    @Size(max = 1, message = "Should at max contain one image!")
    @IsAnImage
    private MultipartFile[] signatureImage;

}
