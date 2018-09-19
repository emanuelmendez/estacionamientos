package ar.com.gbem.istea.estacionamientos.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gbem.istea.estacionamientos.core.services.UserService;
import ar.gob.gbem.istea.estacionamientos.dtos.UserResultDTO;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserResultDTO>> listAllUsers() {
        List<UserResultDTO> users = userService.findAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<List<UserResultDTO>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UserResultDTO>>(users, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.POST,consumes = {"application/json"})
	public @ResponseBody ResponseEntity<String> addNewUser(@RequestBody UserResultDTO userData) {
		
		userService.addUser(userData);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserResultDTO> getUser(@PathVariable Long id) {
		UserResultDTO user = userService.getUserById(id);
		return new ResponseEntity<UserResultDTO>(user, HttpStatus.OK);
	}
}