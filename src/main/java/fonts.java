import java.awt.*;

public class fonts  {
   Font font = new Font("Times New Roman",Font.BOLD,15);
    fonts()
    {

    }

    Font smallFont()
    {
        font = new Font("Times New Roman",Font.PLAIN,12);
        return font;
    }
    Font smallLabelFont()
    {
        font = new Font("Times New Roman",Font.PLAIN,15);
        return font;
    }
    Font largeFont()
    {
        font = new Font("Times New Roman",Font.BOLD,20);
        return font;
    }
    Font largeLabelFont()
    {
        font = new Font("Times New Roman",Font.BOLD,30);
        return font;
    }
    Font buttonFont()
    {
        font = new Font("Times New Roman",Font.BOLD,22);
        return font;
    }
}
