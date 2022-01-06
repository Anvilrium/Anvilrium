package anvilrium.i18n;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import anvilrium.common.FileUtils;

public class TranslationProvider {

	private static final Logger LOGGER = LogManager.getLogger();

	private final Map<String, Translation> translations = new ConcurrentHashMap<>();
	private final String defaultLanguage;

	public TranslationProvider(String defaultLanguage) throws IOException {
		this.defaultLanguage = Objects.requireNonNull(defaultLanguage);
		switchLanguage(defaultLanguage);
	}

	public String getTranslatedString(String translationKey) {
		if (translations.containsKey(translationKey)) {
			return translations.get(translationKey).translation();
		}
		return translationKey;
	}

	public boolean isFallback(String translationKey) {
		if (translations.containsKey(translationKey)) {
			return translations.get(translationKey).isFallback;
		}
		return true;
	}

	public void switchLanguage(String newLanguage) throws IOException {
		translations.clear();

		boolean isDefaultLanguage = newLanguage.equalsIgnoreCase(defaultLanguage);

		try (InputStreamReader selectedLanguageReader = new InputStreamReader(
				FileUtils.getFileFromResourceAsStream("/" + newLanguage + ".json"));
				InputStreamReader defaultLanguageReader = new InputStreamReader(
						FileUtils.getFileFromResourceAsStream("/" + defaultLanguage + ".json"))) {

			JsonObject selectedLanguageJson = JsonParser.parseReader(selectedLanguageReader).getAsJsonObject();
			JsonObject defaultLanguageJson;
			if (!isDefaultLanguage) {
				defaultLanguageJson = JsonParser.parseReader(defaultLanguageReader).getAsJsonObject();
			} else {
				defaultLanguageJson = selectedLanguageJson;
			}

			for (String key : defaultLanguageJson.keySet()) {
				String val;
				boolean isFallback = false;
				if (selectedLanguageJson.keySet().contains(key)) {
					val = selectedLanguageJson.get(key).getAsString();
				} else {
					isFallback = true;
					val = defaultLanguageJson.get(key).getAsString();
					LOGGER.debug("Translation key " + key + " that is present in default language " + defaultLanguage
							+ " is not present in new language " + newLanguage);
				}

				translations.put(key, new Translation(val, isFallback));

			}
		}
	}

	private record Translation(String translation, boolean isFallback) {
	}

}
