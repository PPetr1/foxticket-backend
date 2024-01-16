package org.gfa.avusfoxticketbackend.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseProductDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AdminController.class)

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

  @MockBean private ProductService productService;
  @MockBean private JwtService jwtService;
  @MockBean private UserService userService;
  @MockBean private NewsService newsService;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  private Product product;
  private ProductDTO productDTO;
  private RequestProductDTO requestProductDTO;

  @BeforeEach
  public void init() {
    product = new Product("Single Ticket", 1.99, 2, "Valid for 2 hrs", Type.Adventure);
    productDTO = new ProductDTO(1L, "Single Ticket", 1.99, 2, "Valid for 2 hrs", "Adventure");
    requestProductDTO =
        new RequestProductDTO("Single Ticket", 1.99, 2, "Valid for 2 hrs", "Adventure");
  }

  @Test
  public void adminControllerEditProductReturnEdited() throws Exception {
    when(productService.updateProduct(requestProductDTO, 1L)).thenReturn(productDTO);

    ResultActions response =
        mockMvc.perform(
            patch("/api/admin/products/{productId}/", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(requestProductDTO)));

    response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  @Autowired ObjectMapper objectMapper;

  @Autowired private MockMvc mockMvc;

  @MockBean private ProductService productService;
  @MockBean private JwtService jwtService;
  @MockBean private NewsService newsService;
  @MockBean private UserService userService;

  @Test
  public void createNewProduct_status200() throws Exception {
    RequestProductDTO requestProductDTO =
        new RequestProductDTO("peckaaaa vylet", 13.5, 4, "bomba vylet pecky fest", "Cultural");
    ResponseProductDTO responseProductDTO =
        new ResponseProductDTO(
            3L, "peckaaaaaa vylet", 13.5, "4", "bomba vylet pecky fest", "Cultural");
    when(productService.createNewProductAndReturn(requestProductDTO))
        .thenReturn(responseProductDTO);
    mockMvc
        .perform(
            post("/api/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsString(requestProductDTO)))
        .andExpect(status().is(200))
        .andExpect(content().json(objectMapper.writeValueAsString(responseProductDTO)))
        .andDo(print());
  }

  @Test
  public void createNewProduct_ThrowsException_BodyRequired() throws Exception {
    ApiRequestException response =
        new ApiRequestException("/api/admin/products", "Body is required");
    when(productService.createNewProductAndReturn(null)).thenThrow(response);
    mockMvc
        .perform(
            post("/api/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsString(null)))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.endpoint", CoreMatchers.is(response.getEndpoint())))
        .andExpect(jsonPath("$.message", CoreMatchers.is(response.getMessage())))
        .andDo(print());
  }

  @Test
  public void createNewProduct_ThrowsException_NameNull() throws Exception {
    ApiRequestException response =
        new ApiRequestException("/api/admin/products", "Name is required");
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDuration(4);
    requestProductDTO.setDescription("description");
    requestProductDTO.setType("Adventure");

    when(productService.createNewProductAndReturn(requestProductDTO)).thenThrow(response);

    mockMvc
        .perform(
            post("/api/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsBytes(requestProductDTO)))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.endpoint", CoreMatchers.is(response.getEndpoint())))
        .andExpect(jsonPath("$.message", CoreMatchers.is(response.getMessage())));
  }

  @Test
  public void createNewProduct_ThrowsException_NameEmpty() throws Exception {
    ApiRequestException response =
        new ApiRequestException("/api/admin/products", "Name is required");
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setName("");
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDuration(4);
    requestProductDTO.setDescription("description");
    requestProductDTO.setType("Adventure");

    when(productService.createNewProductAndReturn(requestProductDTO)).thenThrow(response);

    mockMvc
        .perform(
            post("/api/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsBytes(requestProductDTO)))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.endpoint", CoreMatchers.is(response.getEndpoint())))
        .andExpect(jsonPath("$.message", CoreMatchers.is(response.getMessage())));
  }

  @Test
  public void createNewProduct_ThrowsException_DescriptionNull() throws Exception {
    ApiRequestException response =
        new ApiRequestException("/api/admin/products", "Description is required");
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDuration(4);
    requestProductDTO.setType("Adventure");

    when(productService.createNewProductAndReturn(requestProductDTO)).thenThrow(response);

    mockMvc
        .perform(
            post("/api/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsBytes(requestProductDTO)))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.endpoint", CoreMatchers.is(response.getEndpoint())))
        .andExpect(jsonPath("$.message", CoreMatchers.is(response.getMessage())));
  }

  @Test
  public void createNewProduct_ThrowsException_DescriptionEmpty() throws Exception {
    ApiRequestException response =
        new ApiRequestException("/api/admin/products", "Description is required");
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDuration(4);
    requestProductDTO.setDescription("");
    requestProductDTO.setType("Adventure");

    when(productService.createNewProductAndReturn(requestProductDTO)).thenThrow(response);

    mockMvc
        .perform(
            post("/api/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsBytes(requestProductDTO)))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.endpoint", CoreMatchers.is(response.getEndpoint())))
        .andExpect(jsonPath("$.message", CoreMatchers.is(response.getMessage())));
  }
}