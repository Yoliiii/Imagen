package ejerciciostema2;

import java.io.*;

public class TransformaImagen {

    File f = null;

    public TransformaImagen(File fEnt) {
        // Control de existencia del fichero y control de la extensión .bmp (sacar
        // mensajes de error)
        if (fEnt.exists() && fEnt.getName().endsWith(".bmp")) { //Si el archivo existe y tiene el nombre que acaba con la extensión bmp, guárdalo
            f = fEnt;
        } else {
            //sino, no existe
            System.out.println("Don't exist");
        }
    }

    public void transformaNegativo() throws IOException {
        //3 bytes es 1 pixel
        // Transformar a negativo y guardar como *_n.bmp
        File imagen = new File("Resources/" + getNombreSinExtension() + "_n.bmp"); //Nueva imagen creada
        InputStream input = new FileInputStream(f); //Primera imagen, lectura
        OutputStream output = new FileOutputStream(imagen); //Segunda imagen, escritura

        /*Lee los primeros 54 bytes y los copia en el otro fichero, metadatos guarda la información de todos los bytes
        Crear una array de bytes para poder guardarlos*/
        byte[] metadatos = input.readNBytes(54);
        //Escríbelo en el de salida
        output.write(metadatos);

        //Recorrer byte a byte e invertirlo
        //Invertir significa que se copiará los píxeles del revés, me pasas el primer píxel y se copia el último 255-0, 255-1...
        int b = input.read(); //Tengo que crear el contenido del archivo que ya había creado, 'imagen'
        while (b != -1) {
            int invertido = 255 - b; //Fórmula que te la tiene que dar, excepto si fuera la escala de grises
            output.write(invertido); //Le estoy pasando los píxeles invertidos al contenido del archivo de escritura
            b = input.read(); //Para que lea la imagen de nuevo "invertida"

        }
        input.close(); //Hay que cerrarlo siempre, para no consumir recursos
        output.close();
    }

    public void transformaOscuro() throws IOException {
        // Transformar a una imagen más oscura y guardar como *_o.bmp
        File imagen = new File("Resources/" + getNombreSinExtension() + "_o.bmp");
        InputStream input = new FileInputStream(f);
        OutputStream output = new FileOutputStream(imagen);

        byte[] FirtBytes = input.readNBytes(54);
        output.write(FirtBytes);

        int b = input.read();
        while (b != -1) {
            int dark = b / 2;
            output.write(dark);
            b = input.read();

        }
        input.close();
        output.close();
    }

    public void transformaEscalaGrises() throws IOException {
        //Los colores tienen 3 canales, r g b.
        //0-255. 0 Negro - 255 Blanco
        /*Para hacer una imagen en escala de grises, hay que hacer una media y ponerlos en el mismo valor. Por ejemplo si tienes un
        canal verde, rojo y azul. Sumas y divides para hacer una media. */
        File imagen = new File("Resources/" + getNombreSinExtension() + "_eg.bmp");
        InputStream input = new FileInputStream(f);
        OutputStream output = new FileOutputStream(imagen);

        byte[] FirstBytes = input.readNBytes(54);
        output.write(FirstBytes);

        //Leer los tres colores
        int red = input.read();
        int green = input.read();
        int blue = input.read();

        //El último que va a leer es el blue
        while (blue != -1) {
            //Hay que hacer la media
            int media = (red + green + blue) / 3;

            output.write(media);
            output.write(media);
            output.write(media);


            red = input.read();
            green = input.read();
            blue = input.read();
        }

        input.close();
        output.close();
    }

    public void transformaBlancoNegro() throws IOException {
        // Transformar a una imagen en blanco y negro y guardar como *_bn.bmp
        File imagen = new File("Resources/" + getNombreSinExtension() + "_bw.bmp");
        InputStream input = new FileInputStream(f);
        OutputStream output = new FileOutputStream(imagen);

        byte[] FirstBytes = input.readNBytes(54);
        output.write(FirstBytes);

        //Leer los tres colores
        int red = input.read();
        int green = input.read();
        int blue = input.read();


        while (blue != -1) {
            int media = (red + green + blue) / 3;
            // int media = ((red + green + blue) /3) < 128 ? 0 : 255;
            int bw = 0;
            if (media > 128) {
                bw = 255;
            }

            output.write(bw);
            output.write(bw);
            output.write(bw);


            red = input.read();
            green = input.read();
            blue = input.read();

        }
        input.close();
        output.close();
    }

    private String getNombreSinExtension() {
        //Devuelve el nombre del archivo f sin extensión
        String archivo = f.getName();
        String[] arrayArchivo = archivo.split("\\.");
        String nombre = arrayArchivo[0]; //Nombre del archivo sin extensión
        return nombre;
    }
}