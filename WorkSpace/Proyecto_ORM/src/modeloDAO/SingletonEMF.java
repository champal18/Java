package modeloDAO;

import javax.persistence.*;

public class SingletonEMF
{
	private static SingletonEMF instance = new SingletonEMF();
	private EntityManagerFactory emf;
	
	private SingletonEMF()
	{
		this.emf = Persistence.createEntityManagerFactory("miUP");
	}
	
	public static SingletonEMF getIns()
	{
		return instance;
	}
	
	public EntityManagerFactory getEMF()
	{
		return this.emf;
	}

}
