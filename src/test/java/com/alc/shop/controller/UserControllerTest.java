package com.alc.shop.controller;

import com.alc.shop.model.dao.User;
import com.alc.shop.model.dto.UserDto;
import com.alc.shop.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUser() throws Exception {
        UserDto userDto = UserDto.builder()
                .firstName("a")
                .lastName("b")
                .email("d@d.d")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.firstName").value(userDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDto.getLastName()))
                .andExpect(jsonPath("$.birthDate").value(userDto.getBirthDate().toString()));
    }

    @Test
    void shouldNotSaveUserWithIncorrectData() throws Exception {
        UserDto userDto = UserDto.builder()
                .firstName("")
                .lastName("")
                .email("")
                .birthDate(LocalDate.now())
                .build();

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*].field", containsInAnyOrder("birthDate", "firstName",
                        "email", "lastName")))
                .andExpect(jsonPath("$[*].message", containsInAnyOrder("You need 18 years old",
                        "must not be blank", "must not be blank", "must not be blank")));
    }

    @Test
    void shouldNotGetUserById() throws Exception {
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void shouldGetUserById() throws Exception {
        User user = userRepository.save(User.builder()
                .birthDate(LocalDate.now())
                .firstName("first")
                .lastName("last")
                .email("email")
                .build());

        mockMvc.perform(get("/api/v1/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.birthDate").value(user.getBirthDate().toString()));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        User user = userRepository.save(User.builder()
                .birthDate(LocalDate.now())
                .firstName("first")
                .lastName("last")
                .email("email")
                .build());
        UserDto userDto = UserDto.builder()
                .firstName("a")
                .lastName("b")
                .email("d@d.d")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        mockMvc.perform(put("/api/v1/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.firstName").value(userDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDto.getLastName()))
                .andExpect(jsonPath("$.birthDate").value(userDto.getBirthDate().toString()));
    }

    @Test
    void shouldNotUpdateUserWhenNotFound() throws Exception {
        UserDto userDto = UserDto.builder()
                .firstName("a")
                .lastName("b")
                .email("d@d.d")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void shouldNotUpdateUserWhenIncorrectData() throws Exception {
        UserDto userDto = UserDto.builder()
                .firstName("")
                .lastName("")
                .email("")
                .birthDate(LocalDate.now())
                .build();

        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*].field", containsInAnyOrder("birthDate", "firstName",
                        "email", "lastName")))
                .andExpect(jsonPath("$[*].message", containsInAnyOrder("You need 18 years old",
                        "must not be blank", "must not be blank", "must not be blank")));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        User user = userRepository.save(User.builder()
                .birthDate(LocalDate.now())
                .firstName("first")
                .lastName("last")
                .email("email")
                .build());

        mockMvc.perform(delete("/api/v1/users/" + user.getId()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void shouldGetUserPage() throws Exception {
        userRepository.save(User.builder()
                .birthDate(LocalDate.now())
                .firstName("first")
                .lastName("last")
                .email("email")
                .build());
        userRepository.save(User.builder()
                .birthDate(LocalDate.now())
                .firstName("first1")
                .lastName("last2")
                .email("email3")
                .build());

        mockMvc.perform(get("/api/v1/users/")
                        .queryParam("page", "0")
                        .queryParam("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.numberOfElements").value(2));
    }

}
