package org.example;

import java.util.*;
import static java.util.Objects.isNull;

/**
 * por: Luis Alejandro Leon
 * https://www.codingame.com/ide/puzzle/paper-labyrinth
 * 04/05/2024
 */


public class Main {

    public static void main(String args[]) {
//        Scanner in = new Scanner(System.in);
//        Coordenada inicio = new Coordenada(in.nextInt(), in.nextInt());
//        Coordenada rabbit = new Coordenada(in.nextInt(), in.nextInt());
//        int w = in.nextInt();
//        int h = in.nextInt();
//        Laberinto laberinto = new Laberinto(w, h);

        long tiempoInicio = System.currentTimeMillis();

        Scanner in = new Scanner(System.in);
        Coordenada inicio = new Coordenada(1, 5);
        Coordenada rabbit = new Coordenada(5, 1);
        //DATOS DEL ULTIMO TEST
        int w = 7;
        int h = 7;
        Laberinto laberinto = Laberinto.obtenerLaberinto();
        laberinto.establecerMapa(w, h);
        String[] filas = {"644444c", "2000008", "2000008", "2000008", "2000008", "2000008", "3111119"};

        for (int i = 0; i < laberinto.alto; i++) {
            laberinto.llenarFila(i, filas[i]); //in.next());
        }

        System.err.println("tamaño: " + w + ", " + h);
        System.err.print("conejo: ");
        System.err.print(rabbit);
        System.err.println("mapa: ");
        laberinto.mostrarMapa();

        //COPIAR DESDE ACA
        //Ubicarnos donde esta Alicia (inicio)
        ArrayList<Camino> caminos = new ArrayList<>();
        ArrayList<Camino> salidas = new ArrayList<>();
        caminos.add(new Camino(inicio, inicio));

        boolean dondeRabbit = false;
        boolean dondeAlicia = false;
        boolean meta = false;
        int pasosConejo = 0;
        int pasos = 0;
        do {
            salidas.clear();
            pasos++;
            System.err.println("numero de pasos: " + pasos);
            for (Camino camino: caminos) {
                laberinto.visitar(camino.actual);
                for (Camino salida : camino.getSalidas()) {
                    salidas.add(salida);
                    if (salida.actual.equals(rabbit) && !dondeRabbit) {
                        System.err.println("Encontre al conejo");
                        dondeRabbit = true;
                        meta = true;
                        pasosConejo = pasos;
                        laberinto.cleanVisitas();
                        salidas.clear();
                        salidas.add(new Camino(salida.actual, salida.actual));
                        break;
                    }
                    if (salida.actual.equals(inicio) && dondeRabbit) {
                        System.err.println("Regrese");
                        dondeAlicia = true;
                        meta = true;
                        break;
                    }
                }
                if (meta && (dondeRabbit || dondeAlicia)) {
                    meta = false;
                    break;
                }
            }
            caminos.clear();
            caminos.addAll(salidas);
        } while (!(dondeRabbit && dondeAlicia));

        System.out.println(pasosConejo + " " + (pasos - pasosConejo));

        long tiempoFin = System.currentTimeMillis();
        long tiempoTotal = tiempoFin - tiempoInicio;
        System.err.println("Tiempo de ejecución: " + tiempoTotal + " milisegundos");

    }

}

class Coordenada {
    public int coorX;
    public int coorY;

    public Coordenada(int coorX, int coorY) {
        this.coorX = coorX;
        this.coorY = coorY;
    }

    @Override
    public String toString() {
        return "Coordenada{" + "X=" + coorX + ", Y=" + coorY + '}';
    }

    public boolean equals(Coordenada otra) {
        return coorX == otra.coorX && coorY == otra.coorY;
    }

