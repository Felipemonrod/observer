package padrao.observer;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Plataforma plataforma = new Plataforma();

        Usuario ana = new Usuario("Ana", Arrays.asList("Acao", "Ficcao Cientifica"), true);
        Usuario bruno = new Usuario("Bruno", Arrays.asList("Comedia"), true);
        Usuario carla = new Usuario("Carla", Arrays.asList("Terror", "Anime"), true);
        Usuario diego = new Usuario("Diego",
                Arrays.asList("Acao", "Comedia", "Terror", "Anime", "Ficcao Cientifica"), false);
        Usuario elisa = new Usuario("Elisa", Arrays.asList("Anime"), true);

        plataforma.addObserver(ana);
        plataforma.addObserver(bruno);
        plataforma.addObserver(carla);
        plataforma.addObserver(diego);
        plataforma.addObserver(elisa);

        System.out.println("===== Novo lancamento: filme de Acao =====");
        plataforma.adicionarConteudo("Duro de Matar 5", "Acao");

        System.out.println("\n===== Diego ativa as notificacoes e Bruno passa a se interessar por Terror =====");
        diego.ativarNotificacoes();
        bruno.inscreverGenero("Terror");

        System.out.println("\n===== Novo lancamento: filme de Terror =====");
        plataforma.adicionarConteudo("Invocacao do Mal 4", "Terror");

        System.out.println("\n===== Elisa cancela a assinatura da plataforma =====");
        plataforma.removeObserver(elisa);

        System.out.println("\n===== Novo lancamento: serie Anime =====");
        plataforma.adicionarConteudo("Attack on Titan: Temporada Final", "Anime");
    }

}
