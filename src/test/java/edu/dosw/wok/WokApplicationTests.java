package edu.dosw.wok;

import edu.dosw.wok.main.WokApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// Se indica la clase principal porque está en el subpaquete main
@SpringBootTest(classes = WokApplication.class)
class WokApplicationTests {

	@Test
	void contextLoads() {
	}

}
