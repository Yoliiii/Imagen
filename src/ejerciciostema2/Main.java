package ejerciciostema2;
import java.io.File;
import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException {
        
        File f = new File("Resources/Peñagolosa.bmp");
        
        TransformaImagen ti = new TransformaImagen(f);
        ti.transformaNegativo();
        ti.transformaOscuro();
        ti.transformaEscalaGrises();
        ti.transformaBlancoNegro();
    }
}