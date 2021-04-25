package com.adria.adriasign.dto.request;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@Valid
@ToString
@NotNull

public class ClientDTO {
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z\\u00C0-\\u017F ]{3,255}$")
    private String firstName;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z\\u00C0-\\u017F ]{3,255}$")
    private String lastName;
    @NotEmpty
    //TODO double check this
    @Pattern(regexp = "^0\\d{9}$", message = "This not a valid phone number, must contain digits only and start with a zero!")
    private String phoneNumber;
    @NotEmpty
    @Size(min = 6, max = 255)
    private String cin;
    @NotEmpty
    @Email()
    private String email;
    private String businessId;
    private String organisation;
}
