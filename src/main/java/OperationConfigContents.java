import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperationConfigContents {

    @JsonProperty("operation_uris")
    private List<String> operationUris;

    public List<String> getOperationUris() {
        return operationUris;
    }

    public void setOperationUris(List<String> operationUris) {
        this.operationUris = operationUris;
    }
}
