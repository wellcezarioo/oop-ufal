package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchidoException;
import java.io.Serializable;
import java.util.*;

/**
 * Classe que representa um usu�rio na rede social Jackut.
 * Cont�m informa��es de login, senha, nome, amigos, recados, atributos, convites pendentes,
 * f�s, �dolos, paqueras, inimigos e comunidades participando.
 *
 * A classe fornece m�todos para gerenciar e acessar essas informa��es.
 */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;
    private String nome;

    private List<String> amigos          = new ArrayList<>();
    private Queue<String> recados        = new LinkedList<>();
    private Map<String,String> atributos = new HashMap<>();
    private Set<String> convitesPendentes = new HashSet<>();

    private Set<String> idolos    = new LinkedHashSet<>();
    private Set<String> paqueras  = new LinkedHashSet<>();
    private Set<String> inimigos  = new LinkedHashSet<>();
    private Queue<String> mensagens = new LinkedList<>();

    // Hist�rico de comunidades na ordem de ingresso
    private Set<String> comunidadesParticipando = new LinkedHashSet<>();

    /**
     * Constr�i um novo usu�rio com as informa��es fornecidas.
     *
     * @param login Login do usu�rio
     * @param senha Senha do usu�rio
     * @param nome Nome do usu�rio
     */
    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome  = nome;
    }

    /**
     * @return O login do usu�rio
     */
    public String getLogin() { return login; }

    /**
     *
     * @return O nome do usu�rio
     */
    public String getNome()  { return nome; }

    /**
     * Verifica se a senha fornecida corresponde � senha do usu�rio.
     *
     * @param s Senha a ser verificada
     * @return True se a senha for v�lida, false caso contr�rio
     */
    public boolean verificarSenha(String s) { return this.senha.equals(s); }

    // Perfil

    /**
     * Edita um atributo do usu�rio.
     *
     * @param atributo O nome do atributo a ser editado
     * @param valor O valor a ser atribu�do ao atributo
     */
    public void editarAtributo(String atributo, String valor) {
        if ("nome".equals(atributo)) this.nome = valor;
        else atributos.put(atributo, valor);
    }

    /**
     *
     * @param atributo O nome do atributo
     * @return O valor do atributo
     * @throws AtributoNaoPreenchidoException Se o atributo n�o estiver preenchido
     */
    public String getAtributo(String atributo) {
        if ("nome".equals(atributo)) return nome;
        if (atributos.containsKey(atributo)) return atributos.get(atributo);
        throw new AtributoNaoPreenchidoException();
    }

    // Amigos

    /**
     * Verifica se o usu�rio � amigo de outro usu�rio.
     *
     * @param a Login do amigo
     * @return True se forem amigos, false caso contr�rio
     */
    public boolean ehAmigo(String a) { return amigos.contains(a); }

    /**
     * Confirma uma amizade com outro usu�rio.
     *
     * @param a Login do amigo
     */
    public void confirmarAmizade(String a) {
        if (!amigos.contains(a)) amigos.add(a);
    }

    /**
     * Retorna a lista de amigos do usu�rio.
     *
     * @return Lista de amigos
     */
    public List<String> getAmigos() { return amigos; }

    /**
     * Adiciona um convite pendente de amizade.
     *
     * @param de Login do usu�rio que enviou o convite
     */
    public void adicionarConvite(String de) { convitesPendentes.add(de); }

    /**
     * Verifica se h� um convite pendente de amizade.
     *
     * @param de Login do usu�rio que enviou o convite
     * @return True se houver convite pendente, false caso contr�rio
     */
    public boolean temConvite(String de)    { return convitesPendentes.contains(de); }

    /**
     * Remove um convite pendente de amizade.
     *
     * @param de Login do usu�rio que enviou o convite
     */
    public void removerConvite(String de)   { convitesPendentes.remove(de); }

    // Recados privados

    /**
     * Recebe um recado privado.
     *
     * @param msg O recado a ser recebido
     */
    public void receberRecado(String msg) { recados.add(msg); }

    /**
     * L� o pr�ximo recado privado.
     *
     * @return O recado lido, ou null se n�o houver recados
     */
    public String lerRecado()             { return recados.poll(); }

    /**
     * Limpa todos os recados privados.
     */
    public void limparRecados()           { recados.clear(); }

    // Mensagens de comunidade

    /**
     * Recebe uma mensagem de comunidade.
     *
     * @param msg A mensagem a ser recebida
     */
    public void receberMensagem(String msg) { mensagens.add(msg); }

    /**
     * L� a pr�xima mensagem de comunidade.
     *
     * @return A mensagem lida, ou null se n�o houver mensagens
     */
    public String lerMensagem()              { return mensagens.poll(); }

    /**
     * Limpa todas as mensagens de comunidade.
     */
    public void limparMensagens()            { mensagens.clear(); }

    // F�s/�dolos

    /**
     * Adiciona um �dolo ao usu�rio.
     *
     * @param idolo O login do �dolo
     */
    public void adicionarIdolo(String idolo) { idolos.add(idolo); }

    /**
     * Verifica se o usu�rio � �dolo de outro usu�rio.
     *
     * @param idolo O login do �dolo
     * @return True se for �dolo, false caso contr�rio
     */
    public boolean ehIdolo(String idolo)     { return idolos.contains(idolo); }

    // Paqueras

    /**
     * Adiciona uma paquera ao usu�rio.
     *
     * @param p O login da paquera
     */
    public void adicionarPaquera(String p) { paqueras.add(p); }

    /**
     * Verifica se o usu�rio tem uma paquera.
     *
     * @param p O login da paquera
     * @return True se for paquera, false caso contr�rio
     */
    public boolean ehPaquera(String p)     { return paqueras.contains(p); }

    /**
     * Retorna o conjunto de paqueras do usu�rio.
     *
     * @return O conjunto de paqueras
     */
    public Set<String> getPaqueras()       { return paqueras; }

    // Inimizades

    /**
     * Adiciona um inimigo ao usu�rio.
     *
     * @param inimigo O login do inimigo
     */
    public void adicionarInimigo(String inimigo) { inimigos.add(inimigo); }

    /**
     * Verifica se o usu�rio tem um inimigo.
     *
     * @param i O login do inimigo
     * @return True se for inimigo, false caso contr�rio
     */
    public boolean ehInimigo(String i)            { return inimigos.contains(i); }

    // Comunidades

    /**
     * Adiciona o usu�rio a uma comunidade.
     *
     * @param nomeComunidade O nome da comunidade
     */
    public void adicionarComunidadeParticipa(String nomeComunidade) {
        comunidadesParticipando.add(nomeComunidade);
    }

    /**
     * Retorna as comunidades que o usu�rio participa.
     *
     * @return O conjunto de comunidades que o usu�rio participa
     */
    public Set<String> getComunidadesParticipando() {
        return comunidadesParticipando;
    }

    /**
     * Limpa as comunidades nas quais o usu�rio participa, mantendo apenas as que est�o presentes na cole��o fornecida.
     *
     * @param existentes Cole��o de comunidades existentes a serem mantidas
     */
    public void limparComunidadesParticipando(Collection<String> existentes) {
        comunidadesParticipando.retainAll(existentes);
    }
}
