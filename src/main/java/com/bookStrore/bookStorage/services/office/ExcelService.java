package com.bookStrore.bookStorage.services.office;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.bookStrore.bookStorage.dto.mappers.EstimateMapper;
import com.bookStrore.bookStorage.dto.models.EstimateDto;
import com.bookStrore.bookStorage.dto.models.OrderModelDto;
import com.bookStrore.bookStorage.dto.models.SuppliesModelDto;


//класс необходимый для создания смет
@Service
@PropertySource(value =  "classpath:files.properties" )
public class ExcelService 
{
    @Autowired
    private Environment env;
    

    public String SupplyExcelEstimate(SuppliesModelDto dto) 
    {
        try
        {
            return CreateExcelEstimate(
                new ArrayList<EstimateDto>(dto.getBooks().stream().map(EstimateMapper::AsEstimate).collect(Collectors.toList())),// преобразуем в модель, которую принимат CreateExcelEstiamte 
                dto.getProvider(),
                "Некий магазин книг",
                env.getProperty("files.estimates.supplies"));
        }   
        catch(IOException e
        )
        {
            e.printStackTrace();    
        }
        return null;
    }

    public String OrderExcelEstimate(OrderModelDto dto)
    {
        try
        {
            return CreateExcelEstimate(
                new ArrayList<EstimateDto>(dto.getBooks().stream().map(EstimateMapper::AsEstimate).collect(Collectors.toList())),// преобразуем в модель, которую принимат CreateExcelEstiamte 
                "Некий магазин книг",
                dto.getUserFullName(),
                env.getProperty("files.estimates.sendings"));
        }   
        catch(IOException e
        )
        {
            e.printStackTrace();    
        }
        return null;
    }

    //Метод для создания смет по шаблону. Есть небольшая заготовка, которая потом доплняется
    private String CreateExcelEstimate(ArrayList<EstimateDto> books, 
                                    String sender, //отправаитель 
                                    String recipient, //получатель
                                    //Куда записывать файлы
                                    String storage) throws IOException
    {
        // путь куда сохранять сметы
        Path path = Path.of(storage).toAbsolutePath();

        
        // получение и форматирование даты
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateTime = formater.format(LocalDateTime.now());

        // dto.setRealDeliveryDate(new Date());// записываем в модель реальную дату прибытия

        String fileName = sender+"_"+ dateTime+".xlsx";

        if(!Files.exists(path)) // коли такой папки нет, создаем
        {
            path.toFile().mkdir();
        }

        
        // файл, который будем копировать
        Path copyPath = path.resolve(
            Paths.get("template.xlsx")
        ).toAbsolutePath();

        // то куда копируем(меняется только название файла)
        Path destinationPath = path.resolve(Paths.get(fileName)).toAbsolutePath();

        FileInputStream copy = new FileInputStream(copyPath.toFile());

        // создание файла
        Files.copy(copy, destinationPath, StandardCopyOption.REPLACE_EXISTING);

        // начало работы с excel
        Workbook book = new XSSFWorkbook(new FileInputStream(destinationPath.toFile())); // Открываем нужный файл
        Sheet sheet = book.getSheet("sheet");//получаем лист с которым будем работать
        
        Row row = sheet.getRow(1);// получаем второй ряд(нумерация идет с нуля)

        Cell cell = row.getCell(2);// третью строчку  

        cell.setCellValue(sender);// устанавливаем в нее название поставщика

        cell = row.createCell(12); // Ячейка куда записывать того кто принимает заказ
        cell.setCellValue(recipient); // Записываем того кто принимает в ячейку

        row = sheet.getRow(3);// Вибираем ряд и ячейку куда записывать дату
        cell = row.createCell(12);
        
        DataFormat dataFormat = book.createDataFormat();// настраиваем формат ячейки на дату
        CellStyle cellStyle = book.createCellStyle();
        cellStyle.setDataFormat(dataFormat.getFormat("dd.mm.yyyy"));
        cell.setCellStyle(cellStyle);// установка даты в ячейку
        
        cell.setCellValue(dateTime);

        cellStyle = book.createCellStyle();//создаем формат ячейки для расположения текста на сережине ячейки
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        int sheetRow = 5;
        int resultPrice = 0;

        for(int i = 0;i < books.size(); i++)
        {
            row = sheet.createRow(sheetRow);
            // объединяем ячейки
            sheet.addMergedRegion(new CellRangeAddress(sheetRow,sheetRow, 0,1));
            sheet.addMergedRegion(new CellRangeAddress(sheetRow,sheetRow, 2,10));
            sheet.addMergedRegion(new CellRangeAddress(sheetRow,sheetRow, 13,14));

            cell = row.createCell(0);//порядковый номер
            cell.setCellValue(i+1);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);//название книги
            cell.setCellValue(books.get(i).getBook().getBookName());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(11);//колличество книг
            cell.setCellValue(books.get(i).getBookCount());
            cell.setCellStyle(cellStyle);
            
            cell = row.createCell(12);//Стоимость книги
            cell.setCellValue(books.get(i).getBook().getPrice());
            cell.setCellStyle(cellStyle);
            
            cell = row.createCell(13);// итоговая стоимость за книги
            int booksPrice = books.get(i).getBook().getPrice() * books.get(i).getBookCount();
            cell.setCellValue(booksPrice);
            cell.setCellStyle(cellStyle);

            resultPrice+= booksPrice;
            sheetRow++;
        }

        // запись итоговой стоимости всех книг
        sheetRow++;
        row = sheet.createRow(sheetRow);
        
        cell = row.createCell(12);// создаем ячейки для итоговой цены
        cell.setCellValue("Итоговая цена");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(13);// устанавливаем ячейку цены
        cell.setCellValue(resultPrice);
        cell.setCellStyle(cellStyle);

        //записываем все в файл
        book.write(new FileOutputStream(destinationPath.toFile()));
        book.close();// закрываем файл

        return destinationPath.toString();
    }
}
