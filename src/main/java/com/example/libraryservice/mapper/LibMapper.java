package com.example.libraryservice.mapper;

import com.example.libraryservice.libobjects.LibBD;
import com.example.libraryservice.libobjects.LibJSON;
import com.example.libraryservice.libobjects.LibXML;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LibMapper {

    @Mapping(target = "titleJSON", source = "titleBD")
    @Mapping(target = "authorJSON", source = "authorBD")
    LibJSON mapWithoutId(LibBD book);

    @Mapping(target = "titleBD", source = "title")
    @Mapping(target = "authorBD", source = "author")
    LibBD mapGenerated(LibXML book_xml);

}