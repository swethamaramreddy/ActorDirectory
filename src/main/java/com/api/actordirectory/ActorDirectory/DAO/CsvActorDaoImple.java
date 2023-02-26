package com.api.actordirectory.ActorDirectory.DAO;

import com.api.actordirectory.ActorDirectory.model.Actor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
@Component
public class CsvActorDaoImple implements CsvActorDao{
    @Value("${actor.directory.filename}")
     String FILENAME;

    /*this method return the Directory file full path .
     */
     public File getFile() {

         File file =null;
         try {
              file = new ClassPathResource(FILENAME).getFile();
         }catch (Exception e)
         {
             e.toString();
         }
         return file;

     }

    /*
    this method gets the all actor details from the directory file
    and it is reusing in the below methods as per the requirement .
    */
    @Override
    public List<Actor> findAll() throws  Exception{

        List<Actor> actors = new ArrayList<Actor>();
        try (BufferedReader reader=new BufferedReader(new FileReader(getFile()))) {

            String line =null;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                System.out.println("fields[0] "+fields[0]+" fields [1] "+fields[1] + "  fields[2] "+fields[2]);
                String name=fields[0];
                Integer id=Integer.parseInt(fields[1]);
                Double height=Double.parseDouble(fields[2]);
                String DOB=fields[3];
                actors.add(new Actor(name,id ,height ,DOB));

            }
        }
        return actors;

    }
    /*this method gets the all actor details using findAll() method and then filter the data by given actorId
          Here we used the Optional class to avoid the null pointer exception
      */
    @Override
    public Optional<Actor>  findById(Integer actorId) throws  Exception{
        return this.findAll().stream().filter(actor->actor.getActorId().equals(actorId)).findFirst();
    }
    /*This method gets the all actor details using findAll() method and then filter the data by given name
             in the filter condition ,1st converting name to lower case to ignore the case sensitive then checking the given name is present in list or not .
              */
    @Override
    public List<Actor> findByName(String name) throws Exception{
        return this.findAll().stream().filter(actor->actor.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }
    /*
    This method save the given actor details into ActorDirectory file
    */
    @Override
    public String saveActor(Actor actor) throws Exception {
        FileWriter writer = new FileWriter(this.getFile(), true);
        actor.setActorId(this.getNextId());
       writer.write(actor.toString());
        writer.write("\n");
        writer.close();
        return "Actor saved successfully";
    }


    /*
    This method update the given actor details into ActorDirectory file
    If the given Id not present in directory then it returns the "Actor not Exists in Directory" message.
     */
    @Override
    public String updateActor(Actor actor) throws  Exception{

        List<Actor> actors = findAll();
        Optional<Actor> existingActor = actors.stream().filter(a->a.getActorId().equals(actor.getActorId())).findFirst();
        if (existingActor.isPresent()) {
            actors.remove(existingActor.get());
            actors.add(actor);
            this.writeActorsToFile(actors);
            return "Actor details updated successfully";
        } else {
            return "Actor not found in the directory";
        }
    }

    /*
    This method writes the data into directory file .This is method called from updateActor() method to
    add the updated data into directory.
     */
    private  void writeActorsToFile(List<Actor> actors) throws IOException {
        FileWriter writer = new FileWriter(this.getFile());
        for (Actor actor : actors) {
            writer.write(actor.toString());
            writer.write("\n");
        }
        writer.close();
    }

    /*This method returns the Actor Id for new actors .
     */
    private int getNextId() throws Exception {
        List<Actor> actors = findAll();
        if (actors.isEmpty()) {
            return 1;
        }
        Optional<Actor> maxActorId=actors.stream().max(Comparator.comparing(Actor::getActorId));
        Actor lastActor = actors.get(actors.size() - 1);
        return maxActorId.get().getActorId()+1;
    }

}
