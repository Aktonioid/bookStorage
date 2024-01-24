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

import com.bookStrore.bookStorage.dao.sqlDao.BookModelDao;
import com.bookStrore.bookStorage.dto.mappers.BookModelMapper;
import com.bookStrore.bookStorage.dto.models.BookModelDto;
import com.bookStrore.bookStorage.dto.models.GenreModelDto;
import com.bookStrore.bookStorage.excpetions.StorageException;
import com.bookStrore.bookStorage.models.BookModel;
import com.bookStrore.bookStorage.models.GenreModel;
import com.bookStrore.bookStorage.services.IServiceBase;

@Service
@PropertySource(value = "classpath:files.properties")
public class BookService implements IServiceBase<BookModelDto> 
{

    @Autowired
    private BookModelDao bookModelDao;
    @Autowired
    private Environment env;

    
    @Override
    public ArrayList<BookModelDto> GetAllEntities() 
    {
        ArrayList<BookModelDto> dtos = null;
        dtos = new ArrayList<>(
            bookModelDao.GetAllEntities().stream().map(BookModelMapper::AsDto).collect(Collectors.toList()));

        return dtos;
    }

    @Override
    public BookModelDto GetEntitieById(UUID id) 
    {
        BookModel book = bookModelDao.GetEntityById(id);

        if(book == null)
        {
            return null;
        }

        return BookModelMapper.AsDto(book);
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
    
    // Сохранение обложки книги и возвращение абсолютного пути до файла
    public String SaveBookCover(MultipartFile model)
    {
        // проверяем на то null ли поступаемый файл
        if(model.isEmpty())
        {
            throw new StorageException("File cannot be null");
        }

        // получаем path из files.properties и преобразуем в абсолютный путь
        Path path = Path.of(env.getProperty("files.path")+"/covers").toAbsolutePath(); 
        
        // создаем путь до самого файла(к пути path добавляем название загруженного файла) и переводим в абсолютный путь
        Path destinationPath = path.resolve(
            Paths.get(model.getOriginalFilename())).normalize().toAbsolutePath();
        

        if(!Files.exists(path)) // по идее созлание дирректории, если её нет
        {
            path.toFile().mkdir();
        }

        // проверка на то что путь просто до папки и до файла не идентичны(что к изначальному пути добавлено имя файла)
        if(!destinationPath.getParent().equals(path.toAbsolutePath()))
        {
            throw new StorageException("Error at saving in direction"); // коли они одинаковый кидаем ошибку
        }   

        // получаем inputSteram из присланного файла
        try(InputStream inputStream = model.getInputStream())
        {
            // копирукм файл из потока по пути на котором он будет теперь лежать
            Files.copy(inputStream, destinationPath, 
            StandardCopyOption.REPLACE_EXISTING);// сохраняем с заменой уже существующего файл(Если такой есть)
        }

        catch(IOException e)
        {
            throw new StorageException("Exception during saving the file");
        }

        return destinationPath.toString();
    }

    // получение всех книг у которых есть указаный жанр
    public ArrayList<BookModelDto> GetBooksByGenre(UUID genreId)
    {
        GenreModel genre = new GenreModel(genreId);
        
        return new ArrayList<BookModelDto>(bookModelDao.GetBooksByGenre(genre).stream().map(BookModelMapper::AsDto).collect(Collectors.toList()));
    }

    // Удаление жанров из книг. Необходимо для удаления жанройв из бд
    public boolean DeleteGenresFromBooks(ArrayList<BookModelDto> dtos, UUID genreID)
    {
        for (BookModelDto bookModelDto : dtos) 
        {
            //Удаляем из книг упоминание жанров
            bookModelDto.setGenres(new ArrayList<GenreModelDto>
                                    (bookModelDto.getGenres().stream() // получаем текущие жанры книг и берем из stream
                                                             .filter(x -> (!x.getId().equals(genreID))) // фильтруем жанры таким образом, чтобы в них не было жанров с указаным id
                                                             .collect(Collectors.toList())));// преобразование в list
        }

        // Возвращаем true если все успешно и false если при сохранении происходят какие-либо ошибки
        return bookModelDao.DeleteGenresFromBooks(new ArrayList<BookModel>(//полученный arrayList<BookModel> преобразуем в  ArrayList<BookModelDto>
            dtos.stream().map(BookModelMapper::AsEntity).collect(Collectors.toList())
        ));
    }
}
