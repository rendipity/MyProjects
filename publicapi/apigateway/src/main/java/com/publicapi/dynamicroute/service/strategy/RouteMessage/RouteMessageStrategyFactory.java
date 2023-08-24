package com.publicapi.dynamicroute.service.strategy.RouteMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RouteMessageStrategyFactory {

    public static Map<String,RouteMessageStrategy> routeMessageStrategyMap = new ConcurrentHashMap<>();

    public static void addRouteMessageStrategy(String handleType,RouteMessageStrategy messageStrategy){
        routeMessageStrategyMap.put(handleType,messageStrategy);
    }

    public static RouteMessageStrategy getRouteMessageStrategy(String handleType){
        return routeMessageStrategyMap.get(handleType);
    }
}

