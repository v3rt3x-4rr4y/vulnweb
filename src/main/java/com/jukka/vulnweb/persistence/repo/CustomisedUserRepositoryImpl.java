package com.jukka.vulnweb.persistence.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jukka.vulnweb.persistence.model.User;

public class CustomisedUserRepositoryImpl implements CustomisedUserRepository
{
    @PersistenceContext
    private EntityManager em;
    
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsersByRole(String userRole) 
	{
		{
			final StringBuffer buf = new StringBuffer("SELECT a.id as id,");
			buf.append("a.username as username,");
			buf.append(" a.role as role, a.password as password from User a ");
			buf.append("where role = '");
			buf.append(userRole);
			buf.append("'");
		    return em.createNativeQuery(buf.toString(), "UserMapping").getResultList();
		}
	}

}
