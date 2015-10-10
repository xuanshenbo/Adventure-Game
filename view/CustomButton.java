package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalButtonUI;

public class CustomButton extends JButton{
    private JButton button;

    public CustomButton(String msg) {
    	this.button = new JButton(msg);
        Border line, raisedbevel, loweredbevel, title, empty;
        line = BorderFactory.createLineBorder(Color.black);
        raisedbevel = BorderFactory.createRaisedBevelBorder();
        loweredbevel = BorderFactory.createLoweredBevelBorder();
        title = BorderFactory.createTitledBorder("");
        empty = BorderFactory.createEmptyBorder(1, 1, 1, 1);
        final Border compound;
        Color crl = (Color.blue);
        compound = BorderFactory.createCompoundBorder(empty, new OldRoundedBorderLine(crl));
        Color crl1 = (Color.red);
        final Border compound1;
        compound1 = BorderFactory.createCompoundBorder(empty, new OldRoundedBorderLine(crl1));
        Color crl2 = (Color.black);
        final Border compound2;
        compound2 = BorderFactory.createCompoundBorder(empty, new OldRoundedBorderLine(crl2));
        button.setFont(new Font("Serif", Font.BOLD, 14));
        button.setForeground(Color.darkGray);
        button.setPreferredSize(new Dimension(50, 30));
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setBorder(compound);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setUI(new ModifButtonUI());

        button.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isRollover()) {
                    button.setBorder(compound1);
                } else {
                    button.setBorder(compound);
                }
                if (model.isPressed()) {
                    button.setBorder(compound2);
                }
            }
        });
    }


}

class OldRoundedBorderLine extends AbstractBorder {

    private final static int MARGIN = 5;
    private static final long serialVersionUID = 1L;
    private Color color;

    OldRoundedBorderLine(Color clr) {
        color = clr;
    }

    public void setColor(Color clr) {
        color = clr;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        ((Graphics2D) g).setRenderingHint(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.drawRoundRect(x, y, width, height, MARGIN, MARGIN);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = MARGIN;
        insets.top = MARGIN;
        insets.right = MARGIN;
        insets.bottom = MARGIN;
        return insets;
    }
}

class ModifButtonUI extends MetalButtonUI {

    private static final ModifButtonUI buttonUI = new ModifButtonUI();

    ModifButtonUI() {
    }

    public static ComponentUI createUI(JComponent c) {
        return new ModifButtonUI();
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        final Color color1 = new Color(230, 255, 255, 0);
        final Color color2 = new Color(255, 230, 255, 64);
        final Color alphaColor = new Color(200, 200, 230, 64);
        final Color color3 = new Color(
            alphaColor.getRed(), alphaColor.getGreen(), alphaColor.getBlue(), 0);
        final Color color4 = new Color(
            alphaColor.getRed(), alphaColor.getGreen(), alphaColor.getBlue(), 64);
        super.paint(g, c);
        Graphics2D g2D = (Graphics2D) g;
        GradientPaint gradient1 = new GradientPaint(
            0.0F, (float) c.getHeight() / (float) 2, color1, 0.0F, 0.0F, color2);
        Rectangle rec1 = new Rectangle(0, 0, c.getWidth(), c.getHeight() / 2);
        g2D.setPaint(gradient1);
        g2D.fill(rec1);
        GradientPaint gradient2 = new GradientPaint(
            0.0F, (float) c.getHeight() / (float) 2, color3, 0.0F, c.getHeight(), color4);
        Rectangle rec2 = new Rectangle(0, c.getHeight() / 2, c.getWidth(), c.getHeight());
        g2D.setPaint(gradient2);
        g2D.fill(rec2);
    }

    /*@Override
    public void paintButtonPressed(Graphics g, AbstractButton b) {
        paintText(g, b, b.getBounds(), b.getText());
        g.setColor(Color.red.brighter());
        g.fillRect(0, 0, b.getSize().width, b.getSize().height);
    }
*/
    public void paintBorder(Graphics g) {
    }

    @Override
    protected void paintFocus(Graphics g, AbstractButton b,
        Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
    }
}