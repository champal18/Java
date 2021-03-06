package modeloDAO;

import javax.persistence.*;
import interfazDAO.IRutaDAO;
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

}
