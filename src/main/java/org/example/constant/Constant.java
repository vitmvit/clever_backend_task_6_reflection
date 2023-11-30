package org.example.constant;

import com.itextpdf.text.Font;

public class Constant {

    public static final String SOLUTION_CONFIG = "application.yml";
    public static final String LRU = "lru";
    public static final String LFU = "lfu";
    public static final float LOAD_FACTOR = 1.0f;
    public static final float CAPACITY_FACTOR = 1.5f;
    public static final String DIRECTORY = "report";
    public static final String BACKGROUND_IMAGE = "report_background/Clevertec_Template.jpg";
    public static String FILE_BASE = DIRECTORY + "/report_";
    public static String PDF = ".pdf";
    public static Font FONT_TITLE = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
    public static Font FONT_DATA = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    public static Font FONT_BODY = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

}
