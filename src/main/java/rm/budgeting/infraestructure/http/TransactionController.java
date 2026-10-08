package rm.budgeting.infraestructure.http;

import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rm.budgeting.application.ListTransactionCategoryUseCase;
import rm.budgeting.application.PersistTransactionUseCase;
import rm.budgeting.domain.Category;
import rm.budgeting.infraestructure.http.request.TransactionRequest;
import rm.budgeting.infraestructure.http.response.TransactionResponse;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final PersistTransactionUseCase persistTransactionUseCase;
    private final ListTransactionCategoryUseCase listTransactionCategoryUseCase;
    private final TranscriptionModel transcriptionModel;
    private final ChatClient chatClient;
    private final TextToSpeechModel  textToSpeechModel;

    public TransactionController(PersistTransactionUseCase persistTransactionUseCase,
                                 ListTransactionCategoryUseCase listTransactionCategoryUseCase,
                                 TranscriptionModel transcriptionModel,
                                 @Value("classpath:/prompts/system-message.st")  Resource systemPromprt,
                                 ChatClient.Builder chatClientBuilder,
                                 TextToSpeechModel  textToSpeechModel) throws IOException {
        this.persistTransactionUseCase = persistTransactionUseCase;
        this.listTransactionCategoryUseCase = listTransactionCategoryUseCase;
        this.transcriptionModel = transcriptionModel;
        this.chatClient = chatClientBuilder
                .defaultSystem(systemPromprt.getContentAsString(Charset.defaultCharset()))
                .defaultTools(persistTransactionUseCase,  listTransactionCategoryUseCase)
                .build();
        this.textToSpeechModel = textToSpeechModel;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@RequestBody TransactionRequest request) {
      var transaction =  persistTransactionUseCase.execute(request.toInput());
      return TransactionResponse.from(transaction);
    }

    @GetMapping("/{category}")
    public List<TransactionResponse> getTransactions(@PathVariable Category category) {
        return listTransactionCategoryUseCase.execute(category).stream().map(TransactionResponse::from).toList();
    }

    @PostMapping(value = "/ai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "audio/mp3")
    ResponseEntity<Resource> transcribe(@RequestParam("file") MultipartFile file) {
        var userMessage = transcriptionModel.transcribe(file.getResource());
        var result = chatClient.prompt().user(userMessage).call().content();


        byte[] audio = textToSpeechModel.call(result);
        var resource = new ByteArrayResource(audio);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                        .filename("audio.mp3")
                        .build()
                        .toString())
                .body(resource);

    }

}
