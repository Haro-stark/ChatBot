package com.example.ChatBot.Controller;

import com.example.ChatBot.Model.Entity.Role;
import com.example.ChatBot.Service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Optional;

/**
 * @author Talha Farooq
 * @version 0.3
 * @description This class is Controller class which just takes the data from frontend and give data to frontend. Authorization is check in this class. Also
 * This class contains servlets path. It contains api list all role, find role and delete role.
 * @createdTime 11 October 2021
 */
@EnableSwagger2
@RestController
@RequestMapping("/role")
public class RoleController {
    private static final Logger LOG = LogManager.getLogger(UserController.class);
    private final RoleService roleService;

    //an ID that is used to authenticate the request header authentication token
    private final static String uuid = "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454";

    //Initialized the constructor instead of  annotation
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    private void authorized(Optional<String> authToken) throws Exception {
        if (!authToken.isPresent()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        if (!authToken.get().equals(uuid)) throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

    //This API gets all the user in database
    @GetMapping("")
    public ResponseEntity<List<Role>> getAllCategories(@RequestHeader("Authorization") Optional<String> authToken) throws Exception {
        try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to Authorize : " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return roleService.getAllCategories();
    }


    //This API adds a user into the database
    @PostMapping("/addRole")
    public ResponseEntity<List<Role>> createRole(@RequestHeader("Authorization") Optional<String> authToken,
                                                         @Validated @RequestBody List<Role> categories) throws Exception {
        try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to   Authorize : " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return roleService.addRole(categories);
    }

    //This API deletes a user from the database
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Object> deleteRole(@RequestHeader("Authorization") Optional<String> authToken,
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
        return roleService.deleteRole(id);
    }

    //This API updates the user from the status
    @PostMapping("/updateUserInfo")
    public ResponseEntity<Role> updateRole(@RequestHeader("Authorization") Optional<String> authToken,
                                                   @Validated @RequestBody Role role) throws Exception {
        try{
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to   Authorize: " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return roleService.updateRole(role);
    }

}