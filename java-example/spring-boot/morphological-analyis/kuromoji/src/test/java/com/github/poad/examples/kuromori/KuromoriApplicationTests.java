package com.github.poad.examples.kuromori;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class KuromoriApplicationTests {

	@Test
	void contextLoads() {
        KuromoriApplication.main();
        assertTrue(true);
	}

}
