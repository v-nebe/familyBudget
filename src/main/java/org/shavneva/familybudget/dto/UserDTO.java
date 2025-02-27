package org.shavneva.familybudget.dto;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
