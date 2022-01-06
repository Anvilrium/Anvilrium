package anvilrium.test.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import anvilrium.common.FileUtils;
import anvilrium.i18n.TranslationProvider;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class TranslationTest {

	static TranslationProvider translationProvider;

	@BeforeAll
	static void setupAll() throws IOException {
		translationProvider = new TranslationProvider("en_us");
	}

	@Order(value = 0)
	@Test
	void testFileExisting() throws FileNotFoundException {
		FileUtils.getFileFromResourceAsStream("/en_us.json");
	}

	@Order(value = 10)
	@Test
	void testTranslatedString1() {
		assertEquals("This is a Test", translationProvider.getTranslatedString("test"));
		assertEquals("Test 2", translationProvider.getTranslatedString("test2"));
	}

	@Order(value = 20)
	@Test
	void testLanguageSwitch() throws IOException {
		translationProvider.switchLanguage("de_de");
	}

	@Order(value = 30)
	@Test
	void testTranslatedString2() {
		assertEquals("Das ist ein Test", translationProvider.getTranslatedString("test"));
		assertEquals("Test 2", translationProvider.getTranslatedString("test2"));
	}
	
	@Order(value = 30)
	@Test
	void testFallback() {
		assertTrue(translationProvider.isFallback("test2"));
	}

}
