package oprag.project.gestionControleDAcces.handlers;


import lombok.*;
import oprag.project.gestionControleDAcces.exception.ErrorCodes;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDAO {
    private Integer httpCode;
    private ErrorCodes errorCodes;
    private String message;
    private List<String> errors= new ArrayList<>();
}