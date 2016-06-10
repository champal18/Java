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
		em.persist(act);
		etx.commit();
		em.close();
	}

	@Override
	public void modificarActividad(Actividad act) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarActividad(Actividad act) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Actividad recuperarActividad(long id) 
	{
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		Actividad a = em.find(Actividad.class, id);
		em.close();
		
		return a;
		// TODO Auto-generated method stub
		
		
	}

}