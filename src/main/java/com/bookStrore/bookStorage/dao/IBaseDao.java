package com.bookStrore.bookStorage.dao;

import java.util.ArrayList;
import java.util.UUID;

import com.bookStrore.bookStorage.excpetions.OverloadRequiredException;

public interface IBaseDao<T> 
{
    public ArrayList<T> GetAllEntities() throws OverloadRequiredException;
    public T GetEntityById(UUID id);
    public boolean CreateEntity(T model);
    public boolean UpdateEntity(T model);
    public boolean DeleteEntityById(UUID id);
}
