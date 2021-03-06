package modeloDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import interfazDAO.IActividadDAO;
import modelo.Actividad;

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

}
