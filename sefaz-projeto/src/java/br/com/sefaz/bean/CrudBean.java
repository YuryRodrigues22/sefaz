package br.com.sefaz.bean;

import br.com.sefaz.dao.CrudDAO;
import br.com.sefaz.util.exception.ErroSistema;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class CrudBean<E, D extends CrudDAO> {

    private String estadoTela = "consultar";

    private E entidade;
    private List<E> entidades;

    public void novo() {
        entidade = criarNovaEntidade();
        mudarParaInseri();
    }

    public void salvar() {
        try {
            getDao().salvar(entidade);
            entidade = criarNovaEntidade();
            adicionarMensagem("Salvo!", FacesMessage.SEVERITY_INFO);
            mudarParaConsulta();
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(E entidade) {
        this.entidade = entidade;
        mudarParaEdita();
    }

    public void deletar(E entidade) {
        try {
            getDao().deletar(entidade);
            entidades.remove(entidade);
            adicionarMensagem("Deletado!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void consultar() {
        if (isConsulta() == false) {
            mudarParaConsulta();
            return;
        }
        try {
            entidades = getDao().consultar();
            if (entidades == null || entidades.size() < 1) {
                adicionarMensagem("Não exite registros a serem exibidos", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void adicionarMensagem(String mensagem, FacesMessage.Severity tipoErro) {
        FacesMessage fm = new FacesMessage(tipoErro, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public abstract D getDao();

    public abstract E criarNovaEntidade();

    public boolean isInseri() {
        return "inserir".equals(estadoTela);
    }

    public boolean isEdita() {
        return "editar".equals(estadoTela);
    }

    public boolean isConsulta() {
        return "consultar".equals(estadoTela);
    }

    public void mudarParaInseri() {
        estadoTela = "inserir";
    }

    public void mudarParaEdita() {
        estadoTela = "editar";
    }

    public void mudarParaConsulta() {
        estadoTela = "consultar";
    }

    public E getEntidade() {
        return entidade;
    }

    public void setEntidade(E entidade) {
        this.entidade = entidade;
    }

    public List<E> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<E> entidades) {
        this.entidades = entidades;
    }

}
