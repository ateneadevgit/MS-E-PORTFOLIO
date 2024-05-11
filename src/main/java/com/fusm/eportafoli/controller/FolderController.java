package com.fusm.eportafoli.controller;

import com.fusm.eportafoli.entity.Folder;
import com.fusm.eportafoli.model.FolderRequest;
import com.fusm.eportafoli.service.IFolderService;
import com.fusm.eportafoli.utils.AppRoutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase que expone todos los servicios relacioneados con las carpetas del e-portafolio
 * ITSense Inc - Andrea Gómez
 */

@RestController
@RequestMapping(AppRoutes.EPORTAFOLIO_ROUTE + "/folder")
public class FolderController {

    @Autowired
    private IFolderService folderService;


    /**
     * Obtiene las carpetas segun un usuario
     * @param createdBy ID del usuario
     * @return lista de carpetas
     */
    @GetMapping("/user-id/{createdBy}")
    public ResponseEntity<List<Folder>> getFoldersByUser(
            @PathVariable("createdBy") String createdBy) {
        return ResponseEntity.ok(folderService.getFoldersByUser(createdBy));
    }

    /**
     * Crea una carpeta
     * @param folderRequest Modelo que contiene la información necesaria para crear una carpeta
     * @return OK
     */
    @PostMapping
    public ResponseEntity<String> createFolder(
            @RequestBody FolderRequest folderRequest) {
        folderService.createFolder(folderRequest);
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

    /**
     * Actualiza una carpeta
     * @param folderRequest Modelo que contiene la información necesaria para actualizar una carpeta
     * @param folderId ID de la carpeta
     * @return OK
     */
    @PutMapping("/{folderId}")
    public ResponseEntity<String> updateFolder(
            @RequestBody FolderRequest folderRequest,
            @PathVariable("folderId") Integer folderId) {
        folderService.updateFolder(folderRequest, folderId);
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

    /**
     * Elimina una carpeta y todos los archivos que la contiene
     * @param folderId ID de la carpeta
     * @return OK
     */
    @DeleteMapping("/{folderId}")
    public ResponseEntity<String> deleteFolder(
            @PathVariable("folderId") Integer folderId) {
        folderService.deleteFolder(folderId);
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

}
