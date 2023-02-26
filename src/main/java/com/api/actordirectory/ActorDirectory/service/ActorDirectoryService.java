package com.api.actordirectory.ActorDirectory.service;


import com.api.actordirectory.ActorDirectory.DAO.CsvActorDao;
import com.api.actordirectory.ActorDirectory.model.Actor;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorDirectoryService
{
    private CsvActorDao csvActorDao;
    public ActorDirectoryService(CsvActorDao csvActorDao)
    {
        this.csvActorDao=csvActorDao;
    }
    public Optional<Actor> findById(Integer actorId) throws Exception
    {
        return csvActorDao.findById(actorId);
    }
    public List<Actor> findByName(String name) throws Exception
    {
        return csvActorDao.findByName(name);
    }
    public String saveActor(Actor actor) throws Exception
    {
        return csvActorDao.saveActor(actor);
    }
    public String updateActor(Actor actor) throws  Exception
    {
        return csvActorDao.updateActor(actor);
    }



}
