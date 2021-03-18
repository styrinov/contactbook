package tyrinov.sergey.contactbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tyrinov.sergey.contactbook.model.Contact;
import tyrinov.sergey.contactbook.services.ContactService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


@RestController
public class ContactController {
    private static final Logger log = LoggerFactory.getLogger(ContactController.class);
    private final ContactService contactServiceImpl;

    @Autowired
    public ContactController(ContactService contactServiceImpl) {
        this.contactServiceImpl = contactServiceImpl;
    }

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<?> getContacts() {
        try {
            List<Contact> contacts = contactServiceImpl.getAllContacts();
            if (contacts.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(contacts, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/contact", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> addContact( @RequestBody Contact contact,
                                                           HttpServletRequest request,
                                                           Locale locale) {
        try{
            Contact createdContact = contactServiceImpl.addContact(contact);
            return new ResponseEntity<>(createdContact, HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/contact/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteContact( @PathVariable("id") Long id) {
        try {
            Optional<Contact>  optionalContact =  contactServiceImpl.getContactById(id);
            if (optionalContact.isPresent()){
                Contact con = optionalContact.get();
                contactServiceImpl.deleteContact(con);
                return new ResponseEntity<>(con.getId(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/contact/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> editContact( @PathVariable("id") Long id, @RequestBody Contact contact) {
        try {
            Optional<Contact>  optionalContact =  contactServiceImpl.getContactById(id);
            if (optionalContact.isPresent()){
                Contact selectedContact = optionalContact.get();
                selectedContact.setFirstName(contact.getFirstName());
                selectedContact.setLastName(contact.getLastName());
                selectedContact.setPhone(contact.getPhone());
                Contact editedContact = contactServiceImpl.editContact(contact);
                return new ResponseEntity<>(editedContact, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
