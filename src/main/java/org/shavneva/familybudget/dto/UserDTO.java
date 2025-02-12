package org.shavneva.familybudget.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
@Getter
@Setter
public class UserDTO {

    private int iduser;
    @NotBlank
    private String nickname;
    @NotBlank
    @Size(min = 4)
    private String password;
    @NotBlank
    private String role;
}
