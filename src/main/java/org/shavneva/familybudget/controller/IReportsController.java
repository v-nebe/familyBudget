package org.shavneva.familybudget.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public interface IReportsController {
    @GetMapping(value = "/download", produces = {
            "application/pdf",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    })
    ResponseEntity<byte[]> downloadWordFile(@RequestParam String nickname,
                                            @RequestParam String date,
                                            @RequestParam String currency,
                                            @RequestHeader("Accept") String acceptHeader);
}
