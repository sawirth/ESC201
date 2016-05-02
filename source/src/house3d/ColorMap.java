package house3d;

import java.awt.*;

public class ColorMap {

    public static Color getRainbowColor(double colorValue) {
        int r = 0;
        int g = 0;
        int b = 0;

        double cv = colorValue;
        //TODO implement rainbow color map code


        if (cv > 1 && cv < 0) {
            return Color.BLACK;
        }

        if (cv < 0.2) {
            r = 255;
            g = (int) (255 * cv);
            b = 0;
        } else if (cv >= 0.2 && cv < 0.4) {
            r = (int) (255 * cv);
            g = 255;
            b = 0;
        } else if (cv >= 0.4 && cv < 0.6) {
            r = 0;
            g = 255;
            b = (int) (255 * cv);
        } else if (cv >= 0.6 && cv < 0.8) {
            r = 0;
            g = (int) (255 * cv);
            b = 255;
        } else if (cv >= 0.8) {
            r = (int) (255 * cv);
            g = 0;
            b = 255;
        } else {
            return Color.BLACK;
        }

        return Color.BLACK;
    }
}
