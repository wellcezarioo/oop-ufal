package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchidoException;

import java.io.Serializable;
import java.util.*;

/**
 * Classe que representa um usu�rio no sistema Jackut.
 * Gerencia informa��es como login, senha, nome, atributos de perfil, amigos, recados e convites pendentes.
 */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;
    private String nome;
    private List<String> amigos;
    private Queue<String> recados;
    private Map<String, String> atributos;
    private Set<String> convitesPendentes;

    /**
     * Construtor da classe Usuario.
     * Inicializa o usu�rio com um login, senha e nome.
     *
     * @param login O login do usu�rio.
     * @param senha A senha do usu�rio.
     * @param nome O nome do usu�rio.
     */
    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.amigos = new ArrayList<>();
        this.recados = new LinkedList<>();
        this.atributos = new HashMap<>();
        this.convitesPendentes = new HashSet<>();
    }

    /**
     * Verifica se a senha fornecida � v�lida para este usu�rio.
     *
     * @param senha A senha a ser verificada.
     * @return Retorna true se a senha for v�lida, caso contr�rio, false.
     */
    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }

    /**
     * Edita ou cria um atributo no perfil do usu�rio.
     *
     * @param atributo O nome do atributo.
     * @param valor O valor do atributo.
     */
    public void editarAtributo(String atributo, String valor) {
        if ("nome".equals(atributo)) {
            this.nome = valor;
        } else {
            atributos.put(atributo, valor);
        }
    }

    /**
     * Obt�m o valor de um atributo do perfil do usu�rio.
     *
     * @param atributo O nome do atributo.
     * @return O valor do atributo.
     * @throws AtributoNaoPreenchidoException Se o atributo solicitado n�o foi preenchido.
     */
    public String getAtributo(String atributo) {
        if ("nome".equals(atributo)) {
            return this.nome;
        }
        if (atributos.containsKey(atributo)) {
            return atributos.get(atributo);
        }
        throw new AtributoNaoPreenchidoException();
    }

    /**
     * Verifica se o usu�rio � amigo de outro usu�rio.
     *
     * @param amigo O login do outro usu�rio.
     * @return Retorna true se o usu�rio for amigo do outro, caso contr�rio, false.
     */
    public boolean ehAmigo(String amigo) {
        return amigos.contains(amigo);
    }

    /**
     * Adiciona um amigo � lista de amigos do usu�rio.
     *
     * @param amigo O login do amigo a ser adicionado.
     */
    public void confirmarAmizade(String amigo) {
        if (!amigos.contains(amigo)) {
            amigos.add(amigo);
        }
    }

    /**
     * Retorna a lista de amigos do usu�rio.
     *
     * @return A lista de amigos do usu�rio.
     */
    public List<String> getAmigos() {
        return amigos;
    }

    /**
     * Adiciona um convite de amizade � lista de convites pendentes do usu�rio.
     *
     * @param deUsuario O login do usu�rio que enviou o convite.
     */
    public void adicionarConvite(String deUsuario) {
        convitesPendentes.add(deUsuario);
    }

    /**
     * Remove um convite de amizade da lista de convites pendentes do usu�rio.
     *
     * @param deUsuario O login do usu�rio que enviou o convite.
     */
    public void removerConvite(String deUsuario) {
        convitesPendentes.remove(deUsuario);
    }

    /**
     * Verifica se o usu�rio tem um convite pendente de outro usu�rio.
     *
     * @param deUsuario O login do usu�rio que enviou o convite.
     * @return Retorna true se o convite estiver pendente, caso contr�rio, false.
     */
    public boolean temConvite(String deUsuario) {
        return convitesPendentes.contains(deUsuario);
    }

    /**
     * Adiciona um recado � fila de recados do usu�rio.
     *
     * @param mensagem O conte�do do recado.
     */
    public void receberRecado(String mensagem) {
        recados.add(mensagem);
    }

    /**
     * Retorna e remove o primeiro recado da fila de recados do usu�rio.
     *
     * @return O conte�do do primeiro recado, ou null se n�o houver recados.
     */
    public String lerRecado() {
        return recados.poll();
    }
}
