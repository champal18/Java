package modeloDAO;

import java.util.Collections;
import java.util.List;

import javax.persistence.*;
import interfazDAO.IRutaDAO;
import modelo.Persona;
import modelo.Ruta;

public class RutaDAO implements IRutaDAO
{

	@Override
	public void guardarRuta(Ruta ruta) {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		try
		{
			em.persist(ruta);
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		etx.commit();
		em.close();
	}

	@Override
	public void modificarRuta(Ruta ruta) {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		try
		{
			em.merge(ruta);
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		etx.commit();
		em.close();
	}

	@Override
	public void eliminarRuta(Ruta ruta) {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		try
		{
			em.remove(em.find(Ruta.class, ruta.getId()));
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		etx.commit();
		em.close();
	}

	@Override
	public Ruta recuperarRuta(long id) {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		Ruta r = null;
		try
		{
			r = em.find(Ruta.class, id);
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		em.close();
		
		return r;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ruta> recuperarRutasUsuario(long id) {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		
		List<Ruta> rutas = null;
		
		try {
			Query q = em.createQuery("FROM Ruta WHERE owner_id = '"+id+"'");
			rutas = Collections.checkedList(q.getResultList(), Ruta.class);	
		} catch (Exception e) {
			rutas = null;
			System.out.println("Excepcion!");
		}
		
		em.close();
		return rutas;
	}
	
	

}