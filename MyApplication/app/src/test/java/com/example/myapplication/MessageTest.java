package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;
public class MessageTest {
    @Test
    public void messageTest() {
        User user1 = new User("Alice", "password123");
        User user2 = new User("Bob", "password456");

        // Users update their profiles
        user1.updateProfile("AliceWonder", "alice@example.com", "alice_pic.jpg");
        user2.updateProfile("BobBuilder", "bob@example.com", "bob_pic.jpg");

        // Users send messages to each other
        user1.sendMessage(user2.getUserId(), "Hello Bob!");
        user2.sendMessage(user1.getUserId(), "Hi Alice, how are you?");

        // Print user details to the console
        user1.printUser();
        user2.printUser();
    }
}
