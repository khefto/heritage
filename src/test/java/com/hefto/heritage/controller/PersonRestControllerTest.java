package com.hefto.heritage.controller;

import com.hefto.heritage.dto.Person;
import com.hefto.heritage.exceptions.PersonNotFoundException;
import com.hefto.heritage.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(PersonRestController.class)
public class PersonRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;

    Person henrik;
    Person frederik;
    Person margrethe;

    @BeforeEach
    void setUp() {
        henrik = new Person(1L,"Henrik", 0L,0L);
        margrethe = new Person(2L, "Margrethe", 0L,0L);
        frederik = new Person(3L,"Frederik", 1L, 2L);

        Mockito.when(personService.findPerson((henrik.getId())))
                .thenReturn(henrik);
        Mockito.when(personService.hasChildren((henrik.getId())))
                .thenReturn(true);

        Mockito.when(personService.findPerson((frederik.getId())))
                .thenReturn(frederik);
        Mockito.when(personService.hasChildren((frederik.getId())))
                .thenReturn(false);

        Mockito.when(personService.findPerson(99L))
                .thenThrow(PersonNotFoundException.class);

    }


    @Test
    public void whenGetPerson_thenReturnPerson()
            throws Exception {

        mvc.perform(get("/Person/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Frederik"))
                .andExpect(jsonPath("$._links.delete").hasJsonPath())
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/Person/3"));
    }

    @Test
    public void whenGetPersonWithoutChildren_thenReturnDelete()
            throws Exception {

        mvc.perform(get("/Person/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Henrik"))
                .andExpect(jsonPath("$._links.delete").doesNotHaveJsonPath())
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/Person/1"));
    }

    @Test
    public void whenGetInvalidPerson_thenReturn404()
            throws Exception {

        mvc.perform(get("/Person/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}