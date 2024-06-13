package com.auspost.postcode;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.auspost.postcode.PostCode.CreatePostCodeDTO;
import com.auspost.postcode.PostCode.PostCode;
import com.auspost.postcode.PostCode.PostCodeRepository;
import com.auspost.postcode.PostCode.PostCodeService;
import com.auspost.postcode.User.USERROLE;
import com.auspost.postcode.User.User;
import com.auspost.postcode.User.UserRepository;
import com.auspost.postcode.config.auth.TokenProvider;
import com.auspost.postcode.exceptions.ServiceValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import static org.mockito.BDDMockito.given;

// intergration tests for the create post code functionality in the PostCodeController (requires the service layer as a bean)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test") // ensures the JWT token variable is loaded from the test-properties
@AutoConfigureMockMvc
public class CreatePostCodeTests {
        @Autowired
        private MockMvc mockMVC;

        @MockBean
        private PostCodeService postCodeService;

        @MockBean
        private UserRepository repo;

        @MockBean
        private TokenProvider tokenService;

        @MockBean
        private AuthenticationManager authManager;

        private ObjectMapper objectMapper = new ObjectMapper();

        private PostCode postcode;

        private User user;

        private String token;

        // errors will bubble up in the test runner
        @BeforeEach
        public void setupService() throws ServiceValidationException {
                postcode = new PostCode();
                postcode.setId(Long.valueOf(1));
                postcode.setPostcode("2222");

                when(this.postCodeService.createPostCode(any(CreatePostCodeDTO.class))).thenReturn(postcode);
        }

        @BeforeEach
        public void setupUser() throws ServiceValidationException {
                // predefined authentication object eg mocked ADMIN user
                user = new User();
                user.setLogin("admin");
                user.setPassword("admin");
                user.setRole(USERROLE.ADMIN);

                when(this.repo.save(any(User.class))).thenReturn(user); // by pass saving to the db when save is called
                                                                        // with a user object

                // authentication our mocked user
                Authentication usernamePassword = new UsernamePasswordAuthenticationToken(user.getLogin(),
                                user.getPassword());
                Authentication authUser = mock(Authentication.class);
                when(authUser.getPrincipal()).thenReturn(user);
                when(authManager.authenticate(usernamePassword)).thenReturn(authUser);

                SecurityContextHolder.getContext().setAuthentication(authUser);

                // mock up a token
                token = mockTokenValidation.createToken(user);
                System.out.println(token);

                when(tokenService.generateAccessToken(any(User.class))).thenReturn("token");

                when(tokenService.validateToken(anyString())).thenReturn("admin"); // return the subject which is our
                                                                                   // return name
        }

        @Test
        void shouldCreateAndReturnPostCode() throws Exception {

                // mock my service's createPostCode
                given(postCodeService.createPostCode(ArgumentMatchers.any()))
                                .willAnswer(invocation -> invocation.getArgument(0));

                ResultActions response = mockMVC.perform(post("/api/v1/postcodes")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postcode)));

                response.andDo(print())
                                .andExpect(status().isCreated())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.postcode").value("2222"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.associatedSuburbs").isEmpty());
        }

        @Test
        void shouldReturn403StatuCodeIfUnAuthorizated() throws Exception {
                // String mockJsonRes = "{\"postcode\":\"2222\"}";

                // mockMVC.perform(post("/api/v1/postcodes")
                // .contentType(MediaType.APPLICATION_JSON)
                // .content(mockJsonRes))
                // .andDo(print())
                // .andExpect(status().isForbidden());

                given(postCodeService.createPostCode(ArgumentMatchers.any()))
                                .willAnswer(invocation -> invocation.getArgument(0));

                CreatePostCodeDTO createPostCodeDTO = new CreatePostCodeDTO();
                createPostCodeDTO.setPostcode("2222");

                ResultActions response = mockMVC.perform(post("/api/v1/postcodes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createPostCodeDTO)));

                response.andExpect(MockMvcResultMatchers.status().isForbidden());

        }
}
