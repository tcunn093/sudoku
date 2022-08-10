package com.tcunn.sudoku.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcunn.sudoku.entity.Game;
import com.tcunn.sudoku.service.GameService;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GameService<Game, String, Integer, List<Integer>> gameService;

    @Test
    public void createShouldSucceedAndReturnANewGame() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        Game mockGame = new Game();
        when(gameService.create()).thenReturn(mockGame);

        mvc.perform(post("/game"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(mockGame)));

    }

    @Test
    public void deleteShouldSucceedAndReturnNothing() throws Exception{
        String mockGameId = "123";
        doNothing().when(gameService).delete(mockGameId);

        mvc.perform(delete("/" + "game" + "/" + mockGameId))
            .andExpect(status().isOk());

    }

}