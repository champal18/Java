package modeloDAO;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import interfazDAO.IActividadDAO;
import modelo.Actividad;
import modelo.Ruta;

public class ActividadDAO implements IActividadDAO
{

	@Override
	public void guardarActividad(Actividad act) 
	{
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		
		try
		{
			em.persist(act);
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		etx.commit();
		em.close();
	}

	@Override
	public void modificarActividad(Actividad act) {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		try
		{
			em.merge(act);
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		etx.commit();
		em.close();
		
	}

	@Override
	public void eliminarActividad(Actividad act) 
	{
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		try
		{
			em.remove(em.find(Actividad.class, act.getId()));
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		etx.commit();
		em.close();
	}

	@Override
	public Actividad recuperarActividad(long id) 
	{
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		Actividad a = null;
		try
		{
			a = em.find(Actividad.class, id);
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		em.close();
		
		return a;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Actividad> recuperarActividades() {
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		
		List<Actividad> acts = null;
		
		try {
			Query q = em.createQuery("FROM Actividad");
//			acts = Collections.checkedList(q.getResultList(), Actividad.class);	
			if(!q.getResultList().isEmpty())
				acts = Collections.checkedList(q.getResultList(), Actividad.class);	
		} catch (Exception e) {
			acts = null;
			System.out.println("Excepcion!");
		}
		
		em.close();
		return acts;
	}

}
