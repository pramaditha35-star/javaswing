package com.ticketapp.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIHelper {
    // Traveloka Brand Color Palette
    public static final Color COLOR_PRIMARY = new Color(1, 148, 243);       // Traveloka Blue
    public static final Color COLOR_PRIMARY_DARK = new Color(1, 120, 199);  // Deep Traveloka Blue
    public static final Color COLOR_SECONDARY = new Color(255, 94, 31);     // Traveloka CTA Orange
    public static final Color COLOR_SECONDARY_HOVER = new Color(224, 79, 26); // Darker Orange
    public static final Color COLOR_ACCENT = new Color(1, 148, 243);        // Primary Blue
    public static final Color COLOR_ACCENT_HOVER = new Color(1, 120, 199);   // Hover Blue
    public static final Color COLOR_BACKGROUND = new Color(247, 249, 250);  // Traveloka Gray-Blue Bg
    public static final Color COLOR_CARD_BG = Color.WHITE;
    public static final Color COLOR_TEXT_DARK = new Color(42, 42, 42);       // Soft dark charcoal
    public static final Color COLOR_TEXT_LIGHT = new Color(104, 113, 118);   // Muted Traveloka slate
    public static final Color COLOR_BORDER = new Color(225, 232, 235);       // Soft border gray

    // Semantic Colors
    public static final Color COLOR_SUCCESS = new Color(0, 210, 131);      // Traveloka green success
    public static final Color COLOR_DANGER = new Color(242, 53, 53);        // Traveloka red danger
    public static final Color COLOR_DANGER_HOVER = new Color(210, 40, 40);
    public static final Color COLOR_WARNING = new Color(255, 184, 0);       // Traveloka yellow warning

    // Fonts
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BODY_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_SMALL_BOLD = new Font("Segoe UI", Font.BOLD, 12);

    /**
     * Styles a standard JTextField with clean borders, padding, and focus highlight.
     */
    public static void styleTextField(JTextField field) {
        field.setFont(FONT_BODY);
        field.setForeground(COLOR_TEXT_DARK);
        field.setBackground(Color.WHITE);
        field.setCaretColor(COLOR_PRIMARY);

        Border normalBorder = BorderFactory.createCompoundBorder(
                new RoundedBorder(10, COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );
        Border focusBorder = BorderFactory.createCompoundBorder(
                new RoundedBorder(10, COLOR_PRIMARY, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );

        field.setBorder(normalBorder);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(focusBorder);
            }

            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(normalBorder);
            }
        });
    }

    /**
     * Styles a JPasswordField.
     */
    public static void stylePasswordField(JPasswordField field) {
        field.setFont(FONT_BODY);
        field.setForeground(COLOR_TEXT_DARK);
        field.setBackground(Color.WHITE);
        field.setCaretColor(COLOR_PRIMARY);

        Border normalBorder = BorderFactory.createCompoundBorder(
                new RoundedBorder(10, COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );
        Border focusBorder = BorderFactory.createCompoundBorder(
                new RoundedBorder(10, COLOR_PRIMARY, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );

        field.setBorder(normalBorder);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(focusBorder);
            }

            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(normalBorder);
            }
        });
    }

    /**
     * Styles a JComboBox.
     */
    public static void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(FONT_BODY);
        comboBox.setForeground(COLOR_TEXT_DARK);
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10, COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
    }

    /**
     * Legacy button styling helper.
     */
    public static void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setFont(FONT_BODY_BOLD);
        button.setForeground(fgColor);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (bgColor.equals(COLOR_ACCENT)) {
                    button.setBackground(COLOR_ACCENT_HOVER);
                } else if (bgColor.equals(COLOR_DANGER)) {
                    button.setBackground(COLOR_DANGER_HOVER);
                } else {
                    button.setBackground(bgColor.brighter());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    /**
     * Styles a standard JTable with padding, custom header, and row highlights.
     * Uses flat modern light-themed header rendering.
     */
    public static void styleTable(JTable table) {
        table.setFont(FONT_BODY);
        table.setRowHeight(36);
        table.setShowGrid(true);
        table.setGridColor(COLOR_BORDER);
        table.setSelectionBackground(new Color(232, 244, 255)); // Traveloka light selection
        table.setSelectionForeground(COLOR_TEXT_DARK);
        table.setFillsViewportHeight(true);

        // Header Styling
        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_BODY_BOLD);
        header.setBackground(new Color(227, 242, 253)); // Light blue header
        header.setForeground(new Color(13, 71, 161));      // Dark blue text
        header.setPreferredSize(new Dimension(header.getWidth(), 38));
        header.setReorderingAllowed(false);

        // Apply clean flat renderer for table header
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                setFont(FONT_BODY_BOLD);
                setBackground(new Color(227, 242, 253)); // Light blue header
                setForeground(new Color(13, 71, 161));      // Dark blue text
                setHorizontalAlignment(SwingConstants.LEFT);
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER),
                        BorderFactory.createEmptyBorder(0, 12, 0, 12)
                ));
                return this;
            }
        });

        // Center aligned column values or custom paddings
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                setBorder(new EmptyBorder(0, 12, 0, 12)); // Add horizontal padding
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                }
                return c;
            }
        };
        table.setDefaultRenderer(Object.class, cellRenderer);
        table.setDefaultRenderer(Number.class, cellRenderer);
    }

    /**
     * Styles a JScrollPane with thin modern scrollbars and custom bounds.
     */
    public static void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER, 1));
        scrollPane.setBackground(COLOR_CARD_BG);
        scrollPane.getViewport().setBackground(COLOR_CARD_BG);
        
        // Style vertical scrollbar
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getVerticalScrollBar().setUnitIncrement(14); // smooth scroll
        
        // Style horizontal scrollbar
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 8));
        scrollPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUnitIncrement(14);
    }

    /**
     * Custom Scrollbar UI implementation for thin modern style.
     */
    public static class ModernScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }
        
        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }
        
        private JButton createZeroButton() {
            JButton jb = new JButton();
            jb.setPreferredSize(new Dimension(0, 0));
            jb.setMinimumSize(new Dimension(0, 0));
            jb.setMaximumSize(new Dimension(0, 0));
            return jb;
        }
        
        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            g.setColor(new Color(248, 250, 252)); // Slate 50 track background
            g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        }
        
        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(203, 213, 225)); // Slate 300 thumb color
            
            // Paint thin rounded thumb bar
            g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, 
                             thumbBounds.width - 4, thumbBounds.height - 4, 
                             4, 4);
            g2.dispose();
        }
    }

    /**
     * Custom Border with Rounded Corners.
     */
    public static class RoundedBorder implements Border {
        private final int radius;
        private final Color color;
        private final int thickness;

        public RoundedBorder(int radius, Color color, int thickness) {
            this.radius = radius;
            this.color = color;
            this.thickness = thickness;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(thickness + 1, thickness + 4, thickness + 1, thickness + 4);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x + thickness / 2, y + thickness / 2, width - thickness, height - thickness, radius, radius);
            g2.dispose();
        }
    }

    /**
     * Custom Panel with Linear/Diagonal Gradient Background.
     */
    public static class GradientPanel extends JPanel {
        private final Color colorStart;
        private final Color colorEnd;
        private final boolean diagonal;

        public GradientPanel(Color colorStart, Color colorEnd, boolean diagonal) {
            this.colorStart = colorStart;
            this.colorEnd = colorEnd;
            this.diagonal = diagonal;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            GradientPaint gp;
            if (diagonal) {
                gp = new GradientPaint(0, 0, colorStart, getWidth(), getHeight(), colorEnd);
            } else {
                gp = new GradientPaint(0, 0, colorStart, 0, getHeight(), colorEnd);
            }

            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }

    /**
     * Custom Card Panel with Rounded Corners.
     */
    public static class RoundedPanel extends JPanel {
        private final int cornerRadius;
        private final Color backgroundColor;
        private final Color borderColor;
        private final int borderThickness;

        public RoundedPanel(int radius, Color bg) {
            this(radius, bg, null, 0);
        }

        public RoundedPanel(int radius, Color bg, Color border, int thickness) {
            this.cornerRadius = radius;
            this.backgroundColor = bg;
            this.borderColor = border;
            this.borderThickness = thickness;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw shadow/background
            g2.setColor(backgroundColor != null ? backgroundColor : getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

            // Draw border if requested
            if (borderColor != null && borderThickness > 0) {
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(borderThickness));
                g2.drawRoundRect(borderThickness / 2, borderThickness / 2,
                        getWidth() - borderThickness, getHeight() - borderThickness,
                        cornerRadius, cornerRadius);
            }
            g2.dispose();
        }
    }

    /**
     * Custom Rounded Button.
     */
    public static class RoundedButton extends JButton {
        private final Color baseColor;
        private final Color hoverColor;
        private final Color pressedColor;
        private final int radius;

        public RoundedButton(String text, Color baseColor, Color hoverColor, int radius) {
            super(text);
            this.baseColor = baseColor;
            this.hoverColor = hoverColor;
            this.pressedColor = baseColor.darker();
            this.radius = radius;
            
            setFont(FONT_BODY_BOLD);
            setForeground(Color.WHITE);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            ButtonModel model = getModel();
            if (model.isPressed()) {
                g2.setColor(pressedColor);
            } else if (model.isRollover()) {
                g2.setColor(hoverColor);
            } else {
                g2.setColor(baseColor);
            }
            
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * Custom JTextField that displays placeholder text when empty.
     */
    public static class PlaceholderTextField extends JTextField {
        private final String placeholder;

        public PlaceholderTextField(String placeholder) {
            this.placeholder = placeholder;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_TEXT_LIGHT);
                g2.setFont(getFont());
                Insets insets = getInsets();
                FontMetrics fm = g2.getFontMetrics();
                int x = insets.left;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(placeholder, x, y);
                g2.dispose();
            }
        }
    }

    /**
     * Custom JPasswordField that displays placeholder text when empty.
     */
    public static class PlaceholderPasswordField extends JPasswordField {
        private final String placeholder;

        public PlaceholderPasswordField(String placeholder) {
            this.placeholder = placeholder;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getPassword().length == 0) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_TEXT_LIGHT);
                g2.setFont(getFont());
                Insets insets = getInsets();
                FontMetrics fm = g2.getFontMetrics();
                int x = insets.left;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(placeholder, x, y);
                g2.dispose();
            }
        }
    }
}
