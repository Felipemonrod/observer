package padrao.observer;

import java.util.ArrayList;
import java.util.List;

public class Usuario implements Observer {

    private String nome;
    private List<String> generosInteresse;
    private boolean receberNotificacoes;

    public Usuario(String nome, List<String> generosInteresse, boolean receberNotificacoes) {
        this.nome = nome;
        this.generosInteresse = new ArrayList<>(generosInteresse);
        this.receberNotificacoes = receberNotificacoes;
    }

    @Override
    public void update(String tituloConteudo, String genero) {
        if (receberNotificacoes && generosInteresse.contains(genero)) {
            System.out.println("[Notificacao para " + nome + "] Novo titulo disponivel: \""
                    + tituloConteudo + "\" (genero: " + genero + ")");
        }
    }

    public void inscreverGenero(String genero) {
        if (!generosInteresse.contains(genero)) {
            generosInteresse.add(genero);
        }
    }

    public void cancelarInscricaoGenero(String genero) {
        generosInteresse.remove(genero);
    }

    public void ativarNotificacoes() {
        this.receberNotificacoes = true;
    }

    public void desativarNotificacoes() {
        this.receberNotificacoes = false;
    }

    public String getNome() {
        return nome;
    }

}
