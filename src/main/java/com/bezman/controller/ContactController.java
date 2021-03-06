package com.bezman.controller;

import com.bezman.Reference.Reference;
import com.bezman.Reference.util.Util;
import com.bezman.annotation.Transactional;
import com.bezman.model.Contact;
import org.hibernate.internal.SessionImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Terence on 4/28/2015.
 */
@Controller
@RequestMapping("/v1/contact")
public class ContactController {

    /**
     * Feedback for users : Puts new contact row in DB and sends email
     *
     * @param session
     * @param name    Name of the user
     * @param email   Email of the user
     * @param text    Text we should see
     * @param type    Type of feedback
     * @return
     */
    @Transactional
    @RequestMapping("/create")
    public ResponseEntity create(SessionImpl session,
                                 @RequestParam("name") String name,
                                 @RequestParam("email") String email,
                                 @RequestParam("text") String text,
                                 @RequestParam("type") String type) {
        if (text.length() <= 1000 && type.length() < 255) {
            Contact contact = new Contact(name, email, type, text);

            String subject = "New " + type + " Inquiry from " + name;
            String message = "Name: " + name + "\nEmail: " + email + "\nText: " + text + "\nType: " + type;

            Util.sendEmail(Reference.getEnvironmentProperty("emailUsername"), Reference.getEnvironmentProperty("emailPassword"), subject, message, "support@devwars.tv");

            session.save(contact);

            return new ResponseEntity(contact, HttpStatus.OK);
        } else {
            return new ResponseEntity("Enquiry is too long", HttpStatus.BAD_REQUEST);
        }
    }
}
