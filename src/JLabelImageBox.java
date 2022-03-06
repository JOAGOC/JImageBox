
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

public class JLabelImageBox extends javax.swing.JLabel implements java.io.Serializable {

    private double ratio;
    private javax.swing.ImageIcon imageIcon;
    private static java.awt.event.ComponentListener l;

    public javax.swing.ImageIcon getImageIcon() {
        return imageIcon;
    }

    public static void inicializarListener() {
        if (l == null) {
            l = new java.awt.event.ComponentListener() {
                @Override
                public void componentResized(java.awt.event.ComponentEvent e) {
                    JLabelImageBox t = (JLabelImageBox) (e.getComponent());
                    if (t.getImageIcon() != null) {
                        try {
                            int width, height;
                            if (t.getRatio() >= 1) {//Si el ratio es >1 la relación de aspecto es horizontal
                                if (t.getHeight() < t.getWidth() / t.getRatio()) {//Se toma la altura del componente
                                    height = t.getHeight();
                                    width = (int) (height * t.getRatio());
                                } else {//Se toma el ancho del componente
                                    width = t.getWidth();
                                    height = (int) (width / t.getRatio());
                                }
                            } else {
                                /*/Si el ratio es <1 la relación es vertical, por lo que siempre asigna las medidas con base en
                        la menor medida del componente*/
                                if (t.getWidth() < t.getHeight() / t.getRatio()) { //Medida menor es el ancho del componente se toma dicha medida
                                    width = t.getWidth();
                                    height = (int) (width / t.getRatio());
                                } else { //Se toma la altura del componente
                                    height = t.getHeight();
                                    width = (int) (height * t.getRatio());
                                }
                            }
                            t.setIcon(new javax.swing.ImageIcon(t.getImageIcon().getImage().getScaledInstance(width, height, 4)));
                            //Colocar la nueva imágen redimensionada en base a la imágen original
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @Override
                public void componentShown(java.awt.event.ComponentEvent e) {
                }

                @Override
                public void componentMoved(java.awt.event.ComponentEvent e) {
                }

                @Override
                public void componentHidden(java.awt.event.ComponentEvent e) {
                }
            };
        }
    }

    public JLabelImageBox() {
        super();
        inicializarListener();
        setHorizontalAlignment(CENTER);
        addComponentListener(l);
    }

    @Override
    public void setIcon(javax.swing.Icon icon) {
        if (icon == null) {
            ratio = 0;
        } else {
            guardarDatosIniciales(icon);
        }
        super.setIcon(icon);
    }

    private void guardarDatosIniciales(javax.swing.Icon icon) {
        if (ratio == 0) {
            ratio = (double) icon.getIconWidth() / (double) icon.getIconHeight();
            if (icon instanceof javax.swing.ImageIcon) {
                try {
                    String xd = ((javax.swing.ImageIcon) icon).getDescription().substring(5);
                    imageIcon = new javax.swing.ImageIcon(java.awt.Toolkit.getDefaultToolkit().createImage(xd));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //Obtener el ratio del ícono
    }

    public double getRatio() {
        return ratio;
    }
}
