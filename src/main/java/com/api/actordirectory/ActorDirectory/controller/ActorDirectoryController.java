/*
This is the RestController class .
Its has below four end points :
1) /findById -> it accepts the request Parameter actorId and get the details based on the actorId .
               If the actorId is not present in ActorDirectory then it gives response "Actor not exists in ActorDirectory"
2) /findByName -> it accepts the request Parameter name and get the details based on the name .
               If the name is not present in ActorDirectory then it gives response "Actor not exists in ActorDirectory"
3) /saveActor -> This end point accepts request Body as Actor object and it's saved into ActorDirectory.
4) /updateActor -> This end point accepts request Body as Actor object and it's update the given actor details into ActorDirectory if given actor present in directory
                   Otherwise returns error message "Acot not exists in Directory"
 */

package com.api.actordirectory.ActorDirectory.controller;
import com.api.actordirectory.ActorDirectory.model.Actor;
import com.api.actordirectory.ActorDirectory.service.ActorDirectoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/actors")
public class ActorDirectoryController {

     @Autowired
    ActorDirectoryService actorDirectoryService;

     /*This end point accepts the one request paramter (actorId)  and gets the Actor details from ActorDirectory by actorId
        If the actorId not found in ActorDirectory then it gives the "Actor not exists in ActorDirectory" Message .
     */
     @GetMapping("/findById")
    public String findById(@RequestParam("actorId") Integer actorId) throws Exception
    {
        if (actorDirectoryService.findById(actorId).isPresent())
        {
            ObjectMapper mapper=new ObjectMapper();
            Actor actor=actorDirectoryService.findById(actorId).get();
            return mapper.writeValueAsString(actor); //using ObjectMapper class converting List into Json string object
        }
        return "Actor not exists in ActorDirectory";
    }

    /*This end point accepts the one request paramter (name)  and gets the Actor details from ActorDirectory by name
      If the actorId not found in ActorDirectory then it gives the "Actor not exists in ActorDirectory" Message .
    */
    @GetMapping("/findByName")
    public String findByName(@RequestParam("name") String name) throws Exception
    {
        List<Actor> actorList= actorDirectoryService.findByName(name);
        ObjectMapper mapper=new ObjectMapper();
        if(actorList.size()>0)
        {
            return mapper.writeValueAsString(actorList); //using ObjectMapper class converting List into Json string object
        }
        else {
            return "Actor not exists in ActorDirectory";
        }
    }

    /*
    This end points is use to add the new actor into ActorDirectory
     */
    @RequestMapping("/saveActor")
    public String saveActor(@RequestBody Actor actor) throws Exception
    {
        return actorDirectoryService.saveActor(actor);
    }

    /*
    This end points is use to update the existing actor into ActorDirectory
     */
    @RequestMapping("/updateActor")
    public String updateActor(@RequestBody Actor actor) throws  Exception
    {
        return actorDirectoryService.updateActor(actor);
    }

}
