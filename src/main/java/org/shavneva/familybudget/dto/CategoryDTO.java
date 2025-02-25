package org.shavneva.familybudget.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
@Getter
@Setter
public class CategoryDTO {

    private int idcategory;
    @NotBlank
    private String categoryname;
}
