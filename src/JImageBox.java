public class JImageBox extends javax.swing.JLabel implements java.io.Serializable {

    private static java.awt.event.ComponentListener l;
    private double ratio;
    private java.awt.Image imagen;

    public java.awt.Image getImagen() {
        return imagen;
    }

    public static void inicializarListener() {
        if (l == null) {
            l = new java.awt.event.ComponentListener() {
                @Override
                public void componentResized(java.awt.event.ComponentEvent e) {
                    JImageBox t = (JImageBox) (e.getComponent());
                    if (t.getIcon() != null) {
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
                            if (width == 0 || height == 0)
                                return;
                            //Evitar que el tamaño sea argumento ilegal
                            t.setIcon(new javax.swing.ImageIcon(t.getImagen().getScaledInstance(width, height, 4)));
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

    public JImageBox() {
        super();
        inicializarListener();
        setHorizontalAlignment(CENTER);
        addComponentListener(l);
    }

    @Override
    public void setIcon(javax.swing.Icon icon) {
        if (icon != null){
            if (imagen == null){
                ratio = (double) icon.getIconWidth() / (double) icon.getIconHeight();
                imagen = ((javax.swing.ImageIcon) icon).getImage();
            }
        } else
            imagen = null;
        super.setIcon(icon);
    }

    public double getRatio() {
        return ratio;
    }
}