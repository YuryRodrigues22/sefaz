
package br.com.sefaz.dao;

import br.com.sefaz.util.exception.ErroSistema;
import java.util.List;

public interface CrudDAO<E> {
    
    public void salvar(E entidade) throws ErroSistema;
    public void deletar(E entidade) throws ErroSistema;
    public List<E> consultar() throws ErroSistema;
    
    
}
