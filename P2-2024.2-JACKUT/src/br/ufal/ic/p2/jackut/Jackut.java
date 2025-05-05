package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Classe respons�vel pela l�gica de neg�cio do sistema Jackut.
 * Gerencia os usu�rios, sess�es de login, e intera��es entre usu�rios, adi��o de amigos, envio de recados,
 * e a manipula��o de atributos de perfil. Al�m disso, gerencia a persist�ncia dos dados em um arquivo.
 */
public class Jackut implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Usuario> usuarios;  // Mapa de login -> Usuario
    private transient Map<String, String> sessoes;  // Mapa de sessaoId -> login
    private static final String ARQ_PERSISTENCIA = "jackut.dat";  // Arquivo de persist�ncia

    /**
     * Construtor da classe Jackut.
     * Tenta carregar os dados persistidos a partir de um arquivo, se existir.
     * Caso contr�rio, inicializa as cole��es de usu�rios e sess�es.
     */
    public Jackut() {
        carregarDadosDoArquivo();
        if (this.usuarios == null) {
            this.usuarios = new HashMap<>();
        }
        this.sessoes = new HashMap<>();
    }

    //  M�todos principais

    /**
     * Zera os dados do sistema em mem�ria. Usado principalmente em testes.
     * Limpa as listas de usu�rios e sess�es.
     */
    public void zerarSistema() {
        usuarios.clear();
        if (sessoes != null) {
            sessoes.clear();
        }
    }

    /**
     * Cria um novo usu�rio no sistema, verificando as regras de login e senha.
     * Lan�a exce��es caso o login, senha ou nome sejam inv�lidos ou se o login j� estiver em uso.
     *
     * @param login O login do novo usu�rio.
     * @param senha A senha do novo usu�rio.
     * @param nome O nome do novo usu�rio.
     * @throws LoginInvalidoException Se o login for inv�lido.
     * @throws SenhaInvalidaException Se a senha for inv�lida.
     * @throws ContaComEsseNomeJaExisteException Se j� existir uma conta com o mesmo login.
     */
    public void criarUsuario(String login, String senha, String nome) {
        if (login == null || login.trim().isEmpty()) {
            throw new LoginInvalidoException(); // "Login inv�lido."
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new SenhaInvalidaException(); // "Senha inv�lida."
        }
        if (usuarios.containsKey(login)) {
            throw new ContaComEsseNomeJaExisteException(); // "Conta com esse nome j� existe."
        }
        if (nome == null) {
            nome = "";
        }
        Usuario novo = new Usuario(login, senha, nome);
        usuarios.put(login, novo);
    }

    /**
     * Abre uma sess�o para o usu�rio, verificando login e senha.
     * Retorna um ID de sess�o �nico se as credenciais estiverem corretas.
     * Lan�a exce��o se o login ou senha estiverem inv�lidos.
     *
     * @param login O login do usu�rio.
     * @param senha A senha do usu�rio.
     * @return Um ID �nico de sess�o.
     * @throws LoginOuSenhaInvalidosException Se o login ou senha estiverem incorretos.
     */
    public String abrirSessao(String login, String senha) {
        if (login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            throw new LoginOuSenhaInvalidosException();
        }
        Usuario usuario = usuarios.get(login);
        if (usuario == null || !usuario.verificarSenha(senha)) {
            throw new LoginOuSenhaInvalidosException();
        }
        String sessaoId = UUID.randomUUID().toString();
        sessoes.put(sessaoId, login);
        return sessaoId;
    }

    /**
     * Retorna o valor de um atributo do usu�rio especificado.
     * Lan�a "Usu�rio n�o cadastrado" se o login n�o for encontrado.
     *
     * @param login O login do usu�rio.
     * @param atributo O atributo desejado.
     * @return O valor do atributo.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
     * @throws AtributoNaoPreenchidoException Se o atributo n�o estiver preenchido.
     */
    public String getAtributoUsuario(String login, String atributo) {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException(); // "Usu�rio n�o cadastrado."
        }
        return usuario.getAtributo(atributo);
    }

    /**
     * Edita (ou cria) um atributo do perfil do usu�rio com base na sess�o ativa.
     * Lan�a "Usu�rio n�o cadastrado" se a sess�o for inv�lida.
     *
     * @param sessaoId O ID da sess�o do usu�rio.
     * @param atributo O atributo a ser editado.
     * @param valor O novo valor do atributo.
     * @throws UsuarioNaoCadastradoException Se a sess�o n�o for v�lida ou o usu�rio n�o estiver cadastrado.
     */
    public void editarPerfil(String sessaoId, String atributo, String valor) {
        String login = sessoes.get(sessaoId);
        if (login == null) {
            throw new UsuarioNaoCadastradoException(); // "Usu�rio n�o cadastrado."
        }
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException(); // "Usu�rio n�o cadastrado."
        }
        usuario.editarAtributo(atributo, valor);
    }

    /**
     * Adiciona um amigo ao usu�rio, enviando um convite ou confirmando amizade, dependendo do caso.
     * Lan�a erros conforme os requisitos da User Story 3.
     *
     * @param sessaoId O ID da sess�o do usu�rio que est� adicionando o amigo.
     * @param amigo O login do amigo a ser adicionado.
     * @throws UsuarioNaoCadastradoException Se a sess�o for inv�lida ou o usu�rio n�o estiver cadastrado.
     * @throws UsuarioNaoPodeAdicionarASiMesmoException Se o usu�rio tentar adicionar a si mesmo como amigo.
     * @throws UsuarioJaEstaAdicionadoEsperandoException Se o amigo j� estiver esperando aceita��o do convite.
     * @throws UsuarioJaEstaAdicionadoException Se o amigo j� estiver adicionado como amigo.
     */
    public void adicionarAmigo(String sessaoId, String amigo) {
        String loginSolicitante = sessoes.get(sessaoId);
        if (loginSolicitante == null) {
            throw new UsuarioNaoCadastradoException(); // "Usu�rio n�o cadastrado."
        }
        if (loginSolicitante.equals(amigo)) {
            throw new UsuarioNaoPodeAdicionarASiMesmoException(); // "Usu�rio n�o pode adicionar a si mesmo como amigo."
        }
        Usuario solicitante = usuarios.get(loginSolicitante);
        Usuario usuarioAlvo = usuarios.get(amigo);
        if (usuarioAlvo == null) {
            throw new UsuarioNaoCadastradoException();
        }
        if (solicitante.ehAmigo(amigo)) {
            throw new UsuarioJaEstaAdicionadoException(); // "Usu�rio j� est� adicionado como amigo."
        }
        if (usuarioAlvo.temConvite(loginSolicitante)) {
            throw new UsuarioJaEstaAdicionadoEsperandoException(); // "Usu�rio j� est� adicionado como amigo, esperando aceita��o do convite."
        }
        if (solicitante.temConvite(amigo)) {
            solicitante.removerConvite(amigo);
            solicitante.confirmarAmizade(amigo);
            usuarioAlvo.confirmarAmizade(loginSolicitante);
            return;
        }
        usuarioAlvo.adicionarConvite(loginSolicitante);
    }

    /**
     * Verifica se dois usu�rios s�o amigos.
     *
     * @param login O login do usu�rio.
     * @param amigo O login do amigo a ser verificado.
     * @return Retorna true se os usu�rios forem amigos, caso contr�rio, false.
     * @throws UsuarioNaoCadastradoException Se o login n�o estiver cadastrado.
     */
    public boolean ehAmigo(String login, String amigo) {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException(); // "Usu�rio n�o cadastrado."
        }
        return usuario.ehAmigo(amigo);
    }

    /**
     * Retorna os amigos de um usu�rio no formato de conjunto {amigo1, amigo2}.
     *
     * @param login O login do usu�rio.
     * @return A lista de amigos do usu�rio no formato de conjunto.
     * @throws UsuarioNaoCadastradoException Se o login n�o estiver cadastrado.
     */
    public String getAmigos(String login) {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException(); // "Usu�rio n�o cadastrado."
        }
        var lista = usuario.getAmigos();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < lista.size(); i++) {
            sb.append(lista.get(i));
            if (i < lista.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Envia um recado de um usu�rio para outro destinat�rio.
     * Lan�a exce��es apropriadas caso a sess�o seja inv�lida ou o usu�rio tente enviar um recado para si mesmo.
     *
     * @param sessaoId O ID da sess�o do usu�rio que est� enviando o recado.
     * @param destinatario O login do destinat�rio.
     * @param recado O conte�do do recado.
     * @throws UsuarioNaoCadastradoException Se o destinat�rio n�o estiver cadastrado.
     * @throws UsuarioNaoPodeEnviarRecadoParaSiMesmoException Se o usu�rio tentar enviar um recado para si mesmo.
     */
    public void enviarRecado(String sessaoId, String destinatario, String recado) {
        String login = sessoes.get(sessaoId);
        if (login == null) {
            throw new UsuarioNaoCadastradoException(); // "Usu�rio n�o cadastrado."
        }
        if (login.equals(destinatario)) {
            throw new UsuarioNaoPodeEnviarRecadoParaSiMesmoException(); // "Usu�rio n�o pode enviar recado para si mesmo."
        }
        Usuario userDest = usuarios.get(destinatario);
        if (userDest == null) {
            throw new UsuarioNaoCadastradoException();
        }
        userDest.receberRecado(recado);
    }

    /**
     * L� o primeiro recado da fila de recados do usu�rio.
     * Lan�a exce��es apropriadas se a sess�o for inv�lida ou se n�o houver recados.
     *
     * @param sessaoId O ID da sess�o do usu�rio.
     * @return O conte�do do primeiro recado.
     * @throws UsuarioNaoCadastradoException Se o login n�o estiver cadastrado.
     * @throws NaoHaRecadosException Se n�o houver recados na fila.
     */
    public String lerRecado(String sessaoId) {
        String login = sessoes.get(sessaoId);
        if (login == null) {
            throw new UsuarioNaoCadastradoException(); // "Usu�rio n�o cadastrado."
        }
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException(); // "Usu�rio n�o cadastrado."
        }
        String recado = usuario.lerRecado();
        if (recado == null) {
            throw new NaoHaRecadosException(); // "N�o h� recados."
        }
        return recado;
    }

    /**
     * Encerra o sistema, salvando os dados no arquivo e limpando as sess�es.
     */
    public void encerrarSistema() {
        salvarDadosNoArquivo();
        if (sessoes != null) {
            sessoes.clear();
        }
    }

    //  M�todos privados de persist�ncia

    /**
     * Carrega os dados do arquivo de persist�ncia, se existir.
     * Caso o arquivo esteja corrompido ou n�o exista, os dados do sistema s�o carregados como vazios.
     */
    private void carregarDadosDoArquivo() {
        File f = new File(ARQ_PERSISTENCIA);
        if (f.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
                Jackut jackutPersistido = (Jackut) in.readObject();
                this.usuarios = jackutPersistido.usuarios;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Salva os dados do sistema no arquivo de persist�ncia `jackut.dat`.
     */
    private void salvarDadosNoArquivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQ_PERSISTENCIA))) {
            out.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
