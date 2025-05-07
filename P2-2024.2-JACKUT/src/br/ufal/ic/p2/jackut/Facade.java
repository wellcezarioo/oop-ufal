package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;
import java.util.Set;

/**
 * A classe {@code Facade} atua como uma interface de acesso para as funcionalidades do sistema Jackut.
 * Ela abstrai a complexidade do funcionamento interno do sistema, fornecendo m�todos para gerenciar
 * usu�rios, amigos, comunidades e outros aspectos relacionados � rede social.
 * <p>
 * Esta classe delega as chamadas aos m�todos correspondentes da classe {@link Jackut}, que gerencia
 * a l�gica do sistema.
 * </p>
 *
 * @author [Seu Nome]
 * @version 1.0
 */
public class Facade {

    private Jackut jackut = new Jackut();

    /**
     * Zera o sistema, removendo todos os dados existentes.
     */
    public void zerarSistema() {
        jackut.zerarSistema();
    }

    /**
     * Cria um novo usu�rio no sistema com o login, senha e nome fornecidos.
     *
     * @param l o login do novo usu�rio
     * @param s a senha do novo usu�rio
     * @param n o nome do novo usu�rio
     */
    public void criarUsuario(String l, String s, String n) {
        jackut.criarUsuario(l, s, n);
    }

    /**
     * Abre uma sess�o para o usu�rio fornecido com login e senha.
     *
     * @param l o login do usu�rio
     * @param s a senha do usu�rio
     * @return uma string representando a sess�o do usu�rio
     */
    public String abrirSessao(String l, String s) {
        return jackut.abrirSessao(l, s);
    }

    /**
     * Obt�m o valor de um atributo de um usu�rio.
     *
     * @param l o login do usu�rio
     * @param a o nome do atributo a ser obtido
     * @return o valor do atributo solicitado
     */
    public String getAtributoUsuario(String l, String a) {
        return jackut.getAtributoUsuario(l, a);
    }

    /**
     * Edita o perfil de um usu�rio.
     *
     * @param sid a sess�o do usu�rio
     * @param a o atributo a ser editado
     * @param v o novo valor do atributo
     */
    public void editarPerfil(String sid, String a, String v) {
        jackut.editarPerfil(sid, a, v);
    }

    /**
     * Adiciona um amigo a um usu�rio.
     *
     * @param sid a sess�o do usu�rio
     * @param a o login do amigo a ser adicionado
     */
    public void adicionarAmigo(String sid, String a) {
        jackut.adicionarAmigo(sid, a);
    }

    /**
     * Verifica se dois usu�rios s�o amigos.
     *
     * @param l o login do usu�rio
     * @param a o login do poss�vel amigo
     * @return {@code true} se forem amigos, {@code false} caso contr�rio
     */
    public boolean ehAmigo(String l, String a) {
        return jackut.ehAmigo(l, a);
    }

    /**
     * Obt�m os amigos de um usu�rio.
     *
     * @param l o login do usu�rio
     * @return uma string com os amigos do usu�rio
     */
    public String getAmigos(String l) {
        return jackut.getAmigos(l);
    }

    /**
     * Envia um recado de um usu�rio para outro.
     *
     * @param sid a sess�o do usu�rio que est� enviando o recado
     * @param d o destinat�rio do recado
     * @param r o conte�do do recado
     */
    public void enviarRecado(String sid, String d, String r) {
        jackut.enviarRecado(sid, d, r);
    }

    /**
     * L� os recados de um usu�rio.
     *
     * @param sid a sess�o do usu�rio
     * @return o conte�do dos recados do usu�rio
     */
    public String lerRecado(String sid) {
        return jackut.lerRecado(sid);
    }

    /**
     * Cria uma nova comunidade no sistema.
     *
     * @param sid a sess�o do usu�rio
     * @param n o nome da comunidade
     * @param d a descri��o da comunidade
     */
    public void criarComunidade(String sid, String n, String d) {
        jackut.criarComunidade(sid, n, d);
    }

    /**
     * Obt�m a descri��o de uma comunidade.
     *
     * @param n o nome da comunidade
     * @return a descri��o da comunidade
     */
    public String getDescricaoComunidade(String n) {
        return jackut.getDescricaoComunidade(n);
    }

