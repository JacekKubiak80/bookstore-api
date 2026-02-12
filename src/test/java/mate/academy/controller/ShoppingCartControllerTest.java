package mate.academy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import mate.academy.dto.AddCartItemRequestDto;
import mate.academy.dto.UpdateCartItemRequestDto;
import mate.academy.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ShoppingCartController.class)
class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCart_shouldReturn200() throws Exception {
        when(cartService.getCart()).thenReturn(null);

        mockMvc.perform(get("/api/cart")
                        .with(user("user").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    void addBook_shouldReturn200() throws Exception {
        AddCartItemRequestDto request = new AddCartItemRequestDto();
        request.setBookId(1L);
        request.setQuantity(1);

        when(cartService.addBook(request)).thenReturn(null);

        mockMvc.perform(post("/api/cart")
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void updateQuantity_shouldReturn200() throws Exception {
        UpdateCartItemRequestDto request = new UpdateCartItemRequestDto();
        request.setQuantity(3);

        when(cartService.updateQuantity(1L, request)).thenReturn(null);

        mockMvc.perform(put("/api/cart/cart-items/1")
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteItem_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/cart/cart-items/1")
                        .with(user("user").roles("USER")))
                .andExpect(status().isNoContent());
    }
}
