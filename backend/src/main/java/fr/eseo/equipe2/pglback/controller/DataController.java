package fr.eseo.equipe2.pglback.controller;

import fr.eseo.equipe2.pglback.payload.request.SaveRequest;
import fr.eseo.equipe2.pglback.payload.response.Response;
import fr.eseo.equipe2.pglback.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@CrossOrigin
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService dataService;
    /**
     * @author Louise
     * when we do modification on user
     * @param saveRequest, email
     */
    @PutMapping("/change/{email}")
    public Response updateUser(@RequestBody SaveRequest saveRequest, @PathVariable String email){
        dataService.updateUser(saveRequest);
        System.out.println(saveRequest);
        return Response.ok();
    }
    /**
     * Remove user by email
     * @param email user email
     */
    @DeleteMapping("/delete/{email}")
    public void deleteUser(@PathVariable String email) {
        System.out.println("deltete" + email);
        dataService.deleteUser(email);
    }
}
