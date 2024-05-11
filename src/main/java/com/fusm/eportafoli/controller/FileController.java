package com.fusm.eportafoli.controller;

import com.fusm.eportafoli.dto.FileDto;
import com.fusm.eportafoli.model.*;
import com.fusm.eportafoli.service.IFileService;
import com.fusm.eportafoli.utils.AppRoutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Clase que expone todos los servicios relacionados con los archivos del e-portafolio
 * ITSense Inc - Andrea Gómez
 */

@RestController
@RequestMapping(AppRoutes.EPORTAFOLIO_ROUTE + "/file")
public class FileController {

    @Autowired
    private IFileService fileService;


    /**
     * Obtiene los archivos según una carpeta
     *
     * @param fileSearch Modelo que contiene los parámetros de búsqueda para realizar el filtrado
     * @param folderId   ID de la carpeta
     * @param params     Parámetros como page y size para la paginación de la respeusta
     * @return lista de archivos
     */
    @PostMapping("/folder-id/{folderId}")
    public ResponseEntity<ResponsePage<FileDto>> getFilesByFolder(
            @RequestBody FileSearch fileSearch,
            @PathVariable("folderId") Integer folderId,
            @RequestParam Map<String, Object> params
    ) {
        int page = params.get("page") != null ? Integer.parseInt(params.get("page").toString()) : 1;
        int size = params.get("size") != null ? Integer.parseInt(params.get("size").toString()) : 10;
        return ResponseEntity.ok(
                fileService.getFilesByFolder(
                        fileSearch,
                        PageModel.builder()
                                .page(page - 1)
                                .size(size)
                                .build(),
                        folderId)
        );
    }

    /**
     * Obtener archivo según su ID
     *
     * @param fileId ID del archivo
     * @return Archivo
     */
    @GetMapping("/{fileId}")
    public ResponseEntity<FileRequest<String>> getFileById(
            @PathVariable("fileId") Integer fileId) {
        return ResponseEntity.ok(fileService.getFileById(fileId));
    }

    /**
     * Obtiene el espacio consumido por un usuario
     *
     * @param userId ID del usuario
     * @return Modelo que representa el total de espacio consumido por un usuario
     */
    @GetMapping("/consumed/user-id/{user}")
    public ResponseEntity<ConsumedModel> getConsumedSpace(
            @PathVariable("user") String userId) {
        return ResponseEntity.ok(fileService.getConsumedSpace(userId));
    }

    /**
     * Crea un archivo
     * @param fileRequest Modelo que contiene la información necesaria para crear un archivo
     * @return OK
     */
    @PostMapping
    public ResponseEntity<String> createFile(
            @RequestBody FileRequest<FileModel> fileRequest) {
        fileService.createFile(fileRequest);
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

    /**
     * Actualiza un archivo
     * @param fileRequest Modelo que contiene la información necesaria para actualizar un archivo
     * @param fileId ID del archivo
     * @return OK
     */
    @PutMapping("/{fileId}")
    public ResponseEntity<String> updateFile(
            @RequestBody FileRequest<FileModel> fileRequest,
            @PathVariable("fileId") Integer fileId) {
        fileService.updateFile(fileRequest, fileId);
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

    /**
     * Elimina un archivo
     * @param fileId ID del archivo
     * @return OK
     */
    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(
            @PathVariable("fileId") Integer fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

}
