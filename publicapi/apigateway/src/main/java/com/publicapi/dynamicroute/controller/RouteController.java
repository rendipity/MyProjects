package com.publicapi.dynamicroute.controller;

import com.publicapi.dynamicroute.apimodal.ApiResource;
import com.publicapi.dynamicroute.service.dynamicrote.DynamicRouteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Resource
    DynamicRouteService dynamicRouteService;

    @GetMapping("/delete")
    public void deleteRoute(String routeId){
        dynamicRouteService.removeRoute(routeId);
    }

    @PostMapping("/add")
    public void deleteRoute(@RequestBody ApiResource apiResource){
        dynamicRouteService.addRoute(apiResource);
    }
}
