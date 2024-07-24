package com.operativa.gestion.service.demanda;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PrediccionDemandaFactory {

    @Autowired
    private List<PrediccionDemandaStrategy> strategyList;

    private static final Map<String, PrediccionDemandaStrategy> strategyMap = new HashMap<>();

    @PostConstruct
    public void initMyServiceCache() {
        for(PrediccionDemandaStrategy strategy : strategyList) {
            strategyMap.put(strategy.getType(), strategy);
        }
    }

    public PrediccionDemandaStrategy getStrategy(String type) throws Exception {
        PrediccionDemandaStrategy ret = strategyMap.get(type);
        if(ret == null) throw new Exception("Tipo de modelo \"" + type + "\" no soportado");
        return ret;
    }
}
