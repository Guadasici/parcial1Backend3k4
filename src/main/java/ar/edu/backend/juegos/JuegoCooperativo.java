package ar.edu.backend.juegos;
// tiene herencia este ejercicio, si el juego es cooperativo debo de definir su dificultad

public class JuegoCooperativo extends JuegoMesa {
    private final int dificultadCooperativa;

    public JuegoCooperativo(String id, String nombre, int anio, int minJugadores, int maxJugadores, int duracion, double rating, double peso, String categoria, int dificultadCooperativa) {
        super(id, nombre, anio, minJugadores, maxJugadores, duracion, rating, peso, categoria);

        // verificar q dificultad este en los rangos correctos
        if (dificultadCooperativa < 1 || dificultadCooperativa > 10) {
            throw new IllegalArgumentException("El rango de la dificultad es invalido");
        }

        this.dificultadCooperativa = dificultadCooperativa;
    }

    public int getDificultadCooperativa() {
        return dificultadCooperativa;
    }

    // La puntacion hay q redefinirla
    @Override
    public double getPuntuacionAjustada() {
        return super.getPuntuacionAjustada()+(dificultadCooperativa / 2.0);
    }
}
