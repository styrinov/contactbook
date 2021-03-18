package tyrinov.sergey.contactbook.services;

import tyrinov.sergey.contactbook.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    List<Contact> getAllContacts();
    Contact addContact(Contact contact);
    void deleteContact(Contact contact);
    Optional<Contact> getContactById(Long id);
    Contact editContact(Contact contact);
}
