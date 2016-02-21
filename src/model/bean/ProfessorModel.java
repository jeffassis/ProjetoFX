package model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jeff-
 */
public class ProfessorModel {

    private IntegerProperty codigo;
    private StringProperty nome;
    private StringProperty endereco;
    private StringProperty nascimento;
    private StringProperty telefone;
    private StringProperty cep;

    public ProfessorModel() {
        this.codigo = new SimpleIntegerProperty();
        this.nome = new SimpleStringProperty();
        this.endereco = new SimpleStringProperty();
        this.nascimento = new SimpleStringProperty();
        this.telefone = new SimpleStringProperty();
        this.cep = new SimpleStringProperty();
    }

    public ProfessorModel(int codigo, String nome, String endereco, String nascimento, String telefone, String cep) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.nome = new SimpleStringProperty(nome);
        this.endereco = new SimpleStringProperty(endereco);
        this.nascimento = new SimpleStringProperty(nascimento);
        this.telefone = new SimpleStringProperty(telefone);
        this.cep = new SimpleStringProperty(cep);
    }

    public IntegerProperty getCodigoProperty() {
        return this.codigo;
    }

    public int getCodigo() {
        return codigo.get();
    }

    public void setCodigo(int codigo) {
        this.codigo.set(codigo);
    }

    public StringProperty getNomeProperty() {
        return this.nome;
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public StringProperty getEnderecoProperty() {
        return this.endereco;
    }

    public String getEndereco() {
        return endereco.get();
    }

    public void setEndereco(String endereco) {
        this.endereco.set(endereco);
    }

    public StringProperty getNascimentoProperty() {
        return this.nascimento;
    }

    public String getNascimento() {
        return nascimento.get();
    }

    public void setNascimento(String nascimento) {
        this.nascimento.set(nascimento);
    }

    public StringProperty getTelefoneProperty() {
        return this.telefone;
    }

    public String getTelefone() {
        return telefone.get();
    }

    public void setTelefone(String telefone) {
        this.telefone.set(telefone);
    }

    public StringProperty getCepProperty() {
        return this.cep;
    }

    public String getCep() {
        return cep.get();
    }

    public void setCep(String cep) {
        this.cep.set(cep);
    }
}