    public Coordenada mover(String direccion) {
        Laberinto laberinto = Laberinto.obtenerLaberinto();
        Coordenada proxPaso = new Coordenada(this.coorX, this.coorY);
        switch (direccion){
            case "abajo": //abajo
                proxPaso.coorY = this.coorY+1;
                break;
            case "izquierda": //izquierda
                proxPaso.coorX = this.coorX-1;
                break;
            case "arriba": //arriba
                proxPaso.coorY = this.coorY-1;
                break;
            case "derecha": //derecha
                proxPaso.coorX = this.coorX+1;
                break;
        }
        if (proxPaso.coorX<0 || proxPaso.coorX==laberinto.ancho || proxPaso.coorY<0 || proxPaso.coorY == laberinto.alto) {
            return null;
        }
        return proxPaso;
    }

}

class EstadoMapa {
    public int valor;
    public boolean visitado;

    public EstadoMapa(int valor) {
        this.valor = valor;
        this.visitado = false;
    }
    public void marcarVisitada() {
        this.visitado = true;
    }
}
class Laberinto {
    public int ancho;
    public int alto;
    public HashMap<String, EstadoMapa> mapa2;



    private static Laberinto laberinto;

    public static Laberinto obtenerLaberinto() {
        if (laberinto == null) {
            laberinto = new Laberinto();
        }
        return laberinto;
    }

    private Laberinto() {
        this.ancho = 0;
        this.alto = 0;
        this.mapa2 = new HashMap<>();
    }

    public void establecerMapa(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
    }

    public void llenarFila(int numeroFila, String data) {
        for (int i=0; i<this.ancho; i++) {
            int valor = ((int) data.charAt(i)) - 48;
            if (valor > 9) { valor -= 39; }
            this.mapa2.put(getKey(i, numeroFila), new EstadoMapa(valor));
        }
    }

    public int getValor(Coordenada coordenada) {
        return this.mapa2.get(getKey(coordenada)).valor;
    }
    public boolean isVisitado(Coordenada coordenada) {
        return this.mapa2.get(getKey(coordenada)).visitado;
    }
    public void visitar(Coordenada coordenada) {
        String key = getKey(coordenada);
        this.mapa2.get(key).marcarVisitada();
    }
    public void cleanVisitas() {
        for (EstadoMapa estado: mapa2.values()) {
            estado.visitado = false;
        }
    }

    public void mostrarMapa() {
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                System.err.print( this.mapa2.get(getKey(x, y)).valor );
            }
            System.err.println();
        }
    }
    public static String getKey(int x, int y) {
        return (x + ", " + y);
    }
    public static String getKey(Coordenada coordenada) {
        return (coordenada.coorX + ", " + coordenada.coorY);
    }

}

class Camino {
    public Coordenada actual;
    public Coordenada anterior;

    public Camino(Coordenada actual, Coordenada anterior) {
        this.actual = actual;
        this.anterior = anterior;
    }

    public ArrayList<Camino> getSalidas() {
        Laberinto laberinto = Laberinto.obtenerLaberinto();
        ArrayList<Camino> salidas = new ArrayList<>();


        String[] direcciones = {"abajo", "izquierda", "arriba", "derecha"};
        int[] paredes = {0b0001, 0b0010, 0b0100, 0b1000};
//        System.err.print("Revisando la pos: " + actual.coorX +", "+ actual.coorY + ". Valor: " + laberinto.getValor(actual));

        for (int i=0; i<4; i++){
//            System.err.print( ", " + direcciones[i] + " " + ((laberinto.getValor(actual) & paredes[i]) == 0));
            if ((laberinto.getValor(actual) & paredes[i]) == 0) {
                Coordenada posibleSalida = this.actual.mover(direcciones[i]);
                if (
                    (!posibleSalida.equals(this.anterior)) &&
                    !isNull(posibleSalida) &&
                    !laberinto.isVisitado(posibleSalida)
                ) {
                    salidas.add(new Camino(posibleSalida, this.actual));
                }
            }
        }
//        System.err.println();

        return salidas;

    }


    @Override
    public String toString() {
        return "Camino{" + "actual=" + actual + ", anterior=" + anterior + '}';
    }
}