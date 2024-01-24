package com.bookStrore.bookStorage.services;

import java.util.ArrayList;
import java.util.UUID;

public interface IServiceBase<T> 
{
    public ArrayList<T> GetAllEntities(); //получение всех сущностей типа T
    public T GetEntitieById(UUID id); // получение сущности по id
    public boolean CreateEntity(T model);// Создание сущности
    public boolean UpdateEntity(T upadtableModel); // обновление сущности
    public boolean DeleteEntityById(UUID id); // удаление сущности по id
}
