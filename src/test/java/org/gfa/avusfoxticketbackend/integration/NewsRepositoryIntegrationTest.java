package org.gfa.avusfoxticketbackend.integration;

import jakarta.transaction.Transactional;
import java.util.List;
import org.gfa.avusfoxticketbackend.AvusFoxticketBackendApplication;
import org.gfa.avusfoxticketbackend.models.*;
import org.gfa.avusfoxticketbackend.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {AvusFoxticketBackendApplication.class})
@Transactional
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class NewsRepositoryIntegrationTest {

  @Autowired private NewsRepository newsRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private CartRepository cartRepository;

  @Autowired private CartProductRepository cartProductRepository;

  @Autowired private OrderRepository orderRepository;

  @Autowired private OrderProductRepository orderProductRepository;

  @Autowired private ProductTypeRepository productTypeRepository;

  @Autowired private ProductRepository productRepository;

  @Test
  @DirtiesContext
  public void findNewsByTitle() throws Exception {
    List<News> actual = newsRepository.searchInTitleAndContentIgnoreCase("news 10");

    assertNotNull(actual);
    assertEquals(1, actual.size());
    assertEquals("news10", actual.get(0).getTitle());
  }
}
