package com.jukka.vulnweb.persistence.model;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@SqlResultSetMapping(
        name = "UserMapping",
        entities = @EntityResult(
                entityClass = User.class,
                fields = {
                    @FieldResult(name = "id", column = "id"),
                    @FieldResult(name = "username", column = "username"),
                    @FieldResult(name = "role", column = "role"),
                    @FieldResult(name = "password", column = "password")}))

@Entity
public class User
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String role;
    private String password;

    protected User()
    {}

    public User(String name, String role, String password)
    {
        super();
        this.id = Long.valueOf(Math.abs(new Random().nextLong()));
        this.username = name;
        this.role = role;
        this.password = password;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return username;
    }

    public void setName(String name)
    {
        this.username = name;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }
    
    public String getPassword()
    {
    	return password;
    }
    
    public void setPassword(String password)
    {
    	this.password = password;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        User other = (User) obj;

        if (username == null)
        {
            if (other.username != null)
                return false;
        }
        else if (!username.equals(other.username))
            return false;
            
        if (id != other.id)
            return false;
            
        if (role == null)
        {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;

        if (password == null)
        {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
            
        return true;
    }

    @Override
    public String toString()
    {
        return "User [id=" + id + ", name=" + username + ", role=" + role + ", password=" + password + "]";
    }
}
