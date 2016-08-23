package modeloDAO;

import java.util.*;

import javax.persistence.*;
import interfazDAO.IPuntoDAO;
import modelo.Ruta;
import rest.Punto;

public enum PuntoDao implements IPuntoDAO
{ 
	instance;

	private Map<Long, Punto> puntos = new HashMap<Long,Punto>();
	
	public Map<Long, Punto> getPuntos()
	{
		return puntos;
	}

	@Override
	public void guardarPuntos(Ruta ruta) 
	{
		// TODO Auto-generated method stub
		
		int i;
		for(i=0;i<this.puntos.size();i++)
		{
			
			SingletonEMF single = SingletonEMF.getIns();
			EntityManagerFactory emf = single.getEMF();
			EntityManager em = emf.createEntityManager();
			EntityTransaction etx = em.getTransaction();
			etx.begin();
			try
			{
				long j = i;
				Punto p = puntos.get(j+1);
				p.setRuta(ruta);
				em.persist(p);
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
			etx.commit();
			em.close();
		}
		
	}

	@Override
	public void modificarPunto(Punto p)
	{
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
	public void eliminarPunto(Punto p)
	{
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		try
		{
			em.remove(em.find(Punto.class, p.getId()));
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		etx.commit();
		em.close();
		
	}

	@Override
	public Punto recuperarPunto(long id)
	{
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		Punto p = null;
		try
		{
			p = em.find(Punto.class, id);
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		em.close();
		
		return p;
	}
	
	@SuppressWarnings("unchecked")
	public List<Punto> recuperarPuntos(long idRuta)
	{
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		
		List<Punto> puntos = null;
		
		try {
			Query q = em.createQuery("FROM Punto where id_ruta='"+idRuta+"'");
			puntos = Collections.checkedList(q.getResultList(), Punto.class);	
		} catch (Exception e) {
			puntos = null;
			System.out.println("Excepcion!");
		}
		
		em.close();
		return puntos;
	}
	
	
}