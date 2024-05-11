package com.fusm.eportafoli.controller;

import com.fusm.eportafoli.dto.FileDto;
import com.fusm.eportafoli.model.*;
import com.fusm.eportafoli.service.IFavoriteService;
import com.fusm.eportafoli.utils.AppRoutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Clase que expone los servicios relacionados con los favoritos del e-portafolio
 * ITSense Inc - Andrea Gómez
 */

@RestController
@RequestMapping(value = AppRoutes.EPORTAFOLIO_ROUTE + "/file")
public class FavoriteController {

    @Autowired
    private IFavoriteService favoriteService;


    /**
     * Obtiene los archivos en favorito
     * @param fileSearch Modelo que contiene los parámetros de búsqueda para hacer el filtrado
     * @param params parámetros como page y size para la paginación de la respuesta
     * @return lista de archivos
     */
    @PostMapping("/favorite")
    private ResponseEntity<ResponsePage<FileDto>> getFavoritesFiles(
            @RequestBody FileSearch fileSearch,
            @RequestParam Map<String, Object> params
            ) {
        int page = params.get("page") != null ? Integer.parseInt(params.get("page").toString()) : 1;
        int size = params.get("size") != null ? Integer.parseInt(params.get("size").toString()) : 10;
        return ResponseEntity.ok(
                favoriteService.getFavoritesFiles(
                        fileSearch,
                        PageModel.builder()
                                .page(page - 1)
                                .size(size)
                                .build())
        );
    }

    /**
     * Agregar un archivo a favorito
     * @param fileId ID del archivo
     * @param favoriteRequest Modelo que tiene la información del archivo
     * @return OK
     */
    @PostMapping("/{fileId}/favorite/add")
    private ResponseEntity<String> addFileToFavorite(
            @PathVariable("fileId") Integer fileId,
            @RequestBody FavoriteRequest favoriteRequest
            ) {
        favoriteService.addFavorite(fileId, favoriteRequest.getCreatedBy(), favoriteRequest.getIsFavorite());
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

}
