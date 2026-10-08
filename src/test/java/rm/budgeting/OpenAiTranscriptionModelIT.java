package rm.budgeting;

import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
public class OpenAiTranscriptionModelIT {

    @Autowired
    OpenAiAudioTranscriptionModel openAiTranscriptionModel;

    @ParameterizedTest
    @CsvSource({
            "frase_1.wav, 60 reais",
            "frase_2.wav, 120 reais",
            "frase_3.wav,  90 reais",
    })
    public void  should_contentExpectedKeywords_when_audioFilesAreProcessed(String fileName, String expectedKeywor){
        var recording = new ClassPathResource("audio/" + fileName);

      var response =  openAiTranscriptionModel.call(recording);

      System.out.println(response);
    }

}
