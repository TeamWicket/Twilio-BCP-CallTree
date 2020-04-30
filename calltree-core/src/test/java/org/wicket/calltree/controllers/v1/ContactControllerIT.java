package org.wicket.calltree.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.services.ContactService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerIT {

    @Autowired
    ContactService contactService;

    @Autowired
    MockMvc mvc;

    ObjectWriter writer;
    ObjectMapper mapper;

    private final static String API_ROOT = "/api/v1/contacts";

    @BeforeEach
    void setUp() {
        System.out.println("Before each test: " + contactService.getAllContacts(null, null, null, null).size());
        mapper = new ObjectMapper().registerModule(new KotlinModule());
        writer = mapper.writerWithDefaultPrettyPrinter();
    }

    @AfterEach
    void tearDown() {
        System.out.println("AFTER each test: " + contactService.getAllContacts(null, null, null, null).size());
    }

    @Test
    void fetchContact_ReturnsSuccess() throws Exception {
        System.out.println("FETCH one");
        mvc.perform(
                get(API_ROOT.concat("/2"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", equalTo("Richard")))
                .andExpect(jsonPath("$.lastName", equalTo("Helm")))
                .andExpect(jsonPath("$.role", equalTo(Role.MANAGER.name())))
                .andExpect(jsonPath("$.callingOption", hasSize(1)));
    }

    @Test
    void fetchAllContacts_ReturnsListOfContacts() throws Exception {
        System.out.println("FETCH all");
        mvc.perform(
                get(API_ROOT.concat("/all"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void fetchAllContacts_ReturnsListOrderedByLastName_ASC() throws Exception {
        System.out.println("FETCH all orderBy ASC");
        MvcResult mvcResult = mvc.perform(
                get(API_ROOT.concat("/all"))
                        .param("orderBy", "ASC")
                        .param("orderValue", "lastName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andReturn();

        String s = mvcResult.getResponse().getContentAsString();
        val list = List.of(mapper.readValue(s, ContactDto[].class));

        assertEquals("Gamma", list.get(0).getLastName());
    }

    @Test
    void fetchAllContact_ReturnsListOrderedByLastName_DESC() throws Exception {
        System.out.println("FETCH all orderBy DESC");
        MvcResult mvcResult = mvc.perform(
                get(API_ROOT.concat("/all"))
                        .param("orderDirection", "DESC")
                        .param("orderByValue", "lastName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andReturn();

        String s = mvcResult.getResponse().getContentAsString();
        val list = List.of(mapper.readValue(s, ContactDto[].class));

        assertEquals("Vlissides", list.get(0).getLastName());
    }

    @Test
    void fetchAllContact_ReturnsListOrderedByFirstName_DESC() throws Exception {
        System.out.println("FETCH all orderBy DESC");
        MvcResult mvcResult = mvc.perform(
                get(API_ROOT.concat("/all"))
                        .param("orderDirection", "DESC")
                        .param("orderByValue", "firstName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andReturn();

        String s = mvcResult.getResponse().getContentAsString();
        val list = List.of(mapper.readValue(s, ContactDto[].class));

        assertEquals("Richard", list.get(0).getFirstName());
    }

    @Test
    void fetchAllContact_WithPagingAndSortingDesc_ReturnsOrderedList() throws Exception {
        System.out.println("FETCH all orderBy DESC, requesting page 1 of size 2");
        MvcResult mvcResult = mvc.perform(
                get(API_ROOT.concat("/all"))
                        .param("orderDirection", "DESC")
                        .param("orderByValue", "lastName")
                        .param("page", "1")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andReturn();

        String s = mvcResult.getResponse().getContentAsString();
        val list = List.of(mapper.readValue(s, ContactDto[].class));

        assertEquals("Gamma", list.get(1).getLastName());
    }

    @Test
    void saveContact_WithValidProperties_ReturnsSuccess_StatusIsCreated() throws Exception {
        System.out.println("SAVE OK");
        assertThat(contactService.getAllContacts(null, null, null, null)).hasSize(3);

        ContactDto contact = new ContactDto();
        contact.setFirstName("Alessandro");
        contact.setLastName("Arosio");
        contact.setRole(Role.REPORTER);
        contact.setPointOfContactId(1L);
        contact.setPhoneNumber("+1234");

        String body = writer.writeValueAsString(contact);

        mvc.perform(
                post(API_ROOT)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()));

        assertThat(contactService.getAllContacts(null, null, null, null)).hasSize(4);
    }

    @Test
    void saveContact_WithInvalidProperties_ReturnsFail_WithStatus400() throws Exception {
        System.out.println("SAVE fail");

        assertThat(contactService.getAllContacts(null, null, null, null)).hasSize(4);

        ContactDto contact = new ContactDto();
        contact.setFirstName("Alessandro");
        contact.setLastName("Arosio");
        contact.setRole(Role.REPORTER);
        contact.setPhoneNumber("+1234");

        String body = writer.writeValueAsString(contact);

        mvc.perform(
                post(API_ROOT)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        assertThat(contactService.getAllContacts(null, null, null, null)).hasSize(4);
    }

    @Test
    void updateContact_ReturnsStatusOk() throws Exception {
        System.out.println("UPDATE");
        assertEquals(4, contactService.getAllContacts(null, null, null, null).size());
        ContactDto contact = contactService.getContact(3L);

        assertEquals("Ralph", contact.getFirstName());

        String newFirstName = "Changing name";
        contact.setFirstName(newFirstName);

        String body = writer.writeValueAsString(contact);

        mvc.perform(
                put(API_ROOT)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(contact.getId().intValue())))
                .andExpect(jsonPath("$.firstName", equalTo(newFirstName)));

        assertEquals(4, contactService.getAllContacts(null, null, null, null).size());
    }

    @Test
    void removeContact_ReturnsStatus_NoContent() throws Exception {
        System.out.println("REMOVE");
        assertEquals(4, contactService.getAllContacts(null, null, null, null).size());

        ContactDto contact = contactService.getContact(3L);

        String body = writer.writeValueAsString(contact);

        mvc.perform(
                delete(API_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNoContent());

        assertEquals(3, contactService.getAllContacts(null, null, null, null).size());
    }

    @Test
    void testFetchContactsOfOneRole_ReturnsListOfSameRole() throws Exception {
        System.out.println("FETCH BY ROLE");

        mvc.perform(
                get(API_ROOT + "/role/manager")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testFetchTreeUntilRole_ReturnsListOfContacts_UntilSelectedRole() throws Exception {
        System.out.println("FETCH tree");

        mvc.perform(
                get(API_ROOT + "/tree/leader")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].role", equalTo(Role.MANAGER.toString())))
                .andExpect(jsonPath("$[1].role", equalTo(Role.LEADER.toString())));
    }
}