    /**
     * Obt�m o dono de uma comunidade.
     *
     * @param n o nome da comunidade
     * @return o login do dono da comunidade
     */
    public String getDonoComunidade(String n) {
        return jackut.getDonoComunidade(n);
    }

    /**
     * Obt�m os membros de uma comunidade.
     *
     * @param n o nome da comunidade
     * @return uma string com os membros da comunidade
     */
    public String getMembrosComunidade(String n) {
        return jackut.getMembrosComunidade(n);
    }

    /**
     * Obt�m as comunidades associadas a uma chave de busca.
     *
     * @param chave a chave de busca para localizar comunidades
     * @return uma string com as comunidades encontradas
     */
    public String getComunidades(String chave) {
        return jackut.getComunidades(chave);
    }

    /**
     * Adiciona um usu�rio a uma comunidade.
     *
     * @param sid a sess�o do usu�rio
     * @param n o nome da comunidade
     */
    public void adicionarComunidade(String sid, String n) {
        jackut.adicionarComunidade(sid, n);
    }

    /**
     * Envia uma mensagem a uma comunidade.
     *
     * @param sid a sess�o do usu�rio
     * @param com o nome da comunidade
     * @param m o conte�do da mensagem
     */
    public void enviarMensagem(String sid, String com, String m) {
        jackut.enviarMensagem(sid, com, m);
    }

    /**
     * L� as mensagens de um usu�rio.
     *
     * @param sid a sess�o do usu�rio
     * @return o conte�do das mensagens do usu�rio
     */
    public String lerMensagem(String sid) {
        return jackut.lerMensagem(sid);
    }

    /**
     * Adiciona um �dolo a um usu�rio.
     *
     * @param sid a sess�o do usu�rio
     * @param idolo o login do �dolo a ser adicionado
     */
    public void adicionarIdolo(String sid, String idolo) {
        jackut.adicionarIdolo(sid, idolo);
    }

    /**
     * Verifica se um usu�rio � f� de outro.
     *
     * @param login o login do f�
     * @param idolo o login do �dolo
     * @return {@code true} se o usu�rio for f� do �dolo, {@code false} caso contr�rio
     */
    public boolean ehFa(String login, String idolo) {
        return jackut.ehFa(login, idolo);
    }

    /**
     * Obt�m os f�s de um usu�rio.
     *
     * @param login o login do �dolo
     * @return uma string com os f�s do usu�rio
     */
    public String getFas(String login) {
        Set<String> fas = jackut.getFas(login);
        return "{" + String.join(",", fas) + "}";
    }

    /**
     * Adiciona uma paquera a um usu�rio.
     *
     * @param sid a sess�o do usu�rio
     * @param paquera o login da paquera a ser adicionada
     */
    public void adicionarPaquera(String sid, String paquera) {
        jackut.adicionarPaquera(sid, paquera);
    }

    /**
     * Verifica se um usu�rio tem uma paquera.
     *
     * @param login o login do usu�rio
     * @param paquera o login da paquera
     * @return {@code true} se for paquera, {@code false} caso contr�rio
     */
    public boolean ehPaquera(String login, String paquera) {
        return jackut.ehPaquera(login, paquera);
    }

    /**
     * Obt�m as paqueras de um usu�rio.
     *
     * @param login o login do usu�rio
     * @return uma string com as paqueras do usu�rio
     */
    public String getPaqueras(String login) {
        Set<String> ps = jackut.getPaqueras(login);
        return "{" + String.join(",", ps) + "}";
    }

    /**
     * Adiciona um inimigo a um usu�rio.
     *
     * @param sid a sess�o do usu�rio
     * @param inimigo o login do inimigo a ser adicionado
     */
    public void adicionarInimigo(String sid, String inimigo) {
        jackut.adicionarInimigo(sid, inimigo);
    }

    /**
     * Remove um usu�rio do sistema.
     *
     * @param sid a sess�o do usu�rio a ser removido
     */
    public void removerUsuario(String sid) {
        jackut.removerUsuario(sid);
    }

    /**
     * Encerra o sistema, apagando todos os dados.
     */
    public void encerrarSistema() {
        jackut.encerrarSistema();
    }
}
