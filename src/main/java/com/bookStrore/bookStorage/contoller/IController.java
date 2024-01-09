package com.bookStrore.bookStorage.contoller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

public interface IController<T> 
{
    public ResponseEntity<T> GetEntityById(UUID id);
    public ResponseEntity<ArrayList<T>> GetAllEntities();
    public ResponseEntity<String> CreateEntity(T model);
    public ResponseEntity<String> UpdateEntity(T model);
    public ResponseEntity<String> DeleteEntityById(UUID id);
}
