package com.example.springcore_homework;

import com.example.springcore_homework.models.User;
import com.example.springcore_homework.models.UserRoleEnum;
import com.example.springcore_homework.security.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpicyIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    private HttpHeaders headers;
    private ObjectMapper mapper = new ObjectMapper();


    private final String customerX = "4";
    private final String customerY = "3";

    private FoodIntegrationTest.FoodDto food1 = FoodIntegrationTest.FoodDto.builder()
            .id(null)
            .name("쉑버거 더블")
            .price(10900)
            .build();

    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Nested
    @DisplayName("인가 , 권한")
    class JoinTest {

        private Principal mockPrincipal;

        private MockMvc mvc;

        @BeforeEach
        public void setup() {
            mvc = MockMvcBuilders.webAppContextSetup(context)
                    .apply(springSecurity(new MockSpringSecurityFilter()))
                    .build();
        }


        // 로그인 한거처럼 하기위해서
        private void mockCustomerSetup() {
        // Mock 테스트 유져 생성
            String username = "CUSTOMSER";
            String password = "hope!@#";
            UserRoleEnum role = UserRoleEnum.USER;
            User testUser = new User(username, password, role);
            UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
            mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
        }

        // 로그인 한거처럼 하기위해서
        private void mockAdminSetup() {
            // Mock 테스트 유져 생성
            String username = "ADMIN";
            String password = "hope!@#";
            UserRoleEnum role = UserRoleEnum.ADMIN;
            User testUser = new User(username, password, role);
            UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
            mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
        }
        

        @Test
        @Order(1)
        @DisplayName("일반 회원 등록")
        void test1() throws JsonProcessingException {
            // given
            SpicyIntegrationTest.UserDto UserRequest = SpicyIntegrationTest.UserDto.builder()
                    .id(null)
                    .username("Customer")
                    .password("asWDW!")
                    .admin(false)
                    .adminToken("")
                    .build();

            String requestBody = mapper.writeValueAsString(UserRequest);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            // when
            ResponseEntity<SpicyIntegrationTest.UserDto> response = restTemplate.postForEntity(
                    "/user/signup",
                    request,
                    SpicyIntegrationTest.UserDto.class);

            // then
            assertEquals(HttpStatus.OK, response.getStatusCode());

            SpicyIntegrationTest.UserDto UserResponse = response.getBody();
            assertNotNull(UserResponse);
            assertTrue(UserResponse.getId() > 0);
            assertEquals(UserRequest.getUsername(), UserResponse.getUsername());
            assertEquals(UserRequest.getPassword(), UserResponse.getPassword());
            assertEquals("USER", UserResponse.getRole());

        }

        @Test
        @Order(2)
        @DisplayName("관리자 회원가입")
        void test2() throws JsonProcessingException {
            // given
            SpicyIntegrationTest.UserDto UserRequest = SpicyIntegrationTest.UserDto.builder()
                    .id(null)
                    .username("Admin")
                    .password("asWDW!")
                    .admin(true)
                    .adminToken("AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC")
                    .build();

            String requestBody = mapper.writeValueAsString(UserRequest);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            // when
            ResponseEntity<SpicyIntegrationTest.UserDto> response = restTemplate.postForEntity(
                    "/user/signup",
                    request,
                    SpicyIntegrationTest.UserDto.class);

            // then
            assertEquals(HttpStatus.OK, response.getStatusCode());

            SpicyIntegrationTest.UserDto UserResponse = response.getBody();
            assertNotNull(UserResponse);
            assertTrue(UserResponse.getId() > 0);
            assertEquals(UserRequest.getUsername(), UserResponse.getUsername());
            assertEquals(UserRequest.getPassword(), UserResponse.getPassword());
            assertEquals("ADMIN", UserResponse.getRole());

        }

        @Test
        @Order(3)
        @DisplayName("관리자 권한으로 음식점1 등록")
        void test3() throws Exception {
            // given
            this.mockAdminSetup();
             SpicyIntegrationTest.SpicyRestaurantDto restaurantRequest =  SpicyIntegrationTest.SpicyRestaurantDto.builder()
                    .id(null)
                    .name("A")
                    .minOrderPrice(1_000)
                    .deliveryFee(0)
                    .x(2)
                    .y(5)
                    .build();

            String requestBody = mapper.writeValueAsString(restaurantRequest);


            mvc.perform(post("/spicy/restaurant/register")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .principal(mockPrincipal)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        @Order(4)
        @DisplayName("관리자 권한으로 음식점2 등록")
        void test4() throws Exception {
            // given
            this.mockAdminSetup();
            SpicyIntegrationTest.SpicyRestaurantDto restaurantRequest =  SpicyIntegrationTest.SpicyRestaurantDto.builder()
                    .id(null)
                    .name("B")
                    .minOrderPrice(100_000)
                    .deliveryFee(10_000)
                    .x(3)
                    .y(4)
                    .build();

            String requestBody = mapper.writeValueAsString(restaurantRequest);


            mvc.perform(post("/spicy/restaurant/register")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .principal(mockPrincipal)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        @Order(5)
        @DisplayName("관리자 권한으로 음식점3 등록")
        void test5() throws Exception {
            // given
            this.mockAdminSetup();
            SpicyIntegrationTest.SpicyRestaurantDto restaurantRequest =  SpicyIntegrationTest.SpicyRestaurantDto.builder()
                    .id(null)
                    .name("C")
                    .minOrderPrice(100000)
                    .deliveryFee(10000)
                    .x(5)
                    .y(5)
                    .build();

            String requestBody = mapper.writeValueAsString(restaurantRequest);


            mvc.perform(post("/spicy/restaurant/register")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .principal(mockPrincipal)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        @Order(6)
        @DisplayName("관리자 권한으로 음식점4 등록")
        void test6() throws Exception {
            // given
            this.mockAdminSetup();
            SpicyIntegrationTest.SpicyRestaurantDto restaurantRequest =  SpicyIntegrationTest.SpicyRestaurantDto.builder()
                    .id(null)
                    .name("D")
                    .minOrderPrice(100000)
                    .deliveryFee(10000)
                    .x(2)
                    .y(0)
                    .build();

            String requestBody = mapper.writeValueAsString(restaurantRequest);


            mvc.perform(post("/spicy/restaurant/register")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .principal(mockPrincipal)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        @Order(7)
        @DisplayName("관리자 권한으로 음식점5 등록")
        void test7() throws Exception {
            // given
            this.mockAdminSetup();
            SpicyIntegrationTest.SpicyRestaurantDto restaurantRequest =  SpicyIntegrationTest.SpicyRestaurantDto.builder()
                    .id(null)
                    .name("E")
                    .minOrderPrice(100000)
                    .deliveryFee(10000)
                    .x(4)
                    .y(0)
                    .build();

            String requestBody = mapper.writeValueAsString(restaurantRequest);


            mvc.perform(post("/spicy/restaurant/register")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .principal(mockPrincipal)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        @Order(8)
        @DisplayName("관리자 권한으로 음식점1에 음식 1개 등록")
        void test8() throws Exception {
            // given
            this.mockAdminSetup();
            List<FoodIntegrationTest.FoodDto> foodsRequest = new ArrayList<>();
            // 음식2 추가
            foodsRequest.add(food1);

            String requestBody = mapper.writeValueAsString(foodsRequest);

            Long restaurantId = 1L;
            mvc.perform(post("/spicy/restaurant/" + restaurantId + "/food/register")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .principal(mockPrincipal)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }




        @Test
        @Order(9)
        @DisplayName("고객으로 로그인 후 등록된 음식점 중 3km 미만 조회")
        void test9() throws Exception {
            // given
            this.mockCustomerSetup();
            MultiValueMap<String, String> locationForm = new LinkedMultiValueMap<>();
            locationForm.add("x",customerX);
            locationForm.add("y",customerY);


            mvc.perform(get("/spicy/restaurants")
                            .params(locationForm)
                            .principal(mockPrincipal)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }













    }

    @Getter
    @Setter
    @Builder
    static class UserDto {
        private Long id;
        private String username;
        private String password;
        private boolean admin = false;
        private String role;
        private String adminToken = "";
    }

    @Getter
    @Setter
    @Builder
    static class SpicyRestaurantDto {
        private Long id;
        private String name;
        private int minOrderPrice;
        private int deliveryFee;
        private int x;
        private int y;
    }

}
