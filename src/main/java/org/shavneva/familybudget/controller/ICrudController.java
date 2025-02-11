package org.shavneva.familybudget.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ICrudController<DTO> {
    @PutMapping("/create")
    DTO create(@Valid @RequestBody DTO dto);
    @GetMapping("/getAll")
    List<DTO> read();
    @GetMapping("/get/{id}")
    DTO getById(@Valid @PathVariable int id);
    @PutMapping("/update")
    DTO update(@Valid @RequestBody DTO newDTO);
    @DeleteMapping("/delete/{id}")
    void delete(@Valid @PathVariable int id);
}
