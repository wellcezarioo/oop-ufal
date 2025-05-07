package br.ufal.ic.p2.jackut;
import br.ufal.ic.p2.jackut.exceptions.*;

/**
 * A classe Facade fornece uma interface simples para interagir com o sistema Jackut.
 * Ela exp�e os m�todos necess�rios para realizar a��es no sistema, como criar usu�rios,
 * gerenciar sess�es, adicionar amigos e manipular perfis.
 * Esta classe n�o lida com exce��es diretamente, deixando-as propagar para que o EasyAccept
 * capture as mensagens de erro apropriadas durante os testes de aceita��o.
 */
public class Facade {
    private Jackut jackut;

    /**
     * Construtor da classe Facade que inicializa o sistema Jackut.
     * O Jackut � carregado com os dados persistidos, se houver.
     */
    public Facade() {
        this.jackut = new Jackut();
    }

    /**
     * Zera todos os dados do sistema, limpando usu�rios e sess�es.
     * Usado principalmente em testes de aceita��o.
     */
    public void zerarSistema() {
        jackut.zerarSistema();
    }

    /**
     * Cria um novo usu�rio no sistema com login, senha e nome fornecidos.
     *
     * @param login O login do novo usu�rio.
     * @param senha A senha do novo usu�rio.
     * @param nome O nome do novo usu�rio.
     */
    public void criarUsuario(String login, String senha, String nome) {
        jackut.criarUsuario(login, senha, nome);
    }

    /**
     * Abre uma sess�o para o usu�rio com o login e senha fornecidos.
     * Retorna um ID de sess�o �nico para o usu�rio autenticado.
     *
     * @param login O login do usu�rio.
     * @param senha A senha do usu�rio.
     * @return O ID da sess�o.
     * @throws LoginOuSenhaInvalidosException Se o login ou a senha forem inv�lidos.
     */
    public String abrirSessao(String login, String senha) {
        return jackut.abrirSessao(login, senha);
    }

    /**
     * Obt�m o valor de um atributo de um usu�rio especificado.
     *
     * @param login O login do usu�rio.
     * @param atributo O nome do atributo desejado.
     * @return O valor do atributo solicitado.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
     * @throws AtributoNaoPreenchidoException Se o atributo solicitado n�o estiver preenchido.
     */
    public String getAtributoUsuario(String login, String atributo) {
        return jackut.getAtributoUsuario(login, atributo);
    }

    /**
     * Edita ou cria um atributo de perfil para o usu�rio com a sess�o aberta.
     *
     * @param sessaoId O ID da sess�o do usu�rio.
     * @param atributo O nome do atributo a ser editado.
     * @param valor O novo valor do atributo.
     * @throws UsuarioNaoCadastradoException Se a sess�o for inv�lida.
     */
    public void editarPerfil(String sessaoId, String atributo, String valor) {
        jackut.editarPerfil(sessaoId, atributo, valor);
    }

    /**
     * Adiciona um amigo ao usu�rio que est� com a sess�o aberta.
     * Se o usu�rio j� tiver uma amizade ou convite pendente, uma exce��o ser� lan�ada.
     *
     * @param sessaoId O ID da sess�o do usu�rio.
     * @param amigo O login do amigo a ser adicionado.
     * @throws UsuarioNaoCadastradoException Se a sess�o for inv�lida ou o usu�rio n�o estiver cadastrado.
     * @throws UsuarioJaEstaAdicionadoEsperandoException Se o amigo j� est� esperando a aceita��o do convite.
     * @throws UsuarioJaEstaAdicionadoException Se o amigo j� for amigo do usu�rio.
     */
    public void adicionarAmigo(String sessaoId, String amigo) {
        jackut.adicionarAmigo(sessaoId, amigo);
    }

    /**
     * Verifica se dois usu�rios s�o amigos.
     *
     * @param login O login do primeiro usu�rio.
     * @param amigo O login do segundo usu�rio.
     * @return Retorna true se os usu�rios forem amigos, caso contr�rio, false.
     * @throws UsuarioNaoCadastradoException Se qualquer um dos usu�rios n�o estiver cadastrado.
     */
    public boolean ehAmigo(String login, String amigo) {
        return jackut.ehAmigo(login, amigo);
    }

    /**
     * Obt�m a lista de amigos de um usu�rio.
     *
     * @param login O login do usu�rio.
     * @return A lista de amigos do usu�rio em formato de string.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
     */
    public String getAmigos(String login) {
        return jackut.getAmigos(login);
    }

    /**
     * Envia um recado de um usu�rio para outro.
     *
     * @param sessaoId O ID da sess�o do usu�rio que est� enviando o recado.
     * @param destinatario O login do destinat�rio do recado.
     * @param recado O conte�do do recado.
     * @throws UsuarioNaoCadastradoException Se a sess�o ou o destinat�rio n�o estiverem cadastrados.
     * @throws UsuarioNaoPodeEnviarRecadoParaSiMesmoException Se o usu�rio tentar enviar um recado para si mesmo.
     */
    public void enviarRecado(String sessaoId, String destinatario, String recado) {
        jackut.enviarRecado(sessaoId, destinatario, recado);
    }

    /**
     * L� o primeiro recado da fila de recados do usu�rio que est� com a sess�o aberta.
     *
     * @param sessaoId O ID da sess�o do usu�rio.
     * @return O conte�do do recado.
     * @throws UsuarioNaoCadastradoException Se a sess�o n�o for v�lida.
     * @throws NaoHaRecadosException Se n�o houver recados.
     */
    public String lerRecado(String sessaoId) {
        return jackut.lerRecado(sessaoId);
    }

    /**
     * Encerra o sistema, salvando os dados persistidos e limpando as sess�es.
     */
    public void encerrarSistema() {
        jackut.encerrarSistema();
    }

    public void criarComunidade(String sessaoId, String nome, String descricao) {
        jackut.criarComunidade(sessaoId, nome, descricao);
    }

    public String getDescricaoComunidade(String nome) {
        return jackut.getDescricaoComunidade(nome);
    }

    public String getDonoComunidade(String nome) {
        return jackut.getDonoComunidade(nome);
    }

    public String getMembrosComunidade(String nome) {
        return jackut.getMembrosComunidade(nome);
    }

    public String getComunidades(String sessaoId) {
        String login = jackut.validarSessao(sessaoId); // Valida a sess�o
        return jackut.getComunidades(login);
    }

    public void adicionarComunidade(String sessaoId, String nomeComunidade) {
        String login = jackut.validarSessao(sessaoId);
        Comunidade comunidade = jackut.comunidades.get(nomeComunidade);
        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }
        comunidade.adicionarMembro(login);
    }

}
