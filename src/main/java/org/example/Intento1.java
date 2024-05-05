//package org.example;
//
//import java.util.*;
//import static java.util.Objects.isNull;
//
///**
// * Auto-generated code below aims at helping you parse
// * the standard input according to the problem statement.
// int resultadoAND = num1 & num2; // Operación AND (resulta en 2 en decimal o 0010 en binario)
// int resultadoOR = num1 | num2;  // Operación OR (resulta en 14 en decimal o 1110 en binario)
// int resultadoXOR = num1 ^ num2; // Operación XOR (resulta en 12 en decimal o 1100 en binario)
// int resultadoNOT = ~num1;       // Operación NOT (resulta en -11 en decimal o 11111111111111111111111111110101 en binario)
// **/
//
//class Intento1 {
//
//    public static void main(String args[]) {
//
//        long tiempoInicio = System.currentTimeMillis();
//
//        Scanner in = new Scanner(System.in);
////        Coordenada inicio = new Coordenada(in.nextInt(), in.nextInt());
////        Coordenada rabbit = new Coordenada(in.nextInt(), in.nextInt());
////        int w = in.nextInt();
////        int h = in.nextInt();
////        Laberinto laberinto = new Laberinto(w, h);
//
//        Scanner in = new Scanner(System.in);
//        org.example.Coordenada inicio = new org.example.Coordenada(1, 5);
//        org.example.Coordenada rabbit = new org.example.Coordenada(5, 1);
//        int w = 7;
//        int h = 7;
//        org.example.Laberinto laberinto = org.example.Laberinto.obtenerLaberinto();
//        laberinto.establecerMapa(w, h);
//        String[] filas = {"644444c", "2000008", "2000008", "2000008", "2000008", "2000008", "3111119"};
//
//        for (int i = 0; i < laberinto.alto; i++) {
//            laberinto.llenarFila(i, filas[i]); //in.next());
//        }
//
//        //COPIAR DESDE ACA
//
//
//        System.err.println("tamaño: " + w + ", " + h);
//        System.err.print("inicio: ");
//        System.err.println(inicio);
//        System.err.print("conejo: ");
//        System.err.println(rabbit);
//        System.err.println("mapa: ");
//        laberinto.mostrarMapa();
//
//        //Ubicarnos donde esta Alicia (inicio)
//        ArrayList<Camino> caminos = new ArrayList<>();
//        ArrayList<Camino> salidas = new ArrayList<>();
//        caminos.add(new Camino(inicio, inicio));
//
//        boolean dondeRabbit = false;
//        boolean dondeAlicia = false;
//        boolean meta = false;
//        int pasosConejo = 0;
//        int pasos = 0;
//        do {
//            salidas.clear();
//            pasos++;
//            System.err.println("numero de pasos" + pasos);
//            for (Camino camino: caminos) {
//                for (Camino salida : camino.getSalidas()) {
//                    salidas.add(salida);
//                    if (salida.actual.equals(rabbit) && !dondeRabbit) {
//                        System.err.println("Encontre al conejo");
//                        dondeRabbit = true;
//                        meta = true;
//                        pasosConejo = pasos;
//                        salidas.clear();
//                        salidas.add(new Camino(salida.actual, salida.actual));
//                        break;
//                    }
//                    if (salida.actual.equals(inicio) && dondeRabbit) {
//                        System.err.println("Regrese");
//                        dondeAlicia = true;
//                        meta = true;
//                        break;
//                    }
//                }
//                if (meta && (dondeRabbit || dondeAlicia)) {
//                    meta = false;
//                    break;
//                }
//            }
//            caminos.clear();
//            caminos.addAll(salidas);
//        } while (!(dondeRabbit && dondeAlicia));
//
//        System.out.println(pasosConejo + " " + (pasos - pasosConejo));
//
//        long tiempoFin = System.currentTimeMillis();
//        long tiempoTotal = tiempoFin - tiempoInicio;
//        System.err.println("Tiempo de ejecución: " + tiempoTotal + " milisegundos");
//
//    }
//
//}
//
//class Coordenada {
//    public int coorX;
//    public int coorY;
//    public Coordenada(int coorX, int coorY) {
//        this.coorX = coorX;
//        this.coorY = coorY;
//    }
//    public Coordenada() {}
//
//    @Override
//    public String toString() {
//        return "Coordenada{" +
//                "X=" + coorX +
//                ", Y=" + coorY +
//                '}';
//    }
//
//    public boolean equals(Coordenada otra) {
//        return coorX == otra.coorX && coorY == otra.coorY;
//    }
//
//    public Coordenada mover(String direccion) {
//        Laberinto laberinto = Laberinto.obtenerLaberinto();
//        Coordenada proxPaso = new Coordenada(this.coorX, this.coorY);
//        switch (direccion){
//            case "aba": //abajo
//                proxPaso.coorY = this.coorY+1;
//                break;
//            case "izq": //izquierda
//                proxPaso.coorX = this.coorX-1;
//                break;
//            case "arr": //arriba
//                proxPaso.coorY = this.coorY-1;
//                break;
//            case "der": //derecha
//                proxPaso.coorX = this.coorX+1;
//                break;
//        }
//        if (proxPaso.coorX<0 || proxPaso.coorX==laberinto.ancho || proxPaso.coorY<0 || proxPaso.coorY == laberinto.alto) {
//            return null;
//        }
//        return proxPaso;
//    }
//
//}
//
//class Laberinto {
//    public int ancho;
//    public int alto;
//    public int[][] mapa;
//
//    private static Laberinto laberinto;
//
//    public static Laberinto obtenerLaberinto() {
//        if (laberinto == null) {
//            laberinto = new Laberinto();
//        }
//        return laberinto;
//    }
//
//    private Laberinto() {
//        this.ancho = 0;
//        this.alto = 0;
//        this.mapa = new int[0][0];
//    }
//
//    public void establecerMapa(int ancho, int alto) {
//        this.ancho = ancho;
//        this.alto = alto;
//        this.mapa = new int[ancho][alto];
//    }
//
//
//    public void llenarFila(int numeroFila, String data) {
//        for (int i=0; i<this.ancho; i++) {
//            int valor = ((int) data.charAt(i)) - 48;
//            if (valor > 9) { valor -= 39; }
//            this.mapa[i][numeroFila] = valor;
//        }
//    }
//
//    public int getPath(Coordenada coordenada) {
//        return mapa[coordenada.coorX][coordenada.coorY];
//    }
//
//    public void mostrarMapa() {
//        for (int y = 0; y < alto; y++) {
//            for (int x = 0; x < ancho; x++) {
//                System.err.print(mapa[x][y] + " ");
//            }
//            System.err.println();
//        }
//    }
//
//}
//
//class Camino {
//    public Coordenada actual;
//    public Coordenada anterior;
//    private boolean viable = true;
//
//
//    public Camino(Coordenada actual, Coordenada anterior) {
//        this.actual = actual;
//        this.anterior = anterior;
//    }
//
//    public ArrayList<Camino> getSalidas() {
//        Laberinto laberinto = Laberinto.obtenerLaberinto();
//        ArrayList<Camino> salidas = new ArrayList<>();
//        int abajo = 0b0001;
//        int izquierda = 0b0010;
//        int arriba = 0b0100;
//        int derecha = 0b1000;
//        System.err.print("Revisando la pos: " + actual.coorX +", "+ actual.coorY + ". Valor: " + laberinto.getPath(actual));
//        System.err.print( ", abajo" + ((laberinto.getPath(actual) & abajo) == 0));
//        System.err.print( ", izquierda" + ((laberinto.getPath(actual) & izquierda) == 0));
//        System.err.print( ", arriba" + ((laberinto.getPath(actual) & arriba) == 0));
//        System.err.println( ", derecha" + ((laberinto.getPath(actual) & derecha) == 0));
//
//        if ((laberinto.getPath(actual) & abajo) == 0) {
//            Coordenada posibleSalida = this.actual.mover("aba");
//            if ( (!posibleSalida.equals(this.anterior)) && !isNull(posibleSalida) ) {
//                salidas.add(new Camino(posibleSalida, this.actual));
//            }
//
//        }
//        if ((laberinto.getPath(actual) & izquierda) == 0) {
//            Coordenada posibleSalida = this.actual.mover("izq");
//            if ( (!posibleSalida.equals(this.anterior)) && !isNull(posibleSalida) ) {
//                salidas.add(new Camino(posibleSalida, this.actual));
//            }
//
//        }
//        if ((laberinto.getPath(actual) & arriba) == 0) {
//            Coordenada posibleSalida = this.actual.mover("arr");
//            if ( (!posibleSalida.equals(this.anterior)) && !isNull(posibleSalida) ) {
//                salidas.add(new Camino(posibleSalida, this.actual));
//            }
//
//        }
//        if ((laberinto.getPath(actual) & derecha) == 0) {
//            Coordenada posibleSalida = this.actual.mover("der");
//            if ( (!posibleSalida.equals(this.anterior)) && !isNull(posibleSalida) ) {
//                salidas.add(new Camino(posibleSalida, this.actual));
//            }
//        }
//
//        return salidas;
//
//    }
//
//
//    @Override
//    public String toString() {
//        return "Camino{" +
//                "actual=" + actual +
//                ", anterior=" + anterior +
//                '}';
//    }
//}