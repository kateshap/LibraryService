package com.example.libraryservice.libobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibJSON {
    @JsonProperty("titleJSON")
    private String titleJSON;
    @JsonProperty("authorJSON")
    private String authorJSON;
}
