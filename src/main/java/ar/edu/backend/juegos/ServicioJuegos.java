package ar.edu.backend.juegos;

import java.util.*;
import java.util.stream.Collectors;

public class ServicioJuegos {
    private final List<JuegoMesa> juegos;

    public ServicioJuegos(List<JuegoMesa> juegos) {
        this.juegos = List.copyOf(juegos);
    }

    public double calcularPromedioRating() {
        return juegos.stream()
                .mapToDouble(JuegoMesa::getRating)
                .average()
                .orElse(0.0);
    }

    public Optional<JuegoMesa> obtenerMasComplejo() {
        return juegos.stream()
                .max(Comparator.comparingDouble(JuegoMesa::getPeso));
    }

    public Map<String, Long> contarJuegosPorCategoria() {
        return juegos.stream()
                .collect(Collectors.groupingBy(JuegoMesa::getCategoria, Collectors.counting()));
    }

    public List<JuegoMesa> filtrarPorJugadores(int cantidad) {
        return juegos.stream()
                .filter(j -> cantidad >= j.getMinJugadores() && cantidad <= j.getMaxJugadores())
                .collect(Collectors.toList());
    }

    // se suman las puntuaciones
    public double calcularSumaPuntuacionAjustada() {
        return juegos.stream()
                .mapToDouble(JuegoMesa::getPuntuacionAjustada)
                .sum();
    }

    // se cuentan los juegos x tipo
    // otra fomra de hacer este metodo seria q en JuegoMesa tengamos un
    // getTipo y devuelva "Normal" y en JuegoCooperativo el override de
    // getTipo devuelva "Cooperativo", entonces en el collect se hace
    // Collectors.groupingBy(Juego::getTipo, Collectors.counting())
    public Map<String, Long> contarJuegosPorTipo() {
        return juegos.stream()
                .collect(Collectors.groupingBy(
                        j -> (j instanceof JuegoCooperativo) ? "Cooperativo" : "Normal",
                        Collectors.counting()));
    }

    // Invirti argumentos para que coincidan con el string ese :/ los habia puesto al revez
    public String panorama() {
        return String.format(Locale.US, "Panorama: %d juegos | Total Puntos: %.2f | Por Tipo: %s | Categorías: %s",
                juegos.size(),
                calcularSumaPuntuacionAjustada(),
                new java.util.TreeMap<>(contarJuegosPorTipo()),
                new java.util.TreeMap<>(contarJuegosPorCategoria()));
    }
}
