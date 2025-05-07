package br.ufal.ic.p2.jackut;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Representa uma comunidade dentro da rede social Jackut.
 * A comunidade possui um nome, uma descri��o, um dono e um conjunto de membros.
 * O dono da comunidade � automaticamente adicionado como membro no momento da cria��o.
 * A classe permite adicionar novos membros � comunidade.
 *
 * <p>Esta classe implementa a interface {@link Serializable}, o que permite que objetos
 * da classe sejam convertidos para um formato byte-stream e gravados em arquivos ou transmitidos
 * pela rede.</p>
 *
 * @author [Seu Nome]
 * @version 1.0
 */
public class Comunidade implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * O nome da comunidade.
     */
    private String nome;

    /**
     * A descri��o da comunidade.
     */
    private String descricao;

    /**
     * O login do dono da comunidade.
     */
    private String dono;

    /**
     * Conjunto de membros da comunidade.
     * A utiliza��o de um {@link LinkedHashSet} garante a ordem de inser��o dos membros.
     */
    private Set<String> membros = new LinkedHashSet<>();

    /**
     * Construtor da classe Comunidade.
     * Inicializa a comunidade com o nome, descri��o e dono fornecidos.
     * O dono � automaticamente adicionado como membro da comunidade.
     *
     * @param nome o nome da comunidade
     * @param descricao a descri��o da comunidade
     * @param dono o login do dono da comunidade
     */
    public Comunidade(String nome, String descricao, String dono) {
        this.nome = nome;
        this.descricao = descricao;
        this.dono = dono;
        this.membros.add(dono); // O dono � automaticamente adicionado como membro
    }

    /**
     * Retorna o nome da comunidade.
     *
     * @return o nome da comunidade
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a descri��o da comunidade.
     *
     * @return a descri��o da comunidade
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o login do dono da comunidade.
     *
     * @return o login do dono da comunidade
     */
    public String getDono() {
        return dono;
    }

    /**
     * Retorna o conjunto de membros da comunidade.
     *
     * @return o conjunto de membros da comunidade
     */
    public Set<String> getMembros() {
        return membros;
    }

    /**
     * Adiciona um novo membro � comunidade.
     *
     * @param login o login do novo membro a ser adicionado
     */
    public void adicionarMembro(String login) {
        membros.add(login);
    }
}
