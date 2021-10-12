package com.example.ChatBot.Controller;

import com.example.ChatBot.Model.Entity.Permission;
import com.example.ChatBot.Service.PermissionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Optional;

/**
 * @author Haroon Rasheed
 * @version 1.6
 * @description This class is Controller class which just takes the data from frontend and give data to frontend. Authorization is check in this class. Also
 * This class contains servlets path. It contains api list all privilege, find privilege and delete privilege.
 * @createdTime 12 October 2021
 */
@EnableSwagger2
@RestController
@RequestMapping("/permission")
public class PermissionController {
    private static final Logger LOG = LogManager.getLogger(UserController.class);


    //an ID that is used to authenticate the request header authentication token
    private final static String uuid = "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454";

    private final PermissionService permissionService;
    //Initialized the constructor instead of  annotation
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }


    private void authorized(Optional<String> authToken) throws Exception {
        if (!authToken.isPresent()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        if (!authToken.get().equals(uuid)) throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

    //This API gets all the user in database
    @GetMapping("")
    public ResponseEntity<List<Permission>> getAllCategories(@RequestHeader("Authorization") Optional<String> authToken) throws Exception {
        try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to Authorize : " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return permissionService.getAllCategories();
    }


    //This API adds a user into the database
    @PostMapping("/addPermission")
    public ResponseEntity<List<Permission>> createPermission(@RequestHeader("Authorization") Optional<String> authToken,
                                                 @Validated @RequestBody List<Permission> categories) throws Exception {
        try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to   Authorize : " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return permissionService.addPermission(categories);
    }

    //This API deletes a user from the database
    @DeleteMapping("/deletePermission/{id}")
    public ResponseEntity<Object> deletePermission(@RequestHeader("Authorization") Optional<String> authToken,
                                             @PathVariable Long id) throws Exception {
        try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to   Authorize: " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return permissionService.deletePermission(id);
    }

    //This API updates the user from the status
    @PostMapping("/updatePermission")
    public ResponseEntity<Permission> updatePermission(@RequestHeader("Authorization") Optional<String> authToken,
                                           @Validated @RequestBody Permission permission) throws Exception {
        try{
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to   Authorize: " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return permissionService.updatePermission(permission);
    }

}

