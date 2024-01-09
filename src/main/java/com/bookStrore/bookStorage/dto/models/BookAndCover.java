package com.bookStrore.bookStorage.dto.models;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BookAndCover 
{
    private BookModelDto book;
    private MultipartFile bookCover;    
}
