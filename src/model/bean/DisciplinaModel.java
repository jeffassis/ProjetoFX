package model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jassis
 */
public class DisciplinaModel {

    private IntegerProperty codigo;
    private StringProperty descricao;

    public DisciplinaModel() {
        this.codigo = new SimpleIntegerProperty();
        this.descricao = new SimpleStringProperty();
    }

    public DisciplinaModel(int codigo, String descricao) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.descricao = new SimpleStringProperty(descricao);
    }

    public int getCodigo() {
        return codigo.get();
    }

    public IntegerProperty getCodigoProperty() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo.set(codigo);
    }

    public String getDescricao() {
        return descricao.get();
    }

    public StringProperty getDescricaoProperty() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

}
