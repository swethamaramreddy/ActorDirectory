package com.api.actordirectory.ActorDirectory.DAO;

import com.api.actordirectory.ActorDirectory.model.Actor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public interface CsvActorDao {
    List<Actor> findAll() throws Exception;
    Optional<Actor>  findById(Integer actorId) throws Exception;
    List<Actor> findByName(String name) throws Exception;
    String saveActor(Actor actor) throws Exception;
    String updateActor(Actor actor) throws  Exception;


}
