package modeloDAO;

import java.util.Collections;
import java.util.List;

import javax.persistence.*;
import modelo.RutaRealizada;

public class RutaRealizadaDAO
{
	public void guardarRuta(RutaRealizada rutaR) {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		try
		{
			em.persist(rutaR);
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		etx.commit();
		em.close();
	}

	public void modificarRuta(RutaRealizada rutaR) {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		try
		{
			em.merge(rutaR);
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		etx.commit();
		em.close();
	}

	public void eliminarRuta(RutaRealizada rutaR) {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		try
		{
			em.remove(em.find(RutaRealizada.class, rutaR.getId()));
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		etx.commit();
		em.close();
	}

	public RutaRealizada recuperarRutaRealizada(long id) {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		RutaRealizada rutaR = null;
		try
		{
			rutaR = em.find(RutaRealizada.class, id);
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		em.close();
		
		return rutaR;
	}
	
	@SuppressWarnings("unchecked")
	public List<RutaRealizada> fueRealizada(long idOwner) {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		
		List<RutaRealizada> rutasR = null;
		
		try {
			Query q = em.createQuery("FROM RutaRealizada WHERE owner_id = '"+idOwner+"'"); 
			if(!q.getResultList().isEmpty())
				rutasR = Collections.checkedList(q.getResultList(), RutaRealizada.class);
		} catch (Exception e) {
			System.out.println("Excepcion porque la Consulta esta vacia!");
		}
		
		em.close();
		return rutasR;
	}

}
