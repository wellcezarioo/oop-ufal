package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;
import java.io.*;
import java.util.*;

/**
 * Classe principal do sistema Jackut, que representa a rede social com funcionalidades de usu�rios, amizades, comunidades, recados, mensagens, f�s/�dolos, e mais.
 */
public class Jackut implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Usuario> usuarios       = new HashMap<>();
    private Map<String, Comunidade> comunidades = new HashMap<>();
    private transient Map<String, String> sessoes = new HashMap<>();
    private static final String ARQ = "jackut.dat";

    /**
     * Construtor da classe Jackut, que tenta carregar os dados previamente salvos.
     */
    public Jackut() {
        try { carregarDados(); }
        catch(Exception e) { reset(); }
    }

    /**
     * Reseta os dados do sistema, limpando os usu�rios, comunidades e sess�es.
     */
    private void reset() {
        usuarios.clear();
        comunidades.clear();
        sessoes.clear();
    }

    /**
     * Zera o sistema, removendo todos os dados.
     */
    public void zerarSistema() { reset(); }

    // ** Usu�rios **

    /**
     * Cria um novo usu�rio com login, senha e nome.
     *
     * @param login O login do usu�rio.
     * @param senha A senha do usu�rio.
     * @param nome O nome do usu�rio.
     * @throws LoginInvalidoException Se o login for inv�lido.
     * @throws SenhaInvalidaException Se a senha for inv�lida.
     * @throws ContaComEsseNomeJaExisteException Se j� existir um usu�rio com o mesmo login.
     */
    public void criarUsuario(String login, String senha, String nome) {
        if (login == null || login.trim().isEmpty()) throw new LoginInvalidoException();
        if (senha == null || senha.trim().isEmpty()) throw new SenhaInvalidaException();
        if (usuarios.containsKey(login)) throw new ContaComEsseNomeJaExisteException();
        usuarios.put(login, new Usuario(login, senha, nome == null ? "" : nome));
    }

    /**
     * Abre uma sess�o para um usu�rio com login e senha.
     *
     * @param login O login do usu�rio.
     * @param senha A senha do usu�rio.
     * @return O ID da sess�o gerada.
     * @throws LoginOuSenhaInvalidosException Se o login ou a senha estiverem incorretos.
     */
    public String abrirSessao(String login, String senha) {
        if (login == null || login.trim().isEmpty() ||
                senha  == null || senha.trim().isEmpty())
            throw new LoginOuSenhaInvalidosException();
        Usuario u = usuarios.get(login);
        if (u == null || !u.verificarSenha(senha))
            throw new LoginOuSenhaInvalidosException();
        String sid = UUID.randomUUID().toString();
        sessoes.put(sid, login);
        return sid;
    }

    /**
     * Retorna um atributo de um usu�rio, baseado no login e no nome do atributo.
     *
     * @param login O login do usu�rio.
     * @param atr O nome do atributo a ser recuperado.
     * @return O valor do atributo.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
     */
    public String getAtributoUsuario(String login, String atr) {
        Usuario u = usuarios.get(login);
        if (u == null) throw new UsuarioNaoCadastradoException();
        return u.getAtributo(atr);
    }

    /**
     * Edita o perfil de um usu�rio.
     *
     * @param sessao A sess�o do usu�rio.
     * @param atr O atributo a ser editado.
     * @param val O novo valor do atributo.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
     */
    public void editarPerfil(String sessao, String atr, String val) {
        String lg = sessoes.get(sessao);
        if (lg == null) throw new UsuarioNaoCadastradoException();
        Usuario u = usuarios.get(lg);
        if (u == null) throw new UsuarioNaoCadastradoException();
        u.editarAtributo(atr, val);
    }

    // ** Amigos **

    /**
     * Adiciona um amigo a um usu�rio.
     *
     * @param sessao A sess�o do usu�rio.
     * @param amigo O login do amigo a ser adicionado.
     * @throws FuncaoInvalidaException Se o amigo for inimigo.
     * @throws UsuarioNaoPodeAdicionarASiMesmoException Se o usu�rio tentar adicionar a si mesmo.
     * @throws UsuarioJaEstaAdicionadoException Se o amigo j� for adicionado.
     * @throws UsuarioJaEstaAdicionadoEsperandoException Se o amigo j� tiver um convite pendente.
     */
    public void adicionarAmigo(String sessao, String amigo) {
        String sol = validar(sessao);
        Usuario uSol = usuarios.get(sol);
        Usuario uAlvo = usuarios.get(amigo);
        if (uAlvo == null) throw new UsuarioNaoCadastradoException();

        if (uAlvo.ehInimigo(sol)) {
            throw new FuncaoInvalidaException(uAlvo.getNome() + " � seu inimigo.");
        }
        if (sol.equals(amigo)) throw new UsuarioNaoPodeAdicionarASiMesmoException();
        if (uSol.ehAmigo(amigo)) throw new UsuarioJaEstaAdicionadoException();
        if (uAlvo.temConvite(sol))
            throw new UsuarioJaEstaAdicionadoEsperandoException();
        if (uSol.temConvite(amigo)) {
            uSol.removerConvite(amigo);
            uSol.confirmarAmizade(amigo);
            uAlvo.confirmarAmizade(sol);
        } else {
            uAlvo.adicionarConvite(sol);
        }
    }

    /**
     * Verifica se dois usu�rios s�o amigos.
     *
     * @param l O login do usu�rio.
     * @param a O login do amigo a ser verificado.
     * @return True se os usu�rios forem amigos, caso contr�rio, false.
     * @throws UsuarioNaoCadastradoException Se algum dos usu�rios n�o estiver cadastrado.
     */
    public boolean ehAmigo(String l, String a) {
        Usuario u = usuarios.get(l);
        if (u == null) throw new UsuarioNaoCadastradoException();
        return u.ehAmigo(a);
    }

    /**
     * Retorna a lista de amigos de um usu�rio.
     *
     * @param l O login do usu�rio.
     * @return Uma string com os logins dos amigos.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
     */
    public String getAmigos(String l) {
        Usuario u = usuarios.get(l);
        if (u == null) throw new UsuarioNaoCadastradoException();
        return "{" + String.join(",", u.getAmigos()) + "}";
    }

    // ** Recados **

    /**
     * Envia um recado para outro usu�rio.
     *
     * @param sessao A sess�o do usu�rio remetente.
     * @param dest O login do destinat�rio.
     * @param msg A mensagem a ser enviada.
     * @throws FuncaoInvalidaException Se o destinat�rio for inimigo.
     * @throws UsuarioNaoPodeEnviarRecadoParaSiMesmoException Se o usu�rio tentar enviar um recado para si mesmo.
     * @throws UsuarioNaoCadastradoException Se o destinat�rio n�o estiver cadastrado.
     */
    public void enviarRecado(String sessao, String dest, String msg) {
        String sol = validar(sessao);
        Usuario uSol = usuarios.get(sol);
        Usuario uDest = usuarios.get(dest);
        if (uDest == null) throw new UsuarioNaoCadastradoException();

        if (uDest.ehInimigo(sol)) {
            throw new FuncaoInvalidaException(uDest.getNome() + " � seu inimigo.");
        }
        if (sol.equals(dest)) throw new UsuarioNaoPodeEnviarRecadoParaSiMesmoException();
        uDest.receberRecado(msg);
    }

    /**
     * L� o recado de um usu�rio.
     *
     * @param sessao A sess�o do usu�rio.
     * @return O conte�do do recado.
     * @throws NaoHaRecadosException Se n�o houver recados.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
     */
    public String lerRecado(String sessao) {
        String lg = validar(sessao);
        Usuario u = usuarios.get(lg);
        String m = u.lerRecado();
        if (m == null) throw new NaoHaRecadosException();
        return m;
    }

    // ** Persist�ncia **

    /**
     * Encerra o sistema, salvando os dados e limpando as sess�es.
     */
    public void encerrarSistema() {
        salvarDados();
        sessoes.clear();
    }

    // ** Comunidades **

    /**
     * Cria uma nova comunidade.
     *
     * @param sessao A sess�o do usu�rio que est� criando a comunidade.
     * @param nome O nome da comunidade.
     * @param desc A descri��o da comunidade.
     * @throws ComunidadeJaExisteException Se a comunidade j� existir.
     */
    public void criarComunidade(String sessao, String nome, String desc) {
        String lg = validar(sessao);
        if (comunidades.containsKey(nome)) throw new ComunidadeJaExisteException();
        comunidades.put(nome, new Comunidade(nome, desc, lg));
        usuarios.get(lg).adicionarComunidadeParticipa(nome);
    }

    /**
     * Retorna as comunidades nas quais o usu�rio est� participando.
     *
     * Este m�todo verifica se a chave fornecida corresponde a uma sess�o ativa. Caso a chave seja uma sess�o, o login
     * do usu�rio ser� validado a partir da sess�o. Se a chave n�o for uma sess�o, ela � tratada como o login diretamente.
     * O m�todo lan�a uma exce��o se o usu�rio n�o estiver cadastrado.
     *
     * @param chave A chave que pode ser o login do usu�rio ou uma sess�o ativa.
     * @return Uma string representando as comunidades nas quais o usu�rio est� participando, separadas por v�rgulas e
     * envolvidas por chaves (ex: "{comunidade1,comunidade2}").
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado no sistema.
     */
    public String getComunidades(String chave) {
        String login;
        if (sessoes.containsKey(chave)) {
            login = validar(chave);
        } else {
            login = chave;
            if (!usuarios.containsKey(login)) throw new UsuarioNaoCadastradoException();
        }
        Usuario u = usuarios.get(login);
        return "{" + String.join(",", u.getComunidadesParticipando()) + "}";
    }


    /**
     * Retorna a descri��o de uma comunidade.
     *
     * @param nome O nome da comunidade.
     * @return A descri��o da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade n�o existir.
     */
    public String getDescricaoComunidade(String nome) {
        Comunidade c = comunidades.get(nome);
        if (c == null) throw new ComunidadeNaoExisteException();
        return c.getDescricao();
    }

    /**
     * Retorna o dono de uma comunidade.
     *
     * @param nome O nome da comunidade.
     * @return O login do dono da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade n�o existir.
     */
    public String getDonoComunidade(String nome) {
        Comunidade c = comunidades.get(nome);
        if (c == null) throw new ComunidadeNaoExisteException();
        return c.getDono();
    }

    /**
     * Retorna os membros de uma comunidade.
     *
     * @param nome O nome da comunidade.
     * @return Uma string com os logins dos membros.
     * @throws ComunidadeNaoExisteException Se a comunidade n�o existir.
     */
    public String getMembrosComunidade(String nome) {
        Comunidade c = comunidades.get(nome);
        if (c == null) throw new ComunidadeNaoExisteException();
        return "{" + String.join(",", c.getMembros()) + "}";
    }

    /**
     * Adiciona um usu�rio a uma comunidade.
     *
     * @param sessao A sess�o do usu�rio que deseja participar.
     * @param nome O nome da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade n�o existir.
     * @throws UsuarioJaEstaNaComunidadeException Se o usu�rio j� for membro da comunidade.
     */
    public void adicionarComunidade(String sessao, String nome) {
        String lg = validar(sessao);
        Comunidade c = comunidades.get(nome);
        if (c == null) throw new ComunidadeNaoExisteException();
        if (c.getMembros().contains(lg)) throw new UsuarioJaEstaNaComunidadeException();
        c.adicionarMembro(lg);
        usuarios.get(lg).adicionarComunidadeParticipa(nome);
    }

    // ** Mensagens em comunidade **

    /**
     * Envia uma mensagem para todos os membros de uma comunidade, exceto inimigos.
     *
     * @param sessao A sess�o do usu�rio que est� enviando a mensagem.
     * @param com O nome da comunidade.
     * @param msg A mensagem a ser enviada.
     * @throws ComunidadeNaoExisteException Se a comunidade n�o existir.
     * @throws FuncaoInvalidaException Se o usu�rio for inimigo de algum membro da comunidade.
     */
    public void enviarMensagem(String sessao, String com, String msg) {
        String lg = validar(sessao);
        Comunidade c = comunidades.get(com);
        if (c == null) throw new ComunidadeNaoExisteException();
        for (String mb : c.getMembros()) {
            Usuario u = usuarios.get(mb);
            if (!u.ehInimigo(lg)) u.receberMensagem(msg);
        }
    }

    /**
     * L� a mensagem recebida por um usu�rio.
     *
     * @param sessao A sess�o do usu�rio.
     * @return O conte�do da mensagem.
     * @throws NaoHaMensagensException Se n�o houver mensagens.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
     */
    public String lerMensagem(String sessao) {
        String lg = validar(sessao);
        Usuario u = usuarios.get(lg);
        String m  = u.lerMensagem();
        if (m == null) throw new NaoHaMensagensException();
        return m;
    }

    // ** F�s/�dolos **

    /**
     * Adiciona um �dolo a um usu�rio.
     *
     * @param sessao A sess�o do usu�rio.
     * @param idolo O login do �dolo.
     * @throws FuncaoInvalidaException Se o �dolo for inimigo.
     * @throws UsuarioNaoPodeSerFaDeSiMesmoException Se o usu�rio tentar ser f� de si mesmo.
     * @throws UsuarioJaEstaIdoloException Se o usu�rio j� for �dolo do outro.
     * @throws UsuarioNaoCadastradoException Se o �dolo n�o estiver cadastrado.
     */
    public void adicionarIdolo(String sessao, String idolo) {
        String sol = validar(sessao);
        Usuario uSol = usuarios.get(sol);
        Usuario uId  = usuarios.get(idolo);
        if (uId == null) throw new UsuarioNaoCadastradoException();

        if (uId.ehInimigo(sol)) {
            throw new FuncaoInvalidaException(uId.getNome() + " � seu inimigo.");
        }
        if (sol.equals(idolo)) throw new UsuarioNaoPodeSerFaDeSiMesmoException();
        if (uSol.ehIdolo(idolo)) throw new UsuarioJaEstaIdoloException();
        uSol.adicionarIdolo(idolo);
    }

    /**
     * Verifica se um usu�rio � f� de outro.
     *
     * @param chave A chave de sess�o ou o login do usu�rio.
     * @param idolo O login do �dolo.
     * @return True se o usu�rio for f� do �dolo, caso contr�rio, false.
     * @throws UsuarioNaoCadastradoException Se algum dos usu�rios n�o estiver cadastrado.
     */
    public boolean ehFa(String chave, String idolo) {
        String login;
        if (sessoes.containsKey(chave)) {
            login = validar(chave);
        } else {
            login = chave;
            if (!usuarios.containsKey(login)) throw new UsuarioNaoCadastradoException();
        }
        return usuarios.get(login).ehIdolo(idolo);
    }

    /**
     * Retorna a lista de f�s de um �dolo.
     *
     * @param login O login do �dolo.
     * @return O conjunto de f�s do �dolo.
     * @throws UsuarioNaoCadastradoException Se o �dolo n�o estiver cadastrado.
     */
    public Set<String> getFas(String login) {
        if (!usuarios.containsKey(login)) throw new UsuarioNaoCadastradoException();
        Set<String> fas = new LinkedHashSet<>();
        for (Usuario u : usuarios.values()) {
            if (u.ehIdolo(login)) fas.add(u.getLogin());
        }
        return fas;
    }

    // ** Paqueras **

    /**
     * Adiciona uma paquera para o usu�rio.
     *
     * @param sessao A sess�o do usu�rio.
     * @param p O login da pessoa a ser adicionada como paquera.
     * @throws FuncaoInvalidaException Se a pessoa for inimiga.
     * @throws UsuarioNaoPodeSerPaqueraDeSiMesmoException Se o usu�rio tentar ser paquera de si mesmo.
     * @throws UsuarioJaEstaPaqueraException Se j� existe um relacionamento de paquera.
     * @throws UsuarioNaoCadastradoException Se a pessoa n�o estiver cadastrada.
     */
    public void adicionarPaquera(String sessao, String p) {
        String sol = validar(sessao);
        Usuario uSol = usuarios.get(sol);
        Usuario up   = usuarios.get(p);
        if (up == null) throw new UsuarioNaoCadastradoException();

        if (up.ehInimigo(sol)) {
            throw new FuncaoInvalidaException(up.getNome() + " � seu inimigo.");
        }
        if (sol.equals(p)) throw new UsuarioNaoPodeSerPaqueraDeSiMesmoException();
        if (uSol.ehPaquera(p)) throw new UsuarioJaEstaPaqueraException();
        uSol.adicionarPaquera(p);
        if (up.ehPaquera(sol)) {
            uSol.receberRecado(up.getNome() + " � seu paquera - Recado do Jackut.");
            up.receberRecado(uSol.getNome() + " � seu paquera - Recado do Jackut.");
        }
    }

    /**
     * Verifica se um usu�rio � paquera de outro.
     *
     * @param chave A chave de sess�o ou o login do usu�rio.
     * @param p O login da pessoa a ser verificada.
     * @return True se os usu�rios forem paqueras, caso contr�rio, false.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
     */
    public boolean ehPaquera(String chave, String p) {
        String login;
        if (sessoes.containsKey(chave)) {
            login = validar(chave);
        } else {
            login = chave;
            if (!usuarios.containsKey(login)) throw new UsuarioNaoCadastradoException();
        }
        return usuarios.get(login).ehPaquera(p);
    }

    /**
     * Retorna a lista de paqueras de um usu�rio.
     *
     * @param chave A chave de sess�o ou o login do usu�rio.
     * @return O conjunto de paqueras do usu�rio.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
     */
    public Set<String> getPaqueras(String chave) {
        String login;
        if (sessoes.containsKey(chave)) {
            login = validar(chave);
        } else {
            login = chave;
            if (!usuarios.containsKey(login)) throw new UsuarioNaoCadastradoException();
        }
        return usuarios.get(login).getPaqueras();
    }

    // ** Inimizades **

    /**
     * Adiciona um inimigo a um usu�rio.
     *
     * @param sessao A sess�o do usu�rio.
     * @param inimigo O login do inimigo.
     * @throws UsuarioNaoPodeSerInimigoDeSiMesmoException Se o usu�rio tentar ser inimigo de si mesmo.
     * @throws UsuarioNaoCadastradoException Se o inimigo n�o estiver cadastrado.
     * @throws UsuarioJaEstaInimigoException Se j� existe uma inimigo.
     */
    public void adicionarInimigo(String sessao, String inimigo) {
        String sol = validar(sessao);
        Usuario uSol = usuarios.get(sol);
        Usuario ui   = usuarios.get(inimigo);
        if (ui == null) throw new UsuarioNaoCadastradoException();
        if (sol.equals(inimigo)) throw new UsuarioNaoPodeSerInimigoDeSiMesmoException();
        if (uSol.ehInimigo(inimigo)) throw new UsuarioJaEstaInimigoException();
        uSol.adicionarInimigo(inimigo);
    }

    // ** Remo��o de conta **

    /**
     * Remove um usu�rio do sistema, excluindo-o juntamente com suas sess�es e dados.
     *
     * @param sessao A sess�o do usu�rio a ser removido.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
     */
    public void removerUsuario(String sessao) {
        String lg = validar(sessao);

        // 1) remove usu�rio e suas sess�es
        usuarios.remove(lg);
        sessoes.entrySet().removeIf(e -> e.getValue().equals(lg));

        // 2) remove comunidades de que era dono
        comunidades.entrySet().removeIf(en -> en.getValue().getDono().equals(lg));

        // 3) limpa membros e hist�rico de comunidadesParticipando
        Collection<String> existentes = comunidades.keySet();
        for (Usuario u : usuarios.values()) {
            u.getAmigos().remove(lg);
            u.getPaqueras().remove(lg);
            u.limparRecados();
            u.limparMensagens();
            u.limparComunidadesParticipando(existentes);
        }
    }

    /**
     * Valida a sess�o de um usu�rio.
     *
     * @param sessao A sess�o a ser validada.
     * @return O login do usu�rio.
     * @throws UsuarioNaoCadastradoException Se a sess�o n�o for v�lida.
     */
    private String validar(String sessao) {
        String lg = sessoes.get(sessao);
        if (lg == null) throw new UsuarioNaoCadastradoException();
        return lg;
    }

    /**
     * Salva os dados do sistema no arquivo.
     */
    private void salvarDados() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQ))) {
            out.writeObject(this);
        } catch(Exception e) { e.printStackTrace(); }
    }

    /**
     * Carrega os dados do sistema a partir do arquivo.
     *
     * @throws Exception Se houver falha ao carregar os dados.
     */
    private void carregarDados() throws Exception {
        File f = new File(ARQ);
        if (!f.exists()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            Jackut p = (Jackut) in.readObject();
            this.usuarios    = p.usuarios;
            this.comunidades = p.comunidades;
        }
    }

}
