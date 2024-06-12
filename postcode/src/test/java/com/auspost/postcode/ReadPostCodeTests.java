package com.auspost.postcode;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.auspost.postcode.PostCode.CreatePostCodeDTO;
import com.auspost.postcode.PostCode.PostCode;
import com.auspost.postcode.PostCode.PostCodeController;
import com.auspost.postcode.PostCode.PostCodeService;
import com.auspost.postcode.exceptions.ServiceValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PostCodeController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ReadPostCodeTests {
    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private PostCodeService postCodeService;

    private PostCode postcode;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setupService() throws ServiceValidationException {
        postcode = new PostCode();
        postcode.setId(Long.valueOf(1));
        postcode.setPostcode("2222");

        when(this.postCodeService.createPostCode(any(CreatePostCodeDTO.class))).thenReturn(postcode);
    }

    @Test
    void shouldReturnAllPostCodes() throws Exception {
        List<PostCode> postCodes = new ArrayList<>();
        postCodes.add(postcode); // add the mocked postcode to a list
        given(postCodeService.findAllPostCodes()).willReturn(postCodes); // mock the function to return our mocked
                                                                         // result

        ResultActions response = mockMVC.perform(get("/api/v1/postcodes")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
