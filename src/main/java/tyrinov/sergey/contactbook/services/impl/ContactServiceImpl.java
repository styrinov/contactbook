package tyrinov.sergey.contactbook.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tyrinov.sergey.contactbook.model.Contact;
import tyrinov.sergey.contactbook.repository.ContactRepository;
import tyrinov.sergey.contactbook.services.ContactService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> getAllContacts(){
        return contactRepository.findAll();
    }

    @Override
    public Contact addContact(Contact contact){
        Contact createdContact = contactRepository.save(contact);
        return createdContact;
    }

    @Override
    public Contact editContact(Contact contact){
        Contact editedContact = contactRepository.save(contact);
        return editedContact;
    }

    @Override
    public void deleteContact(Contact contact){
        contactRepository.delete(contact);
    }

    @Override
    public Optional<Contact> getContactById(Long id){
        return contactRepository.findById(id);
    }
}
