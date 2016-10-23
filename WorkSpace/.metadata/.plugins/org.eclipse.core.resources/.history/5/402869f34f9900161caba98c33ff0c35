package modeloDAO;

import java.util.Collections;
import java.util.List;

import javax.persistence.*;
import interfazDAO.IRutaDAO;
import modelo.Privacidad;
import modelo.Ruta;
import rest.Punto;

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
			etx.commit();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
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
	
	@SuppressWarnings("unchecked")
	public List<Ruta> recuperarRutasActividad(long idActividad) {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		
		List<Ruta> rutas = null;
		
		try {
			Query q = em.createQuery("FROM Ruta WHERE actividad_id = '"+idActividad+"'");
			
			if(!q.getResultList().isEmpty())
				rutas = Collections.checkedList(q.getResultList(), Ruta.class);	
		} catch (Exception e) {
			rutas = null;
			System.out.println("Excepcion!");
		}
		
		em.close();
		return rutas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Ruta> recuperarAllRutasPublicas() {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		
		List<Ruta> rutas = null;
		
		try {
			Query q = em.createQuery("FROM Ruta WHERE privacidad = '"+Privacidad.Publico+"'");
			
			if(!q.getResultList().isEmpty())
				rutas = Collections.checkedList(q.getResultList(), Ruta.class);	
		} catch (Exception e) {
			rutas = null;
			System.out.println("Excepcion!");
		}
		
		em.close();
		return rutas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Ruta> recuperarAllRutas() {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		
		List<Ruta> rutas = null;
		
		try {
			Query q = em.createQuery("FROM Ruta");
			
			if(!q.getResultList().isEmpty())
				rutas = Collections.checkedList(q.getResultList(), Ruta.class);	
		} catch (Exception e) {
			rutas = null;
			System.out.println("Excepcion!");
		}
		
		em.close();
		return rutas;
	}

}
