package com.bookStrore.bookStorage.services.book;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookStrore.bookStorage.dao.IBaseDao;
import com.bookStrore.bookStorage.dto.mappers.BookModelMapper;
import com.bookStrore.bookStorage.dto.models.BookModelDto;
import com.bookStrore.bookStorage.excpetions.OverloadRequiredException;
import com.bookStrore.bookStorage.excpetions.StorageException;
import com.bookStrore.bookStorage.models.BookModel;
import com.bookStrore.bookStorage.services.IServiceBase;

@Service
@PropertySource(value = "classpath:files.properties")
public class BookService implements IServiceBase<BookModelDto> 
{

    @Autowired
    private IBaseDao<BookModel> bookModelDao;
    @Autowired
    private Environment env;

    
    @Override
    public ArrayList<BookModelDto> GetAllEntities() 
    {
        ArrayList<BookModelDto> dtos = null;
        try 
        {
            dtos = new ArrayList<>(
                bookModelDao.GetAllEntities().stream().map(BookModelMapper::AsDto).collect(Collectors.toList()));
        } 
        catch (OverloadRequiredException e) {
            e.printStackTrace();
        } 

        return dtos;
    }

    @Override
    public BookModelDto GetEntitieById(UUID id) 
    {
        return BookModelMapper.AsDto(bookModelDao.GetEntityById(id));
    }

    @Override
    public boolean CreateEntity(BookModelDto model) 
    {
        return bookModelDao.CreateEntity(BookModelMapper.AsEntity(model));
    }

    @Override
    public boolean UpdateEntity(BookModelDto upadtableModel) 
    {
        return bookModelDao.UpdateEntity(BookModelMapper.AsEntity(upadtableModel));
    }

    @Override
    public boolean DeleteEntityById(UUID id) 
    {
        return bookModelDao.DeleteEntityById(id);
    }
    
    public String SaveBookCover(MultipartFile model)
    {
        if(model.isEmpty())
        {
            throw new StorageException("File cannot be null");
        }

        
        Path path = Path.of(env.getProperty("files.path")+"/covers").toAbsolutePath(); 
        
        Path destinationPath = path.resolve(
            Paths.get(model.getOriginalFilename())).normalize().toAbsolutePath();
        

        if(!Files.exists(path)) // по идее созлание дирректории, если её нет
        {
            path.toFile().mkdir();
        }

        if(!destinationPath.getParent().equals(path.toAbsolutePath()))
        {
            throw new StorageException("Error at saving in direction");
        }   


        try(InputStream inputStream = model.getInputStream())
        {
            Files.copy(inputStream, destinationPath, 
            StandardCopyOption.REPLACE_EXISTING);
        }

        catch(IOException e)
        {
            throw new StorageException("Exception during saving the file");
        }

        return destinationPath.toString();
    }
}
