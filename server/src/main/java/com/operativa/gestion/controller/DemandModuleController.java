package com.operativa.gestion.controller;

import com.operativa.gestion.dto.ParametrosDemandaDTO;
import com.operativa.gestion.dto.PrediccionDemandaModeloDTO;
import com.operativa.gestion.dto.ProximoPeriodoDemandaDTO;
import com.operativa.gestion.service.DemandModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/demandModule")
public class DemandModuleController {

    @Autowired
    DemandModuleService demandModuleService;


    @GetMapping(path = "/generalParameters")
    public ResponseEntity<?> getGeneralParameters(){
        return ResponseEntity.ok(demandModuleService.obtenerParametros());
    }

    @PostMapping(path = "/generalParameters")
    public ResponseEntity<?> saveGeneralParameters(@RequestBody ParametrosDemandaDTO dtoGeneralDemandParameters) throws Exception {
        demandModuleService.guardarParametros(dtoGeneralDemandParameters);
        return ResponseEntity.ok("");
    }

    @GetMapping(path = "/model/{id}")
    public ResponseEntity<?> getModels(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(demandModuleService.obtenerModelos(id));
    }

  /*  @PutMapping(path = "/model/{id}")
    public ResponseEntity<?> putModel(@RequestBody PrediccionDemandaModeloDTO dto, @PathVariable Long id,
                                      @RequestParam("family") Boolean family){
            Long ret = demandModuleService.(dto, id, family);
            return ResponseEntity.ok("{\"id\": " + ret + "}");
    }

    @DeleteMapping(path = "/model/{id}")
    public ResponseEntity<?> deleteModel(@PathVariable Long id){
        try {
            demandModuleService.deleteModel(id);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }
*/
    @GetMapping(path = "/demandPrediction/{id}")
    public ResponseEntity<?> getDemandPrediction(@PathVariable Long id,
                                                 @RequestParam("family") Boolean family,
                                                 @RequestParam("desde") Long desde,
                                                 @RequestParam("predecirMesActual") Boolean predecirMesActual) throws Exception {
        return ResponseEntity.ok(demandModuleService.predecirDemanda(id, family, new Date(desde), predecirMesActual));
    }

    @PostMapping(path = "/demandPrediction")
    public ResponseEntity<?> getDemandPrediction(@RequestBody ProximoPeriodoDemandaDTO dto) throws Exception {
            demandModuleService.guardarProximoPeriodoDemanda(dto);
            return ResponseEntity.ok("");
    }
}
