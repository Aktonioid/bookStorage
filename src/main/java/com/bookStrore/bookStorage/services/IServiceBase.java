package com.bookStrore.bookStorage.services;

import java.util.ArrayList;
import java.util.UUID;

public interface IServiceBase<T> 
{
    public ArrayList<T> GetAllEntities();
    public T GetEntitieById(UUID id);
    public boolean CreateEntity(T model);
    public boolean UpdateEntity(T upadtableModel);
    public boolean DeleteEntityById(UUID id);
}
