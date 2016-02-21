package model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jeff-
 */
public class AlunoModel {

    private IntegerProperty codigo;
    private StringProperty nome;
    private StringProperty sexo;
    private StringProperty endereco;
    private StringProperty nascimento;
    private StringProperty mae;
    private StringProperty pai;
    private StringProperty serie;
    private StringProperty telefone;
    private StringProperty uf;
    private StringProperty cep;
    private StringProperty ensino;

    public AlunoModel() {
        this.codigo = new SimpleIntegerProperty();
        this.nome = new SimpleStringProperty();
        this.sexo = new SimpleStringProperty();
        this.endereco = new SimpleStringProperty();
        this.nascimento = new SimpleStringProperty();
        this.mae = new SimpleStringProperty();
        this.pai = new SimpleStringProperty();
        this.serie = new SimpleStringProperty();
        this.telefone = new SimpleStringProperty();
        this.uf = new SimpleStringProperty();
        this.cep = new SimpleStringProperty();
        this.ensino = new SimpleStringProperty();
    }

    public AlunoModel(int codigo, String nome, String sexo, String endereco, String nascimento,
            String mae, String pai, String serie, String telefone, String uf, String cep, String ensino) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.nome = new SimpleStringProperty(nome);
        this.sexo = new SimpleStringProperty(sexo);
        this.endereco = new SimpleStringProperty(endereco);
        this.nascimento = new SimpleStringProperty(nascimento);
        this.mae = new SimpleStringProperty(mae);
        this.pai = new SimpleStringProperty(pai);
        this.serie = new SimpleStringProperty(serie);
        this.telefone = new SimpleStringProperty(telefone);
        this.uf = new SimpleStringProperty(uf);
        this.cep = new SimpleStringProperty(cep);
        this.ensino = new SimpleStringProperty(ensino);
    }

    public int getCodigo() {
        return codigo.get();
    }

    public void setCodigo(int codigo) {
        this.codigo.set(codigo);
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getSexo() {
        return sexo.get();
    }

    public void setSexo(String sexo) {
        this.sexo.set(sexo);
    }

    public String getEndereco() {
        return endereco.get();
    }

    public void setEndereco(String endereco) {
        this.endereco.set(endereco);
    }

    public String getNascimento() {
        return nascimento.get();
    }

    public void setNascimento(String nascimento) {
        this.nascimento.set(nascimento);
    }

    public String getMae() {
        return mae.get();
    }

    public void setMae(String mae) {
        this.mae.set(mae);
    }

    public String getPai() {
        return pai.get();
    }

    public void setPai(String pai) {
        this.pai.set(pai);
    }

    public String getSerie() {
        return serie.get();
    }

    public void setSerie(String serie) {
        this.serie.set(serie);
    }

    public String getTelefone() {
        return telefone.get();
    }

    public void setTelefone(String telefone) {
        this.telefone.set(telefone);
    }

    public String getUf() {
        return uf.get();
    }

    public void setUf(String uf) {
        this.uf.set(uf);
    }

    public String getCep() {
        return cep.get();
    }

    public void setCep(String cep) {
        this.cep.set(cep);
    }

    public String getEnsino() {
        return ensino.get();
    }

    public void setEnsino(String ensino) {
        this.ensino.set(ensino);
    }

    public IntegerProperty getCodigoProperty() {
        return this.codigo;
    }

    public StringProperty getNomeProperty() {
        return this.nome;
    }

    public StringProperty getSexoProperty() {
        return this.sexo;
    }

    public StringProperty getEnderecoProperty() {
        return this.endereco;
    }

    public StringProperty getNascimentoProperty() {
        return this.nascimento;
    }

    public StringProperty getMaeProperty() {
        return this.mae;
    }

    public StringProperty getPaiProperty() {
        return this.pai;
    }

    public StringProperty getSerieProperty() {
        return this.serie;
    }

    public StringProperty getTelefoneProperty() {
        return this.telefone;
    }

    public StringProperty getUfProperty() {
        return this.uf;
    }

    public StringProperty getCepProperty() {
        return this.cep;
    }

    public StringProperty getEnsinoProperty() {
        return this.ensino;
    }
}
