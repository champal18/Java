package modelo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DataGridView implements Serializable {
    
    private List<Ruta> allRutas;
   
    public List<Ruta> getCars() {
        return allRutas;
    }
 
}
