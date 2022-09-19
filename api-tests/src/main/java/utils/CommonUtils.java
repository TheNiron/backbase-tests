package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class CommonUtils {
  public String readFile(String fileName) throws IOException {
    File file = new File(Constants.FILE_PATH + fileName);
    return FileUtils.readFileToString(file, Constants.UTF8);
  }

  public String getInputDataForArticles(String file, String field) throws IOException {
    String content = this.readFile(Constants.ARTICLES_DIR + file + ".json");
    JsonObject jsonArticle = new JsonParser().parse(content).getAsJsonObject();
    return String.valueOf(jsonArticle.get(field));
  }

  public String generateRandomNumber() {
    int randomNumber;
    int min = 9999;
    int max = 1000000;
    randomNumber = (int) (Math.random() * (max - min + 1) + min);
    return Integer.toString(randomNumber);
  }
}
