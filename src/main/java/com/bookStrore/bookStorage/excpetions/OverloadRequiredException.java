package com.bookStrore.bookStorage.excpetions;

public class OverloadRequiredException extends Exception
{
    public OverloadRequiredException(String objectName)
    {
        super("You must overload the "+ objectName);
    }
}
