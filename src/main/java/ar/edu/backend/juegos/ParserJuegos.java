package ar.edu.backend.juegos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParserJuegos {
    private static final String SEPARADOR = ";";
    private static final String CABECERA = "id;nombre;anio;min_jugadores;max_jugadores;duracion;rating;peso;categoria";
    // declaro nueva cabecera
    private static final String NUEVA_CABECERA = "id;nombre;anio;min_jugadores;max_jugadores;duracion;rating;peso;categoria;dificultad_cooperativa";

    public ResultadoParseo parsear(String rutaArchivo) {
        List<JuegoMesa> juegos = new ArrayList<>();
        int leidas = 0;
        int procesadas = 0;
        int descartadas = 0;
        int invalidas = 0;

        try (Scanner scanner = new Scanner(new File(rutaArchivo))) {
            // delcaro variable de columnas a esperar
            int columnasEsperadas = 0;

            if (scanner.hasNextLine()) {
                String lineaCabecera = scanner.nextLine();

                // me fijo si coincide la cabecera con algun encabezado y defino columnas esperadas
                if (CABECERA.equals(lineaCabecera)) {
                    columnasEsperadas = 9;
                } else if (NUEVA_CABECERA.equals(lineaCabecera)) {
                    columnasEsperadas = 10;
                } else {
                    throw new IllegalArgumentException("Cabecera incorrecta");
                }
            }

            while (scanner.hasNextLine()) {
                leidas++;
                String linea = scanner.nextLine();
                if (linea.isBlank()) continue;

                try {
                    String[] campos = linea.split(SEPARADOR, -1);
                    // si la longitud no es igual a columnas esperadas, error
                    if (campos.length != columnasEsperadas) {
                        throw new IllegalArgumentException("Cantidad de columnas incorrecta");
                    }
                    
                    JuegoMesa juego = JuegoMesa.desdeCampos(campos);
                    
                    if (juego.getRating() < 1.0) {
                        descartadas++;
                    } else {
                        juegos.add(juego);
                        procesadas++;
                    }
                } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                    invalidas++;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Archivo no encontrado: " + rutaArchivo);
        }
        // agrego esta parte de abajo x el tema de expeciones y errores, xq puede
        // q no lea bien el archivo y tiene q tirar error en ese caso, basically
        // en otros parciales si esta, pero no se xq en este no, creo
        catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) e;
            }
            throw new RuntimeException("Error al leer el archivo");
        }

        return new ResultadoParseo(juegos, leidas, procesadas, descartadas, invalidas);
    }
}
