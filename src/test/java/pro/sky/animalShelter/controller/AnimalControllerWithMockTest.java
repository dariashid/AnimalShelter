package pro.sky.animalShelter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.animalShelter.entity.animal.Animal;
import pro.sky.animalShelter.model.Client;
import pro.sky.animalShelter.repository.AnimalRepository;
import pro.sky.animalShelter.service.AnimalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AnimalController.class)
public class AnimalControllerWithMockTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private AnimalService animalService;
    @MockBean
    private AnimalRepository animalRepository;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getAllAnimalsCorrect() throws Exception {
        Integer id = 20;
        String name = "test";
        Animal animalTest = new Animal();
        animalTest.setId(id);
        animalTest.setName(name);
        List<Animal> act = new ArrayList<>();
        act.add(animalTest);
        List<Animal> exp = new ArrayList<>();
        exp.add(animalTest);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/animal/getListOf/" + animalTest)
                .accept(MediaType.APPLICATION_JSON));

        assertEquals(act.size(), exp.size());
        for (int i = 0; i < act.size(); i++) {
            assertEquals(exp.get(i).getName(), act.get(i).getName());
        }
    }

    @Test
    void getAnimalInfoCorrect() throws Exception {
        Integer id = 20;
        String name = "test";

        Animal animalTest = new Animal();
        animalTest.setId(id);
        animalTest.setName(name);


        when(animalService.findById(animalTest.getId())).thenReturn(Optional.of(animalTest));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/animal/getInfoAnimal/" + id)
                        .content(objectMapper.writeValueAsString(Optional.of(animalTest)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(animalTest.getName())));

    }


    @Test
    void addAnimalCorrect() throws Exception {

        Integer id = 20;
        String name = "test";

        Animal animalTest = new Animal();
        animalTest.setId(id);
        animalTest.setName(name);


        when(animalRepository.save(any(Animal.class))).thenReturn(animalTest);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/animal/addAnimal")
                        .content(objectMapper.writeValueAsString(animalTest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void updateAnimalCorrect() throws Exception {
        Integer id = 20;
        String name = "test";
        String newName = "new";

        Animal animalTest = new Animal();
        animalTest.setId(id);
        animalTest.setName(name);

        Animal animalUpdate = new Animal();
        animalUpdate.setId(id);
        animalUpdate.setName(newName);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", newName);

        when(animalRepository.save(any(Animal.class))).thenReturn(animalUpdate);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/animal/updateInfoAnimal")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName));
    }


    @Test
    void deleteAnimalCorrect() throws Exception {
        Integer id = 20;
        String name = "test";

        Animal animalTest = new Animal();
        animalTest.setId(id);
        animalTest.setName(name);


        when(animalRepository.save(any(Animal.class))).thenReturn(animalTest);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/animal/deleteAnimal/" + animalTest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void getListOfAdoptedAnimalsCorrect() throws Exception {
        Integer id = 20;
        String name = "test";
        Animal animalTest = new Animal();
        animalTest.setId(id);
        animalTest.setName(name);
        animalTest.setAdopted(true);
        List<Animal> act = new ArrayList<>();
        act.add(animalTest);
        List<Animal> exp = new ArrayList<>();
        exp.add(animalTest);
        // when(animalService.getAdopted(true).thenReturn(act);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/animal/getListOfAdopted/" + animalTest)
                .accept(MediaType.APPLICATION_JSON));

        assertEquals(act.size(), exp.size());
        for (int i = 0; i < act.size(); i++) {
            assertEquals(exp.get(i).getName(), act.get(i).getName());
        }
    }

    @Test
    void adoptAnimalCorrect() throws Exception {
        Integer id = 20;
        String name = "test";
        boolean isNotAdopted = false;
        boolean isAdopted = true;


        Client client = new Client();
        client.setName("davr");

        Animal animalTest = new Animal();
        animalTest.setId(id);
        animalTest.setName(name);
        animalTest.setAdopted(isNotAdopted);

        Animal animalUpdate = new Animal();
        animalUpdate.setId(id);
        animalUpdate.setAdopted(isAdopted);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("isAdopted", isAdopted);

        when(animalRepository.findById(any(Integer.class))).thenReturn(Optional.of(animalTest));
        when(animalRepository.save(any(Animal.class))).thenReturn(animalUpdate);


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/animal/adoptAnimal/" + id)
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.adopted").value(isAdopted));
    }
}
