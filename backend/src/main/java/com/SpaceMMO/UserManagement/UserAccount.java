package com.SpaceMMO.UserManagement;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserAccount
{
    public enum UserRole
    {
        USER,
        ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String username;

    public UserRole userRole;

    public UserAccount(String username, UserRole userRole)
    {
        this.username = username;
        this.userRole = userRole;
    }

}
