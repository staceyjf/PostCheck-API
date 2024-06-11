package com.auspost.postcode;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.auspost.postcode.PostCode.CreatePostCodeDTO;
import com.auspost.postcode.PostCode.PostCode;
import com.auspost.postcode.PostCode.PostCodeController;
import com.auspost.postcode.PostCode.PostCodeService;
import com.auspost.postcode.User.SignInDTO;
import com.auspost.postcode.User.USERROLE;
import com.auspost.postcode.User.User;
import com.auspost.postcode.config.auth.TokenProvider;
import com.auspost.postcode.exceptions.ServiceValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import static org.mockito.BDDMockito.given;

// intergration tests for the create post code functionality in the PostCodeController (requires the service layer as a bean)
@WebMvcTest(PostCodeController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test") // ensures the JWT token variable is loaded from the test-properties
@AutoConfigureMockMvc
public class CreatePostCodeTests {
        @Autowired
        private MockMvc mockMVC;

        @MockBean
        private PostCodeService postCodeService;

        @MockBean
        private TokenProvider tokenService;

        @MockBean
        private AuthenticationManager authManager;

        private PostCode postcode;

        private ObjectMapper objectMapper = new ObjectMapper();

        // errors will bubble up in the test runner
        @BeforeEach
        public void setupService() throws ServiceValidationException {
                postcode = new PostCode();
                postcode.setId(Long.valueOf(1));
                postcode.setPostcode("2222");

                when(this.postCodeService.createPostCode(any(CreatePostCodeDTO.class))).thenReturn(postcode);
        }

        // @Test
        // @WithMockUser(roles = { "ADMIN", "USER" })
        // void shouldCreateAndReturnPostCode() throws Exception {
        // String mockJsonRes = "{\"postcode\":\"2222\"}";

        // // Mock a JWT token for testing
        // String jwtToken = "Bearer tester-token";

        // mockMVC.perform(post("/api/v1/postcodes")
        // .header("Authorization", jwtToken) // include the mocked token in the request
        // .contentType(MediaType.APPLICATION_JSON)
        // .content(mockJsonRes))
        // .andDo(print())
        // .andExpect(status().isCreated())
        // .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
        // .andExpect(MockMvcResultMatchers.jsonPath("$.postcode").value("2222"))
        // .andExpect(MockMvcResultMatchers.jsonPath("$.associatedSuburbs").isEmpty());
        // }
        @Test
        void shouldCreateAndReturnPostCode() throws Exception {
                given(postCodeService.createPostCode(ArgumentMatchers.any()))
                                .willAnswer(invocation -> invocation.getArgument(0));

                CreatePostCodeDTO createPostCodeDTO = new CreatePostCodeDTO();
                createPostCodeDTO.setPostcode("2222");

                // predefined authentication object eg mocked ADMIN user
                User user = new User();
                user.setLogin("admin");
                user.setPassword("admin");
                user.setRole(USERROLE.ADMIN);

                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

                Authentication authUser = new UsernamePasswordAuthenticationToken(user, null, authorities);
                when(authManager.authenticate(any())).thenReturn(authUser); // return mocked up ADMIN user
                when(tokenService.generateAccessToken(any(User.class))).thenReturn("fakeToken"); // return fake token

                // System.out.println("this is authUser: " + authUser);
                // System.out.println("this is authUser principal: " + authUser.getPrincipal());
                // System.out.println("this is authUser princpla casted to user: " + (User)
                // authUser.getPrincipal());
                // String mockJwtToken = tokenService.generateAccessToken((User)
                // authUser.getPrincipal());

                // System.out.println("this is the token printout: " + mockJwtToken);

                ResultActions response = mockMVC.perform(post("/api/v1/postcodes")
                                .header("Authorization", "Bearer fakeToken")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createPostCodeDTO)));

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
