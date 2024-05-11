package com.fusm.eportafoli.controller;

import com.fusm.eportafoli.dto.FileDto;
import com.fusm.eportafoli.model.FileListModel;
import com.fusm.eportafoli.model.FileSearch;
import com.fusm.eportafoli.model.PageModel;
import com.fusm.eportafoli.model.ResponsePage;
import com.fusm.eportafoli.service.ISharedService;
import com.fusm.eportafoli.utils.AppRoutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Clase que expone los servicios relacionados con los archivos compartidos del e-portafolio
 * ITSense Inc - Andrea Gómez
 */

@RestController
@RequestMapping(AppRoutes.EPORTAFOLIO_ROUTE + "/file")
public class SharedController {

    @Autowired
    private ISharedService sharedService;


    /**
     * Obtiene los archivos compartidos con un usuario
     * @param fileSearch Modelo que contiene los parámetros necesarios para filtrar
     * @param params Parámetros como page y size que sirven para la paginación de la respuesta
     * @return archivo
     */
    @PostMapping("/shared")
    private ResponseEntity<ResponsePage<FileDto>> getSharedFiles(
            @RequestBody FileSearch fileSearch,
            @RequestParam Map<String, Object> params
            ) {
        int page = params.get("page") != null ? Integer.parseInt(params.get("page").toString()) : 1;
        int size = params.get("size") != null ? Integer.parseInt(params.get("size").toString()) : 10;
        return ResponseEntity.ok(
                sharedService.getSharedFiles(
                        fileSearch,
                        PageModel.builder()
                                .page(page - 1)
                                .size(size)
                                .build()
                )
        );
    }

}
