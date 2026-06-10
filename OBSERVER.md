# Plataforma de Streaming - Padrao Observer

Simulacao de uma plataforma de video on-demand (estilo Netflix) onde, sempre
que um novo filme/serie e adicionado, os usuarios sao notificados de acordo
com o(s) genero(s) em que se inscreveram e com sua preferencia de receber ou
nao notificacoes.

Arquivos (pacote `padrao.observer`):

- `Observer.java` - interface do observador
- `Subject.java` - interface do sujeito observado
- `Plataforma.java` - ConcreteSubject
- `Usuario.java` - ConcreteObserver
- `Main.java` - simulacao com 5 usuarios

## 1. O padrao Observer

O Observer e um padrao comportamental em que um objeto (o **Subject** /
**sujeito**) mantem uma lista de dependentes (os **Observers**) e os avisa
automaticamente sempre que seu estado muda, chamando um metodo padronizado
(`update`) em cada um deles.

Vantagens nesse cenario:

- A `Plataforma` nao precisa conhecer os detalhes de cada usuario (e-mail,
  push, SMS, preferencias). Ela so sabe que existe "alguem" que implementa
  `Observer` e que pode ser avisado com `update(...)`.
- Novos tipos de observador podem ser criados (ex: um `SistemaEmail`, um
  `SistemaPush`) sem alterar a `Plataforma`.
- A logica de "quero ou nao quero ser avisado, e sobre quais generos" fica
  encapsulada dentro de cada `Usuario` (baixo acoplamento).

## 2. UML (diagrama em texto)

```
        <<interface>>                      <<interface>>
          Subject                            Observer
   --------------------------       --------------------------
   + addObserver(o: Observer)        + update(titulo: String,
   + removeObserver(o: Observer)               genero: String)
   + notifyObservers()
            ^                                    ^
            | implements                         | implements
            |                                     |
   --------------------------       --------------------------
        Plataforma                         Usuario
   --------------------------       --------------------------
   - observers: List<Observer>       - nome: String
   - tituloConteudo: String           - generosInteresse: List<String>
   - genero: String                   - receberNotificacoes: boolean
   --------------------------       --------------------------
   + adicionarConteudo(titulo,        + update(titulo, genero)
       genero)                        + inscreverGenero(genero)
   + getTituloConteudo()              + cancelarInscricaoGenero(genero)
   + getGenero()                      + ativarNotificacoes()
                                       + desativarNotificacoes()

   Plataforma "1" o------> "*" Observer   (associacao via lista de observers)
```

Leitura do diagrama:

- `Plataforma` implementa `Subject` e mantem uma lista de `Observer`.
- `Usuario` implementa `Observer` e guarda seus generos de interesse e se
  quer ou nao receber notificacoes.
- A seta tracejada com `^` representa "implementa a interface".
- A linha com `o------>` representa a composicao/associacao 1-para-muitos
  entre `Plataforma` e os `Observer` cadastrados.

## 3. Funcionamento do programa

1. `Plataforma` guarda uma lista de observadores e o ultimo conteudo
   adicionado (`tituloConteudo` e `genero`).
2. Cada `Usuario` e criado com:
   - um nome;
   - uma lista de generos de interesse (ex: Acao, Comedia, Terror, Anime,
     Ficcao Cientifica);
   - uma flag `receberNotificacoes` (true/false), que representa a decisao
     do usuario de querer ou nao ser avisado.
3. Os usuarios sao registrados na plataforma com `addObserver`.
4. Quando um novo filme/serie e publicado, chama-se
   `plataforma.adicionarConteudo(titulo, genero)`. Esse metodo:
   - atualiza o estado interno (`tituloConteudo` e `genero`);
   - chama automaticamente `notifyObservers()`.
5. `notifyObservers()` percorre a lista de observadores com um `for` simples
   e chama `update(tituloConteudo, genero)` em cada um.
6. Cada `Usuario.update(...)` decide, sozinho, se deve exibir a notificacao:
   - so exibe se `receberNotificacoes == true` **e** o `genero` do conteudo
     estiver na lista `generosInteresse` do usuario.
7. Um usuario pode mudar de ideia em tempo de execucao usando
   `inscreverGenero`, `cancelarInscricaoGenero`, `ativarNotificacoes` e
   `desativarNotificacoes`. Tambem pode se desligar totalmente da plataforma
   chamando `removeObserver`.

## 4. Exemplo de funcionamento (5 usuarios)

Usuarios cadastrados no `Main`:

| Usuario | Generos de interesse                          | Notificacoes ativas? |
|---------|------------------------------------------------|-----------------------|
| Ana     | Acao, Ficcao Cientifica                        | Sim                   |
| Bruno   | Comedia                                         | Sim                   |
| Carla   | Terror, Anime                                   | Sim                   |
| Diego   | Acao, Comedia, Terror, Anime, Ficcao Cientifica | Nao (desligadas)      |
| Elisa   | Anime                                           | Sim                   |

Passo a passo da simulacao:

1. **Lancamento "Duro de Matar 5" (Acao)**
   - Ana e notificada (interesse em Acao + notificacoes ativas).
   - Diego tambem se interessa por Acao, mas como suas notificacoes estao
     desligadas, nao recebe nada.
   - Bruno, Carla e Elisa nao se interessam por Acao -> nada.

2. **Diego ativa as notificacoes (`ativarNotificacoes`) e Bruno passa a se
   interessar tambem por Terror (`inscreverGenero("Terror")`)**
   - Nenhuma notificacao e disparada aqui, apenas mudanca de preferencias.

3. **Lancamento "Invocacao do Mal 4" (Terror)**
   - Bruno (agora interessado em Terror) e notificado.
   - Carla (ja tinha interesse em Terror) e notificada.
   - Diego (agora com notificacoes ativas e interessado em Terror) e
     notificado.
   - Ana e Elisa nao se interessam por Terror -> nada.

4. **Elisa cancela sua assinatura da plataforma (`removeObserver`)**
   - Elisa sai da lista de observadores da `Plataforma`.

5. **Lancamento "Attack on Titan: Temporada Final" (Anime)**
   - Carla e notificada (interesse em Anime).
   - Diego e notificado (interesse em Anime + notificacoes ativas).
   - Elisa **nao** e notificada, mesmo tendo interesse em Anime, pois ja foi
     removida da lista de observadores.

Saida real do programa (`Main.java`):

```
===== Novo lancamento: filme de Acao =====
[Notificacao para Ana] Novo titulo disponivel: "Duro de Matar 5" (genero: Acao)

===== Diego ativa as notificacoes e Bruno passa a se interessar por Terror =====

===== Novo lancamento: filme de Terror =====
[Notificacao para Bruno] Novo titulo disponivel: "Invocacao do Mal 4" (genero: Terror)
[Notificacao para Carla] Novo titulo disponivel: "Invocacao do Mal 4" (genero: Terror)
[Notificacao para Diego] Novo titulo disponivel: "Invocacao do Mal 4" (genero: Terror)

===== Elisa cancela a assinatura da plataforma =====

===== Novo lancamento: serie Anime =====
[Notificacao para Carla] Novo titulo disponivel: "Attack on Titan: Temporada Final" (genero: Anime)
[Notificacao para Diego] Novo titulo disponivel: "Attack on Titan: Temporada Final" (genero: Anime)
```
