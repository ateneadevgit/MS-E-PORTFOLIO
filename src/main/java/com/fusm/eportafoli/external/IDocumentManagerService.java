package com.fusm.eportafoli.external;

import com.fusm.eportafoli.model.external.DocumentRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IDocumentManagerService {

    String saveFile(DocumentRequest documentRequest);
    void deleteDocumentMassive(List<String> fileName);

}
