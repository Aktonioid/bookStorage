package com.bookStrore.bookStorage;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.bookStrore.bookStorage.dto.models.GenreModelDto;
import com.bookStrore.bookStorage.services.genre.GenreService;

@SpringBootTest
class BookStorageApplicationTests {

	
	private GenreService service = Mockito.mock();

	@Test
	void bookCreateTest() 
	{
		Assumptions.assumeTrue(service.CreateEntity(new GenreModelDto(null, "test")));
	}	


}
