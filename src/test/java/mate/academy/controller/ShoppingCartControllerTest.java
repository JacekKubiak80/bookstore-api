package mate.academy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mate.academy.config.SecurityConfig;
import mate.academy.dto.AddCartItemRequestDto;
import mate.academy.dto.ShoppingCartDto;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.security.JwtUtil;
import mate.academy.security.CustomUserDetailsService;
import mate.academy.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.context.annotation.Import;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShoppingCartController.class)
@Import(SecurityConfig.class)  // Importujemy konfigurację bezpieczeństwa
class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShoppingCartService cartService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    private ShoppingCartDto sampleCart;
    private String validToken;

    @BeforeEach
    void setUp() {
        sampleCart = new ShoppingCartDto();
        sampleCart.setId(1L);

        // Mockowanie generowania tokenu JWT
        String email = "user@example.com";
        when(jwtUtil.generateToken(email)).thenReturn("mocked-jwt-token-for-user");

        // Zapisz wygenerowany token do zmiennej
        validToken = "Bearer " + jwtUtil.generateToken(email);  // Token w formacie "Bearer <token>"
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = "USER")  // Ustawienie użytkownika z rolą USER
    void deleteItem_returns204() throws Exception {
        Mockito.doNothing().when(cartService).deleteItem(1L);

        mockMvc.perform(delete("/api/cart/cart-items/1")
                        .header("Authorization", validToken))  // Dodajemy token do nagłówka
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = "USER")  // Użytkownik z rolą USER
    void deleteItem_nonExisting_returns404() throws Exception {
        Mockito.doThrow(new EntityNotFoundException("Cart item not found"))
                .when(cartService).deleteItem(99L);

        mockMvc.perform(delete("/api/cart/cart-items/99")
                        .header("Authorization", validToken))  // Dodajemy token do nagłówka
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = "USER")  // Użytkownik z rolą USER
    void getCart_returns200() throws Exception {
        when(cartService.getCart()).thenReturn(sampleCart);

        mockMvc.perform(get("/api/cart")
                        .header("Authorization", validToken))  // Dodajemy token do nagłówka
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sampleCart)));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = "USER")  // Użytkownik z rolą USER
    void addBook_returns200() throws Exception {
        AddCartItemRequestDto request = new AddCartItemRequestDto();
        request.setBookId(5L);
        request.setQuantity(2);

        when(cartService.addBook(any(AddCartItemRequestDto.class))).thenReturn(sampleCart);

        mockMvc.perform(post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", validToken))  // Dodajemy token do nagłówka
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sampleCart)));
    }
}
















