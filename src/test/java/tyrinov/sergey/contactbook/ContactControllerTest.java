package tyrinov.sergey.contactbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tyrinov.sergey.contactbook.model.Contact;
import tyrinov.sergey.contactbook.services.impl.ContactServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ContactbookApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactServiceImpl service;

    @Test
    public void getContacts_shouldUseGetAllContacts() throws Exception {
        Contact contactOne = new Contact();
        contactOne.setId(1L);
        contactOne.setFirstName("Сергей");
        contactOne.setLastName("Тыринов");
        contactOne.setPhone("0635971407");

        Contact contactTwo = new Contact();
        contactTwo.setId(2L);
        contactTwo.setFirstName("Иван");
        contactTwo.setLastName("Иванов");
        contactTwo.setPhone("0635971433");

        List<Contact> contacts = new ArrayList<>();
        contacts.add(contactOne);
        contacts.add(contactTwo);

        when(service.getAllContacts()).thenReturn(contacts);

        mockMvc.perform(get("/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Сергей")))
                .andExpect(jsonPath("$[0].lastName", is("Тыринов")))
                .andExpect(jsonPath("$[0].phone", is("0635971407")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Иван")))
                .andExpect(jsonPath("$[1].lastName", is("Иванов")))
                .andExpect(jsonPath("$[1].phone", is("0635971433")));

        verify(service, times(1)).getAllContacts();
    }

    @Test
    public void add_contact_book_OK() throws Exception {

        Contact newContact = new Contact();
        newContact.setId(1L);
        newContact.setFirstName("Сергей");
        newContact.setLastName("Тыринов");
        newContact.setPhone("0635971407");


        when(service.addContact(any(Contact.class))).thenReturn(newContact);

        mockMvc.perform(post("/contact")
                .content(om.writeValueAsString(newContact))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                /*.andDo(print())*/
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Сергей")))
                .andExpect(jsonPath("$.lastName", is("Тыринов")))
                .andExpect(jsonPath("$.phone", is("0635971407")));

        verify(service, times(1)).addContact(any(Contact.class));

    }

    @Test
    public void edit_contact_book_OK() throws Exception {

        Contact updateContact = new Contact();
        updateContact.setId(1L);
        updateContact.setFirstName("Сергей");
        updateContact.setLastName("Тыринов");
        updateContact.setPhone("0635971407");

        when(service.getContactById(any())).thenReturn(Optional.of(updateContact));
        when(service.editContact(any(Contact.class))).thenReturn(updateContact);


        mockMvc.perform(put("/contact/1")
                .content(om.writeValueAsString(updateContact))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Сергей")))
                .andExpect(jsonPath("$.lastName", is("Тыринов")))
                .andExpect(jsonPath("$.phone", is("0635971407")));
    }

    @Test
    public void delete_contact_book_OK() throws Exception {

        Contact deleteContact = new Contact();
        deleteContact.setId(1L);
        deleteContact.setFirstName("Сергей");
        deleteContact.setLastName("Тыринов");
        deleteContact.setPhone("0635971407");

        when(service.getContactById(any())).thenReturn(Optional.of(deleteContact));
        doNothing().when(service).deleteContact(deleteContact);

        mockMvc.perform(delete("/contact/1"))
                /*.andDo(print())*/
                .andExpect(status().isOk());

        verify(service, times(1)).deleteContact(deleteContact);
    }


}
