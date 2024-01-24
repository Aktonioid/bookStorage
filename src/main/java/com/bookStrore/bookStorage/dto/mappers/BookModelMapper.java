package com.bookStrore.bookStorage.dto.mappers;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.bookStrore.bookStorage.dto.models.BookModelDto;
import com.bookStrore.bookStorage.dto.models.GenreModelDto;
import com.bookStrore.bookStorage.models.BookModel;

public class BookModelMapper
{
    public static BookModelDto AsDto(BookModel model)
    {
        return new BookModelDto(model.getId(), 
                                model.getBookName(),
                                model.getAuthorName(), 
                                model.getPublisher(), 
                                model.getPrice(), 
                                model.getIsbn(), 
                                new ArrayList<GenreModelDto>(
                                    model.getGenres().stream()
                                        .map(GenreModelMapper::AsDto).collect(Collectors.toList())),
                                model.getDescription(), 
                                model.getPictureUrl(), 
                                model.getLeftovers());
    }   

    public static BookModel AsEntity(BookModelDto dto)
    {
        return new BookModel(dto.getId(), 
                            dto.getBookName(), 
                            dto.getAuthorName(), 
                            dto.getPublisher(),
                            dto.getPrice(), 
                            dto.getIsbn(), 
                            dto.getGenres().stream()
                                .map(GenreModelMapper::AsEntity).collect(Collectors.toSet()),
                            dto.getDescription(), 
                            dto.getPictureUrl(), 
                            dto.getLeftovers());
    }
}
