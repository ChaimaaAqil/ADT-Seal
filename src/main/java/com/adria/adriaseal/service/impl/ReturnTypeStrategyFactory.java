package com.adria.adriaseal.service.impl;

import com.adria.adriaseal.service.ReturnTypeStrategy;
import com.adria.adriaseal.service.ReturnTypeStrategyName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Set;

@Component
public class ReturnTypeStrategyFactory {

    private EnumMap<ReturnTypeStrategyName, ReturnTypeStrategy> strategies;

    @Autowired
    public ReturnTypeStrategyFactory(Set<ReturnTypeStrategy> strategySet) {
        createStrategy(strategySet);
    }

    public ReturnTypeStrategy findStrategy(ReturnTypeStrategyName strategyName) {
        return strategies.get(strategyName);
    }

    private void createStrategy(Set<ReturnTypeStrategy> strategySet) {
        strategies = new EnumMap<>(ReturnTypeStrategyName.class);
        strategySet.forEach(
                strategy -> strategies.put(strategy.getStrategyName(), strategy));
    }
}
