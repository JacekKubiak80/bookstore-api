package mate.academy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import mate.academy.dto.AddCartItemRequestDto;
import mate.academy.dto.ShoppingCartDto;
import mate.academy.dto.UpdateCartItemRequestDto;
import mate.academy.security.JwtUtil;
import mate.academy.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ShoppingCartControllerCoverageTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShoppingCartService cartService;

    @MockBean
    private JwtUtil jwtUtil;

    @BeforeEach
    void setup() {
        when(cartService.getCart()).thenReturn(new ShoppingCartDto());
        when(cartService.addBook(any())).thenReturn(new ShoppingCartDto());
        when(cartService.updateQuantity(anyLong(), any())).thenReturn(new ShoppingCartDto());
    }

    @Test
    @WithMockUser(roles = "USER")
    void runAllEndpoints_forCoverage() throws Exception {
        // GET
        mockMvc.perform(get("/api/cart"))
                .andExpect(status().isOk());

        // POST
        AddCartItemRequestDto addRequest = new AddCartItemRequestDto();
        addRequest.setBookId(1L);
        addRequest.setQuantity(1);
        mockMvc.perform(post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRequest)))
                .andExpect(status().isOk());

        // PUT
        UpdateCartItemRequestDto updateRequest = new UpdateCartItemRequestDto();
        updateRequest.setQuantity(1);
        mockMvc.perform(put("/api/cart/cart-items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        // DELETE
        mockMvc.perform(delete("/api/cart/cart-items/1"))
                .andExpect(status().isNoContent());
    }
}

