package rm.budgeting;

import aQute.bnd.annotation.jpms.Open;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
public class OpenAiChatModelIT {

    @Autowired
    private OpenAiChatModel chatModel;

    @Test
    void should_receiveResponse_when_chatModelIsCalled() {

        /*var response = chatModel.call("Olá, me diga uma frase curta.");

        System.out.println(response);*/


        var resposta =  chatModel.call("Gere um registro de budgeting, com descricao de gasto, valor em reais e local ");

        System.out.println(resposta);

    }
}



