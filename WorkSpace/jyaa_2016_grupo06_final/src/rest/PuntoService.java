package rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import modeloDAO.PuntoDao;

public class PuntoService 
{
	PuntoDao puntoDao;
	
	public PuntoService()
	{
		puntoDao = PuntoDao.instance;
	}
	
	public void createPunto(Punto p)
	{
		puntoDao.getPuntos().put(p.getIndice(), p);
	}
	
	public Map<Long, Punto> getPuntos()
	{
		return puntoDao.getPuntos();
	}
	
	public List<Punto> getPuntoAsList()
	{
		List<Punto> puntoList = new ArrayList<Punto>();
		puntoList.addAll(puntoDao.getPuntos().values());
		return puntoList;
	}
	
	public int getPuntosCount()
	{
		return puntoDao.getPuntos().size();
	}
	
	public void deletePunto(Long id) {
		puntoDao.getPuntos().remove(id);
	}
	
	public void deleteAllPuntos() {
		puntoDao.getPuntos().clear();
	}
	

}
