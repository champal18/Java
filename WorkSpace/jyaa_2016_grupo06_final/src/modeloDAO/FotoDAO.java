package modeloDAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import interfazDAO.IFotoDAO;
import modelo.Foto;

public class FotoDAO implements IFotoDAO
{

	@Override
	public void guardarFoto(Foto f)
	{
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		try
		{
			em.persist(f);
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		etx.commit();
		em.close();
	}

	@Override
	public void modificarFoto(Foto f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarFoto(Foto f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Foto> recuperarFotos(long idRuta) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Foto recuperarFoto() 
	{
		// TODO Auto-generated method stub
		SingletonEMF single = SingletonEMF.getIns();
		EntityManagerFactory emf = single.getEMF();
		EntityManager em = emf.createEntityManager();
		Foto f = null;
		try
		{
			long l = 1;
			f = em.find(Foto.class, l);
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		em.close();
		
		return f;
	}

}