/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chat_app;

import javax.swing.JOptionPane;

public class Login {

    public void register(User user) {

        String name = JOptionPane.showInputDialog("Enter your name");
        String surname = JOptionPane.showInputDialog("Enter your surname");

        // Username
        String username = JOptionPane.showInputDialog("Create a username for " + name + " " + surname
                + "\n(must contain an underscore and be no more than 5 characters)");

        if (username == null) {
            JOptionPane.showMessageDialog(null, "Registration cancelled because you did not enter any text.");
            return;
        }

        if (username.length() <= 5 && username.contains("_")) {
            user.setUsername(username);
            JOptionPane.showMessageDialog(null, "Username successfully captured.");
        } else {
            JOptionPane.showMessageDialog(null, "Username is not correctly formatted. Please "
                    + "make sure your username contains an underscore and is no more than "
                    + "5 characters.");
            return;
        }

        // Password
        String password = JOptionPane.showInputDialog("Create a password for "
                + name + " " + surname
                + "\nPassword must contain 8 or more characters, "
                + "a capital letter, a number, and a special character.");

        if (password != null &&
                password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[^a-zA-Z0-9].*")) {

            user.setPassword(password);
            JOptionPane.showMessageDialog(null, "Password successfully captured.");
        } else {
            JOptionPane.showMessageDialog(null, "Password is not formatted correctly. "
                    + "It must contain at least 8 characters, a capital letter, "
                    + "a number, and a special character.");
            return;
        }

        // Phone number
        String phoneNumber = JOptionPane.showInputDialog("Enter your phone number (include country code, e.g. +27...)");

        if (phoneNumber != null &&
                phoneNumber.startsWith("+") &&
                phoneNumber.length() <= 14 &&
                phoneNumber.substring(1).matches("\\d{1,4}\\d{1,10}")) {

            user.setPhoneNumber(phoneNumber);
            JOptionPane.showMessageDialog(null, "Phone successfully captured.");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid phone number. Must start with a country "
                    + "code and be no more than 10 digits long.");
            return;
        }

        // Login (moved outside the else block)
        String loginUsername = JOptionPane.showInputDialog("Enter your username to login");
        String loginPassword = JOptionPane.showInputDialog("Enter your password to login");

        if (loginUsername != null && loginPassword != null &&
                loginUsername.equals(user.getUsername()) &&
                loginPassword.equals(user.getPassword())) {

            JOptionPane.showMessageDialog(null, "Welcome back " + name + " " + surname
                    + ". It's great to see you again!");
        } else {
            JOptionPane.showMessageDialog(null, "Username or password is incorrect, please try again");
        }
    }
}