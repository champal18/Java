package modeloDAO;

import java.util.Collections;
import java.util.List;

import javax.persistence.*;
import interfazDAO.IPersonaDAO;
import modelo.Persona;

public class PersonaDAO implements IPersonaDAO
{

	@Override
	public void guardarPersona(Persona p) 
	{
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		try
		{
			em.persist(p);
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		etx.commit();
		em.close();
	}

	@Override
	public void modificarPersona(Persona p) {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		try
		{
			em.merge(p);
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		etx.commit();
		em.close();
		
	}

	@Override
	public void eliminarPersona(Persona p) {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		try
		{
			em.remove(em.find(Persona.class, p.getId()));
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		etx.commit();
		em.close();
	}

	@Override
	public Persona recuperarPersona(long id) {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		Persona p = null;
		try
		{
			p = em.find(Persona.class, id);
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		em.close();
		
		return p;
	}
	
	public Persona recuperarPersona(String nombreUsr)
	{
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		Persona p = null;

		try {
			Query q = em.createQuery("FROM Persona P WHERE P.nombreUser ='"+nombreUsr+"'");
			p = (Persona) q.getSingleResult();			
		} catch (Exception e)
		{
			System.out.println(e);
		}
		
		em.close();
		return p;
	}

	@SuppressWarnings("unchecked")
	public List<Persona> recuperarUsuarios() {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		
		List<Persona> usrs = null;
		
		try {
			Query q = em.createQuery("FROM Persona");
			usrs = Collections.checkedList(q.getResultList(), Persona.class);	
		} catch (Exception e) {
			usrs = null;
			System.out.println("Excepcion!");
		}
		
		em.close();
		return usrs;
	}

}
