package model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jeff-
 */
public class TurmaModel {

    private IntegerProperty codigo;
    private StringProperty descricao;
    private StringProperty horario;

    public TurmaModel() {
        this.codigo = new SimpleIntegerProperty();
        this.descricao = new SimpleStringProperty();
        this.horario = new SimpleStringProperty();
    }

    public TurmaModel(int codigo, String descricao, String horario) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.descricao = new SimpleStringProperty(descricao);
        this.horario = new SimpleStringProperty(horario);
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

    public String getHorario() {
        return horario.get();
    }

    public StringProperty getHorarioProperty() {
        return this.horario;
    }

    public void setHorario(String horario) {
        this.horario.set(horario);
    }

}
