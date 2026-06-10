package padrao.observer;

import java.util.ArrayList;
import java.util.List;

public class Plataforma implements Subject {

    private List<Observer> observers = new ArrayList<>();
    private String tituloConteudo;
    private String genero;

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(tituloConteudo, genero);
        }
    }

    public void adicionarConteudo(String tituloConteudo, String genero) {
        this.tituloConteudo = tituloConteudo;
        this.genero = genero;
        notifyObservers();
    }

    public String getTituloConteudo() {
        return tituloConteudo;
    }

    public String getGenero() {
        return genero;
    }

}
