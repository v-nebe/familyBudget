package org.shavneva.familybudget.dto;

import lombok.*;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private int idcategory;
    @NotBlank
    private String categoryname;
}
