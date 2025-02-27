package org.shavneva.familybudget.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.util.Date;

@Data
@Getter
@Setter
public class TransactionDTO {

    private int idtransaction;
    @NotBlank
    private String currency;
    @NotBlank
    public String amount;
    @NotBlank
    public Date date;

    UserDTO user;
    CategoryDTO category;
}
